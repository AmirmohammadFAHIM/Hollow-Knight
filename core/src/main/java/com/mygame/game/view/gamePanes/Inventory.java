package com.mygame.game.view.gamePanes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygame.game.controller.UiManager;

public class Inventory extends Table {
    public Inventory() {
        this.setFillParent(true);
        CharmView quickSlash = new CharmView("Quick Slash");
        CharmView quickFocus = new CharmView("Quick Focus");
        CharmView HeavyBlow = new CharmView("Heavy Blow");
        CharmView SoulCatcher = new CharmView("Soul Catcher");
        CharmView Dashmaster =  new CharmView("Dashmaster");
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.fontColor = Color.WHITE;
       // labelStyle.font = UiManager.style.getFont("font");
      //  Label label = new  Label("Inventory", labelStyle);
        Image image = new Image(new Texture("menus/fluer.png"));
        this.setBackground(UiManager.style.getDrawable("bgDark"));
        this.top();
       // this.add(label).colspan(2).row();
        Table bg = new Table();
        bg.add(image);
        this.add(bg).row();
        this.add(quickSlash);
        this.add(Dashmaster);
        this.add(quickFocus).row();
        this.add(HeavyBlow);
        this.add(SoulCatcher);

this.debug();


    }
}
