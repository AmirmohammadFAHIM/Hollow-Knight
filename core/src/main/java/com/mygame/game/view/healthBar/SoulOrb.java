package com.mygame.game.view.healthBar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygame.game.models.Game;

public class SoulOrb extends Actor {
    private TextureRegion emptyOrbMask;  // دایره مشکی (پس‌زمینه و ماسک)
    private TextureRegion orbEyes;       // چشم‌های نایت
    private TextureRegion vesselFrame;   // قاب شیشه‌ای دور مخزن
    private Animation<TextureRegion> liquidAnim;
    private float stateTime = 0f;
    private final float MAX_SOUL = 99f;

    public SoulOrb() {
        // ۱. لود کردن عکس‌های جدیدی که دادی
        emptyOrbMask = new TextureRegion(new Texture("menus/SoulOrb_Empty.png"));
        orbEyes = new TextureRegion(new Texture("menus/SoulOrb_Eye.png"));

        // قاب قبلی رو نگه داشتم تا افکت شیشه‌ای از بین نره
        // (اگر کلاً قاب نمی‌خوای، می‌تونی این خط رو پاک کنی)

        // ۲. لود کردن انیمیشن مایع روح از اطلس
        TextureAtlas liquidAtlas = new TextureAtlas("menus/Soulorb.atlas");
        liquidAnim = new Animation<>
            (0.08f, liquidAtlas.getRegions(), Animation.PlayMode.LOOP);

        this.setSize(150, 150);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        float currentSoul = Game.getVessel().getSoul();
        float percent = currentSoul / MAX_SOUL;

        batch.draw(emptyOrbMask, getX(), getY(), getWidth(), getHeight());

        if (currentSoul > 0) {
            batch.flush();

            Gdx.gl.glColorMask(false, false, false, true);
            batch.setBlendFunction(GL20.GL_ONE, GL20.GL_ZERO);
            batch.draw(emptyOrbMask, getX(), getY(), getWidth(), getHeight());
            batch.flush();

            Gdx.gl.glColorMask(true, true, true, true);
            batch.setBlendFunction(GL20.GL_DST_ALPHA, GL20.GL_ONE_MINUS_DST_ALPHA);

            TextureRegion currentFrame = liquidAnim.getKeyFrame(stateTime);
            int fullSrcHeight = currentFrame.getRegionHeight();
            int fullSrcWidth = currentFrame.getRegionWidth();

            int clippedSrcHeight = (int) (fullSrcHeight * percent);
            int clippedSrcY = currentFrame.getRegionY() + (fullSrcHeight - clippedSrcHeight);

            batch.draw(
                currentFrame.getTexture(),
                getX(), getY(), getWidth(), getHeight() * percent,
                currentFrame.getRegionX(), clippedSrcY, fullSrcWidth, clippedSrcHeight,
                false, false
            );
            batch.flush();

            batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        }


        float eyeW = getWidth() * 0.5f;
        float eyeH = eyeW * ((float) orbEyes.getRegionHeight() / orbEyes.getRegionWidth());
        float eyeX = getX() + (getWidth() - eyeW) / 2f;
        float eyeY = getY() + (getHeight() - eyeH) / 2f;

        batch.draw(orbEyes, eyeX, eyeY, eyeW, eyeH);

        if (vesselFrame != null) {
            batch.draw(vesselFrame, getX(), getY(), getWidth(), getHeight());
        }
    }
}
