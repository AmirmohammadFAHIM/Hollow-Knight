package com.mygame.game.models.skill;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public enum ProjectileTypes {

    VENGFUL_SPIRIT(600 , 260 , 140 , 500){
        {

        }
    };


    float range;
    float width;
    float height;
    float speed;
    Animation<TextureAtlas.AtlasRegion> start;
    Animation<TextureAtlas.AtlasRegion> moving;
    Animation<TextureAtlas.AtlasRegion> end;

    ProjectileTypes(float range, float width, float height , float speed) {
        this.range = range;
        this.width = width;
        this.height = height;
        this.speed = speed;
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    public float getRange() {
        return range;
    }

    public float getSpeed() {
        return speed;
    }
}
