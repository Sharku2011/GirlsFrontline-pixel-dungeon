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

package com.gfpixel.gfpixeldungeon.items.weapon.missiles;

import com.gfpixel.gfpixeldungeon.actors.Actor;
import com.gfpixel.gfpixeldungeon.actors.Char;
import com.gfpixel.gfpixeldungeon.actors.hero.Hero;
import com.gfpixel.gfpixeldungeon.actors.hero.HeroClass;
import com.gfpixel.gfpixeldungeon.actors.mobs.Mob;
import com.gfpixel.gfpixeldungeon.items.rings.RingOfSharpshooting;
import com.gfpixel.gfpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class ThrowingKnife extends MissileWeapon {
	
	{
		image = ItemSpriteSheet.THROWING_KNIFE;
		
		bones = false;
		
	}
	
	@Override
	public int min(int lvl) {
		return 3;
	}
	
	@Override
	public int max(int lvl) {
		return 7;
	}
	
	@Override
	public int STRReq(int lvl) {
		return 8;
	}
	
	private Char enemy;
	
	@Override
	protected void onThrow(int cell) {
		enemy = Actor.findChar(cell);
		super.onThrow(cell);
	}
	
	@Override
	public int damageRoll(Char owner) {
		if (owner instanceof Hero) {
			Hero hero = (Hero)owner;
			if (enemy instanceof Mob && ((Mob) enemy).surprisedBy(hero)) {
				//deals 75% toward max to max on surprise, instead of min to max.
				int diff = max() - min();
				int damage = augment.damageFactor(Random.NormalIntRange(
						min() + Math.round(diff*0.95f),
						max()));
				damage = Math.round(damage * RingOfSharpshooting.damageMultiplier( hero ));
				int exStr = hero.STR() - STRReq();
				if (exStr > 0 && hero.heroClass == HeroClass.RANGER) {
					damage += Random.IntRange(0, exStr);
				}
				return damage;
			}
		}
		return super.damageRoll(owner);
	}
	
	@Override
	protected float durabilityPerUse() {
		return super.durabilityPerUse()*2f;
	}
	
	@Override
	public int price() {
		return 6 * quantity;
	}
}
