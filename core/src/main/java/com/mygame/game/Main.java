package com.mygame.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygame.game.controller.UiManager;
import com.mygame.game.controller.data.SaveManager;
import com.mygame.game.view.AudioManager;
import com.mygame.game.view.MainScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    private SpriteBatch batch;
    private Texture image;
    private UiManager manager;
    ShapeRenderer shapeRenderer;


    @Override
    public void create() {

        new UiManager(this);
        setScreen(new MainScreen());
        shapeRenderer = new ShapeRenderer();
        SaveManager.loadAchievements();
        AudioManager.getAudioManager().changeMusic(Gdx.audio.newMusic(Gdx.files.internal("sfx/main.wav")));
    }

    @Override
    public void render() {
        super.render();


    }

    @Override
    public void dispose() {

    }
}
