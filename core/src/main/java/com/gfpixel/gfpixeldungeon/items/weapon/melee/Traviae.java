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

package com.gfpixel.gfpixeldungeon.items.weapon.melee;

import com.gfpixel.gfpixeldungeon.actors.Char;
import com.gfpixel.gfpixeldungeon.sprites.ItemSpriteSheet;

public class Traviae extends MeleeWeapon {

    {
        image = ItemSpriteSheet.TRAVIAE;

        tier=2;
        DLY = 1.5f;
        RCH = 2;
        ACC = 1.27f; //27% boost to accuracy
    }

    @Override
    public int max(int lvl) {
        return  Math.round(7*(tier+1)) +        //35 base, up from 25
                lvl*Math.round(3.1f*(tier+1));  //+8 per level, up from +5
    }

    @Override
    public int defenseFactor( Char owner ) {
        return 5+2*level();     //6 extra defence, plus 2 per level;
    }
}