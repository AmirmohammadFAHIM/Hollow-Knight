package com.mygame.game.models.skill;

import com.badlogic.gdx.Gdx;
import com.mygame.game.models.Game;
import com.mygame.game.models.entities.aiEnemies.AiEnemy;

import java.util.Random;

public class Laser {
    Projectile laser;
    float LaserTime;
    float remainingLaserTime;
    float speedTime;
    float remainingSpeedTime;
    float enragedSpeed = 60;




    public boolean attack(AiEnemy self, Game game) {
        boolean laser = laserFinished();
        boolean speed = speedFinished();
        if(laser && speed){
            return false;
        }
        else if(laser){
            if(remainingSpeedTime <= 0){
                remainingSpeedTime = speedTime;
            }
            speed(self);
        }
        else{
            laser(self);
        }

        return true;
    }

    private void speed(AiEnemy self) {
        boolean right = Game.getVessel().getX() > self.getX();
        self.setVelocityX(enragedSpeed * (right ? 1 : -1));
    }

    public void setTime() {
        remainingLaserTime = LaserTime;
    }

    public float getCoolDown() {
        return 0;
    }

    public void laser(AiEnemy self){
        Random rand = new Random();
        float xStart = self.getX() + (self.isRight()? self.getWidth() : 0);
        float yStart = self.getY() + self.getHeight() / 2;
        float yEnd = rand.nextFloat(Game.getVessel().getY() - 40 ,  Game.getVessel().getY() + 40);
        float xEnd = Game.getVessel().getVelocityX();

    }


    private boolean laserFinished(){
        if(remainingLaserTime <= 0){
            return true;
        }
        remainingLaserTime -= Gdx.graphics.getDeltaTime();
        return false;
    }

    private boolean speedFinished(){
        if(remainingSpeedTime <= 0){
            return true;
        }
        remainingSpeedTime -= Gdx.graphics.getDeltaTime();
        return false;
    }


}
