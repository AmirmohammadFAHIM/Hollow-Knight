package com.mygame.game.view;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygame.game.models.details.Save;

import java.util.ArrayList;

public class LoadingMenu extends Table {
    private LoadCard slot1;
    private LoadCard slot2;
    private LoadCard slot3;
    private LoadCard slot4;
    public LoadingMenu() {
        FileHandle file = new FileHandle("data/save1.json");
        slot1 = new LoadCard(1 , !file.exists());
        file =  new FileHandle("data/save2.json");
        slot2 = new LoadCard(2 , !file.exists());
        file =  new FileHandle("data/save3.json");
        slot3 = new LoadCard(3 , !file.exists());
        file =  new FileHandle("data/save4.json");
        slot4 = new LoadCard(4 , !file.exists());

        this.setFillParent(true);
        this.center();
        this.defaults().size(600 , 120);
        this.defaults().pad(10);
        this.add(slot1).row();
        this.add(slot2).row();
        this.add(slot3).row();
        this.add(slot4).row();

       // this.debug();
    }
}
