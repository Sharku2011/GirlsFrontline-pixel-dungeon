package com.gfpixel.gfpixeldungeon.actors.mobs;

public interface BeamChargeAttackInterface {
    boolean isCharging();
    boolean isCharged();
    int attackRange();
    void shootBeam();
    void charge();
}
