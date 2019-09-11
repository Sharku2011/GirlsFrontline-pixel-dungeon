package com.gfpixel.gfpixeldungeon.actors.mobs;

import com.gfpixel.gfpixeldungeon.Dungeon;
import com.gfpixel.gfpixeldungeon.actors.Actor;
import com.gfpixel.gfpixeldungeon.actors.Char;
import com.gfpixel.gfpixeldungeon.effects.CellEmitter;
import com.gfpixel.gfpixeldungeon.effects.particles.PurpleParticle;
import com.gfpixel.gfpixeldungeon.levels.Level;
import com.gfpixel.gfpixeldungeon.mechanics.Ballistica;
import com.gfpixel.gfpixeldungeon.messages.Messages;
import com.gfpixel.gfpixeldungeon.scenes.GameScene;
import com.gfpixel.gfpixeldungeon.sprites.BeamChargeMobSprite;
import com.gfpixel.gfpixeldungeon.sprites.CharSprite;
import com.gfpixel.gfpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

public abstract class BeamChargeMob extends Mob {

    {
        SLEEPING = new Sleeping();
        WANDERING = new Wandering();
        HUNTING = new Hunting();
        FLEEING = new Fleeing();
    }

    private ChargeManager chargeManager = new ChargeManager();

    public boolean isCharging() {
        return chargeManager.isCharging();
    }

    public boolean isCharged() {
        return chargeManager.isCharged();
    }

    public int attackRange() {
        return 5;
    }

    public int damageReducer() {
        return 1;
    }

    @Override
    public void damage(int dmg, Object src) {
        if (chargeManager.isCharging()) {
            dmg /= damageReducer();
        }
        super.damage(dmg, src);
    }

    @Override
    protected boolean canAttack( Char enemy ) {
        return Dungeon.level.distance( pos, enemy.pos ) <= attackRange();
    }

    @Override
    protected boolean doAttack( Char enemy ) {

        boolean visible = Dungeon.level.heroFOV[pos];

        if (chargeManager.isCharged()) {
            if (visible && sprite instanceof BeamChargeMobSprite) {
                sprite.attack( chargeManager.beamTarget );
            } else {
                chargeManager.shootBeam();
            }
        } else {
            if (visible && sprite instanceof BeamChargeMobSprite) {
                ((BeamChargeMobSprite)sprite).charge( chargeManager.beamTarget );
            } else {
                chargeManager.increaseCharge();
            }
        }

        spend( attackDelay() );

        return !visible;
    }

    // 근접공격을 불가능하게 하기 위하여 부모의 메소드를 호출하지 않도록 수정
    @Override
    public void onAttackComplete() {
        next();
    }

    public void shootBeam() {
        chargeManager.shootBeam();
    }

    public void charge() {
        chargeManager.increaseCharge();
    }

    protected class Sleeping extends Mob.Sleeping {
        @Override
        public boolean act(boolean enemyInFOV, boolean justAlerted) {
            chargeManager.resetCharge();
            return super.act( enemyInFOV, justAlerted );
        }
    }

    protected class Wandering extends Mob.Wandering {
        @Override
        public boolean act(boolean enemyInFOV, boolean justAlerted) {
            chargeManager.decreaseCharge();
            return super.act( enemyInFOV, justAlerted );
        }
    }

    protected class Hunting extends Mob.Hunting {
        @Override
        public boolean act(boolean enemyInFOV, boolean justAlerted) {

            enemySeen = enemyInFOV;
            if (enemyInFOV && !isCharmedBy( enemy ) && canAttack( enemy )) {
                target = enemy.pos;
                chargeManager.updateBeamTrace();
                return doAttack( enemy );
            } else {

                if (enemyInFOV) {
                    target = enemy.pos;
                    chargeManager.updateBeamTrace();
                } else if (enemy == null) {
                    state = WANDERING;
                    target = Dungeon.level.randomDestination();
                    return true;
                }

                int oldPos = pos;
                if (target != -1 && getCloser( target )) {

                    spend( 1 / speed() );
                    return moveSprite( oldPos,  pos );

                } else {
                    spend( TICK );
                    if (!enemyInFOV) {
                        sprite.showLost();
                        state = WANDERING;
                        target = Dungeon.level.randomDestination();
                    }
                    return true;
                }
            }
        }
    }

    protected class Fleeing extends Mob.Fleeing {
        @Override
        public boolean act(boolean enemyInFOV, boolean justAlerted) {
            chargeManager.decreaseCharge();
            return super.act( enemyInFOV, justAlerted );
        }
    }

    protected class ChargeManager {

        protected Ballistica beam;
        private int beamTarget = Level.INVALID_POS;
        private int curChargeCount = 0;
        private int maxChargeCount = 1;

        private Point chargeAmountRange = new Point( 1, 1 );

        public boolean isCharged() {
            return curChargeCount >= maxChargeCount;
        }

        public boolean isCharging() {
            return curChargeCount < maxChargeCount;
        }

        private void resetCharge() {
            curChargeCount = 0;
        }

        private void increaseCharge() {
            if (curChargeCount >= maxChargeCount) {
                curChargeCount = maxChargeCount;
            }

            curChargeCount += Random.IntRange( chargeAmountRange.x, chargeAmountRange.y );
        }

        private void decreaseCharge() {
            if (curChargeCount <= 0) {
                curChargeCount = 0;
            }

            curChargeCount--;
        }

        private boolean updateBeamTrace() {
            // target의 위치가 유효하지 않을 경우 아무것도 하지 않는다
            if ( !Dungeon.level.insideMap( target ) ) {
                beam = null;
                beamTarget = Level.INVALID_POS;
                return true;
            }

            beam = new Ballistica( pos, target, Ballistica.STOP_TERRAIN );

            if ( !Dungeon.level.insideMap(beam.collisionPos) ) {
                beam = null;
                beamTarget = Level.INVALID_POS;
                return true;
            }

            beamTarget = beam.collisionPos;
            return true;
        }

        private void shootBeam() {
            if ( curChargeCount < maxChargeCount || beam == null || beamTarget < 0 ) {
                return;
            }

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

                if (hit( BeamChargeMob.this, ch, true )) {
                    ch.damage( BeamChargeMob.this.damageRoll(), this );

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
            resetCharge();
        }
    }

    private static final String BEAM_TARGET     = "beamTarget";
    private static final String BEAM_CHARGECOUNT    = "beamChargeCount";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put( BEAM_TARGET, chargeManager.beamTarget);
        bundle.put( BEAM_CHARGECOUNT, chargeManager.curChargeCount );
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        if (bundle.contains(BEAM_TARGET)) {
            chargeManager.beamTarget = bundle.getInt(BEAM_TARGET);
        } else {
            chargeManager.beamTarget = -1;
        }

        if (bundle.contains(BEAM_CHARGECOUNT)) {
            chargeManager.curChargeCount = bundle.getInt(BEAM_CHARGECOUNT);
        } else {
            chargeManager.curChargeCount = 0;
        }

    }
}
