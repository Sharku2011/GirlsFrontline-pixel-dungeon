package com.gfpixel.gfpixeldungeon.actors.mobs;

import com.gfpixel.gfpixeldungeon.Assets;
import com.gfpixel.gfpixeldungeon.Dungeon;
import com.gfpixel.gfpixeldungeon.actors.Actor;
import com.gfpixel.gfpixeldungeon.actors.Char;
import com.gfpixel.gfpixeldungeon.actors.blobs.Blob;
import com.gfpixel.gfpixeldungeon.actors.blobs.GenoiseWarn;
import com.gfpixel.gfpixeldungeon.actors.blobs.GooWarn;
import com.gfpixel.gfpixeldungeon.actors.buffs.Buff;
import com.gfpixel.gfpixeldungeon.actors.buffs.Weakness;
import com.gfpixel.gfpixeldungeon.actors.hero.Hero;
import com.gfpixel.gfpixeldungeon.effects.CellEmitter;
import com.gfpixel.gfpixeldungeon.effects.particles.BlastParticle;
import com.gfpixel.gfpixeldungeon.effects.particles.SmokeParticle;
import com.gfpixel.gfpixeldungeon.items.Generator;
import com.gfpixel.gfpixeldungeon.items.Heap;
import com.gfpixel.gfpixeldungeon.items.potions.PotionOfHealing;
import com.gfpixel.gfpixeldungeon.messages.Messages;
import com.gfpixel.gfpixeldungeon.scenes.GameScene;
import com.gfpixel.gfpixeldungeon.sprites.JaguarSprite;
import com.gfpixel.gfpixeldungeon.utils.BArray;
import com.gfpixel.gfpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.HashSet;

/**
 * Created by Android SDK on 2018-08-15.
 */

public class Jaguar extends Mob {

    {
        spriteClass = JaguarSprite.class;

        HP = HT = 70;
        defenseSkill = 25;
        baseSpeed = 0.9f;

        EXP = 11;
        maxLvl = 21;
        loot = Generator.Category.WEAPON;
        lootChance = 0.15f;
    }

    private Bomb bomb;
    private static final float TIME_TO_EXPLODE = 1.0f;
    private static final int RANGE = 8;

    @Override
    protected float attackDelay() { return 2f; }

    @Override
    public boolean canAttack(Char enemy) {
        PathFinder.buildDistanceMap( enemy.pos, BArray.not( Dungeon.level.solid, null ), RANGE );

        return PathFinder.distance[pos] <= RANGE;
    }

    @Override
    public boolean doAttack(Char enemy) {

        boolean visible = Dungeon.level.heroFOV[pos];

        spend( attackDelay() );

        if (visible) {
            sprite.attack(enemy.pos);
        } else {
            fireBomb(enemy.pos);
        }

        return !visible;
    }

    public void fireBomb(int cell) {

        for (int n : PathFinder.NEIGHBOURS8) {
            int c = cell + n;
            if ( c >= 0 && Blob.volumeAt( c, GooWarn.class ) == 0 ) {
                GameScene.add( Blob.seed( c, Math.round( 1 + TIME_TO_EXPLODE ), GooWarn.class) );
            }
        }

        if (cell >= 0 && Blob.volumeAt( cell, GenoiseWarn.class ) == 0) {
            GameScene.add( Blob.seed( cell, Math.round( 1 + TIME_TO_EXPLODE ), GenoiseWarn.class) );
        }

        bomb = new Bomb(cell);
        addDelayed(bomb, TIME_TO_EXPLODE);

        next();
    }

    @Override
    public void onAttackComplete() {
        next();
    }

//공격속도 2턴에 1번 제누와즈 발사, 착탄후 1턴후 폭발하며 폭발에 적중시 약화상태가 됨. 폭발 반경 내에 아이템은 사라지지않음. 근접했을때도 제누와즈를 발사함.
    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 5, 12 );
    }

    @Override
    public int attackSkill( Char target ) {
        return 12;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 5);
    }

    private static final String BOMBPOS      = "BombPos";
    private static final String BOMBTIME     = "BombTime";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);

        if (bomb != null && bomb.getTarget() >= 0) {
            bundle.put(BOMBPOS, bomb.target);
            bundle.put(BOMBTIME, bomb.cooldown());
        } else {
            bundle.put(BOMBPOS, -1);
        }
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);

        int targetPos = bundle.getInt(BOMBPOS);

        if (targetPos != -1) {
            bomb = new Bomb(targetPos);
            addDelayed(bomb, bundle.getFloat(BOMBTIME));
        }
    }

    private class Bomb extends Actor {

        private int target = -1;

        Bomb(int cell) {
            target = cell;
        }

        public final int getTarget() { return target; }

        @Override
        protected boolean act() {

            if (bomb != Bomb.this) {
                return false;
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


                    Char ch = Actor.findChar( c );
                    if (ch == Dungeon.hero) {
                        Buff.prolong(ch, Weakness.class, Weakness.DURATION / 2f);
                    }
                }
            }

            if (Dungeon.level.flamable[target]) {
                Dungeon.level.destroy( target );
                GameScene.updateMap( target );
                terrainAffected = true;
            }

            Char ch = Actor.findChar(target);
            if (ch != null) {

                int dmg = damageRoll() - ch.drRoll();
                if (ch == Jaguar.this) {
                    dmg /= 2;
                }

                ch.damage( dmg,this );

                if (ch == Dungeon.hero && !ch.isAlive()) {
                    Dungeon.fail( Jaguar.this.getClass() );
                }
            }

            if (terrainAffected) {
                Dungeon.observe();
            }

            bomb = null;
            remove(Bomb.this);
            return true;
        }
    }
}