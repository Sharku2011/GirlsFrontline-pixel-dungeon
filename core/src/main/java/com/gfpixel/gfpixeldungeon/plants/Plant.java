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

package com.gfpixel.gfpixeldungeon.plants;

import com.gfpixel.gfpixeldungeon.Assets;
import com.gfpixel.gfpixeldungeon.Challenges;
import com.gfpixel.gfpixeldungeon.Dungeon;
import com.gfpixel.gfpixeldungeon.GirlsFrontlinePixelDungeon;
import com.gfpixel.gfpixeldungeon.actors.Actor;
import com.gfpixel.gfpixeldungeon.actors.Char;
import com.gfpixel.gfpixeldungeon.actors.buffs.Barkskin;
import com.gfpixel.gfpixeldungeon.actors.buffs.Buff;
import com.gfpixel.gfpixeldungeon.actors.hero.Hero;
import com.gfpixel.gfpixeldungeon.actors.hero.HeroSubClass;
import com.gfpixel.gfpixeldungeon.effects.CellEmitter;
import com.gfpixel.gfpixeldungeon.effects.particles.LeafParticle;
import com.gfpixel.gfpixeldungeon.items.Dewdrop;
import com.gfpixel.gfpixeldungeon.items.Generator;
import com.gfpixel.gfpixeldungeon.items.Item;
import com.gfpixel.gfpixeldungeon.items.artifacts.SandalsOfNature;
import com.gfpixel.gfpixeldungeon.levels.Level;
import com.gfpixel.gfpixeldungeon.levels.Terrain;
import com.gfpixel.gfpixeldungeon.messages.Messages;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public abstract class Plant implements Bundlable {

	public String plantName = Messages.get(this, "name");
	
	public int image;
	public int pos;

	public void trigger(){

		Char ch = Actor.findChar(pos);

		if (ch instanceof Hero){
			((Hero) ch).interrupt();
			if (((Hero)ch).subClass == HeroSubClass.WARDEN) {
				Buff.affect(ch, Barkskin.class).level(ch.HT / 3);
			}
		}

		wither();
		activate();
	}
	
	public abstract void activate();
	
	public void wither() {
		Dungeon.level.uproot( pos );

		if (Dungeon.level.heroFOV[pos]) {
			CellEmitter.get( pos ).burst( LeafParticle.GENERAL, 6 );
		}
		
		if (Dungeon.hero.subClass == HeroSubClass.WARDEN) {

			int naturalismLevel = 0;
			SandalsOfNature.Naturalism naturalism = Dungeon.hero.buff( SandalsOfNature.Naturalism.class );
			if (naturalism != null) {
				naturalismLevel = naturalism.itemLevel()+1;
			}

			if (Random.Int( 5 - (naturalismLevel/2) ) == 0) {
				Item seed = Generator.random(Generator.Category.SEED);

				if (seed instanceof BlandfruitBush.Seed) {
					if (Random.Int(3) - Dungeon.LimitedDrops.BLANDFRUIT_SEED.count >= 0) {
						Dungeon.level.drop(seed, pos).sprite.drop();
						Dungeon.LimitedDrops.BLANDFRUIT_SEED.count++;
					}
				} else
					Dungeon.level.drop(seed, pos).sprite.drop();
			}
			if (Random.Int( 5 - naturalismLevel ) == 0) {
				Dungeon.level.drop( new Dewdrop(), pos ).sprite.drop();
			}
		}
	}
	
	private static final String POS	= "pos";

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		pos = bundle.getInt( POS );
	}

	@Override
	public void storeInBundle( Bundle bundle ) {
		bundle.put( POS, pos );
	}
	
	public String desc() {
		return Messages.get(this, "desc");
	}
	
	public static class Seed extends Item {

		public static final String AC_PLANT	= "PLANT";
		
		private static final float TIME_TO_PLANT = 1f;
		
		{
			stackable = true;
			defaultAction = AC_THROW;
		}
		
		protected Class<? extends Plant> plantClass;
		
		public Class<? extends Item> alchemyClass;
		
		@Override
		public ArrayList<String> actions( Hero hero ) {
			ArrayList<String> actions = super.actions( hero );
			actions.add( AC_PLANT );
			return actions;
		}
		
		@Override
		protected void onThrow( int cell ) {
			if (Dungeon.level.map[cell] == Terrain.ALCHEMY
					|| Dungeon.level.pit[cell]
					|| Dungeon.level.traps.get(cell) != null
					|| Dungeon.isChallenged(Challenges.NO_HERBALISM)) {
				super.onThrow( cell );
			} else {
				Dungeon.level.plant( this, cell );
			}
		}
		
		@Override
		public void execute( Hero hero, String action ) {

			super.execute (hero, action );

			if (action.equals( AC_PLANT )) {
							
				hero.spend( TIME_TO_PLANT );
				hero.busy();
				((Seed)detach( hero.belongings.backpack )).onThrow( hero.pos );
				
				hero.sprite.operate( hero.pos );
				
			}
		}
		
		public Plant couch( int pos, Level level ) {
			try {
				if (level != null && level.heroFOV != null && level.heroFOV[pos]) {
					Sample.INSTANCE.play(Assets.SND_PLANT);
				}
				Plant plant = plantClass.newInstance();
				plant.pos = pos;
				return plant;
			} catch (Exception e) {
				GirlsFrontlinePixelDungeon.reportException(e);
				return null;
			}
		}
		
		@Override
		public boolean isUpgradable() {
			return false;
		}
		
		@Override
		public boolean isIdentified() {
			return true;
		}
		
		@Override
		public int price() {
			return 10 * quantity;
		}

		@Override
		public String desc() {
			return Messages.get(plantClass, "desc");
		}

		@Override
		public String info() {
			return Messages.get( Seed.class, "info", desc() );
		}
	}
}
