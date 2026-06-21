package com.mygame.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.mygame.game.models.Game;
import com.mygame.game.models.Vessel;

public class VesselRender {
    private Vessel vessel;
    private Animation<TextureAtlas.AtlasRegion> idle;
    private Animation<TextureAtlas.AtlasRegion> start_run;
    private Animation<TextureAtlas.AtlasRegion> run;
    private Animation<TextureAtlas.AtlasRegion> run_toIdle;
    private Animation<TextureAtlas.AtlasRegion> jump;
    private Animation<TextureAtlas.AtlasRegion> slash;
    private Animation<TextureAtlas.AtlasRegion> down_slash;
    private Animation<TextureAtlas.AtlasRegion> double_jump;
    private  Animation<TextureAtlas.AtlasRegion> fall;
    private Animation<TextureAtlas.AtlasRegion> landing;
    private Animation<TextureAtlas.AtlasRegion> wall_side;
    private Animation<TextureAtlas.AtlasRegion> start_focus;
    private Animation<TextureAtlas.AtlasRegion> focus;
    private  Animation<TextureAtlas.AtlasRegion> death;
    private Animation<TextureAtlas.AtlasRegion> dash;
    private Animation<TextureAtlas.AtlasRegion> fireball;
    private static Animation<TextureAtlas.AtlasRegion> currentAnimation;



    public  VesselRender() {
        initAnimations();
        currentAnimation = idle;
        vessel = Game.getVessel();
    }

    private void initAnimations() {


       /* TextureAtlas x = new TextureAtlas(Gdx.files.internal("knight/Run.atlas"));
        run = new Animation<>(0.1f, x.findRegions("Run"), Animation.PlayMode.LOOP);
        x = new TextureAtlas(Gdx.files.internal("knight/Idle.atlas"));
        idle =  new Animation<>(0.1f, x.findRegions("Idle"), Animation.PlayMode.LOOP);
        x = new TextureAtlas("knight/startRun.atlas");
        start_run =  new Animation<>(0.1f, x.findRegions("Run"), Animation.PlayMode.NORMAL);
        x = new TextureAtlas("knight/jump.atlas");
        jump  = new Animation<>(0.1f, x.findRegions("Airborne"), Animation.PlayMode.NORMAL);
        x = new TextureAtlas("knight/Fall.atlas");
        fall =  new Animation<>(0.1f, x.findRegions("Fall"), Animation.PlayMode.LOOP);
        x =  new TextureAtlas("knight/Dash.atlas");
        dash =  new Animation<>(0.1f, x.findRegions("Dash"), Animation.PlayMode.NORMAL);
        x = new  TextureAtlas("knight/SlashAlt.atlas");
        slash =  new Animation<>(0.1f, x.findRegions("SlashAlt"), Animation.PlayMode.NORMAL);
        x = new  TextureAtlas("knight/Landing.atlas");
        landing = new Animation<>(0.1f, x.findRegions("Landing"), Animation.PlayMode.NORMAL);
        x = new  TextureAtlas("knight/Death.atlas");
        death =  new Animation<>(0.1f, x.findRegions("Death"), Animation.PlayMode.NORMAL);
        x = new  TextureAtlas("knight/FocusStart.atlas");
        start_focus = new   Animation<>(0.1f, x.findRegions("FocusStart"), Animation.PlayMode.NORMAL);
        x = new  TextureAtlas("knight/Focus.atlas");
        focus = new Animation<>(0.1f, x.findRegions("Focus"), Animation.PlayMode.LOOP);
        x = new  TextureAtlas("knight/DoubleJump.atlas");
        double_jump = new Animation<>(0.1f, x.findRegions("DoubleJump"), Animation.PlayMode.NORMAL);
        x = new TextureAtlas("knight/FireballCast.atlas");
        fireball = new Animation<>(0.1f, x.findRegions("FireballCast"), Animation.PlayMode.NORMAL);
*/
    }


    private void update_rendering(float delta) {
      currentAnimation =  vessel.getState().getAnimation();

    }

    public void render(SpriteBatch batch , float stateTime){

       // vessel.update(Gdx.graphics.getDeltaTime());
        update_rendering(Gdx.graphics.getDeltaTime());
        batch.draw(currentAnimation.getKeyFrame(stateTime,true) , vessel.getX() , vessel.getY());

    }


    public static Animation<TextureAtlas.AtlasRegion> getCurrentAnimation() {
        return currentAnimation;
    }
}
