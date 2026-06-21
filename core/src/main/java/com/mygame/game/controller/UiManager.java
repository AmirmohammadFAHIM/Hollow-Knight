package com.mygame.game.controller;

import com.badlogic.gdx.Screen;
import com.mygame.game.Main;

public class UiManager {
    private static Main  main;

    public UiManager(Main main) {
        this.main = main;
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

}
