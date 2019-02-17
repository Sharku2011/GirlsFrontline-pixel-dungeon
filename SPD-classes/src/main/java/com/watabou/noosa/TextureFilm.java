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

package com.watabou.noosa;

import com.watabou.gltextures.SmartTexture;
import com.watabou.gltextures.TextureCache;
import com.watabou.utils.RectF;

import java.util.HashMap;

import static java.lang.Math.round;

public class TextureFilm {
	
	private static final RectF FULL = new RectF( 0, 0, 1, 1 );
	
	private int texWidth;
	private int texHeight;

	private SmartTexture texture;
	
	protected HashMap<Object,RectF> frames = new HashMap<Object, RectF>();
	
	public TextureFilm( Object tx ) {
		
		texture = TextureCache.get( tx );
		
		texWidth = texture.width;
		texHeight = texture.height;
		
		add( null, FULL );
	}
	
	public TextureFilm( SmartTexture texture, int width ) {
		this( texture, width, texture.height );
	}
	
	public TextureFilm( Object tx, int width, int height ) {
		
		texture = TextureCache.get( tx );
		
		texWidth = texture.width;
		texHeight = texture.height;
		
		float uw = (float)width / texWidth;
		float vh = (float)height / texHeight;
		int cols = texWidth / width;
		int rows = texHeight / height;
		
		for (int i=0; i < rows; i++) {
			for (int j=0; j < cols; j++) {
				RectF rect = new RectF( j * uw, i * vh, (j+1) * uw, (i+1) * vh );
				add( i * cols + j, rect );
			}
		}
	}
	
	public TextureFilm( TextureFilm atlas, Object key, int width, int height ) {

		texture = atlas.texture;
	
		texWidth = atlas.texWidth;
		texHeight = atlas.texHeight;
		
		RectF patch = atlas.get( key );
		
		float uw = (float)width / texWidth;
		float vh = (float)height / texHeight;

		int cols = (int)(width( patch ) / width);
		int rows = (int)(height( patch ) / height);
		
		for (int i=0; i < rows; i++) {
			for (int j=0; j < cols; j++) {
				RectF rect = new RectF( j * uw, i * vh, (j+1) * uw, (i+1) * vh );
				rect.shift( patch.left, patch.top );
				add( i * cols + j, rect );
			}
		}
	}
	
	public void add( Object id, RectF rect ) {
		frames.put( id, rect );
	}

	public void add( Object id, int left, int top, int right, int bottom){
		frames.put( id, texture.uvRect(left, top, right, bottom));
	}
	
	public RectF get( Object id ) {
		return frames.get( id );
	}

	public float width( Object id ){
		return width( get( id ) );
	}
	
	public float width( RectF frame ) {
		return frame.width() * texWidth;
	}

	public float height( Object id ){
		return height( get( id ) );
	}
	
	public float height( RectF frame ) {
		// 부동 소수점 연산의 부정확성(0.70897654 - 0.56790125 = 0.14197529) 으로 인해서 5번째 행
		// (4티어 스프라이트 영역)의 높이가 23이 아닌 22.96 픽셀로 판정되어 줄의 수를 계산하는 과정에서
		// (int)를 통한 강제 형변환 때문에 22.96/23 = 0으로 버림되어 버림. 임시로 반올림하여
		// 계산하도록 바꾸었지만 혹시라도 다른 곳에서 문제가 생길 수 있음. 문제가 없다면 width 계산
		// 에도 반영할 것
		return round(frame.height() * texHeight);
	}
}