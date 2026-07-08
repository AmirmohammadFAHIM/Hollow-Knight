package com.mygame.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygame.game.models.entities.Entity;
import com.mygame.game.models.entities.EntityRenderer;
import com.mygame.game.models.entities.Entity; // یا Entity بر اساس پکیج خودت
import com.mygame.game.models.entities.aiEnemies.AiEnemy;
import com.mygame.game.models.entities.boss.FalseKnight;
import com.mygame.game.models.map.Room;
import com.mygame.game.view.gamePanes.FalseKnightRenderer;

import java.util.ArrayList;

public class RoomView {
    private Room room;
    private OrthogonalTiledMapRenderer renderer;
    private ArrayList<EntityRenderer> renderers = new ArrayList<>();

    // --- سیستم باران اختصاصی تیک‌تیک ---
    private Texture rainTexture;
    private int maxDrops = 250; // تعداد قطرات باران در صفحه
    private float[] dropX;
    private float[] dropY;
    private float[] dropSpeed;
    private float[] dropLength;

    // تفکیک اندیس لایه‌های Tiled Map (این اعداد رو بر اساس لایه‌های مپ خودت تنظیم کن)
    private int[] backgroundLayers = {0, 1 , 2 , 3 }; // لایه‌های آسمان و ساختمان‌های دور
    private int[] foregroundLayers = { 4 , 5 ,6 , 7 , 8 ,9 , 10 , 11 , 12}; // لایه‌های زمین، پلتفرم‌ها و دکوراسیون جلو

    public RoomView(Room room) {
        this.room = room;
        for (Entity c : room.getEnemies()) {
           if(c instanceof FalseKnight){
               FalseKnightRenderer r = new FalseKnightRenderer((FalseKnight) c);
               renderers.add(r);
           }
           else{
               EntityRenderer r = new EntityRenderer();
               r.setEntity(c);
               renderers.add(r);
           }
        }
        renderer = new OrthogonalTiledMapRenderer(room.getMap());

        // راه‌اندازی سیستم باران
        initRainSystem();
    }

    private void initRainSystem() {
        // ساخت یک بافت ۱در۱ پیکسل سفید در حافظه (بدون نیاز به لود کردن عکس)
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        rainTexture = new Texture(pixmap);
        pixmap.dispose(); // آزاد کردن حافظه موقت

        dropX = new float[maxDrops];
        dropY = new float[maxDrops];
        dropSpeed = new float[maxDrops];
        dropLength = new float[maxDrops];

        // پخش کردن قطرات به صورت تصادفی در فضای اولیه کادر
        for (int i = 0; i < maxDrops; i++) {
            dropX[i] = (float) Math.random() * Gdx.graphics.getWidth();
            dropY[i] = (float) Math.random() * Gdx.graphics.getHeight();
            dropSpeed[i] = 700f + (float) Math.random() * 300f; // سرعت سقوط
            dropLength[i] = 20f + (float) Math.random() * 25f;  // طول قطره باران
        }
    }

    public void render(OrthographicCamera camera, SpriteBatch batch) {
        // ۱. اگر بتچ از کلاس GameView باز مونده، موقتاً می‌بندیمش تا تداخلی با مپ نداشته باشه
        boolean wasDrawing = batch.isDrawing();
        if (wasDrawing) {
            batch.end();
        }

        // ۲. ست کردن دوربین و رسم لایه‌های پس‌زمینه (City of Tears دوردست)
        renderer.setView(camera);
        renderer.render(backgroundLayers);

        // ۳. باز کردن بتچ اختصاصی برای رسم باران
        batch.begin();

        float camX = camera.position.x - camera.viewportWidth / 2f;
        float camY = camera.position.y - camera.viewportHeight / 2f;

        Color originalColor = batch.getColor().cpy();
        batch.setColor(0.4f, 0.55f, 0.7f, 0.3f);

        for (int i = 0; i < maxDrops; i++) {
            dropY[i] -= dropSpeed[i] * Gdx.graphics.getDeltaTime();
            dropX[i] -= (dropSpeed[i] * 0.15f) * Gdx.graphics.getDeltaTime();

            if (dropY[i] < camY || dropX[i] < camX || dropX[i] > camX + camera.viewportWidth) {
                dropY[i] = camY + camera.viewportHeight + (float) Math.random() * 50;
                dropX[i] = camX + (float) Math.random() * camera.viewportWidth;
            }

            batch.draw(rainTexture, dropX[i], dropY[i], 1.5f, dropLength[i]);
        }

        batch.setColor(originalColor);

        // --- خط جادویی رفع باگ ---
        // بتچ رو همینجا می‌بندیم تا تمام قطرات باران همون لحظه روی صفحه (روی پس‌زمینه) چاپ بشن!
        batch.end();

        // ۴. رسم لایه‌های پیش‌زمینه (پلتفرم‌ها حالا دقیقاً روی باران کشیده میشن)
        renderer.render(foregroundLayers);

        // ۵. دوباره باز کردن بتچ برای رسم کاراکترها و انمی‌ها
        batch.begin();
        for (EntityRenderer r : renderers) {

               r.render(batch);

        }

        // در نهایت بتچ رو باز می‌ذاریم بمونه تا کلاس GameView خودش متد end() رو برای پایان فریم صدا بزنه
    }

    public Room getRoom() {
        return room; //
    }

    public void setRoom(Room room) {} //
}
