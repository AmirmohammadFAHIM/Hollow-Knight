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
        remaining = delay;
        this.MaxSpeed = maxSpeed;
    }
    @Override
    public boolean attack(AiEnemy self, Game game) {

        if(remaining > 0){
            x = Game.getVessel().getX() + Game.getVessel().getWidth()/2;
            y = Game.getVessel().getY()  + Game.getVessel().getHeight()/2;
            remaining -= Gdx.graphics.getDeltaTime();
            self.setVelocityX(0);
            self.setVelocityY(0);
            return true;
        }
        else if(!isAttacking){
            System.out.println("Locked! mission : \n" +
                "x : " + x + " \ny : " + y);
            isAttacking = true;
            dx = x - self.getX();
            dy = y - self.getY();
            setSpeeds(self , dx , dy);
        }
        System.out.println("dx " + Math.abs(self.getX() - x) + " dy " + Math.abs(self.getY() - y));
        boolean reached = (Math.abs(self.getX() - x) < 30) && (Math.abs(self.getY() - y) < 30);
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
