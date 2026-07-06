package com.mygame.game.view.healthBar;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Mask extends Actor {
    public enum State{
        SHINE , FILL , BREAK
    }
    private State state = State.SHINE ;
    public boolean full = true ;
    private int index;
    private Animation<TextureAtlas.AtlasRegion> fill;
    private Animation<TextureAtlas.AtlasRegion> Break;
    private Animation<TextureAtlas.AtlasRegion> shine;
    private Animation<TextureAtlas.AtlasRegion> currAnimation;
    {
        TextureAtlas region = new TextureAtlas("ui/mask/FilledHealthShine.atlas");
        shine = new Animation<>(0.2f ,region.getRegions() , Animation.PlayMode.LOOP);
        region = new TextureAtlas("ui/mask/HealthRefill.atlas");
        fill = new Animation<>(0.1f , region.getRegions());
        region = new TextureAtlas("ui/mask/BreakHealth.atlas");
        Break = new  Animation<>(0.1f , region.getRegions() );

    }
    public Mask(int index , State state){
        this.index = index;
        currAnimation = shine;
        this.state = state;
        this.setSize(120 , 120);
    }


    float stateTime = 0;

    @Override
    public void act(float delta) {
        super.act(delta);
            stateTime += delta;
            if(currAnimation.isAnimationFinished(stateTime) && state == State.FILL){
                state = State.SHINE;
                stateTime = 0;
            }
    }

    public boolean isFull(){
        return full;
    }

    public void FillMask(){
        if(state !=  State.FILL){
            state = State.FILL;
            full = true;
            stateTime = 0;
        }
    }

    public void BreakMask(){
        if(state != State.BREAK){
            state = State.BREAK;
            full = false;
            stateTime = 0;
        }
    }



    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        switch (state) {
            case FILL:
                currAnimation = fill;
                break;
            case SHINE:
                currAnimation = shine;
                break;
            case BREAK:
                currAnimation = Break;
                break;
        }

        batch.draw(currAnimation.getKeyFrame(stateTime) , getX() , getY() , getWidth() , getHeight());

    }
}
