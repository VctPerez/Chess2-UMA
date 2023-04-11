package game.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import elements.Background;
import interaccionFichero.LectorLineas;
import utils.*;

public class ConfigScreen extends AbstractScreen {
	public static Stage stage;
	Background background;	
	
    TextButton home,language;
    Text homeText,Titulo,volumeText,languageText;
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
    	languageReader = new LectorLineas("files/lang/"+ configReader.leerLinea(1) + "main.txt"); //Abrimos el idioma que toca del archivo configuracion
    	
    	//Fuente Arial para probar
    	Titulo = new Text(Resources.FONT_MENU_PATH,100,Color.WHITE,3);
    	Titulo.setText(languageReader.leerLinea(4)); //Configuracion = Linea 4
    	homeText = new Text(Resources.FONT_MENU_PATH,28,Color.WHITE,3);
    	homeText.setText(languageReader.leerLinea(2)); //Inicio = Linea 2
    	volumeText = new Text(Resources.FONT_MENU_PATH,50,Color.WHITE,3);
    	volumeText.setText(languageReader.leerLinea(5)); //Volumen = Linea 5
    	languageText = new Text(Resources.FONT_MENU_PATH,50,Color.WHITE,3);
    	languageText.setText(languageReader.leerLinea(6)); //Idioma = Linea 6
    	Titulo.setPosition(100,600);
        volumeText.setPosition(100,400);
        languageText.setPosition(100,300);
        homeText.setPosition(100,100);
        home = new TextButton(homeText);
        language = new TextButton(languageText);
        Logo = new Image("Logo_Blanco.png");
        Logo.setPosition(800,-50);
        Logo.setSize(500, 500);
        Logo.setTransparency(0.25f);
        
        stage.addActor(background);
        stage.addActor(Titulo);
        stage.addActor(home);
        stage.addActor(language);
        stage.addActor(volumeText);
        stage.addActor(Logo);
        
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
        
        if(home.isPressed()){
            Render.app.setScreen(Render.MAINSCREEN);
        }
        if(language.isPressed()){
            Render.app.setScreen(Render.LANGUAGESCREEN);
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
