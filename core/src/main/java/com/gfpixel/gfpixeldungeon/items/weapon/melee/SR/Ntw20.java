package com.gfpixel.gfpixeldungeon.items.weapon.melee.SR;

import com.gfpixel.gfpixeldungeon.sprites.ItemSpriteSheet;

/**
 * Created by Kirsi on 2017-11-21.
 * Girls Piexl Dungeon , Weapon NTW20
 */

public class Ntw20 extends SniperRifle {

    {
        image = ItemSpriteSheet.NTW20;

        tier = 6;
        ACC = 1.75f;
        DLY = 9f;
        RCH = 50;
    }


    @Override
    public int max(int lvl) {
        return  Math.round(17.4f*(tier+1)) +    //40 base, up from 35
                lvl*Math.round(5.6f*(tier+5)); //+4 per level, up from +3
    }

}