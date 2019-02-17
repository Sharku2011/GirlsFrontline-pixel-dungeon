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

package com.gfpixel.gfpixeldungeon.windows;

import com.gfpixel.gfpixeldungeon.Dungeon;
import com.gfpixel.gfpixeldungeon.actors.hero.HeroClass;
import com.gfpixel.gfpixeldungeon.actors.mobs.npcs.Wandmaker;
import com.gfpixel.gfpixeldungeon.items.Item;
import com.gfpixel.gfpixeldungeon.items.wands.Wand;
import com.gfpixel.gfpixeldungeon.messages.Messages;
import com.gfpixel.gfpixeldungeon.sprites.WandmakerSprite;
import com.gfpixel.gfpixeldungeon.ui.RedButton;
import com.gfpixel.gfpixeldungeon.ui.Window;
import com.gfpixel.gfpixeldungeon.utils.GLog;

public class WndWandmaker extends Window {

	private static final int WIDTH		= 120;
	private static final int BTN_HEIGHT	= 20;
	private static final float GAP		= 2;
	
	public WndWandmaker( final Wandmaker wandmaker, final Item item ) {
		
		super();
		
		IconTitle titlebar = new IconTitle();
		titlebar.icon(new WandmakerSprite());
		titlebar.label(Messages.get(this, "title"));
		titlebar.setRect(0, 0, WIDTH, 0);
		add( titlebar );

		RedButton btnWand1 = new RedButton( Wandmaker.Quest.wand1.name() ) {
			@Override
			protected void onClick() {
				selectReward( wandmaker, item, Wandmaker.Quest.wand1 );
			}
		};
		btnWand1.setRect(0, titlebar.top() + titlebar.height() + GAP, WIDTH, BTN_HEIGHT);
		add( btnWand1 );
		
		RedButton btnWand2 = new RedButton( Wandmaker.Quest.wand2.name() ) {
			@Override
			protected void onClick() {
				selectReward( wandmaker, item, Wandmaker.Quest.wand2 );
			}
		};
		btnWand2.setRect(0, btnWand1.bottom() + GAP, WIDTH, BTN_HEIGHT);
		add( btnWand2 );
		
		resize(WIDTH, (int) btnWand2.bottom());
	}
	
	private void selectReward( Wandmaker wandmaker, Item item, Wand reward ) {
		
		hide();
		
		item.detach( Dungeon.hero.belongings.backpack );

		reward.identify();
		if (reward.doPickUp( Dungeon.hero )) {
			GLog.i( Messages.get(Dungeon.hero, "you_now_have", reward.name()) );
		} else {
			Dungeon.level.drop( reward, wandmaker.pos ).sprite.drop();
		}

		String farewellMsg = Messages.get(this, "farewell");


		if (Dungeon.hero.heroClass == HeroClass.RANGER)
		{
			farewellMsg += Messages.get(this, "farewell_hk416");
		}
		wandmaker.yell( farewellMsg );

		wandmaker.destroy();
		
		wandmaker.sprite.die();
		
		Wandmaker.Quest.complete();
	}
}
