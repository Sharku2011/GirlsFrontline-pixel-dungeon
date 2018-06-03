package com.gfpixel.gfpixeldungeon;

import com.watabou.utils.SparseArray;

import java.util.HashSet;

public  class DialogInfo
{
    public final String ID;
    public final int LENGTH;
    public int[] CharacterArray;
    public int[] EmotionArray;
    public String BRANCH;

    public DialogInfo(String newID, int[] Chrs) {
        this.ID = newID;
        this.CharacterArray = Chrs;
        this.EmotionArray = new int[Chrs.length];
        this.LENGTH = Chrs.length;
        this.BRANCH = "";
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
        this.BRANCH = "";
    }

    public DialogInfo(String newID, int[] Chrs, int[] Emos, String option) {

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

    private static final int G11         = 0;
    private static final int UMP45       = 1;
    private static final int UMP9        = 2;
    private static final int HK416       = 3;
    private static final int P7          = 4;
    private static final int STAR15      = 5;
    private static final int M16A1       = 6;
    private static final int JEPUTY      = 7;
    private static final int PPSH47      = 8;
    private static final int DESTROYER   = 9;
    private static final int DREAMER     = 10;

    public static final String[] NAMES = new String[] {
            "G11", "UMP45", "UMP9", "HK416", "P7", "ST-AR 15", "M16A1", "제퓨티", "Ppsh-47", "디스트로이어", "드리머"
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
    public static final int INIT        = 0;
    public static final int INPROGRESS  = 1;
    public static final int COMPLETE    = 2;

    public static HashSet<Integer> EXPIRED = new HashSet<>();

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
                        new int[]{    0,     2,      0,      1,     0}
                )
        );
        STORIES.put( ID_STAR15_QUEST + INPROGRESS,
                new DialogInfo(
                        "star15quest.inprogress",
                        new int[]{STAR15},
                        new int[]{     0}
                )
        );
        STORIES.put( ID_STAR15_QUEST + COMPLETE,
                new DialogInfo(
                        "star15quest.complete",
                        new int[]{STAR15},
                        new int[]{     1}
                )
        );
        STORIES.put( ID_M16A1_QUEST,
                new DialogInfo (
                        "m16a1quest",
                        new int[]{UMP45, M16A1, M16A1, HK416, UMP45},
                        new int[]{    1,     1,     0,     1,     0}
                )
        );
        STORIES.put( ID_M16A1_QUEST + INPROGRESS,
                new DialogInfo (
                        "m16a1quest.inprogress",
                        new int[]{M16A1, M16A1},
                        new int[]{    0,     0}
                )
        );
        STORIES.put( ID_M16A1_QUEST + COMPLETE,
                new DialogInfo (
                        "m16a1quest.complete",
                        new int[]{M16A1},
                        new int[]{    0}
                )
        );
        STORIES.put( ID_PPSH47_QUEST,
                new DialogInfo (
                        "ppsh47quest",
                        new int[]{HK416, PPSH47, UMP9, PPSH47, PPSH47},
                        new int[]{    0,      1,    1,       0,     1}
                )
        );
        STORIES.put( ID_PPSH47_QUEST + INPROGRESS,
                new DialogInfo (
                        "ppsh47quest.inprogress",
                        new int[]{PPSH47},
                        new int[]{    0}
                )
        );
        STORIES.put( ID_PPSH47_QUEST + COMPLETE,
                new DialogInfo (
                        "ppsh47quest.complete",
                        new int[]{PPSH47},
                        new int[]{    1}
                )
        );
        STORIES.put( ID_P7_QUEST,
                new DialogInfo (
                        "p7quest",
                        new int[]{P7, UMP9, P7, P7, P7},
                        new int[]{ 1,    0,  0,  1,  0}
                )
        );
        STORIES.put( ID_P7_QUEST + INPROGRESS,
                new DialogInfo (
                        "p7quest.inprogress",
                        new int[]{P7},
                        new int[]{    0}
                )
        );
        STORIES.put( ID_P7_QUEST + COMPLETE,
                new DialogInfo (
                        "p7quest.complete",
                        new int[]{P7},
                        new int[]{    1}
                )
        );
    }
};

