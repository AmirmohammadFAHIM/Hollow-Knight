package com.mygame.game.models.skill;

import java.awt.*;

public class Projectile {
        Rectangle bounds;
        float speed;
        float range;

    public Projectile(ProjectileTypes type){
            this.speed = type.speed;
            this.range = type.range;
    }


}
