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

import com.gfpixel.gfpixeldungeon.Assets;
import com.gfpixel.gfpixeldungeon.actors.Actor;
import com.gfpixel.gfpixeldungeon.actors.Char;
import com.gfpixel.gfpixeldungeon.actors.blobs.Blob;
import com.gfpixel.gfpixeldungeon.actors.blobs.ParalyticGas;
import com.gfpixel.gfpixeldungeon.effects.MagicMissile;
import com.gfpixel.gfpixeldungeon.items.weapon.enchantments.Venomous;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.G11;
import com.gfpixel.gfpixeldungeon.mechanics.Ballistica;
import com.gfpixel.gfpixeldungeon.scenes.GameScene;
import com.gfpixel.gfpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.ColorMath;
import com.watabou.utils.PathFinder;

public class M84 extends Wand {

    {
        image = ItemSpriteSheet.M84;

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

    @Override
    protected void onZap(Ballistica bolt) {
        Blob paralyticGas = Blob.seed(bolt.collisionPos, 50 + 10 * level(), ParalyticGas.class);
        GameScene.add(paralyticGas);

        for (int i : PathFinder.NEIGHBOURS9) {
            Char ch = Actor.findChar(bolt.collisionPos + i);
            if (ch != null) {
                processSoulMark(ch, chargesPerCast());
            }
        }
    }

    @Override
    protected void fx(Ballistica bolt, Callback callback) {
        MagicMissile.boltFromChar(
                curUser.sprite.parent,
                MagicMissile.CORROSION,
                curUser.sprite,
                bolt.collisionPos,
                callback);
        Sample.INSTANCE.play(Assets.SND_ZAP);
    }

    @Override
    public void onHit(G11 staff, Char attacker, Char defender, int damage) {
        //acts like venomous enchantment
        new Venomous().proc(staff, attacker, defender, damage);
    }

    @Override
    public void staffFx(G11.StaffParticle particle) {
        particle.color( ColorMath.random( 0x8844FF, 0x00FF00) );
        particle.am = 0.6f;
        particle.setLifespan( 1f );
        particle.acc.set(0, 20);
        particle.setSize( 0.5f, 2f);
        particle.shuffleXY( 1f );
    }

}
