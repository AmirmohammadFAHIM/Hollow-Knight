package com.mygame.game.models;

import com.mygame.game.models.map.Chunk;
import com.mygame.game.models.map.Room;

public class Game {
    private static Vessel vessel;
    private Chunk current_chunk;
    private Room current_room;

    public  Game() {
        vessel = new Vessel();
        current_chunk = new Chunk();
        current_room = new Room(); // It will be replaced by a loading method to load the map
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
        this.current_chunk = current_chunk;
    }

    public Room getCurrent_room() {
        return current_room;
    }

    public void setCurrent_room(Room current_room) {
        this.current_room = current_room;
    }
}
