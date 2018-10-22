package com.gfpixel.gfpixeldungeon.windows;

import com.gfpixel.gfpixeldungeon.Assets;
import com.gfpixel.gfpixeldungeon.Chrome;
import com.gfpixel.gfpixeldungeon.DialogInfo;
import com.gfpixel.gfpixeldungeon.Dungeon;
import com.gfpixel.gfpixeldungeon.SPDSettings;
import com.gfpixel.gfpixeldungeon.actors.mobs.npcs.NPC;
import com.gfpixel.gfpixeldungeon.messages.Messages;
import com.gfpixel.gfpixeldungeon.scenes.GameScene;
import com.gfpixel.gfpixeldungeon.scenes.PixelScene;
import com.gfpixel.gfpixeldungeon.ui.RenderedTextMultiline;
import com.gfpixel.gfpixeldungeon.ui.Window;
import com.watabou.input.Touchscreen;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.TouchArea;


public class WndDialog extends Window {

    private static final int MARGIN_X = 10;
    private static final int MARGIN_Y = 5;

    private static DialogInfo dialog;

    private final int dialogLength;
    private int dialogStep;

    private Image avatar;
    private RenderedText nametag;
    private RenderedTextMultiline tf;

    public NPC npc = null;

    public WndDialog(final int dialogID) {
        super( 0, 0, 0, Chrome.get(Chrome.Type.DIALOG));

        int heightUnit = SPDSettings.landscape()? 4:6;

        int fontSize = SPDSettings.landscape() ? 8:6;

        dialog = DialogInfo.STORIES.get(dialogID);

        this.dialogLength = dialog.LENGTH;
        this.dialogStep = 0;

        final int AvatarWidth  = 24;
        final int AvatarHeight = 23;

        final float AvatarScaleMultiplier = SPDSettings.landscape() ? 2f : 1.5f;

        int chromeHeight = PixelScene.uiCamera.height / heightUnit - MARGIN_Y;
        int chromeWidth = PixelScene.uiCamera.width - 2 * MARGIN_X;

        avatar = new Image( Assets.EMOTION );
        avatar.frame( AvatarWidth * (dialog.CharacterArray[0].x * 3 + dialog.EmotionArray[0]), 1 + (AvatarHeight + 1) * dialog.CharacterArray[0].y, AvatarWidth, AvatarHeight );

        avatar.scale.set(AvatarScaleMultiplier);
        avatar.x = MARGIN_X / 2;

        add(avatar);

        super.shadow.am = 0;

        yOffset = PixelScene.uiCamera.height / (SPDSettings.landscape() ? 4 : 3) - (int)((GameScene)Game.scene()).ToolbarHeight();
        resize( chromeWidth, chromeHeight + Math.round(avatar.height()) );

        chrome.y = avatar.height();
        chrome.size(chromeWidth, chromeHeight);

        nametag = PixelScene.renderText( DialogInfo.NAMES[dialog.CharacterArray[0].x][dialog.CharacterArray[0].y], 8 );
        nametag.invert();
        nametag.hardlight(0x2B7BB9);

        nametag.x = avatar.width() + 5;
        nametag.y = avatar.height() - 15;

        tf = PixelScene.renderMultiline( Messages.get(this, dialog.ID + dialog.BRANCH + dialogStep), fontSize );
        tf.maxWidth( PixelScene.uiCamera.width - 2 * (MARGIN_X + fontSize) );
        tf.setPos( fontSize, chrome.y + fontSize );

        add(tf);
        add(nametag);

        // TODO -100을 나중에 적당한 변수값으로 치환할 것
        add(new TouchArea( 0, -100, PixelScene.uiCamera.width, PixelScene.uiCamera.height ) {
                 @Override
                 protected void onClick( Touchscreen.Touch touch ) {
                     if (dialogLength -1 <= dialogStep) {
                         dialogStep = 0;

                         if (npc != null) {
                             onFinish();
                         }

                         hide();
                     }
                     else {
                         dialogStep += 1;
                         tf.text(Messages.get( WndDialog.class, dialog.ID + dialog.BRANCH + dialogStep));

                         nametag.text( DialogInfo.NAMES[dialog.CharacterArray[dialogStep].x][dialog.CharacterArray[dialogStep].y] );

                         avatar.frame(
                                 AvatarWidth * (3 * dialog.CharacterArray[dialogStep].x + dialog.EmotionArray[dialogStep]),
                                 1 + (AvatarHeight+1) * dialog.CharacterArray[dialogStep].y,
                                 AvatarWidth,
                                 AvatarHeight
                         );
                     }
                 }
             }
        );
    }

    public static void ShowChapter(int dialogID) {
        WndDialog newDialog = new WndDialog(dialogID);
        Game.scene().add(newDialog);
        Dungeon.chapters.add( dialogID );
    }

    public static void setBRANCH (int dialogID, int newOption) {
        DialogInfo.STORIES.get(dialogID).setBRANCH("branch" + newOption);
    }

    protected void onFinish() { }
}

