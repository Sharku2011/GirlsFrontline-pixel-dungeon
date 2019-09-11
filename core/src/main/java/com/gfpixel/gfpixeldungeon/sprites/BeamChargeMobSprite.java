package com.gfpixel.gfpixeldungeon.sprites;

import com.gfpixel.gfpixeldungeon.actors.Actor;
import com.gfpixel.gfpixeldungeon.actors.Char;
import com.gfpixel.gfpixeldungeon.actors.mobs.BeamChargeMob;
import com.gfpixel.gfpixeldungeon.actors.mobs.Hydra;
import com.gfpixel.gfpixeldungeon.effects.Beam;
import com.gfpixel.gfpixeldungeon.tiles.DungeonTilemap;
import com.watabou.noosa.particles.Emitter;

public abstract class BeamChargeMobSprite extends MobSprite {

    private int beamTarget;
    protected Animation charging;
    protected Emitter chargeParticles;

    @Override
    public void link(Char ch) {
        super.link(ch);
        if (((BeamChargeMob)ch).isCharged()) play(charging);
    }

    @Override
    public void update() {
        super.update();
        chargeParticles.pos(center());
        chargeParticles.visible = visible;
    }

    @Override
    public void play(Animation anim) {
        chargeParticles.on = anim == charging;
        super.play(anim);
    }

    @Override
    public void attack( int pos ) {
        beamTarget = pos;
        super.attack( pos );
    }

    public void charge( int pos ){
        turnTo(ch.pos, pos);
        play(charging);
    }

    @Override
    public void onComplete( Animation anim ) {
        super.onComplete( anim );

        if (anim == attack) {
            idle();

            if (Actor.findChar(beamTarget) != null){
                parent.add(new Beam.DeathRay(center(), Actor.findChar( beamTarget ).sprite.center()));
            } else {
                parent.add(new Beam.DeathRay(center(), DungeonTilemap.raisedTileCenterToWorld( beamTarget )));
            }
            ((Hydra)ch).shootBeam();
            ch.next();
        } else if (anim == charging) {
            ((Hydra)ch).charge();
            ch.next();
        } else if (anim == die){
            chargeParticles.killAndErase();
        }
    }
}
