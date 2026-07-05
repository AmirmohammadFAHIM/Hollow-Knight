package com.mygame.game.models.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygame.game.models.entities.aiEnemies.LazyMove;
import com.mygame.game.models.entities.aiEnemies.Move;
import com.mygame.game.models.entities.aiEnemies.NormalMove;
import com.mygame.game.models.skill.*;

public enum ENEMIES {

    CRYSTALLIZED(400 , 50 , 128 , 190){

        {

            move = new LazyMove(0 , 5);
            skill = new Laser(3.5f);
            attack = new Rage(90 , 5 , false);


            TextureAtlas spriteSheet = new TextureAtlas("enemies/Crystallized/Idle.atlas");
            Idle = new Animation<>(0.11f, spriteSheet.findRegions("Idle")
                , Animation.PlayMode.LOOP);
            walk = Idle;
            spriteSheet = new TextureAtlas("enemies/Crystallized/Run.atlas");
            attackStart = new Animation<>(0.09f , spriteSheet.findRegions("Run"));
            attackLung = new  Animation<>(0.09f , spriteSheet.findRegions("Run") ,
                Animation.PlayMode.LOOP);
            attackEnd = new Animation<>(0.09f , spriteSheet.findRegions("Run"));
            spriteSheet = new TextureAtlas("enemies/Crystallized/SkillLung.atlas");
            skillLung = new Animation<>(0.09f , spriteSheet.findRegions("Shoot") ,
                Animation.PlayMode.LOOP);
            spriteSheet = new TextureAtlas("enemies/Crystallized/Evade.atlas");
            skillStart = new Animation<>(0.09f , spriteSheet.findRegions("Evade"));
            spriteSheet = new TextureAtlas("enemies/Crystallized/SkillEnd.atlas");
            skillEnd =  new Animation<>(0.09f , spriteSheet.findRegions("Shoot"));
            spriteSheet = new TextureAtlas("enemies/Crystallized/DeathAir.atlas");
            deathAir = new  Animation<>(0.09f , spriteSheet.findRegions("Death Air"));
            spriteSheet = new TextureAtlas("enemies/Crystallized/DeathLand.atlas");
            deathLand = new  Animation<>(0.09f , spriteSheet.findRegions("Death Land"));

        }
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
    },



    MOSQUITO(400 , 40 , 125 , 100 , new AirAttack(3 , 420) , null){
        {
            move = new NormalMove();
            TextureAtlas spriteSheet = new TextureAtlas("enemies/Mosquito/Idle.atlas");
            walk = new Animation<>(0.09f ,  spriteSheet.findRegions("Idle")
                , Animation.PlayMode.LOOP);
            attackEnd = new Animation<>(0.09f , spriteSheet.findRegions("Idle"));
            spriteSheet = new TextureAtlas("enemies/Mosquito/AttackAnticipate.atlas");
            attackStart = new Animation<>(0.09f , spriteSheet.findRegions("Attack Anticipate"));
            spriteSheet = new TextureAtlas("enemies/Mosquito/Attack.atlas");
            attackLung = new Animation<>(0.09f , spriteSheet.findRegions("Attack"),
                Animation.PlayMode.LOOP);
            spriteSheet = new  TextureAtlas("enemies/Mosquito/DeathAir.atlas");
            deathAir = new Animation<>(0.09f , spriteSheet.findRegions("Death Air"));
            spriteSheet = new  TextureAtlas("enemies/Mosquito/DeathLand.atlas");
            deathLand = new Animation<>(0.09f , spriteSheet.findRegions("Death Land"));

            flying = true;
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
    boolean flying = false;
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

    public boolean isFlying() {
        return flying;
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
