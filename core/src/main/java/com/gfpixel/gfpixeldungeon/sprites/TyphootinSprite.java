/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2017 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.gfpixel.gfpixeldungeon.sprites;

import com.gfpixel.gfpixeldungeon.Assets;
import com.gfpixel.gfpixeldungeon.effects.Speck;
import com.watabou.noosa.TextureFilm;

public class TyphootinSprite extends MobSprite {

    public TyphootinSprite() {
        super();

        texture( Assets.TYPHOOTIN );

        TextureFilm frames = new TextureFilm( texture, 55, 30 );

        idle = new Animation( 8, true );
        idle.frames( frames, 0, 1, 2, 3 );

        run = new Animation( 8, true );
        run.frames( frames, 0, 1, 2, 3 );

        attack = new Animation( 10, false );
        attack.frames( frames, 5, 6, 7, 8, 9 );

        die = new Animation( 15, false );
        die.frames( frames, 0, 10, 10, 11, 11, 12, 12 );

        play( idle );
    }

    @Override
    public void onComplete( Animation anim ) {

        super.onComplete( anim );

        if (anim == die) {
            emitter().burst( Speck.factory( Speck.WOOL ), 15 );
        }
    }

    @Override
    public int blood() {
        return 0xFFFFFF88;
    }
}

