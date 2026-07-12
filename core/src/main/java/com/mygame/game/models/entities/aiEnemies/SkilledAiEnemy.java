package com.mygame.game.models.entities.aiEnemies;

import com.badlogic.gdx.Gdx;
import com.mygame.game.models.Game;
import com.mygame.game.models.entities.ENEMIES;
import com.mygame.game.models.entities.Entity_States;
import com.mygame.game.models.skill.Skill;

import java.util.ArrayList;

public class SkilledAiEnemy extends AiEnemy{
    public SkilledAiEnemy(ENEMIES type, float x, float y) {
        super(type, x, y);
        cooldown = type.getSkill().getCoolDown();
        this.skill = type.getSkill();
        remaining = 0;
        angerStates.add(Entity_States.Attack);
        angerStates.add(Entity_States.START_SKILL);
        angerStates.add(Entity_States.START_ATTACK);
        angerStates.add(Entity_States.Skill);
        angerStates.add(Entity_States.END_SKILL);
        angerStates.add(Entity_States.END_ATTACK);
    }
    Skill skill;
    ArrayList<Entity_States> angerStates = new ArrayList<>();


    @Override
    public boolean update(float delta, Game game) {

        stateTime += delta;

        setAnimation();


        if(state == Entity_States.DEAD_END) return false;

        if(state == Entity_States.DEATH_LANDING && is_grounded){
            setState(Entity_States.DEAD_END);
            return false;
        }
       if(state == Entity_States.DEATH_LANDING){
           super.update_physics(delta , Game.getCurrent_room().getBlocks());
           return false;
       }
       update_physics(delta , Game.getCurrent_room().getBlocks());

        if(Hurt(delta)){
            return true;
        }

        detect();

        if(state == Entity_States.Skill){
            if(!skill.execute(this , game)){
                setState(Entity_States.END_SKILL);
            }
        }
        else if(state == Entity_States.Attack){
            combat(game);
        }
        else if(state == Entity_States.NORMAL || state == Entity_States.IDLE){
            movingLogic.move(this);
        }

        if(state.hasNextState() && currentAnimation.isAnimationFinished(stateTime)){
            if (state != Entity_States.Attack && state != Entity_States.Skill) {

                if (state == Entity_States.END_SKILL) {
                    setState(Entity_States.START_ATTACK);
                }
                else {
                    setState(state.getNextState());
                }

            }
        }

        return true;
    }


    @Override
    public void detect() {
        /// if detected , set the state to Skill , not attack!
        float dx = Math.abs(Game.getVessel().getX() - this.x);
        float dy = Math.abs(Game.getVessel().getY() - this.y);
        //System.out.println("range :" + range + "\n dx :" + dx + "\n dy :" + dy);
        if(dx < range && dy < 30){
            if(state == Entity_States.Attack || state == Entity_States.START_SKILL){
                this.right = Game.getVessel().getX() > this.x;
                if((right && velocityX < 0) || (!right && velocityX > 0)) velocityX *= -1;
            }
          if(!angerStates.contains(state) && remaining <= 0)  setState(Entity_States.START_SKILL);
          else if(!angerStates.contains(state)){
              remaining -= Gdx.graphics.getDeltaTime();
          }

        }



    }

    @Override
    protected void combat(Game game) {
        if(!attack.attack(this , game)){
            System.out.println("combat finished , setted the cooldown , and ended the attack ");
            System.out.println("vX " + velocityX);
            remaining = cooldown;
            setState(Entity_States.END_ATTACK);
        }
    }
}
