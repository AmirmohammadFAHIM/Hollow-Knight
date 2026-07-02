package com.mygame.game.models.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygame.game.models.entities.aiEnemies.Move;
import com.mygame.game.models.entities.aiEnemies.NormalMove;
import com.mygame.game.models.skill.Laser;
import com.mygame.game.models.skill.Attack;
import com.mygame.game.models.skill.Rage;
import com.mygame.game.models.skill.Skill;

public enum ENEMIES {

    Crystallized(100 , 50 , 0 , 0){

    },

    HUSK_DANDY(100 , 50 , 0 , 0);


    ENEMIES(float range , float hp , float width , float height ){
        this.range = range;
        this.hp = hp;
        this.width = width;
        this.height = height;
        this.move = new NormalMove();
    }

    ENEMIES(float range , float hp , float width , float height , Attack attack ,
            Skill skill){
        this.range = range;
        this.hp = hp;
        this.width = width;
        this.height = height;
        this.skill = skill;
        this.attack = attack;

    }


    float range;
    float hp;
    float width;
    float height;
    Skill skill;
    Attack attack;
    Move move;
    Animation<TextureAtlas.AtlasRegion> walk;
    Animation<TextureAtlas.AtlasRegion> attackStart;
    Animation<TextureAtlas.AtlasRegion> attackLung;
    Animation<TextureAtlas.AtlasRegion> attackEnd;
    Animation<TextureAtlas.AtlasRegion> skillStart;
    Animation<TextureAtlas.AtlasRegion> skillLung;
    Animation<TextureAtlas.AtlasRegion> skillEnd;
    Animation<TextureAtlas.AtlasRegion> deathAir;
    Animation<TextureAtlas.AtlasRegion> deathLand;



    public float getRange() {
        return range;
    }

    public float getHp() {
        return hp;
    }

    public Skill getSkill() {
        return skill;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Attack getAttack() {
        return attack;
    }
}
