package com.gfpixel.gfpixeldungeon.items.weapon.melee;

/**
 * Created by LoveKirsi on 2017-11-05.
 */

import com.gfpixel.gfpixeldungeon.actors.Char;
import com.gfpixel.gfpixeldungeon.items.Item;
import com.gfpixel.gfpixeldungeon.sprites.ItemSpriteSheet;

import java.util.ArrayList;

public class Ump40 extends MeleeWeapon {

    {
        image = ItemSpriteSheet.UMP40;

        tier = 1;
        RCH = 5;
    }
    public Char owner;
    public ArrayList<Item> items = new ArrayList<Item>();

    //@Override
    //public int proc(Char attacker, Char defender, int damage ) {
    //collect(hero.belongings.backpack);
    //   damage = 999;
    //    return damage;
    //}


    @Override
    public int max(int lvl) {
      return  3*(tier+1) +                	//20 base, down from 25
              lvl*(tier+1);	//+6 per level, up from +5
    }

}
