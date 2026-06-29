package com.mygame.game.models.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public enum ENEMIES {

    HUSK_DANDY(100 , 50 , null , null , null , null);



     ENEMIES(float range , float hp , TextureAtlas normal , TextureAtlas hit ,
             TextureAtlas die , TextureAtlas angry) {
         this.range = range;
         this.hp = hp;
         this.normal = new Animation<>(0.8f , normal.findRegions(normal.toString()));
         this.hit = new Animation<>(0.8f , hit.findRegions(hit.toString()));
         this.die = new Animation<>(0.8f , die.findRegions(hit.toString()));
         this.angry = new Animation<>(0.8f , angry.findRegions(hit.toString()));
     }
    float range;
    float hp;
    Animation<TextureAtlas.AtlasRegion> normal;
    Animation<TextureAtlas.AtlasRegion> hit;
    Animation<TextureAtlas.AtlasRegion> die;
    Animation<TextureAtlas.AtlasRegion> angry;

}
