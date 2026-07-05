package com.mygame.game.models.skill;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.mygame.game.models.Game;
import com.mygame.game.models.entities.Entity;


public class Projectile {
        Rectangle bounds;
        float speed;
        float range;
        boolean right;
        boolean proved = false;

    public Projectile(ProjectileTypes type , boolean right , float x , float y) {
            this.speed = type.speed;
            this.range = type.range;
            this.right = right;
            bounds = new Rectangle(x , y , type.width , type.height);

    }

    public void move(Game game){
            float dx = speed * Gdx.graphics.getDeltaTime();
            range -= dx;
            bounds.x += dx * (right ? 1 : -1);
            if(range <= 0){
                proved = true;
            }
            checkCollision(game);

    }

    private void checkCollision(Game game){
        for (Entity x : Game.getCurrent_room().getEnemies()){
            if(this.bounds.overlaps(x.getBounds())){
                x.setHurt(true);
                this.proved = true;
                break;
            }
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getRange() {
        return range;
    }

    public void setRange(float range) {
        this.range = range;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isProved() {
        return proved;
    }

    public void setProved(boolean proved) {
        this.proved = proved;
    }
}
