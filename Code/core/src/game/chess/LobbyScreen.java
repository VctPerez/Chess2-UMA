package game.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import multiplayer.Host;
import multiplayer.Player;
import utils.Render;
import utils.Resources;
import utils.Text;
import utils.TextButton;

import java.io.IOException;
import java.net.Inet4Address;

public class LobbyScreen extends AbstractScreen{
    private Stage stage;

    private Player player1, player2;
    private TextButton findMatch;
    private Text statusP2, p2;
    private boolean finding = false, configured= false, host;

    public void create(String namePlayer, boolean hosting){
        host =hosting;
        if(hosting){
            player1 = new Player(namePlayer);
        }else{
            player2 = new Player(namePlayer);
        }
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(1280, 720));
        Gdx.input.setInputProcessor(stage);

        Text p1;
        if(host) {
            p1 = new Text("Jugador 1: " + player1.getName(), Resources.FONT_MENU_PATH, 30, Color.WHITE, 3);
            try {
                Text invCode = new Text("Codigo: " + Inet4Address.getLocalHost().getHostAddress(),
                        Resources.FONT_MENU_PATH, 20, Color.WHITE,2);
                invCode.setPosition(300,500);
                stage.addActor(invCode);
                Render.host = new Host(player1);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
            p2 = new Text("Jugador 2: ", Resources.FONT_MENU_PATH, 30, Color.WHITE, 3);
            statusP2 = new Text("CONNECTED", Resources.FONT_MENU_PATH, 30, Color.RED, 3);
        }else{
            p1 = new Text("Jugador 1: " + Render.guest.getPlayer1().getName(), Resources.FONT_MENU_PATH, 30, Color.WHITE, 3);
            p2 = new Text("Jugador 2: " + player2.getName(), Resources.FONT_MENU_PATH, 30, Color.WHITE, 3);
            statusP2 = new Text("CONNECTED", Resources.FONT_MENU_PATH, 30, Color.GREEN, 3);

        }
        Text statusP1 = new Text("CONNECTED", Resources.FONT_MENU_PATH, 30, Color.GREEN, 3);
        //p2 = new Text("Jugador 2: ", Resources.FONT_MENU_PATH, 30, Color.WHITE, 3);
        //statusP2 = new Text("CONNECTED", Resources.FONT_MENU_PATH, 30, Color.RED, 3);
        findMatch = new TextButton("FIND");
        findMatch.setPosition(600,400);
        addListener();

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
            if(host)matchFinder();
        }catch(IOException e){
            System.err.println(e.getMessage());
        }
        Render.Batch.end();
    }
    private void addListener(){
        findMatch.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(!Render.host.isP2connected() && !finding){
                    Render.host.start();
                    System.out.println("La hebra 2 va por su cuenta");
                } else if (finding && !Render.host.isP2connected()) {
                    cancelSearch();
                }
                return true;
            }
        });
    }
    public void matchFinder() throws IOException {
        finding = Render.host.isServerOpen();
        /*if (findMatch.isPressed() && !Render.host.isP2connected()) {
            //System.out.println("buscando...");
            if (!finding) {

            }
        }
        if(!Render.host.isP2connected()){
            configured = false;
            statusP2.setColor(Color.RED);
            if (finding && !findMatch.isPressed()) {
                cancelSearch();
            }
        }*/
        if (Render.host.isP2connected() &&!configured) {
            statusP2.setColor(Color.GREEN);
            Render.host.receivePlayer2();
            p2.setText("Jugador2: " + Render.host.getPlayer2().getName());
            configured =true;
            System.out.println("Jugador conectado");
            Render.host.sendPlayer1();
        }
    }
    private void cancelSearch(){
        try {
            Render.host.stopFind();
            Render.host = new Host(player1);
            System.out.println("ha parado de buscar");
        } catch (IOException e) {
            System.err.println("la hebra2 ha parado");
        }
    }

    @Override
    public void resize(int width, int height) {
        Render.SCREEN_WIDTH = width;
        Render.SCREEN_HEIGHT = height;
        stage.getViewport().update(width,height);
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
