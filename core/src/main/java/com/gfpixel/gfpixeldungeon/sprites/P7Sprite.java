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
import com.gfpixel.gfpixeldungeon.actors.mobs.npcs.Imp;
import com.gfpixel.gfpixeldungeon.effects.Speck;
import com.watabou.noosa.TextureFilm;

public class P7Sprite extends MobSprite {
	
	public P7Sprite() {
		super();
		
		texture( Assets.P7 );
		
		TextureFilm frames = new TextureFilm( texture, 17, 22 );
		
		idle = new Animation( 5, true );
		idle.frames( frames,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 4, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 8, 5, 5, 5, 5, 5, 6, 6, 7, 7, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 8, 5, 5, 5, 5, 5, 5, 5, 5, 5, 4, 4, 3, 3, 0, 0);
		
		run = new Animation( 20, true );
		run.frames( frames, 0 );
		
		die = new Animation( 5, false );
		die.frames( frames, 3, 4, 5, 8);
		
		play( idle );
	}
	
	@Override
	public void link( Char ch ) {
		super.link( ch );
		
		if (ch instanceof Imp) {
			alpha( 0.4f );
		}
	}
	
	@Override
	public void onComplete( Animation anim ) {
		if (anim == die) {
			
			emitter().burst( Speck.factory( Speck.WOOL ), 15 );
			killAndErase();
			
		} else {
			super.onComplete( anim );
		}
	}
}
