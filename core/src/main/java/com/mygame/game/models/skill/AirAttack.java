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
    boolean isAttacking = false;


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
            self.setVelocityX(0);
            self.setVelocityY(0);
            return true;
        }
        else if(!isAttacking){
            isAttacking = true;
            dx = x + Game.getVessel().getWidth()/2 - self.getX();
            dy = y + Game.getVessel().getHeight()/2 - self.getY();
            setSpeeds(self , dx , dy);
        }
        boolean reached = (Math.abs(self.getX() - x) < 16) && (Math.abs(self.getY() - y) < 16);
        return !reached;
    }

    @Override
    public void reset() {
        remaining = delay;
        isAttacking = false;
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
