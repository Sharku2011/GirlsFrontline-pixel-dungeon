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
import com.gfpixel.gfpixeldungeon.actors.mobs.DM300;
import com.watabou.noosa.TextureFilm;

public class DM300Sprite extends MobSprite {
	
	public DM300Sprite() {
		super();

		texture( Assets.GAGER );

		TextureFilm frames = new TextureFilm( texture, 26, 22 );

		idle = new Animation( 2, true );
		idle.frames( frames, 0, 0, 0, 0 );

		run = new Animation( 10, true );
		run.frames( frames, 1, 2, 3, 4, 5, 6 );

		attack = new Animation( 24, false );
		attack.frames( frames, 7, 8, 7, 8, 7, 8 );

		die = new Animation( 10, false );
		die.frames( frames, 9, 10, 11 );

		play( idle );
	}

	@Override
	public void attack( int cell ) {
		turnTo( ch.pos, cell );
		((DM300)ch).magnum();
		play( attack );
	}
}