package com.mygame.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PointMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygame.game.controller.GameController;
import com.mygame.game.controller.GameInputProcessor;
import com.mygame.game.models.Game;
import com.mygame.game.models.map.MapManager;

public class GameView implements Screen {
    private GameController game;
    private RoomView currentRoomView;
    private VesselRender vesselRender;
    private SpriteBatch  batch;
    private OrthographicCamera  camera;
    private ExtendViewport  viewport;
    private float stateTime = 0f;



    @Override
    public void show() {

        try {
            MapManager.loadChunk("CityOfTears");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
           game = new GameController(new Game() /* here can be the idea for saves */);
       }catch (Exception e){
           System.out.println(e.getMessage());
           return;
       }
        GameInputProcessor processor = new GameInputProcessor(game.getGame() , Game.getVessel());
        Gdx.input.setInputProcessor(processor);
        currentRoomView = new RoomView(MapManager.loadRoom(0));



        vesselRender = new VesselRender();
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(1028, 960,camera);
        PointMapObject spawnPoint = (PointMapObject) currentRoomView.getMap().getLayers().get("Collision").
            getProperties().get("spawnPoint");
        float x = spawnPoint.getProperties().get("x" , Float.class);
        float y = spawnPoint.getProperties().get("y" , Float.class);
        camera.position.set( x , y , 0);
        Game.getVessel().setX(x);
        Game.getVessel().setY(y);
        camera.update();
      //  batch.setProjectionMatrix(camera.combined);
        //viewport.setScreenPosition(0 , 0);

    }

    @Override
    public void render(float delta) {
       Gdx.gl.glClearColor(0, 0, 0, 1);
       Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateTime += delta;

        /// --------MAIN BRAIN OF THE GAME-----------
        game.Update(stateTime , delta);
        /// ---------------RENDERING------------------------
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        vesselRender.render(batch , stateTime);
        camera.position.set( Game.getVessel().getX() , Game.getVessel().getY(), 0);
        camera.update();
        batch.end();
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
}
