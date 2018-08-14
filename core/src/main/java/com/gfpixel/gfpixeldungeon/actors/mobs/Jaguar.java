package com.gfpixel.gfpixeldungeon.actors.mobs;

import com.gfpixel.gfpixeldungeon.actors.Char;
import com.gfpixel.gfpixeldungeon.items.potions.PotionOfHealing;
import com.gfpixel.gfpixeldungeon.sprites.JaguarSprite;
import com.watabou.utils.Random;

/**
 * Created by Android SDK on 2018-08-15.
 */

public class Jaguar extends Mob {

    {
        spriteClass = JaguarSprite.class;

        HP = HT = 30;
        defenseSkill = 30;
        baseSpeed = 1.2f;

        EXP = 7;
        maxLvl = 15;
        loot = new PotionOfHealing();
        lootChance = 0.1667f; //by default, see die()
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 5, 12 );
    }

    @Override
    public int attackSkill( Char target ) {
        return 12;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 5);
    }

    @Override
    protected float attackDelay() {
        return 0.5f;
    }
}