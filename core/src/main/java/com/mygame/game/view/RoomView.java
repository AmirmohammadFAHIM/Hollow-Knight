package com.mygame.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.mygame.game.models.entities.Entity;
import com.mygame.game.models.entities.EntityRenderer;
import com.mygame.game.models.entities.Entity; // یا Entity بر اساس پکیج خودت
import com.mygame.game.models.entities.aiEnemies.AiEnemy;
import com.mygame.game.models.entities.boss.FalseKnight;
import com.mygame.game.models.map.Room;
import com.mygame.game.view.gamePanes.FalseKnightRenderer;

import java.util.ArrayList;

public class RoomView {
    private final Room room;
    private final OrthogonalTiledMapRenderer renderer;
    private final ArrayList<EntityRenderer> renderers = new ArrayList<>();


    private Texture rainTexture;
    private final int maxDrops = 250;
    private float[] dropX;
    private float[] dropY;
    private float[] dropSpeed;
    private float[] dropLength;

    private int[] back;
    private  int[] fore;

    private Music backgroundMusic;
    public RoomView(Room room) {
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sfx/background.mp3"));
        ArrayList<Integer> backgroundLayers = new ArrayList<>();
        ArrayList<Integer> foregroundLayers =  new ArrayList<>();

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

        for (int i = 0; true; i++) {
            backgroundLayers.add(i);
          if(room.getMap().getLayers().get(i).getName().equals("main")) break;
        }
        int layers = room.getMap().getLayers().size();
        for (int i = backgroundLayers.getLast() + 1; i < layers ; i++) {
            foregroundLayers.add(i);
        }
        back = new int[backgroundLayers.size()];
        for (int i = 0; i < back.length; i++) {
            back[i] =  backgroundLayers.get(i);
        }
        fore = new int[foregroundLayers.size()];
        for (int i = 0; i < foregroundLayers.size(); i++) {
            fore[i] = foregroundLayers.get(i);
        }
        initRainSystem();
    }

    private void initRainSystem() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        rainTexture = new Texture(pixmap);
        pixmap.dispose();

        dropX = new float[maxDrops];
        dropY = new float[maxDrops];
        dropSpeed = new float[maxDrops];
        dropLength = new float[maxDrops];

        for (int i = 0; i < maxDrops; i++) {
            dropX[i] = (float) Math.random() * Gdx.graphics.getWidth();
            dropY[i] = (float) Math.random() * Gdx.graphics.getHeight();
            dropSpeed[i] = 700f + (float) Math.random() * 300f;
            dropLength[i] = 20f + (float) Math.random() * 25f;
        }
    }


    Room.State previousState;

    public void renderBackGround(OrthographicCamera camera,  SpriteBatch batch) {
        if(previousState == Room.State.NORMAL && room.currentState == Room.State.BOSS_FIGHT){
            backgroundMusic.play();
        }
        else if(room.currentState == Room.State.NORMAL){
            backgroundMusic.stop();
        }
        previousState = room.currentState;
        renderer.setView(camera);
       renderer.render(back);
       if(room.getName().contains("CityOfTears")){
           renderRain(camera, batch);
       }
        for (EntityRenderer r : renderers) {

            r.render(batch);

        }

    }
    public void renderForeground(OrthographicCamera camera){
        renderer.setView(camera);
        renderer.render(fore);
    }


    public void renderRain(OrthographicCamera camera, SpriteBatch batch) {
        boolean wasDrawing = batch.isDrawing();
        if (wasDrawing) {

        }

        // ۲. ست کردن دوربین و رسم لایه‌های پس‌زمینه (City of Tears دوردست)




        // ۳. باز کردن بتچ اختصاصی برای رسم باران

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


        // ۴. رسم لایه‌های پیش‌زمینه (پلتفرم‌ها حالا دقیقاً روی باران کشیده میشن)

    }

    public void dispose() {
        backgroundMusic.dispose();
    }

    public Room getRoom() {
        return room; //
    }

    public void setRoom(Room room) {} //
}
