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
import com.gfpixel.gfpixeldungeon.Chrome;
import com.gfpixel.gfpixeldungeon.Dungeon;
import com.gfpixel.gfpixeldungeon.SPDSettings;
import com.gfpixel.gfpixeldungeon.messages.Messages;
import com.gfpixel.gfpixeldungeon.scenes.PixelScene;
import com.gfpixel.gfpixeldungeon.ui.RenderedTextMultiline;
import com.gfpixel.gfpixeldungeon.ui.Window;
import com.watabou.input.Touchscreen.Touch;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.TouchArea;
import com.watabou.utils.SparseArray;

public class WndStory extends Window {

	private static final int WIDTH_P = 125;
	private static final int WIDTH_L = 160;
	private static final int MARGIN = 2;
	
	private static final float bgR	= 0.77f;
	private static final float bgG	= 0.73f;
	private static final float bgB	= 0.62f;

	public static final int ID_SEWERS		= 0;
	public static final int ID_PRISON		= 1;
	public static final int ID_CAVES		= 2;
	public static final int ID_CITY     	= 3;
	public static final int ID_RECAVES		= 4;
	public static final int ID_HALLS		= 5;
	public static final int ID_COLDWAR		= 6;
	public static final int ID_STAR_QUEST   = 90;
	public static final int ID_M16_QUEST	= 91;
	public static final int ID_PPSH_QUEST	= 92;
	public static final int ID_P7_QUEST 	= 93;

	private int sceneCount = 0;
	private String questString = "";
	private static int sceneLevel = 0;
	private static int[][] emotionFact = {
			{0,1,2,2,2,1,1,2},
			{0,2,2,1,2,1,1},
			{0,2,0,1,2,1,0,1,0},
			{2,2,2,1,1,0,0,0,2,0},
			{1,2,1,1,1,1,0,2,1,0},
			{1,2,1,0,1,1,1,0},
			{2,1,1}
	};
	private static int[][] QemotionFact = {
			{0,0,1,1,0,1},
			{0,1,0,0,0,1,0,0,0},
			{0,1,1,0,0,1,0,1,0},
			{0,0,0,0,1,0},
			{0,1,2,2,2,1,1,2},
			{0,1,2,2,2,1,1,2}
	};

	private static final SparseArray<String> CHAPTERS = new SparseArray<String>();
	private static final SparseArray<Integer> CHAPTERSLENGTH = new SparseArray<Integer>();
	
	static {
		CHAPTERS.put( ID_SEWERS, "sewers" );
		CHAPTERS.put( ID_PRISON, "prison" );
		CHAPTERS.put( ID_CAVES, "caves" );
		CHAPTERS.put( ID_CITY, "city" );
		CHAPTERS.put( ID_RECAVES, "recaves" );
		CHAPTERS.put( ID_HALLS, "halls" );
		CHAPTERS.put( ID_COLDWAR, "coldwar" );
		CHAPTERS.put( ID_STAR_QUEST, "starquest" );
		CHAPTERS.put( ID_M16_QUEST, "m16quest");
		CHAPTERS.put( ID_PPSH_QUEST, "ppshquest");
		CHAPTERS.put( ID_P7_QUEST, "p7quest");
	};

	// 안쓰임
	static {
		CHAPTERSLENGTH.put( ID_SEWERS, 2 );
		CHAPTERSLENGTH.put( ID_PRISON, 2 );
		CHAPTERSLENGTH.put( ID_CAVES, 2 );
		CHAPTERSLENGTH.put( ID_CITY, 2 );
		CHAPTERSLENGTH.put( ID_RECAVES, 2 );
		CHAPTERSLENGTH.put( ID_HALLS, 2 );
		CHAPTERSLENGTH.put( ID_COLDWAR, 2);
	};

	private RenderedTextMultiline tf;
	private RenderedTextMultiline cname;
	protected Image character;
	protected Image characterEmotion;
	protected NinePatch gpd_talk;
	
	private float delay;
	
	public WndStory( String text ) {
		super( 0, 0, Chrome.get( Chrome.Type.SCROLL ) );
		
		tf = PixelScene.renderMultiline( text, 6 );
		tf.maxWidth(SPDSettings.landscape() ?
					WIDTH_L - MARGIN * 2:
					WIDTH_P - MARGIN *2);
		//tf.invert();
		tf.setPos(MARGIN, 0);
		add( tf );
		
		add( new TouchArea( chrome ) {
			@Override
			protected void onClick( Touch touch ) {
				hide();
			}
		} );
		
		resize( (int)(tf.width() + MARGIN * 2), (int)Math.min( tf.height(), 180 ) );
	}

	// text - 대사 식별자, ctext - 캐릭터 이름
	public WndStory( String text , String ctext , int id) {
		super( 0, 0, Chrome.get( Chrome.Type.NONE ) );
		final int ids = id;
		final int idsFact = ids >=90 ? ids - 90 : ids;
		if(ids >= 90){
			questString = text;
			text = getQuestString(text,ids);
		}
		int characterType = characterFind(ctext);
		int sceneSlash = SPDSettings.landscape() ? 4:6;
		int font_size = SPDSettings.landscape() ? 6:5;
		int emotionGeter = ids >= 90 ? (QemotionFact[idsFact][sceneLevel]) : (emotionFact[idsFact][sceneLevel]);
		gpd_talk = Chrome.get( Chrome.Type.DIALOG );
		gpd_talk.size(
				(PixelScene.uiCamera.width)-20,
				(PixelScene.uiCamera.height/sceneSlash));
		gpd_talk.x =10;
		gpd_talk.y =PixelScene.uiCamera.height - gpd_talk.height() - 32;
		add(gpd_talk);
		character = new Image(Assets.EMOTION);
		character.frame( (24 * emotionGeter), (24*characterType)+1, 24, 23 );
		character.scale.set( SPDSettings.landscape() ?
				2f:
				1.5f );
		character.x = 10;
		character.y = ((PixelScene.uiCamera.height - ((PixelScene.uiCamera.height/sceneSlash)+32)) - (character.height()));
		add(character);


		tf = PixelScene.renderMultiline( text, font_size );
		tf.maxWidth((int)(gpd_talk.width()-16));
		tf.invert();
		tf.setPos(16, PixelScene.uiCamera.height - gpd_talk.height()-16);
		tf.hardlight(0xFFFFFF);
		add( tf );

		cname = PixelScene.renderMultiline( ctext, 8);
		cname.maxWidth(150);
		cname.invert();
		cname.setPos(character.width() + 12 , character.y + (character.height()-cname.height()) - 3);
		cname.hardlight(0x2B7BB9);
		add( cname );
		super.shadow.am = 0f;
		resize( (int)(PixelScene.uiCamera.width ), (int)(PixelScene.uiCamera.height) );
		add( new TouchArea( chrome ) {
			@Override
			protected void onClick( Touch touch ) {
				//sceneCount += 1;
				if(sceneCount >= 999) {
					sceneCount = 0;
					hide();
				}else{
					hide();
					sceneCount += 1;
					sceneLevel += 1;
					String text2 = Messages.get(WndStory.class, CHAPTERS.get( ids )+questString+sceneLevel);
					String ctext = Messages.get(WndStory.class, CHAPTERS.get( ids )+questString+sceneLevel+sceneLevel);
					if(text2 != "!!!NO TEXT FOUND!!!") {
						WndStory wnd = new WndStory( questString!=""?questString:text2 , ctext , ids);
						Game.scene().add(wnd);
					}else{
						sceneCount = 0;
						sceneLevel = 0;
					}
				}


			}
		} );
	}

	// FIXME static int로 변환
	public int characterFind(String ctext){
		switch (ctext){
			case "G11":
				return 0;
			case "UMP45":
				return 1;
			case "UMP9":
				return 2;
			case "HK416":
				return 3;
			case "P7":
				return 4;
			case "ST-AR15":
				return 5;
			case "M16A1":
				return 6;
			case "PPSH":
				return 8;
			case "DESTROYER" :
				return 9;
			case "DREAMER" :
				return 10;
			default:
				return 0;

		}
	}

	public String getQuestString(String text,int qusetid){
		if(qusetid==90) {
			text = Messages.get(WndStory.class, "starquest" + text + sceneLevel);
		}else if(qusetid==91){
			text = Messages.get(WndStory.class, "m16quest" + text + sceneLevel);
		}else if(qusetid==92){
			text = Messages.get(WndStory.class, "ppshquest" + text + sceneLevel);
		}else if(qusetid==93){
			text = Messages.get(WndStory.class, "p7quest" + text + sceneLevel);
		}
		return text;
	}

	@Override
	public void update() {
		super.update();
		
		if (delay > 0 && (delay -= Game.elapsed) <= 0) {
			shadow.visible = chrome.visible = tf.visible = true;
		}
	}
	
	public static void showChapter( int id ) {
		
		if (Dungeon.chapters.contains( id )) {
			return;
		}

		String text = Messages.get(WndStory.class, CHAPTERS.get( id )+sceneLevel);
		String ctext = Messages.get(WndStory.class, CHAPTERS.get( id )+sceneLevel+sceneLevel);

		if (text != null) {
			WndStory wnd = new WndStory( text, ctext , id );
			if ((wnd.delay = 0.6f) > 0) {
				wnd.shadow.visible = wnd.chrome.visible = wnd.tf.visible = false;
			}
			
			Game.scene().add( wnd );
			
			Dungeon.chapters.add( id );
		}
	}
}
