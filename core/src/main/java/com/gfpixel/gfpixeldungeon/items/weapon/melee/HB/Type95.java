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

package com.gfpixel.gfpixeldungeon.items.weapon.melee.HB;

import com.gfpixel.gfpixeldungeon.actors.Char;
import com.gfpixel.gfpixeldungeon.actors.buffs.Bless;
import com.gfpixel.gfpixeldungeon.actors.buffs.Buff;
import com.gfpixel.gfpixeldungeon.actors.hero.Hero;
import com.gfpixel.gfpixeldungeon.sprites.ItemSpriteSheet;

public class Type95 extends HitBuffer {

    {
        image = ItemSpriteSheet.KRISS;

        tier = 3;
        ACC = 0.5f;
    }

    @Override
    public void onAttack( Char h, Char enemy ) {

    }

    @Override
    public int damageRoll(Char owner) {
        if (owner instanceof Hero && owner.buffs(Bless.class).isEmpty()) {
            Buff.prolong(owner, Bless.class, 1.2f);
        }
        return super.damageRoll(owner);
    }

    @Override
    public int max(int lvl) {
        return  Math.round(2.5f*(tier+1)) +     //10 base, down from 20
                lvl*Math.round(0.5f*(tier+1));  //+2 per level, down from +4
    }
}
