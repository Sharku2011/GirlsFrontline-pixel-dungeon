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

package com.gfpixel.gfpixeldungeon.sprites;

import com.gfpixel.gfpixeldungeon.Assets;
import com.gfpixel.gfpixeldungeon.effects.MagicMissile;
import com.watabou.noosa.TextureFilm;

public class NemeumSprite extends BeamChargeMobSprite {

    public NemeumSprite() {
        super();

        texture( Assets.NEMEUM );

        TextureFilm frames = new TextureFilm( texture, 26, 21 );

        idle = new Animation( 4, true );
        idle.frames( frames, 0 );

        charging = new Animation( 2, true);
        charging.frames( frames, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2 );

        chargeParticles = centerEmitter();
        chargeParticles.autoKill = false;
        chargeParticles.pour(MagicMissile.MagicParticle.ATTRACTING, 0.05f);
        chargeParticles.on = false;

        run = new Animation( 12, true );
        run.frames( frames, 5, 6, 7, 8, 9, 10 );

        attack = new Animation( 10, false );
        attack.frames( frames,  3, 4, 4, 1, 1 );
        zap = attack.clone();

        die = new Animation( 8, false );
        die.frames( frames, 1, 1, 11, 12 );

        play( idle );
    }
}