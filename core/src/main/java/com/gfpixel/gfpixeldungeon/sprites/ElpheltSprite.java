package com.gfpixel.gfpixeldungeon.sprites;

import com.gfpixel.gfpixeldungeon.Assets;
import com.gfpixel.gfpixeldungeon.actors.Actor;
import com.gfpixel.gfpixeldungeon.actors.Char;
import com.gfpixel.gfpixeldungeon.actors.mobs.Elphelt;
import com.gfpixel.gfpixeldungeon.effects.Beam;
import com.gfpixel.gfpixeldungeon.effects.MagicMissile;
import com.gfpixel.gfpixeldungeon.tiles.DungeonTilemap;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

public class ElpheltSprite extends MobSprite {

    private int zapPos;

    private Animation charging;
    private Emitter chargeParticles;

    public ElpheltSprite() {
        super();

        texture( Assets.ELPHELT );

        TextureFilm frames = new TextureFilm( texture, 18, 19 );

        idle = new Animation( 5, true );
        idle.frames( frames, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 1, 2, 1, 2, 3, 4, 5, 6, 7, 7, 7 );

        charging = new Animation( 14, true);
        charging.frames( frames, 16 );

        chargeParticles = centerEmitter();
        chargeParticles.autoKill = false;
        chargeParticles.pour(MagicMissile.MagicParticle.ATTRACTING, 0.05f);
        chargeParticles.on = false;

        run = new Animation( 15, true );
        run.frames( frames, 8, 9, 10, 11, 12 );

        attack = new Animation( 15, false );
        attack.frames( frames, 13, 14, 15 );
        zap = new Animation( 15, false );
        zap.frames( frames, 17, 17, 19);

        die = new Animation( 15, false );
        die.frames( frames, 19, 20, 21, 22, 23, 24 );

        play( idle );
    }


    @Override
    public void link(Char ch) {
        super.link(ch);
        if (((Elphelt)ch).beamCharged) play(charging);
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
            ((Elphelt)ch).deathGaze();
            ch.next();
        } else if (anim == die){
            chargeParticles.killAndErase();
        }
    }

    public static class GenoiseParticle extends PixelParticle.Shrinking {

        public static final Emitter.Factory FACTORY = new Emitter.Factory() {
            @Override
            public void emit( Emitter emitter, int index, float x, float y ) {
                ((ElpheltSprite.GenoiseParticle)emitter.recycle( ElpheltSprite.GenoiseParticle.class )).reset( x, y );
            }
        };

        public GenoiseParticle() {
            super();

            color( 0xFF66FF );
            lifespan = 0.3f;

            acc.set( 0, +50 );
        }

        public void reset( float x, float y ) {
            revive();

            this.x = x;
            this.y = y;

            left = lifespan;

            size = 4;
            speed.polar( -Random.Float( PointF.PI ), Random.Float( 32, 48 ) );
        }

        @Override
        public void update() {
            super.update();
            float p = left / lifespan;
            am = p > 0.5f ? (1 - p) * 2f : 1;
        }
    }
}
