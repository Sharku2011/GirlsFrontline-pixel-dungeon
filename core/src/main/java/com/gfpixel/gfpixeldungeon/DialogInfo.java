package com.gfpixel.gfpixeldungeon;

import com.watabou.utils.Point;
import com.watabou.utils.SparseArray;

public  class DialogInfo
{
    public final String ID;
    public final int LENGTH;
    public Point[] CharacterArray;
    public int[] EmotionArray;
    public String BRANCH;

    public DialogInfo(String newID, Point[] Chrs) {
        this.ID = newID;
        this.CharacterArray = Chrs;
        this.EmotionArray = new int[Chrs.length];
        this.LENGTH = Chrs.length;
        this.BRANCH = "";
    }

    public DialogInfo(String newID, Point[] Chrs, int[] Emos) {
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
        this.BRANCH = "";
    }

    public DialogInfo(String newID, Point[] Chrs, int[] Emos, String option) {

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
        this.setBRANCH(option);
    }

    public void setBRANCH(String newOption) {
        this.BRANCH = "."+newOption+".";
    }

    private static final int GRIFFON    = 0;
    private static final int QUEST      = 1;
    private static final int RABBIT     = 2;
    private static final int SANGVIS    = 3;

    private static final Point G11          = new Point(GRIFFON, 0);
    private static final Point UMP45        = new Point(GRIFFON, 1);
    private static final Point UMP9         = new Point(GRIFFON, 2);
    private static final Point HK416        = new Point(GRIFFON, 3);
    private static final Point UMP40        = new Point(GRIFFON, 4);

    private static final Point STAR15       = new Point(QUEST, 0);
    private static final Point M16A1        = new Point(QUEST, 1);
    private static final Point PPSH47       = new Point(QUEST, 2);
    private static final Point P7           = new Point(QUEST, 3);

    private static final Point ELPHELT      = new Point(RABBIT, 0);
    private static final Point JEPUTY       = new Point(RABBIT, 1);
    private static final Point NOEL         = new Point(RABBIT, 2);

    private static final Point EXECUTIONER  = new Point(SANGVIS, 0);
    // TODO emotion.png에서 우로보로스의 스프라이트 위치를 1열 2번째에서 다른 위치로 변경
    private static final Point UROBOROS     = new Point(SANGVIS, 1);
    private static final Point GAGER        = new Point(SANGVIS, 2);
    private static final Point DESTROYER    = new Point(SANGVIS, 3);
    private static final Point DREAMER      = new Point(SANGVIS, 4);

    public static final String[][] NAMES = new String[][] {
            {"G11", "UMP45", "UMP9", "HK416", "UMP40"},
            {"ST-AR 15", "M16A1", "Ppsh-47", "P7"},
            {"엘펠트 발렌타인", "제퓨티", "노엘"},
            {"엑스큐셔너", "우로보로스", "게이저", "디스트로이어", "드리머"}
    };

    public static final int ID_SEWER		= 1;
    public static final int ID_SEWER_BOSS   = 100;

    public static final int ID_PRISON		= 2;
    public static final int ID_PRISON_BOSS	= 200;

    public static final int ID_RABBIT       = 3;
    public static final int ID_RABBIT_BOSS  = 300;

    public static final int ID_CAVES		= 4;
    public static final int ID_CAVES_BOSS	= 400;

    public static final int ID_CITY     	= 5;
    public static final int ID_CITY_BOSS   	= 500;

    public static final int ID_RECAVES		= 6;
    public static final int ID_RECAVES_BOSS	= 600;

    public static final int ID_HALLS		= 7;
    public static final int ID_HALLS_BOSS	= 700;

    public static final int ID_COLDWAR		= 8;
    public static final int ID_COLDWAR_BOSS	= 800;


    public static final int ID_STAR15_QUEST = 1000;
    public static final int ID_M16A1_QUEST	= 2000;
    public static final int ID_PPSH47_QUEST	= 3000;
    public static final int ID_P7_QUEST 	= 4000;
    public static final int ID_NOEL_QUEST 	= 5000;

    // general tag for quest
    public static final int INIT        = 0;
    public static final int INPROGRESS  = 1;
    public static final int COMPLETE    = 2;



    public static SparseArray<DialogInfo> STORIES = new SparseArray<> ();

    static {
        STORIES.put( ID_SEWER,
                new DialogInfo (
                        "sewer",
                        new Point[]{UMP45, UMP9, UMP45, HK416, UMP45, G11, HK416, G11},
                        new int[]{    0,    1,     2,     2,     2,   1,     1,   2} )
        );
        STORIES.put( ID_SEWER_BOSS,
                new DialogInfo (
                        "excutioner",
                        new Point[]{EXECUTIONER},
                        new int[]{        0} )
        );
        STORIES.put( ID_PRISON,
                new DialogInfo (
                        "prison",
                        new Point[]{UMP9, UMP45, UMP9, G11, HK416, UMP9, UMP45},
                        new int[]{   0,     2,    2,   1,     2,    1,     1}
                )
        );
        STORIES.put( ID_PRISON_BOSS,
                new DialogInfo (
                        "uroboros",
                        new Point[]{UROBOROS},
                        new int[]{        0} )
        );
        STORIES.put( ID_RABBIT,
                new DialogInfo (
                        "rabbit",
                        new Point[]{UMP9, UMP45, UMP9, G11, UMP9, HK416, UMP9, UMP45, UMP9, UMP45},
                        new int[]  {   0,   2,    1,   1,   1,   2,   1,   2,   1,   1}
                )
        );
        STORIES.put( ID_RABBIT_BOSS,
                new DialogInfo (
                        "elphelt",
                        new Point[]{NOEL, ELPHELT},
                        new int[]  {      1,       1}
                )
        );
        STORIES.put( ID_RABBIT_BOSS + COMPLETE,
                new DialogInfo (
                        "elphelt.complete",
                        new Point[]{ELPHELT, HK416, NOEL, ELPHELT, ELPHELT},
                        new int[]  {      2,    2,  0,  1,   0}
                )
        );
        STORIES.put( ID_CAVES,
                new DialogInfo (
                        "caves",
                        new Point[]{G11, UMP45, G11, UMP45, G11, HK416, UMP9, HK416, G11},
                        new int[]  {  0,     2,   0,     1,   2,     1,    0,     1,   0}
                )
        );
        STORIES.put( ID_CAVES_BOSS,
                new DialogInfo (
                        "dm300",
                        new Point[]{GAGER},
                        new int[]  {    0}
                )
        );
        STORIES.put( ID_CITY,
                new DialogInfo (
                        "city",
                        new Point[]{HK416, UMP45, UMP9, G11, HK416, UMP9, UMP45, DESTROYER, UMP45, UMP9},
                        new int[]{    2,     2,    2,   1,     1,    0,     0,         0,     2,    0}
                )
        );
        STORIES.put( ID_CITY_BOSS,
                new DialogInfo(
                        "destroyer",
                        new Point[]{DESTROYER},
                        new int[]{0}
                )
        );
        STORIES.put( ID_RECAVES,
                new DialogInfo(
                        "recaves",
                        new Point[]{DESTROYER, DREAMER, DESTROYER, DREAMER, DESTROYER, DREAMER, DREAMER, DESTROYER, G11, UMP45},
                        new int[]{        1,       2,         1,       1,         1,       1,       0,         2,   1,     0}
                )
        );
        STORIES.put( ID_HALLS,
                new DialogInfo (
                        "halls",
                        new Point[]{UMP9, UMP45, UMP9, HK416, G11, HK416, G11, UMP45},
                        new int[]{   1,     2,    1,     0,   1,     1,   1,     0}
                )
        );
        STORIES.put( ID_STAR15_QUEST,
                new DialogInfo (
                        "star15quest",
                        new Point[]{UMP45, UMP9, STAR15, STAR15, STAR15},
                        new int[]{    0,     2,      0,      1,     0}
                )
        );
        STORIES.put( ID_STAR15_QUEST + INPROGRESS,
                new DialogInfo(
                        "star15quest.inprogress",
                        new Point[]{STAR15},
                        new int[]{     0}
                )
        );
        STORIES.put( ID_STAR15_QUEST + COMPLETE,
                new DialogInfo(
                        "star15quest.complete",
                        new Point[]{STAR15},
                        new int[]{     1}
                )
        );
        STORIES.put( ID_M16A1_QUEST,
                new DialogInfo (
                        "m16a1quest",
                        new Point[]{UMP45, M16A1, M16A1, HK416, UMP45},
                        new int[]{    1,     1,     0,     1,     0}
                )
        );
        STORIES.put( ID_M16A1_QUEST + INPROGRESS,
                new DialogInfo (
                        "m16a1quest.inprogress",
                        new Point[]{M16A1, M16A1},
                        new int[]{    0,     0}
                )
        );
        STORIES.put( ID_M16A1_QUEST + COMPLETE,
                new DialogInfo (
                        "m16a1quest.complete",
                        new Point[]{M16A1},
                        new int[]{    0}
                )
        );
        STORIES.put( ID_M16A1_QUEST + INPROGRESS,
                new DialogInfo (
                        "m16a1quest.inprogress",
                        new Point[]{M16A1, M16A1},
                        new int[]{    0,     0}
                )
        );
        STORIES.put( ID_NOEL_QUEST,
                new DialogInfo (
                        "noelquest",
                        new Point[]{NOEL, UMP9, NOEL, UMP9, NOEL, UMP45, NOEL, NOEL},
                        new int[]  {   0,    1,    2,    1,    1,     0,    0,  1}
                )
        );
        STORIES.put( ID_NOEL_QUEST + INPROGRESS,
                new DialogInfo (
                        "noelquest.inprogress",
                        new Point[]{NOEL},
                        new int[]{    0}
                )
        );
        STORIES.put( ID_NOEL_QUEST + COMPLETE,
                new DialogInfo (
                        "noelquest.complete",
                        new Point[]{NOEL},
                        new int[]{    1}
                )
        );
        STORIES.put( ID_PPSH47_QUEST,
                new DialogInfo (
                        "ppsh47quest",
                        new Point[]{HK416, PPSH47, UMP9, PPSH47, PPSH47},
                        new int[]{    0,      1,    1,       0,     1}
                )
        );
        STORIES.put( ID_PPSH47_QUEST + INPROGRESS,
                new DialogInfo (
                        "ppsh47quest.inprogress",
                        new Point[]{PPSH47},
                        new int[]{    0}
                )
        );
        STORIES.put( ID_PPSH47_QUEST + COMPLETE,
                new DialogInfo (
                        "ppsh47quest.complete",
                        new Point[]{PPSH47},
                        new int[]{    1}
                )
        );
        STORIES.put( ID_P7_QUEST,
                new DialogInfo (
                        "p7quest",
                        new Point[]{P7, UMP9, P7, P7, P7},
                        new int[]{ 1,    0,  0,  1,  0}
                )
        );
        STORIES.put( ID_P7_QUEST + INPROGRESS,
                new DialogInfo (
                        "p7quest.inprogress",
                        new Point[]{P7},
                        new int[]{    0}
                )
        );
        STORIES.put( ID_P7_QUEST + COMPLETE,
                new DialogInfo (
                        "p7quest.complete",
                        new Point[]{P7},
                        new int[]{    1}
                )
        );
    }
};

