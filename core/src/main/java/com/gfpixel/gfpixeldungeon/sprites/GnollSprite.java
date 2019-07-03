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
import com.gfpixel.gfpixeldungeon.effects.Speck;
import com.watabou.noosa.TextureFilm;

public class GnollSprite extends MobSprite {
	
	public GnollSprite() {
		super();
		
		texture( Assets.PROWLER );

		TextureFilm frames = new TextureFilm( texture, 36, 32 );

		idle = new Animation( 8, true );
		idle.frames( frames, 0, 1 );

		run = new Animation( 8, true );
		run.frames( frames, 0, 1, 0, 1 );

		attack = new Animation( 15, false );
		attack.frames( frames, 1, 2, 3, 2, 3 );

		die = new Animation( 8, false );
		die.frames( frames, 4, 5, 6 );

		play( idle );
	}
	@Override
	public void onComplete( Animation anim ) {

		super.onComplete( anim );

		if (anim == die) {
			emitter().burst( Speck.factory( Speck.WOOL ), 15 );
		}
	}
}