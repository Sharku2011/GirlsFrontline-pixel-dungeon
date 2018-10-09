package com.gfpixel.gfpixeldungeon.actors.mobs;

import com.gfpixel.gfpixeldungeon.actors.Char;
import com.gfpixel.gfpixeldungeon.items.Generator;
import com.gfpixel.gfpixeldungeon.items.potions.PotionOfHealing;
import com.gfpixel.gfpixeldungeon.sprites.JaguarSprite;
import com.watabou.utils.Random;

/**
 * Created by Android SDK on 2018-08-15.
 */

public class Jaguar extends Mob {

    {
        spriteClass = JaguarSprite.class;

        HP = HT = 70;
        defenseSkill = 25;
        baseSpeed = 0.9f;

        EXP = 11;
        maxLvl = 21;
        loot = Generator.Category.WEAPON;
        lootChance = 0.15f;
    }
//공격속도 2턴에 1번 제누와즈 발사, 착탄후 1턴후 폭발하며 폭발에 적중시 약화상태가 됨. 폭발 반경 내에 아이템은 사라지지않음. 근접했을때도 제누와즈를 발사함.
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
}