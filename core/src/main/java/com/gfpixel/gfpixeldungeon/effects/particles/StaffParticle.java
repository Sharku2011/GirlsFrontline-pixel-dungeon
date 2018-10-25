package com.gfpixel.gfpixeldungeon.effects.particles;

import com.gfpixel.gfpixeldungeon.items.wands.Wand;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.utils.Random;

public class StaffParticle extends PixelParticle {

    Wand wand;

    private float minSize;
    private float maxSize;
    public float sizeJitter = 0;

    public StaffParticle(){
        super();
    }

    public void reset( float x, float y ) {
        revive();

        speed.set(0);

        this.x = x;
        this.y = y;


        if (wand != null) {
            wand.staffFx( this );
        }
    }

    public void setSize( float minSize, float maxSize ){
        this.minSize = minSize;
        this.maxSize = maxSize;
    }

    public void setLifespan( float life ){
        lifespan = left = life;
    }

    public void shuffleXY(float amt){
        x += Random.Float(-amt, amt);
        y += Random.Float(-amt, amt);
    }

    public void radiateXY(float amt){
        float hypot = (float)Math.hypot(speed.x, speed.y);
        this.x += speed.x/hypot*amt;
        this.y += speed.y/hypot*amt;
    }

    @Override
    public void update() {
        super.update();
        size(minSize + (left / lifespan)*(maxSize-minSize) + Random.Float(sizeJitter));
    }
}
