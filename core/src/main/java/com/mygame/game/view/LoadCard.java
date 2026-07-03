package com.mygame.game.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Scaling;
import com.mygame.game.controller.UiManager;
import com.mygame.game.models.details.Save;

public class LoadCard extends Table {
    private boolean New = true;
    private int slot;
    private Image background;
    private float progress;
    public LoadCard(int slot , boolean New){
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


    }

    private void loadBG(){
        if(New){
            background = new Image(new Texture("Area Save Art/Area_Dirtmouth.png"));
            return;
        }

        JsonReader reader = new JsonReader();
        JsonValue chunk = reader.parse("saves/save" +slot+".json");
        String chunkName = chunk.getString("chunkName");
        background = new Image(new Texture("Area_" + chunkName + ".png"));
    }

    private void LoadProgress(){
        if(New){
            progress = 0;
            return;
        }
        JsonReader reader = new JsonReader();
        JsonValue field = reader.parse("saves/save" +slot+".json");
        progress = field.getFloat("progress");
    }


}
