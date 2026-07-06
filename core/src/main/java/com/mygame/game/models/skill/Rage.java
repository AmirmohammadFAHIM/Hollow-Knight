package com.mygame.game.models.skill;

import com.badlogic.gdx.Gdx;
import com.mygame.game.models.Game;
import com.mygame.game.models.entities.aiEnemies.AiEnemy;

public class Rage implements Attack {
    boolean endless;
    final float time;
    float speed;
    float t;
    public Rage(float speed  , float time) {
        this.time = time;
        this.t = time;
        this.speed = speed;
        endless = false;
    }
    public Rage(float speed , float time , boolean endless) {
        this.time = time;
        this.endless = endless;
        this.speed = speed;
    }

    @Override
    public boolean attack(AiEnemy self, Game game) {
       if(endless) {
           return endless(self);
       }
       return timer(self);
    }


    private boolean endless(AiEnemy self) {
        if (Math.abs(self.getVelocityX()) != speed) {
            boolean right = Game.getVessel().getX() > self.getX();
            self.setVelocityX(speed * (right ? 1 : -1));
            self.setRight(right);
        }
        return  true;
    }



    private boolean timer(AiEnemy  self) {
        if(t <= 0){
            reset();
            return false;
        }
        else {
            if (Math.abs(self.getVelocityX()) != speed) {
                boolean right = Game.getVessel().getX() > self.getX();
                self.setVelocityX(speed * (right ? 1 : -1));
                self.setRight(right);
            }
            t -= Gdx.graphics.getDeltaTime();
            return true;
        }
    }
    @Override
    public void reset() {
            t = time;
    }

    @Override
    public float getCoolDown() {
        return 0;
    }

    @Override
    public boolean isEndless() {
        return endless;
    }
}
