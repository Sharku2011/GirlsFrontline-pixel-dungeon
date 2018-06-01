package com.gfpixel.gfpixeldungeon.windows;

import android.app.Dialog;

import com.gfpixel.gfpixeldungeon.Assets;
import com.gfpixel.gfpixeldungeon.Chrome;
import com.gfpixel.gfpixeldungeon.SPDSettings;
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
import com.watabou.utils.SparseArray;


public class WndDialog extends Window {

    static class DialogInfo
    {
        public final String ID;
        public final int LENGTH;
        public int[] CharacterArray;
        public int[] EmotionArray;
        public int BRANCH;

        public DialogInfo(String newID, int[] Chrs) {
            this.ID = newID;
            this.CharacterArray = Chrs;
            this.EmotionArray = new int[Chrs.length];
            this.LENGTH = Chrs.length;
            this.BRANCH = 0;
        }

        public DialogInfo(String newID, int[] Chrs, int[] Emos) {
            this.ID = newID;
            this.CharacterArray = Chrs;

            if (Chrs.length <= Emos.length) {
                this.EmotionArray = Emos;
            } else {
                this.EmotionArray = new int[Chrs.length];
                for (int i=0; i<Emos.length; ++i) {
                    if (Emos[i] > 2)
                    {
                        this.EmotionArray[i] = 0;
                        continue;
                    }
                    this.EmotionArray[i] = Emos[i];
                }
            }

            this.LENGTH = Chrs.length;
            this.BRANCH = 0;
        }

        public DialogInfo(String newID, int[] Chrs, int[] Emos, int option) {

            this.ID = newID;
            this.CharacterArray = Chrs;

            if (Chrs.length <= Emos.length) {
                this.EmotionArray = Emos;
            } else {
                this.EmotionArray = new int[Chrs.length];
                for (int i=0; i<Emos.length; ++i) {
                    if (Emos[i] > 2)
                    {
                        this.EmotionArray[i] = 0;
                        continue;
                    }
                    this.EmotionArray[i] = Emos[i];
                }
            }

            this.LENGTH = Chrs.length;
            this.BRANCH = option;
        }

        public void setBRANCH(int newOption) {
            this.BRANCH = newOption;
        }
    };

    public static final int G11         = 0;
    public static final int UMP45       = 1;
    public static final int UMP9        = 2;
    public static final int HK416       = 3;
    public static final int P7          = 4;
    public static final int STAR15      = 5;
    public static final int M16A1       = 6;
    public static final int JEPUTY      = 7;
    public static final int PPSH47      = 8;
    public static final int DESTROYER   = 9;
    public static final int DREAMER     = 10;

    private static final String[] NAMES = new String[] {
            "G11", "UMP45", "UMP9", "HK416", "P7", "STAR-15", "M16A1", "제퓨티", "Ppsh-47", "디스트로이어", "드리머"
    };

    public static final int ID_SEWER		= 0;
    public static final int ID_PRISON		= 1;
    public static final int ID_CAVES		= 2;
    public static final int ID_CITY     	= 3;
    public static final int ID_RECAVES		= 4;
    public static final int ID_HALLS		= 5;
    public static final int ID_COLDWAR		= 6;
    public static final int ID_STAR15_QUEST = 100;
    public static final int ID_M16A1_QUEST	= 200;
    public static final int ID_PPSH47_QUEST	= 300;
    public static final int ID_P7_QUEST 	= 400;

    // general tag for quest
    public static final int INIT       = 0;
    public static final int COMPLETE   = 1;
    public static final int INPROGRESS = 2;


    public static SparseArray<DialogInfo> STORIES = new SparseArray<> ();

    static {
        STORIES.put( ID_SEWER,
                new DialogInfo (
                        "sewer",
                        new int[]{UMP45, UMP9, UMP45, HK416, UMP45, G11, HK416, G11},
                        new int[]{    0,    1,     2,     2,     2,   1,     1,   2} )
        );
        STORIES.put( ID_PRISON,
                new DialogInfo (
                        "prison",
                        new int[]{UMP9, UMP45, UMP9, G11, HK416, UMP9, UMP45},
                        new int[]{   0,     2,    2,   1,     2,    1,     1}
                )
        );
        STORIES.put( ID_CAVES,
                new DialogInfo (
                        "caves",
                        new int[]{G11, UMP45, G11, UMP45, G11, HK416, UMP9, HK416, G11},
                        new int[]{  0,     2,   0,     1,   2,     1,    0,     1,   0}
                )
        );
        STORIES.put( ID_CITY,
                new DialogInfo (
                        "city",
                        new int[]{HK416, UMP45, UMP9, G11, HK416, UMP9, UMP45, DESTROYER, UMP45, UMP9},
                        new int[]{    2,     2,    2,   1,     1,    0,     0,         0,     2,    0}
                )
        );
        STORIES.put( ID_RECAVES,
                new DialogInfo(
                        "recaves",
                        new int[]{DESTROYER, DREAMER, DESTROYER, DREAMER, DESTROYER, DREAMER, DREAMER, DESTROYER, G11, UMP45},
                        new int[]{        1,       2,         1,       1,         1,       1,       0,         2,   1,     0}
                )
        );
        STORIES.put( ID_HALLS,
                new DialogInfo (
                        "halls",
                        new int[]{UMP9, UMP45, UMP9, HK416, G11, HK416, G11, UMP45},
                        new int[]{   1,     2,    1,     0,   1,     1,   1,     0}
                )
        );
        STORIES.put( ID_COLDWAR,
                new DialogInfo (
                        "coldwar",
                        new int[]{UMP9, G11, UMP45},
                        new int[]{   2,   1,     1}
                )
        );
        STORIES.put( ID_STAR15_QUEST,
                new DialogInfo (
                        "star15quest",
                        new int[]{UMP45, UMP9, STAR15, STAR15, STAR15},
                        new int[]{    1,     2,      0,      1,     0}
                )
        );
        STORIES.put( ID_M16A1_QUEST,
                new DialogInfo (
                        "m16a1quest",
                        new int[]{UMP45, M16A1, M16A1, HK416, UMP45},
                        new int[]{    1,     1,     0,     1,     0}
                )
        );
        STORIES.put( ID_PPSH47_QUEST,
                new DialogInfo (
                        "ppsh47quest",
                        new int[]{HK416, PPSH47, UMP9, PPSH47, PPSH47},
                        new int[]{    0,      1,    1,       0,     1}
                )
        );
        STORIES.put( ID_P7_QUEST,
                new DialogInfo (
                        "p7quest",
                        new int[]{P7, UMP9, P7, P7, P7},
                        new int[]{ 1,    0,  0,  1,  0}
                )
        );
    }


    private static final int MARGIN_X = 10;
    private static final int MARGIN_Y = 5;

    private static final int PADDING_TEXT = 15;

    public static DialogInfo diag;

    private final int dialogLength;
    protected int dialogStep;

    private Image avatar;
    private RenderedText nametag;
    private RenderedTextMultiline tf;

    public WndDialog(final int dialogID) {
        super( 0, 0, 0, Chrome.get(Chrome.Type.DIALOG));

        int heightUnit = SPDSettings.landscape()? 4:6;

        int wndWidth = PixelScene.uiCamera.width - 2 * MARGIN_X;
        int wndHeight = PixelScene.uiCamera.height / heightUnit - MARGIN_Y;

        int fontSize = SPDSettings.landscape() ? 6:5;

        diag = STORIES.get(dialogID);

        this.dialogLength = diag.LENGTH;
        this.dialogStep = 0;

        final int AvatarWidth  = 24;
        final int AvatarHeight = 23;

        final float AvaterScaleMultiplier = SPDSettings.landscape() ? 2f : 1.5f;

        avatar = new Image( Assets.EMOTION );
        avatar.frame( AvatarWidth * diag.EmotionArray[0], 1 + (AvatarHeight + 1) * diag.CharacterArray[0], AvatarWidth, AvatarHeight+1 );

        avatar.scale.set(AvaterScaleMultiplier);
        avatar.x = MARGIN_X / 2;
        avatar.y = - avatar.height() - 2;

        nametag = PixelScene.renderText( NAMES[diag.CharacterArray[0]], 8 );
        nametag.invert();
        nametag.hardlight(0x2B7BB9);

        nametag.x = avatar.width() + MARGIN_X;
        nametag.y = - 15;

        tf = PixelScene.renderMultiline( Messages.get(this, diag.ID + dialogStep), fontSize );
        tf.maxWidth( PixelScene.uiCamera.width - 2 * (MARGIN_X + PADDING_TEXT) );
        tf.setPos( fontSize, fontSize );

        add(avatar);
        add(nametag);
        add(tf);

        add( new TouchArea( chrome ) {
                @Override
                protected void onClick( Touchscreen.Touch touch ) {
                    if (dialogLength -1 <= dialogStep) {
                        dialogStep = 0;
                        hide();
                    }
                    else {
                        dialogStep += 1;
                        tf.text(Messages.get( WndDialog.class, diag.ID + dialogStep));
                        nametag.text( NAMES[diag.CharacterArray[dialogStep]] );
                        avatar.frame(AvatarWidth * diag.EmotionArray[dialogStep], 1 + (AvatarHeight+1) * diag.CharacterArray[dialogStep], AvatarWidth, AvatarHeight );
                    }
                }
            }
        );


        super.shadow.am = 0;
        yOffset = PixelScene.uiCamera.height / (SPDSettings.landscape() ? 7 : 4) - (int)((GameScene)Game.scene()).ToolbarHeight();
        resize(wndWidth, wndHeight);

        camera.resize((int)chrome.width, (int)chrome.height * 2);
        camera.scroll.set(chrome.x, -avatar.width());

    }






}

