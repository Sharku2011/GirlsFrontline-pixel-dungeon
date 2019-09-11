package com.gfpixel.gfpixeldungeon.actors.mobs;

import com.gfpixel.gfpixeldungeon.actors.Char;
import com.gfpixel.gfpixeldungeon.actors.blobs.ToxicGas;
import com.gfpixel.gfpixeldungeon.actors.buffs.Corrosion;
import com.gfpixel.gfpixeldungeon.actors.buffs.Paralysis;
import com.gfpixel.gfpixeldungeon.actors.buffs.Terror;
import com.gfpixel.gfpixeldungeon.actors.buffs.Vertigo;
import com.gfpixel.gfpixeldungeon.items.scrolls.ScrollOfPsionicBlast;
import com.gfpixel.gfpixeldungeon.items.weapon.enchantments.Grim;
import com.gfpixel.gfpixeldungeon.items.weapon.enchantments.Vampiric;
import com.gfpixel.gfpixeldungeon.sprites.TyphoonSprite;
import com.watabou.utils.Random;

public class Typhoon extends BeamChargeMob {

    {
        spriteClass = TyphoonSprite.class;

        HP = HT = 4500;
        EXP = 1000;
        defenseSkill = 0;
        baseSpeed = 0.8f;
        maxLvl = 50;

        properties.add(Property.ARMO);


        flying = true;
    }

    @Override
    public int damageReducer() {
        return 2;
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange(85, 85);
    }

    @Override
    public int attackSkill( Char target ) {
        return 1000;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 10);
    }

    {
        resistances.add( Corrosion.class );
        resistances.add( Vampiric.class );
        resistances.add( ToxicGas.class );
    }

    {
        immunities.add( Paralysis.class );
        immunities.add( Vertigo.class );
        immunities.add( Terror.class );
        immunities.add( Corrosion.class );
        immunities.add( ScrollOfPsionicBlast.class );
        immunities.add( Grim.class );
    }

    @Override
    public void die( Object cause ) {
        super.die( cause );
        //Badges.validateRare( this );
    }

}
