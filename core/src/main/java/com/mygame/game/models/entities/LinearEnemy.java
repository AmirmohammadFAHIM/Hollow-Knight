package com.mygame.game.models.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.mygame.game.models.map.SolidBlock;

import java.util.ArrayList;

public class LinearEnemy extends NPC{
    private float damage;
    private LinearEnemies type;
    private static float vX = 70;
    private static float vY = 300;

    public LinearEnemy(LinearEnemies type, float x, float y) {
        super(type.name() , x , y);
        this.bounds = new Rectangle(x, y, width, height);
        this.type = type;
        setState(NPC_States.NORMAL);
        velocityX = vX * (right? 1 : -1);
        currentAnimation = type.walk;
        this.x = x;
        this.y = y;
        this.name = type.name();
        this.hp = type.hp;
    }


    @Override
    public void update(float delta , ArrayList<SolidBlock> blocks){
        if(state == NPC_States.NORMAL){
            currentAnimation = type.walk;
        }
        else if(state == NPC_States.TURN){
            currentAnimation = type.turn;
        }
        else if(state == NPC_States.DEAD) {
            currentAnimation = type.die;
        }
        else if(state == NPC_States.DEATH_LANDING){
            currentAnimation = type.land;
        }

        stateTime += delta;


       super.update(delta, blocks);

        if(state.hasNextState() && currentAnimation.isAnimationFinished(stateTime)){
            setState(state.getNextState());
        }
    }


    public static float getvX() {
        return vX;
    }

    public static float getvY() {
        return vY;
    }

    @Override
    public void setState(NPC_States state) {
        if(state == NPC_States.TURN){
            velocityX *= -1;
        }
        super.setState(state);
    }
}
