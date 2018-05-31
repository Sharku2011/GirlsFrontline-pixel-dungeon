package com.gfpixel.gfpixeldungeon.windows;

import com.gfpixel.gfpixeldungeon.Chrome;
import com.gfpixel.gfpixeldungeon.SPDSettings;
import com.gfpixel.gfpixeldungeon.scenes.PixelScene;
import com.gfpixel.gfpixeldungeon.ui.Window;
import com.watabou.noosa.Image;

public class WndDialog extends Window {

    private static final int MARGIN_X = 10;
    private static final int MARGIN_Y = 5;
    private static final int PADDING = 16;

    private Image AVATAR;

    public WndDialog( int avatarID, String text) {
        super( 24, 23, 0, Chrome.get(Chrome.Type.DIALOG));

        int heightUnit = SPDSettings.landscape()? 4:6;

        int wndWidth = PixelScene.uiCamera.width - 2 * MARGIN_X;
        int wndHeight = PixelScene.uiCamera.height / heightUnit - MARGIN_Y;
        int fontSize = SPDSettings.landscape() ? 6:5;

        resize(wndWidth, wndHeight);

        chrome.x = MARGIN_X;
        chrome.y = PixelScene.uiCamera.height - chrome.height;
        //super( PixelScene.uiCamera.width - 2 * OFFSET, (PixelScene.uiCamera.height / (SPDSettings.landscape()? 4:6)) - 5, Chrome.get(Chrome.Type.GPDTALK));
    }


}
