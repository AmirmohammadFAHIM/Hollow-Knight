package com.mygame.game.models;


import com.mygame.game.view.VesselRender;

public class Vessel {
    private static float vertical_speed = 0.1f;
    private static float horizontal_speed = 0.1f;
    private static float dash_speed = 0.1f;
    private static float dash_cooldown = 1f;
    private float remaining_dash_time;
    private float x = 100;
    private float y = 100;
    private float velocity ;
    private float width;
    private float height;
    private float hp;
    private boolean is_ground = true;
    private boolean right = false;
    private boolean double_jump = true;
    private States state = States.IDLE;
    private States previous_state = States.IDLE;


    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

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

    public States getState() {
        return state;
    }

    public void setState(States state) {
        this.state = state;
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

    public void update(float state_time , float delta){

        update_physics(delta);


        /// ------------------DASH STATE , MOST PARTICULAR ONE----------------------

        if(state == States.DASH){
            dash_cooldown -= delta;
            if(dash_cooldown <= 0){
                dash_cooldown = 1;
                if(is_ground){
                    state = States.IDLE; ///dash ended on ground
                }
                else {
                    state = States.FALLING; ///dash ended on air
                }
            }
            else return;
        }

        /// --------------------OTHER STATES-------------------------

        if(state == States.FALLING && is_ground){ /// falling ended , time to land
            state = States.LANDING;
        }

        if(!is_ground && state != States.WALL_SIDE){
            state = States.FALLING;
        }

       if(state == States.LANDING){
           if(VesselRender.getCurrentAnimation().isAnimationFinished(state_time)){
               state = States.IDLE;
           }
       }
       else if(state == States.START_FOCUS){
          if(VesselRender.getCurrentAnimation().isAnimationFinished(state_time)) state = States.FOCUS;
       }






    }


    private void update_physics(float delta){
        if(state == States.RUNNING){
           if(right) x += horizontal_speed * delta;
           else x -= horizontal_speed * delta;
        }
        else if(state == States.DASH){
            x += dash_speed * delta;
        }
    }
}
