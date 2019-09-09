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

package com.gfpixel.gfpixeldungeon.ui;

import com.gfpixel.gfpixeldungeon.Assets;
import com.gfpixel.gfpixeldungeon.Dungeon;
import com.gfpixel.gfpixeldungeon.Statistics;
import com.gfpixel.gfpixeldungeon.effects.Speck;
import com.gfpixel.gfpixeldungeon.items.Item;
import com.gfpixel.gfpixeldungeon.scenes.GameScene;
import com.gfpixel.gfpixeldungeon.scenes.PixelScene;
import com.gfpixel.gfpixeldungeon.sprites.HeroSprite;
import com.gfpixel.gfpixeldungeon.windows.WndGame;
import com.gfpixel.gfpixeldungeon.windows.WndHero;
import com.gfpixel.gfpixeldungeon.windows.WndJournal;
import com.watabou.input.Touchscreen.Touch;
import com.watabou.noosa.ColorBlock;
import com.watabou.noosa.BitmapText;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.TouchArea;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.ui.Button;
import com.watabou.noosa.ui.Component;
import com.watabou.utils.ColorMath;

public class StatusPane extends Component {

	private NinePatch bg;
	private Image avatar;
	private float warning;

	private int lastTier = 0;

	private Image rawShielding;
	private Image shieldedHP;
	private Image hp;
	private Image exp;

	private BossHealthBar bossHP;

	private int lastLvl = -1;

	private BitmapText level;
	private BitmapText depth;

	private Image depthIcon;

	private DangerIndicator danger;
	private BuffIndicator buffs;
	private Compass compass;

	private JournalButton btnJournal;
	private MenuButton btnMenu;

	private Toolbar.PickedUpItem pickedUp;

	private KeyDisplay keys;
	private ColorBlock keysBg;
	private TouchArea keysTouchArea;

	@Override
	protected void createChildren() {

		bg = new NinePatch( Assets.STATUS, 0, 0, 106, 41, 106, 0, 0, 0 );
		add( bg );

		add( new TouchArea( 0, 1, 34, 34 ) {
			@Override
			protected void onClick( Touch touch ) {
				Image sprite = Dungeon.hero.sprite;
				if (!sprite.isVisible()) {
					Camera.main.focusOn( sprite );
				}
				GameScene.show( new WndHero() );
			}
		} );

		btnJournal = new JournalButton();
		add( btnJournal );

		btnMenu = new MenuButton();
		add( btnMenu );

		avatar = HeroSprite.avatar( Dungeon.hero.heroClass, lastTier );
		add( avatar );

		compass = new Compass( Statistics.amuletObtained ? Dungeon.level.entrance : Dungeon.level.exit );
		add( compass );

		rawShielding = new Image( Assets.SHLD_BAR );
		rawShielding.alpha(0.5f);
		add(rawShielding);

		shieldedHP = new Image( Assets.SHLD_BAR );
		add(shieldedHP);

		hp = new Image( Assets.HP_BAR );
		add( hp );

		exp = new Image( Assets.XP_BAR );
		add( exp );

		bossHP = new BossHealthBar();
		add( bossHP );

		level = new BitmapText( PixelScene.pixelFont);
		level.hardlight( 0xFFEBA4 );
		add( level );

		depthIcon = new Image( Assets.MENU, 32, 7, 15, 9  );
		add( depthIcon );

		depth = new BitmapText( Integer.toString( Dungeon.depth ), PixelScene.pixelFont);
		depth.hardlight( 0xCACFC2 );
		depth.measure();
		add( depth );

		danger = new DangerIndicator();
		add( danger );

		buffs = new BuffIndicator( Dungeon.hero );
		add( buffs );

		add( pickedUp = new Toolbar.PickedUpItem());

		keysBg = new ColorBlock(1, 1, 0xFF444444);
		add(keysBg);

		keysTouchArea = new TouchArea( keysBg ) {
			@Override
			protected void onClick( Touch touch ) {

				WndJournal.last_index = 1;
				GameScene.show( new WndJournal() );
			}
		};
		add( keysTouchArea );

		keys = new KeyDisplay();
		add(keys);
		updateKeys();
	}

	@Override
	protected void layout() {

		height = 36;

		bg.size( width, bg.height );

		avatar.x = bg.x + 19 - avatar.width / 2f;
		avatar.y = bg.y + 17 - avatar.height / 2f;
		PixelScene.align(avatar);

		compass.x = avatar.x + avatar.width / 2f - compass.origin.x;
		compass.y = avatar.y + avatar.height / 2f - compass.origin.y;
		PixelScene.align(compass);

		hp.x = shieldedHP.x = rawShielding.x = 38;
		hp.y = shieldedHP.y = rawShielding.y = 4;

		bossHP.setPos( 6 + (width - bossHP.width())/2.f, 20);

		depthIcon.x = width - depthIcon.width() - 2;
		depthIcon.y = 4;

		depth.x = depthIcon.x + depthIcon.width() * 0.75f - depth.width() / 2.f;
		depth.y = depthIcon.y + (depthIcon.height() - depth.baseLine()) / 2.f - 0.75f;
		PixelScene.align(depth);

		danger.setPos( width - danger.width(), height);

		buffs.setPos( 37.f, 12.5f );

		btnJournal.setPos( width - 42, 12 );

        btnMenu.setPos( width - btnMenu.width(), 12 );

		keys.x = bg.x + 3;
		keys.y = bg.y + bg.height;

		keysBg.x = keys.x;
		keysBg.y = keys.y;
	}

	private static final int[] warningColors = new int[]{0x660000, 0xCC0000, 0x660000};

	@Override
	public void update() {
		super.update();

		float health = Dungeon.hero.HP;
		float shield = Dungeon.hero.SHLD;
		float max = Dungeon.hero.HT;

		if (!Dungeon.hero.isAlive()) {
			avatar.tint(0x000000, 0.5f);
		} else if ((health/max) < 0.3f) {
			warning += Game.elapsed * 5f *(0.4f - (health/max));
			warning %= 1f;
			avatar.tint(ColorMath.interpolate(warning, warningColors), 0.5f );
		} else {
			avatar.resetColor();
		}

		hp.scale.x = health / Math.max( max, health + shield );//Math.max( 0, (health-shield)/max);
		shieldedHP.scale.x = ( health + shield ) / Math.max( max, health + shield );
		rawShielding.scale.x = 0.f;//shield/max;

		exp.scale.x = (width / exp.width) * Dungeon.hero.exp / Dungeon.hero.maxExp();

		if (Dungeon.hero.lvl != lastLvl) {

			if (lastLvl != -1) {
				Emitter emitter = (Emitter)recycle( Emitter.class );
				emitter.revive();
				emitter.pos( 27, 27 );
				emitter.burst( Speck.factory( Speck.STAR ), 12 );
			}

			lastLvl = Dungeon.hero.lvl;
			level.text( Integer.toString( lastLvl ) );
			level.measure();
			level.x = 32.f - level.width() / 2f;
			level.y = 31.5f - level.baseLine() / 2f;
			PixelScene.align(level);
		}

		int tier = Dungeon.hero.tier();
		if (tier != lastTier) {
			lastTier = tier;
			avatar.copy( HeroSprite.avatar( Dungeon.hero.heroClass, Dungeon.hero.isEquipping() ? 1 : 0 ) );
		}
	}

	public void pickupJournal( Item item, int cell) {
			pickedUp.reset(item,
					cell,
					btnJournal.journalIcon.x + btnJournal.journalIcon.width() / 2f,
					btnJournal.journalIcon.y + btnJournal.journalIcon.height() / 2f);
	}

	public void pickupKey(Item item, int cell){
		pickedUp.reset(item, cell, keys.x, keys.y);
	}

	public void flash(){
		btnJournal.flashing = true;
	}

	public void updateKeys(){
		keys.updateKeys();
		keysTouchArea.active = keysBg.visible = keys.visible;
		keysBg.size(keys.width,  keys.height);
	}

	private static class JournalButton extends Button {

		private Image bg;
		private Image journalIcon;

		private boolean flashing;

		@SuppressWarnings("ConstantConditions")
		public JournalButton() {
			super();

			if (bg == null) {
				return;
			}

			width = bg.width;
			height = bg.height;
		}

		@Override
		protected void createChildren() {
			super.createChildren();

			bg = new Image( Assets.MENU, 0, 0, 16, 16 );
			add( bg );

			journalIcon = new Image( Assets.MENU, 32, 0, 6, 6);
			add( journalIcon );
		}

		@Override
		protected void layout() {
			super.layout();

			bg.x = x + 8;
			bg.y = y + 2;

			hotArea.x = bg.x;
			hotArea.y = bg.y;

			hotArea.width = bg.width;
			hotArea.height = bg.height;

			journalIcon.x = bg.x + 2;
			journalIcon.y = bg.y + 2;
			PixelScene.align(journalIcon);
		}

		private float time;

		@Override
		public void update() {
			super.update();

			if (flashing){
				journalIcon.am = (float)Math.abs(Math.cos( 3 * (time += Game.elapsed) ));
				if (time >= 0.333f*Math.PI) {
					time = 0;
				}
			}
		}

		@Override
		protected void onTouchDown() {
			bg.brightness( 1.5f );
			Sample.INSTANCE.play( Assets.SND_CLICK );
		}

		@Override
		protected void onTouchUp() {
				bg.resetColor();
		}

		@Override
		protected void onClick() {
			flashing = false;
			time = 0;
			journalIcon.am = 1;
			GameScene.show( new WndJournal() );
		}

	}

	private static class MenuButton extends Button {

		private Image image;

		public MenuButton() {
			super();

			width = image.width + 4;
			height = image.height + 4;
		}

		@Override
		protected void createChildren() {
			super.createChildren();

			image = new Image( Assets.MENU, 16, 0, 16, 16 );
			add( image );
		}

		@Override
		protected void layout() {
			super.layout();

			image.x = x + 2;
			image.y = y + 2;

			hotArea.x = image.x;
			hotArea.y = image.y;

			hotArea.width = image.width;
			hotArea.height = image.height;
		}

		@Override
		protected void onTouchDown() {
			image.brightness( 1.5f );
			Sample.INSTANCE.play( Assets.SND_CLICK );
		}

		@Override
		protected void onTouchUp() {
			image.resetColor();
		}

		@Override
		protected void onClick() {
			GameScene.show( new WndGame() );
		}
	}
}
