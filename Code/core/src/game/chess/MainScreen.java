package game.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import utils.*;
import interaccionFichero.*;

public class MainScreen extends AbstractScreen {

    IOS inputs = new IOS();
    TextButton play,exit,confg,reglas;
    Text playText,exitText,confgText,Titulo,reglasText;
    Image background,Logo,news;
    LectorLineas languageReader, configReader;
    
    @Override
    public void show() {
    	
    	//Abrir los ficheros de configuracion e idioma
    	configReader = new LectorLineas("files/config.txt"); //Lector del txt configuracion para sacar el idioma
    	languageReader = new LectorLineas("files/lang/"+ configReader.leerLinea(1) + "main.txt"); //Abrimos el idioma que toca del archivo configuracion
    	
    	//Fuente Arial para probar
    	Titulo = new Text(Resources.FONT_MENU_PATH,100,Color.WHITE,5);
    	Titulo.setText("Chess2");
    	playText = new Text(Resources.FONT_MENU_PATH,50,Color.WHITE,5);
    	playText.setText(languageReader.leerLinea(1)); //Jugar = Linea 1
    	exitText = new Text(Resources.FONT_MENU_PATH,50,Color.WHITE,5);
    	exitText.setText(languageReader.leerLinea(3)); //Salir = Linea 3
    	confgText = new Text(Resources.FONT_MENU_PATH,50,Color.WHITE,5);
    	confgText.setText(languageReader.leerLinea(4)); //Configuracion = Linea 4
    	reglasText = new Text(Resources.FONT_MENU_PATH,50,Color.WHITE,5);
    	reglasText.setText(languageReader.leerLinea(4)); //Configuracion = Linea 4
    	
    	Titulo.setPosition(100,600);
        playText.setPosition(100,400);
        exitText.setPosition(100,100);
        confgText.setPosition(100,300);
        reglasText.setPosition(100, 200);
        play = new TextButton(playText);
        exit = new TextButton(exitText);
        confg = new TextButton(confgText);
        reglas = new TextButton(reglasText);
        
        Logo = new Image("Logo.png");
        Logo.setPosition(800,-50);
        Logo.setSize(500, 500);
        //Logo.setTransparency(0.25f);
        //TODO scroll con imagenes estilo "noticias del juego"
        //news = new Image("prueba.jpg");
        //news.setPosition(850,400);
        //news.setSize(300, 200);
        Gdx.input.setInputProcessor(inputs);
    }

    @Override
    public void render(float delta) {
        Render.clearScreen();

        Render.camera.update();
        Render.Batch.setProjectionMatrix(Render.camera.combined);

        Render.Batch.begin();
        //---------------
        
        Titulo.draw();
        Logo.draw(Render.Batch);
        //news.draw(Render.Batch);
        reglas.establish(inputs, Render.Batch);
        play.establish(inputs, Render.Batch);
        exit.establish(inputs, Render.Batch);
        confg.establish(inputs, Render.Batch);
        
        if(play.isSelected()){
            Render.app.setScreen(new GameScreen());
        }
        if(exit.isSelected()) {
        	Gdx.app.exit();
        }
        if(confg.isSelected()) {
        	Render.app.setScreen(new ConfigScreen());
        }
        if(reglas.isSelected()) {
        	Render.app.setScreen(new ManualScreen());
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
