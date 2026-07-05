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



    @Override
    public boolean execute(AiEnemy self, Game game) {
       if(remainingLaserTime <= 0){
           setTime();
           return false;
       }
       else{
           laser(self);
           remainingLaserTime -= Gdx.graphics.getDeltaTime();
           return  true;
       }
    }


    public void setTime() {
        remainingLaserTime = LaserTime;
    }

    public int getCoolDown() {
        return 5;
    }

    public void laser(AiEnemy self){
        Random rand = new Random();
        float xStart = self.getX() + (self.isRight()? self.getWidth() : 0);
        float yStart = self.getY() + self.getHeight() / 2;
        float yEnd = rand.nextFloat(Game.getVessel().getY() - 40 ,  Game.getVessel().getY() + 40);
        float xEnd = Game.getVessel().getX();

    }






}
