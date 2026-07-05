package com.mygame.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.mygame.game.controller.UiManager;

public class MainScreen implements Screen {
        private Stage stage;
        private Stack rootTable;
        private Table HomeTable;
        private Table Optiontable;
        private Table Achievementtable;
        private Table Quittable;
        private Table LoadTable;
        private ExtendViewport viewport;
        ParticleEffect glowEffect;
        SpriteBatch batch;


        @Override
        public void show() {
            batch  = new SpriteBatch();
            glowEffect = new ParticleEffect();

            glowEffect.load(Gdx.files.internal("menus/mainmenuParticle.p") ,
                Gdx.files.internal("menus"));

          Array<Sprite> sprites = glowEffect.getEmitters().first().getSprites();
          for (Sprite sprite : sprites) {
              sprite.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
          }
            glowEffect.start();


            TextButton.TextButtonStyle  style = new TextButton.TextButtonStyle();
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
                Gdx.files.internal("menus/trajan.ttf"));
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.size = 24;
            parameter.color = com.badlogic.gdx.graphics.Color.WHITE;
            parameter.magFilter = Texture.TextureFilter.Linear;
            parameter.minFilter = Texture.TextureFilter.Linear;
            style.font =  generator.generateFont(parameter);
            style.downFontColor = Color.CYAN;

            stage = new Stage(){
                @Override
                public boolean keyDown(int keyCode) {
                    if (keyCode == Input.Keys.ESCAPE) {
                        rootTable.clearChildren(false);
                        rootTable.add(HomeTable);
                    }
                    return super.keyDown(keyCode);
                }
            };
            viewport = new ExtendViewport(stage.getWidth(), stage.getHeight());
            stage.setViewport(viewport);

            Texture bg = new Texture(Gdx.files.internal("menus/MainMenu.png"));
            bg.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            Image image = new Image(bg);
            image.setFillParent(true);
            image.setScaling(Scaling.fill);
            stage.addActor(image);
            image.toBack();
            miniators();
            rootTable = new Stack();

            rootTable.setFillParent(true);
            //rootTable.center();
            rootTable.add(HomeTable = new MainMenuTable(this));

            stage.addActor(rootTable);
            Gdx.input.setInputProcessor(stage);

        }

        @Override
        public void render(float delta) {
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            stage.act();
            stage.draw();

            glowEffect.update(delta);

            batch.begin();
            glowEffect.draw(batch);
            batch.end();




        }

        @Override
        public void resize(int width, int height) {
            viewport.update(width, height , true);
        }

        @Override
        public void pause() {

        }

        @Override
        public void resume() {

        }

        @Override
        public void hide() {

        }

        @Override
        public void dispose() {

        }

        private void miniators(){
            Table logoTable = new Table();
            logoTable.setFillParent(true);
            logoTable.defaults().pad(50);
           // logoTable.right();
            logoTable.bottom();
            Image image =  new Image(new Texture(Gdx.files.internal("menus/teamcherry.png")));
            logoTable
                .add(image).right().bottom().size(60 , 60 );
            logoTable.add().expand();
            Image minis = new Image(new Texture( Gdx.files.internal("menus/minis.png")));

            logoTable.add(minis).left().bottom().size(70 , 20);
            stage.addActor(logoTable);


        }

    public Stage getStage() {
        return stage;
    }

    public Stack getRootTable() {
        return rootTable;
    }
}


