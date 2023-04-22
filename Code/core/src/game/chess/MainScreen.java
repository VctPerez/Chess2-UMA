package game.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;

import elements.Background;
import utils.*;
import interaccionFichero.*;

public class MainScreen extends AbstractScreen {
	public static Stage stage;	
	
    TextButton[] textButton;
    Table table;
    Label title;
    LectorLineas languageReader, configReader;

    
    
    @Override
    public void show() {
    	
    	stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
    	table = new Table();
    	table.setFillParent(true);
   	    //table.debug();
    	
    	
    	//Abrir los ficheros de configuracion e idioma
    	configReader = new LectorLineas("files/config.txt"); //Lector del txt configuracion para sacar el idioma
    	languageReader = new LectorLineas("files/lang/"+ configReader.leerLinea(Settings.language) + "main.txt"); //Abrimos el idioma que toca del archivo configuracion
//    	languageReader = new LectorLineas("files/lang/esp/main.txt"); //Abrimos el idioma que toca del archivo configuracion
     
    	createTableElements();
        setupTable();
        addActors();
       
        //TODO scroll con imagenes estilo "noticias del juego"
        //news = new Image(Resources.LOGO_PATH);
        //news.setPosition(800,-30);
        //news.setSize(500, 500);
        //news.setTransparency(0.25f);
        
        Gdx.input.setInputProcessor(stage);
        Render.bgMusic = Render.app.getManager().get(Resources.MENU_THEME);
        Render.bgMusic.setLooping(true);
        Render.bgMusic.setVolume(Settings.musicVolume);
        Render.bgMusic.play();
    }

    @Override
    public void render(float delta) {
        Render.clearScreen();
        //---------------
        stage.act();
        stage.draw();
        
        if(textButton[0].isPressed()){
            Render.bgMusic.stop();
            Render.app.setScreen(Render.MATCHMAKINGSCREEN);
        }
        if(textButton[1].isPressed()) {
        	Render.app.setScreen(Render.CONFIGSCREEN);
        }
        if(textButton[2].isPressed()){
        	Render.app.setScreen(Render.MANUALSCREEN);
        }
        if(textButton[3].isPressed()) {
        	Gdx.app.exit();
        }
        
        //-----------------
//        Render.Batch.end();
    }
    
    private void createTableElements() {
    	
    	textButton = new TextButton[4];
    	
    	title = new Label("chess 2", Render.skin, "TitleStyle");
    	
    	textButton[0] = new TextButton(languageReader.leerLinea(1));//Jugar = Linea 1
    	textButton[1] = new TextButton(languageReader.leerLinea(4));//Configuracion = Linea 4
    	textButton[2] = new TextButton(languageReader.leerLinea(7));//Reglas = Linea 7
    	textButton[3] = new TextButton(languageReader.leerLinea(3));//Salir = Linea 3; 
    }
    
    private void addActors() {
        stage.addActor(table);
    }
    
    private void setupTable() {
    	table.left().pad(50);
    	table.defaults().left().space(40);
    	table.add(title);
    	table.row();
    	for (int i = 0 ; i < textButton.length ; i++) {
    		table.add(textButton[i]);
        	table.row();
    	}
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void resize(int width, int height) {
        Render.SCREEN_WIDTH = width;
        Render.SCREEN_HEIGHT = height;
        
        stage.getViewport().update(width, height);
    }

    @Override
    public void dispose() {
       super.dispose();
       stage.dispose();
    }
    
}
