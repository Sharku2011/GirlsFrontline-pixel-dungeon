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

package com.gfpixel.gfpixeldungeon.journal;

import com.gfpixel.gfpixeldungeon.Badges;
import com.gfpixel.gfpixeldungeon.items.Item;
import com.gfpixel.gfpixeldungeon.items.armor.ClothArmor;
import com.gfpixel.gfpixeldungeon.items.armor.HuntressArmor;
import com.gfpixel.gfpixeldungeon.items.armor.LeatherArmor;
import com.gfpixel.gfpixeldungeon.items.armor.MageArmor;
import com.gfpixel.gfpixeldungeon.items.armor.MailArmor;
import com.gfpixel.gfpixeldungeon.items.armor.PlateArmor;
import com.gfpixel.gfpixeldungeon.items.armor.RogueArmor;
import com.gfpixel.gfpixeldungeon.items.armor.ScaleArmor;
import com.gfpixel.gfpixeldungeon.items.armor.WarriorArmor;
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
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Dragunov;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.C96;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Gepard;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Lar;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.M9;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.M99;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.SRS;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Thunder;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Welrod;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Dp;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.AK47;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.G11;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.G36;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.GROZA;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.SaigaPlate;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.SakuraBlade;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Usas12;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.GUA91;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Hk416;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Kar98;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Kriss;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Ks23;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.M16;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.M1903;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.M1911;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.M1a1;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Mg42;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Mos;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.NAGANT;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.NagantRevolver;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Negev;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Ntw20;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.SAIGA;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.SR3;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Sass;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Ump40;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Ump45;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Wa;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Win97;
import com.gfpixel.gfpixeldungeon.items.weapon.missiles.Boomerang;
import com.watabou.utils.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

public enum Catalog {
	
	WEAPONS,
	ARMOR,
	WANDS,
	RINGS,
	ARTIFACTS,
	POTIONS,
	SCROLLS;
	
	private LinkedHashMap<Class<? extends Item>, Boolean> seen = new LinkedHashMap<>();
	
	public Collection<Class<? extends Item>> items(){
		return seen.keySet();
	}
	
	public boolean allSeen(){
		for (Class<?extends Item> item : items()){
			if (!seen.get(item)){
				return false;
			}
		}
		return true;
	}
	
	static {
		WEAPONS.seen.put( M9.class,                   false);
		WEAPONS.seen.put( Welrod.class,                     false);
		WEAPONS.seen.put( G11.class,                 false);
		WEAPONS.seen.put( Ump45.class,                   false);
		WEAPONS.seen.put( Ump40.class,                   false);
		WEAPONS.seen.put( Dp.class,                   false);
		WEAPONS.seen.put( SR3.class,                   false);
		WEAPONS.seen.put( SRS.class,                   false);
		WEAPONS.seen.put( Thunder.class,				false);
		WEAPONS.seen.put( Boomerang.class,                  false);
		WEAPONS.seen.put( M16.class,                 false);
		WEAPONS.seen.put( M1911.class,                    false);
		WEAPONS.seen.put( M1903.class,                      false);
		WEAPONS.seen.put( M1a1.class,               false);
		WEAPONS.seen.put( NagantRevolver.class,        false);
		WEAPONS.seen.put( G36.class,                   false);
		WEAPONS.seen.put( Ks23.class,                      false);
		WEAPONS.seen.put( Kar98.class,                       false);
		WEAPONS.seen.put( Negev.class,                   false);
		WEAPONS.seen.put( Mos.class,                        false);
		WEAPONS.seen.put( Kriss.class,                      false);
		WEAPONS.seen.put( Wa.class,                         false);
		WEAPONS.seen.put( C96.class,                        false);
		WEAPONS.seen.put( Win97.class,                      false);
		WEAPONS.seen.put( Dragunov.class,                  false);
		WEAPONS.seen.put( AK47.class,                      false);
		WEAPONS.seen.put( Hk416.class,                      false);
		WEAPONS.seen.put( GUA91.class,             false);
		WEAPONS.seen.put( AWP.class,                        false);
		WEAPONS.seen.put( Gepard.class,                   false);
		WEAPONS.seen.put( Usas12.class,                 false);
		WEAPONS.seen.put( Sass.class,                       false);
		WEAPONS.seen.put( M99.class,                     false);
		WEAPONS.seen.put( SakuraBlade.class,                   false);
		WEAPONS.seen.put( SaigaPlate.class,                false);
		WEAPONS.seen.put( Lar.class,         			       false);
		WEAPONS.seen.put( SAIGA.class,                        false);
		WEAPONS.seen.put( Mg42.class,                     false);
		WEAPONS.seen.put( Ntw20.class,                   false);
		WEAPONS.seen.put( GROZA.class,                false);
	
		ARMOR.seen.put( ClothArmor.class,                   false);
		ARMOR.seen.put( LeatherArmor.class,                 false);
		ARMOR.seen.put( MailArmor.class,                    false);
		ARMOR.seen.put( ScaleArmor.class,                   false);
		ARMOR.seen.put( PlateArmor.class,                   false);
		ARMOR.seen.put( WarriorArmor.class,                 false);
		ARMOR.seen.put( MageArmor.class,                    false);
		ARMOR.seen.put( RogueArmor.class,                   false);
		ARMOR.seen.put( HuntressArmor.class,                false);
	
		WANDS.seen.put( WandOfMagicMissile.class,           false);
		WANDS.seen.put( WandOfLightning.class,              false);
		WANDS.seen.put( WandOfDisintegration.class,         false);
		WANDS.seen.put( WandOfFireblast.class,              false);
		WANDS.seen.put( WandOfCorrosion.class,              false);
		WANDS.seen.put( WandOfBlastWave.class,              false);
		//WANDS.seen.put( WandOfLivingEarth.class,          false);
		WANDS.seen.put( WandOfFrost.class,                  false);
		WANDS.seen.put( WandOfPrismaticLight.class,         false);
		//WANDS.seen.put( WandOfWarding.class,              false);
		WANDS.seen.put( WandOfTransfusion.class,            false);
		WANDS.seen.put( WandOfCorruption.class,             false);
		WANDS.seen.put( WandOfRegrowth.class,               false);
	
		RINGS.seen.put( RingOfAccuracy.class,               false);
		RINGS.seen.put( RingOfEnergy.class,                 false);
		RINGS.seen.put( RingOfElements.class,               false);
		RINGS.seen.put( RingOfEvasion.class,                false);
		RINGS.seen.put( RingOfForce.class,                  false);
		RINGS.seen.put( RingOfFuror.class,                  false);
		RINGS.seen.put( RingOfHaste.class,                  false);
		RINGS.seen.put( RingOfMight.class,                  false);
		RINGS.seen.put( RingOfSharpshooting.class,          false);
		RINGS.seen.put( RingOfTenacity.class,               false);
		RINGS.seen.put( RingOfWealth.class,                 false);
	
		//ARTIFACTS.seen.put( AlchemistsToolkit.class,      false);
		ARTIFACTS.seen.put( CapeOfThorns.class,             false);
		ARTIFACTS.seen.put( ChaliceOfBlood.class,           false);
		ARTIFACTS.seen.put( CloakOfShadows.class,           false);
		ARTIFACTS.seen.put( DriedRose.class,                false);
		ARTIFACTS.seen.put( EtherealChains.class,           false);
		ARTIFACTS.seen.put( HornOfPlenty.class,             false);
		ARTIFACTS.seen.put( LloydsBeacon.class,             false);
		ARTIFACTS.seen.put( MasterThievesArmband.class,     false);
		ARTIFACTS.seen.put( SandalsOfNature.class,          false);
		ARTIFACTS.seen.put( TalismanOfForesight.class,      false);
		ARTIFACTS.seen.put( TimekeepersHourglass.class,     false);
		ARTIFACTS.seen.put( UnstableSpellbook.class,        false);
	
		POTIONS.seen.put( PotionOfHealing.class,            false);
		POTIONS.seen.put( PotionOfStrength.class,           false);
		POTIONS.seen.put( PotionOfLiquidFlame.class,        false);
		POTIONS.seen.put( PotionOfFrost.class,              false);
		POTIONS.seen.put( PotionOfToxicGas.class,           false);
		POTIONS.seen.put( PotionOfParalyticGas.class,       false);
		POTIONS.seen.put( PotionOfPurity.class,             false);
		POTIONS.seen.put( PotionOfLevitation.class,         false);
		POTIONS.seen.put( PotionOfMindVision.class,         false);
		POTIONS.seen.put( PotionOfInvisibility.class,       false);
		POTIONS.seen.put( PotionOfExperience.class,         false);
		POTIONS.seen.put( PotionOfMight.class,              false);
	
		SCROLLS.seen.put( ScrollOfIdentify.class,           false);
		SCROLLS.seen.put( ScrollOfUpgrade.class,            false);
		SCROLLS.seen.put( ScrollOfRemoveCurse.class,        false);
		SCROLLS.seen.put( ScrollOfMagicMapping.class,       false);
		SCROLLS.seen.put( ScrollOfTeleportation.class,      false);
		SCROLLS.seen.put( ScrollOfRecharging.class,         false);
		SCROLLS.seen.put( ScrollOfMirrorImage.class,        false);
		SCROLLS.seen.put( ScrollOfTerror.class,             false);
		SCROLLS.seen.put( ScrollOfLullaby.class,            false);
		SCROLLS.seen.put( ScrollOfRage.class,               false);
		SCROLLS.seen.put( ScrollOfPsionicBlast.class,       false);
		SCROLLS.seen.put( ScrollOfMagicalInfusion.class,    false);
	}
	
	public static LinkedHashMap<Catalog, Badges.Badge> catalogBadges = new LinkedHashMap<>();
	static {
		catalogBadges.put(WEAPONS, Badges.Badge.ALL_WEAPONS_IDENTIFIED);
		catalogBadges.put(ARMOR, Badges.Badge.ALL_ARMOR_IDENTIFIED);
		catalogBadges.put(WANDS, Badges.Badge.ALL_WANDS_IDENTIFIED);
		catalogBadges.put(RINGS, Badges.Badge.ALL_RINGS_IDENTIFIED);
		catalogBadges.put(ARTIFACTS, Badges.Badge.ALL_ARTIFACTS_IDENTIFIED);
		catalogBadges.put(POTIONS, Badges.Badge.ALL_POTIONS_IDENTIFIED);
		catalogBadges.put(SCROLLS, Badges.Badge.ALL_SCROLLS_IDENTIFIED);
	}
	
	public static boolean isSeen(Class<? extends Item> itemClass){
		for (Catalog cat : values()) {
			if (cat.seen.containsKey(itemClass)) {
				return cat.seen.get(itemClass);
			}
		}
		return false;
	}
	
	public static void setSeen(Class<? extends Item> itemClass){
		for (Catalog cat : values()) {
			if (cat.seen.containsKey(itemClass) && !cat.seen.get(itemClass)) {
				cat.seen.put(itemClass, true);
				Journal.saveNeeded = true;
			}
		}
		Badges.validateItemsIdentified();
	}
	
	private static final String CATALOGS = "catalogs";
	
	public static void store( Bundle bundle ){
		
		Badges.loadGlobal();
		
		ArrayList<String> seen = new ArrayList<>();
		
		//if we have identified all items of a set, we use the badge to keep track instead.
		if (!Badges.isUnlocked(Badges.Badge.ALL_ITEMS_IDENTIFIED)) {
			for (Catalog cat : values()) {
				if (!Badges.isUnlocked(catalogBadges.get(cat))) {
					for (Class<? extends Item> item : cat.items()) {
						if (cat.seen.get(item)) seen.add(item.getSimpleName());
					}
				}
			}
		}
		
		bundle.put( CATALOGS, seen.toArray(new String[0]) );
		
	}
	
	public static void restore( Bundle bundle ){
		
		Badges.loadGlobal();
		
		//logic for if we have all badges
		if (Badges.isUnlocked(Badges.Badge.ALL_ITEMS_IDENTIFIED)){
			for ( Catalog cat : values()){
				for (Class<? extends Item> item : cat.items()){
					cat.seen.put(item, true);
				}
			}
			return;
		}
		
		//catalog-specific badge logic
		for (Catalog cat : values()){
			if (Badges.isUnlocked(catalogBadges.get(cat))){
				for (Class<? extends Item> item : cat.items()){
					cat.seen.put(item, true);
				}
			}
		}
		
		//general save/load
		if (bundle.contains(CATALOGS)) {
			List<String> seen = Arrays.asList(bundle.getStringArray(CATALOGS));
			
			//pre-0.6.3 saves
			//TODO should adjust this to tie into the bundling system's class array
			if (seen.contains("WandOfVenom")){
				WANDS.seen.put(WandOfCorrosion.class, true);
			}
			
			for (Catalog cat : values()) {
				for (Class<? extends Item> item : cat.items()) {
					if (seen.contains(item.getSimpleName())) {
						cat.seen.put(item, true);
					}
				}
			}
		}
	}
	
}
