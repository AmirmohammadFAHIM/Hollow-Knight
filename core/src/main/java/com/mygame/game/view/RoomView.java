package com.mygame.game.view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygame.game.models.entities.EntityRenderer;
import com.mygame.game.models.entities.LinearEnemy;
import com.mygame.game.models.entities.NPC;
import com.mygame.game.models.map.Room;

import java.util.ArrayList;

public class RoomView {
    private Room room;
    private OrthogonalTiledMapRenderer renderer ;
    private ArrayList<EntityRenderer> renderers = new ArrayList<>();


    public void render(OrthographicCamera camera , SpriteBatch batch) {
        renderer.setView(camera);
        renderer.render();

        for (EntityRenderer renderer : renderers) {
            renderer.render(batch , camera);
        }
    }
    public  RoomView(Room room) {
        this.room = room;
        for (NPC c : room.getEnemies()){
                EntityRenderer x = new EntityRenderer();
                x.setEntity(c);
                renderers.add(x);
        }
        renderer = new OrthogonalTiledMapRenderer(room.getMap());
    }

    public Room getRoom() {
        return room;

    }
    public void setRoom(Room room) {}

}
