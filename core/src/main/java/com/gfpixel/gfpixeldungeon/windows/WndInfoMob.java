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

import com.gfpixel.gfpixeldungeon.actors.mobs.Mob;
import com.gfpixel.gfpixeldungeon.messages.Messages;
import com.gfpixel.gfpixeldungeon.scenes.PixelScene;
import com.gfpixel.gfpixeldungeon.sprites.CharSprite;
import com.gfpixel.gfpixeldungeon.ui.BuffIndicator;
import com.gfpixel.gfpixeldungeon.ui.HealthBar;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.ui.Component;

public class WndInfoMob extends WndTitledMessage {
	
	public WndInfoMob( Mob mob ) {
		
		super( new MobTitle( mob ), mob.description() );
		
	}
	
	private static class MobTitle extends Component {

		private static final int GAP	= 2;
		
		private CharSprite image;
		private RenderedText name;
		private HealthBar health;
		private BuffIndicator buffs;
		
		public MobTitle( Mob mob ) {
			
			name = PixelScene.renderText( Messages.titleCase( mob.name ), 9 );
			name.hardlight( TITLE_COLOR );
			add( name );
			
			image = mob.sprite();
			add( image );

			health = new HealthBar();
			health.level(mob);
			add( health );

			buffs = new BuffIndicator( mob );
			add( buffs );
		}
		
		@Override
		protected void layout() {
			
			image.x = 0;
			image.y = Math.max( 0, name.height() + health.height() - image.height );

			name.x = image.width + GAP;
			name.y = Math.max( 0, image.height - health.height() - name.height());

			float w = width - image.width - GAP;

			health.setRect(image.width + GAP, name.y + name.height(), w, health.height());

			buffs.setPos(
				name.x + name.width() + GAP-1,
				name.y + name.baseLine() - BuffIndicator.SIZE-2 );

			height = health.bottom();
		}
	}
}
