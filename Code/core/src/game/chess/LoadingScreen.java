package game.chess;

import com.badlogic.gdx.graphics.Color;
import utils.Image;
import utils.Render;
import utils.Resources;
import utils.Text;

public class LoadingScreen extends AbstractScreen{
    Image background;

    @Override
    public void show() {
        background = new Image(Resources.LOADINGSCREEN_PATH);
        background.setSize(Render.SCREEN_WIDTH, Render.SCREEN_HEIGHT);
    }

    @Override
    public void render(float delta) {
        Render.clearScreen();
        update();
        Render.Batch.begin();
        background.draw(Render.Batch);
        Render.Batch.end();
    }

    private void update(){
        if(Render.app.getManager().update() && background.fader()){
            Render.app.setScreen(Render.MAINSCREEN);
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
