package game.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.ColorAction;
import com.badlogic.gdx.utils.viewport.FitViewport;
import multiplayer.Host;
import multiplayer.Player;
import utils.Render;
import utils.Resources;
import utils.Text;
import utils.TextButton;

import java.io.IOException;

public class LobbyScreen extends AbstractScreen{
    private Stage stage;

    private Player player1, player2;
    private TextButton findMatch;
    private Host host;
    private boolean finding = false;

    @Override
    public void show() {
        stage = new Stage(new FitViewport(1280, 720));
        Gdx.input.setInputProcessor(Render.inputs);

        Text p1 = new Text("Jugador 1: (aqui iria el nombre)", Resources.FONT_MENU_PATH, 30, Color.WHITE, 3);
        Text statusP1 = new Text("CONNECTED", Resources.FONT_MENU_PATH, 30, Color.GREEN, 3);
        Text p2 = new Text("Jugador 2: (aqui iria el nombre)", Resources.FONT_MENU_PATH, 30, Color.WHITE, 3);
        Text statusP2 = new Text("DISCONNECTED", Resources.FONT_MENU_PATH, 30, Color.RED, 3);
        findMatch = new TextButton(new Text("FIND", Resources.FONT_MENU_PATH, 30, Color.CYAN, 3));
        findMatch.setPosition(600,400);
        findMatch.setRemarked(Color.BLUE);

        p1.setPosition(300,600);
        statusP1.setPosition(300,550);
        p2.setPosition(300,200);
        statusP2.setPosition(300, 150);
        stage.addActor(findMatch);
        stage.addActor(p1);
        stage.addActor(statusP1);
        stage.addActor(p2);
        stage.addActor(statusP2);

        try {
            player1 = new Player("Victor");
            host = new Host(player1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void render(float delta) {
        Render.clearScreen();
        Render.Batch.begin();
        stage.act();
        stage.draw();
        if(findMatch.isSelected() && !host.isP2connected()){
            //System.out.println("buscando...");
            if(!finding){
                //TODO -> implementar host con runnable y modificar interrupt
                //host.start();
                System.out.println("La hebra 2 va por su cuenta");
                finding = true;
            }
        }else if(finding && !findMatch.isSelected()){
            //TODO
            finding = false;
            System.out.println("la hebra2 ha parado");
        }
        Render.Batch.end();
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
