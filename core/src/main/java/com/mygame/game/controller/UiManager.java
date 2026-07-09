package com.mygame.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygame.game.Main;
import com.mygame.game.view.GameView;

public class UiManager {
    private static Main  main;
    private static GameView gameView;
    public static Skin  style;
    public static TextButton.TextButtonStyle skin;
    public static FreeTypeFontGenerator generator;
    public static FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    static {

        skin = new TextButton.TextButtonStyle();
         generator = new FreeTypeFontGenerator(
            Gdx.files.internal("menus/trajan.ttf"));
         parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        parameter.color = com.badlogic.gdx.graphics.Color.WHITE;
        parameter.magFilter = Texture.TextureFilter.Linear;
        parameter.minFilter = Texture.TextureFilter.Linear;
        skin.font =  generator.generateFont(parameter);
        skin.downFontColor = Color.CYAN;


        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("ui/Hollow Knight skin.atlas"));

        // ۲. ساخت اسکین و متصل کردن اطلس به آن
        style = new Skin();
        style.addRegions(atlas);
        BitmapFont myCustomFont = new BitmapFont(Gdx.files.internal("menus/trajan.fnt"));

        // ۴. تزریق فونت جدید به اسکین با نام‌های "default" و "title"
        // با این کار، وقتی فایل جیسون لود بشه، به جای فونت‌های قدیمی از فونت شما استفاده می‌کنه!
        //style.add("default", myCustomFont);
        //style.add("title", myCustomFont);

        // ۳. لود کردن فایل جیسون (توی این مرحله لایبرری خودش فونت‌ها و استایل‌ها رو می‌سازه)
        style.load(Gdx.files.internal("ui/Hollow Knight skin.json"));

    }

    public UiManager(Main main) {
        this.main = main;
    }
    public static void setGameView(GameView gameView){
        UiManager.gameView = gameView;
    }
    public static Main getMain() {
        return main;
    }
    public static void setMain(Main main) {
        UiManager.main = main;
    }

    public  static  void setScreen(Screen screen) {
        main.setScreen(screen);
    }

    public static GameView getGameView() {
        return gameView;
    }
}
