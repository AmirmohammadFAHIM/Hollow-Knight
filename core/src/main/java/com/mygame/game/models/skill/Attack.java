package com.mygame.game.models.skill;

import com.mygame.game.models.Game;
import com.mygame.game.models.entities.aiEnemies.AiEnemy;

public interface Attack {

    public boolean attack(AiEnemy enemy , Game game);

    public void setTime();

    public float getCoolDown();

    public boolean isEndless();
}
