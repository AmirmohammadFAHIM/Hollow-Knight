package com.mygame.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Scaling;
import com.mygame.game.controller.UiManager;
import com.mygame.game.controller.data.SaveManager;
import com.mygame.game.models.details.Save;

public class LoadCard extends Table {
    private boolean New = true;
    private int slot;
    private Image background;
    private float progress;
    public LoadCard(int slot , boolean New){
        System.out.println(New);
        this.New = New;
        this.slot = slot;
        this.setSize(270 , 85);
        loadBG();
        LoadProgress();
        background.setScaling(Scaling.fill);
        this.background(background.getDrawable());
        TextButton load = new TextButton("Load" , UiManager.skin);
        int percent = (int) (progress * 100);
        Label label = new Label(percent + "%" , UiManager.style);
        this.right();
        this.defaults().padRight(15);
        this.add(label).left();
        this.add(load).right();

        this.setTouchable(Touchable.enabled);
        this.debug();


        this.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                LoadCard This = (LoadCard) event.getListenerActor();
                super.clicked(event, x, y);
                GameView gameView;


                    gameView = new GameView(SaveManager.load(This.slot));

                UiManager.setScreen(gameView);
            }
        });

    }

    private void loadBG(){
        if(New){
            background = new Image(new Texture("Area Save Art/Area_Dirtmouth.png"));
            return;
        }

        JsonReader reader = new JsonReader();


        FileHandle file = Gdx.files.local("data/save" + slot + ".json");

        JsonValue chunk = reader.parse(file);

        String chunkName = chunk.getString("chunkName");
        background = new Image(new Texture("Area save art/Area_" + chunkName + ".png"));
    }

    private void LoadProgress(){
        if(New){
            progress = 0;
            return;
        }

        JsonReader reader = new JsonReader();

        FileHandle file = Gdx.files.local("data/save" + slot + ".json");

        JsonValue field = reader.parse(file);

        progress = field.getFloat("progress");
    }



}
