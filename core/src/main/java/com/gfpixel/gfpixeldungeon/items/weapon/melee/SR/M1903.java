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

package com.gfpixel.gfpixeldungeon.items.weapon.melee.SR;

import com.gfpixel.gfpixeldungeon.actors.Char;
import com.gfpixel.gfpixeldungeon.actors.buffs.Buff;
import com.gfpixel.gfpixeldungeon.actors.buffs.Charm;
import com.gfpixel.gfpixeldungeon.actors.buffs.Speed;
import com.gfpixel.gfpixeldungeon.actors.hero.Hero;
import com.gfpixel.gfpixeldungeon.sprites.ItemSpriteSheet;

public class M1903 extends SniperRifle {

	{
		image = ItemSpriteSheet.M1903;

		tier = 2;
		ACC = 1.75f;
		DLY = 3f;
		RCH = 60;
	}


	@Override
	public int max(int lvl) {
		return  Math.round(15.4f*(tier+1)) +    //40 base, up from 35
				lvl*Math.round(2.1f*(tier+2)); //+4 per level, up from +3
	}
	@Override
	public void onAttack(Char owner, Char enemy ) {
		if (owner instanceof Hero && owner.buffs(Speed.class).isEmpty()) {
			Buff.prolong(owner, Charm.class, 6.5f);
		}
	}

}
