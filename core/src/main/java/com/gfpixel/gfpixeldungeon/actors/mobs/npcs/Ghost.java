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

package com.gfpixel.gfpixeldungeon.actors.mobs.npcs;

import com.gfpixel.gfpixeldungeon.Assets;
import com.gfpixel.gfpixeldungeon.DialogInfo;
import com.gfpixel.gfpixeldungeon.Dungeon;
import com.gfpixel.gfpixeldungeon.GirlsFrontlinePixelDungeon;
import com.gfpixel.gfpixeldungeon.actors.Char;
import com.gfpixel.gfpixeldungeon.actors.buffs.Buff;
import com.gfpixel.gfpixeldungeon.actors.buffs.Paralysis;
import com.gfpixel.gfpixeldungeon.actors.buffs.Roots;
import com.gfpixel.gfpixeldungeon.actors.mobs.FetidRat;
import com.gfpixel.gfpixeldungeon.actors.mobs.GnollTrickster;
import com.gfpixel.gfpixeldungeon.actors.mobs.GreatCrab;
import com.gfpixel.gfpixeldungeon.actors.mobs.Mob;
import com.gfpixel.gfpixeldungeon.effects.CellEmitter;
import com.gfpixel.gfpixeldungeon.effects.Speck;
import com.gfpixel.gfpixeldungeon.items.Generator;
import com.gfpixel.gfpixeldungeon.items.armor.Armor;
import com.gfpixel.gfpixeldungeon.items.armor.LeatherArmor;
import com.gfpixel.gfpixeldungeon.items.armor.MailArmor;
import com.gfpixel.gfpixeldungeon.items.armor.PlateArmor;
import com.gfpixel.gfpixeldungeon.items.armor.ScaleArmor;
import com.gfpixel.gfpixeldungeon.items.weapon.Weapon;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Shortsword;
import com.gfpixel.gfpixeldungeon.journal.Notes;
import com.gfpixel.gfpixeldungeon.levels.SewerLevel;
import com.gfpixel.gfpixeldungeon.messages.Messages;
import com.gfpixel.gfpixeldungeon.scenes.GameScene;
import com.gfpixel.gfpixeldungeon.scenes.PixelScene;
import com.gfpixel.gfpixeldungeon.sprites.GhostSprite;
import com.gfpixel.gfpixeldungeon.ui.Tag;
import com.gfpixel.gfpixeldungeon.utils.GLog;
import com.gfpixel.gfpixeldungeon.windows.WndDialog;
import com.gfpixel.gfpixeldungeon.windows.WndQuest;
import com.gfpixel.gfpixeldungeon.windows.WndSadGhost;
import com.gfpixel.gfpixeldungeon.windows.WndStory;
import com.watabou.input.Touchscreen;
import com.watabou.noosa.Game;
import com.watabou.noosa.TouchArea;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;
import com.watabou.utils.SparseArray;

import java.lang.reflect.Constructor;
import java.util.HashMap;

public class Ghost extends NPC {

	{
		spriteClass = GhostSprite.class;
		
		flying = true;
		
		state = WANDERING;
	}

	public Ghost() {
		super();

		Sample.INSTANCE.load( Assets.SND_GHOST );
	}

	@Override
	protected boolean act() {
		if (Quest.processed())
			target = Dungeon.hero.pos;
		return super.act();
	}

	@Override
	public int defenseSkill( Char enemy ) {
		return 1000;
	}
	
	@Override
	public float speed() {
		return Quest.processed() ? 2f : 0.5f;
	}
	
	@Override
	protected Char chooseEnemy() {
		return null;
	}
	
	@Override
	public void damage( int dmg, Object src ) {
	}
	
	@Override
	public void add( Buff buff ) {
	}
	
	@Override
	public boolean reset() {
		return true;
	}
	
	@Override
	public boolean interact() {
		sprite.turnTo( pos, Dungeon.hero.pos );
		
		Sample.INSTANCE.play( Assets.SND_GHOST );

		if (Quest.given) {
			if (Quest.weapon != null) {
				if (Quest.processed) {
					int DialogID = DialogInfo.ID_STAR15_QUEST + DialogInfo.COMPLETE;
					WndDialog wnd = new WndDialog( DialogID ) {
						@Override
						protected void onFinish()
						{
							GameScene.show(new WndSadGhost((Ghost)this.npc));
						}
					};

					wnd.npc = this;
					GameScene.show(wnd);

				} else {
					int DialogID = DialogInfo.ID_STAR15_QUEST + DialogInfo.INPROGRESS;
					WndDialog.ShowChapter(DialogID);

					int newPos = -1;
					for (int i = 0; i < 10; i++) {
						newPos = Dungeon.level.randomRespawnCell();
						if (newPos != -1) {
							break;
						}
					}
					if (newPos != -1) {

						CellEmitter.get(pos).start(Speck.factory(Speck.LIGHT), 0.2f, 3);
						pos = newPos;
						sprite.place(pos);
						sprite.visible = Dungeon.level.heroFOV[pos];
					}
				}
			}
		} else {

			try {
				Mob questBoss;

				questBoss = Quest.TARGETS.get(Quest.type).newInstance();

				questBoss.pos = Dungeon.level.randomRespawnCell();

				WndDialog.setBRANCH(DialogInfo.ID_STAR15_QUEST, Quest.type);

				if (questBoss.pos != -1) {
					GameScene.add(questBoss);
					WndDialog.ShowChapter(DialogInfo.ID_STAR15_QUEST);
					//GameScene.show( new WndQuest( this, txt_quest ) );
					Quest.given = true;
					Notes.add( Notes.Landmark.GHOST );
				}

			} catch (Exception e) {
				GirlsFrontlinePixelDungeon.reportException(e);
			}

		}

		return false;
	}
	
	{
		immunities.add( Paralysis.class );
		immunities.add( Roots.class );
	}

	public static class Quest {

		private static final SparseArray<Class<? extends Mob>> TARGETS = new SparseArray<>();

		static {
			TARGETS.put(1, FetidRat.class);
			TARGETS.put(2, GnollTrickster.class);
			TARGETS.put(3, GreatCrab.class);
		}

		private static boolean spawned;

		private static int type;

		private static boolean given;
		private static boolean processed;
		
		private static int depth;
		
		public static Weapon weapon;
		public static Armor armor;
		
		public static void reset() {
			spawned = false;
			
			weapon = null;
			armor = null;
		}
		
		private static final String NODE		= "sadGhost";
		
		private static final String SPAWNED		= "spawned";
		private static final String TYPE        = "type";
		private static final String GIVEN		= "given";
		private static final String PROCESSED	= "processed";
		private static final String DEPTH		= "depth";
		private static final String WEAPON		= "weapon";
		private static final String ARMOR		= "armor";
		
		public static void storeInBundle( Bundle bundle ) {
			
			Bundle node = new Bundle();
			
			node.put( SPAWNED, spawned );
			
			if (spawned) {
				
				node.put( TYPE, type );
				
				node.put( GIVEN, given );
				node.put( DEPTH, depth );
				node.put( PROCESSED, processed);
				
				node.put( WEAPON, weapon );
				node.put( ARMOR, armor );
			}
			
			bundle.put( NODE, node );
		}
		
		public static void restoreFromBundle( Bundle bundle ) {
			
			Bundle node = bundle.getBundle( NODE );

			if (!node.isNull() && (spawned = node.getBoolean( SPAWNED ))) {

				type = node.getInt(TYPE);
				given	= node.getBoolean( GIVEN );
				processed = node.getBoolean( PROCESSED );

				depth	= node.getInt( DEPTH );
				
				weapon	= (Weapon)node.get( WEAPON );
				armor	= (Armor)node.get( ARMOR );
			} else {
				reset();
			}
		}
		
		public static void spawn( SewerLevel level ) {
			if (!spawned && Dungeon.depth > 1 && Random.Int( 5 - Dungeon.depth ) == 0) {
				
				Ghost ghost = new Ghost();
				do {
					ghost.pos = level.randomRespawnCell();
				} while (ghost.pos == -1);
				level.mobs.add( ghost );
				
				spawned = true;
				//dungeon depth determines type of quest.
				//depth2=fetid rat, 3=gnoll trickster, 4=great crab
				type = Dungeon.depth - 1;
				
				given = false;
				processed = false;
				depth = Dungeon.depth;

				//50%:tier2, 30%:tier3, 15%:tier4, 5%:tier5
				float itemTierRoll = Random.Float();
				int wepTier;

				if (itemTierRoll < 0.5f) {
					wepTier = 2;
					armor = new LeatherArmor();
				} else if (itemTierRoll < 0.8f) {
					wepTier = 3;
					armor = new MailArmor();
				} else if (itemTierRoll < 0.95f) {
					wepTier = 4;
					armor = new ScaleArmor();
				} else {
					wepTier = 5;
					armor = new PlateArmor();
				}

				try {
					do {
						weapon = (Weapon) Generator.wepTiers[wepTier - 1].classes[Random.chances(Generator.wepTiers[wepTier - 1].probs)].newInstance();
					} while (!(weapon instanceof MeleeWeapon));
				} catch (Exception e){
					GirlsFrontlinePixelDungeon.reportException(e);
					weapon = new Shortsword();
				}

				//50%:+0, 30%:+1, 15%:+2, 5%:+3
				float itemLevelRoll = Random.Float();
				int itemLevel;
				if (itemLevelRoll < 0.5f){
					itemLevel = 0;
				} else if (itemLevelRoll < 0.8f){
					itemLevel = 1;
				} else if (itemLevelRoll < 0.95f){
					itemLevel = 2;
				} else {
					itemLevel = 3;
				}
				weapon.upgrade(itemLevel);
				armor.upgrade(itemLevel);

				//10% to be enchanted
				if (Random.Int(10) == 0){
					weapon.enchant();
					armor.inscribe();
				}

			}
		}
		
		public static void process() {
			if (spawned && given && !processed && (depth == Dungeon.depth)) {
				GLog.n( Messages.get(Ghost.class, "find_me") );
				Sample.INSTANCE.play( Assets.SND_GHOST );
				processed = true;
			}
		}
		
		public static void complete() {
			weapon = null;
			armor = null;
			
			Notes.remove( Notes.Landmark.GHOST );
		}

		public static boolean processed(){
			return spawned && processed;
		}
		
		public static boolean completed(){
			return processed() && weapon == null && armor == null;
		}
	}

}
