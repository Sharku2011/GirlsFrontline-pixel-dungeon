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

package com.gfpixel.gfpixeldungeon.items;

import com.gfpixel.gfpixeldungeon.Dungeon;
import com.gfpixel.gfpixeldungeon.GirlsFrontlinePixelDungeon;
import com.gfpixel.gfpixeldungeon.items.armor.Armor;
import com.gfpixel.gfpixeldungeon.items.armor.ClothArmor;
import com.gfpixel.gfpixeldungeon.items.armor.LeatherArmor;
import com.gfpixel.gfpixeldungeon.items.armor.MailArmor;
import com.gfpixel.gfpixeldungeon.items.armor.PlateArmor;
import com.gfpixel.gfpixeldungeon.items.armor.ScaleArmor;
import com.gfpixel.gfpixeldungeon.items.artifacts.AlchemistsToolkit;
import com.gfpixel.gfpixeldungeon.items.artifacts.Artifact;
import com.gfpixel.gfpixeldungeon.items.artifacts.CapeOfThorns;
import com.gfpixel.gfpixeldungeon.items.artifacts.ChaliceOfBlood;
import com.gfpixel.gfpixeldungeon.items.artifacts.CloakOfShadows;
import com.gfpixel.gfpixeldungeon.items.artifacts.DriedRose;
import com.gfpixel.gfpixeldungeon.items.artifacts.EtherealChains;
import com.gfpixel.gfpixeldungeon.items.artifacts.HornOfPlenty;
import com.gfpixel.gfpixeldungeon.items.artifacts.LloydsBeacon;
import com.gfpixel.gfpixeldungeon.items.artifacts.MasterThievesArmband;
import com.gfpixel.gfpixeldungeon.items.artifacts.SandalsOfNature;
import com.gfpixel.gfpixeldungeon.items.artifacts.TalismanOfForesight;
import com.gfpixel.gfpixeldungeon.items.artifacts.TimekeepersHourglass;
import com.gfpixel.gfpixeldungeon.items.artifacts.UnstableSpellbook;
import com.gfpixel.gfpixeldungeon.items.bags.Bag;
import com.gfpixel.gfpixeldungeon.items.food.Food;
import com.gfpixel.gfpixeldungeon.items.food.Maccol;
import com.gfpixel.gfpixeldungeon.items.food.MysteryMeat;
import com.gfpixel.gfpixeldungeon.items.food.Pasty;
import com.gfpixel.gfpixeldungeon.items.potions.Potion;
import com.gfpixel.gfpixeldungeon.items.potions.PotionOfExperience;
import com.gfpixel.gfpixeldungeon.items.potions.PotionOfFrost;
import com.gfpixel.gfpixeldungeon.items.potions.PotionOfHealing;
import com.gfpixel.gfpixeldungeon.items.potions.PotionOfInvisibility;
import com.gfpixel.gfpixeldungeon.items.potions.PotionOfLevitation;
import com.gfpixel.gfpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.gfpixel.gfpixeldungeon.items.potions.PotionOfMight;
import com.gfpixel.gfpixeldungeon.items.potions.PotionOfMindVision;
import com.gfpixel.gfpixeldungeon.items.potions.PotionOfParalyticGas;
import com.gfpixel.gfpixeldungeon.items.potions.PotionOfPurity;
import com.gfpixel.gfpixeldungeon.items.potions.PotionOfStrength;
import com.gfpixel.gfpixeldungeon.items.potions.PotionOfToxicGas;
import com.gfpixel.gfpixeldungeon.items.rings.Ring;
import com.gfpixel.gfpixeldungeon.items.rings.RingOfAccuracy;
import com.gfpixel.gfpixeldungeon.items.rings.RingOfElements;
import com.gfpixel.gfpixeldungeon.items.rings.RingOfEnergy;
import com.gfpixel.gfpixeldungeon.items.rings.RingOfEvasion;
import com.gfpixel.gfpixeldungeon.items.rings.RingOfForce;
import com.gfpixel.gfpixeldungeon.items.rings.RingOfFuror;
import com.gfpixel.gfpixeldungeon.items.rings.RingOfHaste;
import com.gfpixel.gfpixeldungeon.items.rings.RingOfMight;
import com.gfpixel.gfpixeldungeon.items.rings.RingOfSharpshooting;
import com.gfpixel.gfpixeldungeon.items.rings.RingOfTenacity;
import com.gfpixel.gfpixeldungeon.items.rings.RingOfWealth;
import com.gfpixel.gfpixeldungeon.items.scrolls.Scroll;
import com.gfpixel.gfpixeldungeon.items.scrolls.ScrollOfIdentify;
import com.gfpixel.gfpixeldungeon.items.scrolls.ScrollOfLullaby;
import com.gfpixel.gfpixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.gfpixel.gfpixeldungeon.items.scrolls.ScrollOfMagicalInfusion;
import com.gfpixel.gfpixeldungeon.items.scrolls.ScrollOfMirrorImage;
import com.gfpixel.gfpixeldungeon.items.scrolls.ScrollOfPsionicBlast;
import com.gfpixel.gfpixeldungeon.items.scrolls.ScrollOfRage;
import com.gfpixel.gfpixeldungeon.items.scrolls.ScrollOfRecharging;
import com.gfpixel.gfpixeldungeon.items.scrolls.ScrollOfRemoveCurse;
import com.gfpixel.gfpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.gfpixel.gfpixeldungeon.items.scrolls.ScrollOfTerror;
import com.gfpixel.gfpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.gfpixel.gfpixeldungeon.items.wands.Wand;
import com.gfpixel.gfpixeldungeon.items.wands.WandOfBlastWave;
import com.gfpixel.gfpixeldungeon.items.wands.WandOfCorrosion;
import com.gfpixel.gfpixeldungeon.items.wands.WandOfCorruption;
import com.gfpixel.gfpixeldungeon.items.wands.WandOfDisintegration;
import com.gfpixel.gfpixeldungeon.items.wands.WandOfFireblast;
import com.gfpixel.gfpixeldungeon.items.wands.WandOfFrost;
import com.gfpixel.gfpixeldungeon.items.wands.WandOfLightning;
import com.gfpixel.gfpixeldungeon.items.wands.WandOfMagicMissile;
import com.gfpixel.gfpixeldungeon.items.wands.WandOfPrismaticLight;
import com.gfpixel.gfpixeldungeon.items.wands.WandOfRegrowth;
import com.gfpixel.gfpixeldungeon.items.wands.WandOfTransfusion;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.AWP;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.AssassinsBlade;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.BattleAxe;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.C96;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Crossbow;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Dagger;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Dirk;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Dp;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Flail;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.G11;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.G36;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.GROZA;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Gauntlet;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Glaive;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Greataxe;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Greatshield;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Greatsword;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Hk416;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Kar98;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Knuckles;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Kriss;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Ks23;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Lar;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.M16;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.M1903;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.M1911;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.M1a1;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Mg42;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Mos;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.NAGANT;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Negev;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Ntw20;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.SAIGA;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.SR3;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Sass;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Ump40;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Ump45;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Wa;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Win97;
import com.gfpixel.gfpixeldungeon.items.weapon.missiles.Bolas;
import com.gfpixel.gfpixeldungeon.items.weapon.missiles.FishingSpear;
import com.gfpixel.gfpixeldungeon.items.weapon.missiles.Javelin;
import com.gfpixel.gfpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.gfpixel.gfpixeldungeon.items.weapon.missiles.Shuriken;
import com.gfpixel.gfpixeldungeon.items.weapon.missiles.ThrowingHammer;
import com.gfpixel.gfpixeldungeon.items.weapon.missiles.ThrowingKnife;
import com.gfpixel.gfpixeldungeon.items.weapon.missiles.ThrowingSpear;
import com.gfpixel.gfpixeldungeon.items.weapon.missiles.Tomahawk;
import com.gfpixel.gfpixeldungeon.items.weapon.missiles.Trident;
import com.gfpixel.gfpixeldungeon.items.weapon.missiles.darts.Dart;
import com.gfpixel.gfpixeldungeon.plants.BlandfruitBush;
import com.gfpixel.gfpixeldungeon.plants.Blindweed;
import com.gfpixel.gfpixeldungeon.plants.Dreamfoil;
import com.gfpixel.gfpixeldungeon.plants.Earthroot;
import com.gfpixel.gfpixeldungeon.plants.Fadeleaf;
import com.gfpixel.gfpixeldungeon.plants.Firebloom;
import com.gfpixel.gfpixeldungeon.plants.Icecap;
import com.gfpixel.gfpixeldungeon.plants.Plant;
import com.gfpixel.gfpixeldungeon.plants.Rotberry;
import com.gfpixel.gfpixeldungeon.plants.Sorrowmoss;
import com.gfpixel.gfpixeldungeon.plants.Starflower;
import com.gfpixel.gfpixeldungeon.plants.Stormvine;
import com.gfpixel.gfpixeldungeon.plants.Sungrass;
import com.watabou.utils.Bundle;
import com.watabou.utils.GameMath;
import com.watabou.utils.Holidays;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Generator {

	public enum Category {
		WEAPON	( 6,    MeleeWeapon.class),
		WEP_T1	( 0,    MeleeWeapon.class),
		WEP_T2	( 0,    MeleeWeapon.class),
		WEP_T3	( 0,    MeleeWeapon.class),
		WEP_T4	( 0,    MeleeWeapon.class),
		WEP_T5	( 0,    MeleeWeapon.class),
		WEP_T6	( 0,	MeleeWeapon.class),
		
		ARMOR	( 4,    Armor.class ),
		
		MISSILE ( 3,    MissileWeapon.class ),
		MIS_T1  ( 0,    MissileWeapon.class ),
		MIS_T2  ( 0,    MissileWeapon.class ),
		MIS_T3  ( 0,    MissileWeapon.class ),
		MIS_T4  ( 0,    MissileWeapon.class ),
		MIS_T5  ( 0,    MissileWeapon.class ),
		
		POTION	( 20,   Potion.class ),
		SCROLL	( 20,   Scroll.class ),
		
		WAND	( 3,    Wand.class ),
		RING	( 1,    Ring.class ),
		ARTIFACT( 1,    Artifact.class),
		
		SEED	( 0,    Plant.Seed.class ),
		
		FOOD	( 0,    Food.class ),
		
		GOLD	( 20,   Gold.class );
		
		public Class<?>[] classes;
		public float[] probs;
		
		public float prob;
		public Class<? extends Item> superClass;

		public static final Calendar calendar = Calendar.getInstance();
		
		private Category( float prob, Class<? extends Item> superClass ) {
			this.prob = prob;
			this.superClass = superClass;
		}
		
		public static int order( Item item ) {
			for (int i=0; i < values().length; i++) {
				if (values()[i].superClass.isInstance( item )) {
					return i;
				}
			}
			
			return item instanceof Bag ? Integer.MAX_VALUE : Integer.MAX_VALUE - 1;
		}
		
		private static final float[] INITIAL_ARTIFACT_PROBS = new float[]{ 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1};
		
		static {
			GOLD.classes = new Class<?>[]{
					Gold.class };
			GOLD.probs = new float[]{ 1 };
			
			SCROLL.classes = new Class<?>[]{
					ScrollOfIdentify.class,
					ScrollOfTeleportation.class,
					ScrollOfRemoveCurse.class,
					ScrollOfUpgrade.class,
					ScrollOfRecharging.class,
					ScrollOfMagicMapping.class,
					ScrollOfRage.class,
					ScrollOfTerror.class,
					ScrollOfLullaby.class,
					ScrollOfMagicalInfusion.class,
					ScrollOfPsionicBlast.class,
					ScrollOfMirrorImage.class };
			SCROLL.probs = new float[]{ 30, 10, 20, 0, 15, 15, 12, 8, 8, 0, 4, 10 };
			
			POTION.classes = new Class<?>[]{
					PotionOfHealing.class,
					PotionOfExperience.class,
					PotionOfToxicGas.class,
					PotionOfParalyticGas.class,
					PotionOfLiquidFlame.class,
					PotionOfLevitation.class,
					PotionOfStrength.class,
					PotionOfMindVision.class,
					PotionOfPurity.class,
					PotionOfInvisibility.class,
					PotionOfMight.class,
					PotionOfFrost.class };
			POTION.probs = new float[]{ 45, 4, 15, 10, 15, 10, 0, 20, 12, 10, 0, 10 };
			
			//TODO: add last ones when implemented
			WAND.classes = new Class<?>[]{
					WandOfMagicMissile.class,
					WandOfLightning.class,
					WandOfDisintegration.class,
					WandOfFireblast.class,
					WandOfCorrosion.class,
					WandOfBlastWave.class,
					//WandOfLivingEarth.class,
					WandOfFrost.class,
					WandOfPrismaticLight.class,
					//WandOfWarding.class,
					WandOfTransfusion.class,
					WandOfCorruption.class,
					WandOfRegrowth.class };
			WAND.probs = new float[]{ 5, 4, 4, 4, 4, 3, /*3,*/ 3, 3, /*3,*/ 3, 3, 3 };
			
			//see generator.randomWeapon
			WEAPON.classes = new Class<?>[]{};
			WEAPON.probs = new float[]{};
			
			WEP_T1.classes = new Class<?>[]{
					Ump45.class,
					Ump40.class,
					Dp.class,
					SR3.class,
					Knuckles.class,
					Dagger.class,
					G11.class
			};
			WEP_T1.probs = new float[]{ 1, 1, 1, 1, 1, 1, 0 };
			
			WEP_T2.classes = new Class<?>[]{
					M16.class,
					M1911.class,
					M1903.class,
					M1a1.class,
					Dirk.class,
					G36.class
			};
			WEP_T2.probs = new float[]{ 6, 5, 5, 4, 4, 2 };
			
			WEP_T3.classes = new Class<?>[]{
					Ks23.class,
					Kar98.class,
					Negev.class,
					Mos.class,
					Kriss.class,
					Wa.class,
					C96.class
			};
			WEP_T3.probs = new float[]{ 6, 5, 5, 4, 4, 4, 2 };
			
			WEP_T4.classes = new Class<?>[]{
					Win97.class,
					BattleAxe.class,
					Flail.class,
					Hk416.class,
					AssassinsBlade.class,
					Crossbow.class,
					AWP.class
			};
			WEP_T4.probs = new float[]{ 6, 5, 5, 4, 4, 4, 4 };
			
			WEP_T5.classes = new Class<?>[]{
					Greatsword.class,
					Sass.class,
					Glaive.class,
					Greataxe.class,
					Greatshield.class,
					Lar.class
			};
			WEP_T5.probs = new float[]{ 6, 5, 5, 4, 4, 4 };

			WEP_T6.classes = new Class<?>[]{
					SAIGA.class,
					Ntw20.class,
					Mg42.class,
					GROZA.class,
					NAGANT.class
			};
			WEP_T6.probs = new float[]{ 2, 3, 2, 2, 1 };
			
			//see Generator.randomArmor
			ARMOR.classes = new Class<?>[]{
					ClothArmor.class,
					LeatherArmor.class,
					MailArmor.class,
					ScaleArmor.class,
					PlateArmor.class };
			ARMOR.probs = new float[]{ 0, 0, 0, 0, 0 };
			
			//see Generator.randomMissile
			MISSILE.classes = new Class<?>[]{};
			MISSILE.probs = new float[]{};
			
			MIS_T1.classes = new Class<?>[]{
					Dart.class,
					ThrowingKnife.class
			};
			MIS_T1.probs = new float[]{ 1, 1 };
			
			MIS_T2.classes = new Class<?>[]{
					FishingSpear.class,
					Shuriken.class
			};
			MIS_T2.probs = new float[]{ 4, 3 };
			
			MIS_T3.classes = new Class<?>[]{
					ThrowingSpear.class,
					Bolas.class
			};
			MIS_T3.probs = new float[]{ 4, 3 };
			
			MIS_T4.classes = new Class<?>[]{
					Javelin.class,
					Tomahawk.class
			};
			MIS_T4.probs = new float[]{ 4, 3 };
			
			MIS_T5.classes = new Class<?>[]{
					Trident.class,
					ThrowingHammer.class
			};
			MIS_T5.probs = new float[]{ 4, 3 };
			
			FOOD.classes = new Class<?>[]{
					Food.class,
					Maccol.class,
					Pasty.class,
					MysteryMeat.class };
			FOOD.probs = ( Holidays.getHolidays() == Holidays.Holiday.BREAD_INDEPENDENT ) ? new float[] { 0, 0, 1, 0 } : new float[]{ 4, 1, 1, 0 };
			
			RING.classes = new Class<?>[]{
					RingOfAccuracy.class,
					RingOfEvasion.class,
					RingOfElements.class,
					RingOfForce.class,
					RingOfFuror.class,
					RingOfHaste.class,
					RingOfEnergy.class,
					RingOfMight.class,
					RingOfSharpshooting.class,
					RingOfTenacity.class,
					RingOfWealth.class};
			RING.probs = new float[]{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
			
			ARTIFACT.classes = new Class<?>[]{
					CapeOfThorns.class,
					ChaliceOfBlood.class,
					CloakOfShadows.class,
					HornOfPlenty.class,
					MasterThievesArmband.class,
					SandalsOfNature.class,
					TalismanOfForesight.class,
					TimekeepersHourglass.class,
					UnstableSpellbook.class,
					AlchemistsToolkit.class, //currently removed from drop tables, pending rework.
					DriedRose.class,
					LloydsBeacon.class,
					EtherealChains.class
			};
			ARTIFACT.probs = INITIAL_ARTIFACT_PROBS.clone();
			
			SEED.classes = new Class<?>[]{
					Firebloom.Seed.class,
					Icecap.Seed.class,
					Sorrowmoss.Seed.class,
					Blindweed.Seed.class,
					Sungrass.Seed.class,
					Earthroot.Seed.class,
					Fadeleaf.Seed.class,
					Rotberry.Seed.class,
					BlandfruitBush.Seed.class,
					Dreamfoil.Seed.class,
					Stormvine.Seed.class,
					Starflower.Seed.class};
			SEED.probs = new float[]{ 10, 10, 10, 10, 10, 10, 10, 0, 2, 10, 10, 1 };
		}
	}

	// Table of weapon spawn probability by every tier
	private static final float[][] floorSetWeaponTierProbs = new float[][] {
			{0, 63, 20, 12,  2,  3},
			{0, 25, 54, 12,  5,  4},
			{0, 10, 35, 40,  7,  5},
			{0,  5, 30, 33, 22,  10},
			{0,  2,  8, 35, 30, 15},
			{0,  2,  8, 20, 40, 30},
	};

	// Table of armor spawn probability by every tier
	private static final float[][] floorSetArmorTierProbs = new float[][] {
			{0, 65, 20, 10, 5 },
			{0, 25, 50, 15, 10},
			{0, 12, 33, 40, 15},
			{0,  5, 15, 60, 20},
			{0,  2,  8, 40, 50},
			{0,  0,  5, 15, 80}
	};

	private static final float[][] floorSetMissileTierProbs = new float[][] {
			{0, 65, 20, 10, 5 },
			{0, 25, 50, 15, 10},
			{0, 12, 33, 40, 15},
			{0,  5, 15, 60, 20},
			{0,  2,  8, 40, 50},
			{0,  0,  5, 15, 80}
	};
	
	private static HashMap<Category,Float> categoryProbs = new LinkedHashMap<>();
	
	public static void reset() {
		for (Category cat : Category.values()) {
			categoryProbs.put( cat, cat.prob );
		}
	}
	
	public static Item random() {
		Category cat = Random.chances( categoryProbs );
		if (cat == null){
			reset();
			cat = Random.chances( categoryProbs );
		}
		categoryProbs.put( cat, categoryProbs.get( cat ) - 1);
		return random( cat );
	}
	
	public static Item random( Category cat ) {
		try {
			
			switch (cat) {
			case ARMOR:
				return randomArmor();
			case WEAPON:
				return randomWeapon();
			case MISSILE:
				return randomMissile();
			case ARTIFACT:
				Item item = randomArtifact();
				//if we're out of artifacts, return a ring instead.
				return item != null ? item : random(Category.RING);
			default:
				return ((Item)cat.classes[Random.chances( cat.probs )].newInstance()).random();
			}
			
		} catch (Exception e) {

			GirlsFrontlinePixelDungeon.reportException(e);
			return null;
			
		}
	}
	
	public static Item random( Class<? extends Item> cl ) {
		try {
			
			return ((Item)cl.newInstance()).random();
			
		} catch (Exception e) {

			GirlsFrontlinePixelDungeon.reportException(e);
			return null;
			
		}
	}

	public static Armor randomArmor(){
		return randomArmor(Dungeon.depth / 5);
	}
	
	public static Armor randomArmor(int floorSet) {

		floorSet = (int)GameMath.gate(0, floorSet, floorSetArmorTierProbs.length-1);

		try {
			Armor a = (Armor)Category.ARMOR.classes[Random.chances(floorSetArmorTierProbs[floorSet])].newInstance();
			a.random();
			return a;
		} catch (Exception e) {
			GirlsFrontlinePixelDungeon.reportException(e);
			return null;
		}
	}

	public static final Category[] wepTiers = new Category[]{
			Category.WEP_T1,
			Category.WEP_T2,
			Category.WEP_T3,
			Category.WEP_T4,
			Category.WEP_T5,
			Category.WEP_T6
	};

	public static MeleeWeapon randomWeapon(){
		return randomWeapon(Dungeon.depth / 5);
	}
	
	public static MeleeWeapon randomWeapon(int floorSet) {

		floorSet = (int)GameMath.gate(0, floorSet, floorSetWeaponTierProbs.length-1);

		try {
			Category c = wepTiers[Random.chances(floorSetWeaponTierProbs[floorSet])];
			MeleeWeapon w = (MeleeWeapon)c.classes[Random.chances(c.probs)].newInstance();
			w.random();
			return w;
		} catch (Exception e) {
			GirlsFrontlinePixelDungeon.reportException(e);
			return null;
		}
	}
	
	public static final Category[] misTiers = new Category[]{
			Category.MIS_T1,
			Category.MIS_T2,
			Category.MIS_T3,
			Category.MIS_T4,
			Category.MIS_T5
	};
	
	public static MissileWeapon randomMissile(){
		return randomMissile(Dungeon.depth / 5);
	}
	
	public static MissileWeapon randomMissile(int floorSet) {
		
		floorSet = (int)GameMath.gate(0, floorSet, floorSetMissileTierProbs.length-1);
		
		try {
			Category c = misTiers[Random.chances(floorSetMissileTierProbs[floorSet])];
			MissileWeapon w = (MissileWeapon)c.classes[Random.chances(c.probs)].newInstance();
			w.random();
			return w;
		} catch (Exception e) {
			GirlsFrontlinePixelDungeon.reportException(e);
			return null;
		}
	}

	//enforces uniqueness of artifacts throughout a run.
	public static Artifact randomArtifact() {

		try {
			Category cat = Category.ARTIFACT;
			int i = Random.chances( cat.probs );

			//if no artifacts are left, return null
			if (i == -1){
				return null;
			}
			
			Class<?extends Artifact> art = (Class<? extends Artifact>) cat.classes[i];

			if (removeArtifact(art)) {
				Artifact artifact = art.newInstance();
				
				artifact.random();
				
				return artifact;
			} else {
				return null;
			}

		} catch (Exception e) {
			GirlsFrontlinePixelDungeon.reportException(e);
			return null;
		}
	}

	public static boolean removeArtifact(Class<?extends Artifact> artifact) {
		if (spawnedArtifacts.contains(artifact))
			return false;

		Category cat = Category.ARTIFACT;
		for (int i = 0; i < cat.classes.length; i++)
			if (cat.classes[i].equals(artifact)) {
				if (cat.probs[i] == 1){
					cat.probs[i] = 0;
					spawnedArtifacts.add(artifact);
					return true;
				} else
					return false;
			}

		return false;
	}

	//resets artifact probabilities, for new dungeons
	public static void initArtifacts() {
		Category.ARTIFACT.probs = Category.INITIAL_ARTIFACT_PROBS.clone();
		spawnedArtifacts = new ArrayList<>();
	}

	private static ArrayList<Class<?extends Artifact>> spawnedArtifacts = new ArrayList<>();
	
	private static final String GENERAL_PROBS = "general_probs";
	private static final String SPAWNED_ARTIFACTS = "spawned_artifacts";
	
	public static void storeInBundle(Bundle bundle) {
		Float[] genProbs = categoryProbs.values().toArray(new Float[0]);
		float[] storeProbs = new float[genProbs.length];
		for (int i = 0; i < storeProbs.length; i++){
			storeProbs[i] = genProbs[i];
		}
		bundle.put( GENERAL_PROBS, storeProbs);
		
		bundle.put( SPAWNED_ARTIFACTS, spawnedArtifacts.toArray(new Class[0]));
	}

	public static void restoreFromBundle(Bundle bundle) {
		if (bundle.contains(GENERAL_PROBS)){
			float[] probs = bundle.getFloatArray(GENERAL_PROBS);
			for (int i = 0; i < probs.length; i++){
				categoryProbs.put(Category.values()[i], probs[i]);
			}
		} else {
			reset();
		}
		
		initArtifacts();
		if (bundle.contains(SPAWNED_ARTIFACTS)){
			for ( Class<?extends Artifact> artifact : bundle.getClassArray(SPAWNED_ARTIFACTS) ){
				removeArtifact(artifact);
			}
		//pre-0.6.1 saves
		} else if (bundle.contains("artifacts")) {
			String[] names = bundle.getStringArray("artifacts");
			Category cat = Category.ARTIFACT;

			for (String artifact : names)
				for (int i = 0; i < cat.classes.length; i++)
					if (cat.classes[i].getSimpleName().equals(artifact))
						cat.probs[i] = 0;
		}
	}
}
