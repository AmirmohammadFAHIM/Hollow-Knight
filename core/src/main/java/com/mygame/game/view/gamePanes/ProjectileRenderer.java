package com.mygame.game.view.gamePanes;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygame.game.models.skill.Projectile;

public class ProjectileRenderer {
    private Projectile projectile;
    private float stateTime = 0;
    private Animation<TextureAtlas.AtlasRegion> start;
    private Animation<TextureAtlas.AtlasRegion> moving;
    private Animation<TextureAtlas.AtlasRegion> end;
    private Animation<TextureAtlas.AtlasRegion> currAnim;
    public ProjectileRenderer(Projectile projectile) {
        this.projectile = projectile;
    }

    public void render(SpriteBatch batch) {
        batch.draw(currAnim.getKeyFrame(stateTime), projectile.getBounds().x,  projectile.getBounds().y);

    }
}
