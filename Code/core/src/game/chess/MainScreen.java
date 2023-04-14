package game.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import elements.Background;
import utils.*;
import interaccionFichero.*;

public class MainScreen extends AbstractScreen {
	public static Stage stage;
	Background background;	
	
    TextButton play,exit,confg,reglas;
    Text playText,exitText,confgText,Titulo,reglasText;
    Image Logo,news;
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
    	//languageReader = new LectorLineas("files/lang/esp/main.txt"); //Abrimos el idioma que toca del archivo configuracion

    	Titulo = new Text(Resources.FONT_MENU_PATH,100,Color.WHITE,3);
    	Titulo.setText(languageReader.leerLinea(8));
    	playText = new Text(Resources.FONT_MENU_PATH,50,Color.WHITE,3);
    	playText.setText(languageReader.leerLinea(1)); //Jugar = Linea 1
    	exitText = new Text(Resources.FONT_MENU_PATH,50,Color.WHITE,3);
    	exitText.setText(languageReader.leerLinea(3)); //Salir = Linea 3
    	confgText = new Text(Resources.FONT_MENU_PATH,50,Color.WHITE,3);
    	confgText.setText(languageReader.leerLinea(4)); //Configuracion = Linea 4
    	reglasText = new Text(Resources.FONT_MENU_PATH,50,Color.WHITE,3);
    	reglasText.setText(languageReader.leerLinea(7)); //Configuracion = Linea 4
    	
    	Titulo.setPosition(100,600);
        playText.setPosition(100,400);
        exitText.setPosition(100,100);
        confgText.setPosition(100,300);
        reglasText.setPosition(100, 200);
        play = new TextButton(playText);
        exit = new TextButton(exitText);
        confg = new TextButton(confgText);
        reglas = new TextButton(reglasText);
        
        Logo = new Image(Resources.LOGO_PATH);
        Logo.setPosition(800,-50);
        Logo.setSize(500, 500);
        Logo.setTransparency(0.25f);
        
        stage.addActor(background);
        stage.addActor(Logo);
        stage.addActor(play);
        stage.addActor(exit);
        stage.addActor(confg);
        stage.addActor(reglas);
        stage.addActor(Titulo);
       
        //TODO scroll con imagenes estilo "noticias del juego"
        //news = new Image(Resources.LOGO_PATH);
        //news.setPosition(800,-30);
        //news.setSize(500, 500);
        //news.setTransparency(0.25f);
        
        Gdx.input.setInputProcessor(Render.inputs);
        Render.bgMusic = Render.app.getManager().get(Resources.MENU_THEME);
        Render.bgMusic.setLooping(true);
        Render.bgMusic.play();
    }

    @Override
    public void render(float delta) {
        Render.clearScreen();

        Render.camera.update();
        Render.Batch.setProjectionMatrix(Render.camera.combined);

        Render.Batch.begin();
        //---------------


        stage.draw();
        
        if(play.isPressed()){
            Render.bgMusic.stop();
            Render.app.setScreen(new DraftScreen());
            //Render.app.setScreen(Render.CREATEMATCHSCREEN);
        }
        if(exit.isPressed()) {
        	Gdx.app.exit();
        }
        if(confg.isPressed()) {
        	Render.app.setScreen(Render.CONFIGSCREEN);
        }
        if(reglas.isPressed()){
        	Render.app.setScreen(Render.MANUALSCREEN);
        }
        
        //-----------------
        Render.Batch.end();
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void resize(int width, int height) {
        Render.SCREEN_WIDTH = width;
        Render.SCREEN_HEIGHT = height;
        Render.camera.setToOrtho(false, width, height);
    }

    @Override
    public void dispose() {
       super.dispose();
    }
}
