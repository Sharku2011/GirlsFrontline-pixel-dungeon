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

        HUNTING = new Hunting();
        WANDERING = new Wandering();

        properties.add(Property.ARMO);
        properties.add(Property.IMMOVABLE);

        maxChargeCount = 3;
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

    protected class Wandering extends Mob.Wandering {
        @Override
        public boolean act(boolean enemyInFOV, boolean justAlerted) {
            if (enemyInFOV && (justAlerted || Random.Int( distance( enemy ) / 2 + enemy.stealth() ) == 0)) {

                enemySeen = true;

                notice();
                alerted = true;
                state = HUNTING;
                target = enemy.pos;

            } else {
                enemySeen = false;
                discharge();

                boolean visible = Dungeon.level.heroFOV[pos];
                if (visible) {
                    sprite.idle();
                }
                spend( TICK );
            }
            return true;
        }
    }

    protected class Hunting extends Mob.Hunting {
        @Override
        public boolean act(boolean enemyInFOV, boolean justAlerted) {

            enemySeen = enemyInFOV;
            if (enemyInFOV) {

                target = enemy.pos;
                chargeManager.updateBeamTrace();

                if (!isCharmedBy( enemy ) && canAttack( enemy )) {
                    return doAttack( enemy );
                }
            } else {
                spend( TICK );
                state = WANDERING;
            }
            return true;
        }
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
