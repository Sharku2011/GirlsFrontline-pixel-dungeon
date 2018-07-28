package com.gfpixel.gfpixeldungeon.actors.mobs;

import com.gfpixel.gfpixeldungeon.Assets;
import com.gfpixel.gfpixeldungeon.DialogInfo;
import com.gfpixel.gfpixeldungeon.Dungeon;
import com.gfpixel.gfpixeldungeon.actors.Actor;
import com.gfpixel.gfpixeldungeon.actors.Char;
import com.gfpixel.gfpixeldungeon.actors.blobs.Blob;
import com.gfpixel.gfpixeldungeon.actors.blobs.GenoiseWarn;
import com.gfpixel.gfpixeldungeon.actors.blobs.GooWarn;
import com.gfpixel.gfpixeldungeon.actors.buffs.Paralysis;
import com.gfpixel.gfpixeldungeon.actors.buffs.Terror;
import com.gfpixel.gfpixeldungeon.effects.CellEmitter;
import com.gfpixel.gfpixeldungeon.effects.Pushing;
import com.gfpixel.gfpixeldungeon.effects.particles.BlastParticle;
import com.gfpixel.gfpixeldungeon.effects.particles.PurpleParticle;
import com.gfpixel.gfpixeldungeon.effects.particles.SmokeParticle;
import com.gfpixel.gfpixeldungeon.items.Heap;
import com.gfpixel.gfpixeldungeon.items.wands.WandOfDisintegration;
import com.gfpixel.gfpixeldungeon.items.weapon.enchantments.Grim;
import com.gfpixel.gfpixeldungeon.items.weapon.enchantments.Vampiric;
import com.gfpixel.gfpixeldungeon.mechanics.Ballistica;
import com.gfpixel.gfpixeldungeon.messages.Messages;
import com.gfpixel.gfpixeldungeon.scenes.GameScene;
import com.gfpixel.gfpixeldungeon.sprites.CharSprite;
import com.gfpixel.gfpixeldungeon.sprites.ElpheltSprite;
import com.gfpixel.gfpixeldungeon.ui.BossHealthBar;
import com.gfpixel.gfpixeldungeon.utils.GLog;
import com.gfpixel.gfpixeldungeon.windows.WndDialog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class Elphelt extends Mob {

    private static final float TIME_TO_EXPLODE = 2f;
    private static final int POWER_OF_BLAST = 6;
    private static final int REGEN_OF_GENOISE = 2;
    private static final int RANGE_MAGNUM = 2;

    {
        spriteClass = ElpheltSprite.class;

        HP = HT = 600;
        EXP = 100;
        defenseSkill = 35;
        baseSpeed = 1f;
        maxLvl = 20;

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

    protected float warnDelay() { return 1f; }
    protected float tackleDelay() { return  2f; }


    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 10);
    }

    private Ballistica traceGenoise;

    private Ballistica traceRush;

    public boolean onGenoise = false;
    private int maxGenoiseStack = 5;
    private int curGenoiseStack = maxGenoiseStack;

    private int timerRush = 3;
    private final int COOLDOWN_RUSH = 3;
    private final int WARMUP_RUSH = 1;

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
                if (canBlast) {
                    Blast();
                    canBlast = false;
                }

                if (!onGenoise && curGenoiseStack < maxGenoiseStack)
                {
                    // 제누와즈 중이 아니고 스택이 모자란 경우 스택 추가
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

        switch (phase) {
            case 0:
            default:
            case 1:
                // phase 1. genoise and stalking

                if (curGenoiseStack == maxGenoiseStack) {
                    onGenoise = true;
                }

                if (Dungeon.level.adjacent(pos, enemy.pos)) {
                    // 근접
                    if (onGenoise) {
                        // 제누와즈 시전중
                        canBlast = true;
                        return true;
                    } else {
                        // 시전중 아닐 때 - 무조건 true라고 봐야함
                        return super.canAttack(enemy);
                    }
                } else {
                    // 비근접
                    return onGenoise && (new Ballistica(pos, enemy.pos, Ballistica.PROJECTILE).collisionPos == enemy.pos);
                }

            case 2:
                // rush and magnum wedding
                if (timerRush >= COOLDOWN_RUSH) {
                    timerRush = COOLDOWN_RUSH;
                    canRush = true;
                }
                return canRush; //|| (Dungeon.level.distance(pos, enemy.pos) <= RANGE_MAGNUM);
        }

    }

    @Override
    protected boolean doAttack( Char enemy ) {

	    switch (phase) {
            case 0: default:
            case 1:

                spend( attackDelay() );

                traceGenoise = new Ballistica(pos, enemy.pos, Ballistica.STOP_TARGET | Ballistica.STOP_TERRAIN);

                if ( onGenoise && curGenoiseStack > 0) {

                    // 엘펠트가 플레이어의 시야 안에 있으면 애니메이션을 재생. true/false 값 리턴은 왜 저렇게 되는지 모르겠음
                    if (Dungeon.level.heroFOV[pos] || Dungeon.level.heroFOV[traceGenoise.collisionPos] ) {
                        sprite.zap( traceGenoise.collisionPos );
                        return false;
                    } else {
                        fireGenoise();
                        return true;
                    }
                } else {
                    return super.doAttack( enemy );
                }

            case 2:
                if (canRush) {
                    if (onRush) {
                        spend( tackleDelay() );
                        return Tackle();
                    } else {
                        onRush = true;
                        spend( warnDelay() );
                        warnTackle();
                        return false;
                    }
                } else {
                    GLog.i("뭔가가 잘못되었음");
                    super.doAttack( enemy );
                }

                return true;
        }

    }

    @Override
    public void damage(int dmg, Object src) {
        int newHP = HP - dmg;
        int newDmg = dmg;

        switch (phase) {
            case 0: default:
            case 1:
                if ( HP > (HT/2) && newHP <= (HT/2)) {
                    newDmg = HP - HT/2;
                    changePhase();
                }
                break;
            case 2:
                if ( HP > (HT/3) && newHP <= (HT/3)) {
                    newDmg = HP - HT/3;
                }
                break;
        }
        super.damage( newDmg, src );
    }

    public void fireGenoise() {

        if (curGenoiseStack <= 0) {
            // 혹시라도 제누와즈 스택이 만땅이 아닐 때 호출되면 풀차지 시켜주고 초기화
            curGenoiseStack = maxGenoiseStack;
            traceGenoise = null;
            GLog.i("충전");
            return;
        }

        boolean terrainAffected = false;

        for (int pos : traceGenoise.subPath(1, traceGenoise.dist)) {

            if (Dungeon.level.flamable[pos]) {
                Dungeon.level.destroy( pos );
                GameScene.updateMap( pos );
                terrainAffected = true;
            }

            Char ch = Actor.findChar( pos );
            if (ch == null) {
                continue;
            }

            for (int n : PathFinder.NEIGHBOURS8) {
                int c = pos + n;
                if ( Blob.volumeAt( c, GooWarn.class ) == 0 ) {
                    GameScene.add( Blob.seed( c, Math.round( 1 + TIME_TO_EXPLODE ), GooWarn.class) );
                }
            }

            if (Blob.volumeAt( pos, GenoiseWarn.class ) == 0) {
                GameScene.add( Blob.seed( pos, Math.round( 1 + TIME_TO_EXPLODE ), GenoiseWarn.class) );
            }

            Genoise newGenoise = new Genoise(pos);
            addDelayed(newGenoise, TIME_TO_EXPLODE);

            Genoises.add( newGenoise );

            curGenoiseStack = Math.max(curGenoiseStack-1, 0);
            if (curGenoiseStack <= 0) {
                onGenoise = false;
            }

            if ( hit( this, ch, true ) ) {

                if (Dungeon.level.heroFOV[pos]) {
                    ch.sprite.flash();
                    CellEmitter.center( pos ).burst( PurpleParticle.BURST, Random.IntRange( 1, 2 ) );
                }

            } else {
                ch.sprite.showStatus( CharSprite.NEUTRAL,  ch.defenseVerb() );
            }
        }

        if (terrainAffected) {
            Dungeon.observe();
        }

        traceGenoise = null;
    }

    public void changePhase() {
        if (phase != 2) {
            phase = 2;
            //TODO 보스레벨과 통신

            yell("2페이즈!");
        }
    }

    public void Blast() {
        //throws other chars around the center.
        for (int i  : PathFinder.NEIGHBOURS8){
            Char ch = Actor.findChar(pos + i);

            if (ch != null){

                if (ch.isAlive()) {
                    Ballistica trajectory = new Ballistica(ch.pos, ch.pos + i, Ballistica.MAGIC_BOLT);
                    throwChar(ch, trajectory, POWER_OF_BLAST);
                }
            }
        }

    }


	public void warnTackle() {

        List<Integer> pathTackle;
        if ( enemy != null && enemy.isAlive() && fieldOfView[enemy.pos] && enemy.invisible <= 0 ) {
            traceRush = new Ballistica( pos, enemy.pos, Ballistica.STOP_CHARS | Ballistica.STOP_TERRAIN );
            if (traceRush.dist > 2) {
                pathTackle = traceRush.subPath(1, traceRush.dist);
            } else {
                // 캐릭터와 엘펠트가 붙어있는 경우
                // 일단 팅겨내기 -> 아니면 벽까지 돌진하게 만들고 그냥 죽여버리기?
                Blast();
                next();
                return;
            }
        } else {
	        traceRush = new Ballistica( pos, Dungeon.level.randomDestination(), Ballistica.STOP_CHARS | Ballistica.STOP_TERRAIN );

	        pathTackle = traceRush.subPath(1, traceRush.dist);
        }


	    for (int c : pathTackle) {
            if ( Blob.volumeAt( c, GenoiseWarn.class ) == 0 ) {
                if (Blob.volumeAt(c, GenoiseWarn.class) == 0) {
                    GameScene.add(Blob.seed(c, 2, GenoiseWarn.class));
                }
            }
            dstRush = c;
        }
        next();

        return;
    }

    public boolean Tackle() {

	    if (!onRush || dstRush < 0) {
	        next();
            return false;
        }

	    if (enemy != null && enemy.isAlive() && fieldOfView[enemy.pos] && enemy.invisible <= 0) {
	        final Ballistica traceChar = new Ballistica( pos, dstRush, Ballistica.STOP_CHARS | Ballistica.STOP_TARGET );

            if ( traceChar.collisionPos != null) {
                List<Integer> tacklePath = traceChar.subPath(1, traceChar.dist);

                int prevCell = pos;

                for(int c : tacklePath) {
                    Char ch = findChar(c);
                    if (ch != null) {
                        final Ballistica trajectory = new Ballistica( prevCell, c, Ballistica.STOP_TERRAIN);
                        final int newPos = trajectory.path.get( Math.min( trajectory.dist, POWER_OF_BLAST/2) );
                        final Char chr = ch;

                        Actor.addDelayed( new Pushing(chr, chr.pos, newPos, new Callback() {
                            @Override
                            public void call() {

                                if (chr.pos == newPos) {
                                    Paralysis.prolong(enemy, Paralysis.class, 2f);
                                }

                                // pushing으로 밀어준 후에 반드시 캐릭터의 위치를 덮어씌워줘야함
                                chr.pos = newPos;

                                Dungeon.level.press(chr.pos, chr, true);
                                if (chr == Dungeon.hero){
                                    Dungeon.observe();
                                }
                            }
                        }), -1f);
                    }
                }
                Actor.addDelayed(new Pushing(this, pos, dstRush, new Callback() {
                    @Override
                    public void call() {
                        canRush = false;
                        onRush = false;
                        pos = dstRush;
                        timerRush = 0;
                        traceRush = null;
                        dstRush = -1;
                        next();
                    }
                }), -1f);
            }
        }

        return true;
    }


    public static void throwChar(final Char ch, final Ballistica trajectory, int power){
        int dist = Math.min(trajectory.dist, power);

        if (ch.properties().contains(Char.Property.BOSS))
            dist = 0;

        if (dist == 0 || ch.properties().contains(Char.Property.IMMOVABLE)) return;

        if (Actor.findChar(trajectory.path.get(dist)) != null){
            dist--;
        }

        final int newPos = trajectory.path.get(dist);

        if (newPos == ch.pos) return;

        final int initialpos = ch.pos;

        Actor.addDelayed(new Pushing(ch, ch.pos, newPos, new Callback() {
            public void call() {
                if (initialpos != ch.pos) {
                    //something cased movement before pushing resolved, cancel to be safe.
                    ch.sprite.place(ch.pos);
                    return;
                }
                ch.pos = newPos;
                if (ch.pos == trajectory.collisionPos) {
                    Paralysis.prolong(ch, Paralysis.class, 3f);
                }
                Dungeon.level.press(ch.pos, ch, true);
                if (ch == Dungeon.hero){
                    Dungeon.observe();
                }
            }
        }), -1);
    }

    private static final String PHASE           = "phase";
    private static final String CUR_GENOISE     = "curGenoise";
    private static final String ONGENOISE       = "onGenoise";
    private static final String GENOISEPOS      = "GenoisePos";
    private static final String GENOISETIME     = "GenoiseTime";
    private static final String NUMGENOISE      = "numGenoise";

    private int NumOfGenoise = 0;

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put( PHASE, phase );
        bundle.put( CUR_GENOISE, curGenoiseStack);
        bundle.put( ONGENOISE, onGenoise);

        NumOfGenoise = Genoises.size();
        bundle.put( NUMGENOISE, NumOfGenoise );

        bundle.put( PHASE, phase );

        Iterator<Genoise> it = Genoises.iterator();

        for (int i=0; i<NumOfGenoise; ++i) {
            Genoise g = it.next();
            bundle.put( GENOISEPOS + String.valueOf(i), g.getTarget() );
            bundle.put( GENOISETIME + String.valueOf(i), g.cooldown() );
        }
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        phase = bundle.getInt(PHASE);
        onGenoise = bundle.getBoolean( ONGENOISE );
        curGenoiseStack = bundle.getInt( CUR_GENOISE );

        NumOfGenoise = bundle.getInt( NUMGENOISE );

        phase = bundle.getInt( PHASE );

        for (int i=0; i< NumOfGenoise; ++i) {
            Genoise g = new Genoise( bundle.getInt(GENOISEPOS + String.valueOf(i) ) );
            addDelayed( g , bundle.getFloat( GENOISETIME + String.valueOf(i) ) );
            Genoises.add(g);
        }
    }

    {
        resistances.add( WandOfDisintegration.class );
        resistances.add( Grim.class );
        resistances.add( Vampiric.class );
    }

    {
        immunities.add( Terror.class );
    }

    /*
    private class Hunting extends Mob.Hunting{
        @Override
        public boolean act(boolean enemyInFOV, boolean justAlerted) {
            //even if enemy isn't seen, attack them if the beam is charged

            //if (beamCharged && enemy != null && canAttack(enemy)) {
            //    enemySeen = enemyInFOV;
            //    return doAttack(enemy);
            //}

            return super.act(enemyInFOV, justAlerted);
        }
    }
    */

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
                            ch.damage( dmg , this );
                        }

                        if (ch == Dungeon.hero && !ch.isAlive()) {
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
