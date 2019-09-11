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

public class JupiterSprite extends BeamChargeMobSprite {

    public JupiterSprite() {
        super();

        texture( Assets.JUPITER );

        TextureFilm frames = new TextureFilm( texture, 73, 38 );

        idle = new Animation( 8, true );
        idle.frames( frames, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 0, 0, 0, 0, 0 );

        charging = new Animation( 12, true);
        charging.frames( frames, 3 );

        chargeParticles = centerEmitter();
        chargeParticles.autoKill = false;
        chargeParticles.pour(MagicMissile.MagicParticle.ATTRACTING, 0.05f);
        chargeParticles.on = false;

        run = new Animation( 12, true );
        run.frames( frames, 0 );

        attack = new Animation( 13, false );
        attack.frames( frames,  4, 5, 6, 7, 2);

        die = new Animation( 8, false );
        die.frames( frames, 0, 8, 9, 10 );

        play( idle );
    }
}
