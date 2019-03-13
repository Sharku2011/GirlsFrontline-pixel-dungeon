package com.gfpixel.gfpixeldungeon.items.weapon.melee.MG;

import com.gfpixel.gfpixeldungeon.sprites.ItemSpriteSheet;

/**
 * Created by LoveKirsi on 2017-11-22.
 */

public class Mg42 extends MachineGun {

    {
        image = ItemSpriteSheet.MG42;

        tier = 6;
        ACC = 0.65f;
        DLY = 0.1f;
    }

    @Override
    public int max(int lvl) {
        return  Math.round(1.3f*(tier+1)) +    //5 base, down from 20
                lvl*Math.round(0.4f*(tier+1));   //+1 per level, down from +2
    }

}


