package com.gfpixel.gfpixeldungeon.windows;

import android.app.Dialog;

import com.gfpixel.gfpixeldungeon.Assets;
import com.gfpixel.gfpixeldungeon.Chrome;
import com.gfpixel.gfpixeldungeon.SPDSettings;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Ump45;
import com.gfpixel.gfpixeldungeon.messages.Messages;
import com.gfpixel.gfpixeldungeon.scenes.GameScene;
import com.gfpixel.gfpixeldungeon.scenes.PixelScene;
import com.gfpixel.gfpixeldungeon.ui.RenderedTextMultiline;
import com.gfpixel.gfpixeldungeon.ui.Toolbar;
import com.gfpixel.gfpixeldungeon.ui.Window;
import com.gfpixel.gfpixeldungeon.utils.GLog;
import com.watabou.input.Touchscreen;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.TouchArea;
import com.watabou.utils.SparseArray;

public class WndDialog extends Window {

    static class DialogInfo
    {
        public final int ID;
        public final int LENGTH;
        public int[] CharacterArray;
        public int[] EmotionArray;
        public int BRANCH;

        public DialogInfo(int newID, int[] Chrs) {
            ID = newID;
            CharacterArray = Chrs;
            EmotionArray = new int[Chrs.length];
            LENGTH = Chrs.length;
            BRANCH = 0;
        }

        public DialogInfo(int newID, int[] Chrs, int[] Emos) {
            ID = newID;
            CharacterArray = Chrs;

            if (Chrs.length <= Emos.length) {
                EmotionArray = Emos;
            } else {
                EmotionArray = new int[Chrs.length];
                for (int i=0; i<Emos.length; ++i) {
                    if (Emos[i] > 2)
                    {
                        EmotionArray[i] = 0;
                        continue;
                    }
                    EmotionArray[i] = Emos[i];
                }
            }

            LENGTH = Chrs.length;
            BRANCH = 0;
        }

        public DialogInfo(int newID, int[] Chrs, int[] Emos, int option) {

            ID = newID;
            CharacterArray = Chrs;

            if (Chrs.length <= Emos.length) {
                EmotionArray = Emos;
            } else {
                EmotionArray = new int[Chrs.length];
                for (int i=0; i<Emos.length; ++i) {
                    if (Emos[i] > 2)
                    {
                        EmotionArray[i] = 0;
                        continue;
                    }
                    EmotionArray[i] = Emos[i];
                }
            }

            LENGTH = Chrs.length;
            BRANCH = option;
        }

        public void setBRANCH(int newOption) {
            BRANCH = newOption;
        }
    };

    public static SparseArray<String> DialogIdentifier = new SparseArray<String>();

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

    public static final int ID_SEWERS		= 0;
    public static final int ID_PRISON		= 1;
    public static final int ID_CAVES		= 2;
    public static final int ID_CITY     	= 3;
    public static final int ID_RECAVES		= 4;
    public static final int ID_HALLS		= 5;
    public static final int ID_COLDWAR		= 6;
    public static final int ID_STAR15_QUEST = 100;
    public static final int ID_M16A1_QUEST	= 101;
    public static final int ID_PPSH47_QUEST	= 102;
    public static final int ID_P7_QUEST 	= 103;

    // general tag for quest
    public static final int QUEST_COMPLETE  = -1;
    public static final int QUEST_INPROGRESS= -2;


    public static DialogInfo[] STORIES = new DialogInfo[] {
            new DialogInfo (
                    WndDialog.ID_SEWERS,
                    new int[]{UMP45, UMP9, UMP45, HK416, UMP45, G11, HK416, G11},
                    new int[]{    0,    1,     2,     2,     2,   1,     1,   2}
            ),
            new DialogInfo (
                    WndDialog.ID_PRISON,
                    new int[]{UMP9, UMP45, UMP9, G11, HK416, UMP9, UMP45},
                    new int[]{   0,     2,    2,   1,     2,    1,     1}
            ),
            new DialogInfo (
                    WndDialog.ID_CAVES,
                    new int[]{G11, UMP45, G11, UMP45, G11, HK416, UMP9, HK416, G11},
                    new int[]{  0,     2,   0,     1,   2,     1,    0,     1,   0}
            ),
            new DialogInfo (
                    WndDialog.ID_CITY,
                    new int[]{HK416, UMP45, UMP9, G11, HK416, UMP9, UMP45, DESTROYER, UMP45, UMP9},
                    new int[]{    2,     2,    2,   1,     1,    0,     0,         0,     2,    0}
            ),
            new DialogInfo (
                    WndDialog.ID_RECAVES,
                    new int[]{DESTROYER, DREAMER, DESTROYER, DREAMER, DESTROYER, DREAMER, DREAMER, DESTROYER, G11, UMP45},
                    new int[]{        1,       2,         1,       1,         1,       1,       0,         2,   1,     0}
            ),
            new DialogInfo (
                    WndDialog.ID_HALLS,
                    new int[]{UMP9, UMP45, UMP9, HK416, G11, HK416, G11, UMP45},
                    new int[]{   1,     2,    1,     0,   1,     1,   1,     0}
            ),
            new DialogInfo (
                    WndDialog.ID_COLDWAR,
                    new int[]{UMP9, G11, UMP45},
                    new int[]{   2,   1,     1}
            ),
            new DialogInfo (
                    WndDialog.ID_STAR15_QUEST,
                    new int[]{UMP45, UMP9, STAR15, STAR15, STAR15},
                    new int[]{   1,     2,      0,      1,      0}
            ),
            new DialogInfo (
                    WndDialog.ID_M16A1_QUEST,
                    new int[]{UMP45, M16A1, M16A1, HK416, UMP45},
                    new int[]{    1,     1,     0,     1,     0}
            ),
            new DialogInfo (
                    WndDialog.ID_PPSH47_QUEST,
                    new int[]{HK416, PPSH47, UMP9, PPSH47, PPSH47},
                    new int[]{    0,      1,    1,       0,     1}
            ),
            new DialogInfo (
                    WndDialog.ID_P7_QUEST,
                    new int[]{P7, UMP9, P7, P7, P7},
                    new int[]{ 1,    0,  0,  1,  0}
            )
    };

    static {
        DialogIdentifier.put( ID_SEWERS, "sewers" );
        DialogIdentifier.put( ID_PRISON, "prison" );
        DialogIdentifier.put( ID_CAVES, "caves" );
        DialogIdentifier.put( ID_CITY, "city" );
        DialogIdentifier.put( ID_RECAVES, "recaves" );
        DialogIdentifier.put( ID_HALLS, "halls" );
        DialogIdentifier.put( ID_COLDWAR, "coldwar" );
        DialogIdentifier.put( ID_STAR15_QUEST, "star15quest" );
        DialogIdentifier.put( ID_M16A1_QUEST, "m16a1quest");
        DialogIdentifier.put( ID_PPSH47_QUEST, "ppsh47quest");
        DialogIdentifier.put( ID_P7_QUEST, "p7quest");
        DialogIdentifier.put( QUEST_COMPLETE, "complete");
        DialogIdentifier.put( QUEST_INPROGRESS, "inprogress");
    };

    private static final int MARGIN_X = 10;
    private static final int MARGIN_Y = 5;
    private static final int PADDING = 16;

    private static final int PADDING_TEXT = 15;

    private final int dialogLength;
    protected int dialogStep;

    private Image AVATAR;
    private RenderedText nametag;
    private RenderedTextMultiline tf;

    public WndDialog(final int dialogID, int newLength, String text) {
        super( 0, 0, 0, Chrome.get(Chrome.Type.DIALOG));

        int heightUnit = SPDSettings.landscape()? 4:6;

        int wndWidth = PixelScene.uiCamera.width - 2 * MARGIN_X;
        int wndHeight = PixelScene.uiCamera.height / heightUnit - MARGIN_Y;

        int fontSize = SPDSettings.landscape() ? 6:5;

        dialogLength = newLength;
        dialogStep = 0;

        int widthAvatar  = 24;
        int heightAvatar = 23;

        AVATAR = new Image(Assets.EMOTION, );

        nametag = PixelScene.renderText(8 );
        tf = PixelScene.renderMultiline( fontSize );

        add(nametag);
        add(tf);


        add( new TouchArea( chrome ) {
            @Override
            protected void onClick( Touchscreen.Touch touch ) {
                if (dialogLength <= dialogStep) {
                    hide();
                }
                else {
                    dialogStep += 1;
                }
            }
        } );



        yOffset = PixelScene.uiCamera.height / 3 - (int)((GameScene)Game.scene()).ToolbarHeight();
        resize(wndWidth, wndHeight);




        //super( PixelScene.uiCamera.width - 2 * OFFSET, (PixelScene.uiCamera.height / (SPDSettings.landscape()? 4:6)) - 5, Chrome.get(Chrome.Type.GPDTALK));

        //RenderedTextMultiline tf = PixelScene.renderMultiline(fontSize);
    }






}

