package com.mygame.game.models.entities.aiEnemies;

import com.badlogic.gdx.Gdx;
import com.mygame.game.models.entities.Entity;
import com.mygame.game.models.entities.Entity_States;

public class LazyMove implements Move {
    final float walkTime;
    final float rest;
    float Rw;
    float Rr;

    public LazyMove(float walkTime, float rest) {
        this.walkTime = walkTime;
        this.rest = rest;
        Rw = walkTime;
        Rr = rest;
    }

    @Override
    public void move(Entity entity) {
            if(Rw <= 0){
                if(Rr <= 0){
                    Rr = rest;
                    Rw = walkTime;
                    entity.setState(Entity_States.NORMAL);
                    /// Same shit as the bellow text
                }
                else{
                    entity.setState(Entity_States.IDLE);
                    /// pay attention to set the velocity at setState Method !
                    Rr -= Gdx.graphics.getDeltaTime();
                }
            }
            else{
                Rw -= Gdx.graphics.getDeltaTime();
            }
    }
}
