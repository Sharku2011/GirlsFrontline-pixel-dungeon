/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2018 Evan Debenham
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

package com.gfpixel.gfpixeldungeon.items.weapon.melee;

import com.gfpixel.gfpixeldungeon.Assets;
import com.gfpixel.gfpixeldungeon.actors.Char;
import com.gfpixel.gfpixeldungeon.actors.hero.Hero;
import com.gfpixel.gfpixeldungeon.actors.mobs.Mob;
import com.gfpixel.gfpixeldungeon.effects.Beam;
import com.gfpixel.gfpixeldungeon.effects.particles.StaffParticle;
import com.gfpixel.gfpixeldungeon.items.Item;
import com.gfpixel.gfpixeldungeon.items.bags.Bag;
import com.gfpixel.gfpixeldungeon.items.wands.Wand;
import com.gfpixel.gfpixeldungeon.items.wands.WandOfGenoise;
import com.gfpixel.gfpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Cypros extends MeleeWeapon {
    {
        mode = Mode.TRAVAILLER;
        image = ItemSpriteSheet.TRAVAILLER;

        defaultAction = AC_ZAP;
        usesTargeting = true;

        unique = true;
        bones = false;

        tier= 2;
        ACC = 1.1f; //10% boost to accuracy
    }

    private Wand wand;

    enum Mode {
        TRAVAILLER,/*shotgun*/
        CONFIRE,/*rifle*/
        MAGNUM/*pistol*/
    }

    private static Mode mode;
    public Mode getMode() { return mode; }

    public static final String AC_ZAP	= "ZAP";
    public static final String AC_SWITCH= "SWITCH";
    private static final float STAFF_SCALE_FACTOR = 0.75f;

    public Cypros(){
        Wand wand = new WandOfGenoise();
        wand.identify();
        wand.cursed = false;
        this.wand = wand;
        wand.maxCharges = 1;
        wand.curCharges = wand.maxCharges;
    }

    public static void switchMode(Cypros cypros) {
        Cypros.Mode newMode;
        switch (cypros.getMode()) {
            case TRAVAILLER:
                newMode = Mode.CONFIRE;
                break;
            case CONFIRE: default:
                newMode = Mode.TRAVAILLER;
                break;
        }

        cypros.setMode(newMode);
    }

    public void setMode(Mode newMode) {
        if (mode == newMode) {
            return;
        }
        mode = newMode;
        switch (mode) {
            case TRAVAILLER: default:
                image = ItemSpriteSheet.TRAVAILLER;
                RCH = 1;
                DLY = 1f;
                ACC = 1.1f;
                curUser.spend(2f);
                break;
            case CONFIRE:
                image = ItemSpriteSheet.CONFIRE;
                RCH = 3;
                DLY = 3f;
                ACC = 1.5f;
                curUser.spend(1f);
                break;
        }
        updateQuickslot();

        curUser.busy();
        curUser.next();
    }

    @Override
    public int damageRoll(Char owner) {

        if (curUser != null && mode == Mode.CONFIRE) {
            Char ch = curUser.enemy();
            if (ch != null) {
                Sample.INSTANCE.play(Assets.SND_ZAP);
                curUser.sprite.parent.add(new Beam.DeathRay(curUser.sprite.center(), ch.sprite.center()));
            }
        }

        Hero hero = (Hero)owner;
        Char enemy = hero.enemy();
        if (enemy instanceof Mob && ((Mob) enemy).surprisedBy(hero)) {
            //deals 85% toward max to max on surprise, instead of min to max.
            int diff = max() - min();
            int damage = augment.damageFactor(Random.NormalIntRange(
                    min() + Math.round(diff*0.85f),
                    max()));
            int exStr = hero.STR() - STRReq();
            if (exStr > 0) {
                damage += Random.IntRange(0, exStr);
            }
            return damage;
        }

        return super.damageRoll(owner);
    }

    @Override
    public int min(int lvl) {
        return  Math.round(2.5f*tier) +         //base
                lvl;                            //level scaling
    }

    @Override
    public int max(int lvl) {

        switch (mode) {
            case TRAVAILLER:
                return  Math.round(5.f*(tier+1)) +        // 15 base
                        lvl*Math.round(2.5f*(tier+1));    //+8 per level
            case CONFIRE:
                return  Math.round(7.f*(tier+1)) +        // 21 base
                        lvl*Math.round(3.5f*(tier+1));    //+11 per level
            default:
                return super.max(lvl);
        }
    }

    @Override
    public int defenseFactor( Char owner ) {
        switch (mode) {
            case CONFIRE: default:
                return 0;
            case TRAVAILLER:
                return 5+2*level();     //5 extra defence, plus 2 per level;
        }
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_ZAP);
        actions.add(AC_SWITCH);

        return actions;
    }

    @Override
    public void activate( Char ch ) {
        if(wand != null) wand.charge( ch, STAFF_SCALE_FACTOR );
    }

    @Override
    public void execute(Hero hero, String action) {

        super.execute(hero, action);

        if (action.equals(AC_ZAP)){
            wand.execute(hero, AC_ZAP);
        }
        if (action.equals(AC_SWITCH)) {
            switchMode(this);
        }
    }

    @Override
    public Emitter emitter() {
        if (wand == null) return null;
        Emitter emitter = new Emitter();
        emitter.pos(12.5f, 3);
        emitter.fillTarget = false;
        emitter.pour(StaffParticleFactory, 0.1f);
        return emitter;
    }

    @Override
    public boolean collect( Bag container ) {
        if (super.collect(container)) {
            if (container.owner != null) {
                wand.charge(container.owner, STAFF_SCALE_FACTOR);
            }
            return true;
        } else {
            return false;
        }
    }

    public void gainCharge( float amt ){
        if (wand != null){
            wand.gainCharge(amt);
        }
    }

    @Override
    public Item upgrade(boolean enchant) {
        super.upgrade( enchant );

        if (wand != null) {
            wand.upgrade();
            wand.curCharges = 1;
            updateQuickslot();
        }

        return this;
    }

    @Override
    public String status() {
        return wand.status();
    }

    @Override
    public int price() {
        return 0;
    }

    private static final String WAND        = "wand";
    private static final String MODE        = "mode";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(WAND, wand);
        bundle.put(MODE, mode.ordinal());
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        wand = (Wand) bundle.get(WAND);
        Mode newMode;
        switch (bundle.getInt(MODE)) {
            case 0: default:
                newMode = Mode.TRAVAILLER;
                break;
            case 1:
                newMode = Mode.CONFIRE;
                break;
        }
        setMode(newMode);
    }

    private final Emitter.Factory StaffParticleFactory = new Emitter.Factory() {
        @Override
        //reimplementing this is needed as instance creation of new staff particles must be within this class.
        public void emit(Emitter emitter, int index, float x, float y ) {
            StaffParticle c = (StaffParticle)emitter.getFirstAvailable(StaffParticle.class);
            if (c == null) {
                c = new StaffParticle();
                emitter.add(c);
            }
            c.reset(x, y);
        }

        @Override
        //some particles need light mode, others don't
        public boolean lightMode() {
            return wand.curCharges != 0;
        }
    };



}