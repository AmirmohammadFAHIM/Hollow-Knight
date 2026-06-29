package com.mygame.game.models.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygame.game.models.entities.NPC;

import java.util.ArrayList;

public class Room {
    private String name;
    private ArrayList<SolidBlock> blocks =  new ArrayList<>();
    private TiledMap  map;
    private ArrayList<NPC> enemies = new ArrayList<>();


    public Room(TiledMap map , String name){
        this.name = name;
        blocks = new ArrayList<>();
        this.map = map;
        //MapManager.setBlocks(map , this);
    }

    public Room(){

    }








    public ArrayList<SolidBlock> getBlocks() {
        return blocks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBlocks(ArrayList<SolidBlock> blocks) {
        this.blocks = blocks;
    }

    public TiledMap getMap() {
        return map;
    }

    public void setMap(TiledMap map) {
        this.map = map;
    }

    public ArrayList<NPC> getEnemies() {
        return enemies;
    }

    public void setEnemies(ArrayList<NPC> enemies) {
        this.enemies = enemies;
    }
}
