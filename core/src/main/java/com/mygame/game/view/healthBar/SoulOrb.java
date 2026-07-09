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
    private TextureRegion vesselFrame;      // قاب شیشه‌ای دور
    private TextureRegion vesselInnerMask;  // 👈 دایره توپر برای مشخص کردن محدوده مجاز مایع
    private Animation<TextureAtlas.AtlasRegion> liquidAnim;
    private float stateTime = 0f;
    private final float MAX_SOUL = 99f;

    public SoulOrb() {
        TextureAtlas frameAtlas = new TextureAtlas("ui/vessel/VesselFrame.atlas");
        vesselFrame = new  TextureRegion(new Texture("menus/HealthBar_005.png"));
        //vesselInnerMask = frameAtlas.findRegion("vessel_mask"); // 👈 لود کردن عکس دایره توپر داخلی

        TextureAtlas liquidAtlas = new TextureAtlas("ui/vessel/SoulLiquid.atlas");
        liquidAnim = new Animation<>(0.05f, liquidAtlas.getRegions(), Animation.PlayMode.LOOP);

        // 👈 صلاخ ۱: بزرگتر کردن سایز پیش‌فرض مخزن روح متناسب با ماسک‌ها
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

        // اگر روحی نبود، فقط قاب خالی را بکش و خارج شو
        if (currentSoul <= 0) {
            batch.draw(vesselFrame, getX(), getY(), getWidth(), getHeight());
            return;
        }

        // ---- شروع جادوی ماسک کردن گرافیکی ----
        // خروجی‌های قبلی بچ را رسم کن تا با ماسک ما تداخل پیدا نکنند
        batch.flush();

        // ۱. غیرفعال کردن رنگ‌ها و فعال کردن کانال آلفا (شفافیت) برای کشیدن محدوده دایره
        Gdx.gl.glColorMask(false, false, false, true);
        batch.setBlendFunction(GL20.GL_ONE, GL20.GL_ZERO);

        // رسم دایره ماسک (این دایره دیده نمیشه، فقط به کارت گرافیک میگه کجاها مجازه)
        batch.draw(vesselInnerMask, getX(), getY(), getWidth(), getHeight());
        batch.flush();

        // ۲. مجدداً کانال‌های رنگی را فعال کن
        Gdx.gl.glColorMask(true, true, true, true);

        // ۳. تنظیم مپینگ بچ به شکلی که رنگ‌ها رو فقط جایی رندر کنه که آلفای دایره ماسک وجود داره
        batch.setBlendFunction(GL20.GL_DST_ALPHA, GL20.GL_ONE_MINUS_DST_ALPHA);

        // حالا فریم انیمیشن مایع رو طبق فرمول قبلی برش عمودی می‌زنیم
        TextureRegion currentFrame = liquidAnim.getKeyFrame(stateTime);
        int fullSrcHeight = currentFrame.getRegionHeight();
        int fullSrcWidth = currentFrame.getRegionWidth();
        int clippedSrcHeight = (int) (fullSrcHeight * percent);
        int clippedSrcY = currentFrame.getRegionY() + (fullSrcHeight - clippedSrcHeight);

        // رسم مایع روح (این بار لبه‌های مربعی‌اش توسط ماسک دایره‌ای کاملاً قیچی می‌شن!)
        batch.draw(
            currentFrame.getTexture(),
            getX(), getY(), getWidth(), getHeight() * percent,
            currentFrame.getRegionX(), clippedSrcY, fullSrcWidth, clippedSrcHeight,
            false, false
        );
        batch.flush();

        // ۴. ریست کردن وضعیت سیستم بلندیگ لایب‌جی‌دی‌ایکس به حالت پیش‌فرض بازی
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        // ۵. در نهایت قاب شیشه‌ای و جزییات دور مخزن را روی مایع رسم می‌کنیم تا لبه‌ها تمیز بپوشند
        batch.draw(vesselFrame, getX(), getY(), getWidth(), getHeight());
    }
}
