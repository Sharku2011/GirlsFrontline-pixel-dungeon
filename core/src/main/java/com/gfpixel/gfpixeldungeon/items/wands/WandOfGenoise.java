package com.gfpixel.gfpixeldungeon.items.wands;

import com.gfpixel.gfpixeldungeon.Assets;
import com.gfpixel.gfpixeldungeon.Dungeon;
import com.gfpixel.gfpixeldungeon.actors.Actor;
import com.gfpixel.gfpixeldungeon.actors.Char;
import com.gfpixel.gfpixeldungeon.actors.blobs.Blob;
import com.gfpixel.gfpixeldungeon.actors.blobs.GenoiseWarn;
import com.gfpixel.gfpixeldungeon.actors.blobs.GooWarn;
import com.gfpixel.gfpixeldungeon.effects.CellEmitter;
import com.gfpixel.gfpixeldungeon.effects.particles.BlastParticle;
import com.gfpixel.gfpixeldungeon.effects.particles.SmokeParticle;
import com.gfpixel.gfpixeldungeon.effects.particles.StaffParticle;
import com.gfpixel.gfpixeldungeon.items.Heap;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.G11;
import com.gfpixel.gfpixeldungeon.mechanics.Ballistica;
import com.gfpixel.gfpixeldungeon.scenes.GameScene;
import com.gfpixel.gfpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.HashSet;
import java.util.Iterator;

public class WandOfGenoise extends Wand {
    {
        image = ItemSpriteSheet.WAND_TRANSFUSION;

        collisionProperties = Ballistica.PROJECTILE;
    }

    private HashSet<Genoise> Genoises = new HashSet<>();

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

        Genoise newGenoise = new Genoise( beam.collisionPos );
        Actor.addDelayed(newGenoise, TIME_TO_EXPLODE);

        Genoises.add( newGenoise );
    }

    @Override
    public void updateLevel() {
        maxCharges = 1;
        curCharges = 1;
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

    public int damageRoll() {
        return Random.NormalIntRange(20,40);
    }

    private static final String GENOISEPOS      = "GenoisePos";
    private static final String GENOISETIME     = "GenoiseTime";
    private static final String NUMGENOISE      = "numGenoise";

    private int NumOfGenoise = 0;

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);

        NumOfGenoise = Genoises.size();
        bundle.put( NUMGENOISE, NumOfGenoise );

        Iterator<Genoise> it = Genoises.iterator();

        for (int i=0; i<NumOfGenoise; ++i) {
            Genoise g = it.next();
            bundle.put( GENOISEPOS + String.valueOf(i), g.getTarget() );
            bundle.put( GENOISETIME + String.valueOf(i), g.cooldown() );
        }
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);

        NumOfGenoise = bundle.getInt( NUMGENOISE );

        for (int i=0; i< NumOfGenoise; ++i) {
            Genoise g = new Genoise( bundle.getInt(GENOISEPOS + String.valueOf(i) ) );
            Actor.addDelayed( g , bundle.getFloat( GENOISETIME + String.valueOf(i) ) );
            Genoises.add(g);
        }
    }

    private class Genoise extends Actor {

        private int target = -1;

        Genoise(int cell) {
            target = cell;
        }

        public final int getTarget() { return target; }

        @Override
        protected boolean act() {

            if (!Genoises.contains(Genoise.this)) {
                return false;
            }

            Genoises.remove(Genoise.this);

            Sample.INSTANCE.play( Assets.SND_BLAST );

            if (target >= 0 && Dungeon.level.heroFOV[target]) {
                CellEmitter.center( target ).burst( BlastParticle.FACTORY, 30 );
            }

            boolean terrainAffected = false;
            for (int n : PathFinder.NEIGHBOURS9) {
                int c = target + n;
                if (c >= 0 && c < Dungeon.level.length()) {
                    if (Dungeon.level.heroFOV[c]) {
                        CellEmitter.get( c ).burst( SmokeParticle.FACTORY, 4 );
                    }

                    if (Dungeon.level.flamable[c]) {
                        Dungeon.level.destroy( c );
                        GameScene.updateMap( c );
                        terrainAffected = true;
                    }

                    //destroys items / triggers bombs caught in the blast.
                    Heap heap = Dungeon.level.heaps.get( c );
                    if(heap != null)
                        heap.explode();

                    Char ch = Actor.findChar( c );
                    if (ch != null) {
                        //those not at the center of the blast take damage less consistently.
                        int dmg = (c == target ? damageRoll() * 2: damageRoll()) - ch.drRoll();
                        if (dmg > 0) {
                            ch.damage( dmg , Dungeon.hero );
                        }

                        if (ch == Dungeon.hero && !ch.isAlive()) {
                            Dungeon.fail( getClass() );
                        }
                    }
                }
            }

            if (terrainAffected) {
                Dungeon.observe();
            }

            remove(Genoise.this);
            return true;
        }
    }
}
