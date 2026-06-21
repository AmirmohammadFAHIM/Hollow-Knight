package com.mygame.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.mygame.game.controller.UiManager;

import java.awt.*;
import java.util.ArrayList;

public class MainMenuView implements Screen {
        private Stage stage;
        private Table Optiontable;
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
//System.out.println("los");
            glowEffect.start();



            stage = new Stage();
            viewport = new ExtendViewport(stage.getWidth(), stage.getHeight());
            stage.setViewport(viewport);

            Texture bg = new Texture(Gdx.files.internal("menus/MainMenu.png"));
            bg.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            Image image = new Image(bg);
            image.setFillParent(true);
            image.setScaling(Scaling.fill);
            stage.addActor(image);
            image.toBack();
            Gdx.input.setInputProcessor(stage);
            Image title =  new Image(new Texture(Gdx.files.internal("menus/title.png")));
            // title.setSize(200 , 100);
            Table table  = new Table();
            table.setFillParent(true);
            table.top();
            table.add(title).size(400 , 180).align(Align.top).padBottom(50).row();


            TextButton.TextButtonStyle  style = new TextButton.TextButtonStyle();
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
                Gdx.files.internal("menus/trajan.ttf"));
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.size = 24;
            parameter.color = Color.WHITE;
            parameter.magFilter = Texture.TextureFilter.Linear;
            parameter.minFilter = Texture.TextureFilter.Linear;
            style.font =  generator.generateFont(parameter);
            style.downFontColor = Color.CYAN;
            style.overFontColor =  Color.GRAY;
            //style.font = new BitmapFont(Gdx.files.internal("menus/CENTURY.TTF"));



            // Label label = new Label("Load Game" , null , "Century");

            TextButton newGame  = new TextButton("New Game", style);
            newGame.addListener(new  ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    UiManager.setScreen(new GameView());
                }
            });
            newGame.setTouchable(Touchable.enabled);

            TextButton loadGame  = new TextButton("Load Game", style);
            loadGame.setTouchable(Touchable.enabled);

            TextButton options = new  TextButton("Options", style);
            options.setTouchable(Touchable.enabled);

            TextButton exit = new TextButton("Exit", style);
            exit.setTouchable(Touchable.enabled);



            //  button.setSize(100 , 50);
            table.defaults().padBottom(15);
           // table.setDebug(true);
            table.add(newGame).align(Align.center).row();
            table.add(loadGame).align(Align.center).row();
            table.add(options).align(Align.center).row();
            table.add(exit).center().row();

            stage.addActor(table);
            miniators();
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
    }


