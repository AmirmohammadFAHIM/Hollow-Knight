package com.mygame.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.mygame.game.controller.UiManager;

public class OptionMenu extends Table {
    public OptionMenu() {
        super();
        setFillParent(true);

        this.top();

        Texture b = new Texture(Gdx.files.internal("menus/fluer.png"));
        b.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Image title = new Image(b);
        title.setScaling(Scaling.fit);

        Label lable = new Label("Volume" , UiManager.style , "title");
        Label reset = new Label("Reset" , UiManager.style , "title");


        Texture resetTex = new Texture("menus/reset.png");
        resetTex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TextureRegionDrawable a = new TextureRegionDrawable(new TextureRegion(resetTex));
        ImageButton button = new ImageButton(a);

        Slider music = new Slider(0 , 1 , 0.1f ,false , UiManager.style , "default-horizontal");
        music.setValue(0.5f);



        this.add(title).width(500).padTop(30).row();

        Table centerMenu = new Table();
        centerMenu.defaults().padBottom(15);

        centerMenu.add(lable).right().padRight(20);
        centerMenu.add(music).width(250).left().row();
        centerMenu.add(reset).right().padRight(20);
        centerMenu.add(button).size(50 , 50).left().row();


        this.add(centerMenu).expandY().center();

        this.debug();
    }
}
