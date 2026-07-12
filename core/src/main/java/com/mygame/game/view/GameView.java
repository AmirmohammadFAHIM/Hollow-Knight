package com.mygame.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.PointMapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygame.game.controller.GameController;
import com.mygame.game.controller.GameInputProcessor;
import com.mygame.game.models.Game;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.mygame.game.models.map.MapManager;
import com.mygame.game.models.map.Room;
import com.mygame.game.models.skill.Projectile;
import com.mygame.game.view.gamePanes.Inventory;
import com.mygame.game.view.gamePanes.ProjectileRenderer;
import com.mygame.game.view.healthBar.HUD;


public class GameView implements Screen {
    private static GameController game; /// save
    private GameInputProcessor processor;
    ///
    private static RoomView currentRoomView; /// save
    private VesselRender vesselRender;
    private ProjectileRenderer  projectileRenderer;
    private SpriteBatch  batch;
    private OrthographicCamera  camera;
    private ExtendViewport  viewport;
    private ExtendViewport uiViewport;
    private float stateTime = 0f;
    private Stage stage;
    private Stack mainStack;
    private HUD hud;

    public GameView(Game game){
         processor = new GameInputProcessor(Game.getVessel());
        GameView.game = new GameController(game , processor);
    }

    public GameView(){
         processor = new GameInputProcessor(Game.getVessel());
        GameView.game = new GameController(new Game(),processor);
    }



    @Override
    public void show() {

        projectileRenderer = new ProjectileRenderer();
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(1028, 960,camera);

        uiViewport = new ExtendViewport(1080, 960);
        stage = new Stage(uiViewport);

        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {

                if (keycode == Input.Keys.ESCAPE) {
                    if(!GameController.isPaused()){
                        mainStack.add(new PauseMenu());
                        GameController.setPaused(true);
                    }
                    else{
                        GameController.setPaused(false);
                        mainStack.clearChildren();
                    }
                }
                else if(keycode == Input.Keys.I){
                    if(!GameController.isPaused()){
                        mainStack.add(new Inventory());
                        GameController.setPaused(true);
                    }
                    else{
                        GameController.setPaused(false);
                        mainStack.clearChildren();
                    }
                }
                return super.keyDown(event, keycode);
            }
        });
        mainStack = new Stack();
        mainStack.setFillParent(true);
        stage.addActor(mainStack);


        hud = new HUD();
        stage.addActor(hud);
        currentRoomView = new RoomView(Game.getCurrent_room());
        processor.setVessel(Game.getVessel());
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(processor);
        Gdx.input.setInputProcessor(multiplexer);
        processor.setGame(game.getGame());



            /// here , a vessel is initialized , a room is initialized



        vesselRender = new VesselRender();
        batch = new SpriteBatch();


        camera.update();
      //  batch.setProjectionMatrix(camera.combined);
        //viewport.setScreenPosition(0 , 0);

    }

    float winScreenTime = 8;
    @Override
    public void render(float delta) {
       Gdx.gl.glClearColor(0, 0, 0, 1);
       Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateTime += delta;

        if(Game.getCurrent_room().currentState == Room.State.VICTORY && winScreenTime==8){
            mainStack.add(new VictoryMenu());
        }
        else if(Game.getCurrent_room().currentState == Room.State.VICTORY){
          if(winScreenTime <= 0){
              mainStack.clearChildren();
              Game.getCurrent_room().currentState = Room.State.NORMAL;
              winScreenTime = 8;
          }
          else winScreenTime -= delta;
        }

        /// ---------------RENDERING------------------------
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        currentRoomView.renderBackGround(camera,batch);
        vesselRender.render(batch , stateTime);
        for (Projectile x : game.getGame().getProjectiles()){
            projectileRenderer.render(batch , x);
        }
        batch.end();
        currentRoomView.renderForeground(camera);
        updateCamera();
        camera.update();


        stage.act(delta);
        stage.draw();


        /// --------MAIN BRAIN OF THE GAME-----------
      if(!GameController.isPaused())  game.Update(stateTime , delta);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height , false);
        uiViewport.update(width, height, true);
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
        vesselRender.dispose();

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
            if(Game.getCurrent_room().currentState == Room.State.BOSS_FIGHT){
                Rectangle rect = Game.getCurrent_room().bossArea;
                camera.position.x = rect.x + rect.width / 2f;
            }
            camera.update();
        }



    public Stage getStage() {
        return stage;
    }

    public Stack getMainStack() {
        return mainStack;
    }


}
