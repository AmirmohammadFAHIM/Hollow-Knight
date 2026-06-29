package com.mygame.game.models.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class EntityRenderer {
    private NPC entity;

    public void render(SpriteBatch batch, OrthographicCamera camera) {


        TextureAtlas.AtlasRegion frame = entity.currentAnimation.getKeyFrame(entity.stateTime);

        if(entity.right && !frame.isFlipX()) frame.flip(true, false);
        else if(!entity.right && frame.isFlipX()) frame.flip(true, false);

        batch.draw(frame , entity.x ,  entity.y);
    }

    public void setEntity(NPC entity) {
        this.entity = entity;
    }

    public NPC getEntity() {
        return entity;
    }
}
