package com.mygame.game.models.map;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PointMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.mygame.game.models.Game;
import com.mygame.game.models.entities.LinearEnemies;
import com.mygame.game.models.entities.LinearEnemy;

import java.util.ArrayList;

public class MapManager {
    private static ArrayList<Room> chunk = new ArrayList<>();
    public static void loadChunk(String chunk){
        MapManager.chunk.clear();
        TmxMapLoader loader = new TmxMapLoader();
            for (int i = 0; i < 1; i++) {
                Room room = new Room();
                room.setMap(loader.load("maps/" + chunk +i+ "/" + chunk + i + ".tmx"));
                room.setName(chunk + i);
                setBlocks(room.getMap() , room);
             loadEnemies(room);
                MapManager.chunk.add(room);
            }
       // System.out.println(MapManager.chunk.get(0));
        //return MapManager.chunk.getFirst();
    }

    private static void loadEnemies(Room room){ /// you get the index from the POINT object's fields(transition point)
        /// TO DO : LOAD ENEMIS ONE BY ONE

        PointMapObject spawnPoint = (PointMapObject) room.getMap().getLayers().
            get("Collisions").getObjects().get("tiktik");
        if(room.getName().equals("CityOfTears0")){
            LinearEnemy tiktik = new LinearEnemy(LinearEnemies.TIKTIK , spawnPoint.getProperties().get("x" , Float.class) ,
                spawnPoint.getProperties().get("y" , Float.class));
            room.getEnemies().add(tiktik);
        }
    }



    private static void setBlocks(TiledMap map , Room room){
        for (MapObject obj : map.getLayers().get("Collisions").getObjects().getByType(
            RectangleMapObject.class
        )){

            room.getBlocks().add(new SolidBlock((RectangleMapObject) obj));
        }
    }



    public static Room loadRoom(int index){
        return  chunk.get(index);
    }

    /// when we call this function? when we go into a transition area

   public static void transition(MapObject object){
        boolean new_chunk = object.getProperties().get("new_chunk", Boolean.class);
        if(new_chunk){
            MapManager.loadChunk(object.getProperties().get("new_chunk", String.class));
            Game.setCurrent_room(loadRoom(0));
        }
         else{
            Game.setCurrent_room(loadRoom(object.getProperties().get("room_index", Integer.class)));
         }
    }


}
