package com.gfpixel.gfpixeldungeon.actors.mobs;

import com.gfpixel.gfpixeldungeon.Dungeon;
import com.gfpixel.gfpixeldungeon.actors.Char;
import com.gfpixel.gfpixeldungeon.effects.Speck;
import com.gfpixel.gfpixeldungeon.items.Item;
import com.gfpixel.gfpixeldungeon.items.potions.PotionOfHealing;
import com.gfpixel.gfpixeldungeon.items.weapon.enchantments.Vampiric;
import com.gfpixel.gfpixeldungeon.sprites.BatSprite;
import com.gfpixel.gfpixeldungeon.sprites.RipperSprite;
import com.watabou.utils.Random;

public class Ripper extends Mob {

    {
        spriteClass = RipperSprite.class;

        HP = HT = 30;
        defenseSkill = 30;
        baseSpeed = 1f;

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
}
