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

package com.gfpixel.gfpixeldungeon.actors.mobs;

import com.gfpixel.gfpixeldungeon.actors.Char;
import com.gfpixel.gfpixeldungeon.actors.buffs.Bleeding;
import com.gfpixel.gfpixeldungeon.actors.buffs.Buff;
import com.gfpixel.gfpixeldungeon.actors.buffs.Charm;
import com.gfpixel.gfpixeldungeon.actors.buffs.Terror;
import com.gfpixel.gfpixeldungeon.items.artifacts.TimekeepersHourglass;
import com.gfpixel.gfpixeldungeon.items.scrolls.ScrollOfPsionicBlast;
import com.gfpixel.gfpixeldungeon.items.weapon.enchantments.Grim;
import com.gfpixel.gfpixeldungeon.items.weapon.enchantments.Vampiric;
import com.gfpixel.gfpixeldungeon.sprites.IntruderSprite;
import com.watabou.utils.Random;

public class Intruder extends Golem {

    {
        spriteClass = IntruderSprite.class;
        EXP = 40;
        defenseSkill = 20;
        baseSpeed = 0.98f;
        maxLvl = 26;

        loot = new TimekeepersHourglass();
        lootChance = 0.2f;

        HP = HT = 370;
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 25, 25 );
    }

    @Override
    public int attackSkill( Char target ) {
        return 25;
    }

    @Override
    protected float attackDelay() {
        return 0.2f;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 5);
    }

    @Override
    public int attackProc( Char enemy, int damage ) {
        damage = super.attackProc( enemy, damage );
        if (Random.Int( 2 ) == 0) {
            Buff.affect( enemy, Bleeding.class ).set( damage );
        }

        return damage;
    }

    {
        resistances.add( ScrollOfPsionicBlast.class );
        resistances.add( Grim.class );
    }

    {
        immunities.add( Terror.class );
    }
}