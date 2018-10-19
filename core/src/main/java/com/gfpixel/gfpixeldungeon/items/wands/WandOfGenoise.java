package com.gfpixel.gfpixeldungeon.items.wands;

import com.gfpixel.gfpixeldungeon.actors.Actor;
import com.gfpixel.gfpixeldungeon.actors.Char;
import com.gfpixel.gfpixeldungeon.actors.Genoise;
import com.gfpixel.gfpixeldungeon.actors.blobs.Blob;
import com.gfpixel.gfpixeldungeon.actors.blobs.GenoiseWarn;
import com.gfpixel.gfpixeldungeon.actors.blobs.GooWarn;
import com.gfpixel.gfpixeldungeon.actors.buffs.LockedFloor;
import com.gfpixel.gfpixeldungeon.actors.buffs.Recharging;
import com.gfpixel.gfpixeldungeon.actors.mobs.npcs.Noel;
import com.gfpixel.gfpixeldungeon.effects.particles.StaffParticle;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.G11;
import com.gfpixel.gfpixeldungeon.mechanics.Ballistica;
import com.gfpixel.gfpixeldungeon.scenes.GameScene;
import com.gfpixel.gfpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.PathFinder;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

public class WandOfGenoise extends Wand {
    {
        image = ItemSpriteSheet.WAND_TRANSFUSION;

        collisionProperties = Ballistica.PROJECTILE;

        charger = new Charger();
    }

    private static final float TIME_TO_EXPLODE = 2f;

    @Override
    protected void onZap(Ballistica beam) {
        for (int n : PathFinder.NEIGHBOURS8) {
            int c = beam.collisionPos + n;
            if ( c >= 0 && Blob.volumeAt( c, GooWarn.class ) == 0 ) {
                GameScene.add( Blob.seed( c, Math.round( 1 + TIME_TO_EXPLODE ), GooWarn.class) );
            }
        }

        if (beam.collisionPos >= 0 && Blob.volumeAt( beam.collisionPos, GenoiseWarn.class ) == 0) {
            GameScene.add( Blob.seed( beam.collisionPos, Math.round( 1 + TIME_TO_EXPLODE ), GenoiseWarn.class) );
        }

        Genoise newGenoise = Genoise.newGenoise( beam.collisionPos, min(level()), max(level()), TIME_TO_EXPLODE );
        Actor.add(newGenoise);

    }

    @Override
    public void updateLevel() {
        maxCharges = 1;
        curCharges = Noel.Quest.completed() ? 2 : 1;
    }

    @Override
    protected int initialCharges() {
        return 1;
    }

    @Override
    public void onHit(G11 staff, Char attacker, Char defender, int damage) {}

    @Override
    public void staffFx(StaffParticle particle) {
        particle.color( 0xCC0000 );
        particle.am = 0.6f;
        particle.setLifespan(1f);
        particle.speed.polar( Random.Float(PointF.PI2), 2f );
        particle.setSize(1f, 2f);
        particle.radiateXY(0.5f);
    }

    public int min(int lvl){
        return 20 + lvl;
    }

    public int max(int lvl){
        return 30 + 2*lvl;
    }

    public class Charger extends Wand.Charger {

        @Override
        protected void recharge(){

            float turnsToCharge = 25f;

            LockedFloor lock = target.buff(LockedFloor.class);
            if (lock == null || lock.regenOn())
                partialCharge += 1f/turnsToCharge;

            for (Recharging bonus : target.buffs(Recharging.class)){
                if (bonus != null && bonus.remainder() > 0f) {
                    partialCharge += CHARGE_BUFF_BONUS * bonus.remainder();
                }
            }
        }
    }

}
