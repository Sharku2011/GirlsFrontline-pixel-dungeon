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

package com.gfpixel.gfpixeldungeon;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.gfpixel.gfpixeldungeon.scenes.PixelScene;
import com.gfpixel.gfpixeldungeon.scenes.WelcomeScene;
import com.watabou.noosa.Game;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.DeviceCompat;

import javax.microedition.khronos.opengles.GL10;

public class GirlsFrontlinePixelDungeon extends Game {
	
	//variable constants for specific older versions of shattered, used for data conversion
	//versions older than v0.6.0b are no longer supported, and data from them is ignored
	public static final int v0_6_0b = 185;
	
	public static final int v0_6_1b = 209;
	
	public static final int v0_6_2e = 229;
	
	public static final int v0_6_3b = 245;
	
	public static final int v0_6_4a = 252;

	public static final int v0_6_5  = 260;

	//new version numbering for gpd
	public static final int v0_4_7_g	= 100;
	
	public GirlsFrontlinePixelDungeon() {
		super( WelcomeScene.class );

		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.items.weapon.melee.AR.G36.class,
				"com.gfpixel.gfpixeldungeon.items.weapon.melee.G36" );
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.items.weapon.melee.AR.Hk416.class,
				"com.gfpixel.gfpixeldungeon.items.weapon.melee.HK416" );
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.items.weapon.melee.BP.Mos.class,
				"com.gfpixel.gfpixeldungeon.items.weapon.melee.Mos" );
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.items.weapon.melee.BP.SaigaPlate.class,
				"com.gfpixel.gfpixeldungeon.items.weapon.melee.SaigaPlate" );
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.items.weapon.melee.DMR.AK47.class,
				"com.gfpixel.gfpixeldungeon.items.weapon.melee.AK47" );
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.items.weapon.melee.DMR.Dragunov.class,
				"com.gfpixel.gfpixeldungeon.items.weapon.melee.Dragunov" );
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.items.weapon.melee.DMR.Kar98.class,
				"com.gfpixel.gfpixeldungeon.items.weapon.melee.Kar98" );
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.items.weapon.melee.DMR.M16.class,
				"com.gfpixel.gfpixeldungeon.items.weapon.melee.M16" );
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.items.weapon.melee.DMR.M99.class,
				"com.gfpixel.gfpixeldungeon.items.weapon.melee.M99" );
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.items.weapon.melee.DMR.Sass.class,
				"com.gfpixel.gfpixeldungeon.items.weapon.melee.Sass" );
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.items.weapon.melee.HB.Kriss.class,
				"com.gfpixel.gfpixeldungeon.items.weapon.melee.Kriss" );
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.items.weapon.melee.HB.Type95.class,
				"com.gfpixel.gfpixeldungeon.items.weapon.melee.Type95" );
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.items.weapon.melee.Launcher.Gepard.class,
				"com.gfpixel.gfpixeldungeon.items.weapon.melee.Gepard" );
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.items.weapon.melee.Launcher.SRS.class,
				"com.gfpixel.gfpixeldungeon.items.weapon.melee.SRS" );
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.items.weapon.melee.LR.M1911.class,
				"com.gfpixel.gfpixeldungeon.items.weapon.melee.M1911" );
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.items.weapon.melee.LR.NAGANT.class,
				"com.gfpixel.gfpixeldungeon.items.weapon.melee.NAGANT" );
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.items.weapon.melee.LR.Ump40.class,
				"com.gfpixel.gfpixeldungeon.items.weapon.melee.Ump40" );
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.items.weapon.melee.LR.Wa.class,
				"com.gfpixel.gfpixeldungeon.items.weapon.melee.Wa" );
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.items.weapon.melee.MG.Dp.class,
				"com.gfpixel.gfpixeldungeon.items.weapon.melee.Dp" );
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.items.weapon.melee.MG.Mg42.class,
				"com.gfpixel.gfpixeldungeon.items.weapon.melee.Mg42" );
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.items.weapon.melee.MG.Negev.class,
				"com.gfpixel.gfpixeldungeon.items.weapon.melee.Negev" );
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.items.weapon.melee.SA.GROZA.class,
				"com.gfpixel.gfpixeldungeon.items.weapon.melee.GROZA" );
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.items.weapon.melee.SA.GUA91.class,
				"com.gfpixel.gfpixeldungeon.items.weapon.melee.GUA91" );
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.items.weapon.melee.SA.NagantRevolver.class,
				"com.gfpixel.gfpixeldungeon.items.weapon.melee.NagantRevolver" );
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.items.weapon.melee.SA.UMP9.class,
				"com.gfpixel.gfpixeldungeon.items.weapon.melee.UMP9" );

		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.items.weapon.melee.SG.Ks23.class,
				"com.gfpixel.gfpixeldungeon.items.weapon.melee.Ks23" );
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.items.weapon.melee.SG.Usas12.class,
				"com.gfpixel.gfpixeldungeon.items.weapon.melee.Usas12" );
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.items.weapon.melee.SG.Win97.class,
				"com.gfpixel.gfpixeldungeon.items.weapon.melee.Win97" );

		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.items.weapon.melee.SMG.M1a1.class,
				"com.gfpixel.gfpixeldungeon.items.weapon.melee.M1a1" );
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.items.weapon.melee.SMG.M9.class,
				"com.gfpixel.gfpixeldungeon.items.weapon.melee.M9" );
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.items.weapon.melee.SMG.SAIGA.class,
				"com.gfpixel.gfpixeldungeon.items.weapon.melee.SAIGA" );
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.items.weapon.melee.SMG.Ump45.class,
				"com.gfpixel.gfpixeldungeon.items.weapon.melee.Ump45" );

		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.items.weapon.melee.SR.AWP.class,
				"com.gfpixel.gfpixeldungeon.items.weapon.melee.AWP" );
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.items.weapon.melee.SR.M1903.class,
				"com.gfpixel.gfpixeldungeon.items.weapon.melee.M1903" );
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.items.weapon.melee.SR.Ntw20.class,
				"com.gfpixel.gfpixeldungeon.items.weapon.melee.Ntw20" );

		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.items.weapon.melee.UG.C96.class,
				"com.gfpixel.gfpixeldungeon.items.weapon.melee.C96" );
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.items.weapon.melee.UG.Cannon.class,
				"com.gfpixel.gfpixeldungeon.items.weapon.melee.Cannon" );
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.items.weapon.melee.UG.Lar.class,
				"com.gfpixel.gfpixeldungeon.items.weapon.melee.Lar" );
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.items.weapon.melee.UG.SR3.class,
				"com.gfpixel.gfpixeldungeon.items.weapon.melee.SR3" );


		
		//v0.6.2
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.levels.rooms.secret.RatKingRoom.class,
				"com.gfpixel.gfpixeldungeon.levels.rooms.special.RatKingRoom" );
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.levels.rooms.standard.PlantsRoom.class,
				"com.gfpixel.gfpixeldungeon.levels.rooms.standard.GardenRoom" );
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.levels.rooms.special.GardenRoom.class,
				"com.gfpixel.gfpixeldungeon.levels.rooms.special.FoliageRoom" );
		
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.levels.traps.WornDartTrap.class,
				"com.gfpixel.gfpixeldungeon.levels.traps.WornTrap" );
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.levels.traps.PoisonDartTrap.class,
				"com.gfpixel.gfpixeldungeon.levels.traps.PoisonTrap" );
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.levels.traps.ShockingTrap.class,
				"com.gfpixel.gfpixeldungeon.levels.traps.ParalyticTrap" );
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.levels.traps.ShockingTrap.class,
				"com.gfpixel.gfpixeldungeon.levels.traps.LightningTrap" );
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.levels.traps.GrippingTrap.class,
				"com.gfpixel.gfpixeldungeon.levels.traps.SpearTrap" );
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.levels.traps.BurningTrap.class,
				"com.gfpixel.gfpixeldungeon.levels.traps.FireTrap" );
		
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.actors.buffs.BlobImmunity.class,
				"com.gfpixel.gfpixeldungeon.actors.buffs.GasesImmunity" );
		
		//v0.6.3
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.items.weapon.missiles.Tomahawk.class,
				"com.gfpixel.gfpixeldungeon.items.weapon.missiles.Tamahawk" );
		
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.items.weapon.missiles.darts.Dart.class,
				"com.gfpixel.gfpixeldungeon.items.weapon.missiles.Dart" );
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.items.weapon.missiles.darts.IncendiaryDart.class,
				"com.gfpixel.gfpixeldungeon.items.weapon.missiles.IncendiaryDart" );
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.items.weapon.missiles.darts.ParalyticDart.class,
				"com.gfpixel.gfpixeldungeon.items.weapon.missiles.CurareDart" );
		
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.items.wands.WandOfCorrosion.class,
				"com.gfpixel.gfpixeldungeon.items.wands.WandOfVenom" );
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.actors.blobs.CorrosiveGas.class,
				"com.gfpixel.gfpixeldungeon.actors.blobs.VenomGas" );
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.actors.buffs.Corrosion.class,
				"com.gfpixel.gfpixeldungeon.actors.buffs.Venom" );
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.levels.traps.CorrosionTrap.class,
				"com.gfpixel.gfpixeldungeon.levels.traps.VenomTrap" );
		
		//v0.6.4
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.items.bags.VelvetPouch.class,
				"com.gfpixel.gfpixeldungeon.items.bags.SeedPouch" );
		
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.items.bags.MagicalHolster.class,
				"com.gfpixel.gfpixeldungeon.items.bags.WandHolster" );
		
		//v0.6.5
		com.watabou.utils.Bundle.addAlias(
				com.gfpixel.gfpixeldungeon.items.stones.StoneOfAugmentation.class,
				"com.gfpixel.gfpixeldungeon.items.Weightstone" );
		
	}
	
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate(savedInstanceState);

		updateSystemUI();
		SPDSettings.landscape ( SPDSettings.landscape() );
		
		Music.INSTANCE.enable( SPDSettings.music() );
		Music.INSTANCE.volume( SPDSettings.musicVol()/10f );
		Sample.INSTANCE.enable( SPDSettings.soundFx() );
		Sample.INSTANCE.volume( SPDSettings.SFXVol()/10f );
		
		Music.setMuteListener();

		Sample.INSTANCE.load(
				Assets.SND_CLICK,
				Assets.SND_BADGE,
				Assets.SND_GOLD,

				Assets.SND_STEP,
				Assets.SND_WATER,
				Assets.SND_OPEN,
				Assets.SND_UNLOCK,
				Assets.SND_ITEM,
				Assets.SND_DEWDROP,
				Assets.SND_HIT,
				Assets.SND_MISS,

				Assets.SND_DESCEND,
				Assets.SND_EAT,
				Assets.SND_READ,
				Assets.SND_LULLABY,
				Assets.SND_DRINK,
				Assets.SND_SHATTER,
				Assets.SND_ZAP,
				Assets.SND_LIGHTNING,
				Assets.SND_LEVELUP,
				Assets.SND_DEATH,
				Assets.SND_CHALLENGE,
				Assets.SND_CURSED,
				Assets.SND_EVOKE,
				Assets.SND_TRAP,
				Assets.SND_TOMB,
				Assets.SND_ALERT,
				Assets.SND_MELD,
				Assets.SND_BOSS,
				Assets.SND_BLAST,
				Assets.SND_PLANT,
				Assets.SND_RAY,
				Assets.SND_BEACON,
				Assets.SND_TELEPORT,
				Assets.SND_CHARMS,
				Assets.SND_MASTERY,
				Assets.SND_PUFF,
				Assets.SND_ROCKS,
				Assets.SND_BURNING,
				Assets.SND_FALLING,
				Assets.SND_GHOST,
				Assets.SND_SECRET,
				Assets.SND_BONES,
				Assets.SND_BEE,
				Assets.SND_DEGRADE,
				Assets.SND_MIMIC );

		if (!SPDSettings.systemFont()) {
			RenderedText.setFont("pixelfont.ttf");
		} else {
			RenderedText.setFont( null );
		}
		
	}

	@Override
	public void onWindowFocusChanged( boolean hasFocus ) {
		super.onWindowFocusChanged( hasFocus );
		if (hasFocus) updateSystemUI();
	}

	@Override
	@SuppressWarnings("deprecation")
	public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
		super.onMultiWindowModeChanged(isInMultiWindowMode);
		updateSystemUI();
	}

	public static void switchNoFade(Class<? extends PixelScene> c){
		switchNoFade(c, null);
	}

	public static void switchNoFade(Class<? extends PixelScene> c, SceneChangeCallback callback) {
		PixelScene.noFade = true;
		switchScene( c, callback );
	}

	@Override
	public void onSurfaceChanged( GL10 gl, int width, int height ) {

		super.onSurfaceChanged( gl, width, height );

		updateDisplaySize();

	}

	public void updateDisplaySize(){
		boolean landscape = SPDSettings.landscape();
		
		if (landscape != (width > height)) {
			instance.setRequestedOrientation(landscape ?
					ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE :
					ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
		}
		
		if (view.getMeasuredWidth() == 0 || view.getMeasuredHeight() == 0)
			return;

		dispWidth = view.getMeasuredWidth();
		dispHeight = view.getMeasuredHeight();

		float dispRatio = dispWidth / (float)dispHeight;

		float renderWidth = dispRatio > 1 ? PixelScene.MIN_WIDTH_L : PixelScene.MIN_WIDTH_P;
		float renderHeight = dispRatio > 1 ? PixelScene.MIN_HEIGHT_L : PixelScene.MIN_HEIGHT_P;

		//force power saver in this case as all devices must run at at least 2x scale.
		if (dispWidth < renderWidth*2 || dispHeight < renderHeight*2)
			SPDSettings.put( SPDSettings.KEY_POWER_SAVER, true );

		if (SPDSettings.powerSaver()){

			int maxZoom = (int)Math.min(dispWidth/renderWidth, dispHeight/renderHeight);

			renderWidth *= Math.max( 2, Math.round(1f + maxZoom*0.4f));
			renderHeight *= Math.max( 2, Math.round(1f + maxZoom*0.4f));

			if (dispRatio > renderWidth / renderHeight){
				renderWidth = renderHeight * dispRatio;
			} else {
				renderHeight = renderWidth / dispRatio;
			}

			final int finalW = Math.round(renderWidth);
			final int finalH = Math.round(renderHeight);
			if (finalW != width || finalH != height){

				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						view.getHolder().setFixedSize(finalW, finalH);
					}
				});

			}
		} else {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					view.getHolder().setSizeFromLayout();
				}
			});
		}
	}

	public static void updateSystemUI() {

		boolean fullscreen = Build.VERSION.SDK_INT < Build.VERSION_CODES.N
								|| !instance.isInMultiWindowMode();

		if (fullscreen){
			instance.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		} else {
			instance.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		}

		if (DeviceCompat.supportsFullScreen()){
			if (fullscreen && SPDSettings.fullscreen()) {
				instance.getWindow().getDecorView().setSystemUiVisibility(
						View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
						View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
						View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
						View.SYSTEM_UI_FLAG_HIDE_NAVIGATION );
			} else {
				instance.getWindow().getDecorView().setSystemUiVisibility(
						View.SYSTEM_UI_FLAG_LAYOUT_STABLE );
			}
		}

	}
	
}