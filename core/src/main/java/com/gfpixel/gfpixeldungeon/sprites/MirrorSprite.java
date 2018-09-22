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

import com.gfpixel.gfpixeldungeon.Dungeon;
import com.gfpixel.gfpixeldungeon.actors.Char;
import com.gfpixel.gfpixeldungeon.actors.mobs.npcs.MirrorImage;
import com.watabou.noosa.TextureFilm;

public class MirrorSprite extends MobSprite {
	
	private static final int FRAME_WIDTH	= 15;
	private static final int FRAME_HEIGHT	= 17;
	
	public MirrorSprite() {
		super();
		
		texture( Dungeon.hero.heroClass.spritesheet() );
		updateArmor( 0 );
		idle();
	}
	
	@Override
	public void link( Char ch ) {
		super.link( ch );
		updateArmor( ((MirrorImage)ch).tier );
	}
	
	public void updateArmor( int tier ) {
		TextureFilm film = new TextureFilm( HeroSprite.tiers(), tier, FRAME_WIDTH, FRAME_HEIGHT );

		idle = new Animation( 3, true );
		idle.frames( film, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 2, 3, 4, 4, 4, 5, 5, 5, 5, 4, 4, 4, 5, 5, 5, 5, 4, 4, 4, 5, 5, 5, 5, 3, 3, 2, 2, 0, 0, 0, 0 );
		
		run = new Animation( 20, true );
		run.frames( film, 6, 7, 8, 9, 10, 11 );
		
		die = new Animation( 20, false );
		die.frames( film, 12, 13, 14, 15 );
		
		attack = new Animation( 30, false );
		attack.frames( film, 17, 18, 0, 19, 17, 18, 19  );
		
		idle();
	}
}
