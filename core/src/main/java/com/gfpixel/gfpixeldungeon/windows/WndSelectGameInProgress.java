package com.gfpixel.gfpixeldungeon.windows;

import com.gfpixel.gfpixeldungeon.Assets;
import com.gfpixel.gfpixeldungeon.Challenges;
import com.gfpixel.gfpixeldungeon.scenes.PixelScene;
import com.gfpixel.gfpixeldungeon.ui.ScrollPane;
import com.gfpixel.gfpixeldungeon.ui.Window;
import com.gfpixel.gfpixeldungeon.utils.GLog;
import com.watabou.input.Touchscreen;
import com.watabou.noosa.BitmapText;
import com.watabou.noosa.Image;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.TouchArea;
import com.watabou.noosa.ui.Component;
import com.watabou.utils.Random;

import android.graphics.Typeface;


public class WndSelectGameInProgress extends Window {

    public static final int HEIGHT   = PixelScene.uiCamera.height / 3 * 2;;

    protected static ScrollPane squad;

    public WndSelectGameInProgress()
    {

        int DISPWIDTH = 323;

        GLog.i(String.valueOf(HEIGHT));

        resize(DISPWIDTH, HEIGHT);

        squad = new ScrollPane( new Component() );

        Component content = squad.content();

        content.setRect(0, 0, 500, HEIGHT);

        for (int i=0; i<8; ++i) {

            Component btn = new SaveSlot( i%5 );

            content.add(btn);
            btn.setPos(  5 + (btn.width() + 7) * i, 5);

        }

        add(squad);

        squad.setSize( DISPWIDTH, HEIGHT );
        squad.scrollTo(0, 0);

    }

    private static class SaveSlot extends Component {

        protected Image portrait;
        protected Image frame;

        protected Image[] challenges;

        protected TouchArea hotArea;
        public float SCALE;

        protected String[] names = { "파이터 UMP45", "호크아이 G11", "암살자 UMP9", "저격수 HK416", "UMP 40"};
        protected RenderedText name;
        protected RenderedText level;
        protected RenderedText score;

        private int order;

        public SaveSlot( int cl ) {

            order = cl;

            portrait = new Image(Assets.PORTRAIT, cl * 20, 0, 19, 25);
            frame = new Image(Assets.SAVESLOT, 0, 0, 21, 52);
            SCALE = (HEIGHT - 5*2) / frame.height;

            setRect(0, 0, frame.width * SCALE, frame.height * SCALE);


        }

        @Override
        protected void createChildren() {
            super.createChildren();

            challenges = new Image[10];

            for (int i=0; i<10; ++i) {
                challenges[i] = new Image(Assets.SAVESLOT, 22, 2, 3, 3);
            }

            hotArea = new TouchArea(0, 0, 0, 0) {
                @Override
                protected void onClick( Touchscreen.Touch touch ) {

                    GLog.i("Hello!");
                }
                @Override
                protected void onDrag( Touchscreen.Touch touch ) {
                    squad.OnDrag(touch);
                }
            };

            add(hotArea);

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

            hotArea.x = x;
            hotArea.y = y;
            hotArea.width = width;
            hotArea.height = height;

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



    }


}
