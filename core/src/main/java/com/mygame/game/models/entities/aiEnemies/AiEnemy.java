package com.mygame.game.models.entities.aiEnemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.mygame.game.models.Game;
import com.mygame.game.models.entities.ENEMIES;
import com.mygame.game.models.entities.Entity;
import com.mygame.game.models.entities.Entity_States;
import com.mygame.game.models.skill.Attack;

public class AiEnemy extends Entity {

    public AiEnemy(ENEMIES type , float x , float y){
        super(type.name() , x , y);
        this.attack = type.getAttack();
        this.type = type;
        range = type.getRange();
        this.hp = type.getHp();
        this.cooldown = attack.getCoolDown();
        this.movingLogic = type.getMove();
        this.width = type.getWidth();
        this.height = type.getHeight();
        this.bounds = new Rectangle(x, y, width, height);
        this.state = Entity_States.NORMAL;
        this.currentAnimation = type.getWalk();

    }
    ENEMIES type;
    private float range;
    Attack attack;
    float cooldown;
    float remaining;
    Move movingLogic;

    @Override
    public void update(float delta, Game game) {
        setAnimation();

        super.update(delta, game);

        detect();

    if(state == Entity_States.Attack){
        combat(game);
    }
    else if(state == Entity_States.NORMAL || state == Entity_States.IDLE){
        movingLogic.move(this);
    }





    }

    public void detect(){
        if(state == Entity_States.START_ATTACK || state == Entity_States.Attack){
            return;
        }
        if(Math.abs(Game.getVessel().getX() - this.x) < range &&
        Math.abs(Game.getVessel().getY() - this.y) < 5){
           if(remaining <= 0) setState(Entity_States.START_ATTACK);
           else remaining -= Gdx.graphics.getDeltaTime();
        }
        else{
            if((state == Entity_States.Attack || state == Entity_States.START_ATTACK
            || state == Entity_States.Skill ||
            state == Entity_States.START_SKILL) && !attack.isEndless()) setState(Entity_States.NORMAL);
        }
    }


    protected void combat(Game game) {
        boolean done = attack.attack(this , game);
        if(!done){
            attack.setTime();
            setState(Entity_States.NORMAL);
            remaining = attack.getCoolDown();
        }
    }


    private void setAnimation(){
        switch (state){
            case NORMAL:
                currentAnimation = type.getWalk();
                break;
            case IDLE:
                currentAnimation = type.getIdle();
                break;
            case DEAD:
                currentAnimation = type.getDeathAir();
                break;
            case DEATH_LANDING:
                currentAnimation = type.getDeathLand();
                break;
            case START_ATTACK:
                currentAnimation = type.getAttackStart();
                break;
            case Attack:
                currentAnimation = type.getAttackLung();
                break;
            case START_SKILL:
                currentAnimation = type.getSkillStart();
                break;
            case Skill:
                currentAnimation = type.getSkillLung();
                break;
            case END_SKILL:
                currentAnimation = type.getSkillEnd();
                break;
        }

    }

    public ENEMIES getType() {
        return type;
    }

    @Override
    public void setState(Entity_States state) {
        super.setState(state);
        if(state == Entity_States.IDLE){
            velocityX = 0;
            velocityY = 0;
        }
    }
}
