package com.mygame.game.controller;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PointMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.mygame.game.controller.data.SaveManager;
import com.mygame.game.models.Game;
import com.mygame.game.models.States;
import com.mygame.game.models.Vessel;
import com.mygame.game.models.details.Save;
import com.mygame.game.models.entities.Entity;
import com.mygame.game.models.entities.Entity_States;
import com.mygame.game.models.map.Door;
import com.mygame.game.models.map.MapManager;
import com.mygame.game.models.map.Room;
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



        if(!Game.getVessel().getState().getPriority() || !Game.getVessel().hurt)  gameInputProcessor.processInput(delta);
        Game.getVessel().update(delta , game);
        for (Entity c :Game.getCurrent_room().getEnemies()){
           if(c.getState() != Entity_States.DEAD_END) c.update(delta , game);

        }
        updateProjectiles();


        SaveManager.achievements.observe(game);/// observing achievements

        transition();
        BossRoom(game);

        if(!paused) SaveManager.save.timePlay += delta;
    }



    private void transition(){
        Vessel knight = Game.getVessel();
        MapLayer transitionLayer = Game.getCurrent_room().getMap().getLayers().get("transition");
        if(transitionLayer == null) return;


        for (MapObject mapObject : transitionLayer.getObjects()){
            if(mapObject instanceof RectangleMapObject &&
            mapObject.getProperties().get("type") != null && mapObject.getProperties()
                .get("type").equals("Door")){

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
        float x = spawnPoint.getProperties().get("x", Float.class);
        float y = spawnPoint.getProperties().get("y", Float.class);
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


    private void BossRoom(Game game){
        Vessel vessel = Game.getVessel();
        Rectangle bossRoom = Game.getCurrent_room().bossArea;
        if(bossRoom != null){
            if(vessel.getX() > bossRoom.x + 450 &&
                Game.getCurrent_room().currentState == Room.State.NORMAL){
                Game.getCurrent_room().currentState = Room.State.BOSS_FIGHT;
                for (Door x : Game.getCurrent_room().getDoors()){
                    x.setOpen(false);
                }
            }
            else if(vessel.getX() < bossRoom.x){
                Game.getCurrent_room().currentState = Room.State.NORMAL;
            }

        }

        for (Door door : Game.getCurrent_room().getDoors()){
            door.collision();
        }
    }
}
