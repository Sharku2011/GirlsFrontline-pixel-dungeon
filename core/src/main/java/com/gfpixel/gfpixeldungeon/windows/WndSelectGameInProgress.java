package com.gfpixel.gfpixeldungeon.windows;

import com.gfpixel.gfpixeldungeon.Assets;
import com.gfpixel.gfpixeldungeon.Badges;
import com.gfpixel.gfpixeldungeon.Challenges;
import com.gfpixel.gfpixeldungeon.GamesInProgress;
import com.gfpixel.gfpixeldungeon.GirlsFrontlinePixelDungeon;
import com.gfpixel.gfpixeldungeon.SPDSettings;
import com.gfpixel.gfpixeldungeon.actors.hero.HeroClass;
import com.gfpixel.gfpixeldungeon.actors.hero.HeroSubClass;
import com.gfpixel.gfpixeldungeon.effects.Speck;
import com.gfpixel.gfpixeldungeon.journal.Journal;
import com.gfpixel.gfpixeldungeon.scenes.PixelScene;
import com.gfpixel.gfpixeldungeon.ui.ScrollPane;
import com.gfpixel.gfpixeldungeon.ui.Window;
import com.gfpixel.gfpixeldungeon.utils.GLog;
import com.watabou.noosa.Image;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.ui.Component;
import com.watabou.utils.Point;

import java.util.ArrayList;


public class WndSelectGameInProgress extends Window {

    public static float SLOT_SCALE = 105 / 45f;

    public static int DISPWIDTH;
    public static int DISPHEIGHT;

    private ArrayList<SaveSlot> Slots = new ArrayList<>(GamesInProgress.MAX_SLOTS);

    protected static final int MARGIN = SPDSettings.landscape() ? 7:5;

    protected static Point SlotsToDisplay;
    protected static ScrollPane squad;


    public WndSelectGameInProgress()
    {
        Badges.loadGlobal();
        Journal.loadGlobal();

        SlotsToDisplay = SPDSettings.landscape()? new Point( 5, 1)  : new Point( 2, 2 );

        ArrayList<GamesInProgress.Info> games = GamesInProgress.checkAll();

        squad = new ScrollPane( new Component() ) {
            @Override
            public void onClick( float x, float y ) {
                for ( SaveSlot slot : Slots ) {
                    if (slot.inside( x, y )) {
                        slot.onClick();
                    }
                }
            }
        };

        Component content = squad.content();


        int slotNumber = 0;
        GLog.i(String.valueOf(games.size()));
        for (GamesInProgress.Info info : games) {
            SaveSlot newSlot = new SaveSlot( info ) {
                @Override
                public void onClick() {
                    GirlsFrontlinePixelDungeon.scene().add( new WndGameInProgress( this.slot ) );
                }
            };
            Slots.add( newSlot );
            content.add( newSlot );
            newSlot.setPos( 5 + (newSlot.width() + MARGIN) * ((slotNumber) % SlotsToDisplay.x),
                            MARGIN + (newSlot.height() + MARGIN) * ((slotNumber) / SlotsToDisplay.x) );
            ++slotNumber;
        }

        final int newSlotIndex = GamesInProgress.firstEmpty();

        if (newSlotIndex > 0) {
            SaveSlot newSlot = new SaveSlot( new GamesInProgress.Info() ) {
                @Override
                public void onClick() {
                    GirlsFrontlinePixelDungeon.scene().add( new WndStartGame( newSlotIndex ) );
                }
            };
            Slots.add( newSlot );
            content.add( newSlot );
            newSlot.setPos( 5 + (Slots.get(0).width() + MARGIN) * ((games.size()) % SlotsToDisplay.x),
                            MARGIN + (newSlot.height() + MARGIN) * ((games.size()) / SlotsToDisplay.x) );
        }

        add(squad);

        DISPWIDTH = SlotsToDisplay.x * (int)Slots.get(0).width() + (SlotsToDisplay.x + 1) * MARGIN;
        DISPHEIGHT = SlotsToDisplay.y * (int)Slots.get(0).height() + (SlotsToDisplay.y + 1) * MARGIN;
        resize(DISPWIDTH, DISPHEIGHT);



        Point TotalSlots = SPDSettings.landscape() ? new Point( 5, 2 ) : new Point( 2, 5 );
        int REALWIDTH = (int)Slots.get(0).width() * TotalSlots.x + MARGIN * (TotalSlots.x+1);
        int REALHEIGHT = (int)Slots.get(0).height() * TotalSlots.y  + MARGIN * (TotalSlots.y + 1);
        content.setRect(0, 0, REALWIDTH, REALHEIGHT);

        squad.setSize( DISPWIDTH, DISPHEIGHT );
        squad.scrollTo(0, 0);
    }

    private static class SaveSlot extends Component {

        protected Image portrait;
        protected Image frame;

        protected Image[] challengeMarks;
        protected Emitter[] depthEmmiters;

        private GamesInProgress.Info Info;

        public static float SCALE = SLOT_SCALE;

        protected RenderedText name;
        protected RenderedText level;
        protected RenderedText score;
        protected RenderedText depth;

        private HeroClass cls;

        public int slot;

        public SaveSlot( GamesInProgress.Info info ) {

            Info = info;
            cls = Info.heroClass;
            slot = info.slot;

            int order;

            switch (cls) {
                case WARRIOR:
                    order = 1;
                    break;
                case MAGE:
                    order = 2;
                    break;
                case ROGUE:
                    order = 3;
                    break;
                case RANGER:
                    order = 4;
                    break;
                default:
                    order = 0;
                    break;
            }

            portrait = new Image(Assets.PORTRAIT1, (order % 5) * 38, (order / 5) * 60, 38, 60);
            frame = new Image(Assets.SAVESLOT, 0, 0, 21, 52);

            setRect(0, 0, frame.width * SCALE, frame.height * SCALE);


        }

        @Override
        protected void createChildren() {
            super.createChildren();

            challengeMarks = new Image[Challenges.MASKS.length];
            depthEmmiters = new Emitter[6];



            for (int i=0; i<10; ++i) {
                challengeMarks[i] = new Image(Assets.SAVESLOT, 22, 2, 3, 3);
            }
            for (int i=0; i<6; ++i) {
                depthEmmiters[i] = new Emitter();
            }

            name = PixelScene.renderText( 7 );
            level = PixelScene.renderText( 7 );
            score = PixelScene.renderText( 8 );
            depth = PixelScene.renderText( 7 );
        }

        @Override
        protected void layout() {
            super.layout();

            frame.scale.set( SCALE );

            portrait.scale.set( 8.0f / 17.0f * SCALE );

            // 2 는 프레임 좌측 상단에서 margin 넣는거, x는 변할 일 없는데 y는 높낮이 조절용으로 좀 건드릴듯
            // 하드코딩이니 나중에 한 곳에 다 몰아넣고 조절하도록 바꾸자
            //TODO Refactor as variable
            portrait.x = x + 2 * SCALE;
            portrait.y = y + 2 * SCALE;

            add( portrait );

            frame.x = x;
            frame.y = y;

            add( frame );


            add( name );

            if (Info.heroClass == HeroClass.NONE) {
                name.text( Info.heroClass.title() );
            } else {
                name.text( Info.subClass != HeroSubClass.NONE ? Info.subClass.title() + " " + Info.heroClass.title() :Info.heroClass.title() );
            }


            name.x = x + 10.5f * SCALE - name.width() / 2f;
            name.y = y + 34.5f * SCALE - name.height() / 3f;

            name.alpha(3.0f);

            //add(depth);
            //depth.text(String.valueOf(Info.depth));
            //depth.x = x + 15f * SCALE - depth.width() / 4f;
            //depth.y = y + 2.5f * SCALE - depth.height() / 3f;

            add(level);
            level.text(String.valueOf(Info.level));

            level.x = x + 16f * SCALE - level.width() / 4f;
            level.y = y + 29f * SCALE - level.height() / 8f;

            add(score);
            score.text(String.valueOf(0));

            score.x = x + 10.5f * SCALE - score.width() / 2f;
            score.y = y + 47f * SCALE;

            for (int i = 0; i < 10; ++i) {
                add(challengeMarks[i]);

                challengeMarks[i].scale.set(SCALE);

                challengeMarks[i].y = y + (38 + 4 *(i / 5)) * SCALE;
                challengeMarks[i].x = x + (1 + 4 * (i % 5)) * SCALE;

                challengeMarks[i].visible = false;
            }

            for (int i = 0; i < Challenges.MASKS.length; ++i) {
                challengeMarks[ i ].visible = ((Info.challenges) & Challenges.MASKS[i]) != 0 ;
            }

            int procTheme = Math.min( Info.heroClass == HeroClass.NONE ? 0 : (Info.maxDepth-1)/5 + 1, 6 );

            for (int i = 0; i < procTheme; ++i) {
                add(depthEmmiters[i]);
                depthEmmiters[i].pos( x + (11f + (i+1) * 8f/(procTheme+1)) * SCALE, y + 2.5f * SCALE);
                depthEmmiters[i].fillTarget = false;
                depthEmmiters[i].pour(Speck.factory( Speck.YELLOW_LIGHT ), 0.6f);

            }

        }

        protected void onClick() {

        }

    }


}
