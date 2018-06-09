package com.gfpixel.gfpixeldungeon.windows;

import com.gfpixel.gfpixeldungeon.Assets;
import com.gfpixel.gfpixeldungeon.GamesInProgress;
import com.gfpixel.gfpixeldungeon.SPDSettings;
import com.gfpixel.gfpixeldungeon.scenes.PixelScene;
import com.gfpixel.gfpixeldungeon.ui.ScrollPane;
import com.gfpixel.gfpixeldungeon.ui.Window;
import com.gfpixel.gfpixeldungeon.utils.GLog;
import com.watabou.noosa.Image;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.TouchArea;
import com.watabou.noosa.ui.Component;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

import java.util.ArrayList;


public class WndSelectGameInProgress extends Window {

    public static float SLOT_SCALE = 140 / 52f;

    public static int DISPWIDTH;
    public static int DISPHEIGHT;

    private ArrayList<SaveSlot> Slots = new ArrayList<>(GamesInProgress.MAX_SLOTS);

    protected static final int MARGIN = SPDSettings.landscape() ? 7:5;

    protected static Point SlotsToDisplay;
    protected static ScrollPane squad;


    public WndSelectGameInProgress()
    {
        SlotsToDisplay = SPDSettings.landscape()? new Point( 5, 1)  : new Point( 2, 2 );

        ArrayList<GamesInProgress.Info> games = GamesInProgress.checkAll();

        int slotCount = Math.min(GamesInProgress.MAX_SLOTS, games.size()+1);

        squad = new ScrollPane( new Component() ) {
            @Override
            public void onClick( float x, float y ) {
                for ( SaveSlot slot : Slots ) {
                    if (slot.inside(x,y)) {
                        slot.onClick();
                    }
                }
            }
        };

        Component content = squad.content();

        for (int i=0; i<10; ++i) {

            Slots.add(new SaveSlot( i%5 ));

            content.add(Slots.get(i));
            Slots.get(i).setPos( 5 + (Slots.get(i).width() + MARGIN) * (i % SlotsToDisplay.x), MARGIN + (Slots.get(i).height() + MARGIN) * (i / SlotsToDisplay.x) );

        }

        add(squad);

        DISPWIDTH = SlotsToDisplay.x * (int)Slots.get(0).width() + (SlotsToDisplay.x + 1) * MARGIN;
        DISPHEIGHT = SlotsToDisplay.y * (int)Slots.get(0).height() + (SlotsToDisplay.y + 1) * MARGIN;
        resize(DISPWIDTH, DISPHEIGHT);
        squad.setSize( DISPWIDTH, DISPHEIGHT );
        squad.scrollTo(0, 0);

        Point TotalSlots = SPDSettings.landscape() ? new Point( 5, 2 ) : new Point( 2, 5 );
        int REALWIDTH = (int)Slots.get(0).width() * TotalSlots.x + MARGIN * (TotalSlots.x+1);
        int REALHEIGHT = (int)Slots.get(0).height() * TotalSlots.y  + MARGIN * (TotalSlots.y + 1);
        content.setRect(0, 0, REALWIDTH, REALHEIGHT);
    }

    private static class SaveSlot extends Component {

        protected Image portrait;
        protected Image frame;

        protected Image[] challenges;

        protected TouchArea hotArea;
        public static float SCALE = SLOT_SCALE;

        protected String[] names = { "파이터 UMP45", "호크아이 G11", "암살자 UMP9", "저격수 HK416", "UMP 40"};
        protected RenderedText name;
        protected RenderedText level;
        protected RenderedText score;

        private int order;


        public SaveSlot( int cl ) {

            order = cl;

            portrait = new Image(Assets.PORTRAIT, cl * 20, 0, 19, 25);
            frame = new Image(Assets.SAVESLOT, 0, 0, 21, 52);

            setRect(0, 0, frame.width * SCALE, frame.height * SCALE);


        }

        @Override
        protected void createChildren() {
            super.createChildren();

            challenges = new Image[10];

            for (int i=0; i<10; ++i) {
                challenges[i] = new Image(Assets.SAVESLOT, 22, 2, 3, 3);
            }

            name = PixelScene.renderText( 8 );
            level = PixelScene.renderText( 7 );
            score = PixelScene.renderText( 8 );
        }

        @Override
        protected void layout() {
            super.layout();

            frame.scale.set( SCALE );

            portrait.scale.set( 15.0f / 17.0f * SCALE );

            portrait.x = x + 2 * SCALE;
            portrait.y = y + 8 * SCALE;

            add( portrait );

            frame.x = x;
            frame.y = y;

            add( frame );


            add(name);
            name.text( names[order] );

            name.x = x + 10.5f * SCALE - name.width() / 2f; //+ getNameLength()/2f * SCALE;
            name.y = y + 34.5f * SCALE - name.height() / 3f;

            name.alpha(3.0f);

            add(level);
            level.text(String.valueOf(Random.Int(1,30)));

            level.x = x + 16 * SCALE - level.width() / 4f;
            level.y = y + 29 * SCALE - level.height() / 8f;

            add(score);
            score.text(String.valueOf(Random.Int(400,50000)));

            score.x = x + 10.5f * SCALE - score.width() / 2f;
            score.y = y + 47 * SCALE;

            for (int i = 0; i < 10; ++i) {
                add(challenges[i]);

                challenges[i].scale.set(SCALE);

                challenges[i].y = y + (38 + 4 *(i / 5)) * SCALE;
                challenges[i].x = x + (1 + 4 * (i % 5)) * SCALE;

                challenges[i].visible = false;
            }

            for (int i=0; i < Random.Int(3, 8); ++i) {
                int index = Random.Int( 0, 9 );
                challenges[index].visible = true;
            }

        }

        protected void onClick() {
            GLog.i("Hi");
        }

    }


}
