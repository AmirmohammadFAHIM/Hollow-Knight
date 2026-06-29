package com.mygame.game.controller;

import com.mygame.game.models.FireBall;
import com.mygame.game.models.Game;
import com.mygame.game.models.States;
import com.mygame.game.models.entities.NPC;

public class GameController {
    private Game game;
    private GameInputProcessor  gameInputProcessor;
    public GameController(Game game , GameInputProcessor processor) {
        this.game = game;
        this.gameInputProcessor = processor;

    }


    /// In this function we need to do these things :
    /*
    * process the inputs
    * update the knight : update physics , update states & collisions , do the skills if needed
    * update the enemies : update physics , update states & collisions , do the skills if needed
    * */

    public void Update(float state_time ,float delta){


        if(Game.getVessel().getState() != States.DASH)  gameInputProcessor.processInput(delta);
        Game.getVessel().update(delta , game);
        for (NPC c :Game.getCurrent_room().getEnemies()){
           if(c.isAlive()) c.update(delta , Game.getCurrent_room().getBlocks());
        }

    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public GameInputProcessor getGameInputProcessor() {
        return gameInputProcessor;
    }

    public void setGameInputProcessor(GameInputProcessor gameInputProcessor) {
        this.gameInputProcessor = gameInputProcessor;
    }
}
