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

import com.gfpixel.gfpixeldungeon.Dungeon;
import com.gfpixel.gfpixeldungeon.actors.Char;
import com.gfpixel.gfpixeldungeon.actors.buffs.Buff;
import com.gfpixel.gfpixeldungeon.actors.buffs.Speed;
import com.gfpixel.gfpixeldungeon.actors.hero.Hero;
import com.gfpixel.gfpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.GameMath;

public class Kriss extends MeleeWeapon {

	{
		image = ItemSpriteSheet.KRISS;

		tier = 3;
		DLY = 0.5f; //2x speed
	}

	@Override
	public int damageRoll(Char owner) {
		if (owner instanceof Hero) {
			Buff.prolong(owner, Speed.class, 1.75f);
		}


		return super.damageRoll(owner);
	}

	@Override
	public int max(int lvl) {
		return  Math.round(2.5f*(tier+1)) +     //10 base, down from 20
				lvl*Math.round(0.5f*(tier+1));  //+2 per level, down from +4
	}
	@Override
	public int defenseFactor( Char owner ) {
		return 3;	//3 extra defence
	}
}
