package com.mygame.game.models.skill;

import com.badlogic.gdx.Gdx;
import com.mygame.game.models.Game;
import com.mygame.game.models.entities.Entity;
import com.mygame.game.models.entities.aiEnemies.AiEnemy;

public class AirAttack implements Attack {

    final float cooldown = 4;
    float MaxSpeed;
    float delay;
    float remaining;
    float dx;
    float dy;
    float x;
    float y;


    public AirAttack(float delay , float maxSpeed) {
        this.delay = delay;
        this.MaxSpeed = maxSpeed;
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

            setSpeeds(self , dx , dy);
            boolean reached = (Math.abs(self.getX() - x) < 12) && (Math.abs(self.getY() - y) < 12);
            return !reached;
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

    @Override
    public boolean isEndless() {
        return false;
    }

    private void setSpeeds(AiEnemy self, float dx , float dy){
        // محاسبه فاصله کل بین پشه و نایت
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        if (distance > 0) {
            // محاسبه سرعت استاندارد در هر دو جهت
            self.setVelocityX((dx / distance) * MaxSpeed);
            self.setVelocityY((dy / distance) * MaxSpeed);
        }
    }
}
