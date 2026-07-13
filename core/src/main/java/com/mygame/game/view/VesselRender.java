package com.mygame.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
    private Animation<TextureAtlas.AtlasRegion> scream;
    private Sound slashSound;
    private Sound focusGet;
    private Music focusCharging;


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
        blast = new Animation<>(0.06f , effect.findRegions("Blast"));
        effect = new TextureAtlas("knight/SoulScream.atlas");
        scream = new Animation<>(0.06f, effect.getRegions());

        slashSound = Gdx.audio.newSound(Gdx.files.internal("sfx/knight/slash.wav"));
        focusGet = Gdx.audio.newSound(Gdx.files.internal("sfx/knight/focusGet.wav"));
        focusCharging = Gdx.audio.newMusic(Gdx.files.internal("sfx/knight/focusCharging.wav"));
    }



    private void update_rendering(float delta) {
        if(currentAnimation != vessel.getState().getAnimation()){
            if(vessel.getState() == States.SLASH || vessel.getState() == States.UP_SLASH ||
            vessel.getState() == States.DOWN_SLASH) slashSound.play();
             if(vessel.getState() == States.FOCUS && !focusCharging.isPlaying()){
                 focusCharging.play();
             }
             else{
                 focusCharging.stop();
             }
             if(vessel.getState() == States.FOCUS_GET) focusGet.play();
        }
      currentAnimation =  vessel.getState().getAnimation();


    }

    public void render(SpriteBatch batch , float stateTime){


       // vessel.update(Gdx.graphics.getDeltaTime());
        update_rendering(Gdx.graphics.getDeltaTime());




        TextureAtlas.AtlasRegion frame = (TextureAtlas.AtlasRegion) getCurrentAnimation().getKeyFrame(vessel.getStateTime() , true);

        float drawX = vessel.getX() - (frame.getRegionWidth() - vessel.getWidth()) / 2f;
        float drawY = vessel.getY();

        checkDir(frame);

        if (vessel.hurt) {
            float alpha = ((int)(vessel.getStateTime() * 15) % 2 == 0) ? 0.3f : 1f;
            batch.setColor(1, 1, 1, alpha);
        } else {
            batch.setColor(1, 1, 1, 1);
        }




        batch.draw(frame , drawX , drawY);
        batch.setColor(1, 1, 1, 1);

        renderSlash(batch);
        renderBlast(batch);
        soulScream(batch);



    }

    float screamTime = 0;
    private void soulScream(SpriteBatch batch){
        if(vessel.getState() == States.SCREAM){
            screamTime +=  Gdx.graphics.getDeltaTime();
            batch.draw(scream.getKeyFrame(screamTime) , vessel.soulScram.x , vessel.soulScram.y);
        }
        else screamTime = 0;
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
        if(vessel.getState() == States.SLASH ||
            vessel.getState() == States.UP_SLASH || vessel.getState() == States.DOWN_SLASH)  currentAnimation.setFrameDuration(vessel.getCharms().containsKey("Quick Slash") ? 0.072f : 0.09f);
        slashEffect.setFrameDuration(vessel.getCharms().containsKey("Quick Slash") ? 0.048f : 0.06f);
        upSlashEffect.setFrameDuration(vessel.getCharms().containsKey("Quick Slash") ? 0.048f : 0.06f);
        downSlashEffect.setFrameDuration(vessel.getCharms().containsKey("Quick Slash") ? 0.048f : 0.06f);
        if(vessel.getState() == States.SLASH){
            slashFrame = slashEffect.getKeyFrame(slashStateTime);
            checkDir(slashFrame);
            batch.draw(slashFrame, rect.x + (vessel.isRight()? -35 : 20), rect.y);
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


    private void sound(){
        if(vessel.getState() == States.SLASH){
            slashSound.play();
        }
    }

    public void dispose(){
        slashSound.dispose();
        focusCharging.dispose();
        focusGet.dispose();
    }

    public static Animation<TextureAtlas.AtlasRegion> getCurrentAnimation() {
        return currentAnimation;
    }
}
