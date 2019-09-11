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
import com.gfpixel.gfpixeldungeon.actors.Actor;
import com.gfpixel.gfpixeldungeon.actors.Char;
import com.gfpixel.gfpixeldungeon.actors.mobs.Hydra;
import com.gfpixel.gfpixeldungeon.effects.Beam;
import com.gfpixel.gfpixeldungeon.effects.MagicMissile;
import com.gfpixel.gfpixeldungeon.tiles.DungeonTilemap;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.particles.Emitter;

public class HydraSprite extends MobSprite implements BeamChargeMobSpriteInterface {

    private int beamTarget;

    private Animation charging;
    private Emitter chargeParticles;

    public HydraSprite() {
        super();

        texture( Assets.HYDRA );

        TextureFilm frames = new TextureFilm( texture, 48, 35 );

        idle = new Animation( 2, true );
        idle.frames( frames, 0, 0, 0, 0 );

        charging = new Animation( 14, true);
        charging.frames( frames, 9 );

        chargeParticles = centerEmitter();
        chargeParticles.autoKill = false;
        chargeParticles.pour(MagicMissile.MagicParticle.ATTRACTING, 0.05f);
        chargeParticles.on = false;

        run = new Animation( 10, true );
        run.frames( frames, 4, 5, 6, 7, 8 );

        attack = new Animation( 25, false );
        attack.frames( frames, 0, 1, 0, 2, 0, 1, 0, 2, 0 );
        zap = new Animation( 15, false );
        zap.frames( frames, 0, 3, 0 );

        die = new Animation( 10, false );
        die.frames( frames, 9, 10, 11 );

        play( idle );
    }


    @Override
    public void link(Char ch) {
        super.link(ch);
        if (((Hydra)ch).isCharged()) play(charging);
    }

    @Override
    public void update() {
        super.update();
        chargeParticles.pos(center());
        chargeParticles.visible = visible;
    }

    public void charge( int pos ){
        turnTo(ch.pos, pos);
        play(charging);
    }

    @Override
    public void play(Animation anim) {
        chargeParticles.on = anim == charging;
        super.play(anim);
    }

    @Override
    public void attack( int pos ) {
        beamTarget = pos;
        super.attack( pos );
    }

    @Override
    public void onComplete( Animation anim ) {
        super.onComplete( anim );

        if (anim == attack) {
            idle();

            if (Actor.findChar(beamTarget) != null){
                parent.add(new Beam.DeathRay(center(), Actor.findChar( beamTarget ).sprite.center()));
            } else {
                parent.add(new Beam.DeathRay(center(), DungeonTilemap.raisedTileCenterToWorld( beamTarget )));
            }
            ((Hydra)ch).shootBeam();
            ch.next();
        } else if (anim == charging) {
            ((Hydra)ch).charge();
            ch.next();
        } else if (anim == die){
            chargeParticles.killAndErase();
        }
    }

}

