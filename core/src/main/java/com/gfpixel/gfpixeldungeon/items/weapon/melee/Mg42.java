package com.gfpixel.gfpixeldungeon.items.weapon.melee;

import com.gfpixel.gfpixeldungeon.actors.Char;
import com.gfpixel.gfpixeldungeon.actors.hero.Hero;
import com.gfpixel.gfpixeldungeon.actors.mobs.Mob;
import com.gfpixel.gfpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

/**
 * Created by LoveKirsi on 2017-11-22.
 */

public class Mg42 extends MeleeWeapon {

    {
        image = ItemSpriteSheet.MG42;

        tier = 6;
        ACC = 0.35f;
        DLY = 0.1f;
    }

    @Override
    public int max(int lvl) {
        return  Math.round(1.3f*(tier+1)) +    //5 base, down from 20
                lvl*Math.round(0.4f*(tier+1));   //+1 per level, down from +2
    }

    @Override
    public int damageRoll(Char owner) {
        if (owner instanceof Hero) {
            Hero hero = (Hero)owner;
            Char enemy = hero.enemy();
            if (enemy instanceof Mob && ((Mob) enemy).surprisedBy(hero)) {
                //deals 85% toward max to max on surprise, instead of min to max.
                int damage = augment.damageFactor(Random.NormalIntRange(
                        Math.max(1, min()/3),
                        max()/3));
                int exStr = hero.STR() - STRReq();
                if (exStr > 0) {
                    damage -= Random.IntRange(0, exStr);
                }
                return damage;
            }
        }
        return super.damageRoll(owner);
    }

}


