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

package com.gfpixel.gfpixeldungeon.sprites;

import com.gfpixel.gfpixeldungeon.Assets;
import com.gfpixel.gfpixeldungeon.actors.Char;
import com.gfpixel.gfpixeldungeon.actors.mobs.Yog;
import com.gfpixel.gfpixeldungeon.effects.Halo;
import com.gfpixel.gfpixeldungeon.effects.Splash;
import com.watabou.glwrap.Blending;
import com.watabou.noosa.Game;
import com.watabou.noosa.TextureFilm;
import com.watabou.utils.PointF;

public class M4a1Sprite extends MobSprite {

	public M4a1Sprite() {
		super();

		perspectiveRaise = 0.2f;

		texture( Assets.M4A1 );
		
		TextureFilm frames = new TextureFilm( texture, 17, 20 );
		
		idle = new Animation( 10, true );
		idle.frames( frames, 0);
		
		run = new Animation( 12, true );
		run.frames( frames, 0 );
		
		attack = new Animation( 12, false );
		attack.frames( frames, 0 );
		
		die = new Animation( 3, false );
		die.frames( frames, 1, 2, 3, 4, 4, 4, 4, 3, 2 );
		
		play( idle );
	}

	public class Shield extends Halo {

		private float phase;

		public Shield() {

			super( 9, 0xBBAACC, 1f );

			am = -0.33f;
			aa = +0.33f;

			phase = 1;
		}

		@Override
		public void update() {
			super.update();

			if (phase < 1) {
				if ((phase -= Game.elapsed) <= 0) {
					killAndErase();
				} else {
					scale.set( (2 - phase) * radius / RADIUS );
					am = phase * (-1);
					aa = phase * (+1);
				}
			}

			if (visible = M4a1Sprite.this.visible) {
				PointF p = M4a1Sprite.this.center();
				point(p.x, p.y );
			}
		}

		@Override
		public void draw() {
			Blending.setLightMode();
			super.draw();
			Blending.setNormalMode();
		}

		public void putOut() {
			phase = 0.999f;
		}
	}
}
