package game.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import utils.Image;
import utils.Render;
import utils.Resources;
import utils.Settings;

import java.util.ArrayList;
import java.util.Random;

public class LoadingScreen extends AbstractScreen{
    private Stage stage;
    private Table table;
    private Image title, logo;
    @Override
    public void show() {
        stage = new Stage(new FitViewport(Render.SCREEN_WIDTH, Render.SCREEN_HEIGHT));
        table = new Table();
        table.setFillParent(true);
        stage.addActor(Render.menuBG);
        title = new Image(Resources.LOADINGTITLE_PATH);
        title.setSize(400* Gdx.graphics.getWidth()/ 1280f,100*Gdx.graphics.getHeight() / 720f);
        logo = new Image(Resources.PIXEL_LOGO_PATH);
        logo.setSize(256* Gdx.graphics.getWidth()/ 1280f,256*Gdx.graphics.getHeight() / 720f);
        table.add(title);
        table.add(logo);
        stage.addActor(table);

        Render.bgMusic = Render.app.getManager().get(Resources.MENU_THEME);
        Render.bgMusic.setLooping(true);
        Render.bgMusic.setVolume(Settings.musicVolume);
        Render.bgMusic.play();
    }

    @Override
    public void render(float delta) {
        Render.clearScreen();
        stage.draw();
        stage.act();
    }

    private void update(){
        if(Render.app.getManager().update() ){
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
