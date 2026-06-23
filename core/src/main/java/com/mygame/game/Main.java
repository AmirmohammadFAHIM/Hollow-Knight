package com.mygame.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygame.game.controller.UiManager;
import com.mygame.game.view.GameView;
import com.mygame.game.view.MainMenuView;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    private SpriteBatch batch;
    private Texture image;
    private UiManager manager;
    ShapeRenderer shapeRenderer;


    @Override
    public void create() {

        manager = new UiManager(this);
        setScreen(new MainMenuView());
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render() {
        super.render();


    }

    @Override
    public void dispose() {

    }
}
