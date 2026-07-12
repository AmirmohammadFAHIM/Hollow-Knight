package com.mygame.game.models.map;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.mygame.game.models.entities.Entity;

import java.util.ArrayList;

public class Room {
    private String name;
    private ArrayList<SolidBlock> blocks =  new ArrayList<>();
    private TiledMap  map;
    private ArrayList<Entity> enemies = new ArrayList<>();
    private ArrayList<Spike> spikes = new ArrayList<>();
    ArrayList<Door>  doors = new ArrayList<>();
    public enum State{NORMAL,BOSS_FIGHT,VICTORY}
    public State currentState = State.NORMAL;


    public Room(TiledMap map , String name){
        this.name = name;
        blocks = new ArrayList<>();
        this.map = map;


    }

    public Room(){

    }

    public Rectangle bossArea;
    public void setDoors(){
      MapLayer layer =  map.getLayers().get("BossRoom");
      if(layer == null) return;
        Rectangle rect = ((RectangleMapObject) layer.getObjects().get("BossRoom")).getRectangle();
        bossArea = rect;
      Door left = new Door(rect.x , 200 , 80 , 180);
      Door right = new Door(rect.x + rect.width - 80 , 200 , 80 , 180);
      doors.add(left);
      doors.add(right);
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

    public ArrayList<Spike> getSpikes() {
        return spikes;
    }

    public ArrayList<Door> getDoors() {
        return doors;
    }
}
