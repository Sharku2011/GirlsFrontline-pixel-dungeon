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
import com.gfpixel.gfpixeldungeon.effects.Speck;
import com.watabou.noosa.TextureFilm;

public class DM300Sprite extends MobSprite {
	
	public DM300Sprite() {
		super();
		
		texture( Assets.GEGAR );

		TextureFilm frames = new TextureFilm( texture, 24, 18 );

		idle = new Animation( 2, true );
		idle.frames( frames, 0, 0, 0, 0, 0, 0, 0, 0, 10 );

		run = new Animation( 15, true );
		run.frames( frames, 1, 2, 3, 4, 5 );

		attack = new Animation( 20, false );
		attack.frames( frames, 7, 6, 7, 6 );
		//돌진 전 대기동작은 8, 돌진 후 동작은 9

		die = new Animation( 2, false );
		die.frames( frames, 10, 11, 12 );

		/*
		TextureFilm frames = new TextureFilm( texture, 22, 20 );

		idle = new Animation( 10, true );
		idle.frames( frames, 0, 1 );

		run = new Animation( 10, true );
		run.frames( frames, 2, 3 );

		attack = new Animation( 15, false );
		attack.frames( frames, 4, 5, 6 );

		die = new Animation( 20, false );
		die.frames( frames, 0, 7, 0, 7, 0, 7, 0, 7, 0, 7, 0, 7, 8 );
		*/

		play( idle );
	}

	@Override
	public void attack(int pos) {
		super.attack(pos);
		((DM300)ch).magnum();
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
