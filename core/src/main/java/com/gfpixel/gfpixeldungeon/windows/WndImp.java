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
import com.gfpixel.gfpixeldungeon.actors.mobs.npcs.Imp;
import com.gfpixel.gfpixeldungeon.items.Item;
import com.gfpixel.gfpixeldungeon.items.quest.DwarfToken;
import com.gfpixel.gfpixeldungeon.messages.Messages;
import com.gfpixel.gfpixeldungeon.scenes.PixelScene;
import com.gfpixel.gfpixeldungeon.sprites.ImpSprite;
import com.gfpixel.gfpixeldungeon.sprites.ItemSprite;
import com.gfpixel.gfpixeldungeon.ui.RedButton;
import com.gfpixel.gfpixeldungeon.ui.RenderedTextMultiline;
import com.gfpixel.gfpixeldungeon.ui.Window;
import com.gfpixel.gfpixeldungeon.utils.GLog;

public class WndImp extends Window {
	
	private static final int WIDTH      = 120;
	private static final int BTN_HEIGHT = 20;
	private static final int GAP        = 2;

	public WndImp( final Imp imp, final DwarfToken tokens ) {
		
		super();
		
		IconTitle titlebar = new IconTitle();
		titlebar.icon( new ImpSprite() );
		titlebar.label( "아~무 이상없는 사이트야!" );
		titlebar.setRect( 0, 0, WIDTH, 0 );
		add( titlebar );

		RedButton btnReward = new RedButton( Messages.get(this, "reward") ) {
			@Override
			protected void onClick() {
				takeReward( imp, tokens, Imp.Quest.reward );
			}
		};
		btnReward.setRect( 0, titlebar.top() + titlebar.height() + GAP, WIDTH, BTN_HEIGHT );
		add( btnReward );
		
		resize( WIDTH, (int)btnReward.bottom() );
	}
	
	private void takeReward( Imp imp, DwarfToken tokens, Item reward ) {
		
		hide();
		
		tokens.detachAll( Dungeon.hero.belongings.backpack );
		if (reward == null) return;

		reward.identify();
		if (reward.doPickUp( Dungeon.hero )) {
			GLog.i( Messages.get(Dungeon.hero, "you_now_have", reward.name()) );
		} else {
			Dungeon.level.drop( reward, imp.pos ).sprite.drop();
		}
		
		imp.flee();
		
		Imp.Quest.complete();
	}
}
