/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2018 Evan Debenham
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
import com.gfpixel.gfpixeldungeon.actors.Char;
import com.watabou.noosa.MovieClip;
import com.watabou.noosa.TextureFilm;

public class PyroSprite extends MobSprite{

    public PyroSprite() {
        super();

        texture( Assets.PYRO );

        TextureFilm frames = new TextureFilm( texture, 20, 19 );

        idle = new MovieClip.Animation( 1, true );
        idle.frames( frames, 0, 0, 0, 0, 0, 1 );

        run = new MovieClip.Animation( 12, true );
        run.frames( frames, 5, 6, 7, 8, 9, 10 );

        attack = new MovieClip.Animation( 15, false );
        attack.frames( frames, 1, 2, 3, 4, 3, 4 );

        die = new MovieClip.Animation( 15, false );
        die.frames( frames, 11, 12, 13 );

        play( idle );
    }

    @Override
    public void die() {
        super.die();
        remove( CharSprite.State.BURNING );
    }

}
