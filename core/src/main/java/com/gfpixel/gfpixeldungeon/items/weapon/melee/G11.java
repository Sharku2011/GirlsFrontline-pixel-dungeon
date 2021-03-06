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

package com.gfpixel.gfpixeldungeon.items.weapon.melee;

import com.gfpixel.gfpixeldungeon.Assets;
import com.gfpixel.gfpixeldungeon.Badges;
import com.gfpixel.gfpixeldungeon.Dungeon;
import com.gfpixel.gfpixeldungeon.actors.Char;
import com.gfpixel.gfpixeldungeon.actors.hero.Hero;
import com.gfpixel.gfpixeldungeon.actors.hero.HeroSubClass;
import com.gfpixel.gfpixeldungeon.effects.particles.ElmoParticle;
import com.gfpixel.gfpixeldungeon.effects.particles.StaffParticle;
import com.gfpixel.gfpixeldungeon.items.Item;
import com.gfpixel.gfpixeldungeon.items.bags.Bag;
import com.gfpixel.gfpixeldungeon.items.scrolls.ScrollOfRecharging;
import com.gfpixel.gfpixeldungeon.items.wands.Wand;
import com.gfpixel.gfpixeldungeon.items.wands.WandOfCorrosion;
import com.gfpixel.gfpixeldungeon.items.wands.WandOfCorruption;
import com.gfpixel.gfpixeldungeon.items.wands.WandOfDisintegration;
import com.gfpixel.gfpixeldungeon.items.wands.WandOfRegrowth;
import com.gfpixel.gfpixeldungeon.messages.Messages;
import com.gfpixel.gfpixeldungeon.scenes.GameScene;
import com.gfpixel.gfpixeldungeon.sprites.ItemSpriteSheet;
import com.gfpixel.gfpixeldungeon.utils.GLog;
import com.gfpixel.gfpixeldungeon.windows.WndBag;
import com.gfpixel.gfpixeldungeon.windows.WndItem;
import com.gfpixel.gfpixeldungeon.windows.WndOptions;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public class G11 extends MeleeWeapon {

	private Wand wand;

	public static final String AC_IMBUE = "IMBUE";
	public static final String AC_ZAP	= "ZAP";

	private static final float STAFF_SCALE_FACTOR = 0.75f;

	{
		image = ItemSpriteSheet.G11;

		tier = 1;

		defaultAction = AC_ZAP;
		usesTargeting = true;

		unique = true;
		bones = false;
	}

	public G11() {
		wand = null;
	}

	@Override
	public int max(int lvl) {
		return  4*(tier+1) +    //8 base damage, down from 10
				lvl*(tier+1);   //scaling unaffected
	}

	public G11(Wand wand){
		this();
		wand.identify();
		wand.cursed = false;
		this.wand = wand;
		wand.maxCharges = Math.min(wand.maxCharges + 1, 10);
		wand.curCharges = wand.maxCharges;
		name = Messages.get(wand, "staff_name");
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions( hero );
		actions.add(AC_IMBUE);
		if (wand!= null && wand.curCharges > 0) {
			actions.add( AC_ZAP );
		}
		return actions;
	}

	@Override
	public void activate( Char ch ) {
		if(wand != null) wand.charge( ch, STAFF_SCALE_FACTOR );
	}

	@Override
	public void execute(Hero hero, String action) {

		super.execute(hero, action);

		if (action.equals(AC_IMBUE)) {

			curUser = hero;
			GameScene.selectItem(itemSelector, WndBag.Mode.WAND, Messages.get(this, "prompt"));

		} else if (action.equals(AC_ZAP)){

			if (wand == null) {
				GameScene.show(new WndItem(null, this, true));
				return;
			}

			wand.execute(hero, AC_ZAP);
		}
	}

	@Override
	public int proc(Char attacker, Char defender, int damage) {
		if (wand != null && Dungeon.hero.subClass == HeroSubClass.BATTLEMAGE) {
			if (wand.curCharges < wand.maxCharges) wand.partialCharge += 0.33f;
			ScrollOfRecharging.charge((Hero)attacker);
			wand.onHit(this, attacker, defender, damage);
		}
		return super.proc(attacker, defender, damage);
	}

	@Override
	public int reachFactor(Char owner) {
		int reach = super.reachFactor(owner);
		if (owner instanceof Hero
				&& wand instanceof WandOfDisintegration
				&& ((Hero)owner).subClass == HeroSubClass.BATTLEMAGE){
			reach++;
		}
		return reach;
	}

	@Override
	public boolean collect( Bag container ) {
		if (super.collect(container)) {
			if (container.owner != null && wand != null) {
				wand.charge(container.owner, STAFF_SCALE_FACTOR);
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void onDetach( ) {
		if (wand != null) wand.stopCharging();
	}

	public Item imbueWand(Wand wand, Char owner){

		wand.cursed = false;
		this.wand = null;

		//syncs the level of the two items.
		int targetLevel = Math.max(this.level(), wand.level());

		//if the staff's level is being overridden by the wand, preserve 1 upgrade
		if (wand.level() >= this.level() && this.level() > 0) targetLevel++;

		int staffLevelDiff = targetLevel - this.level();
		if (staffLevelDiff > 0)
			this.upgrade(staffLevelDiff);
		else if (staffLevelDiff < 0)
			this.degrade(Math.abs(staffLevelDiff));

		int wandLevelDiff = targetLevel - wand.level();
		if (wandLevelDiff > 0)
			wand.upgrade(wandLevelDiff);
		else if (wandLevelDiff < 0)
			wand.degrade(Math.abs(wandLevelDiff));

		this.wand = wand;
		wand.maxCharges = Math.min(wand.maxCharges + 1, 10);
		wand.curCharges = wand.maxCharges;
		wand.identify();
		if (owner != null) wand.charge(owner);

		name = Messages.get(wand, "staff_name");

		//This is necessary to reset any particles.
		//FIXME this is gross, should implement a better way to fully reset quickslot visuals
		int slot = Dungeon.quickslot.getSlot(this);
		if (slot != -1){
			Dungeon.quickslot.clearSlot(slot);
			updateQuickslot();
			Dungeon.quickslot.setSlot( slot, this );
			updateQuickslot();
		}
		
		Badges.validateItemLevelAquired(this);

		return this;
	}
	
	public void gainCharge( float amt ){
		if (wand != null){
			wand.gainCharge(amt);
		}
	}

	public Class<?extends Wand> wandClass(){
		return wand != null ? wand.getClass() : null;
	}

	@Override
	public Item upgrade(boolean enchant) {
		super.upgrade( enchant );

		if (wand != null) {
			int curCharges = wand.curCharges;
			wand.upgrade();
			//gives the wand one additional charge
			wand.maxCharges = Math.min(wand.maxCharges + 1, 10);
			wand.curCharges = Math.min(wand.curCharges + 1, 10);
			updateQuickslot();
		}

		return this;
	}

	@Override
	public Item degrade() {
		super.degrade();

		if (wand != null) {
			int curCharges = wand.curCharges;
			wand.degrade();
			//gives the wand one additional charge
			wand.maxCharges = Math.min(wand.maxCharges + 1, 10);
			wand.curCharges = curCharges-1;
			updateQuickslot();
		}

		return this;
	}

	@Override
	public String status() {
		if (wand == null) return super.status();
		else return wand.status();
	}

	@Override
	public String info() {
		String info = super.info();

		if (wand == null){
			info += "\n\n" + Messages.get(this, "no_wand");
		} else {
			info += "\n\n" + Messages.get(this, "has_wand", Messages.get(wand, "name")) + " " + wand.statsDesc();
		}

		return info;
	}

	@Override
	public Emitter emitter() {
		if (wand == null) return null;
		Emitter emitter = new Emitter();
		emitter.pos(12.5f, 3);
		emitter.fillTarget = false;
		emitter.pour(StaffParticleFactory, 0.1f);
		return emitter;
	}

	private static final String WAND = "wand";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(WAND, wand);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		wand = (Wand) bundle.get(WAND);
		if (wand != null) {
			wand.maxCharges = Math.min(wand.maxCharges + 1, 10);
			name = Messages.get(wand, "staff_name");
		}
	}

	@Override
	public int price() {
		return 0;
	}

	private final WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect( final Item item ) {
			if (item != null) {

				if (!item.isIdentified()) {
					GLog.w(Messages.get(G11.class, "id_first"));
					return;
				} else if (item.cursed){
					GLog.w(Messages.get(G11.class, "cursed"));
					return;
				}

				if (wand == null){
					applyWand((Wand)item);
				} else {
					final int newLevel =
							item.level() >= level() ?
									level() > 0 ?
										item.level() + 1
										: item.level()
									: level();
					GameScene.show(
							new WndOptions("",
									Messages.get(G11.class, "warning", newLevel),
									Messages.get(G11.class, "yes"),
									Messages.get(G11.class, "no")) {
								@Override
								protected void onSelect(int index) {
									if (index == 0) {
										applyWand((Wand)item);
									}
								}
							}
					);
				}
			}
		}

		private void applyWand(Wand wand){
			Sample.INSTANCE.play(Assets.SND_BURNING);
			curUser.sprite.emitter().burst( ElmoParticle.FACTORY, 12 );
			evoke(curUser);

			Dungeon.quickslot.clearItem(wand);

			wand.detach(curUser.belongings.backpack);

			GLog.p( Messages.get(G11.class, "imbue", wand.name()));
			imbueWand( wand, curUser );

			updateQuickslot();
		}
	};

	private final Emitter.Factory StaffParticleFactory = new Emitter.Factory() {
		@Override
		//reimplementing this is needed as instance creation of new staff particles must be within this class.
		public void emit( Emitter emitter, int index, float x, float y ) {
			StaffParticle c = (StaffParticle)emitter.getFirstAvailable(StaffParticle.class);
			if (c == null) {
				c = new StaffParticle();
				emitter.add(c);
			}
			c.reset(x, y);
		}

		@Override
		//some particles need light mode, others don't
		public boolean lightMode() {
			return !((wand instanceof WandOfDisintegration)
					|| (wand instanceof WandOfCorruption)
					|| (wand instanceof WandOfCorrosion)
					|| (wand instanceof WandOfRegrowth));
		}
	};


}
