package com.gfpixel.gfpixeldungeon.items.weapon.melee.SR;

import com.gfpixel.gfpixeldungeon.items.weapon.melee.MeleeWeapon;

public abstract class  SniperRifle extends MeleeWeapon {

    //SR- 스프링필드, AWP, NTW20
    // 기본 사거리가 50 이상 반동이 5턴 이상인 무기
    {
        DLY = 5f;
        RCH = 50;
        ACC = 1.75f;
    }
}
