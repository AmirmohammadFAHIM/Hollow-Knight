package com.mygame.game.models.skill;

import com.badlogic.gdx.Gdx;
import com.mygame.game.models.Game;
import com.mygame.game.models.entities.aiEnemies.AiEnemy;

import java.util.Random;

public class Laser implements Skill {
    Projectile laser;
    float LaserTime;
    float remainingLaserTime;


    public Laser(float LaserTime){
        this.LaserTime = LaserTime;
        this.remainingLaserTime = LaserTime;
    }


    boolean hit = false;

    @Override
    public boolean execute(AiEnemy self, Game game) {
       if(remainingLaserTime <= 0){
           setTime();
           return false;
       }
       else{
          if(!hit && remainingLaserTime <= 2.8){
              laser(self , game);
              hit = true;
          }
           remainingLaserTime -= Gdx.graphics.getDeltaTime();
           return  true;
       }
    }


    public void setTime() {
        remainingLaserTime = LaserTime;
        hit = false;
    }

    public int getCoolDown() {
        return 5;
    }

    public void laser(AiEnemy self , Game game){
        float x = self.getX() + (self.isRight() ? self.getWidth() : -ProjectileTypes.LASER.width + 70);
        float y = self.getY() + self.getHeight() / 2 - 50;
        for (int i = 0; i < 10; i++) {
            game.getProjectiles().add(new Projectile(ProjectileTypes.LASER,self.isRight(),
                x + i * 120 * (self.isRight() ? 1 : -1) , y));
        }
        //game.getProjectiles().add(new Projectile(ProjectileTypes.LASER , self.isRight() , x , y));
    }






}
