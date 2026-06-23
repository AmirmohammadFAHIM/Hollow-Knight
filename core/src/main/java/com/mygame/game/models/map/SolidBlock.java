package com.mygame.game.models.map;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;

public class SolidBlock {
    private Rectangle block;
    private boolean lethal;


    SolidBlock(RectangleMapObject block , boolean lethal){
        this.block = block.getRectangle();
        this.lethal = lethal;
    }

    public SolidBlock(RectangleMapObject block){
        this.block = block.getRectangle();
      //  this.lethal = block.getProperties().get("lethal", Boolean.class);
    }

    public Rectangle getBlock() {
        return block;
    }

    public void setBlock(Rectangle block) {
        this.block = block;
    }

    public boolean isLethal() {
        return lethal;
    }

    public void setLethal(boolean lethal) {
        this.lethal = lethal;
    }

}
