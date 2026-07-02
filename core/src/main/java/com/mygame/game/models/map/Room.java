package com.mygame.game.models.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygame.game.models.entities.Entity;

import java.util.ArrayList;

public class Room {
    private String name;
    private ArrayList<SolidBlock> blocks =  new ArrayList<>();
    private TiledMap  map;
    private ArrayList<Entity> enemies = new ArrayList<>();


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

    public ArrayList<Entity> getEnemies() {
        return enemies;
    }

    public void setEnemies(ArrayList<Entity> enemies) {
        this.enemies = enemies;
    }
}
