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

package com.gfpixel.gfpixeldungeon.items.bags;

import com.gfpixel.gfpixeldungeon.items.Ankh;
import com.gfpixel.gfpixeldungeon.items.Item;
import com.gfpixel.gfpixeldungeon.items.Stylus;
import com.gfpixel.gfpixeldungeon.items.stones.Runestone;
import com.gfpixel.gfpixeldungeon.plants.Plant;
import com.gfpixel.gfpixeldungeon.sprites.ItemSpriteSheet;

public class VelvetPouch extends Bag {

	{
		image = ItemSpriteSheet.POUCH;
		
		size = 20;
	}
	
	@Override
	public boolean grab( Item item ) {
		// 씨앗
		// 증강석(고급 장비 교정권), 인챈트석(특성 장비 교정권)
		// 수복 계약
		return item instanceof Plant.Seed || item instanceof Runestone || item instanceof Stylus || item instanceof Ankh;
	}
	
	@Override
	public int price() {
		return 30;
	}

}
