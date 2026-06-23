package com.mygame.game.view;

import com.badlogic.gdx.maps.tiled.TiledMap;

public class RoomView {
    private TiledMap map;

    public  RoomView(TiledMap map) {
        this.map = map;
    }

    public TiledMap getMap() {
        return map;
    }
}
