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

package com.gfpixel.gfpixeldungeon.items.scrolls;

import com.gfpixel.gfpixeldungeon.Assets;
import com.gfpixel.gfpixeldungeon.Dungeon;
import com.gfpixel.gfpixeldungeon.actors.buffs.Blindness;
import com.gfpixel.gfpixeldungeon.actors.buffs.Buff;
import com.gfpixel.gfpixeldungeon.actors.buffs.Invisibility;
import com.gfpixel.gfpixeldungeon.actors.buffs.Paralysis;
import com.gfpixel.gfpixeldungeon.actors.mobs.Mob;
import com.gfpixel.gfpixeldungeon.messages.Messages;
import com.gfpixel.gfpixeldungeon.scenes.GameScene;
import com.gfpixel.gfpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class ScrollOfPsionicBlast extends Scroll {

	{
		initials = 5;

		bones = true;
	}
	
	@Override
	public void doRead() {
		
		GameScene.flash( 0xFFFFFF );
		
		Sample.INSTANCE.play( Assets.SND_BLAST );
		Invisibility.dispel();
		
		for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
			if (Dungeon.level.heroFOV[mob.pos]) {
				mob.damage(mob.HP, this);
			}
		}

		curUser.damage(Math.max(curUser.HT/5, curUser.HP/2), this);
		if (curUser.isAlive()) {
			Buff.prolong(curUser, Paralysis.class, Random.Int(4, 6));
			Buff.prolong(curUser, Blindness.class, Random.Int(6, 9));
			Dungeon.observe();
		}
		
		setKnown();
		
		readAnimation();

		if (!curUser.isAlive()) {
			Dungeon.fail( getClass() );
			GLog.n( Messages.get(this, "ondeath") );
		}
	}
	
	@Override
	public void empoweredRead() {
		GameScene.flash( 0xFFFFFF );
		
		Sample.INSTANCE.play( Assets.SND_BLAST );
		Invisibility.dispel();
		
		for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
			if (Dungeon.level.heroFOV[mob.pos]) {
				mob.damage(mob.HT, this );
			}
		}
		
		setKnown();
		
		readAnimation();
	}
	
	@Override
	public int price() {
		return isKnown() ? 50 * quantity : super.price();
	}
}
