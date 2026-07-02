package com.mygame.game.models.entities.aiEnemies;

import com.mygame.game.models.Game;
import com.mygame.game.models.entities.ENEMIES;
import com.mygame.game.models.entities.Entity_States;
import com.mygame.game.models.skill.Skill;

public class SkilledAiEnemy extends AiEnemy{
    public SkilledAiEnemy(ENEMIES type, float x, float y , Skill skill) {
        super(type, x, y);
        this.skill = skill;
    }
    Skill skill;


    @Override
    public void update(float delta, Game game) {
        super.update_physics(delta , Game.getCurrent_room().getBlocks());

        if(Hurt(delta)){
            return;
        }

        if(state == Entity_States.Skill){
            ///  skill.exectue , till it's really executed , cooldown , hitting wall etc ... .
        }
        else if(state == Entity_States.Attack){
            combat(game);
        }
        else if(state == Entity_States.NORMAL){
            movingLogic.move(this);
        }


        if(state.hasNextState() && currentAnimation.isAnimationFinished(stateTime)){
            state = state.getNextState();
        }
    }


    @Override
    public void detect() {
        /// if detected , set the state to Skill , not attack!
    }
}
