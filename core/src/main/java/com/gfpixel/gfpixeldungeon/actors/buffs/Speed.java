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

package com.gfpixel.gfpixeldungeon.actors.buffs;

import com.gfpixel.gfpixeldungeon.messages.Messages;
import com.gfpixel.gfpixeldungeon.ui.BuffIndicator;

public class Speed extends FlavourBuff {

	{
		type = buffType.POSITIVE;
	}
	
	public static final float DURATION = 10f;

	private static float MULTIPLIER = 1f;

	public static float getMultiplier() { return MULTIPLIER; }
	public static void setMultiplier(float newMult) {
		MULTIPLIER = Math.max(0.1f, newMult);
	}

	@Override
	public int icon() { return BuffIndicator.EVASION; }

	@Override
	public String toString() {
		return Messages.get(this, "name");
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc", Math.round(MULTIPLIER * 100), dispTurns());
	}

}