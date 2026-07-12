package com.mygame.game.controller.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.objects.PointMapObject;
import com.badlogic.gdx.utils.Json;
import com.mygame.game.models.Game;
import com.mygame.game.models.details.Achievement;
import com.mygame.game.models.details.Save;
import com.mygame.game.models.map.MapManager;
import com.mygame.game.view.GameView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mygame.game.view.GameView.*;

public class SaveManager {
    public static Save save;
    public static Achievement achievements;
    private static final Json json = new Json();
    static {
        json.setUsePrototypes(false);
    }

    public static void loadAchievements(){
        FileHandle file = Gdx.files.local("save/achievements.json");
       if(file.exists()) achievements = json.fromJson(Achievement.class, file.readString());
       else  achievements = new Achievement();
    }

    public static Game load(int slot){
        FileHandle file = Gdx.files.local("data/save" + slot + ".json");
        Game game;
        if(file.exists()){
            save = json.fromJson(Save.class, file.readString());
            MapManager.loadChunk(save.getChunkName() , 1);
            game = new Game(save.getRoomIndex() , save.getVesel());
        }
        else{
            save = new Save();
            save.setSlotIndex(slot);
            MapManager.loadChunk("CityOfTears", 1);
            game = new Game();

            PointMapObject spawnPoint = (PointMapObject) MapManager.loadRoom(0).getMap().getLayers().get("Collisions").
                getObjects().get("SpawnPoint");
            float x = spawnPoint.getProperties().get("x" , Float.class);
            float y = spawnPoint.getProperties().get("y" , Float.class);

            Game.getVessel().setX(x);
            Game.getVessel().setY(y);
            Game.getVessel().updateRect();

        }
        return game;
    }


    public static void save(){

        FileHandle file = Gdx.files.local("data/save" + save.getSlotIndex() + ".json");
        if(save.getProgress() != 100) save.setProgress(save.getProgress());
        createSave();
            file.writeString(json.toJson(save), false);


        FileHandle achievement = Gdx.files.local("save/achievements.json");

            achievement.writeString(json.toJson(achievements), false);


    }

    private static void createSave(){

        save.setVesel(Game.getVessel());
        Pattern pattern = Pattern.compile("(?<ChunkName>.*)(?<index>\\d+)");
        Matcher matcher = pattern.matcher(Game.getCurrent_room().getName());
        matcher.find();
        save.setRoomIndex(Integer.parseInt(matcher.group("index")));
        String ChunkName = matcher.group("ChunkName");
        save.setChunkName(ChunkName);
    }


    public static Save getSave() {
        return save;
    }
}
