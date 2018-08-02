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

package com.gfpixel.gfpixeldungeon.items.weapon.melee;

import com.gfpixel.gfpixeldungeon.Dungeon;
import com.gfpixel.gfpixeldungeon.actors.Char;
import com.gfpixel.gfpixeldungeon.actors.buffs.Buff;
import com.gfpixel.gfpixeldungeon.actors.buffs.Light;
import com.gfpixel.gfpixeldungeon.actors.buffs.Speed;
import com.gfpixel.gfpixeldungeon.actors.hero.Hero;
import com.gfpixel.gfpixeldungeon.actors.mobs.Mob;
import com.gfpixel.gfpixeldungeon.effects.Speck;
import com.gfpixel.gfpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class Cannon extends MeleeWeapon {

    {
        image = ItemSpriteSheet.CANNON;

        tier = 1;
        RCH = 70;    //lots of extra reach
        ACC = 20f;
        DLY = 0.1f;
    }

    @Override
    public int max(int lvl) {
        return  500*(tier+1) +    //8 base, down from 10
                lvl*(tier+60);   //scaling unchanged
    }

    @Override
    public int damageRoll(Char owner) {
        if (owner instanceof Hero) {
            Hero hero = (Hero)owner;
            Char enemy = hero.enemy();
            Buff.affect(hero, Light.class, 10);
            Buff.affect(hero, Speed.class, 10);
            //Dungeon.Flag = true;
            if (enemy instanceof Mob && ((Mob) enemy).surprisedBy(hero)) {
                //deals 75% toward max to max on surprise, instead of min to max.
                int diff = max() - min();
                int damage = augment.damageFactor(Random.NormalIntRange(
                        min() + Math.round(diff*3.75f),
                        max()));
                int exStr = hero.STR() - STRReq();
                if (exStr > 0) {
                    damage += Random.IntRange(0, exStr);
                }
                owner.sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
                hero.HP += Math.min(damage, hero.HT-hero.HP);
                return damage;
            }
        }
        owner.sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
        int damage = super.damageRoll(owner);
        owner.HP += Math.min(damage, owner.HT-owner.HP);
        return damage;
    }
    @Override
    public int defenseFactor( Char owner ) {
        return 30;	//3 extra defence
    }

}
