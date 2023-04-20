package game.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import elements.Background;
import interaccionFichero.LectorLineas;
import utils.Image;
import utils.Render;
import utils.Resources;
import utils.Text;
import utils.TextButton;
import utils.TextField;

public class ManualScreen extends AbstractScreen{

		public static Stage stage;
	
		TextButton[] textButton;
		Label title;
		Table table;
	    LectorLineas languageReader, configReader;
	    
	    @Override
	    public void show() {
	    	stage = new Stage(new FitViewport(1280, 720));
	    	
	    	table = new Table();
	    	table.setFillParent(true);
	    	
	    	//Abrir los ficheros de configuracion e idioma
	    	configReader = new LectorLineas("files/config.txt"); //Lector del txt configuracion para sacar el idioma
	    	languageReader = new LectorLineas("files/lang/"+ configReader.leerLinea(1) + "Manual.txt"); //Abrimos el idioma que toca del archivo configuracion
	    	
	    	//Inicializar los elementos de la escena
	    	createTableElements();
		    	
		    //Introducir los elementos en la table
		    setupTable();
		    //Añadir todos los actores a la escena;
	    	addActors();
	        
	        Gdx.input.setInputProcessor(stage);
	    }

	    private void addActors() {
	        stage.addActor(table);
		}

		private void setupTable() {
			table.left().pad(50);
	    	table.add(title).left().space(50);
	    	table.row();
	    	for (int i = 0 ; i < textButton.length ; i++) {
	    		table.add(textButton[i]).left().space(25);
	        	table.row();
	    	}
		}

		private void createTableElements() {
	    	
	    	textButton = new TextButton[3];
	    	
	    	title = new Label("Manual", Render.app.getManager().get(Resources.SKIN_PATH,Skin.class), "TitleStyle");
	    	
	    	textButton[0] = new TextButton(languageReader.leerLinea(2));
	    	textButton[1] = new TextButton(languageReader.leerLinea(3));
	    	textButton[2] = new TextButton(languageReader.leerLinea(1));
	    }

		@Override
	    public void render(float delta) {
	        Render.clearScreen();

	        Render.camera.update();
	        Render.Batch.setProjectionMatrix(Render.camera.combined);

	        Render.Batch.begin();
	        //---------------
	        
	        stage.act();
	        stage.draw();
	        
	        if(textButton[0].isPressed()){
	            Render.bgMusic.stop();
	            Render.app.setScreen(new ClassicManScreen());
	            //Render.app.setScreen(Render.CREATEMATCHSCREEN);
	        }
	        if(textButton[1].isPressed()) {

	        }
	        if(textButton[2].isPressed()){
	        	Render.app.setScreen(Render.MAINSCREEN);
	        }
	        
	        //-----------------
	        Render.Batch.end();
	    }

	    @Override
	    public void resize(int width, int height) {
	        Render.SCREEN_WIDTH = width;
	        Render.SCREEN_HEIGHT = height;
	        Render.camera.setToOrtho(false, width, height);
	    }

	    @Override
	    public void dispose() {
	        // TODO: 21/03/2023  
	    }
	}

