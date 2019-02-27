package com.gfpixel.gfpixeldungeon.sprites;

import com.gfpixel.gfpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class RipperSprite extends MobSprite {

    public RipperSprite() {
        super();

        texture(Assets.RIPPER );

        TextureFilm frames = new TextureFilm( texture, 19, 22 );

        idle = new Animation( 5, true );
        idle.frames( frames, 0, 0, 0, 0, 0, 0, 0);

        run = new Animation( 12, true );
        run.frames( frames, 4, 5, 6, 7, 8, 9 );

        attack = new Animation( 12, false );
        attack.frames( frames, 1, 2, 3, 1, 2, 3 );

        die = new Animation( 12, false );
        die.frames( frames, 10, 11, 12 );

        play( idle );
    }
}