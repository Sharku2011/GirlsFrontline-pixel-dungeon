package com.gfpixel.gfpixeldungeon.sprites;

import com.gfpixel.gfpixeldungeon.Assets;
import com.gfpixel.gfpixeldungeon.actors.mobs.Jaguar;
import com.watabou.noosa.TextureFilm;

public class JaguarSprite extends MobSprite {

    public JaguarSprite() {
        super();

        texture(Assets.JAGUAR );

        TextureFilm frames = new TextureFilm( texture, 15, 15 );

        idle = new Animation( 8, true );
        idle.frames( frames, 0, 1 );

        run = new Animation( 8, true );
        run.frames( frames, 0, 1, 0, 1 );

        attack = new Animation( 10, false );
        attack.frames( frames, 1, 2, 3 );

        die = new Animation( 8, false );
        die.frames( frames, 4, 5, 6 );

        play( idle );
    }

    @Override
    public void attack(int cell) {
        super.attack(cell);

        ((Jaguar)ch).fireBomb(cell);
    }
}