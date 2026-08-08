package com.mygame.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygame.game.controller.GameController;
import com.mygame.game.controller.UiManager;

public class GuidMenu extends Table {

    public GuidMenu() {
        this.setFillParent(true);
        this.center();

        // اضافه کردن بک‌گراند تاریک در صورتی که بازی متوقف (Pause) شده باشد
        if (GameController.isPaused()) {
            this.setBackground(UiManager.style.getDrawable("bgDark"));
        }

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

        this.add(new Label("CONTROLS", titleStyle)).padBottom(30).colspan(2).row();

        // دکمه‌های اصلی
        addGuideRow(this, "JUMP", "SPACE", guideStyle, keyStyle);
        addGuideRow(this, "SLASH", "K", guideStyle, keyStyle);
        addGuideRow(this, "VENGEFUL SPIRIT", "J", guideStyle, keyStyle);
        addGuideRow(this, "SOUL SCREAM", "J + W", guideStyle, keyStyle);
        addGuideRow(this, "DASH", "U", guideStyle, keyStyle);

        // بخش چیت‌کدها
        this.row().padTop(10);
        this.add(new Label("CHEAT CODES", titleStyle)).padBottom(20).colspan(2).row();

        addGuideRow(this, "BOSS ROOM TELEPORT", "ALT", guideStyle, keyStyle);
        addGuideRow(this, "GHOST MODE", "SHIFT", guideStyle, keyStyle);
        addGuideRow(this, "HP CHEAT", "ALT + H", guideStyle, keyStyle);
        addGuideRow(this, "SOUL CHEAT", "ALT + S", guideStyle, keyStyle);
        addGuideRow(this, "INSTA KILL", "CTRL + ALT", guideStyle, keyStyle);
        addGuideRow(this, "GOD MODE", "CTRL + G", guideStyle, keyStyle);

        this.row().padTop(30);

        // دکمه بازگشت با استایل بازی
        TextButton backBtn = new TextButton("BACK", UiManager.skin);
        this.add(backBtn).colspan(2);

        // مدیریت کلیک روی دکمه بازگشت
        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                closeMenu();
            }
        });

        // پشتیبانی از دکمه ESCAPE برای خروج از این صفحه
        this.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    closeMenu();
                    return true;
                }
                return false;
            }
        });
    }

    private void addGuideRow(Table table, String action, String key, Label.LabelStyle actionStyle, Label.LabelStyle keyStyle) {
        table.add(new Label(action, actionStyle)).padRight(40).right();
        table.add(new Label(key, keyStyle)).left();
        table.row().padBottom(15);
    }

    private void closeMenu() {
        if (GameController.isPaused()) {
            UiManager.mainStack.clearChildren();
            UiManager.mainStack.add(new PauseMenu());
        } else {
            // بازگشت به منوی اصلی اگر در صفحه MainScreen هستیم
            UiManager.getGameView().getMainStack().clearChildren();
            UiManager.setScreen(new MainScreen());
        }
    }
}
