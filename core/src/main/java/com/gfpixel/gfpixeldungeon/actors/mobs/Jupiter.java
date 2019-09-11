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
import com.gfpixel.gfpixeldungeon.actors.buffs.Light;
import com.gfpixel.gfpixeldungeon.actors.buffs.Terror;
import com.gfpixel.gfpixeldungeon.items.wands.WandOfDisintegration;
import com.gfpixel.gfpixeldungeon.items.weapon.enchantments.Grim;
import com.gfpixel.gfpixeldungeon.items.weapon.enchantments.Vampiric;
import com.gfpixel.gfpixeldungeon.sprites.JupiterSprite;
import com.watabou.utils.Random;

public class Jupiter extends BeamChargeMob {

    {
        spriteClass = JupiterSprite.class;

        HP = HT = 150;
        defenseSkill = 5;
        viewDistance = Light.DISTANCE;
        baseSpeed = 1f;
        maxLvl = 26;

        properties.add(Property.ARMO);
    }

    @Override
    public int damageReducer() {
        return 2;
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 30, 70 );
    }

    @Override
    public int attackSkill( Char target ) {
        return 70;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 7);
    }

    @Override
    public void move( int step ) {
        // TODO 움직이지 못하는것을 immovable 태그를 주고 해당 메소드 오버라이드를 제거할 것

    }

    {
        resistances.add( WandOfDisintegration.class );
        resistances.add( Grim.class );
        resistances.add( Vampiric.class );
    }

    {
        immunities.add( Terror.class );
    }
}
