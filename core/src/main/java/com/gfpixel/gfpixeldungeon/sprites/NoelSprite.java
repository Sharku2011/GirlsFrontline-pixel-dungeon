package com.gfpixel.gfpixeldungeon.sprites;

import com.gfpixel.gfpixeldungeon.Assets;
import com.gfpixel.gfpixeldungeon.actors.Char;
import com.gfpixel.gfpixeldungeon.effects.Halo;
import com.gfpixel.gfpixeldungeon.effects.particles.ElmoParticle;
import com.watabou.glwrap.Blending;
import com.watabou.noosa.Game;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PointF;

public class NoelSprite extends MobSprite {

    private Shield shield;

    public NoelSprite() {
        super();

        texture( Assets.NOEL );

        TextureFilm frames = new TextureFilm( texture, 13, 14 );

        idle = new Animation( 4, true );
        idle.frames( frames, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 2, 2, 3, 3 );

        run = new Animation( 20, true );
        run.frames( frames, 0 );

        die = new Animation( 20, false );
        die.frames( frames, 0 );

        play( idle );
    }

    @Override
    public void link( Char ch ) {
        super.link( ch );

        if (shield == null) {
            parent.add( shield = new Shield() );
        }
    }

    @Override
    public void die() {
        super.die();

        if (shield != null) {
            shield.putOut();
        }
        emitter().start( ElmoParticle.FACTORY, 0.03f, 60 );

        if (visible) {
            Sample.INSTANCE.play( Assets.SND_BURNING );
        }
    }

    public class Shield extends Halo {

        private float phase;

        public Shield() {

            super( 9, 0xBBAACC, 1f );

            am = -0.33f;
            aa = +0.33f;

            phase = 1;
        }

        @Override
        public void update() {
            super.update();

            if (phase < 1) {
                if ((phase -= Game.elapsed) <= 0) {
                    killAndErase();
                } else {
                    scale.set( (2 - phase) * radius / RADIUS );
                    am = phase * (-1);
                    aa = phase * (+1);
                }
            }

            if (visible = NoelSprite.this.visible) {
                PointF p = NoelSprite.this.center();
                point(p.x, p.y );
            }
        }

        @Override
        public void draw() {
            Blending.setLightMode();
            super.draw();
            Blending.setNormalMode();
        }

        public void putOut() {
            phase = 0.999f;
        }
    }

}
