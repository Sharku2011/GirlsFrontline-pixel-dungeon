package com.gfpixel.gfpixeldungeon.windows;

import com.gfpixel.gfpixeldungeon.SPDSettings;
import com.gfpixel.gfpixeldungeon.scenes.PixelScene;
import com.gfpixel.gfpixeldungeon.ui.Window;

public class WndDialog extends Window {

    private static final int OFFSET = 10;

    public WndDialog( int avartar, String text) {
        int wndHeight = (PixelScene.uiCamera.height / (SPDSettings.landscape()? 4:6)) - 5;
        int fontSize = SPDSettings.landscape() ? 6:5;
        //super( PixelScene.uiCamera.width - 2 * OFFSET, (PixelScene.uiCamera.height / (SPDSettings.landscape()? 4:6)) - 5);
    }


}
