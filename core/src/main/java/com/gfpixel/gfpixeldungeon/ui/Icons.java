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

package com.gfpixel.gfpixeldungeon.ui;

import com.gfpixel.gfpixeldungeon.Assets;
import com.gfpixel.gfpixeldungeon.actors.hero.HeroClass;
import com.watabou.noosa.Image;

public enum Icons {

	SKULL,
	BUSY,
	COMPASS,
	INFO,
	PREFS,
	WARNING,
	TARGET,
	MASTERY,
	WATA,
	SHPX,
	WARRIOR,
	MAGE,
	ROGUE,
	RANGER,
	CLOSE,
	DEPTH,
	SLEEP,
	ALERT,
	LOST,
	BACKPACK,
	SEED_POUCH,
	SCROLL_HOLDER,
	POTION_BANDOLIER,
	WAND_HOLSTER,
	CHECKED,
	UNCHECKED,
	EXIT,
	NOTES,
	LANGS,
	CHALLENGE_OFF,
	CHALLENGE_ON,
	RESUME;

	public Image get() {
		return get( this );
	}
	
	public static Image get( Icons type ) {
		Image icon = new Image( Assets.ICONS );
		switch (type) {
		case SKULL:
			icon.frame( icon.texture.uvRect( 0, 0, 16, 16 ) );
			break;
		case BUSY:
			icon.frame( icon.texture.uvRect( 16, 0, 32, 16 ) );
			break;
		case COMPASS:
			icon.frame( icon.texture.uvRect( 0, 16, 14, 26 ) );
			break;
		case INFO:
			icon.frame( icon.texture.uvRect( 32, 0, 60, 28 ) );
			break;
		case PREFS:
			icon.frame( icon.texture.uvRect( 60, 0, 92, 32 ) );
			break;
		case WARNING:
			icon.frame( icon.texture.uvRect( 92, 0, 116, 24 ) );
			break;
		case TARGET:
			icon.frame( icon.texture.uvRect( 0, 26, 32, 58 ) );
			break;
		case MASTERY:
			icon.frame( icon.texture.uvRect( 32, 28, 60, 56 ) );
			break;
		case WATA:
			icon.frame( icon.texture.uvRect( 60, 32, 90, 52 ) );
			break;
		case SHPX:
			icon.frame( icon.texture.uvRect( 112, 98, 140, 126 ) );
			break;
		case WARRIOR:
			icon.frame( icon.texture.uvRect( 0, 58, 32, 90 ) );
			break;
		case MAGE:
			icon.frame( icon.texture.uvRect( 32, 58, 64, 90 ) );
			break;
		case ROGUE:
			icon.frame( icon.texture.uvRect( 64, 58, 96, 90 ) );
			break;
		case RANGER:
			icon.frame( icon.texture.uvRect( 96, 58, 128, 90 ) );
			break;
		case CLOSE:
			icon.frame( icon.texture.uvRect( 0, 90, 26, 116 ) );
			break;
		case DEPTH:
			icon.frame( icon.texture.uvRect( 76, 92, 108, 124 ) );
			break;
		case SLEEP:
			icon.frame( icon.texture.uvRect( 26, 90, 44, 106 ) );
			break;
		case ALERT:
			icon.frame( icon.texture.uvRect( 44, 90, 60, 106 ) );
			break;
		case LOST:
			icon.frame( icon.texture.uvRect( 60, 90, 76, 106 ) );
			break;
		case BACKPACK:
			icon.frame( icon.texture.uvRect( 116, 0, 136, 20 ) );
			break;
		case SCROLL_HOLDER:
			icon.frame( icon.texture.uvRect( 136, 0, 156, 20 ) );
			break;
		case SEED_POUCH:
			icon.frame( icon.texture.uvRect( 156, 0, 176, 20 ) );
			break;
		case WAND_HOLSTER:
			icon.frame( icon.texture.uvRect( 176, 0, 196, 20 ) );
			break;
		case POTION_BANDOLIER:
			icon.frame( icon.texture.uvRect( 196, 0, 216, 20 ) );
			break;
		case CHECKED:
			icon.frame( icon.texture.uvRect( 108, 24, 132, 48 ) );
			break;
		case UNCHECKED:
			icon.frame( icon.texture.uvRect( 132, 24, 156, 48 ) );
			break;
		case EXIT:
			icon.frame( icon.texture.uvRect( 216, 0, 248, 32 ) );
			break;
		case NOTES:
			icon.frame( icon.texture.uvRect( 144, 104, 164, 126 ) );
			break;
		case LANGS:
			icon.frame( icon.texture.uvRect( 166, 108, 190, 126 ) );
			break;
		case CHALLENGE_OFF:
			icon.frame( icon.texture.uvRect( 156, 24, 184, 48 ) );
			break;
		case CHALLENGE_ON:
			icon.frame( icon.texture.uvRect( 184, 24, 216, 48 ) );
			break;
		case RESUME:
			icon.frame( icon.texture.uvRect( 26, 106, 48, 128 ) );
			break;
		}
		return icon;
	}
	
	public static Image get( HeroClass cl ) {
		switch (cl) {
		case WARRIOR:
			return get( WARRIOR );
		case MAGE:
			return get( MAGE );
		case ROGUE:
			return get( ROGUE );
		case RANGER:
			return get( RANGER );
		default:
			return null;
		}
	}
}
