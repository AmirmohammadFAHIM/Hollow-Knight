package com.mygame.game.models.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class EntityRenderer {
    private Entity entity;
    private Sound damage;

    public void render(SpriteBatch batch) {
        damage = Gdx.audio.newSound(Gdx.files.internal("sfx/enemy/enemy_damage.wav"));

        TextureAtlas.AtlasRegion frame = entity.currentAnimation.getKeyFrame(entity.stateTime);

        if(entity.right && !frame.isFlipX()) frame.flip(true, false);
        else if(!entity.right && frame.isFlipX()) frame.flip(true, false);

        batch.draw(frame , entity.x ,  entity.y - 10);
        hurtSound();
    }


    boolean hurt = false;
    private void hurtSound() {
        if(!hurt && entity.isHurt()){
            damage.play();
        }
        hurt = entity.isHurt();
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }
}
