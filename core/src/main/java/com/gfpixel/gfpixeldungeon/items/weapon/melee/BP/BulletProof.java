package com.gfpixel.gfpixeldungeon.items.weapon.melee.BP;

import com.gfpixel.gfpixeldungeon.actors.Char;
import com.gfpixel.gfpixeldungeon.actors.hero.Hero;
import com.gfpixel.gfpixeldungeon.actors.hero.HeroSubClass;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.watabou.utils.Bundle;

public abstract class BulletProof extends MeleeWeapon {

    //BP- 모스버그의 방탄판, 사이가의 방탄판
    //스탯이 방어에만 치중된 무기

    private static final String CHARGEDDAMAGE = "chargeddamage";

    private int charged;

    public void charge(int damage) {
        charged += damage;
    }

    @Override
    public void onAttack( Char owner, Char enemy ) {
        super.onAttack(owner, enemy);

        if (((Hero)owner).subClass == HeroSubClass.BERSERKER && charged > 0) {
            enemy.damage(charged, this, 0x0000FF);
            charged = 0;
        }
    }

    @Override
    public boolean doEquip( Hero hero ) {
        charged = 0;
        return super.doEquip(hero);
    }

    @Override
    public boolean doUnequip(Hero hero, boolean collect, boolean single) {
        charged = 0;
        return super.doUnequip(hero, collect, single);
    }

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(CHARGEDDAMAGE, charged);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        charged = bundle.getInt(CHARGEDDAMAGE);
    }
}