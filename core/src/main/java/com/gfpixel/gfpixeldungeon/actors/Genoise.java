package com.gfpixel.gfpixeldungeon.actors;

import com.gfpixel.gfpixeldungeon.Assets;
import com.gfpixel.gfpixeldungeon.Dungeon;
import com.gfpixel.gfpixeldungeon.effects.CellEmitter;
import com.gfpixel.gfpixeldungeon.effects.particles.BlastParticle;
import com.gfpixel.gfpixeldungeon.effects.particles.SmokeParticle;
import com.gfpixel.gfpixeldungeon.items.Generator;
import com.gfpixel.gfpixeldungeon.items.Heap;
import com.gfpixel.gfpixeldungeon.scenes.GameScene;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class Genoise extends Actor {
    private int target = -1;
    private int minDmg = 0;
    private int maxDmg = 0;

    public float fuseTime;

    public static Genoise newGenoise(int cell, int min, int max, float time) {
        Genoise genoise = new Genoise();
        genoise.target = cell;
        genoise.minDmg = min;
        genoise.maxDmg = max;
        genoise.fuseTime = genoise.getNow()+time;
        return genoise;
    }

    private static final String TARGET = "target";
    private static final String MINDMG = "mindmg";
    private static final String MAXDMG = "maxdmg";
    private static final String FUSETIME = "fusetime";

    public final int getTarget() { return target; }

    @Override
    protected boolean act() {

        if (getTime() < fuseTime)
        {
            spend(TICK);
            return true;
        }

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
                    int dmg = Random.NormalIntRange(minDmg, maxDmg) * (c == target ? 2 : 1) - ch.drRoll();
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

        Actor.remove(this);
        return true;
    }

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);

        bundle.put(TARGET, target);
        bundle.put(MINDMG, minDmg);
        bundle.put(MAXDMG, maxDmg);
        bundle.put(FUSETIME, fuseTime);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);

        target = bundle.getInt(TARGET);
        minDmg = bundle.getInt(MINDMG);
        maxDmg = bundle.getInt(MAXDMG);
        fuseTime = bundle.getFloat(FUSETIME);
    }
}
