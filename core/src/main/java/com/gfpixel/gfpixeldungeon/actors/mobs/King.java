/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2018 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.gfpixel.gfpixeldungeon.actors.mobs;

import com.gfpixel.gfpixeldungeon.Assets;
import com.gfpixel.gfpixeldungeon.Badges;
import com.gfpixel.gfpixeldungeon.Dungeon;
import com.gfpixel.gfpixeldungeon.actors.Actor;
import com.gfpixel.gfpixeldungeon.actors.Char;
import com.gfpixel.gfpixeldungeon.actors.blobs.ToxicGas;
import com.gfpixel.gfpixeldungeon.actors.buffs.Amok;
import com.gfpixel.gfpixeldungeon.actors.buffs.Blindness;
import com.gfpixel.gfpixeldungeon.actors.buffs.Buff;
import com.gfpixel.gfpixeldungeon.actors.buffs.LockedFloor;
import com.gfpixel.gfpixeldungeon.actors.buffs.Paralysis;
import com.gfpixel.gfpixeldungeon.actors.buffs.Sleep;
import com.gfpixel.gfpixeldungeon.actors.buffs.Terror;
import com.gfpixel.gfpixeldungeon.actors.buffs.Vertigo;
import com.gfpixel.gfpixeldungeon.effects.CellEmitter;
import com.gfpixel.gfpixeldungeon.effects.Flare;
import com.gfpixel.gfpixeldungeon.effects.Speck;
import com.gfpixel.gfpixeldungeon.effects.particles.PurpleParticle;
import com.gfpixel.gfpixeldungeon.items.ArmorKit;
import com.gfpixel.gfpixeldungeon.items.artifacts.LloydsBeacon;
import com.gfpixel.gfpixeldungeon.items.keys.SkeletonKey;
import com.gfpixel.gfpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.gfpixel.gfpixeldungeon.items.wands.WandOfDisintegration;
import com.gfpixel.gfpixeldungeon.items.weapon.enchantments.Grim;
import com.gfpixel.gfpixeldungeon.items.weapon.enchantments.Vampiric;
import com.gfpixel.gfpixeldungeon.levels.CityBossLevel;
import com.gfpixel.gfpixeldungeon.mechanics.Ballistica;
import com.gfpixel.gfpixeldungeon.messages.Messages;
import com.gfpixel.gfpixeldungeon.scenes.GameScene;
import com.gfpixel.gfpixeldungeon.sprites.CharSprite;
import com.gfpixel.gfpixeldungeon.sprites.EyeSprite;
import com.gfpixel.gfpixeldungeon.sprites.KingSprite;
import com.gfpixel.gfpixeldungeon.sprites.UndeadSprite;
import com.gfpixel.gfpixeldungeon.sprites.hydraSprite;
import com.gfpixel.gfpixeldungeon.ui.BossHealthBar;
import com.gfpixel.gfpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class King extends Mob {
	
	private static final int MAX_ARMY_SIZE	= 5;
	
	{
		spriteClass = KingSprite.class;
		
		HP = HT = 30000;
		EXP = 40;
		defenseSkill = 25;
		
		Undead.count = 0;

		properties.add(Property.BOSS);
		properties.add(Property.UNDEAD);
	}
	
	private boolean nextPedestal = true;
	
	private static final String PEDESTAL = "pedestal";
	
	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( PEDESTAL, nextPedestal );
	}
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		nextPedestal = bundle.getBoolean( PEDESTAL );
		BossHealthBar.assignBoss(this);
	}
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 25, 40 );
	}
	
	@Override
	public int attackSkill( Char target ) {
		return 32;
	}
	
	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 14);
	}
	
	@Override
	protected boolean getCloser( int target ) {
		return canTryToSummon() ?
			super.getCloser( ((CityBossLevel)Dungeon.level).pedestal( nextPedestal ) ) :
			super.getCloser( target );
	}
	
	@Override
	protected boolean canAttack( Char enemy ) {
		return canTryToSummon() ?
			pos == ((CityBossLevel)Dungeon.level).pedestal( nextPedestal ) :
			Dungeon.level.adjacent( pos, enemy.pos );
	}
	
	private boolean canTryToSummon() {
		if (Undead.count < maxArmySize()) {
			Char ch = Actor.findChar( ((CityBossLevel)Dungeon.level).pedestal( nextPedestal ) );
			return ch == this || ch == null;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean attack( Char enemy ) {
		if (canTryToSummon() && pos == ((CityBossLevel)Dungeon.level).pedestal( nextPedestal )) {
			summon();
			return true;
		} else {
			if (Actor.findChar( ((CityBossLevel)Dungeon.level).pedestal( nextPedestal ) ) == enemy) {
				nextPedestal = !nextPedestal;
			}
			return super.attack(enemy);
		}
	}

	@Override
	public void damage(int dmg, Object src) {
		super.damage(dmg, src);
		LockedFloor lock = Dungeon.hero.buff(LockedFloor.class);
		if (lock != null) lock.addTime(dmg);
	}
	
	@Override
	public void die( Object cause ) {

		GameScene.bossSlain();
		Dungeon.level.drop( new ArmorKit(), pos ).sprite.drop();
		Dungeon.level.drop( new SkeletonKey( Dungeon.depth ), pos ).sprite.drop();
		
		super.die( cause );
		
		Badges.validateBossSlain();

		LloydsBeacon beacon = Dungeon.hero.belongings.getItem(LloydsBeacon.class);
		if (beacon != null) {
			beacon.upgrade();
		}
		
		yell( Messages.get(this, "defeated", Dungeon.hero.givenName()) );
	}

	@Override
	public void aggro(Char ch) {
		super.aggro(ch);
		for (Mob mob : Dungeon.level.mobs){
			if (mob instanceof Undead){
				mob.aggro(ch);
			}
		}
	}

	private int maxArmySize() {
		return 1 + MAX_ARMY_SIZE * (HT - HP) / HT;
	}
	
	private void summon() {

		nextPedestal = !nextPedestal;
		
		sprite.centerEmitter().start( Speck.factory( Speck.SCREAM ), 0.4f, 2 );
		Sample.INSTANCE.play( Assets.SND_CHALLENGE );
		
		boolean[] passable = Dungeon.level.passable.clone();
		for (Char c : Actor.chars()) {
			passable[c.pos] = false;
		}
		
		int undeadsToSummon = maxArmySize() - Undead.count;

		PathFinder.buildDistanceMap( pos, passable, undeadsToSummon );
		PathFinder.distance[pos] = Integer.MAX_VALUE;
		int dist = 1;
		
	undeadLabel:
		for (int i=0; i < undeadsToSummon; i++) {
			do {
				for (int j=0; j < Dungeon.level.length(); j++) {
					if (PathFinder.distance[j] == dist) {
						
						Undead undead = new Undead();
						undead.pos = j;
						GameScene.add( undead );
						
						ScrollOfTeleportation.appear( undead, j );
						new Flare( 3, 32 ).color( 0x000000, false ).show( undead.sprite, 2f ) ;
						
						PathFinder.distance[j] = Integer.MAX_VALUE;
						
						continue undeadLabel;
					}
				}
				dist++;
			} while (dist < undeadsToSummon);
		}
		
		yell( Messages.get(this, "arise") );
	}
	
	@Override
	public void notice() {
		super.notice();
		BossHealthBar.assignBoss(this);
		yell( Messages.get(this, "notice") );
	}
	
	{
		resistances.add( WandOfDisintegration.class );
	}
	
	{
		immunities.add( Paralysis.class );
		immunities.add( Vertigo.class );
		immunities.add( Blindness.class );
		immunities.add( Terror.class );
	}
	
	public static class Undead extends Mob {
		
		public static int count = 0;
		
		{
			spriteClass = UndeadSprite.class;
			
			HP = HT = 28;
			defenseSkill = 15;
			
			EXP = 0;
			
			state = WANDERING;

			properties.add(Property.UNDEAD);
			properties.add(Property.INORGANIC);
		}

        public int DamageReducer() { return 3; }

		@Override
		protected void onAdd() {
			count++;
			super.onAdd();
		}

		@Override
		protected void onRemove() {
			count--;
			super.onRemove();
		}

		@Override
		public int attackProc( Char enemy, int damage ) {
			damage = super.attackProc( enemy, damage );
			if (Random.Int( MAX_ARMY_SIZE ) == 0) {
				Buff.prolong( enemy, Paralysis.class, 1 );
			}

			return damage;
		}

        @Override
        public int damageRoll() {
            return Random.NormalIntRange(25, 35);
        }

        @Override
        public int attackSkill( Char target ) {
            return 30;
        }

        @Override
        protected float attackDelay() {
            return 0.5f;
        }

        @Override
        public int drRoll() {
            return Random.NormalIntRange(0, 10);
        }

        private Ballistica beam;
        private int beamTarget = -1;
        private int beamCooldown;
        public boolean beamCharged;

        @Override
        protected boolean canAttack( Char enemy ) {

            if (beamCooldown == 0) {
                Ballistica aim = new Ballistica(pos, enemy.pos, Ballistica.STOP_TERRAIN);

                if (enemy.invisible == 0 && !isCharmedBy(enemy) && fieldOfView[enemy.pos] && aim.subPath(1, aim.dist).contains(enemy.pos)){
                    beam = aim;
                    beamTarget = aim.collisionPos;
                    return true;
                } else
                    //if the beam is charged, it has to attack, will aim at previous location of target.
                    return beamCharged;
            } else
                return super.canAttack(enemy);
        }

        @Override
        protected boolean act() {
            if (beamCharged && state != HUNTING){
                beamCharged = false;
            }
            if (beam == null && beamTarget != -1) {
                beam = new Ballistica(pos, beamTarget, Ballistica.STOP_TERRAIN);
                sprite.turnTo(pos, beamTarget);
            }
            if (beamCooldown > 0)
                beamCooldown--;
            return super.act();
        }

        @Override
        protected boolean doAttack( Char enemy ) {

            if (beamCooldown > 0) {
                return super.doAttack(enemy);
            } else if (!beamCharged){
                ((hydraSprite)sprite).charge( enemy.pos );
                spend( attackDelay()*2f );
                beamCharged = true;
                return true;
            } else {

                spend( attackDelay() );

                beam = new Ballistica(pos, beamTarget, Ballistica.STOP_TERRAIN);
                if (Dungeon.level.heroFOV[pos] || Dungeon.level.heroFOV[beam.collisionPos] ) {
                    sprite.zap( beam.collisionPos );
                    return false;
                } else {
                    deathGaze();
                    return true;
                }
            }

        }

        // Reduce damage during charge. Nerf this 4 to 2.
        @Override
        public void damage(int dmg, Object src) {
            if (beamCharged) dmg /= DamageReducer();
            super.damage(dmg, src);
        }

        public void deathGaze(){
            if (!beamCharged || beamCooldown > 0 || beam == null)
                return;

            beamCharged = false;
            beamCooldown = Random.IntRange(3, 6);

            boolean terrainAffected = false;

            for (int pos : beam.subPath(1, beam.dist)) {

                if (Dungeon.level.flamable[pos]) {

                    Dungeon.level.destroy( pos );
                    GameScene.updateMap( pos );
                    terrainAffected = true;

                }

                Char ch = Actor.findChar( pos );
                if (ch == null) {
                    continue;
                }

                if (hit( this, ch, true )) {
                    ch.damage( Random.NormalIntRange( 30, 50 ), this );

                    if (Dungeon.level.heroFOV[pos]) {
                        ch.sprite.flash();
                        CellEmitter.center( pos ).burst( PurpleParticle.BURST, Random.IntRange( 1, 2 ) );
                    }

                    if (!ch.isAlive() && ch == Dungeon.hero) {
                        Dungeon.fail( getClass() );
                        GLog.n( Messages.get(this, "deathgaze_kill") );
                    }
                } else {
                    ch.sprite.showStatus( CharSprite.NEUTRAL,  ch.defenseVerb() );
                }
            }

            if (terrainAffected) {
                Dungeon.observe();
            }

            beam = null;
            beamTarget = -1;
        }

        private static final String BEAM_TARGET     = "beamTarget";
        private static final String BEAM_COOLDOWN   = "beamCooldown";
        private static final String BEAM_CHARGED    = "beamCharged";

        @Override
        public void storeInBundle(Bundle bundle) {
            super.storeInBundle(bundle);
            bundle.put( BEAM_TARGET, beamTarget);
            bundle.put( BEAM_COOLDOWN, beamCooldown );
            bundle.put( BEAM_CHARGED, beamCharged );
        }

        @Override
        public void restoreFromBundle(Bundle bundle) {
            super.restoreFromBundle(bundle);
            if (bundle.contains(BEAM_TARGET))
                beamTarget = bundle.getInt(BEAM_TARGET);
            beamCooldown = bundle.getInt(BEAM_COOLDOWN);
            beamCharged = bundle.getBoolean(BEAM_CHARGED);
        }

        {
            resistances.add( WandOfDisintegration.class );
            resistances.add( Grim.class );
            resistances.add( Vampiric.class );
        }

        {
            immunities.add( Terror.class );
        }

        private class Hunting extends Mob.Hunting{
            @Override
            public boolean act(boolean enemyInFOV, boolean justAlerted) {
                //even if enemy isn't seen, attack them if the beam is charged
                if (beamCharged && enemy != null && canAttack(enemy)) {
                    enemySeen = enemyInFOV;
                    return doAttack(enemy);
                }
                return super.act(enemyInFOV, justAlerted);
            }
        }
    }
}
