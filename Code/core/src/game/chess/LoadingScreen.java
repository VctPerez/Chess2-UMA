package game.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import utils.Image;
import utils.Render;
import utils.Resources;
import utils.Settings;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class LoadingScreen extends AbstractScreen{
    private Stage stage;
    private Table table;
    private Image title, logo;
    private ArrayList<Image> images = new ArrayList<>();
    private Color backgroundColor = Color.BLACK;
    @Override
    public void show() {
        stage = new Stage(new FitViewport(Render.SCREEN_WIDTH, Render.SCREEN_HEIGHT));
        table = new Table();
        table.setFillParent(true);

        ;
        stage.addActor(Render.menuBG);

        title = new Image(Resources.LOADINGTITLE_PATH);
        title.setSize(800,200);
        logo = new Image(Resources.PIXEL_LOGO_PATH);
        logo.setSize(256*2,256*2);
        table.add(title).row();
        table.add(logo);
        stage.addActor(table);

        images.add(title);
        images.add(logo);

        Render.bgMusic = Render.app.getManager().get(Resources.MENU_THEME);
        Render.bgMusic.setLooping(true);
        Render.bgMusic.setVolume(Settings.musicVolume);
        Render.bgMusic.play();


    }

    @Override
    public void render(float delta) {
        Render.clearLoadingScreen(backgroundColor);
        update();
        stage.draw();
        stage.act();
    }

    private void update(){
        if(Image.fader(1,0.005f, images)){
            if(backgroundColor.r < 0.0549019f){
                backgroundColor.r += 0.001f;
            }
            if(backgroundColor.g < 0.062745f){
                backgroundColor.g += 0.001f;
            }
            if(backgroundColor.b < 0.2666666f){
                backgroundColor.b += 0.001f;
            }
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
