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
    private float blastStateTime = 0;
    private ShapeRenderer renderer = new ShapeRenderer();

    private static Animation<TextureAtlas.AtlasRegion> currentAnimation;
    private Animation<TextureAtlas.AtlasRegion> slashEffect;
    private Animation<TextureAtlas.AtlasRegion> upSlashEffect;
    private Animation<TextureAtlas.AtlasRegion> downSlashEffect;
    private Animation<TextureAtlas.AtlasRegion> blast;


    public  VesselRender() {
        vessel = Game.getVessel();
        initEffects();
    }

    private void initEffects(){
        TextureAtlas effect = new  TextureAtlas(Gdx.files.internal("knight/SlashEffect.atlas"));
        slashEffect = new Animation<>(0.06f , effect.findRegions("SlashEffect"));
        effect = new TextureAtlas("knight/UpSlashEffect.atlas");
        upSlashEffect = new Animation<>(0.06f , effect.findRegions("UpSlashEffect"));
        effect = new TextureAtlas("knight/DownSlashEffect.atlas");
        downSlashEffect = new Animation<>(0.06f , effect.findRegions("DownSlashEffect"));
        effect = new TextureAtlas("knight/Blast.atlas");
        blast = new Animation<>(0.1f , effect.findRegions("Blast"));
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
        renderSlash(batch);
        renderBlast(batch);


    }

    private void checkDir(TextureAtlas.AtlasRegion frame){
        if(vessel.isRight() && !frame.isFlipX()) frame.flip(true,false);
        else if(!vessel.isRight()  && frame.isFlipX()) frame.flip(true,false);
    }

    private void checkDirRight(TextureAtlas.AtlasRegion frame){
        if(vessel.isRight() && frame.isFlipX()) frame.flip(true,false);
        else if(!vessel.isRight()  && !frame.isFlipX()) frame.flip(true,false);
    }

    private void renderBlast(SpriteBatch batch){
        if(vessel.getState() == States.FIREBALL){
            blastStateTime += Gdx.graphics.getDeltaTime();

            // اول فریم رو می‌گیریم تا بتونیم جهت رو اصلاح کنیم و عرض عکس رو داشته باشیم
            TextureAtlas.AtlasRegion blastFrame = blast.getKeyFrame(blastStateTime);
            checkDirRight(blastFrame);

            float x;
            if (vessel.isRight()) {
                // شلیک به راست: فایربال از لبه‌ی راست نایت شروع به رسم شدن می‌کنه
                x = vessel.getX() + vessel.getWidth();
            } else {
                // شلیک به چپ: نقطه شروعِ رسم رو به اندازه عرض فایربال به عقب می‌بریم
                x = vessel.getX() - blastFrame.getRegionWidth();
            }

            float y = vessel.getY();

            batch.draw(blastFrame, x, y - 50);
        }
        else {
            blastStateTime = 0;
        }
    }


    private void renderSlash(SpriteBatch batch){
        slashStateTime += Gdx.graphics.getDeltaTime();
        Rectangle rect = vessel.getSlashBounds();
        TextureAtlas.AtlasRegion slashFrame;
        if(vessel.getState() == States.SLASH){
            slashFrame = slashEffect.getKeyFrame(slashStateTime);
            checkDir(slashFrame);
            batch.draw(slashFrame, rect.x, rect.y);
        }
        else if(vessel.getState() == States.UP_SLASH){
            slashFrame = upSlashEffect.getKeyFrame(slashStateTime);
            checkDir(slashFrame);
            batch.draw(slashFrame, rect.x, rect.getY());
        }
        else if(vessel.getState() == States.DOWN_SLASH){
            slashFrame = downSlashEffect.getKeyFrame(slashStateTime);
            checkDir(slashFrame);
            batch.draw(upSlashEffect.getKeyFrame(slashStateTime), rect.x,   rect.y);
        }
        else slashStateTime = 0;



    }

    public static Animation<TextureAtlas.AtlasRegion> getCurrentAnimation() {
        return currentAnimation;
    }
}
