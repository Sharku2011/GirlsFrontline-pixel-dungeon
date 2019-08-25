package com.gfpixel.gfpixeldungeon.items.weapon.melee.MG;

import com.gfpixel.gfpixeldungeon.actors.Char;
import com.gfpixel.gfpixeldungeon.actors.buffs.Bleeding;
import com.gfpixel.gfpixeldungeon.actors.buffs.Bless;
import com.gfpixel.gfpixeldungeon.actors.buffs.Buff;
import com.gfpixel.gfpixeldungeon.actors.buffs.Light;
import com.gfpixel.gfpixeldungeon.actors.buffs.Speed;
import com.gfpixel.gfpixeldungeon.actors.hero.Hero;
import com.gfpixel.gfpixeldungeon.sprites.ItemSpriteSheet;

/**
 * Created by LoveKirsi on 2017-11-22.
 */

public class M2HB extends MachineGun {

    {
        image = ItemSpriteSheet.M2HB;

        tier = 5;
        DLY = 0.2f;
    }

    @Override
    public int max(int lvl) {
        return  Math.round(3f*(tier+1)) +    //5 base, down from 20
                lvl*Math.round(0.5f*(tier+3));   //+1 per level, down from +2
    }

    @Override
    public int damageRoll(Char owner) {
        if (owner instanceof Hero && owner.buffs(Bless.class).isEmpty()) {
            Buff.prolong(owner, Bless.class, 1.2f);
        }
        return super.damageRoll(owner);
    }

}