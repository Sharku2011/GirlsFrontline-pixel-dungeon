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

package com.gfpixel.gfpixeldungeon.items.food;

import com.gfpixel.gfpixeldungeon.Assets;
import com.gfpixel.gfpixeldungeon.Badges;
import com.gfpixel.gfpixeldungeon.Dungeon;
import com.gfpixel.gfpixeldungeon.actors.buffs.Buff;
import com.gfpixel.gfpixeldungeon.actors.buffs.Hunger;
import com.gfpixel.gfpixeldungeon.actors.buffs.Invisibility;
import com.gfpixel.gfpixeldungeon.actors.buffs.Recharging;
import com.gfpixel.gfpixeldungeon.actors.buffs.Terror;
import com.gfpixel.gfpixeldungeon.actors.hero.Hero;
import com.gfpixel.gfpixeldungeon.actors.mobs.Mob;
import com.gfpixel.gfpixeldungeon.effects.Flare;
import com.gfpixel.gfpixeldungeon.effects.Speck;
import com.gfpixel.gfpixeldungeon.items.scrolls.ScrollOfRecharging;
import com.gfpixel.gfpixeldungeon.messages.Messages;
import com.gfpixel.gfpixeldungeon.sprites.ItemSpriteSheet;
import com.gfpixel.gfpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Holidays;

import java.util.Calendar;

public class Pasty extends Food {

	{
		reset();

		energy = Hunger.STARVING;

		bones = true;
	}
	
	@Override
	public void reset() {
		super.reset();
		switch( Holidays.getHolidays() ){
			case NONE:
				name = Messages.get(this, "pasty");
				image = ItemSpriteSheet.PASTY;
				break;
			case BREAD_INDEPENDENT:
				name = Messages.get( this, "cinnamonroll" );
				image = ItemSpriteSheet.CINNAMONROLL;
				break;
			case HWEEN:
				name = Messages.get(this, "pie");
				image = ItemSpriteSheet.PUMPKIN_PIE;
				break;
			case XMAS:
				name = Messages.get(this, "cane");
				image = ItemSpriteSheet.CANDY_CANE;
				break;
		}
	}
	
	@Override
	public void execute(Hero hero, String action) {
		super.execute(hero, action);

		if (action.equals(AC_EAT)){
			switch( Holidays.getHolidays() ){
				case NONE:
					new Flare( 5, 32 ).color( 0xFF0000, true ).show( curUser.sprite, 2f );
					Sample.INSTANCE.play( Assets.SND_READ );
					Invisibility.dispel();

					int count = 0;
					Mob affected = null;
					for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
						if (Dungeon.level.heroFOV[mob.pos]) {
							Buff.affect( mob, Terror.class, TIME_TO_EAT ).object = curUser.id();

							if (mob.buff(Terror.class) != null){
								count++;
								affected = mob;
							}
						}
					}

					switch (count) {
						case 0:
							break;
						case 1:
							GLog.n( Messages.get(this, "one", affected.name) );
							break;
						default:
							GLog.n( Messages.get(this, "many") );
					}
					break; //으악 미친 씹놈이다 - 민초충 out!
				case BREAD_INDEPENDENT:
					if (Badges.getLocal().contains(Badges.Badge.FOOD_EATEN_1))
						GLog.n(Messages.get(this, "cinnamon_msg"));
					break;
				case HWEEN:
					//heals for 10% max hp
					hero.HP = Math.min(hero.HP + hero.HT/10, hero.HT);
					hero.sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
					break;
				case XMAS:
					Buff.affect( hero, Recharging.class, 2f ); //half of a charge
					ScrollOfRecharging.charge( hero );
					break;
			}
		}
	}

	@Override
	public String info() {
		switch( Holidays.getHolidays() ){
			case NONE: default:
				return Messages.get(this, "pasty_desc" );
			case BREAD_INDEPENDENT:
				return Messages.get( this, "cinnamonroll_desc" );
			case HWEEN:
				return Messages.get(this, "pie_desc" );
			case XMAS:
				return Messages.get(this, "cane_desc" );
		}
	}
	
	@Override
	public int price() {
		return 20 * quantity;
	}
}
