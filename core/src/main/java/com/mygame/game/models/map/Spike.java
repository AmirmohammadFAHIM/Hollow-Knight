package com.mygame.game.models.map;

import com.badlogic.gdx.math.Rectangle;

public class Spike {
    private Rectangle bounds;
    private boolean pogo;
    public Spike(Rectangle bounds, boolean pogo) {
        this.bounds = bounds;
        this.pogo = pogo;
    }

    public Spike(Rectangle bounds) {
        this.bounds = bounds;
    }
    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public boolean isPogo() {
        return pogo;
    }

    public void setPogo(boolean pogo) {
        this.pogo = pogo;
    }
}
