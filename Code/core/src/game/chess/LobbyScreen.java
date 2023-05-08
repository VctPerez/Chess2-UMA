package game.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import interaccionFichero.LineReader;
import multiplayer.Host;
import multiplayer.Player;
import utils.*;

import static org.mockito.ArgumentMatchers.intThat;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;

public class LobbyScreen extends AbstractScreen{
    private Stage stage;
    private Table leftTable;
    private Table rightTable;
    private Table bottomTable;
    
    private LineReader configReader, languageReader;
    
    private Player player1, player2;
    
    private TextButton[] textButton;
    private Label[] label;
 
    
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
        
        leftTable = new Table();
        leftTable.setFillParent(true);
//        leftTable.debug();
        
        rightTable = new Table();
        rightTable.setFillParent(true);
//        rightTable.debug();
        
        bottomTable = new Table();
        bottomTable.setFillParent(true);
        bottomTable.setVisible(false);
//        bottomTable.debug();
        
        configReader = new LineReader("files/config.txt"); //Lector del txt configuracion para sacar el idioma
        languageReader = new LineReader("files/lang/"+ configReader.readLine(Settings.language) + "lobby.txt"); //Abrimos el idioma que toca del archivo configuracion
        
        createTableElements();
        
        setupTable();
        
        addActors();
        
        
        try {
        	if(Render.hosting) {
        		Render.host = new Host(player1);
        		Render.player = Render.host;
        	}else {
        		Render.player = Render.guest;
        	}
			setTextValues(Render.hosting);		
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
        
        
        addListener();


        Gdx.input.setInputProcessor(stage);
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
                    Render.app.setScreen(new DraftScreen());
                }
            }
        }catch(IOException e){
            System.err.println(e.getMessage());
        }
        Render.Batch.end();
    }
    
    private void createTableElements() {
    	// TITLE - INVCODE - P1 - P1NAME - P2 - P2NAME
    	label = new Label[6];
    	// SEARCH - CANCEL - START
    	textButton = new TextButton[3];
    	
    	label[0] = new Label(languageReader.readLine(1), Render.skin, "TitleStyle");
    	label[1] = new Label("", Render.skin, "SmallTextStyle");
    
    	
    	for(int i = 2; i < label.length ; i++) {
    		if(i % 2 == 0) {
    			label[i] = new Label(languageReader.readLine(i), Render.skin,"SmallTextStyle");
    		}else {
    			label[i] = new Label("", Render.skin,"PlayerONStyle");
    		}
    	}
    	
    	
    	for(int i = 0; i < textButton.length ; i++) {	
    		if(i == 2) {
    			textButton[i] = new TextButton(languageReader.readLine(8),"BigStyle");
    		}else {
    			textButton[i] = new TextButton("");	
    		}
    		textButton[i].addSounds();
    	}
    	
    }
    /**
     * Crea la estructura de las 3 tablas:
     * -leftTable es la tabla que controla el titulo, el codigo y los botones.
     * -rightTable controla la tabla de jugadores
     * -bottomTable es simplemente un boton para empezar la partida.(Era mas facil hacer otra tabla)
     */
    private void setupTable(){
    	
    	//LEFT TABLE
    	leftTable.padLeft(50).padTop(60).left().top();
    	leftTable.defaults().left();
    	
    	leftTable.add(label[0]).space(30);
    	leftTable.row();
    	
    	leftTable.add(label[1]);
    	leftTable.row();
    	
    	leftTable.add(textButton[0]).padTop(90);
    	leftTable.row();
    	leftTable.add(textButton[1]).space(50);
    	
    	//RIGHT TABLE
    	rightTable.padRight(50).right();
    	rightTable.defaults().left().fillX().minWidth(400);
    	for(int i = 2 ; i < label.length ; i++) {
    		label[i].setAlignment(Align.center);
    		rightTable.add(label[i]).space(15);
    		rightTable.row();
    	}
    	//BOTTOM TABLE
    	bottomTable.padBottom(50).bottom();
    	bottomTable.add(textButton[2]);
    }
    
    /**
     * Modifica los valores de los elementos de la UI en funci�n del rol.
     * @param hosting Booleano que determina si el proceso es host o guest.
     * @throws UnknownHostException
     */
    private void setTextValues(boolean hosting) throws UnknownHostException {
    	if (hosting) {
    		textButton[0].setText(languageReader.readLine(6));
    		label[3].setText(player1.getName());
    		label[5].setText("...");
    		label[5].setStyle(Render.skin.get("PlayerOFFStyle",LabelStyle.class));
    		label[1].setText(languageReader.readLine(3) + obfuscateIP(Inet4Address.getLocalHost().getHostAddress()));
    	}else {
    		textButton[0].setText(languageReader.readLine(5));
    		textButton[0].setChecked(true);
    		textButton[0].setTouchable(Touchable.disabled);
    		label[3].setText(Render.guest.getPlayer1().getName());
    		label[5].setText(player2.getName());
    	}
    	
    	textButton[1].setText(languageReader.readLine(9));
    	textButton[2].setText(languageReader.readLine(10));
    }
    
    private void addListener(){
    	//SEARCH BUTTON
        textButton[0].addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	super.clicked(event, x, y);
                if(!Render.host.isP2connected() && !finding){
                    Render.host.start();
                    textButton[0].setText(languageReader.readLine(7));
                    System.out.println("La hebra 2 va por su cuenta");
                } else if (finding && !Render.host.isP2connected()) {
                    cancelSearch();
                    textButton[0].setText(languageReader.readLine(6));
                }
            }
        });
        
        //START BUTTON
        textButton[2].addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		super.clicked(event, x, y);
        		if(Render.host.isP2connected()){
                    try {
                        Render.host.sendMessage("start");
                        configured = false;
                        Render.app.setScreen(new DraftScreen());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        	}
        });
    }
    
    /**
     * Meodo que se llama en cada frame por el host. Una vez encontrado el oponente, actualiza sus valores
     * y activa el boton de empezar partida
     * @throws IOException
     */
    public void matchFinder() throws IOException {
        finding = Render.host.isServerOpen();
        if (Render.host.isP2connected()) {
            textButton[0].setText(languageReader.readLine(5));
            if(!configured) {
            	//Desactiva el boton de buscar
        		textButton[0].setTouchable(Touchable.disabled);
        		//Activa el boton de empezar
            	bottomTable.setVisible(true);
            	bottomTable.addAction(Actions.sequence(Actions.alpha(0),Actions.fadeIn(0.5f)));
            	//Actualiza la lista de jugadores
                label[5].setText(Render.host.getPlayer2().getName());
                label[5].setStyle(Render.skin.get("PlayerONStyle",LabelStyle.class));
                configured = true;
                System.out.println("Jugador conectado");
            }
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
    
    private void addActors() {
    	stage.addActor(Render.menuBG);
        stage.addActor(leftTable);
        stage.addActor(rightTable);
        stage.addActor(bottomTable);
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
