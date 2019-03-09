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
import com.watabou.noosa.particles.PixelParticle;

public class ShopkeeperSprite extends MobSprite {
	
	private PixelParticle coin;
	
	public ShopkeeperSprite() {
		super();
		
		texture( Assets.KEEPER );
		TextureFilm film = new TextureFilm( texture, 27, 20 );
		
		idle = new Animation( 10, true );
		idle.frames( film, 1, 1, 1, 1, 1, 0, 0, 0, 0 );

		die = new Animation( 20, false );
		die.frames( film, 0 );

		run = idle.clone();

		attack = idle.clone();
		
		idle();
	}
	
	@Override
	public void onComplete( Animation anim ) {
		super.onComplete( anim );
		
		if (visible && anim == idle) {
			if (coin == null) {
				coin = new PixelParticle();
				parent.add( coin );
			}
			//TODO 추후 타일맵 크기에 맞추어 일정 비율에 해당하는 위치로 세팅하도록 리팩토링 할 것
			coin.reset( x + (flipHorizontal ? 7 : 19), y + 11, 0xFFFF00, 1, 0.5f );
			coin.speed.y = -40;
			coin.acc.y = +160;
		}
	}
}
