package com.gfpixel.gfpixeldungeon.items.weapon.melee.SR;

import com.gfpixel.gfpixeldungeon.actors.Char;
import com.gfpixel.gfpixeldungeon.actors.buffs.Buff;
import com.gfpixel.gfpixeldungeon.actors.buffs.Charm;
import com.gfpixel.gfpixeldungeon.actors.buffs.Speed;
import com.gfpixel.gfpixeldungeon.actors.hero.Hero;
import com.gfpixel.gfpixeldungeon.sprites.ItemSpriteSheet;

/**
 * Created by Kirsi on 2017-11-21.
 * Girls Piexl Dungeon , Weapon NTW20
 */

public class Ntw20 extends SniperRifle {

    {
        image = ItemSpriteSheet.NTW20;

        tier = 6;
        ACC = 5f;
        DLY = 6f;
        RCH = 1000;
    }


    @Override
    public int max(int lvl) {
        return  Math.round(17.4f*(tier+1)) +    //40 base, up from 35
                lvl*Math.round(5.6f*(tier+5)); //+4 per level, up from +3
    }

    @Override
    public void onAttack(Char owner, Char enemy ) {
        if (owner instanceof Hero && owner.buffs(Speed.class).isEmpty()) {
            Buff.prolong(owner, Charm.class, 10.5f);
        }
    }

}