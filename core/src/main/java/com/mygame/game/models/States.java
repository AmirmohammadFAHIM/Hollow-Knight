package com.mygame.game.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygame.game.view.VesselRender;

public enum States {
    IDLE(new TextureAtlas(Gdx.files.internal("knight/Idle.atlas")) , "Idle" , Animation.PlayMode.LOOP),

    RUNNING(new TextureAtlas(Gdx.files.internal("knight/Run.atlas")) , "Run"
        , Animation.PlayMode.LOOP),





    DASH(new TextureAtlas("knight/Dash.atlas") , "Dash" , 0.05f
, IDLE , true){

    },

    SLASH(new  TextureAtlas("knight/SlashAlt.atlas") ,  "SlashAlt", 0.09f ,
        IDLE , false
),

   DOWN_SLASH(new TextureAtlas("knight/DownSlash.atlas") , "DownSlash" ,0.09f ,IDLE,false),

   UP_SLASH(new TextureAtlas("knight/UpSlash.atlas") , "UpSlash",0.09f ,IDLE,false),


    FOCUS_GET(new TextureAtlas("knight/FocusGet.atlas") , "Focus Get"),

    FOCUS(new  TextureAtlas("knight/Focus.atlas") ,  "Focus"
, 0.1f , null , false){
        {
            this.animation.setPlayMode(Animation.PlayMode.LOOP);
        }
    },

    START_FOCUS(new  TextureAtlas("knight/FocusStart.atlas") , "Focus Start"
     , 0.09f , FOCUS , false),

    LANDING(new  TextureAtlas("knight/Landing.atlas") , "Landing" , 0.09f,
        IDLE , false
),

    FALLING(new TextureAtlas("knight/Fall.atlas") , "Fall" , 0.09f ,
        LANDING , false
){
        {
            this.animation.setPlayMode(Animation.PlayMode.LOOP);
        }
        @Override
        boolean shouldGoNext(float stateTime) {
            return Game.getVessel().isIs_ground();
        }
    },

    WALL_JUMP(new  TextureAtlas("knight/WallJump.atlas") , "Walljump" ),

    WALL_SIDE(new  TextureAtlas("knight/WallSide.atlas") , "Wall Slide" , Animation.PlayMode.LOOP),

   // BRAKE,

    FIREBALL(new TextureAtlas("knight/FireballCast.atlas") , "Fireball Cast"
,0.09f , null , true ),

    JUMPING(new TextureAtlas("knight/jump.atlas") , "Airborne",
 0.09f , FALLING , false),

    DOUBLE_JUMP(new  TextureAtlas("knight/DoubleJump.atlas") ,  "Double Jump",
        0.09f , FALLING , false
    ),

    Death(new  TextureAtlas("knight/Death.atlas") ,  "Death"
);

    Animation<TextureAtlas.AtlasRegion> animation;

     States(TextureAtlas atlas ,String regionName , Animation.PlayMode playMode){
        animation = new Animation<>(0.09f , atlas.findRegions(regionName) , playMode);
    }

    States(TextureAtlas atlas , String regionName){
         animation = new Animation<>(0.09f , atlas.findRegions(regionName) , Animation.PlayMode.NORMAL);
    }

    States(TextureAtlas atlas , String regionName, float frameTime){
         animation = new Animation<>(frameTime ,  atlas.findRegions(regionName) , Animation.PlayMode.NORMAL);
    }

    States(TextureAtlas atlas , String regionName, float frameTime
    , States nextState , boolean priority ){
         animation = new Animation<>(frameTime , atlas.findRegions(regionName)
             , Animation.PlayMode.NORMAL);
         this.nextState  = nextState;
         this.priority = priority;
        // this.once = once;
    }

    States nextState = null;

     boolean priority = false;

    // boolean once = true;

     boolean shouldGoNext(float stateTime ){
         if(VesselRender.getCurrentAnimation().isAnimationFinished(stateTime)){
             return nextState != null;
         }
         return false;
     }

     public boolean getPriority(){
         return priority;
     }

    public Animation<TextureAtlas.AtlasRegion> getAnimation() {
        return animation;
    }
}
