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

import com.gfpixel.gfpixeldungeon.Dungeon;
import com.gfpixel.gfpixeldungeon.actors.Char;
import com.gfpixel.gfpixeldungeon.messages.Messages;
import com.gfpixel.gfpixeldungeon.sprites.CharSprite;
import com.gfpixel.gfpixeldungeon.sprites.CyclopsSprite;
import com.watabou.utils.Random;

public class Cyclops extends Mob {

    {
        spriteClass = CyclopsSprite.class;

        HP = HT = 120;
        EXP = 17;
        defenseSkill = 60;
        baseSpeed = 1f;
        maxLvl = 36;

        properties.add(Property.ARMO);
    }

    private boolean enraged = false;

    @Override
    public int damageRoll() {
        return enraged ?
                Random.NormalIntRange( 40, 70 ) :
                Random.NormalIntRange( 30, 60 );
    }

    @Override
    public int attackSkill( Char target ) {
        return 60;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 5);
    }

    @Override
    public void damage( int dmg, Object src ) {
        super.damage( dmg, src );

        if (isAlive() && !enraged && HP < HT / 4) {
            enraged = true;
            spend( TICK );
            if (Dungeon.level.heroFOV[pos]) {
                sprite.showStatus( CharSprite.NEGATIVE, Messages.get(this, "enraged") );
            }
        }
    }

}
