package com.gfpixel.gfpixeldungeon.actors.mobs;

import com.gfpixel.gfpixeldungeon.Badges;
import com.gfpixel.gfpixeldungeon.Dungeon;
import com.gfpixel.gfpixeldungeon.actors.Actor;
import com.gfpixel.gfpixeldungeon.actors.Char;
import com.gfpixel.gfpixeldungeon.actors.buffs.Terror;
import com.gfpixel.gfpixeldungeon.effects.CellEmitter;
import com.gfpixel.gfpixeldungeon.effects.particles.PurpleParticle;
import com.gfpixel.gfpixeldungeon.items.scrolls.ScrollOfPsionicBlast;
import com.gfpixel.gfpixeldungeon.items.wands.WandOfDisintegration;
import com.gfpixel.gfpixeldungeon.items.weapon.enchantments.Grim;
import com.gfpixel.gfpixeldungeon.items.weapon.enchantments.Vampiric;
import com.gfpixel.gfpixeldungeon.mechanics.Ballistica;
import com.gfpixel.gfpixeldungeon.messages.Messages;
import com.gfpixel.gfpixeldungeon.scenes.GameScene;
import com.gfpixel.gfpixeldungeon.sprites.CharSprite;
import com.gfpixel.gfpixeldungeon.sprites.TyphoonSprite;
import com.gfpixel.gfpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Typhoon extends Hydra {

    {
        spriteClass = TyphoonSprite.class;

        HP = HT = 1800;
        EXP = 100;
        defenseSkill = 5;
        baseSpeed = 0.6f;
        maxLvl = 36;

        properties.add(Property.DEMONIC);
        properties.add(Property.UNDEAD);
    }

    @Override
    public int DamageReducer() { return 4; }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange(5, 10);
    }

    @Override
    public int attackSkill( Char target ) {
        return 10;
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
            ((TyphoonSprite)sprite).charge( enemy.pos );
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

    public void deathGaze(){
        if (!beamCharged || beamCooldown > 0 || beam == null)
            return;

        beamCharged = false;
        beamCooldown = 1;

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
        resistances.add( ScrollOfPsionicBlast.class );
        resistances.add( Grim.class );
        resistances.add( Vampiric.class );
    }

    {
        immunities.add( Terror.class );
    }

    @Override
    public void die( Object cause ) {
        super.die( cause );
        //Badges.validateRare( this );
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
