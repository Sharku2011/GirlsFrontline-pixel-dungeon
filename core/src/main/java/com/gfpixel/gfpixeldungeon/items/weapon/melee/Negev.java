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
import com.gfpixel.gfpixeldungeon.actors.hero.Hero;
import com.gfpixel.gfpixeldungeon.actors.mobs.Mob;
import com.gfpixel.gfpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class Negev extends MeleeWeapon {

	{
		image = ItemSpriteSheet.NEGEV;

		tier = 3;
		ACC = 0.45f;
		DLY = 0.2f; //1time 5hit
	}

	@Override
	public int max(int lvl) {
		return  Math.round(2.1f*(tier+1)) +    //5 base, down from 20
				lvl*Math.round(0.5f*(tier+1));   //+1 per level, down from +2
	}

	@Override
	public int damageRoll(Char owner) {
		if (owner instanceof Hero) {
			Hero hero = (Hero)owner;
			Char enemy = hero.enemy();
			if (enemy instanceof Mob && ((Mob) enemy).surprisedBy(hero)) {
				//deals 85% toward max to max on surprise, instead of min to max.
				int damage = augment.damageFactor(Random.NormalIntRange(
						Math.max(1, Math.round(min()*DLY)),
						Math.round(max()*DLY)));
				int exStr = hero.STR() - STRReq();
				if (exStr > 0) {
					damage -= Random.IntRange(0, exStr);
				}
				return damage;
			}
		}
		return super.damageRoll(owner);
	}

}