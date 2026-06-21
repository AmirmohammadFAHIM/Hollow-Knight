package com.mygame.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.mygame.game.models.States;
import com.mygame.game.models.Vessel;
import com.mygame.game.models.Game;

public class GameInputProcessor extends InputAdapter {
    private com.mygame.game.models.Game game;
    private Vessel vessel;

    public GameInputProcessor(Game game, Vessel vessel) {
        this.game = game;
        this.vessel = vessel;
    }

    public void processInput(float delta){ /// for holding buttons : A D F
        if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.D)){
            vessel.setRight(!Gdx.input.isKeyPressed(Input.Keys.A));
            if(vessel.isIs_ground()){
                vessel.setState(States.RUNNING);
            }
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.F)){
            States state = vessel.getState();
            if(vessel.isIs_ground()){
                vessel.setState(States.START_FOCUS);
            }
        }

    }

    @Override
    public boolean keyDown(int keycode) { ///  for pressing buttons : space K U
         if(keycode == Input.Keys.U){ //Dash state
             vessel.setPrevious_state(vessel.getState());
            vessel.setState(States.DASH);
            vessel.setRemaining_dash_time(Vessel.getDash_cooldown());
        }
        else if(keycode ==  Input.Keys.SPACE){ //Jump & Double Jump
            if(vessel.getState() == States.JUMPING
            || vessel.getState() == States.FALLING){
                vessel.setPrevious_state(States.JUMPING);
                vessel.setState(States.DOUBLE_JUMP);
                vessel.setDouble_jump(false);
            }
            else if(vessel.getState() == States.WALL_SIDE){
                vessel.setPrevious_state(States.WALL_SIDE);
                vessel.setState(States.WALL_JUMP);
            }
            else {
                vessel.setPrevious_state(vessel.getState());
                vessel.setState(States.JUMPING);
                vessel.setIs_ground(false);
            }

        }
        else if(keycode ==  Input.Keys.K){
                vessel.setState(States.SLASH);
         }





        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.F){
            if(vessel.getState() == States.FOCUS ||  vessel.getState() == States.START_FOCUS){
                vessel.setState(States.IDLE);
            }
        }
       return super.keyUp(keycode);
    }

}


