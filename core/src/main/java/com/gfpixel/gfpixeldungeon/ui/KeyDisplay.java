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

package com.gfpixel.gfpixeldungeon.ui;

import com.gfpixel.gfpixeldungeon.Assets;
import com.gfpixel.gfpixeldungeon.Dungeon;
import com.gfpixel.gfpixeldungeon.items.keys.CrystalKey;
import com.gfpixel.gfpixeldungeon.items.keys.GoldenKey;
import com.gfpixel.gfpixeldungeon.items.keys.IronKey;
import com.gfpixel.gfpixeldungeon.items.keys.Key;
import com.gfpixel.gfpixeldungeon.items.keys.SkeletonKey;
import com.gfpixel.gfpixeldungeon.journal.Notes;
import com.watabou.gltextures.SmartTexture;
import com.watabou.gltextures.TextureCache;
import com.watabou.glwrap.Quad;
import com.watabou.glwrap.Vertexbuffer;
import com.watabou.noosa.ColorBlock;
import com.watabou.noosa.Group;
import com.watabou.noosa.NoosaScript;
import com.watabou.noosa.Visual;
import com.watabou.utils.RectF;

import java.nio.FloatBuffer;
import java.util.LinkedHashMap;

public class KeyDisplay extends Visual {
    public static final int MAX_WIDTH = 32;
    public static final int KEY_UV_TOP = 0;
    public static final int KEY_SHORT_UV_TOP = 11;
    public static final int KEY_WIDTH = 8;
    public static final int KEY_HEIGHT = 10;
    public static final int KEY_SHORT_WIDTH = 6;
    public static final int KEY_SHORT_HEIGHT = 6;
    public static final int PADDING_SIDE = 3;
    public static final int PADDING_PER_ROW = 1;
    public static final int PADDING_PER_KEY = 1;

    private float[] vertices = new float[16];
    private FloatBuffer quads;
    private Vertexbuffer buffer;

    private SmartTexture tx = TextureCache.get(Assets.KEY);

    private boolean dirty = true;
    private int[] keys;

    private int keyWidth;
    private int keyHeight;

    //mapping of key types to slots in the array, 0 is reserved for black (missed) keys
    //this also determines the order these keys will appear (lower first)
    //and the order they will be truncated if there is no space (higher first, larger counts first)
    private static final LinkedHashMap<Class<? extends Key>, Integer> keyMap = new LinkedHashMap<>();

    static {
        keyMap.put(SkeletonKey.class, 1);
        keyMap.put(CrystalKey.class, 2);
        keyMap.put(GoldenKey.class, 3);
        keyMap.put(IronKey.class, 4);
    }

    private int totalKeys = 0;

    public KeyDisplay() {
        super(0, 0, 0, 0);
    }

    public void updateKeys() {
        keys = new int[keyMap.size() + 1];

        for (Notes.KeyRecord rec : Notes.getRecords(Notes.KeyRecord.class)) {
            if (rec.depth() != Dungeon.depth) {
                //only ever 1 black key
                keys[0] = 1;
            } else if (rec.depth() == Dungeon.depth) {
                keys[keyMap.get(rec.type())] += rec.quantity();
            }
        }

        totalKeys = 0;
        for (int k : keys) {
            totalKeys += k;
        }

        visible = totalKeys > 0;

       updateSize();

        dirty = true;
    }

    public void updateSize() {
        width = keyWidth * totalKeys + PADDING_PER_KEY * (totalKeys - 1) + PADDING_SIDE * 2;
        height = keyHeight + PADDING_PER_ROW * 2;
    }

    public int keyCount() {
        return totalKeys;
    }

    @Override
    public void draw() {
        super.draw();
        if (dirty) {

            updateVertices();

            quads.limit(quads.position());
            if (buffer == null)
                buffer = new Vertexbuffer(quads);
            else
                buffer.updateVertices(quads);
        }

        NoosaScript script = NoosaScript.get();

        tx.bind();

        script.camera(camera());

        script.uModel.valueM4(matrix);
        script.lighting(
                rm, gm, bm, am,
                ra, ga, ba, aa);
        script.drawQuadSet(buffer, totalKeys, 0);
    }

    private void updateVertices() {

        int maxKeys = MAX_WIDTH / (KEY_SHORT_WIDTH + PADDING_PER_KEY);
        while (totalKeys > maxKeys) {
            Class<? extends Key> mostType = null;
            int mostNum = 0;
            for (Class<? extends Key> k : keyMap.keySet()) {
                if (keys[keyMap.get(k)] >= mostNum) {
                    mostType = k;
                    mostNum = keys[keyMap.get(k)];
                }
            }

            keys[keyMap.get(mostType)]--;
            totalKeys--;
        }

        boolean shortKeys = MAX_WIDTH < KEY_WIDTH * totalKeys + PADDING_PER_KEY * (totalKeys - 1) + PADDING_SIDE * 2;
        keyWidth = shortKeys ? KEY_SHORT_WIDTH : KEY_WIDTH;
        keyHeight = shortKeys ? KEY_SHORT_HEIGHT : KEY_HEIGHT;

        float left = PADDING_SIDE;
        float top = PADDING_PER_ROW; // + keyHeight * 0.5f; //(height - (rows * keyHeight)) / 2;

        quads = Quad.createSet(totalKeys);
        for (int i = 0; i < totalKeys; i++) {
            int keyIdx = 0;

            if (i == 0 && keys[0] > 0) { //black key
                keyIdx = 0;
            } else {
                for (int j = 1; j < keys.length; j++) {
                    if (keys[j] > 0) {
                        keys[j]--;
                        keyIdx = j;
                        break;
                    }
                }
            }

            //texture coordinates
            RectF r = shortKeys ?
                    tx.uvRect((keyWidth + 1) * keyIdx, KEY_SHORT_UV_TOP, keyWidth, keyHeight + KEY_SHORT_UV_TOP) :
                    tx.uvRect((keyWidth + 1) * keyIdx, KEY_UV_TOP, keyWidth, keyHeight);

            vertices[2] = r.left;
            vertices[3] = r.top;

            vertices[6] = r.right + r.left;
            vertices[7] = r.top;

            vertices[10] = r.right + r.left;
            vertices[11] = r.bottom;

            vertices[14] = r.left;
            vertices[15] = r.bottom;

            //screen coordinates
            vertices[0] = left;
            vertices[1] = top;

            vertices[4] = left + keyWidth;
            vertices[5] = top;

            vertices[8] = left + keyWidth;
            vertices[9] = top + keyHeight;

            vertices[12] = left;
            vertices[13] = top + keyHeight;

            quads.put(vertices);

            left += (keyWidth + PADDING_PER_KEY);
        }

        dirty = false;
    }
}
