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

public class AlbinoSprite extends MobSprite {

	public AlbinoSprite() {
		super();
		
		texture( Assets.SPRING );
		
		TextureFilm frames = new TextureFilm( texture, 20, 20 );
		
		idle = new Animation( 2, true );
		idle.frames( frames, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 2, 1 );
		
		run = new Animation( 10, true );
		run.frames( frames, 7, 8, 9, 10, 11, 12 );
		
		attack = new Animation( 15, false );
		attack.frames( frames, 3, 4, 5, 6 );
		
		die = new Animation( 10, false );
		die.frames( frames, 0, 13, 14, 15 );
		
		play( idle );
	}
}
