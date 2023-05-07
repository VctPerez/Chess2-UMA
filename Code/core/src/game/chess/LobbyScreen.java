package game.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import interaccionFichero.LineReader;
import multiplayer.Host;
import multiplayer.Player;
import utils.*;

import java.io.IOException;
import java.net.Inet4Address;

public class LobbyScreen extends AbstractScreen{
    private Stage stage;
    private LineReader configReader, languageReader;
    private Player player1, player2;
    private TextButton match;
    private Text statusP2, p2;
    private boolean finding = false, configured= false;

    public void create(String namePlayer, boolean hosting){
        Render.hosting = hosting;
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

        configReader = new LineReader("files/config.txt"); //Lector del txt configuracion para sacar el idioma
        languageReader = new LineReader("files/lang/"+ configReader.readLine(Settings.language) + "lobby.txt"); //Abrimos el idioma que toca del archivo configuracion

        Text p1;
        if(Render.hosting) {
            p1 = new Text(languageReader.readLine(2) + player1.getName(), Resources.FONT_MENU_PATH, 30, Color.WHITE, 3);
            try {
                Text invCode = new Text(languageReader.readLine(3) + obfuscateIP(Inet4Address.getLocalHost().getHostAddress()),
                        Resources.FONT_MENU_PATH, 20, Color.WHITE,2);
                invCode.setPosition(300,500);
                stage.addActor(invCode);
                
                Render.host = new Host(player1);
                Render.player = Render.host;
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
            p2 = new Text(languageReader.readLine(4), Resources.FONT_MENU_PATH, 30, Color.WHITE, 3);
            statusP2 = new Text(languageReader.readLine(5), Resources.FONT_MENU_PATH, 30, Color.RED, 3);
        }else{
            p1 = new Text(languageReader.readLine(2) + Render.guest.getPlayer1().getName(), Resources.FONT_MENU_PATH, 30, Color.WHITE, 3);
            p2 = new Text(languageReader.readLine(4) + player2.getName(), Resources.FONT_MENU_PATH, 30, Color.WHITE, 3);
            statusP2 = new Text(languageReader.readLine(5), Resources.FONT_MENU_PATH, 30, Color.GREEN, 3);
            
            Render.player = Render.guest;

        }
        Text statusP1 = new Text(languageReader.readLine(5), Resources.FONT_MENU_PATH, 30, Color.GREEN, 3);
        //p2 = new Text("Jugador 2: ", Resources.FONT_MENU_PATH, 30, Color.WHITE, 3);
        //statusP2 = new Text("CONNECTED", Resources.FONT_MENU_PATH, 30, Color.RED, 3);
        match = new TextButton(languageReader.readLine(6));
        match.setPosition(600,400);
        addListener();

        p1.setPosition(300,600);
        statusP1.setPosition(300,550);
        p2.setPosition(300,200);
        statusP2.setPosition(300, 150);
        if(Render.hosting)stage.addActor(match);
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
            if(Render.hosting){
                matchFinder();
            }else{
                if(Render.guest.getMessage().equalsIgnoreCase("start")){
                    Render.guest.resetMessage();
                    Render.app.setScreen(Render.DRAFTSCREEN);
                }
            }
        }catch(IOException e){
            System.err.println(e.getMessage());
        }
        Render.Batch.end();
    }
    private void addListener(){
        match.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(!Render.host.isP2connected() && !finding){
                    Render.host.start();
                    System.out.println("La hebra 2 va por su cuenta");
                } else if (finding && !Render.host.isP2connected()) {
                    cancelSearch();
                }else if(Render.host.isP2connected()){
                    try {
                        Render.host.sendMessage("start");
                        configured = false;
                        Render.app.setScreen(Render.DRAFTSCREEN);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
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
        if (Render.host.isP2connected()) {
            match.setText(languageReader.readLine(7));
            if(!configured) {
                statusP2.setColor(Color.GREEN);
                //Render.host.receivePlayer2();
                p2.setText(languageReader.readLine(4) + Render.host.getPlayer2().getName());
                configured = true;
                System.out.println("Jugador conectado");
                //Render.host.sendPlayer1();
            }
        }else{
            match.setText(languageReader.readLine(6));
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

    /**
     * Devuelve la dirección codificada de forma que cada dos cifras representan un numero en base 16
     * y su valor es su distancia a A, por ejemplo, AAAAAAAA es 0.0.0.0
     * <p>ip debe ser valida</p>
     * @param ip String de la ip que codificar
     * @return String que representa la ip
     */
    public static String obfuscateIP(String ip){
        StringBuilder res = new StringBuilder();
        int[] valores = new int[4],cifras = new int[8]; //Valores guarda los valores de cada parte
        String[] partes = ip.split("\\."); //Dividimos la ip en cuatro partes
        assert partes.length == 4: "Bruh";
        for (int i = 0; i < 4; i++) {
            valores[i] = Integer.parseInt(partes[i]);
            cifras[2*i] = (byte) (valores[i]>>4); //Una cifra en base 16 son 4 en base 2
            cifras[2*i+1] = (byte) (valores[i]&15); //Los cuatro primeros bits son los cuatro primeros bits
        }
        for (int i = 0; i < 8; i++) {
            res.append((char) ('A' + cifras[i]));
        }
        return res.toString();
    }

    /**
     * Decodifica direcciones ip encriptadas por obfuscarIP
     * @param cod ip codificada
     * @return String de la ip valida
     */
    public static String decodeIP(String cod){
        StringBuilder res = new StringBuilder();
        int[] valores = new int[4];
        int cifra1,cifra2;
        for (int i = 0; i < 4; i++) {
            if (cod.charAt(2*i) > 'P' || cod.charAt(2*i+1) < 'A') throw new IllegalArgumentException();
            cifra1 = cod.charAt(2*i) - 'A';
            cifra2 = cod.charAt(2*i+1) - 'A';
            res.append((cifra1<<4) + cifra2);
            if (i < 3) res.append('.');
        }
        return res.toString();
    }
}
