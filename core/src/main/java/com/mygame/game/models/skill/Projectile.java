package com.mygame.game.models.skill;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.mygame.game.models.Game;
import com.mygame.game.models.entities.Entity;


public class Projectile {
    enum States{START , MOVING , END}
    States state = States.START;
        Rectangle bounds;
        float speed;
        float range;
        boolean right;
        boolean proved = false;
        boolean timer;
        boolean enemy;
       public float stateTime = 0;
    final private Animation<TextureAtlas.AtlasRegion> start;
    final private Animation<TextureAtlas.AtlasRegion> moving;
    final private Animation<TextureAtlas.AtlasRegion> end;
    public Animation<TextureAtlas.AtlasRegion> currAnim;

    public Projectile(ProjectileTypes type , boolean right , float x , float y) {
            this.speed = type.speed;
            this.range = type.range;
            this.right = right;
            bounds = new Rectangle(x , y , type.width , type.height);
            start = type.getStart();
            moving = type.getMoving();
            end = type.getEnd();
            currAnim = start;
            this.enemy = type.enemy;
            this.timer = type.timer;
    }

    public void move(Game game){
            stateTime += Gdx.graphics.getDeltaTime();
            float dx = speed * Gdx.graphics.getDeltaTime();
            range -= dx;
            bounds.x += dx * (right ? 1 : -1);
            if(range <= 0){
                proved = true;
            }
             checkCollision(game);
            if(timer) timerProjectile(Gdx.graphics.getDeltaTime());

            if(proved && state == States.MOVING){
                stateTime = 0;
                state = States.END;
                currAnim = end;
            }
            else if(currAnim.isAnimationFinished(stateTime)){
                if(state == States.END){
                    game.getProjectiles().removeValue(this, true);
                }
                else if(state == States.START){
                    state = States.MOVING;
                    currAnim = moving;
                    stateTime = 0;
                }

            }

    }

    float t = 3.5f;
    private void timerProjectile(float delta){
        if(t <= 0){
            proved = true;
        }
        else{
            t -= delta;
        }
    }


    private void checkCollision(Game game){
       if(enemy) {
           for (Entity x : Game.getCurrent_room().getEnemies()) {
               if (this.bounds.overlaps(x.getBounds())) {
                   x.setHurt(true);
                   /// to do : deal the damage to the enemy
                   this.proved = true;
                   break;
               }
           }
       }
       else{
          if(!Game.getVessel().hurt && bounds.overlaps(Game.getVessel().getBounds())){
           Game.getVessel().setHurt(true);
           Game.getVessel().setHp(Game.getVessel().getHp() - 1);
          if(!timer) this.proved = true;
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
