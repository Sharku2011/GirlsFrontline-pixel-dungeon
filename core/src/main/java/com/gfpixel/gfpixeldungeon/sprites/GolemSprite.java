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
import com.gfpixel.gfpixeldungeon.actors.mobs.Jupiter;
import com.gfpixel.gfpixeldungeon.effects.Beam;
import com.gfpixel.gfpixeldungeon.effects.MagicMissile;
import com.gfpixel.gfpixeldungeon.effects.particles.ElmoParticle;
import com.gfpixel.gfpixeldungeon.tiles.DungeonTilemap;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.particles.Emitter;

public class GolemSprite extends MobSprite {

	private int zapPos;

	private Animation charging;
	private Emitter chargeParticles;

	public GolemSprite() {
		super();
		
		texture( Assets.GOLEM );
		
		TextureFilm frames = new TextureFilm( texture, 16, 16 );
		
		idle = new Animation( 4, true );
		idle.frames( frames, 0, 1 );

		charging = new Animation( 12, true);
		charging.frames( frames, 6 );

		chargeParticles = centerEmitter();
		chargeParticles.autoKill = false;
		chargeParticles.pour(MagicMissile.MagicParticle.ATTRACTING, 0.05f);
		chargeParticles.on = false;

		run = new Animation( 12, true );
		run.frames( frames, 2, 3, 4, 5 );
		
		attack = new Animation( 10, false );
		attack.frames( frames,  7, 8, 9 );
		zap = attack.clone();

		die = new Animation( 8, false );
		die.frames( frames, 10, 11, 12, 13 );

		play( idle );
	}

	@Override
	public void link(Char ch) {
		super.link(ch);
		if (((Jupiter)ch).beamCharged) play(charging);
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
	public void zap( int pos ) {
		zapPos = pos;
		super.zap( pos );
	}

	@Override
	public void onComplete( Animation anim ) {
		super.onComplete( anim );

		if (anim == zap) {
			idle();
			if (Actor.findChar(zapPos) != null){
				parent.add(new Beam.DeathRay(center(), Actor.findChar(zapPos).sprite.center()));
			} else {
				parent.add(new Beam.DeathRay(center(), DungeonTilemap.raisedTileCenterToWorld(zapPos)));
			}
			((Jupiter)ch).deathGaze();
			ch.next();
		} else if (anim == die){
			chargeParticles.killAndErase();
		}
	}
}
