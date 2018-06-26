package com.gfpixel.gfpixeldungeon.sprites;

import com.gfpixel.gfpixeldungeon.Assets;
import com.gfpixel.gfpixeldungeon.actors.Actor;
import com.gfpixel.gfpixeldungeon.actors.Char;
import com.gfpixel.gfpixeldungeon.actors.mobs.Hydra;
import com.gfpixel.gfpixeldungeon.effects.Beam;
import com.gfpixel.gfpixeldungeon.effects.MagicMissile;
import com.gfpixel.gfpixeldungeon.tiles.DungeonTilemap;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.particles.Emitter;

public class ElpheltSprite extends MobSprite {

    private int zapPos;

    private Animation charging;
    private Emitter chargeParticles;

    public ElpheltSprite() {
        super();

        texture( Assets.ELPHELT );

        TextureFilm frames = new TextureFilm( texture, 18, 22 );

        idle = new Animation( 5, true );
        idle.frames( frames, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 1, 2, 1, 2, 3, 4, 5, 6, 7, 7, 7 );

        charging = new Animation( 14, true);
        charging.frames( frames, 1 );

        chargeParticles = centerEmitter();
        chargeParticles.autoKill = false;
        chargeParticles.pour(MagicMissile.MagicParticle.ATTRACTING, 0.05f);
        chargeParticles.on = false;

        run = new Animation( 15, true );
        run.frames( frames, 8, 9, 10, 11, 12 );

        attack = new Animation( 15, false );
        attack.frames( frames, 16, 17, 18 );
        zap = new Animation( 15, false );
        zap.frames( frames, 13, 14, 15);

        die = new Animation( 15, false );
        die.frames( frames, 19, 20, 21, 22, 23, 24 );

        play( idle );
    }


    @Override
    public void link(Char ch) {
        super.link(ch);
        if (((Hydra)ch).beamCharged) play(charging);
    }

    @Override
    public void update() {
        super.update();
        chargeParticles.pos(center());
        chargeParticles.visible = visible;
    }

    public void charge( int pos ){
        turnTo(ch.pos, pos);
        play(charging);
    }

    @Override
    public void play(Animation anim) {
        chargeParticles.on = anim == charging;
        super.play(anim);
    }

    @Override
    public void zap( int pos ) {
        zapPos = pos;
        super.zap( pos );
    }

    @Override
    public void onComplete( Animation anim ) {
        super.onComplete( anim );

        if (anim == zap) {
            idle();
            if (Actor.findChar(zapPos) != null){
                parent.add(new Beam.DeathRay(center(), Actor.findChar(zapPos).sprite.center()));
            } else {
                parent.add(new Beam.DeathRay(center(), DungeonTilemap.raisedTileCenterToWorld(zapPos)));
            }
            ((Hydra)ch).deathGaze();
            ch.next();
        } else if (anim == die){
            chargeParticles.killAndErase();
        }
    }
}

