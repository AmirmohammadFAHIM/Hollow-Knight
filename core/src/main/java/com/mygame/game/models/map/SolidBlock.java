package com.mygame.game.models.map;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;

public class SolidBlock {
    private Rectangle block;
    private boolean lethal;
    private boolean deadly;

    SolidBlock(RectangleMapObject block , boolean lethal, boolean deadly){
        this.block = block.getRectangle();
        this.lethal = lethal;
        this.deadly = deadly;
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

    public boolean isDeadly() {
        return deadly;
    }

    public void setDeadly(boolean deadly) {
        this.deadly = deadly;
    }
}
