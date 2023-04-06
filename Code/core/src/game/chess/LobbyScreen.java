package game.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import java.net.Inet4Address;
import java.net.UnknownHostException;

public class LobbyScreen extends AbstractScreen{
    private Stage stage;

    private Player player1, player2;
    private TextButton findMatch;
    private Text statusP2, p2;
    private boolean finding = false, configured= false;

    public void create(String namePlayer, boolean hosting){
        if(hosting){
            player1 = new Player(namePlayer);
        }else{
            player2 = new Player(namePlayer);
        }
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(1280, 720));
        Gdx.input.setInputProcessor(Render.inputs);

        try {
            Text invCode = new Text("Codigo: " + Inet4Address.getLocalHost().getHostAddress(),
                    Resources.FONT_MENU_PATH, 20, Color.WHITE,2);
            invCode.setPosition(300,500);
            stage.addActor(invCode);
            Render.host = new Host(player1);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        Text p1 = new Text("Jugador 1: " + player1.getName(), Resources.FONT_MENU_PATH, 30, Color.WHITE, 3);
        Text statusP1 = new Text("CONNECTED", Resources.FONT_MENU_PATH, 30, Color.GREEN, 3);
        p2 = new Text("Jugador 2: ", Resources.FONT_MENU_PATH, 30, Color.WHITE, 3);
        statusP2 = new Text("CONNECTED", Resources.FONT_MENU_PATH, 30, Color.RED, 3);
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


    }

    @Override
    public void render(float delta) {
        Render.clearScreen();
        Render.Batch.begin();
        stage.act();
        stage.draw();
        try {
            matchFinder();
        }catch(IOException e){
            System.err.println(e.getMessage());
        }
        Render.Batch.end();
    }

    public void matchFinder() throws IOException {
        if(!Render.host.isP2connected()) {
            configured = false;
            statusP2.setColor(Color.RED);
            if (findMatch.isPressed()) {
                //System.out.println("buscando...");
                if (!finding && !Render.host.isP2connected()) {
                    Render.host.start();
                    finding = true;
                } else if (finding) {
                    cancelSearch();
                }
            } else if (finding && !findMatch.isPressed()) { //Se ha producido un timeout
                finding = false;
                cancelSearch();
            }
        }
        else if(!configured){
            statusP2.setColor(Color.GREEN);
            Render.guest.sendPlayer2();
            Render.host.receivePlayer2();
            Render.host.sendPlayer1();
            Render.guest.receivePlayer1();
            player2 = Render.host.getPlayer2();
            p2.setText("Jugador 2: " + player2.getName());
            configured = true;
        }
    }

    private void cancelSearch(){
        try {
            Render.host.stopFind();
            Render.host = new Host(player1);
        } catch (IOException e) {
            System.err.println("la hebra2 ha parado");
        }
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
