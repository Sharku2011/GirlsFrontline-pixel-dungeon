package com.gfpixel.gfpixeldungeon.actors.buffs;

import com.gfpixel.gfpixeldungeon.actors.Char;

public class Deploy extends FlavourBuff {

    {
        type = buffType.NEGATIVE;
    }

    // 이렇게 하지 말고 한 자리에서 가만히 있으면 스택이 쌓이는 버프로 만들어서 deploying 건드려야 할듯
    @Override
    public boolean attachTo( Char target ) {
        if (super.attachTo( target )) {
            if (!target.flying)
            {
                target.rooted = true;
            }
            target.deploying = true;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void detach() {
        target.deploying = false;
        if (target.buffs(Roots.class).isEmpty()) {
            target.rooted = false;
        }
        super.detach();
    }

}
