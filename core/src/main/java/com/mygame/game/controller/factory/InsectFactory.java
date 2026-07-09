package com.mygame.game.controller.factory;

import com.badlogic.gdx.math.Vector2;
import com.mygame.game.models.entities.ENEMIES;
import com.mygame.game.models.entities.Entity;
import com.mygame.game.models.entities.aiEnemies.AiEnemy;
import com.mygame.game.models.entities.aiEnemies.SkilledAiEnemy;
import com.mygame.game.models.entities.boss.FalseKnight;
import com.mygame.game.models.entities.linearEnemies.LinearEnemies;
import com.mygame.game.models.entities.linearEnemies.LinearEnemy;

public class InsectFactory {

    public Entity createInsect(String name , int type , Vector2 position){
        return switch (type) {
            case 0 -> LinearEnemy(name, position);
            case 1 -> AiEnemy(name, position);
            case 2 -> SkilledAiEnemy(name, position);
          //  case 3 -> boss(position);
            default -> null;
        };
    }

    private Entity LinearEnemy(String name , Vector2 position){
        LinearEnemies type = LinearEnemies.valueOf(name.toUpperCase());
        return new LinearEnemy(type , position.x, position.y);
    }

    private Entity AiEnemy(String name , Vector2 position){
        ENEMIES type = ENEMIES.valueOf(name.toUpperCase());
        return new AiEnemy(type ,position.x, position.y);
    }

    private Entity SkilledAiEnemy(String name ,  Vector2 position){
        ENEMIES type =  ENEMIES.valueOf(name.toUpperCase());
        return new SkilledAiEnemy(type , position.x, position.y);
    }

    private Entity  boss(Vector2 position){
        Entity e = new FalseKnight(position.x, position.y);

        return e;
    }
}
