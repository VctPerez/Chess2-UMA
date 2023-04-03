package game.chess;

import utils.Image;
import utils.Render;
import utils.Resources;

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
        background.draw(Render.Batch, 0);
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
