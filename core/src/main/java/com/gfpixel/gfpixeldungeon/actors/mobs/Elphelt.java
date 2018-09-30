package com.gfpixel.gfpixeldungeon.actors.mobs;

import com.gfpixel.gfpixeldungeon.Assets;
import com.gfpixel.gfpixeldungeon.DialogInfo;
import com.gfpixel.gfpixeldungeon.Dungeon;
import com.gfpixel.gfpixeldungeon.actors.Actor;
import com.gfpixel.gfpixeldungeon.actors.Char;
import com.gfpixel.gfpixeldungeon.actors.blobs.Blob;
import com.gfpixel.gfpixeldungeon.actors.blobs.GenoiseWarn;
import com.gfpixel.gfpixeldungeon.actors.blobs.GooWarn;
import com.gfpixel.gfpixeldungeon.actors.buffs.Buff;
import com.gfpixel.gfpixeldungeon.actors.buffs.Charm;
import com.gfpixel.gfpixeldungeon.actors.buffs.Paralysis;
import com.gfpixel.gfpixeldungeon.actors.buffs.Terror;
import com.gfpixel.gfpixeldungeon.effects.Beam;
import com.gfpixel.gfpixeldungeon.effects.CellEmitter;
import com.gfpixel.gfpixeldungeon.effects.Pushing;
import com.gfpixel.gfpixeldungeon.effects.Speck;
import com.gfpixel.gfpixeldungeon.effects.particles.BlastParticle;
import com.gfpixel.gfpixeldungeon.effects.particles.BloodParticle;
import com.gfpixel.gfpixeldungeon.effects.particles.SmokeParticle;
import com.gfpixel.gfpixeldungeon.items.Heap;
import com.gfpixel.gfpixeldungeon.items.wands.WandOfDisintegration;
import com.gfpixel.gfpixeldungeon.items.weapon.enchantments.Grim;
import com.gfpixel.gfpixeldungeon.items.weapon.enchantments.Vampiric;
import com.gfpixel.gfpixeldungeon.levels.Terrain;
import com.gfpixel.gfpixeldungeon.levels.features.Door;
import com.gfpixel.gfpixeldungeon.mechanics.Ballistica;
import com.gfpixel.gfpixeldungeon.messages.Messages;
import com.gfpixel.gfpixeldungeon.scenes.GameScene;
import com.gfpixel.gfpixeldungeon.sprites.ElpheltSprite;
import com.gfpixel.gfpixeldungeon.ui.BossHealthBar;
import com.gfpixel.gfpixeldungeon.utils.GLog;
import com.gfpixel.gfpixeldungeon.windows.WndDialog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.GameMath;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import static com.gfpixel.gfpixeldungeon.Dungeon.hero;


public class Elphelt extends Mob {

    private static final float TIME_TO_EXPLODE = 2f;
    private static final int POWER_OF_BLAST = 4;
    private static final int REGEN_OF_GENOISE = 2;
    private static final int RANGE_MAGNUM = 5;

    {
        spriteClass = ElpheltSprite.class;

        HP = HT = 600;
        EXP = 100;
        defenseSkill = 35;
        baseSpeed = 1f;
        maxLvl = 20;

        HUNTING = new Hunting();

        properties.add(Property.BOSS);
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange(25, 35);
    }

    @Override
    public int attackSkill( Char target ) {
        return 30;
    }

    @Override
    protected float attackDelay() {
        return 1f;
    }

    private float warnDelay() { return 1f; }
    private float bridleExpressDelay() { return  1f; }
    private float magnumDelay() { return 3f; }


    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 10);
    }

    private Ballistica traceGenoise;
    private Ballistica traceMagnum;
    private Ballistica traceRush;

    public final Ballistica getTraceGenoise()   { return traceGenoise; }
    public final Ballistica getTraceMagnum()    { return traceMagnum; }
    public final Ballistica getTraceRush()      { return traceRush; }

    public boolean onGenoise = false;
    private int maxGenoiseStack = 5;
    private int curGenoiseStack = maxGenoiseStack;
    public int genoiseDst = -1;


    private int timerRush = 3;
    private final int COOLDOWN_RUSH = 3;

    public boolean canRush = false;
    public boolean onRush = false;

    public int dstRush = -1;

    public boolean canBlast = false;

    private HashSet<Genoise> Genoises = new HashSet<>();

    public int phase = 0;

	@Override
    public void move( int step ) {
        Dungeon.level.seal();
        super.move( step );
    }

    @Override
    public void notice() {
        super.notice();
        BossHealthBar.assignBoss(this);
        if (!Dungeon.level.locked) {
            WndDialog.ShowChapter(DialogInfo.ID_RABBIT_BOSS);
            if (phase < 1) {
                phase = 1;
            }
        }
    }


    @Override
    protected boolean act() {
        switch (phase) {
            case 0: default:
                break;
            case 1:
                if (!onGenoise && curGenoiseStack < maxGenoiseStack)
                {
                    curGenoiseStack += REGEN_OF_GENOISE;
                    if (curGenoiseStack >= maxGenoiseStack) {
                        curGenoiseStack = maxGenoiseStack;
                    }
                }
                break;
            case 2:
                if (!onRush && !canRush) {
                    timerRush += 1;
                    if (timerRush >= COOLDOWN_RUSH) {
                        timerRush = COOLDOWN_RUSH;
                        canRush = true;
                    }
                }
                break;
        }

        return super.act();
    }


    @Override
    protected boolean canAttack( Char enemy ) {

	    if (enemy == null) {
	        return false;
        }

        switch (phase) {
            case 0: default:
                return super.canAttack(enemy);
            case 1:
                // check genoise stack
                if (curGenoiseStack <= 0)
                {
                    curGenoiseStack = 0;
                    onGenoise = false;
                } else if (curGenoiseStack >= maxGenoiseStack) {
                    curGenoiseStack = maxGenoiseStack;
                    onGenoise = true;
                }
                GLog.i("onGenoise: "+String.valueOf(onGenoise));
                GLog.i("curGenoiseStack: "+String.valueOf(curGenoiseStack));
                if ( onGenoise ) {
                    traceGenoise = new Ballistica(pos, enemy.pos, Ballistica.PROJECTILE);
                    genoiseDst = traceGenoise.collisionPos;
                }
                return onGenoise || super.canAttack( enemy );
            case 2:
                // rush and magnum wedding
                if (timerRush >= COOLDOWN_RUSH) {
                    timerRush = COOLDOWN_RUSH;
                    canRush = true;
                    traceMagnum = null;
                } else {
                    traceMagnum = new Ballistica(pos, enemy.pos, Ballistica.PROJECTILE);
                }
                return onRush || canRush || ( timerRush <= 1 && findChar(traceMagnum.collisionPos) == enemy && (Dungeon.level.distance(pos, enemy.pos) <= RANGE_MAGNUM) );
        }

    }

    @Override
    protected boolean doAttack( Char enemy ) {

	    switch (phase) {
            case 0: default:
            case 1:
                if (enemy == null) {
                    onGenoise = false;
                    return true;
                }
                spend( attackDelay() );
                if ( genoiseDst > 0 ) {
                    if (Dungeon.level.adjacent(pos, genoiseDst)) {
                        Blast();
                        return true;
                    }
                    if ( Dungeon.level.heroFOV[pos] || Dungeon.level.heroFOV[genoiseDst] ) {
                        ((ElpheltSprite)sprite).genoise( genoiseDst );
                    } else {
                        fireGenoise( genoiseDst );
                    }
                    if (genoiseDst == Dungeon.hero.pos) {
                        Dungeon.hero.interrupt();
                    }
                    return true;
                } else {
                    return super.doAttack( enemy );
                }
            case 2:
                if (enemy == null)
                {
                    spend( TICK );
                    return true;
                }
                if (canRush) {
                    if (onRush) {
                        // 브라이들 러시 발동
                        spend( bridleExpressDelay() );
                        bridleExpress();
                        return true;
                    } else {
                        // 경고 궤적 표시
                        onRush = true;
                        spend( warnDelay() );
                        warnExpress();
                        return true;
                    }
                } else {
                    spend( magnumDelay() );
                    if ( Dungeon.level.heroFOV[pos] || Dungeon.level.heroFOV[traceMagnum.collisionPos] ) {
                        sprite.zap( traceMagnum.collisionPos );
                    } else {
                        magnumWedding();
                    }
                    sprite.parent.add(new Beam.DeathRay(sprite.center(), findChar(traceMagnum.collisionPos).sprite.center()));
                    return true;
                }

        }
    }

    @Override
    public void damage(int dmg, Object src) {
        int newHP = HP - dmg;
        int newDmg = dmg;

        if ( HP > (HT/2) && newHP <= (HT/2)) {
            newDmg = HP - HT/2;
            changePhase();
        }

        super.damage( newDmg, src );
    }

    public void fireGenoise( int pos ) {
        boolean terrainAffected = false;

        for (int c : traceGenoise.subPath(1, traceGenoise.dist)) {
            if (Dungeon.level.flamable[c]) {
                Dungeon.level.destroy(c);
                GameScene.updateMap(c);
                terrainAffected = true;
            }
        }

        for (int n : PathFinder.NEIGHBOURS8) {
            int c = pos + n;
            if ( c >= 0 && Blob.volumeAt( c, GooWarn.class ) == 0 ) {
                GameScene.add( Blob.seed( c, Math.round( 1 + TIME_TO_EXPLODE ), GooWarn.class) );
            }
        }

        if (pos >= 0 && Blob.volumeAt( pos, GenoiseWarn.class ) == 0) {
            GameScene.add( Blob.seed( pos, Math.round( 1 + TIME_TO_EXPLODE ), GenoiseWarn.class) );
        }

        Genoise newGenoise = new Genoise( pos );
        addDelayed(newGenoise, TIME_TO_EXPLODE);

        Genoises.add( newGenoise );

        curGenoiseStack = Math.max(curGenoiseStack-1, 0);

        if (terrainAffected) {
            Dungeon.observe();
        }
    }

    public void changePhase() {
        if (phase != 2) {
            phase = 2;
            //TODO 보스레벨과 통신
            sprite.idle();
            yell("2페이즈!");
        }
    }

    public void Blast() {
        //throws other chars around the center.
        for (int i  : PathFinder.NEIGHBOURS8){
            Char ch = Actor.findChar(pos + i);

            if (ch != null){
                if (ch.isAlive()) {
                    final Ballistica trajectory = new Ballistica(ch.pos, ch.pos + i, Ballistica.MAGIC_BOLT);

                    final Char fch = ch;

                    int dist = Math.min(trajectory.dist, POWER_OF_BLAST);
                    //충돌 경로에 다른 캐릭터 있을 때 1칸 감소
                    if (Actor.findChar(trajectory.path.get(dist)) != null){
                        dist = Math.max(dist-1, 0);
                    }

                    // 적을 못날리면 자신이 1칸 뒤로 후퇴, 움직일 공간이 없으면 그냥 제누와즈 발사
                    if (dist == 0 || ch.properties().contains(Char.Property.IMMOVABLE)) {
                        int oldPos = pos;
                        if (getFurther( ch.pos )) {
                            spend( 1 / speed() );
                            moveSprite( oldPos, pos );
                        } else {
                            if ( Dungeon.level.heroFOV[pos] || Dungeon.level.heroFOV[ch.pos] ) {
                                ((ElpheltSprite)sprite).genoise( ch.pos );
                            } else {
                                fireGenoise( ch.pos );
                            }
                        }
                        continue;
                    }

                    final int newPos = trajectory.path.get(dist);

                    if (newPos == ch.pos) {
                        continue;
                    }

                    final int initialpos = ch.pos;

                    Actor.addDelayed(new Pushing(ch, ch.pos, newPos, new Callback() {
                        public void call() {
                            if (initialpos != fch.pos) {
                                //something cased movement before pushing resolved, cancel to be safe.
                                fch.sprite.place(fch.pos);
                                return;
                            }
                            fch.pos = newPos;
                            if (fch.pos == trajectory.collisionPos) {
                                Paralysis.prolong(fch, Paralysis.class, 2.5f);
                            }
                            Dungeon.level.press(fch.pos, fch, true);
                            if (fch == hero){
                                Dungeon.observe();
                            }
                        }
                    }), -1);
                }
                ch.next();
                if (ch == Dungeon.hero) {
                    Dungeon.hero.interrupt();
                }
            }
        }
        curGenoiseStack = Math.max(curGenoiseStack-1, 0);
        next();
    }

    private List<Integer> bridlePath;

	private void warnExpress() {

        traceRush = new Ballistica( pos, target, Ballistica.STOP_CHARS | Ballistica.STOP_TERRAIN );
        if (traceRush.dist > 1) {
            bridlePath = traceRush.subPath(1, traceRush.dist);
        } else {
            // 캐릭터와 엘펠트가 붙어있는 경우
            // 일단 팅겨내기 -> 아니면 벽까지 돌진하게 만들고 그냥 죽여버리기?

            canRush = false;
            onRush = false;

            return;
        }

	    for (int c : bridlePath) {
            if ( Blob.volumeAt( c, GenoiseWarn.class ) == 0 ) {
                if (Blob.volumeAt(c, GenoiseWarn.class) == 0) {
                    GameScene.add(Blob.seed(c, 2, GenoiseWarn.class));
                }
            }
        }

        dstRush = traceRush.collisionPos;
        ((ElpheltSprite)sprite).charge(dstRush);

        next();
    }

    private void bridleExpress() {

	    if (!onRush || dstRush < 0) {
	        next();
            return;
        }

        if (traceRush == null) {
            if ( enemy != null && enemy.isAlive() && fieldOfView[enemy.pos] && enemy.invisible <= 0 ) {
                traceRush = new Ballistica( pos, dstRush, Ballistica.STOP_CHARS | Ballistica.STOP_TERRAIN );
            } else {
                traceRush = new Ballistica( pos, Dungeon.level.randomDestination(), Ballistica.STOP_CHARS | Ballistica.STOP_TERRAIN );
            }
            bridlePath = traceRush.subPath(1, traceRush.dist);
        }

	    int procPos = pos;

	    for (int c : bridlePath) {
	        final Char ch = findChar(c);

	        if (ch != null) {
	            // 튕겨나갈 경로 계산
	            final Ballistica traceChar = new Ballistica( c, traceRush.path.get(traceRush.dist+1), Ballistica.STOP_CHARS | Ballistica.STOP_TERRAIN );

                Char collideChar = findChar(traceChar.collisionPos);

                // 0. 아무것도 안충돌
                // 1. 지형에 충돌
                // 2. 다른 캐릭터에 충돌
                int collideTag = 0;

                if ( traceChar.dist <= POWER_OF_BLAST ) {
                    if ( collideChar != null && Dungeon.level.distance(ch.pos, collideChar.pos) == traceChar.dist ) {
                        collideTag = 2;
                    } else {
                        collideTag = 1;
                    }
                }

                // 벽에 부딪힌 경우는 충돌 위치까지
                // 캐릭터에 부딪힌 경우 날아갈 위치는 해당 캐릭터의 위치-1까지
                int dist = (collideTag < 2) ? traceChar.dist : Math.max(traceChar.dist - 1, 0);

	            final int newPos = traceChar.path.get( dist );

	            final int initialPos = ch.pos;

	            // 이동경로에서 부딫힌 적을 튕겨냄
	            Actor.addDelayed( new Pushing(ch, ch.pos, newPos, new Callback() {
                    @Override
                    public void call() {

                        if (initialPos != ch.pos) {
                            ch.sprite.place(ch.pos);
                            return;
                        }

                        ch.pos = newPos;

                        // 2테마 보스에 맞는 데미지
                        ch.damage( Random.NormalIntRange(30,50) - ch.drRoll(), Elphelt.this );

                        if (traceChar.collisionPos == newPos) {
                            Paralysis.prolong(ch, Paralysis.class, 2f);
                        }

                        Dungeon.level.press(ch.pos, ch, true);
                        if (ch == hero) {
                            Dungeon.observe();
                        }
                    }
                }), 0f );

	            break;
            }

            procPos = c;
        }

        final int finalPos = procPos;
        // 엘펠트 돌진
        Actor.addDelayed( new Pushing(Elphelt.this, pos, finalPos, new Callback() {
            @Override
            public void call() {
                if (Dungeon.level.map[pos] == Terrain.OPEN_DOOR) {
                    Door.leave( pos );
                }
                pos = finalPos;
                if (Dungeon.level.map[pos] == Terrain.DOOR) {
                    Door.enter( pos );
                }
                canRush = false;
                onRush = false;
                timerRush = 0;
                traceRush = null;
                dstRush = -1;
                bridlePath.clear();

                yell( Elphelt.this.enemy != null ? "브라이들 익스프레스!" : "어디있나요, 달링!");
            }
        }), -1f);

    }


    public void magnumWedding() {

        for (int c : traceMagnum.subPath(0, traceMagnum.dist))
            CellEmitter.center(c).burst( BloodParticle.BURST, 1 );

	    int damage = Random.NormalIntRange(12,20);

	    Char ch = findChar(traceMagnum.collisionPos);
	    if (ch != null) {
            ch.damage(damage - ch.drRoll(), Elphelt.this );
            ch.sprite.centerEmitter().start( Speck.factory( Speck.HEART ), 0.2f, 5 );
            //  3턴 지속 매혹 부여
            if (Random.IntRange(0,99) >= 40) {
                Buff.affect( ch, Charm.class, magnumDelay() );
            }
        }
    }

    private static final String PHASE           = "phase";
    private static final String CURGENOISE      = "curGenoise";
    private static final String ONGENOISE       = "onGenoise";
    private static final String GENOISEPOS      = "GenoisePos";
    private static final String GENOISETIME     = "GenoiseTime";
    private static final String NUMGENOISE      = "numGenoise";

    private static final String CANBLAST        = "canBlast";

    private static final String CANBRIDLE       = "canBridle";
    private static final String ONBRIDLE        = "onBridle";
    private static final String BRIDLETIME      = "BridleTime";
    private static final String BRIDLEPATH      = "BridlePath";
    private static final String BRIDLEDST       = "BridleDst";


    private int NumOfGenoise = 0;

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put( PHASE, phase );
        bundle.put( CURGENOISE, curGenoiseStack);
        bundle.put( ONGENOISE, onGenoise);

        bundle.put( CANBLAST, canBlast );

        NumOfGenoise = Genoises.size();
        bundle.put( NUMGENOISE, NumOfGenoise );

        bundle.put( PHASE, phase );

        Iterator<Genoise> it = Genoises.iterator();

        for (int i=0; i<NumOfGenoise; ++i) {
            Genoise g = it.next();
            bundle.put( GENOISEPOS + String.valueOf(i), g.getTarget() );
            bundle.put( GENOISETIME + String.valueOf(i), g.cooldown() );
        }

        bundle.put( BRIDLETIME, timerRush );
        bundle.put( CANBRIDLE, canRush );
        bundle.put( ONBRIDLE, onRush );
        if (onRush) {
            bundle.put( BRIDLEDST, dstRush );
            int[] tmp = new int[bridlePath.size()];
            for (int i = 0; i < bridlePath.size(); ++i) {
                tmp[i] = bridlePath.get(i);
            }
            bundle.put( BRIDLEPATH, tmp );
        }
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        phase = bundle.getInt(PHASE);
        onGenoise = bundle.getBoolean( ONGENOISE );
        curGenoiseStack = bundle.getInt( CURGENOISE );

        canBlast = bundle.getBoolean( CANBLAST );

        NumOfGenoise = bundle.getInt( NUMGENOISE );

        phase = bundle.getInt( PHASE );

        for (int i=0; i< NumOfGenoise; ++i) {
            Genoise g = new Genoise( bundle.getInt(GENOISEPOS + String.valueOf(i) ) );
            addDelayed( g , bundle.getFloat( GENOISETIME + String.valueOf(i) ) );
            Genoises.add(g);
        }

        timerRush = bundle.getInt( BRIDLETIME );
        canRush = bundle.getBoolean( CANBRIDLE );
        onRush = bundle.getBoolean( ONBRIDLE );

        if (onRush) {
            dstRush = bundle.getInt( BRIDLEDST );
            int[] tmp = bundle.getIntArray( BRIDLEPATH );
            bridlePath = new ArrayList<Integer>();
            for (int c : tmp) {
                bridlePath.add(c);
            }
        }
    }

    {
        resistances.add( WandOfDisintegration.class );
        resistances.add( Grim.class );
        resistances.add( Vampiric.class );
    }

    {
        immunities.add( Charm.class );
        immunities.add( Terror.class );
    }


    private class Hunting extends Mob.Hunting{
        @Override
        public boolean act(boolean enemyInFOV, boolean justAlerted) {

            enemySeen = enemyInFOV;
            switch (phase) {
                case 0: default:
                    state = SLEEPING;
                    spend( TICK );
                    return true;
                case 1:
                    if (enemy == null) {
                        spend( TICK );
                        state = WANDERING;
                        target = Dungeon.level.randomDestination();
                        return true;
                    }

                    if (canAttack( enemy )) {
                        if (enemyInFOV && enemy != null)
                        {
                            target = enemy.pos;
                        }
                        return doAttack( enemy );
                    } else {
                        // 제누와즈 중이 아니거나 근접공격 불가능한 경우
                        int oldPos = pos;
                        if (target != -1 && getCloser( target )) {
                            spend( 1 / speed() );
                            return moveSprite( oldPos, pos );
                        } else {
                            spend( TICK );
                            sprite.showLost();
                            if (!enemyInFOV) {
                                state = WANDERING;
                                target = Dungeon.level.randomDestination();
                            }
                            return true;
                        }
                    }
                case 2:
                    if (canAttack(enemy)) {
                        if (enemyInFOV) {
                            target = enemy.pos;
                        } else {
                            target = Dungeon.level.randomDestination();
                        }
                        doAttack( enemy );
                        return true;
                    } else {
                        int oldPos = pos;
                        if (target != -1 && getCloser( target )) {
                            spend( 1 / speed() );
                            return moveSprite( oldPos, pos );
                        } else {
                            spend( TICK );
                            sprite.showLost();
                            if (!enemyInFOV) {
                                state = WANDERING;
                                target = Dungeon.level.randomDestination();
                            }
                            return true;
                        }
                    }
            }
        }
    }


    private class Genoise extends Actor {

        private int target = -1;

        Genoise(int cell) {
            target = cell;
        }

        public final int getTarget() { return target; }

        @Override
        protected boolean act() {

            if (!Genoises.contains(Elphelt.Genoise.this)) {
                return false;
            }

            Genoises.remove(Elphelt.Genoise.this);

            Sample.INSTANCE.play( Assets.SND_BLAST );

            if (target >= 0 && Dungeon.level.heroFOV[target]) {
                CellEmitter.center( target ).burst( BlastParticle.FACTORY, 30 );
            }

            boolean terrainAffected = false;
            for (int n : PathFinder.NEIGHBOURS9) {
                int c = target + n;
                if (c >= 0 && c < Dungeon.level.length()) {
                    if (Dungeon.level.heroFOV[c]) {
                        CellEmitter.get( c ).burst( SmokeParticle.FACTORY, 4 );
                    }

                    if (Dungeon.level.flamable[c]) {
                        Dungeon.level.destroy( c );
                        GameScene.updateMap( c );
                        terrainAffected = true;
                    }

                    //destroys items / triggers bombs caught in the blast.
                    Heap heap = Dungeon.level.heaps.get( c );
                    if(heap != null)
                        heap.explode();

                    Char ch = Actor.findChar( c );
                    if (ch != null) {
                        //those not at the center of the blast take damage less consistently.
                        int minDamage = c == target ? 100 : 50;
                        int maxDamage = c == target ? 120 : 60;

                        int dmg = Random.NormalIntRange( minDamage, maxDamage ) - ch.drRoll();
                        if (dmg > 0) {
                            if (ch instanceof Elphelt) {
                                dmg /= 10;
                            }
                            ch.damage( dmg , this );
                        }

                        if (ch == hero && !ch.isAlive()) {
                            Dungeon.fail( Elphelt.this.getClass() );
                            GLog.n( Messages.get( Elphelt.this, "genoise_kill") );
                        }
                    }
                }
            }

            if (terrainAffected) {
                Dungeon.observe();
            }

            remove(Elphelt.Genoise.this);
            return true;
        }
    }
}
