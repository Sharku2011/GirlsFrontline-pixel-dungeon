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

public class TyphoonSprite extends BeamChargeMobSprite {

    public TyphoonSprite() {
        super();

        texture( Assets.TYPHOON );

        TextureFilm frames = new TextureFilm( texture, 76, 48 );

        idle = new Animation( 2, true );
        idle.frames( frames, 0 );

        charging = new Animation( 14, true);
        charging.frames( frames, 4 );

        chargeParticles = centerEmitter();
        chargeParticles.autoKill = false;
        chargeParticles.pour(MagicMissile.MagicParticle.ATTRACTING, 0.05f);
        chargeParticles.on = false;

        run = new Animation( 8, true );
        run.frames( frames, 0, 1, 2, 3, 0 );

        //attack = new Animation( 1, false );
        //attack.frames( frames, 4, 4, 4, 4, 4, 4, 5, 5, 6, 6, 7, 7 );

        //zap = new Animation( 15, false );
        //zap.frames( frames, 4, 5, 6, 7 );

        attack = new Animation( 15, false );
        attack.frames( frames, 4, 5, 6, 7 );

        die = new Animation( 15, false );
        die.frames( frames, 0, 8, 9, 10 );

        play( idle );
    }
}
