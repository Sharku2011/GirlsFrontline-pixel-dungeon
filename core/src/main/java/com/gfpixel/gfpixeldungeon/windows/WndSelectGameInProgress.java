package com.gfpixel.gfpixeldungeon.windows;

import com.gfpixel.gfpixeldungeon.actors.hero.HeroClass;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Dagger;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.gfpixel.gfpixeldungeon.scenes.PixelScene;
import com.gfpixel.gfpixeldungeon.sprites.ItemSprite;
import com.gfpixel.gfpixeldungeon.ui.RedButton;
import com.gfpixel.gfpixeldungeon.ui.ScrollPane;
import com.gfpixel.gfpixeldungeon.ui.Window;
import com.watabou.noosa.Image;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.ui.Button;
import com.watabou.noosa.ui.Component;

import static com.gfpixel.gfpixeldungeon.sprites.ItemSpriteSheet.*;

public class WndSelectGameInProgress extends Window {

    private static final int WIDTH    = 300;
    private static final int HEIGHT   = 150;

    private ScrollPane squad;

    public WndSelectGameInProgress()
    {
        resize(WIDTH, HEIGHT);

        squad = new ScrollPane( new Component() );
        add(squad);

        Component content = squad.content();


        //content.setSize(500f, (float)HEIGHT);
        content.setRect(0, 0, 500, HEIGHT);

        for (int i=0; i<8; ++i) {
            RedButton btn = new RedButton(String.valueOf(i+1));
            btn.icon(new Image(HeroClass.WARRIOR.spritesheet(), 0, 15*1, 12, 15));
            btn.setRect((2*i+1) * WIDTH/12f, 15f, WIDTH/8f, 120f);
            content.add(btn);
        }

        squad.setSize( WIDTH, HEIGHT );
        squad.scrollTo(0, 0);

    }

    private static class SaveSlotButton extends Button {
        @Override
        protected void createChildren() {
            super.createChildren();
        }

        @Override
        protected void layout() {
            super.layout();
        }
    }
}
