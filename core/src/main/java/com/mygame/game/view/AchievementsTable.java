package com.mygame.game.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.mygame.game.controller.UiManager;
import com.mygame.game.controller.data.SaveManager;
import com.mygame.game.models.details.Achievement;

import java.awt.*;

public class AchievementsTable extends Table {
    Array<Image> images = new Array<Image>();

	public AchievementsTable(){
        super();
        setFillParent(true);
        loadImages();
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = UiManager.skin.font;
        labelStyle.fontColor = Color.WHITE;
        String[] lables = {"Speed Run" , "True Hunter" , " Vengeful Spirit", "Game Completion" , "Boss Defeat"};

        int i = 0;
        for (Achievement.Type type : Achievement.Type.values()){
            boolean unlocked = SaveManager.achievements.achievements.contains(type);
            Image image = this.images.get(i);
            image.setColor(1,1,1, unlocked ? 1 : 0.5f);
            Label label = new Label(lables[i] , labelStyle);
            label.setColor(1,1,1, unlocked ? 1 : 0.5f);
            this.add(image).pad(10);
            this.add(label).row();
            i++;
        }
    }


    private void loadImages(){
        for (Achievement.Type type : Achievement.Type.values()){
            images.add(new Image(new Texture("menus/achievements/"+type.toString()+".png")));
        }
    }



}
