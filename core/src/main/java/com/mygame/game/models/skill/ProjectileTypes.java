package com.mygame.game.models.skill;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public enum ProjectileTypes {

    VENGFUL_SPIRIT(600 , 260 , 140 , 500){
        {
            TextureAtlas spriteSheet = new TextureAtlas("knight/SoulBallStart.atlas");
            start = new Animation<>(0.09f , spriteSheet.findRegions("SoulBall"));
            spriteSheet = new TextureAtlas("knight/SoulBall.atlas");
            moving = new Animation<>(0.09f , spriteSheet.findRegions("SoulBall"),
                Animation.PlayMode.LOOP);
            spriteSheet = new TextureAtlas("knight/BallEnd.atlas");
            end = new Animation<>(0.09f , spriteSheet.findRegions("Ball End"));
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

    public Animation<TextureAtlas.AtlasRegion> getEnd() {
        return end;
    }

    public Animation<TextureAtlas.AtlasRegion> getStart() {
        return start;
    }

    public Animation<TextureAtlas.AtlasRegion> getMoving() {
        return moving;
    }
}
