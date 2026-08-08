package com.mygame.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.mygame.game.controller.GameController;
import com.mygame.game.controller.UiManager;

public class OptionMenu extends Table {
    public OptionMenu() {
        super();
        setFillParent(true);
        this.top();

        // اضافه کردن بک‌گراند تاریک (اگر بازی پاز باشد)
        if (GameController.isPaused()) {
            this.setBackground(UiManager.style.getDrawable("bgDark"));
        }

        Texture b = new Texture(Gdx.files.internal("menus/fluer.png"));
        b.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Image title = new Image(b);
        title.setScaling(Scaling.fit);

        Label volumeLabel = new Label("Volume", UiManager.style, "title");

        Slider musicSlider = new Slider(0, 1, 0.1f, false, UiManager.style, "default-horizontal");
        musicSlider.setValue(AudioManager.masterVolume); // مقدار فعلی را نشان دهد


        // ساخت دکمه‌های Mute با استفاده از اسکین دکمه‌های بازی
        final TextButton muteMusicBtn = new TextButton(AudioManager.isMusicMuted ? "Music: MUTED" : "Music: ON", UiManager.skin);
        final TextButton muteSfxBtn = new TextButton(AudioManager.isSfxMuted ? "SFX: MUTED" : "SFX: ON", UiManager.skin);
        TextButton backBtn = new TextButton("Back", UiManager.skin);

        this.add(title).width(500).padTop(30).row();

        Table centerMenu = new Table();
        centerMenu.defaults().padBottom(15);

        // چیدمان اسلایدر ولوم
        centerMenu.add(volumeLabel).right().padRight(20);
        centerMenu.add(musicSlider).width(250).left().row();

        // چیدمان دکمه‌های Mute و SFX که جایگزین Reset شده‌اند
        centerMenu.add(muteMusicBtn).colspan(2).fillX().padTop(20).row();
        centerMenu.add(muteSfxBtn).colspan(2).fillX().row();
        centerMenu.add(backBtn).colspan(2).center().padTop(30).row();

        this.add(centerMenu).expandY().center();

        // ------------------ LISTENERS ------------------
        muteMusicBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AudioManager.isMusicMuted = !AudioManager.isMusicMuted;
                muteMusicBtn.setText(AudioManager.isMusicMuted ? "Music: MUTED" : "Music: ON");
            }
        });

        muteSfxBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AudioManager.isSfxMuted = !AudioManager.isSfxMuted;
                muteSfxBtn.setText(AudioManager.isSfxMuted ? "SFX: MUTED" : "SFX: ON");
            }
        });

        musicSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                float value = musicSlider.getValue();
                AudioManager.getAudioManager().setMasterVolume(value);
            }
        });

        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // برگشت به منوی قبلی (بسته به اینکه در بازی هستیم یا در منوی اصلی)
                if (GameController.isPaused()) {
                    UiManager.mainStack.clearChildren();
                    UiManager.mainStack.add(new PauseMenu());
                } else {
                    UiManager.getGameView().getMainStack().clearChildren();
                    UiManager.setScreen(new MainScreen());
                    AudioManager.getAudioManager().changeMusic(UiManager.music);
                }
            }
        });
    }
}
