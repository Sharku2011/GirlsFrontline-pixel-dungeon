package com.gfpixel.gfpixeldungeon.actors.mobs;

import com.gfpixel.gfpixeldungeon.Assets;
import com.gfpixel.gfpixeldungeon.Badges;
import com.gfpixel.gfpixeldungeon.Dungeon;
import com.gfpixel.gfpixeldungeon.actors.Actor;
import com.gfpixel.gfpixeldungeon.actors.Char;
import com.gfpixel.gfpixeldungeon.actors.blobs.Blob;
import com.gfpixel.gfpixeldungeon.actors.blobs.Freezing;
import com.gfpixel.gfpixeldungeon.actors.buffs.Buff;
import com.gfpixel.gfpixeldungeon.actors.buffs.LockedFloor;
import com.gfpixel.gfpixeldungeon.actors.buffs.Paralysis;
import com.gfpixel.gfpixeldungeon.actors.buffs.Terror;
import com.gfpixel.gfpixeldungeon.effects.CellEmitter;
import com.gfpixel.gfpixeldungeon.effects.Speck;
import com.gfpixel.gfpixeldungeon.effects.particles.ElmoParticle;
import com.gfpixel.gfpixeldungeon.items.Ankh;
import com.gfpixel.gfpixeldungeon.items.artifacts.LloydsBeacon;
import com.gfpixel.gfpixeldungeon.items.keys.SkeletonKey;
import com.gfpixel.gfpixeldungeon.items.scrolls.ScrollOfPsionicBlast;
import com.gfpixel.gfpixeldungeon.items.weapon.enchantments.Grim;
import com.gfpixel.gfpixeldungeon.levels.Level;
import com.gfpixel.gfpixeldungeon.levels.Terrain;
import com.gfpixel.gfpixeldungeon.messages.Messages;
import com.gfpixel.gfpixeldungeon.scenes.GameScene;
import com.gfpixel.gfpixeldungeon.sprites.TyphootinSprite;
import com.gfpixel.gfpixeldungeon.ui.BossHealthBar;
import com.gfpixel.gfpixeldungeon.utils.GLog;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Typhootin extends Mob {

    {
        spriteClass = TyphootinSprite.class;

        HP = HT = 700;
        EXP = 70;
        defenseSkill = 5;

        loot = new Ankh();
        lootChance = 1f;

        properties.add(Property.BOSS);
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 10, 60 );
    }

    @Override
    public int attackSkill( Char target ) {
        return 58;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 10);
    }

    @Override
    public boolean act() {

        GameScene.add( Blob.seed( pos, 30, Freezing.class ) );

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

    @Override
    public void damage(int dmg, Object src) {
        super.damage(dmg, src);
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
        yell( Messages.get(this, "notice") );
    }

    {
        resistances.add( Grim.class );
        resistances.add( ScrollOfPsionicBlast.class );
    }

    {
        immunities.add( Freezing.class );
        immunities.add( Terror.class );
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        BossHealthBar.assignBoss(this);
    }
}
