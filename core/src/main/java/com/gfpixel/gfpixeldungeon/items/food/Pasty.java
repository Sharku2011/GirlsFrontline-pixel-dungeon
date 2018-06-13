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

import com.gfpixel.gfpixeldungeon.Badges;
import com.gfpixel.gfpixeldungeon.actors.buffs.Buff;
import com.gfpixel.gfpixeldungeon.actors.buffs.Hunger;
import com.gfpixel.gfpixeldungeon.actors.buffs.Recharging;
import com.gfpixel.gfpixeldungeon.actors.hero.Hero;
import com.gfpixel.gfpixeldungeon.effects.Speck;
import com.gfpixel.gfpixeldungeon.items.scrolls.ScrollOfRecharging;
import com.gfpixel.gfpixeldungeon.messages.Messages;
import com.gfpixel.gfpixeldungeon.sprites.ItemSpriteSheet;
import com.gfpixel.gfpixeldungeon.utils.GLog;

import java.util.Calendar;

public class Pasty extends Food {

	//TODO: implement fun stuff for other holidays
	//TODO: probably should externalize this if I want to add any more festive stuff.
	private enum Holiday{
		NONE,
		EASTER, //TBD
		BREAD_INDEPENDENT, // 6월 4일 빵복절
		HWEEN,//2nd week of october though first day of november
		XMAS //3rd week of december through first week of january
	}

	private static Holiday holiday;

	static{

		holiday = Holiday.NONE;

		final Calendar calendar = Calendar.getInstance();
		switch(calendar.get(Calendar.MONTH)){
			case Calendar.JANUARY:
				if (calendar.get(Calendar.WEEK_OF_MONTH) == 1)
					holiday = Holiday.XMAS;
				break;
			case Calendar.JUNE:
				if (calendar.get(Calendar.WEEK_OF_MONTH) <= 2)
					holiday = holiday.BREAD_INDEPENDENT;
					break;
			case Calendar.OCTOBER:
				if (calendar.get(Calendar.WEEK_OF_MONTH) >= 2)
					holiday = Holiday.HWEEN;
				break;
			case Calendar.NOVEMBER:
				if (calendar.get(Calendar.DAY_OF_MONTH) == 1)
					holiday = Holiday.HWEEN;
				break;
			case Calendar.DECEMBER:
				if (calendar.get(Calendar.WEEK_OF_MONTH) >= 3)
					holiday = Holiday.XMAS;
				break;
		}
	}

	{
		reset();

		energy = Hunger.STARVING;

		bones = true;
	}
	
	@Override
	public void reset() {
		super.reset();
		switch(holiday){
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
			switch(holiday){
				case NONE:
					break; //do nothing extra
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
		switch(holiday){
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
