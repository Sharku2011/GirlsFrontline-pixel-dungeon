package com.gfpixel.gfpixeldungeon.actors.mobs;

import com.gfpixel.gfpixeldungeon.sprites.ElidSprite;
import com.gfpixel.gfpixeldungeon.actors.Char;
import com.gfpixel.gfpixeldungeon.actors.buffs.Bleeding;
import com.gfpixel.gfpixeldungeon.actors.buffs.Buff;
import com.gfpixel.gfpixeldungeon.items.potions.PotionOfParalyticGas;
import com.gfpixel.gfpixeldungeon.sprites.ElidSprite;
import com.watabou.utils.Random;

public class ELID extends Mob {

    {
        spriteClass = ElidSprite.class;

        HP = HT = 1000;
        EXP = 100;
        defenseSkill = 5;
        baseSpeed = 0.6f;
        maxLvl = 36;

        properties.add(Property.DEMONIC);
        properties.add(Property.UNDEAD);
        lootChance = 1;
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 45, 60 );
    }

    @Override
    public int attackSkill( Char target ) {
        return 48;
    }

    @Override
    protected float attackDelay() {
        return 1.2f;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 12);
    }

    @Override
    public int attackProc( Char enemy, int damage ) {
        damage = super.attackProc( enemy, damage );
        if (Random.Int( 10 ) == 0) {
            Buff.affect( enemy, Bleeding.class ).set( damage );
        }

        return damage;
    }
}
