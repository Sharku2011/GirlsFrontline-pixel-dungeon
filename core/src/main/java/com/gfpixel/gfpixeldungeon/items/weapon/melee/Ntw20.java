package com.gfpixel.gfpixeldungeon.items.weapon.melee;

import com.gfpixel.gfpixeldungeon.sprites.ItemSpriteSheet;

/**
 * Created by Kirsi on 2017-11-21.
 * Girls Piexl Dungeon , Weapon NTW20
 */

public class Ntw20 extends MeleeWeapon {

    {
        image = ItemSpriteSheet.NTW20;

        tier = 6;
        ACC = 1.75f;
        DLY = 5f;
        RCH = 50;
    }


    @Override
    public int max(int lvl) {
        return  Math.round(17.4f*(tier+1)) +    //40 base, up from 35
                lvl*Math.round(3.1f*(tier+5)); //+4 per level, up from +3
    }

}