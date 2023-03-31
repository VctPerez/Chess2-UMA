package game.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import multiplayer.Player;
import utils.Render;
import utils.Resources;
import utils.Text;

public class LobbyScreen extends AbstractScreen{
    private Stage stage;
    Player player1, player2;

    @Override
    public void show() {
        stage = new Stage(new FitViewport(1280, 720));
        Gdx.input.setInputProcessor(Render.inputs);

        Text p1 = new Text("Jugador 1: (aqui iria el nombre)", Resources.FONT_MENU_PATH, 30, Color.WHITE, 3);
        Text statusP1 = new Text("CONNECTED", Resources.FONT_MENU_PATH, 30, Color.GREEN, 3);
        Text p2 = new Text("Jugador 2: (aqui iria el nombre)", Resources.FONT_MENU_PATH, 30, Color.WHITE, 3);
        Text statusP2 = new Text("DISCONNECTED", Resources.FONT_MENU_PATH, 30, Color.RED, 3);
        p1.setPosition(300,600);
        statusP1.setPosition(300,550);
        p2.setPosition(300,200);
        statusP2.setPosition(300, 150);
        stage.addActor(p1);
        stage.addActor(statusP1);
        stage.addActor(p2);
        stage.addActor(statusP2);
    }

    @Override
    public void render(float delta) {
        Render.clearScreen();

        stage.act();
        stage.draw();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
