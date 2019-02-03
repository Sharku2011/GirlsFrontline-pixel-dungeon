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
import com.gfpixel.gfpixeldungeon.Dungeon;
import com.gfpixel.gfpixeldungeon.actors.hero.Hero;
import com.gfpixel.gfpixeldungeon.actors.hero.HeroClass;
import com.watabou.gltextures.SmartTexture;
import com.watabou.gltextures.TextureCache;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Image;
import com.watabou.noosa.TextureFilm;
import com.watabou.utils.Callback;
import com.watabou.utils.PointF;
import com.watabou.utils.RectF;

public class HeroSprite extends CharSprite {
	
	private static final int FRAME_WIDTH	= 22;
	private static final int FRAME_HEIGHT	= 23;
	
	private static final int RUN_FRAMERATE	= 18;
	
	private static TextureFilm tiers;
	
	private Animation fly;
	private Animation read;

	public HeroSprite() {
		super();
		
		link( Dungeon.hero );
		
		texture( Dungeon.hero.heroClass.spritesheet() );
		updateArmor();

		if (ch.isAlive())
			idle();
		else
			die();
	}
	
	public void updateArmor() {

		TextureFilm film = new TextureFilm( tiers(), ((Hero)ch).tier(), FRAME_WIDTH, FRAME_HEIGHT );
		
		idle = new Animation( 5, true );
		idle.frames( film, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1 );
		
		run = new Animation( RUN_FRAMERATE, true );
		run.frames( film, 2, 3, 4, 5, 6, 7 );
		
		die = new Animation( 15, false );
		die.frames( film, 8, 9, 10, 11, 12, 13 );
		
		attack = new Animation( 23, false );
		attack.frames( film, 14, 15, 16, 17, 18, 16, 17, 14 );
		
		zap = attack.clone();
		
		operate = new Animation( 8, false );
		operate.frames( film, 28, 29, 28, 29 );

		fly = new Animation( 1, true );
		fly.frames( film, 27 );

		read = new Animation( 11, false );
		read.frames( film, 19, 20, 21, 22, 23, 24, 25, 26, 20, 19  );
		
		if (ch.isAlive())
			idle();
		else
			die();
	}
	
	@Override
	public void place( int p ) {
		super.place( p );
		Camera.main.target = this;
	}

	@Override
	public void move( int from, int to ) {
		super.move( from, to );
		if (ch.flying) {
			play( fly );
		}
		Camera.main.target = this;
	}

	@Override
	public void jump( int from, int to, Callback callback ) {
		super.jump( from, to, callback );
		play( fly );
	}

	public void read() {
		animCallback = new Callback() {
			@Override
			public void call() {
				idle();
				ch.onOperateComplete();
			}
		};
		play( read );
	}

	@Override
	public void bloodBurstA(PointF from, int damage) {
		//Does nothing.

		/*
		 * This is both for visual clarity, and also for content ratings regarding violence
		 * towards human characters. The heroes are the only human or human-like characters which
		 * participate in combat, so removing all blood associated with them is a simple way to
		 * reduce the violence rating of the game.
		 */
	}

	@Override
	public void update() {
		sleeping = ch.isAlive() && ((Hero)ch).resting;
		
		super.update();
	}
	
	public void sprint( float speed ) {
		run.delay = 1f / speed / RUN_FRAMERATE;
	}
	
	public static TextureFilm tiers() {
		if (tiers == null) {
			// 우선적으로  texture를 불러와서 캐릭터 스프라이트 전체의 크기를 가져온다 - 기존의 경우 도적, 현재는 흥국이로 변경
			SmartTexture texture = TextureCache.get( Assets.RANGER );
			// 불러온 texture의 넓이와 정해진 tier별 높이만큼을 다른 스프라이트에서 제한적으로 불러와 메모리를 절약한다
			// 향후 TIERWIDTH와 TIERHEIGHT 등의 변수를 별도로 분리하여 관리하는 방안 고려할 것
			tiers = new TextureFilm( texture, texture.width, FRAME_HEIGHT );
		}
		
		return tiers;
	}
	
	public static Image avatar( HeroClass cl, int armorTier ) {
		
		RectF patch = tiers().get( armorTier );
		Image avatar = new Image( cl.spritesheet() );
		RectF frame = avatar.texture.uvRect( 1, 0, FRAME_WIDTH, FRAME_HEIGHT );
		frame.shift( patch.left, patch.top );
		avatar.frame( frame );
		
		return avatar;
	}
}
