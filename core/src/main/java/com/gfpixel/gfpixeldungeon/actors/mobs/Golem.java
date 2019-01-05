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

package com.gfpixel.gfpixeldungeon.actors.mobs;

import com.gfpixel.gfpixeldungeon.actors.Char;
import com.gfpixel.gfpixeldungeon.actors.buffs.Amok;
import com.gfpixel.gfpixeldungeon.actors.buffs.Sleep;
import com.gfpixel.gfpixeldungeon.actors.buffs.Terror;
import com.gfpixel.gfpixeldungeon.actors.mobs.npcs.Imp;
import com.gfpixel.gfpixeldungeon.items.artifacts.CapeOfThorns;
import com.gfpixel.gfpixeldungeon.sprites.GolemSprite;
import com.watabou.utils.Random;

public class Golem extends Mob {

	{
		spriteClass = GolemSprite.class;

		HP = HT = 150;
		defenseSkill = 0;
		baseSpeed = 0.9f;
		EXP = 15;
		maxLvl = 25;

		loot = new CapeOfThorns();
		lootChance = 0.02f;

		properties.add(Property.ARMO);
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 30, 45 );
	}

	@Override
	public int attackSkill( Char target ) {
		return 28;
	}

	@Override
	protected float attackDelay() {
		return 0.9f;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 12);
	}

	private final int damageCap = 50;

	@Override
	public void damage(int dmg, Object src) {
		int finalDmg = Math.min(dmg, damageCap);
		super.damage(finalDmg, src);
	}

	@Override
	public void die( Object cause ) {
		Imp.Quest.process( this );

		super.die( cause );
	}

	{
		immunities.add( Amok.class );
		immunities.add( Terror.class );
		immunities.add( Sleep.class );
	}
}