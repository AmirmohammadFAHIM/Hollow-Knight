package com.mygame.game.view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygame.game.models.map.Room;

public class RoomView {
    private Room room;
    private OrthogonalTiledMapRenderer renderer ;


    public void render(OrthographicCamera camera) {
        renderer.setView(camera);
        renderer.render();
    }
    public  RoomView(Room room) {
        this.room = room;
        renderer = new OrthogonalTiledMapRenderer(room.getMap());
    }

    public Room getRoom() {
        return room;

    }
    public void setRoom(Room room) {}

}
