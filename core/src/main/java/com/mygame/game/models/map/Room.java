package com.mygame.game.models.map;

import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygame.game.models.Game;

import java.util.ArrayList;

public class Room {
    private String name;
    private ArrayList<SolidBlock> blocks;
    private MapLayers layers;

    public Room( MapLayers layers , String name){
        this.name = name;
        blocks = new ArrayList<>();
        MapManager.setBlocks(layers , this);
    }


    public void checkCollisions(){
        Rectangle knightBounds = Game.getVessel().getBounds();
        boolean collision = false;
        for (SolidBlock block : blocks) {
            if(block.getBlock().overlaps(knightBounds)){
                collision = true;
                /// TO DO: See how the knight overlaps with the block : vertical or horizontal
                if(knightBounds.x > block.getBlock().x){
                    if(knightBounds.getCenter(new Vector2()).y > block.getBlock().y){
                        Game.getVessel().setIs_ground(true);
                        Game.getVessel().setVelocityY(0); ///we're on the ground baby
                    }
                    else {
                        Game.getVessel().setIs_ground(false);
                        Game.getVessel().setVelocityY(0); /*the wall has stopped the knight , so the
                        gravity will pull it back to the ground*/
                    }
                }
            }
        }

        if(!collision){
            Game.getVessel().setIs_ground(false);
        }


    }

    public ArrayList<SolidBlock> getBlocks() {
        return blocks;
    }
}
