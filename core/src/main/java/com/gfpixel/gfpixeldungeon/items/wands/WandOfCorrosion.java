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

package com.gfpixel.gfpixeldungeon.items.wands;

import com.gfpixel.gfpixeldungeon.Assets;
import com.gfpixel.gfpixeldungeon.Dungeon;
import com.gfpixel.gfpixeldungeon.actors.Actor;
import com.gfpixel.gfpixeldungeon.actors.Char;
import com.gfpixel.gfpixeldungeon.actors.blobs.Blob;
import com.gfpixel.gfpixeldungeon.actors.blobs.CorrosiveGas;
import com.gfpixel.gfpixeldungeon.actors.buffs.Buff;
import com.gfpixel.gfpixeldungeon.actors.buffs.Ooze;
import com.gfpixel.gfpixeldungeon.effects.CellEmitter;
import com.gfpixel.gfpixeldungeon.effects.MagicMissile;
import com.gfpixel.gfpixeldungeon.effects.particles.CorrosionParticle;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.MagesStaff;
import com.gfpixel.gfpixeldungeon.mechanics.Ballistica;
import com.gfpixel.gfpixeldungeon.scenes.GameScene;
import com.gfpixel.gfpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.ColorMath;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class WandOfCorrosion extends Wand {

	{
		image = ItemSpriteSheet.WAND_CORROSION;

		collisionProperties = Ballistica.STOP_TARGET | Ballistica.STOP_TERRAIN;
	}

	@Override
	protected void onZap(Ballistica bolt) {
		Blob corrosiveGas = Blob.seed(bolt.collisionPos, 50 + 10 * level(), CorrosiveGas.class);
		CellEmitter.center(bolt.collisionPos).burst( CorrosionParticle.SPLASH, 10 );
		((CorrosiveGas)corrosiveGas).setStrength(level()+1);
		GameScene.add(corrosiveGas);

		for (int i : PathFinder.NEIGHBOURS9) {
			Char ch = Actor.findChar(bolt.collisionPos + i);
			if (ch != null) {
				processSoulMark(ch, chargesPerCast());
			}
		}
		
		if (Actor.findChar(bolt.collisionPos) == null){
			Dungeon.level.press(bolt.collisionPos, null, true);
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
	public void onHit(MagesStaff staff, Char attacker, Char defender, int damage) {
		// lvl 0 - 33%
		// lvl 1 - 50%
		// lvl 2 - 60%
		if (Random.Int( level() + 3 ) >= 2) {
			
			Buff.affect( defender, Ooze.class );
			CellEmitter.center(defender.pos).burst( CorrosionParticle.SPLASH, 5 );
			
		}
	}

	@Override
	public void staffFx(MagesStaff.StaffParticle particle) {
		particle.color( ColorMath.random( 0xAAAAAA, 0xFF8800) );
		particle.am = 0.6f;
		particle.setLifespan( 1f );
		particle.acc.set(0, 20);
		particle.setSize( 0.5f, 3f );
		particle.shuffleXY( 1f );
	}

}
