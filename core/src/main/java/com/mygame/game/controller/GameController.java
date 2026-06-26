package com.mygame.game.controller;

import com.mygame.game.models.FireBall;
import com.mygame.game.models.Game;
import com.mygame.game.models.States;

public class GameController {
    private Game game;
    private GameInputProcessor  gameInputProcessor;
    public GameController(Game game , GameInputProcessor processor) {
        this.game = game;
        this.gameInputProcessor = processor;

    }


    public void Update(float state_time ,float delta){


        if(Game.getVessel().getState() != States.DASH)  gameInputProcessor.processInput(delta);
        Game.getVessel().update(delta);

        game.getCurrent_room().checkCollisions();

        /*for (FireBall x : game.getFireballs()){
            x.move(game);
        }*/
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
