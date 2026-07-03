package com.mygame.game.models.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygame.game.models.entities.aiEnemies.LazyMove;
import com.mygame.game.models.entities.aiEnemies.Move;
import com.mygame.game.models.entities.aiEnemies.NormalMove;
import com.mygame.game.models.skill.Laser;
import com.mygame.game.models.skill.Attack;
import com.mygame.game.models.skill.Rage;
import com.mygame.game.models.skill.Skill;

public enum ENEMIES {

    Crystallized(100 , 50 , 0 , 0){

    },

    HUSK_HORNHEAD(300 , 100 , 100 , 190){
        {
            move = new LazyMove(3 , 2.5f);
            attack = new Rage(150 , 4 , true);

            /// Initializing animations
            TextureAtlas atlas = new TextureAtlas("enemies/Husk Hornhead/Walk.atlas");
            walk = new Animation<>(0.09f , atlas.findRegions("Walk") , Animation.PlayMode.LOOP);
            atlas = new TextureAtlas("enemies/Husk Hornhead/Idle.atlas");
            Idle = new Animation<>(0.09f , atlas.findRegions("Idle") , Animation.PlayMode.LOOP);
            atlas = new TextureAtlas("enemies/Husk Hornhead/AttackAnticipate.atlas");
            attackStart = new Animation<>(0.09f , atlas.findRegions("Attack Anticipate"));
            atlas = new TextureAtlas("enemies/Husk Hornhead/AttackLung.atlas");
            attackLung = new Animation<>(0.09f , atlas.findRegions("Attack Lunge") , Animation.PlayMode.LOOP);
            atlas = new TextureAtlas("enemies/Husk Hornhead/DeathAir.atlas");
            deathAir =  new Animation<>(0.09f , atlas.findRegions("Death Air"));
            atlas = new TextureAtlas("enemies/Husk Hornhead/DeathLand.atlas");
            deathLand =  new Animation<>(0.09f , atlas.findRegions("Death Land"));
        }
    };


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
    Skill skill = null;
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
    Animation<TextureAtlas.AtlasRegion> Idle;


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

    public Move getMove() {
        return move;
    }

    public Animation<TextureAtlas.AtlasRegion> getWalk() {
        return walk;
    }

    public Animation<TextureAtlas.AtlasRegion> getAttackStart() {
        return attackStart;
    }

    public Animation<TextureAtlas.AtlasRegion> getAttackLung() {
        return attackLung;
    }

    public Animation<TextureAtlas.AtlasRegion> getAttackEnd() {
        return attackEnd;
    }

    public Animation<TextureAtlas.AtlasRegion> getSkillStart() {
        return skillStart;
    }

    public Animation<TextureAtlas.AtlasRegion> getSkillLung() {
        return skillLung;
    }

    public Animation<TextureAtlas.AtlasRegion> getSkillEnd() {
        return skillEnd;
    }

    public Animation<TextureAtlas.AtlasRegion> getDeathAir() {
        return deathAir;
    }

    public Animation<TextureAtlas.AtlasRegion> getDeathLand() {
        return deathLand;
    }

    public Animation<TextureAtlas.AtlasRegion> getIdle() {
        return Idle;
    }
}
