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

package com.gfpixel.gfpixeldungeon.items.weapon.melee.AR;

import com.gfpixel.gfpixeldungeon.sprites.ItemSpriteSheet;

public class Lvoat2 extends AssaultRifle {

    {
        image = ItemSpriteSheet.LVOAT2;

        ACC = 1.2f;
        tier = 1;
    }

    //Essentially it's a tier 4 weapon, with tier 3 base max damage, and tier 5 scaling.
    //equal to tier 4 in damage at +5

    @Override
    public int max(int lvl) {
        return  Math.round(4*(tier+1)) +  //base, down from 25
                Math.round(lvl*(tier+4));	//+6 per level, up from +5
    }
}