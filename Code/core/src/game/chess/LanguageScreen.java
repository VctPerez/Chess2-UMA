package game.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;

import interaccionFichero.LectorLineas;
import utils.*;

public class LanguageScreen extends AbstractScreen {

    IOS inputs = new IOS();
    TextButton home,exit,config;
    Text homeText,exitText,Language,configText;
    Image background,Logo,news;
    LectorLineas languageReader, configReader;
    
    @Override
    public void show() {
    	
    	//Abrir los ficheros de configuracion e idioma
    	configReader = new LectorLineas("files/config.txt"); //Lector del txt configuracion para sacar el idioma
    	languageReader = new LectorLineas("files/lang/"+ configReader.leerLinea(1)); //Abrimos el idioma que toca del archivo configuracion
    	
    	//Fuente Arial para probar
    	Language = new Text(Resources.FONT_MENU_PATH,100,Color.WHITE,5);
    	Language.setText(languageReader.leerLinea(6)); //Idioma = Linea 6
    	homeText = new Text(Resources.FONT_MENU_PATH,50,Color.WHITE,5);
    	homeText.setText(languageReader.leerLinea(2)); //Inicio = Linea 2
    	configText = new Text(Resources.FONT_MENU_PATH,50,Color.WHITE,5);
    	configText.setText(languageReader.leerLinea(4)); //Configuracion = Linea 4
    	exitText = new Text(Resources.FONT_MENU_PATH,50,Color.WHITE,5);
    	exitText.setText(languageReader.leerLinea(3)); //Salir = Linea 3
    	Language.setPosition(300,600);
        homeText.setPosition(100,400);
        configText.setPosition(100,300);
        exitText.setPosition(100,200);
        home = new TextButton(homeText);
        config = new TextButton(configText);
        exit = new TextButton(exitText);
        Logo = new Image("Logo.png");
        Logo.setPosition(800,-50);
        Logo.setSize(500, 500);
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
        
        Language.draw();
        Logo.draw(Render.Batch);
        //news.draw(Render.Batch);
        home.establish(inputs, Render.Batch);
        config.establish(inputs, Render.Batch);
        exit.establish(inputs, Render.Batch);
        
        if(home.isSelected()){
            Render.app.setScreen(new MainScreen());
        }
        if(config.isSelected()) {
        	Render.app.setScreen(new ConfigScreen());
        }
        if(exit.isSelected()) {
        	Gdx.app.exit();
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
