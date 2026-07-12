package com.mygame.game.models.details;

import com.badlogic.gdx.utils.Json;
import com.mygame.game.controller.data.SaveManager;
import com.mygame.game.models.Game;
import com.mygame.game.models.entities.Entity;
import com.mygame.game.models.entities.boss.FalseKnight;
import com.mygame.game.models.skill.Projectile;

import java.util.ArrayList;

public class Achievement {
    ArrayList<String> DefeatedEnemies = new ArrayList<>() ;
    boolean bossDefeated = false;
    /// Time
    boolean killWithVengefulSprit = false;
    public enum Type{SpeedRun, TrueHunter, Vengeful, GameCompletion, BossDefeat}
    public ArrayList<Type> achievements =  new ArrayList<Type>();
    Json json =  new Json();

    public ArrayList<String> getDefeatedEnemies() {
        return DefeatedEnemies;
    }

    public void setDefeatedEnemies(ArrayList<String> defeatedEnemies) {
        DefeatedEnemies = defeatedEnemies;
    }

    public boolean isBossDefeated() {
        return bossDefeated;
    }

    public void setBossDefeated(boolean bossDefeated) {
        this.bossDefeated = bossDefeated;
    }

    public boolean isKillWithVengefulSprit() {
        return killWithVengefulSprit;
    }

    public void setKillWithVengefulSprit(boolean killWithVengefulSprit) {
        this.killWithVengefulSprit = killWithVengefulSprit;
    }


    public void observe(Game game){
        if(!achievements.contains(Type.TrueHunter)){
            observeDefeatedEnemies();
        }
        if(!achievements.contains(Type.SpeedRun)){
            ///
        }
        if(!achievements.contains(Type.Vengeful)){
            Vengeful(game);
        }
        if(!achievements.contains(Type.BossDefeat)){
            BossDefeated();
        }
        if(!achievements.contains(Type.SpeedRun)){
            speedRun(game);
        }
    }

    private void Vengeful(Game game){
        for (Projectile p : game.getProjectiles()){
            if(p.isProved()){
                achievements.add(Type.Vengeful);
            }
        }
    }

    private void BossDefeated(){
        for (Entity e : Game.getCurrent_room().getEnemies()) {
            if(e instanceof FalseKnight && !e.isAlive()){
                achievements.add(Type.GameCompletion);
                achievements.add(Type.BossDefeat);
            }
        }
    }

    private void speedRun(Game game){
        if(achievements.contains(Type.BossDefeat)){
            if(SaveManager.save.timePlay <= 180) achievements.add(Type.SpeedRun);
        }
    }

    private void observeDefeatedEnemies(){
        if(DefeatedEnemies.size() >= 4) return;


       for (Entity x : Game.getCurrent_room().getEnemies()){
           if(!x.isAlive() && !DefeatedEnemies.contains(x.getName())){
               DefeatedEnemies.add(x.getName());
           }
       }
       if(DefeatedEnemies.size() >= 4){
           achievements.add(Type.TrueHunter);
       }

    }
}
