package game.chess;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import utils.Image;
import utils.Render;
import utils.Resources;

import java.util.ArrayList;
import java.util.Random;

public class LoadingScreen extends AbstractScreen{
    Image background;
    ArrayList<Sound> sounds;
    int indexSound = new Random().nextInt(4);
    float wait, increase;
    @Override
    public void show() {
        background = new Image(Render.app.getManager().get(Resources.LOADINGSCREEN_PATH, Texture.class));
        background.setSize(Render.SCREEN_WIDTH, Render.SCREEN_HEIGHT);
        sounds = new ArrayList<>();
        sounds.add(Render.app.getManager().get(Resources.LODINGSOUND1, Sound.class));
        sounds.add(Render.app.getManager().get(Resources.LODINGSOUND2, Sound.class));
        sounds.add(Render.app.getManager().get(Resources.LODINGSOUND3, Sound.class));
        sounds.add(Render.app.getManager().get(Resources.LODINGSOUND4, Sound.class));
        selectParams();
        sounds.get(indexSound).play();
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
        if(Render.app.getManager().update() && background.fader(wait,increase)){
            sounds.get(indexSound).stop();
            Render.app.setScreen(Render.MAINSCREEN);
        }
    }
    private void selectParams(){
        switch (indexSound){
            case 0:
                wait = 3;
                increase = 0.0075f;
                break;
            case 1:
                wait = 2.3f;
                increase = 0.003f;
                break;
            case 2:
                wait = 2.3f;
                increase = 0.005f;
                break;
            case 3:
                wait = 5f;
                increase = 0.005f;
                break;
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
