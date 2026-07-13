package com.mygame.game.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.Gdx;

public class GuidMenu extends Table {

    public GuidMenu() {
        this.setFillParent(true);
        this.center();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("menus/trajan.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 40;
        BitmapFont titleFont = generator.generateFont(parameter);

        parameter.size = 22;
        BitmapFont guideFont = generator.generateFont(parameter);
        generator.dispose();

        Label.LabelStyle titleStyle = new Label.LabelStyle(titleFont, Color.WHITE);
        Label.LabelStyle guideStyle = new Label.LabelStyle(guideFont, Color.LIGHT_GRAY);
        Label.LabelStyle keyStyle = new Label.LabelStyle(guideFont, Color.GOLD);

        this.add(new Label("CONTROLS", titleStyle)).padBottom(50).colspan(2).row();

        addGuideRow(this, "JUMP", "SPACE", guideStyle, keyStyle);
        addGuideRow(this, "SLASH", "K", guideStyle, keyStyle);
        addGuideRow(this, "VENGEFUL SPIRIT", "J", guideStyle, keyStyle);
        addGuideRow(this, "SOUL SCREAM", "J + W", guideStyle, keyStyle);
        addGuideRow(this, "DASH", "U", guideStyle, keyStyle);

        this.row().padTop(40);
        this.add(new Label("PRESS ESC TO RETURN", new Label.LabelStyle(guideFont, Color.GRAY))).colspan(2);
    }

    private void addGuideRow(Table table, String action, String key, Label.LabelStyle actionStyle, Label.LabelStyle keyStyle) {
        table.add(new Label(action, actionStyle)).padRight(40).right();
        table.add(new Label(key, keyStyle)).left();
        table.row().padBottom(15);
    }
}
