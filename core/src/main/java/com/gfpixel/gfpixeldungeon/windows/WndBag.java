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

package com.gfpixel.gfpixeldungeon.windows;

import com.gfpixel.gfpixeldungeon.Assets;
import com.gfpixel.gfpixeldungeon.Dungeon;
import com.gfpixel.gfpixeldungeon.SPDSettings;
import com.gfpixel.gfpixeldungeon.actors.hero.Belongings;
import com.gfpixel.gfpixeldungeon.actors.hero.Hero;
import com.gfpixel.gfpixeldungeon.items.EquipableItem;
import com.gfpixel.gfpixeldungeon.items.Gold;
import com.gfpixel.gfpixeldungeon.items.Item;
import com.gfpixel.gfpixeldungeon.items.armor.Armor;
import com.gfpixel.gfpixeldungeon.items.bags.Bag;
import com.gfpixel.gfpixeldungeon.items.bags.MagicalHolster;
import com.gfpixel.gfpixeldungeon.items.bags.PotionBandolier;
import com.gfpixel.gfpixeldungeon.items.bags.ScrollHolder;
import com.gfpixel.gfpixeldungeon.items.bags.VelvetPouch;
import com.gfpixel.gfpixeldungeon.items.food.Blandfruit;
import com.gfpixel.gfpixeldungeon.items.food.Food;
import com.gfpixel.gfpixeldungeon.items.potions.Potion;
import com.gfpixel.gfpixeldungeon.items.scrolls.Scroll;
import com.gfpixel.gfpixeldungeon.items.wands.Wand;
import com.gfpixel.gfpixeldungeon.items.weapon.Weapon;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.gfpixel.gfpixeldungeon.items.weapon.missiles.Boomerang;
import com.gfpixel.gfpixeldungeon.items.weapon.missiles.darts.Dart;
import com.gfpixel.gfpixeldungeon.messages.Messages;
import com.gfpixel.gfpixeldungeon.plants.BlandfruitBush;
import com.gfpixel.gfpixeldungeon.plants.Plant.Seed;
import com.gfpixel.gfpixeldungeon.scenes.GameScene;
import com.gfpixel.gfpixeldungeon.scenes.PixelScene;
import com.gfpixel.gfpixeldungeon.sprites.ItemSprite;
import com.gfpixel.gfpixeldungeon.sprites.ItemSpriteSheet;
import com.gfpixel.gfpixeldungeon.ui.Icons;
import com.gfpixel.gfpixeldungeon.ui.ItemSlot;
import com.gfpixel.gfpixeldungeon.ui.QuickSlotButton;
import com.watabou.gltextures.TextureCache;
import com.watabou.noosa.BitmapText;
import com.watabou.noosa.ColorBlock;
import com.watabou.noosa.Image;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.RectF;

public class WndBag extends WndTabbed {
	
	//FIXME this is getting cumbersome, there should be a better way to manage this
	public enum Mode {
		ALL,
		UNIDENTIFED,
		UNIDED_OR_CURSED,
		UPGRADEABLE,
		QUICKSLOT,
		FOR_SALE,
		WEAPON,
		ARMOR,
		ENCHANTABLE,
		WAND,
		SEED,
		FOOD,
		POTION,
		SCROLL,
		UNIDED_POTION_OR_SCROLL,
		EQUIPMENT,
		ALCHEMY
	}

	protected static final int COLS_P    = 4;
	protected static final int COLS_L    = 6;
	
	protected static final int SLOT_WIDTH	= 28;
	protected static final int SLOT_HEIGHT	= 28;
	protected static final int SLOT_MARGIN	= 1;
	
	protected static final int TITLE_HEIGHT	= 14;
	protected static final int TITLE_MARGIN = 3;
	
	private Listener listener;
	private WndBag.Mode mode;
	private String title;

	private int nCols;
	private int nRows;

	protected int count;
	protected int col;
	protected int row;
	
	private static Mode lastMode;
	private static Bag lastBag;
	
	public WndBag( Bag bag, Listener listener, Mode mode, String title ) {
		
		super();
		
		this.listener = listener;
		this.mode = mode;
		this.title = title;
		
		lastMode = mode;
		lastBag = bag;

		nCols = SPDSettings.landscape() ? COLS_L : COLS_P;
		nRows = (int)Math.ceil((Belongings.BACKPACK_SIZE + 4) / (float)nCols);

		int slotsWidth = SLOT_WIDTH * nCols + SLOT_MARGIN * (nCols - 1);
		int slotsHeight = SLOT_HEIGHT * nRows + SLOT_MARGIN * (nRows - 1);

		placeTitle( bag, slotsWidth );
		
		placeItems( bag );

		resize( slotsWidth, slotsHeight + TITLE_HEIGHT + TITLE_MARGIN );

		Belongings stuff = Dungeon.hero.belongings;
		Bag[] bags = {
			stuff.backpack,
			stuff.getItem( VelvetPouch.class ),
			stuff.getItem( ScrollHolder.class ),
			stuff.getItem( PotionBandolier.class ),
			stuff.getItem( MagicalHolster.class )};

		for (Bag b : bags) {
			if (b != null) {
				BagTab tab = new BagTab( b );
				add( tab );
				tab.select( b == bag );
			}
		}

		layoutTabs();
	}
	
	public static WndBag lastBag( Listener listener, Mode mode, String title ) {
		
		if (mode == lastMode && lastBag != null &&
			Dungeon.hero.belongings.backpack.contains( lastBag )) {
			
			return new WndBag( lastBag, listener, mode, title );
			
		} else {
			
			return new WndBag( Dungeon.hero.belongings.backpack, listener, mode, title );
			
		}
	}

	public static WndBag getBag( Class<? extends Bag> bagClass, Listener listener, Mode mode, String title ) {
		Bag bag = Dungeon.hero.belongings.getItem( bagClass );
		return bag != null ?
				new WndBag( bag, listener, mode, title ) :
				lastBag( listener, mode, title );
	}
	
	protected void placeTitle( Bag bag, int width ){
		
		RenderedText txtTitle = PixelScene.renderText(
				title != null ? Messages.titleCase(title) : Messages.titleCase( bag.name() ), 9 );
		txtTitle.hardlight( TITLE_COLOR );
		txtTitle.x = 1;
		txtTitle.y = (int)(TITLE_HEIGHT - txtTitle.baseLine()) / 2f - 1;
		PixelScene.align(txtTitle);
		add( txtTitle );

		Image gold = Icons.get( Icons.COINS );
		gold.x = width - gold.width() - 1;
		gold.y = (TITLE_HEIGHT - gold.height())/2f - 1;
		PixelScene.align(gold);
		add(gold);
		
		BitmapText amt = new BitmapText( Integer.toString(Dungeon.gold), PixelScene.pixelFont );
		amt.hardlight(TITLE_COLOR);
		amt.measure();
		amt.x = width - gold.width() - amt.width() - 2;
		amt.y = (TITLE_HEIGHT - amt.baseLine())/2f - 1;
		PixelScene.align(amt);
		add(amt);
	}
	
	protected void placeItems( Bag container ) {
		
		// Equipped items
		Belongings stuff = Dungeon.hero.belongings;
		placeItem( stuff.weapon != null ? stuff.weapon : new Placeholder( ItemSpriteSheet.WEAPON_HOLDER ) );
		placeItem( stuff.armor != null ? stuff.armor : new Placeholder( ItemSpriteSheet.ARMOR_HOLDER ) );
		placeItem( stuff.misc1 != null ? stuff.misc1 : new Placeholder( ItemSpriteSheet.RING_HOLDER ) );
		placeItem( stuff.misc2 != null ? stuff.misc2 : new Placeholder( ItemSpriteSheet.RING_HOLDER ) );

		// Items in the bag
		for (Item item : container.items.toArray(new Item[0])) {
			placeItem( item );
		}
		
		// Free Space
		while ((count - 4) < container.size) {
			placeItem( null );
		}
	}
	
	protected void placeItem( final Item item ) {

		int x = col * (SLOT_WIDTH + SLOT_MARGIN);
		int y = TITLE_HEIGHT + row * (SLOT_HEIGHT + SLOT_MARGIN) + Math.min(1, row) * TITLE_MARGIN;

		add( new ItemButton( item ).setPos( x, y ) );
		
		if (++col >= nCols) {
			col = 0;
			row++;
		}
		
		count++;
	}
	
	@Override
	public void onMenuPressed() {
		if (listener == null) {
			hide();
		}
	}
	
	@Override
	public void onBackPressed() {
		if (listener != null) {
			listener.onSelect( null );
		}
		super.onBackPressed();
	}
	
	@Override
	protected void onClick( Tab tab ) {
		hide();
		GameScene.show( new WndBag( ((BagTab)tab).bag, listener, mode, title ) );
	}
	
	@Override
	protected int tabHeight() {
		return 27;
	}
	
	private class BagTab extends Tab {
		
		private Image icon;

		private Bag bag;
		
		public BagTab( Bag bag ) {
			super();
			
			this.bag = bag;
			
			icon = icon();
			add( icon );
		}
		
		@Override
		protected void select( boolean value ) {
			super.select( value );
			icon.am = selected ? 1.0f : 0.6f;
		}
		
		@Override
		protected void layout() {
			super.layout();
			
			icon.copy( icon() );
			icon.x = x + (width - icon.width) / 2;
			icon.y = y + (height - icon.height) / 2 - 2 - (selected ? 0 : 1);
			if (!selected && icon.y < y + CUT) {
				RectF frame = icon.frame();
				frame.top += (y + CUT - icon.y) / icon.texture.height;
				icon.frame( frame );
				icon.y = y + CUT;
			}
		}
		
		private Image icon() {
			if (bag instanceof VelvetPouch) {
				return Icons.get( bag == lastBag ? Icons.SEED_POUCH_SELECTED : Icons.SEED_POUCH_UNSELECTED );
			} else if (bag instanceof ScrollHolder) {
				return Icons.get( bag == lastBag ? Icons.SCROLL_HOLDER_SELECTED : Icons.SCROLL_HOLDER_UNSELECTED );
			} else if (bag instanceof MagicalHolster) {
				return Icons.get( bag == lastBag ? Icons.WAND_HOLSTER_SELECTED : Icons.WAND_HOLSTER_UNSELECTED );
			} else if (bag instanceof PotionBandolier) {
				return Icons.get( bag == lastBag ? Icons.POTION_BANDOLIER_SELECTED : Icons.POTION_BANDOLIER_UNSELECTED );
			} else {
				return Icons.get( bag == lastBag ? Icons.BACKPACK_SELECTED : Icons.BACKPACK_UNSELECTED );
			}
		}
	}
	
	public static class Placeholder extends Item {
		{
			name = null;
		}
		
		public Placeholder( int image ) {
			this.image = image;
		}
		
		@Override
		public boolean isIdentified() {
			return true;
		}
		
		@Override
		public boolean isEquipped( Hero hero ) {
			return true;
		}
	}
	
	private class ItemButton extends ItemSlot {
		
		private static final int NORMAL		= 0x5250534D;
		private static final int EQUIPPED	= 0x5250534D;
		
		private Item item;
		private ColorBlock bg;
		
		public ItemButton( Item item ) {
			
			super( item );

			this.item = item;
			if (item instanceof Gold) {
				bg.visible = false;
			}
			
			width = SLOT_WIDTH;
			height = SLOT_HEIGHT;
		}
		
		@Override
		protected void createChildren() {
			bg = new ColorBlock( SLOT_WIDTH, SLOT_HEIGHT, NORMAL );
			add( bg );
			
			super.createChildren();
		}
		
		@Override
		protected void layout() {
			bg.x = x;
			bg.y = y;
			
			super.layout();
		}
		
		@Override
		public void item( Item item ) {
			
			super.item( item );
			if (item != null) {

				bg.texture( TextureCache.createSolid( item.isEquipped( Dungeon.hero ) ? EQUIPPED : NORMAL ) );
				if (item.cursed && item.cursedKnown) {
					bg.ra = +0.3f;
					bg.ga = -0.15f;
				} else if (!item.isIdentified()) {
					bg.ra = 0.2f;
					bg.ba = 0.2f;
				}
				
				if (item.name() == null) {
					enable( false );
				} else {
					enable(
						mode == Mode.FOR_SALE && !item.unique && (item.price() > 0) && (!item.isEquipped( Dungeon.hero ) || !item.cursed) ||
						mode == Mode.UPGRADEABLE && item.isUpgradable() ||
						mode == Mode.UNIDENTIFED && !item.isIdentified() ||
						mode == Mode.UNIDED_OR_CURSED && ((item instanceof EquipableItem || item instanceof Wand) && (!item.isIdentified() || item.cursed)) ||
						mode == Mode.QUICKSLOT && (item.defaultAction != null) ||
						mode == Mode.WEAPON && (item instanceof MeleeWeapon || item instanceof Boomerang) ||
						mode == Mode.ARMOR && (item instanceof Armor) ||
						mode == Mode.ENCHANTABLE && (item instanceof MeleeWeapon || item instanceof Boomerang || item instanceof Armor) ||
						mode == Mode.WAND && (item instanceof Wand) ||
						mode == Mode.SEED && (item instanceof Seed) ||
						mode == Mode.FOOD && (item instanceof Food) ||
						mode == Mode.POTION && (item instanceof Potion) ||
						mode == Mode.SCROLL && (item instanceof Scroll) ||
						mode == Mode.UNIDED_POTION_OR_SCROLL && (!item.isIdentified() && (item instanceof Scroll || item instanceof Potion)) ||
						mode == Mode.EQUIPMENT && (item instanceof EquipableItem) ||
						mode == Mode.ALCHEMY && ((item instanceof Seed && !(item instanceof BlandfruitBush.Seed)) || (item instanceof Blandfruit && ((Blandfruit) item).potionAttrib == null) || (item.getClass() == Dart.class)) ||
						mode == Mode.ALL
					);
					//extra logic for cursed weapons or armor
					if (!active && mode == Mode.UNIDED_OR_CURSED){
						if (item instanceof Weapon){
							Weapon w = (Weapon) item;
							enable(w.hasCurseEnchant());
						}
						if (item instanceof Armor){
							Armor a = (Armor) item;
							enable(a.hasCurseGlyph());
						}
					}
				}
			} else {
				bg.color( NORMAL );
			}
		}
		
		@Override
		protected void onTouchDown() {
			bg.brightness( 1.5f );
			Sample.INSTANCE.play( Assets.SND_CLICK, 0.7f, 0.7f, 1.2f );
		}

		protected void onTouchUp() {
			bg.brightness( 1.0f );
		}

		@Override
		protected void onClick() {
			if (!lastBag.contains(item) && !item.isEquipped(Dungeon.hero)){

				hide();

			} else if (listener != null) {
				
				hide();
				listener.onSelect( item );
				
			} else {
				
				GameScene.show(new WndItem( WndBag.this, item ) );
				
			}
		}
		
		@Override
		protected boolean onLongClick() {
			if (listener == null && item.defaultAction != null) {
				hide();
				Dungeon.quickslot.setSlot( 0 , item );
				QuickSlotButton.refresh();
				return true;
			} else {
				return false;
			}
		}
	}
	
	public interface Listener {
		void onSelect( Item item );
	}
}
