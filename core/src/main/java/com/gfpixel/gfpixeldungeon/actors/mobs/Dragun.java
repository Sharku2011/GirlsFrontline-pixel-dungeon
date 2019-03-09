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
import com.gfpixel.gfpixeldungeon.items.Torch;
import com.gfpixel.gfpixeldungeon.sprites.DragunSprite;
import com.watabou.utils.Random;

public class Dragun extends Mob {

    {
        spriteClass = DragunSprite.class;

        HP = HT = 20;
        EXP = 16;
        defenseSkill = 75;
        baseSpeed = 2f;
        maxLvl = 26;

        loot = new Torch();
        lootChance = 0.253f;
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 30, 35 );
    }

    @Override
    public int attackSkill( Char target ) {
        return 55;
    }

    @Override
    protected float attackDelay() {
        return 0.5f;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 5);
    }
}