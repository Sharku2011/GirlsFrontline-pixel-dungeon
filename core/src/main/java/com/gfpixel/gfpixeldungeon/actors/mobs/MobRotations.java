package com.gfpixel.gfpixeldungeon.actors.mobs;

import java.util.ArrayList;


public class MobRotations {
    public int Tier = -1;

    MobRotations(final int newTier) {
        Tier = newTier;
    }

    public ArrayList<Class<? extends Mob>> getRotation(int floor) {
        return null;
    }
}


