/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Girls Frontline Pixel Dungeon
 * Copyright (C) 2018-2018 Lycoris
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.gfpixel.gfpixeldungeon.items.wands;

import com.gfpixel.gfpixeldungeon.actors.Actor;
import com.gfpixel.gfpixeldungeon.actors.Char;
import com.gfpixel.gfpixeldungeon.actors.buffs.Buff;
import com.gfpixel.gfpixeldungeon.actors.buffs.Recharging;
import com.gfpixel.gfpixeldungeon.effects.SpellSprite;
import com.gfpixel.gfpixeldungeon.items.Bomb;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.MagesStaff;
import com.gfpixel.gfpixeldungeon.mechanics.Ballistica;
import com.gfpixel.gfpixeldungeon.messages.Messages;
import com.gfpixel.gfpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Bundle;

/**
 * Created by LoveKirsi on 2017-11-22.
 */

public class M79 extends DamageWand {
    {
        image = ItemSpriteSheet.M79;

        collisionProperties = Ballistica.STOP_TERRAIN;

        unique = true;
        bones = false;
    }

    public int min(int lvl){
        return 1;
    }

    public int max(int lvl){
        return 1;
    }

    private float damageStack = 0;
    private static final String STACK     = "DamageStack";

    @Override
    protected void onZap( Ballistica bolt ) {

        Char ch = Actor.findChar( bolt.collisionPos );
        if (ch != null) {

            processSoulMark(ch, chargesPerCast());
            ch.damage(damageRoll(), this);

            ch.sprite.burst(0xFFFFFFFF, level() / 2 + 2);
            new Bomb().explode(bolt.collisionPos, damageStack);
            damageStack = 0;
        }
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }


    @Override
    public void onHit(MagesStaff staff, Char attacker, Char defender, int damage) {
        Buff.prolong( attacker, Recharging.class, 1 + staff.level()/2f);
        SpellSprite.show(attacker, SpellSprite.CHARGE);
    }


    //@Override
    public boolean chargeMe(){

            super.curCharges = 1;
            return true;

    }

    //@Override
    public int addStack(int stack){
        if(damageStack == 0){
            stack = 1;
        }
        damageStack = (float)(stack * 0.10);
        //Log.d("TEST0909",""+damageStack);
        return (int)stack;
    }

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put( STACK , damageStack );
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        if (bundle.contains(STACK)){
            damageStack = bundle.getFloat(STACK);
        }else{
            damageStack = 0;
        }
    }

    //@Override
    //public boolean getUniq(){ return true; }

    @Override
    public String statsDesc(){
        return Messages.get(this, "stats_desc", returnPlusDamage());
    };

    public int returnPlusDamage(){
        return (int)(damageStack * 100 + 100);
    }
    protected int initialCharges() {
        return 1;
    }

}
