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
import com.gfpixel.gfpixeldungeon.Dungeon;
import com.gfpixel.gfpixeldungeon.actors.mobs.Golyat;
import com.gfpixel.gfpixeldungeon.effects.Speck;
import com.watabou.noosa.TextureFilm;

public class GolyatpSprite extends MobSprite {

    public GolyatpSprite() {
        super();

        texture( Assets.GOLYATP );

        TextureFilm frames = new TextureFilm( texture, 26, 23 );

        idle = new Animation( 12, true );
        idle.frames( frames, 0, 0, 1, 1, 2, 2, 1, 1 );

        run = new Animation( 10, true );
        run.frames( frames, 0, 1, 2, 1, 0 );

        attack = new Animation( 15, false );
        attack.frames( frames, 3, 3, 4 );

        die = new Animation( 18, false );
        die.frames( frames, 5, 6, 7, 8, 9 );

        play( idle );
    }

    @Override
    public void die() {
        super.die();
        if (Dungeon.level.heroFOV[ch.pos]) {
            emitter().burst( Speck.factory( Speck.BONE ), 6 );
        }
    }

    @Override
    public int blood() {
        return 0xFFcccccc;
    }
}
