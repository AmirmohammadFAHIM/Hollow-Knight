package com.mygame.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygame.game.models.details.Save;


public class VictoryMenu extends Table {



    public VictoryMenu(Save gameSave) {
        super();
        this.setFillParent(true);

        // ۱. بک‌گراند منو (یک عکس تیره و مه‌آلود یا نیمه شفاف هالو نایتی)
        Texture bgTexture = new Texture("menus/end_game_bg.png");
        this.setBackground(new TextureRegionDrawable(bgTexture));

        // ۲. ساخت فونت‌ها با سایزهای مختلف
        Label.LabelStyle[] fontStyles = createFontStyles();
        Label.LabelStyle titleStyle = fontStyles[0];
        Label.LabelStyle statsStyle = fontStyles[1];

        // ۳. تیتر اصلی منو
        Label titleLabel = new Label("JOURNEY'S END", titleStyle);
        this.add(titleLabel).padBottom(40).colspan(2).row();

        // یک خط تزیینی زیر تیتر (جلوه قشنگ به سبک هالو نایت)
        Image divider = new Image(new Texture("menus/divider.png"));
        this.add(divider).width(400).height(10).padBottom(50).colspan(2).row();

        // ۴. جدول اختصاصی برای آمارها (برای نظم بیشتر)
        Table statsTable = new Table();
        statsTable.defaults().pad(15).left();

        // ردیف اول: زمان بازی
        Image timeIcon = new Image(new Texture("menus/icons/time.png")); // عکس ساعت
        Label timeTitle = new Label("Time Played: ", statsStyle);
        Label timeValue = new Label(formatTime(gameSave.timePlay), statsStyle);
        timeValue.setColor(Color.GOLD); // رنگ طلایی برای مقادیر عددی

        statsTable.add(timeIcon).size(32, 32);
        statsTable.add(timeTitle);
        statsTable.add(timeValue).row();

        // ردیف دوم: دشمنان کشته شده
        Image enemyIcon = new Image(new Texture("menus/icons/knight_hollow.png")); // عکس شمشیر یا ماسک دشمن
        Label enemyTitle = new Label("Enemies Defeated: ", statsStyle);
        Label enemyValue = new Label(String.valueOf(gameSave.enemiesKilled), statsStyle);
        enemyValue.setColor(Color.GOLD);

        statsTable.add(enemyIcon).size(32, 32);
        statsTable.add(enemyTitle);
        statsTable.add(enemyValue).row();

        // ردیف سوم: تعداد دفعات مرگ
        Image deathIcon = new Image(new Texture("menus/icons/death.png")); // عکس ماسک شکسته نایت
        Label deathTitle = new Label("Times Perished: ", statsStyle);
        Label deathValue = new Label(String.valueOf(gameSave.died), statsStyle);
        deathValue.setColor(Color.RED); // رنگ قرمز برای مرگ

        statsTable.add(deathIcon).size(32, 32);
        statsTable.add(deathTitle);
        statsTable.add(deathValue).row();

        // اضافه کردن جدول آمارها به جدول اصلی
        this.add(statsTable).colspan(2).padBottom(60).row();

        // ۵. دکمه‌های ناوبری پایین صفحه
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = fontStyles[1].font;
        buttonStyle.fontColor = Color.WHITE;
        buttonStyle.overFontColor = Color.GOLD; // تغییر رنگ به طلایی وقتی موس روی دکمه میرود

        TextButton mainMenuBtn = new TextButton("Return to Main Menu", buttonStyle);
        mainMenuBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // کدهای برگشتن به منوی اصلی بازی
                Gdx.app.log("Menu", "Going back to Main Menu...");
            }
        });

        this.add(mainMenuBtn).pad(20).colspan(2);
        this.top().padTop(100); // کل منو کمی از بالای صفحه فاصله داشته باشد
    }

    // متد ساخت فونت با دو سایز مختلف از روی فایل Trajan شما
    private Label.LabelStyle[] createFontStyles() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("menus/trajan.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        // فونت بزرگ برای تیتر
        parameter.size = 48;
        BitmapFont titleFont = generator.generateFont(parameter);

        // فونت متوسط برای نوشته‌ها و آمارها
        parameter.size = 20;
        BitmapFont statsFont = generator.generateFont(parameter);

        generator.dispose();

        Label.LabelStyle titleStyle = new Label.LabelStyle(titleFont, Color.WHITE);
        Label.LabelStyle statsStyle = new Label.LabelStyle(statsFont, Color.LIGHT_GRAY);

        return new Label.LabelStyle[]{titleStyle, statsStyle};
    }

    // متد کمکی برای تبدیل ثانیه (Float) به فرمت خوشگل HH:MM:SS
    private String formatTime(float totalSeconds) {
        int hours = (int) (totalSeconds / 3600);
        int minutes = (int) ((totalSeconds % 3600) / 60);
        int seconds = (int) (totalSeconds % 60);
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
