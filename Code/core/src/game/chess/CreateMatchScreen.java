package game.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import interaccionFichero.LineReader;
import multiplayer.Guest;
import utils.Render;
import utils.Settings;
import utils.TextButton;
import utils.TextField;

import java.io.IOException;

public class CreateMatchScreen extends AbstractMenuScreen{
	
    private TextField textField;
    private LineReader languageReader, configReader;
    
    private Table popUpTable;
    private TextButton[] popUpButtons;
    
    private boolean found = false;
    private boolean searchStarted;
    
    @Override
    public void show() {
    	configReader = new LineReader("files/config.txt"); //Lector del txt configuracion para sacar el idioma
    	languageReader = new LineReader("files/lang/"+ configReader.readLine(Settings.language) + "matchmaking.txt"); //Abrimos el idioma que toca del archivo configuracion
    	
    	//Nueva tabla para el PopUp
    	popUpTable = new Table();
    	popUpTable.setFillParent(true);
    	popUpTable.setVisible(false);
    	
    	searchStarted = false;
    	
    	super.show();

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
    	super.render(delta);
    	
    	//Cuando el guest le da algo y ya ha acabado la animacion
    	if(searchStarted && !popUpTable.hasActions()) {
    		//Si le ha dado a buscar
    		if(activatedTextButton == -1) {
    			try{
    				update();
    			}catch(IOException | InterruptedException e){
    				System.err.println(e.getMessage());
    			}
    		//Si le da ha dado a cancelar
    		}else {
    			selectScreen(activatedTextButton);
    		}
    	}
    }
    /**
     * M�todo que provoca el cambio de pantalla cuando se conecta correctamente al host
     * @throws IOException
     * @throws InterruptedException
     */
    public void update() throws IOException, InterruptedException {
        if(found) {
            if (Render.guest.isConnected()) {
            	Render.guest.setReceiving(true);
            	Render.guest.start();
            	Render.LOBBYSCREEN.create(Render.guest.getPlayer2().getName(), false);
            	found = false;
            	Render.app.setScreen(Render.LOBBYSCREEN);
            }
        }
    }
    @Override
    protected void createTableElements(){
        textButton = new TextButton[3];
        popUpButtons = new TextButton[2];
        
        title = new Label(languageReader.readLine(1)
                , Render.skin, "TitleStyle");
        for(int i =  0; i < textButton.length-1; i++){
        	textButton[i] = new TextButton(languageReader.readLine(8+i));
        	textButton[i].addAnimation();
        	textButton[i].addSounds();
        }
        
        textButton[2] = new TextButton(languageReader.readLine(4));
        textButton[2].addAnimation();
        textButton[2].addSounds();
        
        
        //POPUP TABLE
        for(int i = 0; i < popUpButtons.length; i++) {
        	popUpButtons[i] = new TextButton(languageReader.readLine(i + 11));
        	popUpButtons[i].addSounds();
        }
        
        textField = new TextField(languageReader.readLine(10));
        textField.setAlignment(Align.center);
    }
    
    @Override
    protected void setupTable(){
    	super.setupTable();
    	
    	//POPUP TABLE
    	popUpTable.defaults().width(400).space(30);
    	popUpTable.add(textField);
    	popUpTable.row();
    	for(int i = 0; i < popUpButtons.length; i++) {
    		popUpTable.add(popUpButtons[i]);
    		popUpTable.row();
    	}
    }
    
    /**
     * A�ade los listeners a todos los botones de la escena. Los botones normales se llaman en el "super.addListeners()"
     */
    @Override
    protected void addListeners(){
    	super.addListeners();
    	
        //Boton de buscar
        popUpButtons[0].addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		super.clicked(event, x, y);
        		if(textField.getText().length() == 8) {
        			try {
        				searchStarted = true;
        				//-1 para que no cambie de pantalla en selectScreen
        				activatedTextButton = -1;
                        Render.guest.connect(LobbyScreen.decodeIP(textField.getText().toUpperCase()));
                        //Solo si no salta excepcion
                        popUpTable.addAction(Actions.fadeOut(0.5f));
                        found = true;
                    } catch (IOException | IllegalArgumentException e) {
                        textField.setText(languageReader.readLine(13));
                        System.out.println("Error de codigo");
                    }

        		}
        	}
        });
        
        //Boton de cancelar
        popUpButtons[1].addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		super.clicked(event, x, y);
        		//Activa el fadeOut y marca searchStarted a true
                popUpTable.addAction(Actions.fadeOut(0.5f));
                searchStarted = true;
                activatedTextButton = 3;
        	}
        });
    }
    
    @Override
    protected void selectScreen(int button) {
    	LineReader ProfileReader = new LineReader("files/Datos.txt");
        switch (button) {
            case 0:
                Render.LOBBYSCREEN.create(ProfileReader.readLine(1), true);// nombre del perfil, nombre base es P1 y P2
                Render.app.setScreen(Render.MODESCREEN);
                break;
            case 1:
                Render.guest = new Guest(ProfileReader.readLine(1));
                activatePopUpTable();
                break;
            case 2:
                Render.app.setScreen(Render.MATCHMAKINGSCREEN);
                break;
            case 3:
                //BOTON SOLO ACCESIBLE DESDE POPUPTABLE
                //VUELVE A LA PANTALLA DE SELECCION CREATEMATCH
                Render.app.setScreen(Render.CREATEMATCHSCREEN);
                break;
        }
    }
    
    @Override
    protected void addActors() {
    	super.addActors();
    	stage.addActor(popUpTable);
    }
    
    /*
     * Activa la animaci�n de la popUpTable y activa sus inputs
     */
    private void activatePopUpTable() {
    	animationStarted = false;
    	popUpTable.setVisible(true);
    	popUpTable.addAction(Actions.sequence(Actions.alpha(0),Actions.fadeIn(0.5f)));
    }
    
    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}

