package com.gfpixel.gfpixeldungeon.sprites;

import com.gfpixel.gfpixeldungeon.Assets;
import com.gfpixel.gfpixeldungeon.actors.Char;
import com.gfpixel.gfpixeldungeon.actors.mobs.Elphelt;
import com.gfpixel.gfpixeldungeon.effects.MagicMissile;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.utils.Callback;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

public class ElpheltSprite extends MobSprite {

    private int zapPos;

    private Animation charging;
    private Animation genoise;
    private Animation blast;

    private Emitter chargeParticles;

    private Animation magnum;


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

        genoise = new Animation( 8, false );
        genoise.frames( frames, 16, 17, 18, 19, 18, 17, 16);

        zap = new Animation( 15, false );
        zap.frames( frames, 13, 14, 15);

        die = new Animation( 15, false );
        die.frames( frames, 19, 20, 21, 22, 23, 24 );

        play( idle );
    }


    @Override
    public void link(Char ch) {
        super.link(ch);
        if (((Elphelt)ch).onGenoise || ((Elphelt)ch).onRush) play(charging);
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

    public void genoise( int pos ) {
        turnTo( ch.pos, pos );
        play( genoise );

        // show cursed wand effect - CursedWand.fx()
        final int tpos = pos;
        MagicMissile.boltFromChar(
            parent,
            MagicMissile.RAINBOW,
            this,
            pos,
            new Callback() {
                @Override
                public void call() {
                    ((Elphelt)ch).fireGenoise( tpos );
                }
            }
        );
        Sample.INSTANCE.play(Assets.SND_ZAP);
    }

    @Override
    public void play(Animation anim) {
        chargeParticles.on = anim == charging;
        super.play(anim);
    }

    @Override
    public void zap( int pos ) {
        zapPos = pos;
        if (((Elphelt)ch).phase == 2) {
            ((Elphelt)ch).magnumWedding();
        }
        super.zap( zapPos );
    }

    @Override
    public void onComplete( Animation anim ) {
        super.onComplete( anim );

        if (anim == zap) {
            idle();
        } else if (anim == die){
            chargeParticles.killAndErase();
        } else if (anim == genoise && ((Elphelt)ch).getTraceGenoise() != null) {
            charge( ((Elphelt)ch).genoiseDst );
        }
        if (ch != null) {
            ch.next();
        }
        super.onComplete( anim );
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
