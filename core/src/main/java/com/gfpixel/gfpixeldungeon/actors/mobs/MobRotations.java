package com.gfpixel.gfpixeldungeon.actors.mobs;

import com.gfpixel.gfpixeldungeon.Dungeon;

import java.util.ArrayList;

import static com.gfpixel.gfpixeldungeon.Dungeon.Themes.SEWER;

public class MobRotations {
    public int Tier = -1;

    MobRotations(final int newTier) {
        Tier = newTier;
    }

    public ArrayList<Class<? extends Mob>> getRotation(int floor) {
        return null;
    };
}


