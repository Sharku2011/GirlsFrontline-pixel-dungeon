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
import com.watabou.noosa.TextureFilm;

public class BruteSprite extends MobSprite {

	public BruteSprite() {
		super();

		texture( Assets.VESPID );

		TextureFilm frames = new TextureFilm( texture, 20, 21 );

		idle = new Animation( 5, true );
		idle.frames( frames, 0 );

		run = new Animation( 15, true );
		run.frames( frames,  4, 5, 6, 7, 8, 9 );

		attack = new Animation( 18, false );
		attack.frames( frames, 1, 2, 3, 2 );

		die = new Animation( 12, false );
		die.frames( frames, 10, 11, 12 );

		play( idle );
	}
}
