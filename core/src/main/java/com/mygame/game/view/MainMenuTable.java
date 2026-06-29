package com.mygame.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygame.game.controller.UiManager;

import java.awt.*;

public class MainMenuTable extends Table {

    public  MainMenuTable(){
        this.setFillParent(true);
        this.top();
        com.badlogic.gdx.scenes.scene2d.ui.Image title =  new Image(new Texture(Gdx.files.internal("menus/title.png")));
        this.add(title).size(400 , 180).align(Align.top).padBottom(50).row();


        TextButton.TextButtonStyle  style = new TextButton.TextButtonStyle();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
            Gdx.files.internal("menus/trajan.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        parameter.color = com.badlogic.gdx.graphics.Color.WHITE;
        parameter.magFilter = Texture.TextureFilter.Linear;
        parameter.minFilter = Texture.TextureFilter.Linear;
        style.font =  generator.generateFont(parameter);
        style.downFontColor = Color.CYAN;
        //style.overFontColor =  Color.GRAY;
        //style.font = new BitmapFont(Gdx.files.internal("menus/CENTURY.TTF"));



        // Label label = new Label("Load Game" , null , "Century");

        TextButton newGame  = new TextButton("Start Game", style);
        newGame.addListener(new  ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                UiManager.setScreen(new GameView());
            }
        });
        newGame.setTouchable(Touchable.enabled);

        TextButton loadGame  = new TextButton("Load Game", style);
        loadGame.setTouchable(Touchable.enabled);

        TextButton options = new  TextButton("Options", style);
        options.setTouchable(Touchable.enabled);

        TextButton exit = new TextButton("Exit", style);
        exit.setTouchable(Touchable.enabled);



        //  button.setSize(100 , 50);
        this.defaults().padBottom(15);
        this.setDebug(true);
        this.add(newGame).align(Align.center).row();
        this.add(loadGame).align(Align.center).row();
        this.add(options).align(Align.center).row();
        this.add(exit).center().row();


    }
}
