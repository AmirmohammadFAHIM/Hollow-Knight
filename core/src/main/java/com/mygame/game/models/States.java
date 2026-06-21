package com.mygame.game.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public enum States {
    IDLE(new TextureAtlas(Gdx.files.internal("knight/Idle.atlas")) , "Idle" , Animation.PlayMode.LOOP),

    RUNNING(new TextureAtlas(Gdx.files.internal("knight/Run.atlas")) , "Run"
        , Animation.PlayMode.LOOP),

    JUMPING(new TextureAtlas("knight/jump.atlas") , "Airborne"
),

    DOUBLE_JUMP(new  TextureAtlas("knight/DoubleJump.atlas") ,  "DoubleJump"
),

    DASH(new TextureAtlas("knight/Dash.atlas") , "Dash"
),

    SLASH(new  TextureAtlas("knight/SlashAlt.atlas") ,  "SlashAlt"
),

   // DOWN_SLASH,

   // UP_SLASH,

    START_FOCUS(new  TextureAtlas("knight/FocusStart.atlas") , "FocusStart"
),

    FOCUS(new  TextureAtlas("knight/Focus.atlas") ,  "Focus"
),

    LANDING(new  TextureAtlas("knight/Landing.atlas") , "Landing"
),

    FALLING(new TextureAtlas("knight/Fall.atlas") , "Fall" , Animation.PlayMode.LOOP
),

    WALL_JUMP(new  TextureAtlas("knight/WallJump.atlas") , "WallJump" ),

    WALL_SIDE(new  TextureAtlas("knight/WallSide.atlas") , "WallSide" , Animation.PlayMode.LOOP),

   // BRAKE,

    FIREBALL(new TextureAtlas("knight/FireballCast.atlas") , "FireballCast"
),

    Death(new  TextureAtlas("knight/Death.atlas") ,  "Death"
);

    Animation<TextureAtlas.AtlasRegion> animation;

     States(TextureAtlas atlas ,String regionName , Animation.PlayMode playMode){
        animation = new Animation<>(0.1f , atlas.findRegions(regionName) , playMode);
    }

    States(TextureAtlas atlas , String regionName){
         animation = new Animation<>(0.1f , atlas.findRegions(regionName) , Animation.PlayMode.NORMAL);
    }


    public Animation<TextureAtlas.AtlasRegion> getAnimation() {
        return animation;
    }
}
