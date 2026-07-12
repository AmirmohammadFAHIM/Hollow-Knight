package com.mygame.game.view.gamePanes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.mygame.game.models.entities.EntityRenderer;
import com.mygame.game.models.entities.boss.FalseKnight;

import java.util.ArrayList;
import java.util.Arrays;

public class FalseKnightRenderer extends EntityRenderer {
    FalseKnight boss;
    public FalseKnightRenderer(FalseKnight boss) {
        this.boss = boss;
    }
    enum AnimationState {
        IDLE(null ,new TextureAtlas("enemies/False Knight/Idle.atlas") ,"Idle",true)
        ,ATTACK_RECOVER(IDLE,new TextureAtlas("enemies/False Knight/Attack Recover.atlas"),"Attack Recover",false)
        ,ATTACK(ATTACK_RECOVER,new TextureAtlas("enemies/False Knight/Attack.atlas"),"Attack",false)
        ,ATTACK_ANTIC(ATTACK,new TextureAtlas("enemies/False Knight/Attack Antic.atlas"),"Attack Antic",false)
        ,LAND(IDLE,new TextureAtlas("enemies/False Knight/Land.atlas"),"Land",false)
        ,JUMP(null,new TextureAtlas("enemies/False Knight/Jump.atlas"),"Jump",false)
        ,BODY(null,new TextureAtlas("enemies/False Knight/Body.atlas"),"Body",true)
        ,STUN_RECOVER(IDLE,new TextureAtlas("enemies/False Knight/StunRecover.atlas"),"Stun Recover",false)
        ,DEATH_LAND(BODY,new TextureAtlas("enemies/False Knight/DeathLand.atlas"),"DeathLand",false)
        ,DEATH_HIT(BODY,new TextureAtlas("enemies/False Knight/DeathHit.atlas"),"DeathHit",false){
            @Override
            void goNext(float stateTime, FalseKnightRenderer renderer) {
                if(next != null && renderer.animS.animation.isAnimationFinished(stateTime)){
                    renderer.setAnimS(next);
                    renderer.boss.setHurt(false);
                }

            }
        }
        ,DEATH_AIR(DEATH_LAND,new TextureAtlas("enemies/False Knight/DeathFall.atlas"),"DeathFall",false),
        RUN(null,new TextureAtlas("enemies/False Knight/Run.atlas"),"Run",true),
        TURN(IDLE,new TextureAtlas("enemies/False Knight/Turn.atlas"),"Turn",false){

        },
        JUMP_ATTACK(IDLE , new TextureAtlas("enemies/False Knight/JumpAttack.atlas"),
            "Jump Attack",false, 0.116f);

    AnimationState next;
    Animation<TextureAtlas.AtlasRegion> animation;
    AnimationState(AnimationState next , TextureAtlas spriteSheet,String regionName,boolean loop) {
        this.next = next;
        this.animation = new Animation<>(0.09f , spriteSheet.findRegions(regionName),
            loop ? Animation.PlayMode.LOOP : Animation.PlayMode.NORMAL);
    }
        AnimationState(AnimationState next , TextureAtlas spriteSheet,String regionName,boolean loop,
                       float frameDuration) {
            this.next = next;
            this.animation = new Animation<>(frameDuration , spriteSheet.getRegions(),
                loop ? Animation.PlayMode.LOOP : Animation.PlayMode.NORMAL);
        }
    void goNext(float stateTime , FalseKnightRenderer renderer){
            if(next != null && renderer.animS.animation.isAnimationFinished(stateTime)){
                renderer.setAnimS(next);
                System.out.println(next);
                //renderer.currAnime = next.animation;
            }
        }
    }

    FalseKnightRenderer.AnimationState animS = AnimationState.IDLE;
    Animation<TextureAtlas.AtlasRegion> idle;


    private void attackAnimation(){
        FalseKnight.Action action = boss.action;
        if(animS == AnimationState.IDLE && action == FalseKnight.Action.KNOCK){
            setAnimS(AnimationState.ATTACK_ANTIC);
        }
    }

    private void jump_attackAnimation(){
        if(boss.action == FalseKnight.Action.JUMP_KNOCK && animS == AnimationState.IDLE){
            setAnimS(AnimationState.JUMP_ATTACK);
        }
    }

    private void landAndJumpAnimation(){
        FalseKnight.Action action = boss.action;
        if (animS == AnimationState.IDLE && (action == FalseKnight.Action.AGGRESSIVE_JUMP ||
            action == FalseKnight.Action.DEFENSIVE_JUMP)) {
            setAnimS(AnimationState.JUMP);
        }
        else if(animS == AnimationState.JUMP && action == FalseKnight.Action.IDLE){
            setAnimS(AnimationState.LAND);
        }
    }

    ArrayList<AnimationState> deaths = new ArrayList<>(Arrays.asList(AnimationState.DEATH_AIR,
        AnimationState.DEATH_LAND, AnimationState.DEATH_HIT,AnimationState.BODY));

    private void DeathAnimation(){
        FalseKnight.Action action = boss.action;
        if(!deaths.contains(animS) && action == FalseKnight.Action.DEAD){
            setAnimS(AnimationState.DEATH_AIR);
        }
        else if(animS == AnimationState.BODY && boss.isHurt()){
            setAnimS(AnimationState.DEATH_HIT);
            boss.setHurt(false);
        }
        else if(animS == AnimationState.BODY && action == FalseKnight.Action.IDLE){
            setAnimS(AnimationState.STUN_RECOVER);
        }

    }

    private void Run(){
        if(animS == AnimationState.IDLE && boss.action == FalseKnight.Action.RUN){
            setAnimS(AnimationState.RUN);
        }
        else if(animS == AnimationState.RUN && boss.action == FalseKnight.Action.IDLE){
            setAnimS(AnimationState.IDLE);
        }

    }

    private void Turn(){
        if(boss.turnPalse){
            boss.turnPalse = false;
            setAnimS(AnimationState.TURN);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime();
        DeathAnimation();
        landAndJumpAnimation();
        attackAnimation();
        jump_attackAnimation();
        Run();
        Turn();
        animS.goNext(stateTime,this);
        TextureAtlas.AtlasRegion frame = this.animS.animation.getKeyFrame(stateTime);
        if(frame.isFlipX() && !boss.isRight()) frame.flip(true , false);
        else if(!frame.isFlipX() && boss.isRight()) frame.flip(true , false);
        batch.draw(frame , boss.getX() - 400, boss.getY() - 35);

        /// rendering the animation and flipping it according to boss' direction
    }

    float stateTime = 0;
    public void setAnimS(AnimationState animS) {
        stateTime = 0;
        this.animS = animS;
    }
}
