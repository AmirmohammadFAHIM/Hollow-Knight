package com.mygame.game.models.skill;

import com.mygame.game.models.Game;
import com.mygame.game.models.entities.aiEnemies.AiEnemy;

import java.awt.*;

public class Shootable {
        Rectangle bounds;
        float speed;
        float range;

    public Shootable(ShootableTypes type){
            this.speed = type.speed;
            this.range = type.range;
    }


}
