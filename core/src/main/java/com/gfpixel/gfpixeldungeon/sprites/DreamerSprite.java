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
import com.gfpixel.gfpixeldungeon.effects.MagicMissile;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

public class DreamerSprite extends MobSprite {
	
	public DreamerSprite() {
		super();
		
		texture( Assets.DREAMER );
		
		TextureFilm frames = new TextureFilm( texture, 24, 24 );
		
		idle = new Animation( 2, true );
		idle.frames( frames, 0, 1, 2, 1, 0 );
		
		run = new Animation( 3, true );
		run.frames( frames, 0, 1, 2, 1, 0 );
		
		attack = new Animation( 8, false );
		attack.frames( frames, 0, 3, 4, 0 );
		
		die = new Animation( 8, false );
		die.frames( frames, 0, 3, 5, 6, 7 );
		
		play( idle );
	}
	
	private int posToShoot;
	
	@Override
	public void attack( int cell ) {
		posToShoot = cell;
		super.attack( cell );
	}
	
	@Override
	public void onComplete( Animation anim ) {
		if (anim == attack) {

			Sample.INSTANCE.play( Assets.SND_ZAP );
			MagicMissile.boltFromChar( parent,
					MagicMissile.SHADOW,
					this,
					posToShoot,
					new Callback() {
						@Override
						public void call() {
							ch.onAttackComplete();
						}
					} );

			idle();
			
		} else {
			super.onComplete( anim );
		}
	}
}
