package com.mygame.game.models;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.mygame.game.models.map.SolidBlock;
import com.mygame.game.view.VesselRender;

import java.util.ArrayList;

public class Vessel {
    /// ---------------STATICS-----------------
    private static float vertical_speed = 650f;
    private static float horizontal_speed = 350f;
    private static float dash_speed = 550f;
    private static float dash_cooldown = 0.4f;
    private static float gravity = 5f;
    private float stateTime = 0f;
    /// ---------------FIELDS------------------------
    private float x ;
    private float y ;
    private float velocityY ;
    private float velocityX ;
    private Rectangle bounds;
    private float width = 80;
    private float height = 135;
    private float remaining_dash_time;
    private float hp;
    /// -----------BOOLEANS--------------------
    private boolean is_ground = true;
    private boolean right = false;
    private boolean double_jump = true;
    /// -------------STATES--------------------
    private States state = States.IDLE;
    private States previous_state = States.IDLE;



    public static float getDash_speed() {
        return dash_speed;
    }

    public static void setDash_speed(float dash_speed) {
        Vessel.dash_speed = dash_speed;
    }

    public boolean isIs_ground() {
        return is_ground;
    }

    public void setIs_ground(boolean is_ground) {
        this.is_ground = is_ground;
    }

    public float getHp() {
        return hp;
    }

    public void setHp(float hp) {
        this.hp = hp;
    }

    public boolean isDouble_jump() {
        return double_jump;
    }

    public void setDouble_jump(boolean double_jump) {
        this.double_jump = double_jump;
    }

    public static float getVertical_speed() {
        return vertical_speed;
    }

    public static void setVertical_speed(float vertical_speed) {
        Vessel.vertical_speed = vertical_speed;
    }

    public static float getHorizontal_speed() {
        return horizontal_speed;
    }

    public static void setHorizontal_speed(float horizontal_speed) {
        Vessel.horizontal_speed = horizontal_speed;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getStateTime() {
        return stateTime;
    }

    public States getState() {
        return state;
    }

    public void setState(States state) {
       if(state != this.state){
           previous_state = this.state;
           this.state = state;
           this.stateTime = 0f; ///resetting the state time so animations don't glitch
       }

    }


    public static float getDash_cooldown() {
        return dash_cooldown;
    }

    public static void setDash_cooldown(float dash_cooldown) {
        Vessel.dash_cooldown = dash_cooldown;
    }

    public float getRemaining_dash_time() {
        return remaining_dash_time;
    }

    public void setRemaining_dash_time(float remaining_dash_time) {
        this.remaining_dash_time = remaining_dash_time;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public States getPrevious_state() {
        return previous_state;
    }

    public void setPrevious_state(States previous_state) {
        this.previous_state = previous_state;
    }

    public void update(float delta){

        stateTime += Gdx.graphics.getDeltaTime();

        update_physics(delta , Game.getCurrent_room().getBlocks());


        /// ------------------DASH STATE , MOST PARTICULAR ONE----------------------

        if(state == States.DASH){
            remaining_dash_time -= delta;
            if(remaining_dash_time <= 0){
                remaining_dash_time = dash_cooldown;
                if(is_ground){
                    state = States.IDLE; ///dash ended on ground
                }
                else {
                    state = States.FALLING; ///dash ended on air
                }
                velocityX = 0;
            }
            else return;
        }

        /// --------------------OTHER STATES-------------------------

        if(state == States.FALLING && is_ground){ /// falling ended , time to land
            state = States.LANDING;
            return;
        }

        if(!is_ground && state != States.WALL_SIDE &&
        state != States.JUMPING && state != States.DOUBLE_JUMP){
            state = States.FALLING;
        }

        if(state == States.DOUBLE_JUMP){
            if(VesselRender.getCurrentAnimation().isAnimationFinished(stateTime)){
                state = previous_state;
            }
        }
       if(state == States.LANDING){
           if(VesselRender.getCurrentAnimation().isAnimationFinished(stateTime)){
               state = States.IDLE;
           }
       }
       else if(state == States.START_FOCUS){
          if(VesselRender.getCurrentAnimation().isAnimationFinished(stateTime)) state = States.FOCUS;
       }
       else if (state == States.FIREBALL) {
           if (VesselRender.getCurrentAnimation().isAnimationFinished(stateTime)) {
               state = States.IDLE;
           }
       }
       if(state == States.SLASH){
          if(VesselRender.getCurrentAnimation().isAnimationFinished(stateTime)){
              state =  States.IDLE;
          }

       }


       if(is_ground) velocityY = 0; /// bro we're on the ground !
        else velocityY -= gravity;




    }


    private void update_physics(float delta , ArrayList<SolidBlock> blocks){
        float copy_x = x;
        float copy_y = y;
        x += velocityX * delta;
        bounds.x = x;
       /* for (SolidBlock x : blocks){
            if(x.getBlock().overlaps(bounds)){
                state = States.WALL_SIDE;
                velocityX = 0;
                this.x = copy_x;
                return;
            }
        }*/

        y += velocityY * delta;
        bounds.y = y;

        /* for (SolidBlock y : blocks){
            if(y.getBlock().overlaps(bounds)){
                state = States.IDLE;
                velocityY = 0;
                this.y = copy_y;
            }
        }*/
    }


    public Rectangle getBounds() {
        return bounds;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }

    public float getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public void updateRect() {
        bounds  = new Rectangle(x, y, width, height);
    }
}
