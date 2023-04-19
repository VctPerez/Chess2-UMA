package game.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import elements.Background;
import interaccionFichero.LectorLineas;
import utils.Image;
import utils.Render;
import utils.Resources;
import utils.Text;
import utils.TextButton;

public class ManualScreen extends AbstractScreen{

		public static Stage stage;
		Background background;	
	
	    TextButton volver,clasico,modificado;
	    Text volverText,Titulo,clasicoText,modificadoText;
	    Image Logo;
	    LectorLineas languageReader, configReader;
	    
	    @Override
	    public void show() {
	    	stage = new Stage(new FitViewport(1280, 720));
	    	background = new Background();
	    	background.setColor(new Color(60/255f, 60/255f,60/255f,1f));
	    	background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	    	
	    	//Abrir los ficheros de configuracion e idioma
	    	configReader = new LectorLineas("files/config.txt"); //Lector del txt configuracion para sacar el idioma
	    	languageReader = new LectorLineas("files/lang/"+ configReader.leerLinea(1) + "Manual.txt"); //Abrimos el idioma que toca del archivo configuracion
	    	
	    	//Fuente Arial para probar
	    	Titulo = new Text(Resources.FONT_MENU_PATH,100,Color.WHITE,3);
	    	Titulo.setText(languageReader.leerLinea(4));
	    	volverText = new Text(Resources.FONT_MENU_PATH,28,Color.WHITE,3);
	    	volverText.setText(languageReader.leerLinea(1)); //Jugar = Linea 1
	    	clasicoText = new Text(Resources.FONT_MENU_PATH,50,Color.WHITE,3);
	    	clasicoText.setText(languageReader.leerLinea(2)); //Salir = Linea 3
	    	modificadoText = new Text(Resources.FONT_MENU_PATH,50,Color.WHITE,3);
	    	modificadoText.setText(languageReader.leerLinea(3)); //Configuracion = Linea 
	    	
	    	Titulo.setPosition(100,600);
	        volverText.setPosition(100,100);
	        clasicoText.setPosition(100,400);
	        modificadoText.setPosition(100,300);
	        volver = new TextButton(languageReader.leerLinea(1));
	        clasico = new TextButton(languageReader.leerLinea(2));
	        modificado = new TextButton(languageReader.leerLinea(3));
	        
	        Logo = new Image("Logo_Blanco.png");
	        Logo.setPosition(800,-50);
	        Logo.setSize(500, 500);
	        Logo.setTransparency(0.25f);
	        
	        stage.addActor(background);
	        stage.addActor(Logo);
	        stage.addActor(Titulo);
	        stage.addActor(volver);
	        stage.addActor(clasico);
	        stage.addActor(modificado);
	        
	        Gdx.input.setInputProcessor(Render.inputs);
	    }

	    @Override
	    public void render(float delta) {
	        Render.clearScreen();

	        Render.camera.update();
	        Render.Batch.setProjectionMatrix(Render.camera.combined);

	        Render.Batch.begin();
	        //---------------
	        
	        stage.draw();
	        
	        if(volver.isPressed()){
	            Render.app.setScreen(Render.MAINSCREEN);
	        }
	        if(clasico.isPressed()) {
	        	Render.app.setScreen(new ClassicManScreen());
	        }
	        if(modificado.isPressed()) {
	        	//TODO
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

