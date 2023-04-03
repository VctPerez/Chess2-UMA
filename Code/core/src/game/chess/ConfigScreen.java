package game.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import interaccionFichero.LectorLineas;
import utils.*;

public class ConfigScreen extends AbstractScreen {
    TextButton home,language;
    Text homeText,Titulo,volumeText,languageText;
    Image background,Logo,news;
    LectorLineas languageReader, configReader;
    
    @Override
    public void show() {
    	
    	//Abrir los ficheros de configuracion e idioma
    	configReader = new LectorLineas("files/config.txt"); //Lector del txt configuracion para sacar el idioma
    	languageReader = new LectorLineas("files/lang/"+ configReader.leerLinea(1) + "main.txt"); //Abrimos el idioma que toca del archivo configuracion
    	
    	//Fuente Arial para probar
    	Titulo = new Text(Resources.FONT_MENU_PATH,100,Color.WHITE,3);
    	Titulo.setText(languageReader.leerLinea(4)); //Configuracion = Linea 4
    	homeText = new Text(Resources.FONT_MENU_PATH,50,Color.WHITE,3);
    	homeText.setText(languageReader.leerLinea(2)); //Inicio = Linea 2
    	volumeText = new Text(Resources.FONT_MENU_PATH,50,Color.WHITE,3);
    	volumeText.setText(languageReader.leerLinea(5)); //Volumen = Linea 5
    	languageText = new Text(Resources.FONT_MENU_PATH,50,Color.WHITE,3);
    	languageText.setText(languageReader.leerLinea(6)); //Idioma = Linea 6
    	Titulo.setPosition(100,600);
        homeText.setPosition(100,400);
        volumeText.setPosition(100,300);
        languageText.setPosition(100,200);
        home = new TextButton(homeText);
        language = new TextButton(languageText);
        Logo = new Image("Logo_Blanco.png");
        Logo.setPosition(800,-50);
        Logo.setSize(500, 500);
        Logo.setTransparency(0.25f);
        
        Gdx.input.setInputProcessor(Render.inputs);
    }

    @Override
    public void render(float delta) {
        Render.clearScreen();

        Render.camera.update();
        Render.Batch.setProjectionMatrix(Render.camera.combined);

        Render.Batch.begin();
        //---------------
        
        Titulo.draw(Render.Batch, 0);
        Logo.draw(Render.Batch, 0);
        //news.draw(Render.Batch);
        home.draw(Render.Batch,0);
        volumeText.draw(Render.Batch, 0);
        language.draw(Render.Batch,0);
        
        if(home.isSelected()){
            Render.app.setScreen(Render.MAINSCREEN);
        }
        if(language.isSelected()){
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
