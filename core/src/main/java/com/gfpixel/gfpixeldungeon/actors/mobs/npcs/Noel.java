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

import com.gfpixel.gfpixeldungeon.DialogInfo;
import com.gfpixel.gfpixeldungeon.Dungeon;
import com.gfpixel.gfpixeldungeon.actors.Char;
import com.gfpixel.gfpixeldungeon.actors.buffs.Buff;
import com.gfpixel.gfpixeldungeon.items.Generator;
import com.gfpixel.gfpixeldungeon.items.Item;
import com.gfpixel.gfpixeldungeon.items.quest.CeremonialCandle;
import com.gfpixel.gfpixeldungeon.items.quest.Embers;
import com.gfpixel.gfpixeldungeon.items.wands.Wand;
import com.gfpixel.gfpixeldungeon.journal.Notes;
import com.gfpixel.gfpixeldungeon.levels.Level;
import com.gfpixel.gfpixeldungeon.levels.rooms.Room;
import com.gfpixel.gfpixeldungeon.levels.rooms.standard.RitualSiteRoom;
import com.gfpixel.gfpixeldungeon.scenes.GameScene;
import com.gfpixel.gfpixeldungeon.sprites.NoelSprite;
import com.gfpixel.gfpixeldungeon.utils.GLog;
import com.gfpixel.gfpixeldungeon.windows.WndDialog;
import com.gfpixel.gfpixeldungeon.windows.WndNoel;
import com.watabou.utils.Bundle;
import com.watabou.utils.SparseArray;

import java.util.ArrayList;

public class Noel extends NPC {

    {
        spriteClass = NoelSprite.class;

        properties.add(Property.IMMOVABLE);
    }

    @Override
    protected boolean act() {
        throwItem();
        return super.act();
    }

    @Override
    public int defenseSkill( Char enemy ) {
        return 1000;
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
        
        // 퀘스트 진행 검사
        if (Quest.given) {
            final Item item = Dungeon.hero.belongings.getItem( Quest.TARGETS.get(0));

            // 퀘스트 완료
            if (item != null) {

                int DialogID = DialogInfo.ID_NOEL_QUEST + DialogInfo.COMPLETE;
                WndDialog wnd = new WndDialog( DialogID ) {
                    @Override
                    protected void onFinish()
                    {
                        GameScene.show(new WndNoel((Noel) this.npc, item));
                    }
                };

                wnd.npc = this;
                GameScene.show(wnd);

            } else {
                // 진행중 대사 출력
                int DialogID = DialogInfo.ID_NOEL_QUEST + DialogInfo.INPROGRESS;

                WndDialog.setBRANCH(DialogID, Quest.type);
                WndDialog.ShowChapter(DialogID);

            }

        } else {
            // 퀘스트 수주
            int DialogID = DialogInfo.ID_NOEL_QUEST;

            WndDialog.setBRANCH(DialogID, Quest.type);
            WndDialog.ShowChapter(DialogID);

            Notes.add( Notes.Landmark.NOEL );
            Quest.given = true;
        }

        return false;
    }

    public static class Quest {

        private static int type;
        // 2 = elemental embers quest

        private static SparseArray<Class<? extends Item>> TARGETS = new SparseArray<>();

        static {
            TARGETS.put(0, Embers.class);
        }

        private static boolean spawned;

        private static boolean given;

        public static Wand wand1;
        public static Wand wand2;

        public static void reset() {
            spawned = false;

            wand1 = null;
            wand2 = null;
        }

        private static final String NODE		= "noel";

        private static final String SPAWNED		= "spawned";
        private static final String GIVEN		= "given";
        private static final String WAND1		= "wand1";
        private static final String WAND2		= "wand2";

        private static final String RITUALPOS	= "ritualpos";

        public static void storeInBundle( Bundle bundle ) {

            Bundle node = new Bundle();

            node.put( SPAWNED, spawned );

            if (spawned) {
                GLog.i("퀘스트 저장됨");
                node.put( GIVEN, given );

                node.put( WAND1, wand1 );
                node.put( WAND2, wand2 );

                node.put( RITUALPOS, CeremonialCandle.ritualPos );
            }

            bundle.put( NODE, node );
        }

        public static void restoreFromBundle( Bundle bundle ) {

            Bundle node = bundle.getBundle( NODE );

            if (!node.isNull() && (spawned = node.getBoolean( SPAWNED ))) {

                given = node.getBoolean( GIVEN );

                wand1 = (Wand)node.get( WAND1 );
                wand2 = (Wand)node.get( WAND2 );

                CeremonialCandle.ritualPos = node.getInt( RITUALPOS );

            } else {
                GLog.i(String.valueOf(!node.isNull()));
                GLog.i(String.valueOf(spawned));
                reset();
            }
        }

        private static boolean questRoomSpawned;

        public static void spawnNoel( Level level, Room room ) {
            if (questRoomSpawned) {

                questRoomSpawned = false;

                Noel npc = new Noel();
                do {
                    npc.pos = level.pointToCell(room.random());
                } while (npc.pos == level.entrance);
                level.mobs.add( npc );

                spawned = true;

                given = false;
                wand1 = (Wand) Generator.random(Generator.Category.WAND);
                wand1.cursed = false;
                wand1.upgrade();

                do {
                    wand2 = (Wand) Generator.random(Generator.Category.WAND);
                } while (wand2.getClass().equals(wand1.getClass()));
                wand2.cursed = false;
                wand2.upgrade();

            }
        }

        public static ArrayList<Room> spawnRoom( ArrayList<Room> rooms) {
            questRoomSpawned = false;
            if (!spawned && (Dungeon.depth == 7)) {

                rooms.add(new RitualSiteRoom());
                questRoomSpawned = true;
            }
            return rooms;
        }

        public static void complete() {
            wand1 = null;
            wand2 = null;

            Notes.remove( Notes.Landmark.NOEL );
        }
    }
}
