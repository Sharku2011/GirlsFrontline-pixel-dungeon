package com.gfpixel.gfpixeldungeon.sprites;

import com.gfpixel.gfpixeldungeon.Assets;
import com.gfpixel.gfpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class RipperSprite extends MobSprite {

    public RipperSprite() {
        super();

        texture(Assets.RIPPER );

        TextureFilm frames = new TextureFilm( texture, 13, 16 );

        idle = new Animation( 8, true );
        idle.frames( frames, 0, 1 );

        run = new Animation( 12, true );
        run.frames( frames, 2, 3, 4, 5 );

        attack = new Animation( 12, false );
        attack.frames( frames, 6, 7 );

        die = new Animation( 12, false );
        die.frames( frames, 8, 9, 10 );

        play( idle );
    }
}