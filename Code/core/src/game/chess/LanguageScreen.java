package game.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;

import interaccionFichero.*;
import utils.*;

public class LanguageScreen extends AbstractScreen {

    IOS inputs = new IOS();
    TextButton home,exit,config;
    Text homeText,exitText,Language,configText;
    Image background,Logo,news;
    LectorLineas languageReader, configReader, languageConfigReader;
    EscritorLineas languageSettingWriter;
    
    Text spanishText,englishText;
    TextButton spanish,english;
    
    @Override
    public void show() {
    	
    	//Abrir los ficheros de configuracion e idioma
    	configReader = new LectorLineas("files/config.txt"); //Lector del txt configuracion para sacar el idioma
    	languageConfigReader = new LectorLineas("files/languages.txt"); //Lector del txt donde vienen todos los idiomas implementados
    	languageReader = new LectorLineas("files/lang/"+ configReader.leerLinea(1)); //Abrimos el idioma que toca del archivo configuracion
    	languageSettingWriter = new EscritorLineas("files/config.txt"); //Para actualizar la configuracion abrimos el escritor en el config.txt 
    	
    	//Fuente Arial para probar
    	Language = new Text(Resources.FONT_MENU_PATH,100,Color.WHITE,5);
    	Language.setText(languageReader.leerLinea(6)); //Idioma = Linea 6
    	
    	//Menu idiomas
    	spanishText = new Text(Resources.FONT_MENU_PATH,50,Color.WHITE,5);
    	spanishText.setText(languageConfigReader.leerLinea(1)); //Espa�ol = Linea 1 
    	englishText = new Text(Resources.FONT_MENU_PATH,50,Color.WHITE,5);
    	englishText.setText(languageConfigReader.leerLinea(2)); //Ingles = Linea 2 
    	
    	//Menu inferior
    	homeText = new Text(Resources.FONT_MENU_PATH,28,Color.WHITE,5);
    	homeText.setText(languageReader.leerLinea(2)); //Inicio = Linea 2
    	configText = new Text(Resources.FONT_MENU_PATH,28,Color.WHITE,5);
    	configText.setText(languageReader.leerLinea(4)); //Configuracion = Linea 4
    	exitText = new Text(Resources.FONT_MENU_PATH,28,Color.WHITE,5);
    	exitText.setText(languageReader.leerLinea(3)); //Salir = Linea 3
    	Language.setPosition(500,600);
    	spanishText.setPosition(100, 400);
    	englishText.setPosition(100, 300);
        homeText.setPosition(100,100);
        configText.setPosition(220,100);
        exitText.setPosition(480,100);
        home = new TextButton(homeText);
        config = new TextButton(configText);
        exit = new TextButton(exitText);
        spanish = new TextButton(spanishText);
        english = new TextButton(englishText);
        Logo = new Image("Logo.png");
        Logo.setPosition(800,-50);
        Logo.setSize(500, 500);
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
        spanish.establish(inputs, Render.Batch);
        english.establish(inputs, Render.Batch);
        
        home.establish(inputs, Render.Batch);
        config.establish(inputs, Render.Batch);
        exit.establish(inputs, Render.Batch);
        
        if(spanish.isSelected()) {
        	languageSettingWriter.escribirLinea(1, "esp.txt"); //La linea 1 de la configuracion contiene el idioma
        	Render.app.setScreen(new LanguageScreen());
        }
        if(english.isSelected()) {
        	languageSettingWriter.escribirLinea(1, "eng.txt"); //El nombre es el nombre del archivo txt de idioma
        	Render.app.setScreen(new LanguageScreen());
        }
        
        
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
