package com.mygame.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.mygame.game.models.Game;
import com.mygame.game.models.States;
import com.mygame.game.models.Vessel;

public class VesselRender {
    private Vessel vessel;
    private float slashStateTime = 0;

    private static Animation<TextureAtlas.AtlasRegion> currentAnimation;
    private Animation<TextureAtlas.AtlasRegion> slashEffect;


    public  VesselRender() {
        vessel = Game.getVessel();
        initEffects();
    }

    private void initEffects(){
        TextureAtlas effect = new  TextureAtlas(Gdx.files.internal("knight/SlashEffectAlt.atlas"));
        slashEffect = new Animation<>(0.06f , effect.findRegions("SlashEffectAlt"));
    }



    private void update_rendering(float delta) {
      currentAnimation =  vessel.getState().getAnimation();

    }

    public void render(SpriteBatch batch , float stateTime){


       // vessel.update(Gdx.graphics.getDeltaTime());
        update_rendering(Gdx.graphics.getDeltaTime());




        TextureAtlas.AtlasRegion frame = (TextureAtlas.AtlasRegion) getCurrentAnimation().getKeyFrame(vessel.getStateTime() , true);

        float drawX = vessel.getX() - (frame.getRegionWidth() - vessel.getWidth()) / 2f;
        float drawY = vessel.getY();

        checkDir(frame);
        batch.draw(frame , drawX , drawY);
        if(vessel.getState() == States.SLASH){
            slashStateTime += Gdx.graphics.getDeltaTime();
           Rectangle rect = vessel.getSlashBounds();
           TextureAtlas.AtlasRegion slashFrame = slashEffect.getKeyFrame(slashStateTime);
           checkDir(slashFrame);
           batch.draw(slashFrame,rect.x , rect.y);
        }
        else slashStateTime = 0;

    }

    private void checkDir(TextureAtlas.AtlasRegion frame){
        if(vessel.isRight() && !frame.isFlipX()) frame.flip(true,false);
        else if(!vessel.isRight()  && frame.isFlipX()) frame.flip(true,false);
    }


    public static Animation<TextureAtlas.AtlasRegion> getCurrentAnimation() {
        return currentAnimation;
    }
}
