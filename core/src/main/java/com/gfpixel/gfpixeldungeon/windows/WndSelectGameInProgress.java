package com.gfpixel.gfpixeldungeon.windows;

import com.gfpixel.gfpixeldungeon.Assets;
import com.gfpixel.gfpixeldungeon.scenes.PixelScene;
import com.gfpixel.gfpixeldungeon.ui.ScrollPane;
import com.gfpixel.gfpixeldungeon.ui.Window;
import com.gfpixel.gfpixeldungeon.utils.GLog;
import com.watabou.input.Touchscreen;
import com.watabou.noosa.Image;
import com.watabou.noosa.TouchArea;
import com.watabou.noosa.ui.Component;

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

            Component btn = new SaveSlot( i%4 );

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
        protected TouchArea hotArea;
        public float SCALE;


        public SaveSlot( int cl ) {

            portrait = new Image(Assets.PORTRAIT, cl * 20, 0, 19, 25);
            frame = new Image(Assets.SAVESLOT, 0, 0, 21, 52);
            SCALE = (HEIGHT - 5*2) / frame.height;

            setRect(0, 0, frame.width * SCALE, frame.height * SCALE);

        }

        @Override
        protected void createChildren() {
            super.createChildren();

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
        }

        @Override
        protected void layout() {
            super.layout();

            frame.scale.set( SCALE );

            portrait.scale.set( SCALE );


            portrait.x = x + 2 * SCALE;
            portrait.y = y + 6 * SCALE;

            add( portrait );

            //2.408907 // 2.6923077

            frame.x = x;
            frame.y = y;

            add( frame );

            hotArea.x = x;
            hotArea.y = y;
            hotArea.width = width;
            hotArea.height = height;


        }


    }
}
