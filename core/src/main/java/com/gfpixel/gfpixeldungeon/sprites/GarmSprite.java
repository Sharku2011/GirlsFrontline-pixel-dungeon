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
import com.watabou.noosa.Camera;
import com.watabou.noosa.TextureFilm;

public class GarmSprite extends MobSprite {
	
	private static final float FALL_SPEED	= 64;
	
	public GarmSprite() {
		super();
		
		texture( Assets.GARM );
		
		TextureFilm frames = new TextureFilm( texture, 36, 27 );
		
		idle = new Animation( 2, true );
		idle.frames( frames, 0);
		
		run = new Animation( 12, true );
		run.frames( frames, 1, 2, 3, 4 );
		
		attack = new Animation( 5, false );
		attack.frames( frames, 2, 2, 4, 4 );
		
		die = new Animation( 8, false );
		die.frames( frames, 5, 6, 7 );
		
		play( idle );
	}
	
	@Override
	public void attack( int cell ) {
		super.attack( cell );
		
		speed.set( 0, -FALL_SPEED );
		acc.set( 0, FALL_SPEED * 4 );
	}
	
	@Override
	public void onComplete( Animation anim ) {
		super.onComplete( anim );
		if (anim == attack) {
			speed.set( 0 );
			acc.set( 0 );
			place( ch.pos );
			
			Camera.main.shake( 1, 0.2f );
		}
	}
}
