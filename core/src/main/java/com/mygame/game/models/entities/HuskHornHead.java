package com.mygame.game.models.entities;

import com.mygame.game.models.Game;

public class HuskHornHead extends Entity {
    public HuskHornHead(String name, float x, float y) {
        super(name, x, y);
    }
    ENEMIES type;


    public void detectEnemy(Game game){
        if(Math.abs(Game.getVessel().getX() - this.x) < type.range &&
        Math.abs(Game.getVessel().getY() - this.y) < 30) {
            this.state = Entity_States.Attack;
        }
    }


}
