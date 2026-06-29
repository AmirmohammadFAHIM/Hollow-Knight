package com.mygame.game.models.entities;

public class Enemy extends NPC {
    private static float velocityNormal;
    private static float velocityAngry;
    private static float areaRange;
    private ENEMIES type;


    public Enemy(String name, float x, float y , ENEMIES type) {
        super(name, x, y);
        this.type = type;


    }

}
