package com.gfpixel.gfpixeldungeon.actors.mobs;

import com.watabou.utils.Random;

public class Jager extends Warlock {
    {
        HP = HT = 30;
        defenseSkill = 8;

        EXP = 6;
        maxLvl = 14;
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 3, 7 );
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(1, 2);
    }
}