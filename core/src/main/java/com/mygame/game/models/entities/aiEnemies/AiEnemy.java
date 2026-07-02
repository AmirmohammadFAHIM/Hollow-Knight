package com.mygame.game.models.entities.aiEnemies;

import com.badlogic.gdx.Gdx;
import com.mygame.game.models.Game;
import com.mygame.game.models.entities.ENEMIES;
import com.mygame.game.models.entities.Entity;
import com.mygame.game.models.entities.Entity_States;
import com.mygame.game.models.skill.Attack;

public class AiEnemy extends Entity {

    public AiEnemy(ENEMIES type , float x , float y){
        this.type = type;
        range = type.getRange();
        this.hp = type.getHp();
        this.skill = type.getSkill();
        this.cooldown = skill.getCoolDown();

    }
    ENEMIES type;
    private float range;
    Attack skill;
    float cooldown;
    float remaining;
    Move movingLogic;

    @Override
    public void update(float delta, Game game) {
        super.update(delta, game);

        detect();

    if(state == Entity_States.Attack){
        combat(game);
    }
    else if(state == Entity_States.NORMAL){
        movingLogic.move(this);
    }


    }

    public void detect(){
        if(Math.abs(Game.getVessel().getX() - this.x) < range &&
        Math.abs(Game.getVessel().getY() - this.y) < 50){
           if(remaining <= 0) setState(Entity_States.Attack);
           else remaining -= Gdx.graphics.getDeltaTime();
        }
        else setState(Entity_States.NORMAL);
    }


    protected void combat(Game game) {
        boolean done = skill.attack(this , game);
        if(!done){
            skill.setTime();
            setState(Entity_States.NORMAL);
            remaining = skill.getCoolDown();
        }
    }
}
