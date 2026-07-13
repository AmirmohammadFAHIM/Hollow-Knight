package com.mygame.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygame.game.controller.GameController;
import com.mygame.game.controller.UiManager;
import com.mygame.game.controller.data.SaveManager;

public class PauseMenu extends Table {

    public  PauseMenu() {


        this.setFillParent(true);
        this.setBackground(UiManager.style.getDrawable("bgDark"));
        Image image = new Image(new Texture(Gdx.files.internal("menus/fluer.png")));

        TextButton continueButton = new TextButton("Continue", UiManager.skin);
        TextButton options =  new TextButton("Options", UiManager.skin);
        TextButton guid =  new TextButton("GUIDE",   UiManager.skin);
        TextButton quit =  new TextButton("Quit & Save",  UiManager.skin);

        this.add(image).colspan(2).row();
        this.defaults().padBottom(20);
        this.add(continueButton).row();
        this.add(options).row();
        this.add(guid).row();
        this.add(quit).row();

        addActionListeners(continueButton , options ,  guid , quit );

    }

    private void addActionListeners(TextButton continueButton , TextButton options,
                                    TextButton guid, TextButton quit ){
        continueButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                PauseMenu.this.remove();
                GameController.setPaused(false);
                super.clicked(event, x, y);
            }
        });

        quit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                /// here we should implement the saving methods(with SaveManager Class)
                SaveManager.save();
                /// ---------------------------------------------
                UiManager.setScreen(new MainScreen());
                super.clicked(event, x, y);
            }
        });

        options.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                UiManager.mainStack.clearChildren();
                UiManager.mainStack.add(new OptionMenu());
            }
        });

        guid.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                UiManager.mainStack.clearChildren();
                UiManager.mainStack.add(new GuidMenu());
            }
        });


    }

}
