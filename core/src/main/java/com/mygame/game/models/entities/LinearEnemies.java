package com.mygame.game.models.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public enum LinearEnemies {

    TIKTIK(50 , 50 , 80){
        {
            TextureAtlas x = new TextureAtlas("enemies/tiktik/Walk.atlas");
            this.walk = new  Animation<>(0.09f, x.findRegions("Walk") ,
                Animation.PlayMode.LOOP);
            x = new  TextureAtlas("enemies/tiktik/DeathAir.atlas");
            this.die = new   Animation<>(0.09f, x.findRegions("Death Air"));
            x = new   TextureAtlas("enemies/tiktik/DeathLand.atlas");
            this.land  = new  Animation<>(0.09f, x.findRegions
                ("DeathLand"));
            x = new   TextureAtlas("enemies/tiktik/Turn.atlas");
            this.turn = new  Animation<>(0.09f, x.findRegions("Turn"));
        }
    };



    Animation<TextureAtlas.AtlasRegion> walk;
    Animation<TextureAtlas.AtlasRegion> die;
    Animation<TextureAtlas.AtlasRegion> land;
    Animation<TextureAtlas.AtlasRegion> turn;

    float width;
    float height;
    float hp;
    LinearEnemies(float width , float height ,  float hp){
        this.width = width;
        this.height = height;
        this.hp = hp;
    }
}
