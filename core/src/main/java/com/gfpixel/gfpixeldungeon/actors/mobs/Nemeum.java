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
import com.gfpixel.gfpixeldungeon.actors.buffs.Terror;
import com.gfpixel.gfpixeldungeon.items.weapon.enchantments.Grim;
import com.gfpixel.gfpixeldungeon.items.weapon.enchantments.Vampiric;
import com.gfpixel.gfpixeldungeon.sprites.NemeumSprite;
import com.watabou.utils.Random;

public class Nemeum extends BeamChargeMob {

    {
        spriteClass = NemeumSprite.class;

        HP = HT = 40;
        EXP = 9;
        defenseSkill = 5;
        baseSpeed = 0.8f;
        maxLvl = 17;

        properties.add(Property.ARMO);
    }

    @Override
    public int attackRange() {
        return 2;
    }

    @Override
    public int damageReducer() {
        return 1;
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange(15, 25);
    }

    @Override
    public int attackSkill( Char target ) {
        return 30;
    }

    @Override
    protected float attackDelay() {
        return 1f;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 10);
    }

    {
        resistances.add( Grim.class );
        resistances.add( Vampiric.class );
    }

    {
        immunities.add( Terror.class );
    }
}