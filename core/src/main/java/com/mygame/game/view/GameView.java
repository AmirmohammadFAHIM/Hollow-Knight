package com.mygame.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.PointMapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygame.game.controller.GameController;
import com.mygame.game.controller.GameInputProcessor;
import com.mygame.game.models.FireBall;
import com.mygame.game.models.Game;
import com.mygame.game.models.map.MapManager;

public class GameView implements Screen {
    private static GameController game;
    private static RoomView currentRoomView;
    private VesselRender vesselRender;
    private SpriteBatch  batch;
    private OrthographicCamera  camera;
    private ExtendViewport  viewport;
    private float stateTime = 0f;
    private ShapeRenderer  shapeRenderer;


    @Override
    public void show() {
        shapeRenderer = new ShapeRenderer();

        try {
            MapManager.loadChunk("CityOfTears");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        GameInputProcessor processor = new GameInputProcessor(Game.getVessel());
        game = new GameController(new Game() /* here can be the idea for saves */ ,
            processor);
        currentRoomView = new RoomView(Game.getCurrent_room());
        processor.setVessel(Game.getVessel());
        Gdx.input.setInputProcessor(processor);
        processor.setGame(game.getGame());


            /// here , a vessel is initialized , a room is initialized



        vesselRender = new VesselRender();
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(1028, 960,camera);
        PointMapObject spawnPoint = (PointMapObject) currentRoomView.getRoom().getMap().getLayers().get("Collisions").
            getObjects().get("SpawnPoint");
        float x = spawnPoint.getProperties().get("x" , Float.class);
        float y = spawnPoint.getProperties().get("y" , Float.class);
        camera.position.set( x , y , 0);
        Game.getVessel().setX(x);
        Game.getVessel().setY(y);
        Game.getVessel().updateRect();
        camera.update();
      //  batch.setProjectionMatrix(camera.combined);
        //viewport.setScreenPosition(0 , 0);

    }

    @Override
    public void render(float delta) {
       Gdx.gl.glClearColor(0, 0, 0, 1);
       Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateTime += delta;


        /// ---------------RENDERING------------------------
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        currentRoomView.render(camera , batch);
        vesselRender.render(batch , stateTime);
       /* for (FireBall x : game.getGame().getFireballs()){
            x.render(stateTime , batch);
        }*/
        //camera.position.set( Game.getVessel().getX() , Game.getVessel().getY(), 0);
        updateCamera();
        camera.update();
        batch.end();


        /// --------MAIN BRAIN OF THE GAME-----------
        game.Update(stateTime , delta);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height , false);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }


    public static RoomView getCurrentRoomView() {
        return currentRoomView;
    }

    public static void setCurrentRoomView(RoomView currentRoomView) {
        GameView.currentRoomView = currentRoomView;
    }



// ... بقیه کدهای GameView ...

         void updateCamera() {
            // ۱. گرفتن ابعاد نقشه (مپ Tiled) بر حسب پیکسل
            MapProperties prop = currentRoomView.getRoom().getMap().getProperties();
            int mapWidth = prop.get("width", Integer.class);
            int tilePixelWidth = prop.get("tilewidth", Integer.class);
            int mapPixelWidth = mapWidth * tilePixelWidth;

            int mapHeight = prop.get("height", Integer.class);
            int tilePixelHeight = prop.get("tileheight", Integer.class);
            int mapPixelHeight = mapHeight * tilePixelHeight;

            // ۲. محاسبه نصف عرض و ارتفاع دوربینی که الان داره رندر میشه
            // استفاده از viewport.getWorldWidth باعث میشه موقع ریسایز شدن پنجره هم همه‌چی درست بمونه
            float cameraHalfWidth = viewport.getWorldWidth() / 2f;
            float cameraHalfHeight = viewport.getWorldHeight() / 2f;

            // ۳. پیدا کردن موقعیت هدف (بهتره دوربین روی مرکزِ نایت فوکوس کنه نه گوشه‌ی پایینِ چپش)
            float targetX = Game.getVessel().getX() + (Game.getVessel().getWidth() / 2f);
            float targetY = Game.getVessel().getY() + (Game.getVessel().getHeight() / 2f);

            // ۴. محدود کردن (Clamp) مختصات X و Y
            // فرمول: MathUtils.clamp(مقدار فعلی, حداقل مقدار ممکن, حداکثر مقدار ممکن)
            float clampedX = MathUtils.clamp(targetX, cameraHalfWidth, mapPixelWidth - cameraHalfWidth);
            float clampedY = MathUtils.clamp(targetY, cameraHalfHeight, mapPixelHeight - cameraHalfHeight);

            // ۵. اعمال مختصات جدید به دوربین
            camera.position.set(clampedX, clampedY, 0);
            camera.update();
        }

}
