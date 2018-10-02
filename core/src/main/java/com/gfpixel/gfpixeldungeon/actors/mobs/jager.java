package com.gfpixel.gfpixeldungeon.actors.mobs;

import com.watabou.utils.Random;

public class Jager extends Warlock {
    {

        HP = HT = 20;
        defenseSkill = 8;

        EXP = 71;
        maxLvl = 11;


        properties.add(Property.UNDEAD);
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 6, 12 );
    }
}
