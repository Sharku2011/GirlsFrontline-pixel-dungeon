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

import com.gfpixel.gfpixeldungeon.Assets;
import com.gfpixel.gfpixeldungeon.Dungeon;
import com.gfpixel.gfpixeldungeon.actors.Char;
import com.gfpixel.gfpixeldungeon.items.Generator;
import com.gfpixel.gfpixeldungeon.items.Item;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.gfpixel.gfpixeldungeon.levels.features.Chasm;
import com.gfpixel.gfpixeldungeon.messages.Messages;
import com.gfpixel.gfpixeldungeon.sprites.GolyatPlusSprite;
import com.gfpixel.gfpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class GolyatPlus extends Mob {

    {
        spriteClass = GolyatPlusSprite.class;

        HP = HT = 1;
        defenseSkill = 5;

        EXP = 3;
        maxLvl = 10;

        properties.add(Property.ARMO);
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 8, 8 );
    }

    @Override
    public void die( Object cause ) {

        super.die( cause );

        if (cause == Chasm.class) return;

        boolean heroKilled = false;
        for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
            Char ch = findChar( pos + PathFinder.NEIGHBOURS8[i] );
            if (ch != null && ch.isAlive()) {
                int damage = Random.NormalIntRange(35, 45);
                damage = Math.max( 0,  damage - (ch.drRoll() + ch.drRoll()) );
                ch.damage( damage, this );
                if (ch == Dungeon.hero && !ch.isAlive()) {
                    heroKilled = true;
                }
            }
        }

        if (Dungeon.level.heroFOV[pos]) {
            Sample.INSTANCE.play( Assets.SND_BONES );
        }

        if (heroKilled) {
            Dungeon.fail( getClass() );
            GLog.n( Messages.get(this, "explo_kill") );
        }
    }

    @Override
    public int attackSkill( Char target ) {
        return 75;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 5);
    }

}
