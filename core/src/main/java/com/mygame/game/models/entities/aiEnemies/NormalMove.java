package com.mygame.game.models.entities.aiEnemies;

import com.mygame.game.models.entities.Entity;
import com.mygame.game.models.entities.Entity_States;

public class NormalMove implements  Move {
    @Override
    public void move(Entity entity) {
       if(entity.getState() != Entity_States.NORMAL) entity.setState(Entity_States.NORMAL);
        /// the velocity is set at setState method
    }
}
