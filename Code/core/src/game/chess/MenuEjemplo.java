package game.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import utils.*;

public class MenuEjemplo extends ScreenAdapter {

    IOS inputs = new IOS();
    TextButton boton;
    Image background;

    @Override
    public void show() {

        Text ejemplo = new Text("Ejemplo", Resources.FONT_MENU_PATH, 100, Color.WHITE, 3);
        boton = new TextButton(ejemplo);
        boton.setPosition(400,400);
        Gdx.input.setInputProcessor(inputs);
    }

    @Override
    public void render(float delta) {
        Render.clearScreen();

        Render.camera.update();
        Render.Batch.setProjectionMatrix(Render.camera.combined);

        Render.Batch.begin();
        boton.establish(inputs, Render.Batch);
        if(boton.isSelected()){
            Render.app.setScreen(new GameScreen());
        }
        Render.Batch.end();
    }

    @Override
    public void resize(int width, int height) {
        Render.SCREEN_WIDTH = width;
        Render.SCREEN_HEIGHT = height;
        Render.camera.setToOrtho(false, width, height);
    }

    @Override
    public void dispose() {
        // TODO: 21/03/2023  
    }
}
