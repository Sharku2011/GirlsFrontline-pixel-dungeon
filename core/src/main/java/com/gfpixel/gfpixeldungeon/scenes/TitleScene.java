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

package com.gfpixel.gfpixeldungeon.scenes;

import com.gfpixel.gfpixeldungeon.Assets;
import com.gfpixel.gfpixeldungeon.BuildConfig;
import com.gfpixel.gfpixeldungeon.GamesInProgress;
import com.gfpixel.gfpixeldungeon.GirlsFrontlinePixelDungeon;
import com.gfpixel.gfpixeldungeon.SPDSettings;
import com.gfpixel.gfpixeldungeon.effects.BannerSprites;
import com.gfpixel.gfpixeldungeon.effects.Fireball;
import com.gfpixel.gfpixeldungeon.messages.Messages;
import com.gfpixel.gfpixeldungeon.ui.Archs;
import com.gfpixel.gfpixeldungeon.ui.ChangesButton;
import com.gfpixel.gfpixeldungeon.ui.ExitButton;
import com.gfpixel.gfpixeldungeon.ui.LanguageButton;
import com.gfpixel.gfpixeldungeon.ui.PrefsButton;
import com.gfpixel.gfpixeldungeon.utils.GLog;
import com.gfpixel.gfpixeldungeon.windows.WndSelectGameInProgress;
import com.gfpixel.gfpixeldungeon.windows.WndStartGame;
import com.watabou.glwrap.Blending;
import com.watabou.noosa.BitmapText;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.ui.Button;
import com.watabou.utils.PointF;

import java.util.ArrayList;

public class TitleScene extends PixelScene {

	static public final int PADDING = 4;

	private enum playbackDirection {
		FORWARD,
		BACKWARD,
		PAUSE
	}

	@Override
	public void create() {
		
		super.create();

		Music.INSTANCE.play( Assets.THEME, true );

		uiCamera.visible = false;
		
		int w = Camera.main.width;
		int h = Camera.main.height;
		
		Archs archs = new Archs();
		archs.setSize( w, h );
		add( archs );

		Image logoGif = new Image( Assets.LOGO, 0, 0, 128, 128) {
			private float time = 0;
			final private float pauseTime = 4.f;
			private int index = 0;
			private playbackDirection playDirection = playbackDirection.FORWARD;

			@Override
			public void update() {
				super.update();

				time += Game.elapsed;

				boolean bShouldUpdate = false;

				if (playDirection == playbackDirection.PAUSE) {
					bShouldUpdate = ( time >= pauseTime );
				} else {
					// 11 Fps
					bShouldUpdate = ( time >= 0.09f );
				}

				if (!bShouldUpdate) {
					return;
				} else {
					time = 0.f;
				}

				int currentFrame = index;

				switch (playDirection) {
					case FORWARD:
						if (index >= 10) {
							playDirection = playbackDirection.PAUSE;
						} else {
							index++;
						}
						break;
					case BACKWARD:
						if (index <= 0) {
							playDirection = playbackDirection.PAUSE;
						} else {
							index--;
						}
						break;
					case PAUSE:
						if (index >= 10) {
							playDirection = playbackDirection.BACKWARD;
						} else if (index <= 0) {
							playDirection = playbackDirection.FORWARD;
						} else {
							if (!BuildConfig.DEBUG) {
								playDirection = playbackDirection.FORWARD;
								index = 0;
								return;
							} else {
								GLog.w( "Animation is not ended, but playback status is pause" );
								return;
							}
						}
						return;
					default:
						return;
				}

				int row = ( currentFrame % 4 ) * 128;
				int col = ( currentFrame / 4 ) * 128;

				frame( col, row, 128, 128 );
			}
		};
		add(logoGif);

		float topRegion = Math.max(95f, h*0.45f);

		logoGif.x = (w - logoGif.width()) / 2f;
		logoGif.y = topRegion - logoGif.height() - 7.f;

		Image title = BannerSprites.get( BannerSprites.Type.PIXEL_DUNGEON_TITLE );
		title.x = (w - title.width()) / 2f;

		if (SPDSettings.landscape()) {
			title.y = (topRegion - title.height()) / 6f * 5f;
		} else {
			title.y = 16 + (topRegion - title.height() - 16) / 6f * 5f;
		}
		add(title);

		DashboardItem btnPlay = new DashboardItem( Messages.get(this, "play"), 0 ) {
			@Override
			protected void onClick() {
				if (GamesInProgress.checkAll().size() == 0){
					TitleScene.this.add( new WndStartGame(1) );
				} else {
					TitleScene.this.add( new WndSelectGameInProgress() );
					//GirlsFrontlinePixelDungeon.switchNoFade( StartScene.class );
				}
			}
		};
		add( btnPlay );

		final DashboardItem btnAbout = new DashboardItem( Messages.get(this, "about"), 1 ) {
			@Override
			protected void onClick() {
				GirlsFrontlinePixelDungeon.switchNoFade( AboutScene.class );
			}
		};
		add( btnAbout );
		
		DashboardItem btnRankings = new DashboardItem( Messages.get(this, "rankings"), 2 ) {
			@Override
			protected void onClick() {
				GirlsFrontlinePixelDungeon.switchNoFade( RankingsScene.class );
			}
		};
		add( btnRankings );

		DashboardItem btnBadges = new DashboardItem( Messages.get(this, "badges"), 3 ) {
			@Override
			protected void onClick() {
				GirlsFrontlinePixelDungeon.switchNoFade( BadgesScene.class );
			}
		};
		add(btnBadges);

		if (SPDSettings.landscape()) {
			btnRankings     .setPos( w / 2 - btnRankings.width(), topRegion );
			btnBadges       .setPos( w / 2, topRegion );
			btnPlay         .setPos( btnRankings.left() - btnPlay.width(), topRegion );
			btnAbout        .setPos( btnBadges.right(), topRegion );
		} else {
			btnPlay.setPos( w / 2 - btnPlay.width(), topRegion );
			btnRankings.setPos( w / 2, btnPlay.top() );
			btnBadges.setPos( w / 2 - btnBadges.width(), btnPlay.top() + DashboardItem.SIZE );
			btnAbout.setPos( w / 2, btnBadges.top() );
		}

		Button changes = new ChangesButton();
		changes.setPos( w - changes.width() - PADDING, h - changes.height() - PADDING);
		add( changes );

		BitmapText version = new BitmapText( "v " + Game.version + "", pixelFont);
		version.measure();
		version.hardlight( 0xCCCCCC );
		version.scale = new PointF( 1.25f, 1.25f );
		version.x = w - changes.width() - PADDING - version.width() - PADDING;
		version.y = h - version.height() - PADDING;
		add( version );

		int pos = PADDING;

		PrefsButton btnPrefs = new PrefsButton();
		btnPrefs.setRect( pos, PADDING, 12, 12 );
		add( btnPrefs );

		pos += btnPrefs.width() + PADDING;

		LanguageButton btnLang = new LanguageButton();
		btnLang.setRect(pos, PADDING, 12, 12);
		add( btnLang );

		ExitButton btnExit = new ExitButton();
		btnExit.setRect(w - btnExit.width() - PADDING, PADDING, 12, 12 );
		add( btnExit );

		fadeIn();
	}
	
	private void placeTorch( float x, float y ) {
		Fireball fb = new Fireball();
		fb.setPos( x, y );
		add( fb );
	}
	
	private static class DashboardItem extends Button {
		
		public static final float SIZE	= 48;
		
		private static final int IMAGE_SIZE	= 32;
		
		private Image image;
		private RenderedText label;
		
		public DashboardItem( String text, int index ) {
			super();
			
			image.frame( image.texture.uvRect( index * IMAGE_SIZE, 0, (index + 1) * IMAGE_SIZE, IMAGE_SIZE ) );
			this.label.text( text );
			
			setSize( SIZE, SIZE );
		}

		public final Image getImage() { return image; }
		
		@Override
		protected void createChildren() {
			super.createChildren();
			
			image = new Image( Assets.DASHBOARD );
			add( image );
			
			label = renderText( 9 );
			add( label );
		}
		
		@Override
		protected void layout() {
			super.layout();
			
			image.x = x + (width - image.width()) / 2;
			image.y = y;
			align(image);
			
			label.x = x + (width - label.width()) / 2;
			label.y = image.y + image.height() +2;
			align(label);
		}
		
		@Override
		protected void onTouchDown() {
			getImage().color(52/255f, 50/255f, 53/255f);
			Sample.INSTANCE.play( Assets.SND_CLICK, 1, 1, 0.8f );
		}
		
		@Override
		protected void onTouchUp() {
			image.resetColor();
		}
	}
}
