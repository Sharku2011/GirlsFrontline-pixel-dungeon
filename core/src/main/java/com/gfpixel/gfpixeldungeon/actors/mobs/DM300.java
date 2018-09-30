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
import com.gfpixel.gfpixeldungeon.DialogInfo;
import com.gfpixel.gfpixeldungeon.Dungeon;
import com.gfpixel.gfpixeldungeon.actors.Actor;
import com.gfpixel.gfpixeldungeon.actors.Char;
import com.gfpixel.gfpixeldungeon.actors.blobs.Blob;
import com.gfpixel.gfpixeldungeon.actors.blobs.ToxicGas;
import com.gfpixel.gfpixeldungeon.actors.buffs.Buff;
import com.gfpixel.gfpixeldungeon.actors.buffs.Charm;
import com.gfpixel.gfpixeldungeon.actors.buffs.LockedFloor;
import com.gfpixel.gfpixeldungeon.actors.buffs.Paralysis;
import com.gfpixel.gfpixeldungeon.actors.buffs.Terror;
import com.gfpixel.gfpixeldungeon.effects.CellEmitter;
import com.gfpixel.gfpixeldungeon.effects.Speck;
import com.gfpixel.gfpixeldungeon.effects.particles.BloodParticle;
import com.gfpixel.gfpixeldungeon.effects.particles.ElmoParticle;
import com.gfpixel.gfpixeldungeon.items.artifacts.CapeOfThorns;
import com.gfpixel.gfpixeldungeon.items.artifacts.LloydsBeacon;
import com.gfpixel.gfpixeldungeon.items.keys.SkeletonKey;
import com.gfpixel.gfpixeldungeon.levels.Level;
import com.gfpixel.gfpixeldungeon.levels.Terrain;
import com.gfpixel.gfpixeldungeon.mechanics.Ballistica;
import com.gfpixel.gfpixeldungeon.messages.Messages;
import com.gfpixel.gfpixeldungeon.scenes.GameScene;
import com.gfpixel.gfpixeldungeon.sprites.DM300Sprite;
import com.gfpixel.gfpixeldungeon.ui.BossHealthBar;
import com.gfpixel.gfpixeldungeon.utils.GLog;
import com.gfpixel.gfpixeldungeon.windows.WndDialog;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class DM300 extends Mob {
	
	{
		spriteClass = DM300Sprite.class;
		
		HP = HT = 270;
		EXP = 30;
		defenseSkill = 18;
		
		loot = new CapeOfThorns();
		lootChance = 0.333f;

		properties.add(Property.BOSS);
		properties.add(Property.INORGANIC);


	}

	private final int RANGE = 3;
	private Ballistica aim;

	@Override
	protected boolean canAttack( Char enemy ) {
		if (enemy == null) { return false; }
		aim = new Ballistica(pos, enemy.pos, Ballistica.PROJECTILE);
		return aim.collisionPos == enemy.pos && aim.dist <= RANGE;
	}

	@Override
	protected boolean doAttack( Char enemy ) {
		boolean visible = Dungeon.level.heroFOV[pos];

		if (visible) {
			sprite.attack( enemy.pos );
		} else {
			magnum();
		}

		spend( attackDelay() );

		return !visible;
	}

	public void magnum() {
		for (int c : aim.subPath(0, aim.dist)) {
			CellEmitter.center(c).burst(BloodParticle.BURST, 1);
		}
		
		Char ch = findChar(aim.collisionPos);
		if (ch != null) {
			ch.damage(damageRoll() - ch.drRoll(), DM300.this );
			ch.sprite.centerEmitter().start( Speck.factory( Speck.HEART ), 0.2f, 5 );
		}
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 20, 30 );
	}
	
	@Override
	public int attackSkill( Char target ) {
		return 28;
	}
	
	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 10);
	}
	
	@Override
	public boolean act() {
		
		GameScene.add( Blob.seed( pos, 30, ToxicGas.class ) );
		
		return super.act();
	}
	
	@Override
	public void move( int step ) {
		super.move( step );
		
		if (Dungeon.level.map[step] == Terrain.INACTIVE_TRAP && HP < HT) {
			
			HP += Random.Int( 1, HT - HP );
			sprite.emitter().burst( ElmoParticle.FACTORY, 5 );
			
			if (Dungeon.level.heroFOV[step] && Dungeon.hero.isAlive()) {
				GLog.n( Messages.get(this, "repair") );
			}
		}
		
		int[] cells = {
			step-1, step+1, step-Dungeon.level.width(), step+Dungeon.level.width(),
			step-1-Dungeon.level.width(),
			step-1+Dungeon.level.width(),
			step+1-Dungeon.level.width(),
			step+1+Dungeon.level.width()
		};
		int cell = cells[Random.Int( cells.length )];
		
		if (Dungeon.level.heroFOV[cell]) {
			CellEmitter.get( cell ).start( Speck.factory( Speck.ROCK ), 0.07f, 10 );
			Camera.main.shake( 3, 0.7f );
			Sample.INSTANCE.play( Assets.SND_ROCKS );
			
			if (Dungeon.level.water[cell]) {
				GameScene.ripple( cell );
			} else if (Dungeon.level.map[cell] == Terrain.EMPTY) {
				Level.set( cell, Terrain.EMPTY_DECO );
				GameScene.updateMap( cell );
			}
		}

		Char ch = Actor.findChar( cell );
		if (ch != null && ch != this) {
			Buff.prolong( ch, Paralysis.class, 2 );
		}
	}

	private final int damageCap = 50;

	@Override
	public void damage(int dmg, Object src) {
		int finalDmg = Math.max(dmg, damageCap);
		super.damage(finalDmg, src);
		LockedFloor lock = Dungeon.hero.buff(LockedFloor.class);
		if (lock != null && !isImmune(src.getClass())) lock.addTime(dmg*1.5f);
	}

	@Override
	public void die( Object cause ) {
		
		super.die( cause );
		
		GameScene.bossSlain();
		Dungeon.level.drop( new SkeletonKey( Dungeon.depth  ), pos ).sprite.drop();
		
		Badges.validateBossSlain();

		LloydsBeacon beacon = Dungeon.hero.belongings.getItem(LloydsBeacon.class);
		if (beacon != null) {
			beacon.upgrade();
		}
		
		yell( Messages.get(this, "defeated") );
	}

	@Override
	public void notice() {
		super.notice();
		BossHealthBar.assignBoss(this);
		if (!Dungeon.level.locked) {
			WndDialog.ShowChapter(DialogInfo.ID_CAVES_BOSS);
		}
	}
	
	{
		immunities.add( ToxicGas.class );
		immunities.add( Terror.class );
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		BossHealthBar.assignBoss(this);
	}
}
