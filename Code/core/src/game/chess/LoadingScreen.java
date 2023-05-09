package game.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import utils.Image;
import utils.Render;
import utils.Resources;

import java.util.ArrayList;

public class LoadingScreen extends AbstractScreen{
    private Stage stage;
    private Table table;
    private Image title, logo;
    private ArrayList<Image> images = new ArrayList<>(), backgroundAux = new ArrayList<>();
    private Image background;
    @Override
    public void show() {
        stage = new Stage(new FitViewport(Render.SCREEN_WIDTH, Render.SCREEN_HEIGHT));
        table = new Table();
        table.setFillParent(true);

        initializeElements();
        setupDataStructures();

        Render.playBgMusic();
    }

    @Override
    public void render(float delta) {
        Render.clearScreen();
        update();
        stage.draw();
        stage.act();
    }

    private void update(){
        if(Image.fader(1,0.005f, images)){
            if(Image.fader(0,0.01f, backgroundAux) && Render.app.getManager().isFinished()){
                Render.app.setScreen(Render.MAINSCREEN);
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        Render.SCREEN_WIDTH = width;
        Render.SCREEN_HEIGHT = height;
        stage.getViewport().update(width,height);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    private void initializeElements(){
        title = new Image(Resources.LOADINGTITLE_PATH);
        title.setFadeIn(true);
        title.setFadeOut(true);
        title.setSize(800,200);

        background = new Image(Resources.BLACK_BACKGROUND_PATH);
        background.setFadeOut(true);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        background.setTransparencyConst(1);

        logo = new Image(Resources.PIXEL_LOGO_PATH);
        logo.setFadeIn(true);
        logo.setFadeOut(true);
        logo.setSize(256*2,256*2);
    }

    private void setupDataStructures(){
        table.add(title).row();
        table.add(logo);

        stage.addActor(Render.menuBG);
        stage.addActor(background);
        stage.addActor(table);

        images.add(title);
        images.add(logo);
        backgroundAux.add(background);
    }
}
