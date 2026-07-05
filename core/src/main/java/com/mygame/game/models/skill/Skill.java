package com.mygame.game.models.skill;

import com.mygame.game.models.Game;
import com.mygame.game.models.entities.aiEnemies.AiEnemy;

public interface Skill {
        public boolean execute(AiEnemy self , Game game);

        public int getCoolDown();



}
