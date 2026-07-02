package com.mygame.game.models.map;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PointMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.mygame.game.controller.factory.InsectFactory;
import com.mygame.game.models.Game;
import com.mygame.game.view.GameView;
import com.mygame.game.view.RoomView;

import java.util.ArrayList;

public class MapManager {
    private static ArrayList<Room> chunk = new ArrayList<>();
    private static InsectFactory factory = new InsectFactory();
    public static void loadChunk(String chunk , int roomNumbers){
        MapManager.chunk.clear();
        TmxMapLoader loader = new TmxMapLoader();
            for (int i = 0; i < roomNumbers; i++) {
                Room room = new Room();
                room.setMap(loader.load("maps/" + chunk +i+ "/" + chunk + i + ".tmx"));
                room.setName(chunk + i);
                setBlocks(room.getMap() , room);
             loadEnemies(room);
                MapManager.chunk.add(room);
            }

    }

    private static void loadEnemies(Room room){ /// you get the index from the POINT object's fields(transition point)
        /// TO DO : LOAD ENEMIES ONE BY ONE

       for (PointMapObject point : room.getMap().getLayers().get("Collisions")
           .getObjects().getByType(PointMapObject.class)) {
           if(point.getProperties().get("Class") == "EnemySpawnPoint"){
               String name = point.getProperties().get("Name").toString();
               int type = Integer.parseInt(point.getProperties().get("Type").toString());
               float x = point.getProperties().get("X" , Float.class);
               float y = point.getProperties().get("Y" , Float.class);
               Vector2  position = new Vector2(x , y);
              try {
                  room.getEnemies().add(factory.createInsect(name, type, position));
              }catch (Exception e){
                  System.out.println("Sometimes in the life I'm too competitive.\n" +
                      "It's good to be competitive");
              }
           }
       }
    }



    private static void setBlocks(TiledMap map , Room room){
        for (RectangleMapObject obj : map.getLayers().get("Collisions").getObjects().getByType(
            RectangleMapObject.class
        )){

            room.getBlocks().add(new SolidBlock(obj));
        }
    }



    public static void loadRoom(int index){
        Game.setCurrent_room(chunk.get(index));
       if(GameView.getCurrentRoomView() != null) GameView.setCurrentRoomView(new RoomView(chunk.get(index)));
    }

    public static InsectFactory getFactory() {
        return factory;
    }

    public static void setFactory(InsectFactory factory) {
        MapManager.factory = factory;
    }

    /// when we call this function? when we go into a transition area




}
