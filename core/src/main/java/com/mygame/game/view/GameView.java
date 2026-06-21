package com.mygame.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygame.game.controller.GameController;
import com.mygame.game.controller.GameInputProcessor;
import com.mygame.game.models.Game;

public class GameView implements Screen {
    private GameController game;
    private VesselRender vesselRender;
    private SpriteBatch  batch;
    private OrthographicCamera  camera;
    private ExtendViewport  viewport;
    private float stateTime = 0f;



    @Override
    public void show() {

        game = new GameController(new Game());
        GameInputProcessor processor = new GameInputProcessor(game.getGame() , Game.getVessel());
        Gdx.input.setInputProcessor(processor);



        vesselRender = new VesselRender();
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(1028, 960,camera);
        camera.position.set( 80 , 60 , 0);
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
