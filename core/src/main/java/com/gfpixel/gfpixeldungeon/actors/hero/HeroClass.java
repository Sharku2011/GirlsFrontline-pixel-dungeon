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

package com.gfpixel.gfpixeldungeon.actors.hero;

import com.gfpixel.gfpixeldungeon.Assets;
import com.gfpixel.gfpixeldungeon.Badges;
import com.gfpixel.gfpixeldungeon.BuildConfig;
import com.gfpixel.gfpixeldungeon.Challenges;
import com.gfpixel.gfpixeldungeon.Dungeon;
import com.gfpixel.gfpixeldungeon.items.BrokenSeal;
import com.gfpixel.gfpixeldungeon.items.Item;
import com.gfpixel.gfpixeldungeon.items.armor.ClothArmor;
import com.gfpixel.gfpixeldungeon.items.armor.PlateArmor;
import com.gfpixel.gfpixeldungeon.items.artifacts.CloakOfShadows;
import com.gfpixel.gfpixeldungeon.items.artifacts.DriedRose;
import com.gfpixel.gfpixeldungeon.items.bags.MagicalHolster;
import com.gfpixel.gfpixeldungeon.items.bags.PotionBandolier;
import com.gfpixel.gfpixeldungeon.items.bags.ScrollHolder;
import com.gfpixel.gfpixeldungeon.items.bags.VelvetPouch;
import com.gfpixel.gfpixeldungeon.items.food.Food;
import com.gfpixel.gfpixeldungeon.items.food.Maccol;
import com.gfpixel.gfpixeldungeon.items.food.Pasty;
import com.gfpixel.gfpixeldungeon.items.food.SmallRation;
import com.gfpixel.gfpixeldungeon.items.potions.PotionOfHealing;
import com.gfpixel.gfpixeldungeon.items.potions.PotionOfInvisibility;
import com.gfpixel.gfpixeldungeon.items.potions.PotionOfMindVision;
import com.gfpixel.gfpixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.gfpixel.gfpixeldungeon.items.scrolls.ScrollOfRecharging;
import com.gfpixel.gfpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.gfpixel.gfpixeldungeon.items.wands.M79;
import com.gfpixel.gfpixeldungeon.items.wands.M84;
import com.gfpixel.gfpixeldungeon.items.wands.WandOfDisintegration;
import com.gfpixel.gfpixeldungeon.items.wands.WandOfMagicMissile;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Cannon;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Gepard;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.G11;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Kriss;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.M1903;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.M9;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.NAGANT;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Ntw20;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Traviae;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Ump45;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Welrod;
import com.gfpixel.gfpixeldungeon.items.weapon.missiles.Boomerang;
import com.gfpixel.gfpixeldungeon.items.weapon.missiles.ThrowingKnife;
import com.gfpixel.gfpixeldungeon.items.weapon.missiles.ThrowingStone;
import com.gfpixel.gfpixeldungeon.messages.Messages;
import com.watabou.utils.Bundle;
import com.watabou.utils.Holidays;


public enum HeroClass {

	NONE( "none", HeroSubClass.NONE ),
	WARRIOR( "warrior", HeroSubClass.BERSERKER, HeroSubClass.GLADIATOR ),
	MAGE( "mage", HeroSubClass.BATTLEMAGE, HeroSubClass.WARLOCK ),
	ROGUE( "rogue", HeroSubClass.ASSASSIN, HeroSubClass.FREERUNNER ),
	HUNTRESS( "huntress", HeroSubClass.WARDEN, HeroSubClass.SNIPER );

	private String title;
	private HeroSubClass[] subClasses;

	HeroClass( String title, HeroSubClass...subClasses ) {
		this.title = title;
		this.subClasses = subClasses;
	}

	public void initHero( Hero hero ) {

		hero.heroClass = this;

		initCommon( hero );

		switch (this) {
			case WARRIOR:
				initWarrior( hero );
				break;

			case MAGE:
				initMage( hero );
				break;

			case ROGUE:
				initRogue( hero );
				break;

			case HUNTRESS:
				initHuntress( hero );
				break;
		}
		
	}

	private static void initCommon( Hero hero ) {
		Item i = new ClothArmor().identify();
		if (!Challenges.isItemBlocked(i)) hero.belongings.armor = (ClothArmor)i;


		i = ( (Holidays.getHolidays() == Holidays.Holiday.BREAD_INDEPENDENT) ? new Pasty() : new Food() );
		if (!Challenges.isItemBlocked(i)) i.collect();

		if (Dungeon.isChallenged(Challenges.NO_FOOD)){
			new SmallRation().collect();
		}

		if (BuildConfig.DEBUG) {
            new Maccol().collect();
            new Pasty().collect();
        }


	}

	public Badges.Badge masteryBadge() {
		switch (this) {
			case WARRIOR:
				return Badges.Badge.MASTERY_WARRIOR;
			case MAGE:
				return Badges.Badge.MASTERY_MAGE;
			case ROGUE:
				return Badges.Badge.MASTERY_ROGUE;
			case HUNTRESS:
				return Badges.Badge.MASTERY_HUNTRESS;
		}
		return null;
	}

	private static void initWarrior( Hero hero ) {
		(hero.belongings.weapon = new Ump45()).identify();
		new PotionOfHealing().identify().collect();

		if (hero.belongings.armor != null){
			hero.belongings.armor.affixSeal(new BrokenSeal());
		}

		new ThrowingStone().identify().quantity(3).collect();

		new PotionBandolier().collect();
		Dungeon.LimitedDrops.POTION_BANDOLIER.drop();
		new PotionOfHealing().identify();

		if (BuildConfig.DEBUG) 	{
			new DriedRose().identify().collect();
			new Cannon().identify().collect();
			new Ntw20().identify().upgrade(60).collect();
			new WandOfDisintegration().identify().upgrade().collect();
			new PlateArmor().identify().upgrade(100).collect();
		}

	}

	private static void initMage( Hero hero ) {
		G11 staff;
		
		staff = new G11(new WandOfMagicMissile());
		new ScrollOfRecharging().identify().collect();

		(hero.belongings.weapon = staff).identify();
		hero.belongings.weapon.activate(hero);

		Dungeon.quickslot.setSlot(0, staff);

		new MagicalHolster().collect();
		Dungeon.LimitedDrops.MAGICAL_HOLSTER.drop();
		new ScrollOfUpgrade().identify();
	}

	private static void initRogue( Hero hero ) {
		(hero.belongings.weapon = new Welrod()).identify();

		CloakOfShadows cloak = new CloakOfShadows();
		(hero.belongings.misc1 = cloak).identify();
		hero.belongings.misc1.activate( hero );
		new PotionOfInvisibility().identify().collect();
		new Welrod().identify().upgrade(3).collect();

		ThrowingKnife knives = new ThrowingKnife();
		knives.quantity(3).collect();

		Dungeon.quickslot.setSlot(0, cloak);
		Dungeon.quickslot.setSlot(1, knives);

		new ScrollHolder().collect();
		Dungeon.LimitedDrops.SCROLL_HOLDER.drop();
		new ScrollOfMagicMapping().identify();

		if (BuildConfig.DEBUG) 	{
			new Ntw20().identify().collect();
			new Traviae().identify().collect();
		}
	}

	private static void initHuntress( Hero hero ) {

		(hero.belongings.weapon = new M9()).identify();
		Boomerang boomerang = new Boomerang();
		boomerang.identify().collect();
		M79 m79 = new M79();
		m79.identify().collect();


		Dungeon.quickslot.setSlot(0, boomerang);
		Dungeon.quickslot.setSlot(2, m79);

		new VelvetPouch().collect();
		Dungeon.LimitedDrops.VELVET_POUCH.drop();
		new PotionOfMindVision().identify();
	}

	public String title() {
		return Messages.get(HeroClass.class, title);
	}
	
	public HeroSubClass[] subClasses() {
		return subClasses;
	}
	
	public String spritesheet() {
		
		switch (this) {
		case WARRIOR:
			return Assets.WARRIOR;
		case MAGE:
			return Assets.MAGE;
		case ROGUE:
			return Assets.ROGUE;
		case HUNTRESS:
			return Assets.RANGER;
		}
		
		return null;
	}
	
	public String[] perks() {
		
		switch (this) {
		case WARRIOR:
			return new String[]{
					Messages.get(HeroClass.class, "warrior_perk1"),
					Messages.get(HeroClass.class, "warrior_perk2"),
					Messages.get(HeroClass.class, "warrior_perk3"),
					Messages.get(HeroClass.class, "warrior_perk4"),
					Messages.get(HeroClass.class, "warrior_perk5"),
			};
		case MAGE:
			return new String[]{
					Messages.get(HeroClass.class, "mage_perk1"),
					Messages.get(HeroClass.class, "mage_perk2"),
					Messages.get(HeroClass.class, "mage_perk3"),
					Messages.get(HeroClass.class, "mage_perk4"),
					Messages.get(HeroClass.class, "mage_perk5"),
			};
		case ROGUE:
			return new String[]{
					Messages.get(HeroClass.class, "rogue_perk1"),
					Messages.get(HeroClass.class, "rogue_perk2"),
					Messages.get(HeroClass.class, "rogue_perk3"),
					Messages.get(HeroClass.class, "rogue_perk4"),
					Messages.get(HeroClass.class, "rogue_perk5"),
			};
		case HUNTRESS:
			return new String[]{
					Messages.get(HeroClass.class, "huntress_perk1"),
					Messages.get(HeroClass.class, "huntress_perk2"),
					Messages.get(HeroClass.class, "huntress_perk3"),
					Messages.get(HeroClass.class, "huntress_perk4"),
					Messages.get(HeroClass.class, "huntress_perk5"),
			};
		}
		
		return null;
	}

	private static final String CLASS	= "class";
	
	public void storeInBundle( Bundle bundle ) {
		bundle.put( CLASS, toString() );
	}
	
	public static HeroClass restoreInBundle( Bundle bundle ) {
		String value = bundle.getString( CLASS );
		return value.length() > 0 ? valueOf( value ) : ROGUE;
	}
}
