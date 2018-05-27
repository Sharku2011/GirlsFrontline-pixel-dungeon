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

package com.gfpixel.gfpixeldungeon.levels.traps;

import com.gfpixel.gfpixeldungeon.Assets;
import com.gfpixel.gfpixeldungeon.Dungeon;
import com.gfpixel.gfpixeldungeon.actors.hero.Hero;
import com.gfpixel.gfpixeldungeon.effects.CellEmitter;
import com.gfpixel.gfpixeldungeon.effects.particles.ShadowParticle;
import com.gfpixel.gfpixeldungeon.items.EquipableItem;
import com.gfpixel.gfpixeldungeon.items.Heap;
import com.gfpixel.gfpixeldungeon.items.Item;
import com.gfpixel.gfpixeldungeon.items.KindOfWeapon;
import com.gfpixel.gfpixeldungeon.items.KindofMisc;
import com.gfpixel.gfpixeldungeon.items.armor.Armor;
import com.gfpixel.gfpixeldungeon.items.weapon.Weapon;
import com.gfpixel.gfpixeldungeon.items.weapon.missiles.Boomerang;
import com.gfpixel.gfpixeldungeon.messages.Messages;
import com.gfpixel.gfpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Collections;

public class CursingTrap extends Trap {

	{
		color = VIOLET;
		shape = WAVES;
	}

	@Override
	public void activate() {
		if (Dungeon.level.heroFOV[ pos ]) {
			CellEmitter.get(pos).burst(ShadowParticle.UP, 5);
			Sample.INSTANCE.play(Assets.SND_CURSED);
		}

		Heap heap = Dungeon.level.heaps.get( pos );
		if (heap != null){
			for (Item item : heap.items){
				if (item.isUpgradable())
					curse(item);
			}
		}

		if (Dungeon.hero.pos == pos){
			curse(Dungeon.hero);
		}
	}

	public static void curse(Hero hero){
		//items the trap wants to curse because it will create a more negative effect
		ArrayList<Item> priorityCurse = new ArrayList<>();
		//items the trap can curse if nothing else is available.
		ArrayList<Item> canCurse = new ArrayList<>();

		KindOfWeapon weapon = hero.belongings.weapon;
		if (weapon instanceof Weapon && !weapon.cursed && !(weapon instanceof Boomerang)){
			if (((Weapon) weapon).enchantment == null)
				priorityCurse.add(weapon);
			else
				canCurse.add(weapon);
		}

		Armor armor = hero.belongings.armor;
		if (armor != null && !armor.cursed){
			if (armor.glyph == null)
				priorityCurse.add(armor);
			else
				canCurse.add(armor);
		}

		KindofMisc misc1 = hero.belongings.misc1;
		if (misc1 != null){
			canCurse.add(misc1);
		}

		KindofMisc misc2 = hero.belongings.misc2;
		if (misc2 != null){
			canCurse.add(misc2);
		}

		Collections.shuffle(priorityCurse);
		Collections.shuffle(canCurse);

		int numCurses = Random.Int(2) == 0 ? 1 : 2;

		for (int i = 0; i < numCurses; i++){
			if (!priorityCurse.isEmpty()){
				curse(priorityCurse.remove(0));
			} else if (!canCurse.isEmpty()){
				curse(canCurse.remove(0));
			}
		}

		EquipableItem.equipCursed(hero);
		GLog.n( Messages.get(CursingTrap.class, "curse") );
	}

	private static void curse(Item item){
		item.cursed = item.cursedKnown = true;

		if (item instanceof Weapon){
			Weapon w = (Weapon) item;
			if (w.enchantment == null){
				w.enchantment = Weapon.Enchantment.randomCurse();
			}
		}
		if (item instanceof Armor){
			Armor a = (Armor) item;
			if (a.glyph == null){
				a.glyph = Armor.Glyph.randomCurse();
			}
		}
	}
}
