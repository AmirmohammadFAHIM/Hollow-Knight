package com.mygame.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class PauseMenu extends Table {

    public  PauseMenu() {
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


        TextButton continueButton = new TextButton("Continue", style);
        TextButton options =  new TextButton("Options", style);
        TextButton guid =  new TextButton("GUIDE", style);
        TextButton quit =  new TextButton("Quit & Save", style);

        this.defaults().padBottom(40);
        this.add(continueButton);
        this.add(options);
        this.add(guid);
        this.add(quit);
    }
}
