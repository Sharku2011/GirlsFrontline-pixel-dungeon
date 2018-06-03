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
import com.gfpixel.gfpixeldungeon.effects.particles.ShadowParticle;
import com.watabou.noosa.TextureFilm;

public class SuccubusSprite extends MobSprite {
	
	public SuccubusSprite() {
		super();
		
		texture( Assets.SUCCUBUS );
		
		TextureFilm frames = new TextureFilm( texture, 24, 15 );

		idle = new Animation( 15, true );
		idle.frames( frames, 0, 0, 0, 0, 0, 1 );

		run = new Animation( 22, true );
		run.frames( frames, 2, 3, 4, 5 );

		attack = new Animation( 15, false );
		attack.frames( frames, 6, 7 );

		die = new Animation( 10, false );
		die.frames( frames, 8, 9, 10 );
		
		play( idle );
	}
	
	@Override
	public void die() {
		super.die();
		emitter().burst( Speck.factory( Speck.HEART ), 6 );
		emitter().burst( ShadowParticle.UP, 8 );
	}
}
