package com.mygame.game.models.skill;

import com.badlogic.gdx.Gdx;
import com.mygame.game.models.Game;
import com.mygame.game.models.entities.aiEnemies.AiEnemy;

public class AirAttack implements Attack {

    final float cooldown;
    float MaxSpeed;
    float delay;
    float remaining;
    float dx;
    float dy;
    float x;
    float y;


    public AirAttack(float cooldown) {
        this.cooldown = cooldown;
    }
    @Override
    public boolean attack(AiEnemy self, Game game) {

        if(remaining > 0){
            x = Game.getVessel().getX();
            y = Game.getVessel().getY();
            remaining -= Gdx.graphics.getDeltaTime();
        }
        else{
            dx = x - self.getX();
            dy = y - self.getY();
            boolean a = Math.abs(dx) > Math.abs(dy);
            float vX = a ? MaxSpeed : MaxSpeed * dx / dy * (dx > 0 ? 1 : -1 );
            float vY = a ?  MaxSpeed : MaxSpeed * dy / dx * (dy > 0 ? 1 : -1);
            self.setVelocityX(vX);
            self.setVelocityY(vY);
            if(Math.abs(self.getX() - x) < 3 &&  Math.abs(self.getY() - y) < 3){
                return false;
            }
        }
        return true;
    }

    @Override
    public void setTime() {
        remaining = delay;
    }

    @Override
    public float getCoolDown() {
        return cooldown;
    }
}
