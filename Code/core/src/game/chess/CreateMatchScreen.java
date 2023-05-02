package game.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import interaccionFichero.LectorLineas;
import multiplayer.Guest;
import utils.*;

import static org.mockito.ArgumentMatchers.intThat;

import java.io.IOException;
import java.util.Objects;

public class CreateMatchScreen extends AbstractMenuScreen{
	
    private TextField textField;
    private LectorLineas languageReader, configReader;
    
    private Table popUpTable;
    private TextButton[] popUpButtons;
    
    private boolean found = false;
    private boolean searchStarted;
    
    @Override
    public void show() {
    	configReader = new LectorLineas("files/config.txt"); //Lector del txt configuracion para sacar el idioma
    	languageReader = new LectorLineas("files/lang/"+ configReader.leerLinea(Settings.language) + "matchmaking.txt"); //Abrimos el idioma que toca del archivo configuracion
    	
    	//Nueva tabla para el PopUp
    	popUpTable = new Table();
    	popUpTable.setFillParent(true);
    	popUpTable.setVisible(false);
    	
//    	popUpTable.debug();
    	
    	searchStarted = false;
    	
    	super.show();
        
//        Image logo = new Image(Resources.LOGO_PATH);
//        logo.setPosition(800,-50);
//        logo.setSize(500, 500);
//        logo.setTransparency(0.25f);
//        stage.addActor(logo);

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
     * Método que provoca el cambio de pantalla cuando se conecta correctamente al host
     * @throws IOException
     * @throws InterruptedException
     */
    public void update() throws IOException, InterruptedException {
    /*
        if(create.isPressed()){
            Render.LOBBYSCREEN.create("Victor", true);
            Render.app.setScreen(Render.LOBBYSCREEN);
        } else if (join.isPressed() && !finding) {
            finding = true;
            System.out.println();
            Render.guest.start();
        }
        if(join.isPressed()){
            System.out.println(Render.guest.getStatus());
        }*/
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
        
        title = new Label(languageReader.leerLinea(1)
                , Render.skin, "TitleStyle");
        for(int i =  0; i < textButton.length-1; i++){
        	textButton[i] = new TextButton(languageReader.leerLinea(8+i));
        	textButton[i].addAnimation();
        }
        
        textButton[2] = new TextButton(languageReader.leerLinea(4));
        textButton[2].addAnimation();
        
        
        //POPUP TABLE
        popUpButtons[0] = new TextButton("Buscar");
        popUpButtons[1] = new TextButton("Cancelar");
        textField = new TextField(languageReader.leerLinea(10));
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
     * Añade los listeners a todos los botones de la escena. Los botones normales se llaman en el "super.addListeners()"
     */
    @Override
    protected void addListeners(){
    	super.addListeners();
//        textField.addListener(new InputListener(){
//            @Override
//            public boolean keyDown(InputEvent event, int keycode) {
//                if(keycode == Input.Keys.ENTER && !textField.getText().equals("")){
//                    try {
//                        Render.guest.connect(LobbyScreen.decodeIP(textField.getText().toUpperCase()));
//                        found = true;
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//                return true;
//            }
//        });
    	
        //Boton de buscar
        popUpButtons[0].addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		super.clicked(event, x, y);
        		if(!textField.getText().equals("")) {
        			try {
        				popUpTable.addAction(Actions.fadeOut(0.5f));
        				searchStarted = true;
        				//-1 para que no cambie de pantalla en selectScreen
        				activatedTextButton = -1;
                        Render.guest.connect(LobbyScreen.decodeIP(textField.getText().toUpperCase()));
        				found = true;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
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
    	if(button == 0) {
    		Render.LOBBYSCREEN.create("Victor", true);
    		Render.app.setScreen(Render.LOBBYSCREEN);
    	}else if(button == 1) {
            Render.guest = new Guest("Vega");
            activatePopUpTable();
    	}else if(button == 2) {
    		Render.app.setScreen(Render.MATCHMAKINGSCREEN);
    	}else if(button == 3) {
    		//BOTON SOLO ACCESIBLE DESDE POPUPTABLE
    		//VUELVE A LA PANTALLA DE SELECCION CREATEMATCH
    		Render.app.setScreen(Render.CREATEMATCHSCREEN);
    	}
    }
    
    @Override
    protected void addActors() {
    	super.addActors();
    	stage.addActor(popUpTable);
    }
    
    /*
     * Activa la animación de la popUpTable y activa sus inputs
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

