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

        // ۱. کلید حل مشکل: به جریان انداختن زمان برای انیمیشن‌ها!
        stateTime += delta;

        // ۲. تنظیم انیمیشنِ درست برای همین فریم
        setAnimation();



        // ۳. آپدیت فیزیک
        if(state == Entity_States.DEATH_LANDING && is_grounded){
            setState(Entity_States.DEAD_END);
            return false;
        }
       if(state == Entity_States.DEATH_LANDING){
           super.update_physics(delta , Game.getCurrent_room().getBlocks());
           return false;
       }

        if(Hurt(delta)){
            return true;
        }

        // ۴. روشن کردن رادار
        detect();

        // ۵. مدیریت منطقِ کمبوها
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

        // ۶. جادوی زنجیره کردن (Combo Chain)
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
        if(dx < range && dy < 10){
          if(!angerStates.contains(state) && remaining <= 0)  setState(Entity_States.START_SKILL);
          else if(!angerStates.contains(state)){
              remaining -= Gdx.graphics.getDeltaTime();
          }

        }
        else if(angerStates.contains(state)){
            setState(Entity_States.NORMAL);
        }


    }

    @Override
    protected void combat(Game game) {
        if(!attack.attack(this , game)){
            remaining = cooldown;
            setState(Entity_States.END_ATTACK);
        }
    }
}
