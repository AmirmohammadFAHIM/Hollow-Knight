package com.mygame.game.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.mygame.game.Main;

public class FireBall {
    private float damage;
    private static float velocity = 30f;
    private boolean proved = false;
    private boolean right ;
    private Rectangle bounds;
    private static Animation<TextureAtlas.AtlasRegion> moving;
    private static Animation<TextureAtlas.AtlasRegion> hit;

    public FireBall(float x , float y , boolean right){
        bounds = new Rectangle();
        bounds.x  = x;
        bounds.y  = y;
        this.right = right;
    }


    public void render(float stateTime , SpriteBatch batch){
        bounds.width = moving.getKeyFrame(stateTime).getRegionWidth();
        bounds.height = moving.getKeyFrame(stateTime).getRegionHeight();

        batch.draw(!proved ? moving.getKeyFrame(stateTime) : hit.getKeyFrame(stateTime)
            , bounds.x , bounds.y);
    }

    public void move(Game game){
        ///  To Do : check the collision with enemies so we see if we should hit
        bounds.x += velocity * (right ? 1 : -1);
        if(bounds.x - Gdx.graphics.getWidth() < -100 ||
        bounds.x - Gdx.graphics.getWidth() > 100) {
            game.getFireballs().removeValue(this, true);
        }
    }
}
