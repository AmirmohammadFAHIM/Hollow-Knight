package com.mygame.game.view.gamePanes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygame.game.controller.UiManager;
import com.mygame.game.models.Game;
import com.mygame.game.models.charms.Charm;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class CharmView extends Table {
    private Button button;
    private final String name;

    public CharmView(String name){

        this.name=name;
        Label.LabelStyle[] labelStyle = makeStyles();
        Label CharmName = new Label(name, labelStyle[0]);
        boolean equipped = Game.getVessel().getCharms().containsKey(name);
        Label equip = new  Label(equipped ? "equipped" : "not equipped", labelStyle[1]);
        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
        TextureRegionDrawable up = new
            TextureRegionDrawable(new Texture("charms/" + name + ".png"));
        TextureRegionDrawable down = (TextureRegionDrawable) up.tint(new Color(1,1,1,0.5f));
        ImageButton.ImageButtonStyle imageButtonStyle = new ImageButton.ImageButtonStyle();
        imageButtonStyle.up = up;
        imageButtonStyle.down = down;
        ImageButton imageButton = new ImageButton(imageButtonStyle);
        addListener(imageButton);
        this.top();
        this.defaults().padBottom(7);
        this.add(CharmName).row();
        this.add(imageButton).row();
        this.add(equip);


    }

    private Label.LabelStyle[] makeStyles(){
        Label.LabelStyle labelStyleBig = new Label.LabelStyle();
        labelStyleBig.fontColor = Color.WHITE;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/hollow_knight_font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 22;
        BitmapFont topFont = generator.generateFont(parameter);

        parameter.size = 14;
        BitmapFont bottomFont = generator.generateFont(parameter);

        generator.dispose();
        Label.LabelStyle topTextStyle = new Label.LabelStyle(topFont, Color.WHITE);
        Label.LabelStyle bottomTextStyle = new Label.LabelStyle(bottomFont, Color.LIGHT_GRAY); // مثلاً کمی کم‌رنگ‌تر برای جذابیت
        return  new Label.LabelStyle[]{topTextStyle, bottomTextStyle};
    }

    private void addListener(ImageButton charm){
        charm.addListener(new  ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                HashMap<String , Charm> charms = Game.getVessel().getCharms();
                if(charms.size() < 3){
                    charms.put(name , new Charm(Charm.Type.valueOf
                        (name.replaceAll(" " , "_").toUpperCase())));
                }
            }
        });
    }

}
