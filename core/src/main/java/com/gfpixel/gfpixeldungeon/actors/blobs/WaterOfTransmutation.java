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

package com.gfpixel.gfpixeldungeon.actors.blobs;

import com.gfpixel.gfpixeldungeon.Challenges;
import com.gfpixel.gfpixeldungeon.GirlsFrontlinePixelDungeon;
import com.gfpixel.gfpixeldungeon.actors.hero.Hero;
import com.gfpixel.gfpixeldungeon.effects.BlobEmitter;
import com.gfpixel.gfpixeldungeon.effects.Speck;
import com.gfpixel.gfpixeldungeon.items.Generator;
import com.gfpixel.gfpixeldungeon.items.Generator.Category;
import com.gfpixel.gfpixeldungeon.items.Item;
import com.gfpixel.gfpixeldungeon.items.artifacts.Artifact;
import com.gfpixel.gfpixeldungeon.items.potions.Potion;
import com.gfpixel.gfpixeldungeon.items.potions.PotionOfMight;
import com.gfpixel.gfpixeldungeon.items.potions.PotionOfStrength;
import com.gfpixel.gfpixeldungeon.items.rings.Ring;
import com.gfpixel.gfpixeldungeon.items.scrolls.Scroll;
import com.gfpixel.gfpixeldungeon.items.scrolls.ScrollOfMagicalInfusion;
import com.gfpixel.gfpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.gfpixel.gfpixeldungeon.items.wands.Wand;
import com.gfpixel.gfpixeldungeon.items.weapon.Weapon;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.G11;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.gfpixel.gfpixeldungeon.journal.Catalog;
import com.gfpixel.gfpixeldungeon.journal.Notes.Landmark;
import com.gfpixel.gfpixeldungeon.messages.Messages;
import com.gfpixel.gfpixeldungeon.plants.Plant;
import com.watabou.utils.Random;

public class WaterOfTransmutation extends WellWater {
	
	@Override
	protected Item affectItem( Item item ) {
		
		if (item instanceof G11) {
			item = changeStaff( (G11) item );
		} else if (item instanceof MeleeWeapon) {
			item = changeWeapon( (MeleeWeapon)item );
		} else if (item instanceof Scroll) {
			item = changeScroll( (Scroll)item );
		} else if (item instanceof Potion) {
			item = changePotion( (Potion)item );
		} else if (item instanceof Ring) {
			item = changeRing( (Ring)item );
		} else if (item instanceof Wand) {
			item = changeWand( (Wand)item );
		} else if (item instanceof Plant.Seed) {
			item = changeSeed( (Plant.Seed)item );
		} else if (item instanceof Artifact) {
			item = changeArtifact( (Artifact)item );
		} else {
			item = null;
		}
		
		//incase a never-seen item pops out
		if (item != null&& item.isIdentified()){
			Catalog.setSeen(item.getClass());
		}

		return item;

	}
	
	@Override
	protected boolean affectHero(Hero hero) {
		return false;
	}
	
	@Override
	public void use( BlobEmitter emitter ) {
		super.use( emitter );
		emitter.start( Speck.factory( Speck.CHANGE ), 0.2f, 0 );
	}
	
	@Override
	protected Landmark record() {
		return Landmark.WELL_OF_TRANSMUTATION;
	}

	private G11 changeStaff( G11 staff ){
		Class<?extends Wand> wandClass = staff.wandClass();

		if (wandClass == null){
			return null;
		} else {
			Wand n;
			do {
				n = (Wand)Generator.random(Category.WAND);
			} while (Challenges.isItemBlocked(n) || n.getClass() == wandClass);
			n.level(0);
			staff.imbueWand(n, null);
		}

		return staff;
	}
	
	private Weapon changeWeapon( MeleeWeapon w ) {
		
		Weapon n;
		Category c = Generator.wepTiers[w.tier-1];

		do {
			try {
				n = (MeleeWeapon)c.classes[Random.chances(c.probs)].newInstance();
			} catch (Exception e) {
				GirlsFrontlinePixelDungeon.reportException(e);
				return null;
			}
		} while (Challenges.isItemBlocked(n) || n.getClass() == w.getClass());

		int level = w.level();
		if (level > 0) {
			n.upgrade( level );
		} else if (level < 0) {
			n.degrade( -level );
		}

		n.enchantment = w.enchantment;
		n.levelKnown = w.levelKnown;
		n.cursedKnown = w.cursedKnown;
		n.cursed = w.cursed;
		n.augment = w.augment;

		return n;

	}
	
	private Ring changeRing( Ring r ) {
		Ring n;
		do {
			n = (Ring)Generator.random( Category.RING );
		} while (Challenges.isItemBlocked(n) || n.getClass() == r.getClass());
		
		n.level(0);
		
		int level = r.level();
		if (level > 0) {
			n.upgrade( level );
		} else if (level < 0) {
			n.degrade( -level );
		}
		
		n.levelKnown = r.levelKnown;
		n.cursedKnown = r.cursedKnown;
		n.cursed = r.cursed;
		
		return n;
	}

	private Artifact changeArtifact( Artifact a ) {
		Artifact n = Generator.randomArtifact();

		if (n != null && !Challenges.isItemBlocked(n)){
			n.cursedKnown = a.cursedKnown;
			n.cursed = a.cursed;
			n.levelKnown = a.levelKnown;
			n.transferUpgrade(a.visiblyUpgraded());
			return n;
		}

		return null;
	}
	
	private Wand changeWand( Wand w ) {
		
		Wand n;
		do {
			n = (Wand)Generator.random( Category.WAND );
		} while ( Challenges.isItemBlocked(n) || n.getClass() == w.getClass());
		
		n.level( 0 );
		n.upgrade( w.level() );
		
		n.levelKnown = w.levelKnown;
		n.cursedKnown = w.cursedKnown;
		n.cursed = w.cursed;
		
		return n;
	}
	
	private Plant.Seed changeSeed( Plant.Seed s ) {
		
		Plant.Seed n;
		
		do {
			n = (Plant.Seed)Generator.random( Category.SEED );
		} while (n.getClass() == s.getClass());
		
		return n;
	}
	
	private Scroll changeScroll( Scroll s ) {
		if (s instanceof ScrollOfUpgrade) {
			
			return new ScrollOfMagicalInfusion();
			
		} else if (s instanceof ScrollOfMagicalInfusion) {
			
			return new ScrollOfUpgrade();
			
		} else {
			
			Scroll n;
			do {
				n = (Scroll)Generator.random( Category.SCROLL );
			} while (n.getClass() == s.getClass());
			return n;
		}
	}
	
	private Potion changePotion( Potion p ) {
		if (p instanceof PotionOfStrength) {
			
			return new PotionOfMight();
			
		} else if (p instanceof PotionOfMight) {
			
			return new PotionOfStrength();
			
		} else {
			
			Potion n;
			do {
				n = (Potion)Generator.random( Category.POTION );
			} while (n.getClass() == p.getClass());
			return n;
		}
	}
	
	@Override
	public String tileDesc() {
		return Messages.get(this, "desc");
	}
}
