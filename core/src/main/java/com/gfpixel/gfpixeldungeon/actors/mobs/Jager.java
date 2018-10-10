package com.gfpixel.gfpixeldungeon.actors.mobs;

import com.watabou.utils.Random;

public class Jager extends Warlock {
    {
        HP = HT = 20;
        defenseSkill = 8;

        EXP = 71;
        maxLvl = 11;
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 6, 13 );
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 4);
    }
}
