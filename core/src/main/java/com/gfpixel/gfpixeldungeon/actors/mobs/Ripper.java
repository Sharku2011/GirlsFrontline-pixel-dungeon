package com.gfpixel.gfpixeldungeon.actors.mobs;

import com.gfpixel.gfpixeldungeon.actors.Char;
import com.gfpixel.gfpixeldungeon.sprites.RipperSprite;
import com.watabou.utils.Random;

public class Ripper extends Mob {

    {
        spriteClass = RipperSprite.class;

        HP = HT = 30;
        defenseSkill = 10;

        EXP = 7;
        maxLvl = 15;
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 5, 7 );
    }

    @Override
    public int attackSkill( Char target ) {
        return 18;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 5);
    }

    @Override
    protected float attackDelay() {
        return 0.5f;
    }

    @Override
    public void damage(int dmg, Object src) {
        super.damage(dmg, src);

        int initialDefenceSkill = 10;

        if (isAlive()) {
            defenseSkill = initialDefenceSkill + ((HT-HP) / 3);
        }
    }
}
