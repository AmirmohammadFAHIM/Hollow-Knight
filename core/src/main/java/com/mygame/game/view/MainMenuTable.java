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
    MainScreen screen;
    public  MainMenuTable(MainScreen mainScreen){
        this.screen = mainScreen;
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


        newGame.setTouchable(Touchable.enabled);

        TextButton achievements  = new TextButton("Achievements", style);
        achievements.setTouchable(Touchable.enabled);

        TextButton options = new  TextButton("Settings", style);
        options.setTouchable(Touchable.enabled);

        TextButton exit = new TextButton("Exit", style);
        exit.setTouchable(Touchable.enabled);

        TextButton guide = new TextButton("Guide", style);



        //  button.setSize(100 , 50);
        this.defaults().padBottom(15);

        this.add(newGame).align(Align.center).row();
        this.add(options).align(Align.center).row();
        this.add(achievements).align(Align.center).row();
        this.add(guide).align(Align.center).row();
        this.add(exit).center().row();

        addListener(options , guide , exit , newGame, achievements);

    }


    private void addListener(TextButton options , TextButton guide , TextButton exit ,
                             TextButton newGame , TextButton achievements){
        options.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screen.getRootTable().clearChildren();
                screen.getRootTable().addActor(new OptionMenu());
            }
        });

        newGame.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screen.getRootTable().clearChildren();
                screen.getRootTable().add(new LoadingMenu());
            }
        });

        achievements.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screen.getRootTable().clearChildren();
                screen.getRootTable().add(new AchievementsTable());
            }
        });
    }
}
