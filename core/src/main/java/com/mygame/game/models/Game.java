package com.mygame.game.models;

import com.badlogic.gdx.utils.Array;
import com.mygame.game.models.map.Chunk;
import com.mygame.game.models.map.MapManager;
import com.mygame.game.models.map.Room;
import com.mygame.game.models.skill.Projectile;

public class Game {
    private static Vessel vessel;
    private static Chunk current_chunk;
    private static Room current_room;
    private Array<Projectile> shootables =  new Array<>();

    public  Game(){
        vessel = new Vessel();
        current_chunk = new Chunk();
        MapManager.loadChunk("CityOfTears" , 1);
        MapManager.loadRoom(0);

    }
    public static Vessel getVessel() {
        return vessel;
    }

    public static void setVessel(Vessel vessel) {
        Game.vessel = vessel;
    }

    public Chunk getCurrent_chunk() {
        return current_chunk;
    }

    public void setCurrent_chunk(Chunk current_chunk) {
        Game.current_chunk = current_chunk;
    }

    public static Room getCurrent_room() {
        return current_room;
    }

    public static void setCurrent_room(Room current_room) {
        Game.current_room = current_room;
    }

    public Array<Projectile> getShootables() {
        return shootables;
    }
}
