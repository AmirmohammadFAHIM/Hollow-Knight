package com.mygame.game.controller.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.mygame.game.models.Game;
import com.mygame.game.models.details.Save;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SaveManager {
    private static Save save;
    private static Json json = new Json();

    public static boolean load(int slot){
        FileHandle file = Gdx.files.local("data/save" + slot + ".json");
        if(file.exists()){
            save = json.fromJson(Save.class, file.readString());
            return  true;
        }
        else return false;
    }


    public static void save(){

        FileHandle file = Gdx.files.local("data/save" + save.getSlotIndex() + ".json");
        createSave();
        if(file.exists()){
            file.writeString(json.toJson(save), false);
        }

    }

    private static void createSave(){
         save = new Save();
        save.setVesel(Game.getVessel());
        Pattern pattern = Pattern.compile("(?<ChunkName>.*)(?<index>\\d+)");
        Matcher matcher = pattern.matcher(Game.getCurrent_room().getName());
        matcher.find();
        save.setRoomIndex(Integer.parseInt(matcher.group("index")));
        String ChunkName = matcher.group("ChunkName");
    }


    public static Save getSave() {
        return save;
    }
}
