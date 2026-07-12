package com.mygame.game.models.map;


import com.badlogic.gdx.math.Rectangle;
import com.mygame.game.models.Game;
import com.mygame.game.models.Vessel;

public class Door {
    private boolean open = true;
    Rectangle bounds;
    public Door(float x , float y , float width , float height){
        bounds = new Rectangle(x,y,width,height);
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        if(open){
           state = State.OPENING;
        }
        else{
            state = State.CLOSING;
        }
        this.open = open;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public void collision(){
        Vessel vessel = Game.getVessel();
        if(!open){
            if(vessel.getBounds().overlaps(bounds)){
                if(vessel.getVelocityX() < 0){
                    vessel.setX(this.bounds.x + this.bounds.width);
                }
                else if(vessel.getVelocityX() > 0){
                    vessel.setX(this.bounds.x);
                }
            }
        }
    }

    public enum State{OPENING,CLOSING,NORMAL}
    State state = State.NORMAL;
}
