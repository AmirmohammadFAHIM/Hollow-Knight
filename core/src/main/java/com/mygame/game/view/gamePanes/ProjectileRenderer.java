package com.mygame.game.view.gamePanes;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygame.game.models.skill.Projectile;

public class ProjectileRenderer {


    public void render(SpriteBatch batch , Projectile projectile) {
        TextureAtlas.AtlasRegion frame = projectile.currAnim.getKeyFrame(projectile.stateTime);
        checkDirRight(frame , projectile.isRight());
        batch.draw(projectile.currAnim.getKeyFrame(projectile.stateTime) ,
            projectile.getBounds().x,
            projectile.getBounds().y , projectile.getBounds().width ,  projectile.getBounds().height );
    }

    private void checkDirRight(TextureAtlas.AtlasRegion frame , boolean right){
        if(right && frame.isFlipX()) frame.flip(true,false);
        else if(!right  && !frame.isFlipX()) frame.flip(true,false);
    }
}
