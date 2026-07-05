package com.mygame.game.controller;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PointMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.mygame.game.models.Game;
import com.mygame.game.models.States;
import com.mygame.game.models.Vessel;
import com.mygame.game.models.entities.Entity;
import com.mygame.game.models.entities.Entity_States;
import com.mygame.game.models.map.MapManager;
import com.mygame.game.models.skill.Projectile;

import java.util.Objects;

public class GameController {
    private Game game;
    private GameInputProcessor  gameInputProcessor;
    private static boolean paused = false;
    public GameController(Game game , GameInputProcessor processor) {
        this.game = game;
        this.gameInputProcessor = processor;


    }

    public static boolean isPaused() {
        return paused;
    }
    public static void setPaused(boolean paused) {
        GameController.paused = paused;
    }

    /// In this function we need to do these things :
    /*
    * process the inputs
    * update the knight : update physics , update states & collisions , do the skills if needed
    * update the enemies : update physics , update states & collisions , do the skills if needed
    * */

    public void Update(float state_time ,float delta){



        if(Game.getVessel().getState() != States.DASH)  gameInputProcessor.processInput(delta);
        Game.getVessel().update(delta , game);
        for (Entity c :Game.getCurrent_room().getEnemies()){
           if(c.getState() != Entity_States.DEAD_END) c.update(delta , game);
        }



        transition();
    }



    private void transition(){
        Vessel knight = Game.getVessel();
        MapLayer transitionLayer = Game.getCurrent_room().getMap().getLayers().get("transition");
        for (MapObject mapObject : transitionLayer.getObjects()){
            if(mapObject instanceof RectangleMapObject &&
                Objects.equals(mapObject.getProperties().get("type", String.class), "Door")){
                RectangleMapObject rect = (RectangleMapObject) mapObject;
                if(rect.getRectangle().overlaps(knight.getBounds())){
                    if(mapObject.getProperties().get("newChunk" , Boolean.class)){
                        String chunkName = mapObject.getProperties().get("ChunkName" , String.class);
                        MapManager.loadChunk(chunkName ,
                            mapObject.getProperties().get("ChunkRoomCount" , Integer.class));
                    }

                    int index = mapObject.getProperties().get("Room" , Integer.class);
                    MapManager.loadRoom(index);
                    spawn();
                }



            }
        }
    }


    public void spawn(){
        PointMapObject spawnPoint = (PointMapObject) Game.getCurrent_room().getMap().getLayers()
            .get("Collisions").getObjects().get("SpawnPoint");
        float x = spawnPoint.getProperties().get("X", Float.class);
        float y = spawnPoint.getProperties().get("Y", Float.class);
        Game.getVessel().setX(x);
        Game.getVessel().setY(y);
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public GameInputProcessor getGameInputProcessor() {
        return gameInputProcessor;
    }

    public void setGameInputProcessor(GameInputProcessor gameInputProcessor) {
        this.gameInputProcessor = gameInputProcessor;
    }

    private void updateProjectiles(){
        for (Projectile p : game.getProjectiles()){
            p.move(game);
        }
    }
}
