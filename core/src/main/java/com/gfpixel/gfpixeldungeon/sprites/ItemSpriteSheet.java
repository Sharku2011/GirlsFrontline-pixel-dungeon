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
import com.watabou.noosa.TextureFilm;

public class ItemSpriteSheet {

	private static int WIDTH = 16;

	public static TextureFilm film = new TextureFilm( Assets.ITEMS, 16, 16 );

	private static int xy(int x, int y){
		x -= 1; y -= 1;
		if(y != 11) {
			return x + WIDTH * y;
		}else{
			return x + 16 * y;
		}
	}

	private static final int PLACEHOLDERS   =                               xy(1, 1);   //16 slots
	//null warning occupies space 0, should only show up if there's a bug.
	public static final int NULLWARN        = PLACEHOLDERS+0;
	public static final int WEAPON_HOLDER   = PLACEHOLDERS+1;
	public static final int ARMOR_HOLDER    = PLACEHOLDERS+2;
	public static final int WAND_HOLDER     = PLACEHOLDERS+3;
	public static final int RING_HOLDER     = PLACEHOLDERS+4;
	public static final int ARTIFACT_HOLDER = PLACEHOLDERS+5;
	public static final int POTION_HOLDER   = PLACEHOLDERS+6;
	public static final int SCROLL_HOLDER   = PLACEHOLDERS+7;
	public static final int SOMETHING       = PLACEHOLDERS+8;
	static{
		assignItemRect(NULLWARN,        16, 7);
		assignItemRect(WEAPON_HOLDER,   14, 14);
		assignItemRect(ARMOR_HOLDER,    14, 12);
		assignItemRect(WAND_HOLDER,     14, 14);
		assignItemRect(RING_HOLDER,     15,  10);
		assignItemRect(ARTIFACT_HOLDER, 15, 15);
		assignItemRect(POTION_HOLDER,   10, 14);
		assignItemRect(SCROLL_HOLDER,   15, 14);
		assignItemRect(SOMETHING,       8,  13);
	}


	private static final int UNCOLLECTIBLE  =                               xy(1, 2);   //16 slots
	public static final int GOLD            = UNCOLLECTIBLE+0;
	public static final int DEWDROP         = UNCOLLECTIBLE+1;
	public static final int PETAL           = UNCOLLECTIBLE+2;
	public static final int SANDBAG         = UNCOLLECTIBLE+3;
	public static final int DBL_BOMB        = UNCOLLECTIBLE+4;
	public static final int GUIDE_PAGE      = UNCOLLECTIBLE+5;
	public static final int MEMORY1      = UNCOLLECTIBLE+6;

	static{
		assignItemRect(GOLD,        15, 13);
		assignItemRect(DEWDROP,     10, 10);
		assignItemRect(PETAL,       8,  8);
		assignItemRect(SANDBAG,     10, 10);
		assignItemRect(DBL_BOMB,    14, 13);
		assignItemRect(GUIDE_PAGE,  10, 11);
		assignItemRect(MEMORY1,  8, 8);
	}

	private static final int CONTAINERS     =                               xy(1, 3);   //16 slots
	public static final int BONES           = CONTAINERS+0;
	public static final int REMAINS         = CONTAINERS+1;
	public static final int TOMB            = CONTAINERS+2;
	public static final int GRAVE           = CONTAINERS+3;
	public static final int CHEST           = CONTAINERS+4;
	public static final int LOCKED_CHEST    = CONTAINERS+5;
	public static final int CRYSTAL_CHEST   = CONTAINERS+6;
	public static final int EBONY_CHEST     = CONTAINERS+7;
	static{
		assignItemRect(BONES,           14, 11);
		assignItemRect(REMAINS,         14, 11);
		assignItemRect(TOMB,            14, 15);
		assignItemRect(GRAVE,           14, 15);
		assignItemRect(CHEST,           16, 14);
		assignItemRect(LOCKED_CHEST,    16, 14);
		assignItemRect(CRYSTAL_CHEST,   16, 14);
		assignItemRect(EBONY_CHEST,     16, 14);
	}

	private static final int SINGLE_USE     =                               xy(1, 4);   //16 slots
	public static final int ANKH            = SINGLE_USE+0;
	public static final int STYLUS          = SINGLE_USE+1;
	public static final int WEIGHT          = SINGLE_USE+2;
	public static final int SEAL            = SINGLE_USE+3;
	public static final int TORCH           = SINGLE_USE+4;
	public static final int BEACON          = SINGLE_USE+5;
	public static final int BOMB            = SINGLE_USE+6;
	public static final int HONEYPOT        = SINGLE_USE+7;
	public static final int SHATTPOT        = SINGLE_USE+8;
	public static final int IRON_KEY        = SINGLE_USE+9;
	public static final int GOLDEN_KEY      = SINGLE_USE+10;
	public static final int CRYSTAL_KEY     = SINGLE_USE+11;
	public static final int SKELETON_KEY    = SINGLE_USE+12;
	public static final int MASTERY         = SINGLE_USE+13;
	public static final int KIT             = SINGLE_USE+14;
	public static final int AMULET          = SINGLE_USE+15;
	static{
		assignItemRect(ANKH,            10, 16);
		assignItemRect(STYLUS,          12, 13);
		assignItemRect(WEIGHT,          14, 12);
		assignItemRect(SEAL,            9,  15);
		assignItemRect(TORCH,           15, 15);
		assignItemRect(BEACON,          16, 15);
		assignItemRect(BOMB,            10, 13);
		assignItemRect(HONEYPOT,        14, 12);
		assignItemRect(SHATTPOT,        14, 12);
		assignItemRect(IRON_KEY,        8,  14);
		assignItemRect(GOLDEN_KEY,      8,  14);
		assignItemRect(CRYSTAL_KEY,     8,  14);
		assignItemRect(SKELETON_KEY,    8,  14);
		assignItemRect(MASTERY,         13, 16);
		assignItemRect(KIT,             16, 15);
		assignItemRect(AMULET,          16, 16);
	}

	                                                                                    //32 free slots

	private static final int WEP_TIER1      =                               xy(1, 7);   //8 slots
	public static final int UMP45         	= WEP_TIER1+0;
	public static final int DP             	= WEP_TIER1+1;
	public static final int M9   = WEP_TIER1+2;
	public static final int UMP40          	= WEP_TIER1+3;
	public static final int DAGGER          = WEP_TIER1+4;
	public static final int G11             = WEP_TIER1+5;
	public static final int SR3             = WEP_TIER1+6;
	public static final int CANNON         	= WEP_TIER1+7;
	public static final int SRS         	= WEP_TIER1+8;
	public static final int THUNDER         	= WEP_TIER1+9;
	
	static{
		assignItemRect(UMP45, 	15, 16);
		assignItemRect(DP,     	15, 16);
		assignItemRect(M9,   	14, 16);
		assignItemRect(UMP40, 	16, 16);
		assignItemRect(DAGGER,  12, 13);
		assignItemRect(G11,     15, 16);
		assignItemRect(SR3,     15, 16);
		assignItemRect(CANNON,  15, 15);
		assignItemRect(SRS, 	16, 16);
		assignItemRect(THUNDER, 	15, 15);
	}

	private static final int WEP_TIER2      =                               xy(1, 8);   //8 slots
	public static final int M16      = WEP_TIER2+0;
	public static final int M1911        = WEP_TIER2+1;
	public static final int M1903           = WEP_TIER2+2;
	public static final int M1A1    = WEP_TIER2+3;
	public static final int NAGANTREVOLVER      = WEP_TIER2+4;
	public static final int G36            = WEP_TIER2+5;
	public static final int TRAVAILLER           = WEP_TIER2+6;
	public static final int CONFIRE          = WEP_TIER2+7;
	public static final int MAGNUMWEDDING	 = WEP_TIER2+8;
	static{
		assignItemRect(M16,					15, 16);
		assignItemRect(M1911,				12, 14);
		assignItemRect(M1903,				14, 16);
		assignItemRect(M1A1,				16, 16);
		assignItemRect(NAGANTREVOLVER,		13, 14);
		assignItemRect(G36,					16, 16);
		assignItemRect(TRAVAILLER,			15, 15);
		assignItemRect(CONFIRE,				14, 16);
		assignItemRect(MAGNUMWEDDING,		12, 12);
	}

	private static final int WEP_TIER3      =                               xy(1, 9);   //8 slots
	public static final int KS23           = WEP_TIER3+0;
	public static final int KAR98            = WEP_TIER3+1;
	public static final int NEGEV        = WEP_TIER3+2;
	public static final int MOS    = WEP_TIER3+3;
	public static final int KRISS             = WEP_TIER3+4;
	public static final int WA            = WEP_TIER3+5;
	public static final int C96            = WEP_TIER3+6;
	static{
		assignItemRect(KS23,           16, 16);
		assignItemRect(KAR98,            15, 15);
		assignItemRect(NEGEV,        16, 16);
		assignItemRect(MOS,    15, 14);
		assignItemRect(KRISS,             16, 16);
		assignItemRect(WA,            14, 14);
		assignItemRect(C96,            8, 12);
	}

	private static final int WEP_TIER4      =                               xy(9, 9);   //8 slots
	public static final int WIN97      		= WEP_TIER4+0;
	public static final int DRAGUNOV      	= WEP_TIER4+1;
	public static final int AK47         	= WEP_TIER4+2;
	public static final int HK416     		= WEP_TIER4+3;
	public static final int GUA91 			= WEP_TIER4+4;
	public static final int AWP             = WEP_TIER4+5;
	public static final int CROSSBOW        = WEP_TIER4+6;
	static{
		assignItemRect(WIN97,       15, 15);
		assignItemRect(DRAGUNOV,      16, 16);
		assignItemRect(AK47,           14, 14);
		assignItemRect(HK416,     14, 14);
		assignItemRect(GUA91, 14, 15);
		assignItemRect(AWP,               16, 18);
		assignItemRect(CROSSBOW,        16, 18);
	}

	private static final int WEP_TIER5      =                               xy(1, 10);   //8 slots
	public static final int USAS12      = WEP_TIER5+0;
	public static final int SASS      = WEP_TIER5+1;
	public static final int GLAIVE          = WEP_TIER5+2;
	public static final int GREATAXE        = WEP_TIER5+3;
	public static final int GREATSHIELD     = WEP_TIER5+4;
	public static final int LAR              = WEP_TIER5+5;
	static{
		assignItemRect(USAS12,  16, 16);
		assignItemRect(SASS,  16, 16);
		assignItemRect(GLAIVE,      16, 16);
		assignItemRect(GREATAXE,    12, 16);
		assignItemRect(GREATSHIELD, 12, 16);
		assignItemRect(LAR,            11, 15);
	}

	private static final int WEP_TIER6      =                               xy(1, 12);
	public static final int GROZA            = WEP_TIER6+0;
	public static final int MG42			= WEP_TIER6+1;
	public static final int SAIGA			= WEP_TIER6+2;
	public static final int NAGANT			= WEP_TIER6+3;
	public static final int NTW20			= WEP_TIER6+4;
	static{
		assignItemRect(GROZA,     16, 17);
		assignItemRect(MG42,     16, 17);
		assignItemRect(SAIGA,     16, 17);
		assignItemRect(NAGANT,     16, 17);
		assignItemRect(NTW20,     19, 19);
	}

	private static final int MISSILE_WEP    =                               xy(1, 5);  //16 slots. 3 per tier + boomerang
	public static final int BOOMERANG       = MISSILE_WEP+0;
	
	public static final int DART            = MISSILE_WEP+1;
	public static final int THROWING_KNIFE  = MISSILE_WEP+2;
	public static final int THROWING_STONE  = MISSILE_WEP+3;
	
	public static final int FISHING_SPEAR   = MISSILE_WEP+4;
	public static final int SHURIKEN        = MISSILE_WEP+5;

	public static final int BOLAS           = MISSILE_WEP+6;
	public static final int THROWING_SPEAR  = MISSILE_WEP+7;

	public static final int GENOISE			= MISSILE_WEP+8;

	public static final int JAVELIN         = MISSILE_WEP+9;
	public static final int TOMAHAWK        = MISSILE_WEP+10;
	
	public static final int TRIDENT         = MISSILE_WEP+11;
	public static final int THROWING_HAMMER = MISSILE_WEP+12;
	
	static{
		assignItemRect(BOOMERANG,       14, 14);

		assignItemRect(DART,            11, 11);
		assignItemRect(THROWING_KNIFE,  16, 14);
		assignItemRect(THROWING_STONE,  12,  13);

		assignItemRect(FISHING_SPEAR,   11, 12);
		assignItemRect(SHURIKEN,        12, 12);

		assignItemRect(BOLAS,           15, 14);
		assignItemRect(THROWING_SPEAR,  13, 12);

		assignItemRect(GENOISE,			7, 16);

		assignItemRect(JAVELIN,         16, 15);
		assignItemRect(TOMAHAWK,        13, 13);

		assignItemRect(TRIDENT,         16, 16);
		assignItemRect(THROWING_HAMMER, 12, 12);
	}
	
	public static final int TIPPED_DARTS    =                               xy(1, 6);  //16 slots
	public static final int ROT_DART        = TIPPED_DARTS+0;
	public static final int INCENDIARY_DART = TIPPED_DARTS+1;
	public static final int HOLY_DART       = TIPPED_DARTS+2;
	public static final int BLINDING_DART   = TIPPED_DARTS+3;
	public static final int HEALING_DART    = TIPPED_DARTS+4;
	public static final int CHILLING_DART   = TIPPED_DARTS+5;
	public static final int SHOCKING_DART   = TIPPED_DARTS+6;
	public static final int POISON_DART     = TIPPED_DARTS+7;
	public static final int SLEEP_DART      = TIPPED_DARTS+8;
	public static final int PARALYTIC_DART  = TIPPED_DARTS+9;
	public static final int DISPLACING_DART = TIPPED_DARTS+10;
	static {
		for (int i = TIPPED_DARTS; i < TIPPED_DARTS+16; i++)
			assignItemRect(i, 11, 11);
	}

	private static final int ARMOR          =                               xy(1, 11);  //16 slots
	public static final int ARMOR_CLOTH     = ARMOR+0;
	public static final int ARMOR_LEATHER   = ARMOR+1;
	public static final int ARMOR_MAIL      = ARMOR+2;
	public static final int ARMOR_SCALE     = ARMOR+3;
	public static final int ARMOR_PLATE     = ARMOR+4;
	public static final int ARMOR_WARRIOR   = ARMOR+5;
	public static final int ARMOR_MAGE      = ARMOR+6;
	public static final int ARMOR_ROGUE     = ARMOR+7;
	public static final int ARMOR_HUNTRESS  = ARMOR+8;
	public static final int ARMOR_UMP40 	= ARMOR+9;
	static{
		assignItemRect(ARMOR_CLOTH,     15, 12);
		assignItemRect(ARMOR_LEATHER,   14, 13);
		assignItemRect(ARMOR_MAIL,      14, 12);
		assignItemRect(ARMOR_SCALE,     14, 11);
		assignItemRect(ARMOR_PLATE,     12, 12);
		assignItemRect(ARMOR_WARRIOR,   12, 12);
		assignItemRect(ARMOR_MAGE,      15, 15);
		assignItemRect(ARMOR_ROGUE,     14, 12);
		assignItemRect(ARMOR_HUNTRESS,  13, 15);
		assignItemRect(ARMOR_UMP40,  12, 12);
	}

	                                                                                    //16 free slots

	private static final int WANDS              =                           xy(1, 14);  //16 slots
	public static final int WAND_MAGIC_MISSILE  = WANDS+0;
	public static final int WAND_FIREBOLT       = WANDS+1;
	public static final int WAND_FROST          = WANDS+2;
	public static final int WAND_LIGHTNING      = WANDS+3;
	public static final int WAND_DISINTEGRATION = WANDS+4;
	public static final int WAND_PRISMATIC_LIGHT= WANDS+5;
	public static final int WAND_CORROSION      = WANDS+6;
	public static final int WAND_LIVING_EARTH   = WANDS+7;
	public static final int WAND_BLAST_WAVE     = WANDS+8;
	public static final int WAND_CORRUPTION     = WANDS+9;
	public static final int WAND_WARDING        = WANDS+10;
	public static final int WAND_REGROWTH       = WANDS+11;
	public static final int WAND_TRANSFUSION    = WANDS+12;
	public static final int M79                   = WANDS+13;
	public static final int M84                   = WANDS+14;
	static {
		for (int i = WANDS; i < WANDS+16; i++)
			assignItemRect(i, 15, 15);
	}

	private static final int RINGS          =                               xy(1, 15);  //16 slots
	public static final int RING_GARNET     = RINGS+0;
	public static final int RING_RUBY       = RINGS+1;
	public static final int RING_TOPAZ      = RINGS+2;
	public static final int RING_EMERALD    = RINGS+3;
	public static final int RING_ONYX       = RINGS+4;
	public static final int RING_OPAL       = RINGS+5;
	public static final int RING_TOURMALINE = RINGS+6;
	public static final int RING_SAPPHIRE   = RINGS+7;
	public static final int RING_AMETHYST   = RINGS+8;
	public static final int RING_QUARTZ     = RINGS+9;
	public static final int RING_AGATE      = RINGS+10;
	public static final int RING_DIAMOND    = RINGS+11;
	static {
		for (int i = RINGS; i < RINGS+16; i++)
			assignItemRect(i, 15, 10);
	}

	private static final int ARTIFACTS          =                            xy(1, 16);  //32 slots
	public static final int ARTIFACT_CLOAK      = ARTIFACTS+0;
	public static final int ARTIFACT_ARMBAND    = ARTIFACTS+1;
	public static final int ARTIFACT_CAPE       = ARTIFACTS+2;
	public static final int ARTIFACT_TALISMAN   = ARTIFACTS+3;
	public static final int ARTIFACT_HOURGLASS  = ARTIFACTS+4;
	public static final int ARTIFACT_TOOLKIT    = ARTIFACTS+5;
	public static final int ARTIFACT_SPELLBOOK  = ARTIFACTS+6;
	public static final int ARTIFACT_BEACON     = ARTIFACTS+7;
	public static final int ARTIFACT_CHAINS     = ARTIFACTS+8;
	public static final int ARTIFACT_HORN1      = ARTIFACTS+9;
	public static final int ARTIFACT_HORN2      = ARTIFACTS+10;
	public static final int ARTIFACT_HORN3      = ARTIFACTS+11;
	public static final int ARTIFACT_HORN4      = ARTIFACTS+12;
	public static final int ARTIFACT_CHALICE1   = ARTIFACTS+13;
	public static final int ARTIFACT_CHALICE2   = ARTIFACTS+14;
	public static final int ARTIFACT_CHALICE3   = ARTIFACTS+15;
	public static final int ARTIFACT_SANDALS    = ARTIFACTS+16;
	public static final int ARTIFACT_SHOES      = ARTIFACTS+17;
	public static final int ARTIFACT_BOOTS      = ARTIFACTS+18;
	public static final int ARTIFACT_GREAVES    = ARTIFACTS+19;
	public static final int ARTIFACT_ROSE1      = ARTIFACTS+20;
	public static final int ARTIFACT_ROSE2      = ARTIFACTS+21;
	public static final int ARTIFACT_ROSE3      = ARTIFACTS+22;
	public static final int TOWCALL      = ARTIFACTS+23;
	public static final int AGLCALL      = ARTIFACTS+24;
	public static final int MTRCALL      = ARTIFACTS+25;
	static{
		assignItemRect(ARTIFACT_CLOAK,      16,  14);
		assignItemRect(ARTIFACT_ARMBAND,    16, 13);
		assignItemRect(ARTIFACT_CAPE,       16, 14);
		assignItemRect(ARTIFACT_TALISMAN,   15, 13);
		assignItemRect(ARTIFACT_HOURGLASS,  13, 16);
		assignItemRect(ARTIFACT_TOOLKIT,    15, 13);
		assignItemRect(ARTIFACT_SPELLBOOK,  13, 16);
		assignItemRect(ARTIFACT_BEACON,     16, 16);
		assignItemRect(ARTIFACT_CHAINS,     16, 16);
		assignItemRect(ARTIFACT_HORN1,      15, 15);
		assignItemRect(ARTIFACT_HORN2,      15, 15);
		assignItemRect(ARTIFACT_HORN3,      15, 15);
		assignItemRect(ARTIFACT_HORN4,      15, 15);
		assignItemRect(ARTIFACT_CHALICE1,   16, 15);
		assignItemRect(ARTIFACT_CHALICE2,   16, 15);
		assignItemRect(ARTIFACT_CHALICE3,   16, 15);
		assignItemRect(ARTIFACT_SANDALS,    16, 6 );
		assignItemRect(ARTIFACT_SHOES,      16, 6 );
		assignItemRect(ARTIFACT_BOOTS,      16, 9 );
		assignItemRect(ARTIFACT_GREAVES,    16, 14);
		assignItemRect(ARTIFACT_ROSE1,      14, 14);
		assignItemRect(ARTIFACT_ROSE2,      14, 14);
		assignItemRect(ARTIFACT_ROSE3,      14, 14);
		assignItemRect(TOWCALL,               7, 14);
		assignItemRect(AGLCALL,               7, 14);
		assignItemRect(MTRCALL,               7, 14);
	}
	                                                                                    //32 free slots

	private static final int SCROLLS        =                               xy(1, 20);  //16 slots
	public static final int SCROLL_KAUNAN   = SCROLLS+0;
	public static final int SCROLL_SOWILO   = SCROLLS+1;
	public static final int SCROLL_LAGUZ    = SCROLLS+2;
	public static final int SCROLL_YNGVI    = SCROLLS+3;
	public static final int SCROLL_GYFU     = SCROLLS+4;
	public static final int SCROLL_RAIDO    = SCROLLS+5;
	public static final int SCROLL_ISAZ     = SCROLLS+6;
	public static final int SCROLL_MANNAZ   = SCROLLS+7;
	public static final int SCROLL_NAUDIZ   = SCROLLS+8;
	public static final int SCROLL_BERKANAN = SCROLLS+9;
	public static final int SCROLL_ODAL     = SCROLLS+10;
	public static final int SCROLL_TIWAZ    = SCROLLS+11;
	public static final int CLIP             = SCROLLS+12;
	static {
		for (int i = SCROLLS; i < SCROLLS+16; i++)
			assignItemRect(i, 15, 14);
	}

	private static final int STONES        =                                xy(1, 21);  //16 slots
	public static final int STONE_KAUNAN   = STONES+0;
	public static final int STONE_SOWILO   = STONES+1;
	public static final int STONE_LAGUZ    = STONES+2;
	public static final int STONE_YNGVI    = STONES+3;
	public static final int STONE_GYFU     = STONES+4;
	public static final int STONE_RAIDO    = STONES+5;
	public static final int STONE_ISAZ     = STONES+6;
	public static final int STONE_MANNAZ   = STONES+7;
	public static final int STONE_NAUDIZ   = STONES+8;
	public static final int STONE_BERKANAN = STONES+9;
	public static final int STONE_ODAL     = STONES+10;
	public static final int STONE_TIWAZ    = STONES+11;
	static {
		for (int i = STONES; i < STONES+16; i++)
			assignItemRect(i, 12, 13);
	}

	private static final int POTIONS        =                               xy(1, 22);  //16 slots
	public static final int POTION_CRIMSON  = POTIONS+0;
	public static final int POTION_AMBER    = POTIONS+1;
	public static final int POTION_GOLDEN   = POTIONS+2;
	public static final int POTION_JADE     = POTIONS+3;
	public static final int POTION_TURQUOISE= POTIONS+4;
	public static final int POTION_AZURE    = POTIONS+5;
	public static final int POTION_INDIGO   = POTIONS+6;
	public static final int POTION_MAGENTA  = POTIONS+7;
	public static final int POTION_BISTRE   = POTIONS+8;
	public static final int POTION_CHARCOAL = POTIONS+9;
	public static final int POTION_SILVER   = POTIONS+10;
	public static final int POTION_IVORY    = POTIONS+11;
	static {
		for (int i = POTIONS; i < POTIONS+16; i++)
			assignItemRect(i, 10, 14);
	}

	private static final int SEEDS          =                               xy(1, 23);  //16 slots
	public static final int SEED_ROTBERRY   = SEEDS+0;
	public static final int SEED_FIREBLOOM  = SEEDS+1;
	public static final int SEED_STARFLOWER = SEEDS+2;
	public static final int SEED_BLINDWEED  = SEEDS+3;
	public static final int SEED_SUNGRASS   = SEEDS+4;
	public static final int SEED_ICECAP     = SEEDS+5;
	public static final int SEED_STORMVINE  = SEEDS+6;
	public static final int SEED_SORROWMOSS = SEEDS+7;
	public static final int SEED_DREAMFOIL  = SEEDS+8;
	public static final int SEED_EARTHROOT  = SEEDS+9;
	public static final int SEED_FADELEAF   = SEEDS+10;
	public static final int SEED_BLANDFRUIT = SEEDS+11;
	static{
		for (int i = SEEDS; i < SEEDS+16; i++)
			assignItemRect(i, 10, 10);
	}

	//16 free slots

	private static final int FOOD       =                                   xy(1, 25);  //16 slots
	public static final int MEAT        = FOOD+0;
	public static final int STEAK       = FOOD+1;
	public static final int OVERPRICED  = FOOD+2;
	public static final int CARPACCIO   = FOOD+3;
	public static final int BLANDFRUIT  = FOOD+4;
	public static final int RATION      = FOOD+5;
	public static final int PASTY       = FOOD+6;
	public static final int PUMPKIN_PIE = FOOD+7;
	public static final int CANDY_CANE  = FOOD+8;
	public static final int MACCOL      = FOOD+9;
	public static final int CINNAMONROLL= FOOD+10;
	static{
		assignItemRect(MEAT,        15, 11);
		assignItemRect(STEAK,       15, 11);
		assignItemRect(OVERPRICED,  14, 11);
		assignItemRect(CARPACCIO,   15, 11);
		assignItemRect(BLANDFRUIT,  9,  12);
		assignItemRect(RATION,      16, 12);
		assignItemRect(PASTY,       16, 11);
		assignItemRect(PUMPKIN_PIE, 16, 12);
		assignItemRect(CANDY_CANE,  13, 16);
		assignItemRect(MACCOL,       7, 10);
		assignItemRect(CINNAMONROLL, 8, 8 );
	}

	private static final int QUEST  =                                       xy(1, 26);  //32 slots
	public static final int SKULL   = QUEST+0;
	public static final int DUST    = QUEST+1;
	public static final int CANDLE  = QUEST+2;
	public static final int EMBER   = QUEST+3;
	public static final int PICKAXE = QUEST+4;
	public static final int ORE     = QUEST+5;
	public static final int TOKEN   = QUEST+6;
	static{
		assignItemRect(SKULL,   16, 11);
		assignItemRect(DUST,    12, 11);
		assignItemRect(CANDLE,  12, 12);
		assignItemRect(EMBER,   12, 11);
		assignItemRect(PICKAXE, 14, 14);
		assignItemRect(ORE,     15, 15);
		assignItemRect(TOKEN,   12, 12);

	}

	private static final int BAGS       =                                   xy(1, 28);  //16 slots
	public static final int VIAL        = BAGS+0;
	public static final int POUCH       = BAGS+1;
	public static final int HOLDER      = BAGS+2;
	public static final int BANDOLIER   = BAGS+3;
	public static final int HOLSTER     = BAGS+4;
	static{
		assignItemRect(VIAL,        12, 12);
		assignItemRect(POUCH,       14, 15);
		assignItemRect(HOLDER,      16, 16);
		assignItemRect(BANDOLIER,   15, 16);
		assignItemRect(HOLSTER,     15, 16);
	}

	                                                                                    //64 free slots


	private static void assignItemRect( int item, int width, int height){
		int x = (item % WIDTH) * WIDTH;
		int y = (item / WIDTH) * WIDTH;
		film.add( item, x, y, x+width, y+height);
	}

}
