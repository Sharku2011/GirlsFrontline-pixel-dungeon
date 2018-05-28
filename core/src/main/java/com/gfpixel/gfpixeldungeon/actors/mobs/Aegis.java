/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2017 Evan Debenham
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

package com.gfpixel.gfpixeldungeon.actors.mobs;

import com.gfpixel.gfpixeldungeon.Dungeon;
import com.gfpixel.gfpixeldungeon.actors.Char;
import com.gfpixel.gfpixeldungeon.actors.buffs.Amok;
import com.gfpixel.gfpixeldungeon.actors.buffs.Terror;
import com.gfpixel.gfpixeldungeon.actors.hero.Hero;
import com.gfpixel.gfpixeldungeon.items.KindOfWeapon;
import com.gfpixel.gfpixeldungeon.items.potions.PotionOfHealing;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Knuckles;
import com.gfpixel.gfpixeldungeon.messages.Messages;
import com.gfpixel.gfpixeldungeon.sprites.AegisSprite;
import com.gfpixel.gfpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Aegis extends Mob {

    {
        spriteClass = AegisSprite.class;

        HP = HT = 400;
        EXP = 15;
        defenseSkill = 5;
        baseSpeed = 0.8f;
        maxLvl = 26;

        loot = new PotionOfHealing();
        lootChance = 0.3333f; //by default, see die()
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 5, 15 );
    }

    @Override
    public int attackSkill( Char target ) {
        return 15;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 5);
    }
    private int hitsToDisarm = 0;

    @Override
    public int attackProc( Char enemy, int damage ) {
        damage = super.attackProc( enemy, damage );

        if (enemy == Dungeon.hero) {

            Hero hero = Dungeon.hero;
            KindOfWeapon weapon = hero.belongings.weapon;

            if (weapon != null && !(weapon instanceof Knuckles) && !weapon.cursed) {
                if (hitsToDisarm == 0) hitsToDisarm = Random.NormalIntRange(4, 8);

                if (--hitsToDisarm == 0) {
                    hero.belongings.weapon = null;
                    Dungeon.quickslot.convertToPlaceholder(weapon);
                    weapon.updateQuickslot();
                    Dungeon.level.drop(weapon, hero.pos).sprite.drop();
                    GLog.w(Messages.get(this, "disarm", weapon.name()));
                }
            }
        }

        return damage;
    }

    {
        immunities.add( Amok.class );
        immunities.add( Terror.class );
    }

    private static String DISARMHITS = "hitsToDisarm";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(DISARMHITS, hitsToDisarm);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        hitsToDisarm = bundle.getInt(DISARMHITS);
    }
}
