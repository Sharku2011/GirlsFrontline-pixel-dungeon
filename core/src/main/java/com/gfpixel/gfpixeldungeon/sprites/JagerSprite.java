package com.gfpixel.gfpixeldungeon.sprites;

import com.gfpixel.gfpixeldungeon.Assets;
import com.gfpixel.gfpixeldungeon.actors.mobs.Jager;
import com.gfpixel.gfpixeldungeon.effects.MagicMissile;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

public class JagerSprite extends WarlockSprite {

    @Override
    public void zap( int cell ) {

        turnTo( ch.pos , cell );
        play( zap );

        MagicMissile.boltFromChar( parent,
                MagicMissile.SHADOW,
                this,
                cell,
                new Callback() {
                    @Override
                    public void call() {
                        ((Jager)ch).onZapComplete();
                    }
                } );
        Sample.INSTANCE.play( Assets.SND_ZAP );
    }
}
