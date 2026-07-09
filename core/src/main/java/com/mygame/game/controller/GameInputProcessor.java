package com.mygame.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.mygame.game.models.*;
import com.mygame.game.models.skill.Projectile;
import com.mygame.game.models.skill.ProjectileTypes;
import com.mygame.game.view.VesselRender;

public class GameInputProcessor extends InputAdapter {
    private Vessel vessel;
    private Game game;

    public GameInputProcessor( Vessel vessel) {
        this.vessel = vessel;
    }

    public void processInput(float delta){ /// for holding buttons : A D F
        if(vessel.getState().getPriority()) return;
        if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.D)){
            vessel.setRight(!Gdx.input.isKeyPressed(Input.Keys.A));
            if(vessel.isIs_ground() && vessel.getState() != States.SLASH){
                vessel.setState(States.RUNNING);
            }
            vessel.setVelocityX(Vessel.getHorizontal_speed() *
                (vessel.isRight() ? 1 : -1));
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.F)){
            States state = vessel.getState();
            if(vessel.isIs_ground() && state != States.FOCUS && state != States.START_FOCUS &&
            state != States.FOCUS_GET){
                vessel.setState(States.START_FOCUS);
                vessel.healing_time = vessel.getCharms().containsKey("Quick Focus") ? 1.2f : 1.5f;
            }
        }


    }

    @Override
    public boolean keyDown(int keycode) { ///  for pressing buttons : space K U

        if( vessel.getState().getPriority() ||
        vessel.getState() == States.Death || vessel.hurt) return super.keyDown(keycode);

         if(keycode == Input.Keys.U){ //Dash state
             if(vessel.getRemaining_dash_time() > 0) return super.keyDown(keycode); /// cannot dash
            vessel.setState(States.DASH);
            vessel.setRemaining_dash_time(vessel.getCharms().containsKey("Dashmaster") ? 0.7f : 1.2f);
            vessel.setVelocityX(Vessel.getDash_speed() * (vessel.isRight() ? 1 : -1));
            vessel.setVelocityY(0);

        }
        else if(keycode ==  Input.Keys.SPACE){ //Jump & Double Jump

            if(!vessel.isDouble_jump()) return super.keyDown(keycode);

            if(vessel.getState() == States.JUMPING
            || vessel.getState() == States.FALLING && vessel.isDouble_jump()){
              //  vessel.setPrevious_state(States.JUMPING);
                vessel.setState(States.DOUBLE_JUMP);
                vessel.setDouble_jump(false);
                vessel.setVelocityY(Vessel.getVertical_speed());
            }
            else if(vessel.getState() == States.WALL_SIDE){
              //  vessel.setPrevious_state(States.WALL_SIDE);
                vessel.setState(States.WALL_JUMP);

            }
            else {
                vessel.setPrevious_state(vessel.getState());
                vessel.setState(States.JUMPING);
                vessel.setVelocityY(Vessel.getVertical_speed());
                vessel.setIs_ground(false);
            }


        }
        else if(keycode ==  Input.Keys.K){
            if(vessel.slash_cooldown > 0) return  super.keyDown(keycode);
            if(Gdx.input.isKeyPressed(Input.Keys.W)){
                vessel.setState(States.UP_SLASH);
            }
            else if(Gdx.input.isKeyPressed(Input.Keys.S)){
                vessel.setState(States.DOWN_SLASH);
            }
            else vessel.setState(States.SLASH);
            vessel.slash_cooldown = vessel.getCharms().containsKey("Quick Slash") ? 0.5f : 0.75f;

         }
        else if(keycode == Input.Keys.J){
            if(vessel.getSoul() < 33) return super.keyDown(keycode);

            vessel.setState(States.FIREBALL);
            float x;
             if (vessel.isRight()) {
                 x = vessel.getX() + vessel.getWidth();
             } else {
                 x = vessel.getX() - 260;
             }
             float y = vessel.getY();
            game.getProjectiles().add(new Projectile( ProjectileTypes.VENGFUL_SPIRIT,vessel.isRight() ,x,y ));
            vessel.setSoul(vessel.getSoul() - 33);
         }






        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        if(vessel.getState() == States.DASH) return super.keyUp(keycode);
        if(keycode == Input.Keys.F){
            if(vessel.getState() == States.FOCUS ||  vessel.getState() == States.START_FOCUS){
                vessel.setState(States.IDLE);
                vessel.healing_time = vessel.getCharms().containsKey("Quick Focus") ? 1.2f : 1.5f;
            }
        }
        else if(keycode ==  Input.Keys.A ||
            keycode ==  Input.Keys.D){
            if(vessel.isIs_ground()){
                vessel.setState(States.IDLE);
            }
            vessel.setVelocityX(0);
        }
       return super.keyUp(keycode);
    }


    public void setGame(Game game) {
        this.game = game;
    }

    public void setVessel(Vessel vessel) {
        this.vessel = vessel;
    }
}


