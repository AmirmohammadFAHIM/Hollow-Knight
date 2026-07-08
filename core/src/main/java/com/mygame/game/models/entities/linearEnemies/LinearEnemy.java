package com.mygame.game.models.entities.linearEnemies;

import com.badlogic.gdx.math.Rectangle;
import com.mygame.game.models.Game;
import com.mygame.game.models.entities.Entity;
import com.mygame.game.models.entities.Entity_States;
import com.mygame.game.models.map.SolidBlock;

import java.util.ArrayList;

public class LinearEnemy extends Entity {
    private float damage;
    private LinearEnemies type;
    private static float vX = 70;
    private static float vY = 300;

    public LinearEnemy(LinearEnemies type, float x, float y) {
        super(type.name() , x , y , LinearEnemy.getvX());
        this.bounds = new Rectangle(x, y, width, height);
        this.type = type;
        setState(Entity_States.NORMAL);
        velocityX = vX * (right? 1 : -1);
        currentAnimation = type.walk;
        this.x = x;
        this.y = y;
        this.name = type.name();
        this.hp = type.hp;
    }


    @Override
    public boolean update(float delta , Game game){
        if(state == Entity_States.NORMAL){
            currentAnimation = type.walk;
        }
        else if(state == Entity_States.TURN){
            currentAnimation = type.turn;
        }
        else if(state == Entity_States.DEAD) {
            currentAnimation = type.die;
        }
        else if(state == Entity_States.DEATH_LANDING){
            currentAnimation = type.land;
        }

        stateTime += delta;


       if(!super.update(delta, game)){
           return false;
       }

        if(state.hasNextState() && currentAnimation.isAnimationFinished(stateTime)){
            setState(state.getNextState());
        }

        return true;
    }


    public static float getvX() {
        return vX;
    }

    public static float getvY() {
        return vY;
    }

    @Override
    public void setState(Entity_States state) {
        if(state == Entity_States.TURN){
            velocityX *= -1;
        }
        super.setState(state);
    }
}
