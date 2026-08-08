package com.mygame.game.view;

import com.badlogic.gdx.audio.Music;

public class AudioManager {
    private static AudioManager audioManager = new AudioManager();

    private AudioManager() {}

    private Music currentMusic;
    private Music nextMusic;

    private float fadeDuration = 1.5f;
    private float fadeTimer = 0f;
    private boolean isFading = false;

    // متغیر صدای کل بازی (قابل تغییر از منوی Options)
    // بهتر است بین 0.0f تا 1.0f باشد
    public static float masterVolume = 1f;

    public static AudioManager getAudioManager() {
        return audioManager;
    }

    private enum FadeState { NONE, FADING_OUT, FADING_IN }
    private FadeState state = FadeState.NONE;

    public void update(float delta) {
        // اگر موزیکی نداریم، نیازی به آپدیت نیست
        if (currentMusic == null) return;

        // اگر در حال Fade نیستیم، همیشه صدای موزیک را با تنظیمات آپشنز هماهنگ کن
        if (!isFading) {
            currentMusic.setVolume(masterVolume);
            return;
        }

        switch (state) {
            case FADING_OUT:
                fadeTimer += delta;
                // درصد Fade را محاسبه می‌کنیم (از 1 به 0)
                float fadeOutPercent = 1f - (fadeTimer / fadeDuration);

                if (fadeOutPercent <= 0) {
                    fadeOutPercent = 0;
                    currentMusic.stop();
                    currentMusic = nextMusic;
                    // صدای شروعِ موزیک جدید صفر است
                    currentMusic.setVolume(0);
                    currentMusic.play();
                    state = FadeState.FADING_IN;
                    fadeTimer = 0;
                } else {
                    // اعمال محو شدن، ضرب در صدای اصلی بازی
                    currentMusic.setVolume(fadeOutPercent * masterVolume);
                }
                break;

            case FADING_IN:
                fadeTimer += delta;
                // درصد Fade را محاسبه می‌کنیم (از 0 به 1)
                float fadeInPercent = fadeTimer / fadeDuration;

                if (fadeInPercent >= 1f) {
                    fadeInPercent = 1f;
                    isFading = false;
                    state = FadeState.NONE;
                }
                // اعمال بلند شدن صدا، ضرب در صدای اصلی بازی
                currentMusic.setVolume(fadeInPercent * masterVolume);
                break;
        }
    }

    public void changeMusic(Music newMusic) {
        // جلوگیری از باگ: اگر موزیک جدید همان موزیک فعلی است، ری‌استارت نکن
        if (currentMusic == newMusic) return;

        if (currentMusic == null) {
            currentMusic = newMusic;
            currentMusic.setVolume(masterVolume);
            currentMusic.play();
            return;
        }

        this.nextMusic = newMusic;
        this.isFading = true;
        this.state = FadeState.FADING_OUT;
        this.fadeTimer = 0;
    }

    // یک متد کمکی برای استفاده در منوی آپشنز
    public void setMasterVolume(float newVolume) {
        // محدود کردن عدد بین صفر و یک برای جلوگیری از کرش شدن صدا
        masterVolume = Math.max(0f, Math.min(1f, newVolume));
    }
}
