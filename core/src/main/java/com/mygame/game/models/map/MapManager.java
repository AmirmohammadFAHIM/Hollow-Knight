package com.mygame.game.models.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

public class MapManager {
    private static ArrayList<TiledMap> chunk;
    public static TiledMap loadChunk(String chunk) throws Exception{
        MapManager.chunk = new ArrayList<>();
        TmxMapLoader loader = new TmxMapLoader();
            for (int i = 0; i < 3; i++) {
                MapManager.chunk.add(loader.load("maps/" + chunk + i + ".tmx"));
            }
        return MapManager.chunk.getFirst();
    }

    public static TiledMap loadRoom(int index){ /// you get the index from the POINT object's fields(transition point)
        return  MapManager.chunk.get(index);
    }



    public static void setBlocks(MapLayers layers , Room room){
        for (MapObject obj : layers.get("Collision").getObjects().getByType(
            RectangleMapObject.class
        )){

            room.getBlocks().add(new SolidBlock((RectangleMapObject) obj));
        }
    }

}
