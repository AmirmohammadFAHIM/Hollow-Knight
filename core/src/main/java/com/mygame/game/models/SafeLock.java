package com.mygame.game.models;

public class SafeLock {

    private float x;
    private float y;

   public SafeLock(){

    }
    public SafeLock(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}
