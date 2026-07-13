package com.mygame.game.view;

import com.badlogic.gdx.audio.Music;

public class AudioManager {
    private static AudioManager audioManager =  new AudioManager();
    private AudioManager() {

    }
    private Music currentMusic;
    private Music nextMusic;

    private float fadeDuration = 1.5f;
    private float fadeTimer = 0f;
    private boolean isFading = false;

    public static AudioManager getAudioManager() {
        return audioManager;
    }

    public static void setAudioManager(AudioManager audioManager) {
        AudioManager.audioManager = audioManager;
    }

    private enum FadeState { NONE, FADING_OUT, FADING_IN }
    private FadeState state = FadeState.NONE;

    public void update(float delta) {
        if (!isFading) return;

        switch (state) {
            case FADING_OUT:
                fadeTimer += delta;
                float volume = 1f - (fadeTimer / fadeDuration);
                if (volume <= 0) {
                    volume = 0;
                    currentMusic.stop();
                    currentMusic = nextMusic;
                    currentMusic.setVolume(0);
                    currentMusic.play();
                    state = FadeState.FADING_IN;
                    fadeTimer = 0;
                }
                currentMusic.setVolume(volume);
                break;

            case FADING_IN:
                fadeTimer += delta;
                float newVolume = fadeTimer / fadeDuration;
                if (newVolume >= 1f) {
                    newVolume = 1f;
                    isFading = false;
                    state = FadeState.NONE;
                }
                currentMusic.setVolume(newVolume);
                break;
        }

        currentMusic.setVolume(volume);
    }

    public static float volume = 1f;

    public void changeMusic(Music newMusic) {
        if (currentMusic == null) {
            currentMusic = newMusic;
            currentMusic.setVolume(1f);
            currentMusic.play();
            return;
        }

        this.nextMusic = newMusic;
        this.isFading = true;
        this.state = FadeState.FADING_OUT;
        this.fadeTimer = 0;
    }
}
