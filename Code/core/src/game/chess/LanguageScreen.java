package game.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import elements.Background;
import interaccionFichero.*;
import utils.*;

public class LanguageScreen extends AbstractScreen {
	public static Stage stage;
	Background background;	
	
    TextButton back;
    Text backText,Language;
    Image Logo;
    LineReader languageReader, configReader, languageConfigReader;
    LineWriter languageSettingWriter;
    
    Text spanishText,englishText;
    TextButton spanish,english;
    
    @Override
    public void show() {
    	stage = new Stage(new FitViewport(1280, 720));
    	background = new Background();
    	background.setColor(new Color(60/255f, 60/255f,60/255f,1f));
    	background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    	
    	//Abrir los ficheros de configuracion e idioma
    	configReader = new LineReader("files/config.txt"); //Lector del txt configuracion para sacar el idioma
    	languageConfigReader = new LineReader("files/languages.txt"); //Lector del txt donde vienen todos los idiomas implementados
    	languageReader = new LineReader("files/lang/"+ configReader.readLine(1) + "main.txt"); //Abrimos el idioma que toca del archivo configuracion
    	languageSettingWriter = new LineWriter("files/config.txt"); //Para actualizar la configuracion abrimos el escritor en el config.txt 
    	
    	//Fuente Arial para probar
    	Language = new Text(Resources.FONT_MENU_PATH,100,Color.WHITE,3);
    	Language.setText(languageReader.readLine(6)); //Idioma = Linea 6
    	
    	//Menu idiomas
    	spanishText = new Text(Resources.FONT_MENU_PATH,50,Color.WHITE,3);
    	spanishText.setText(languageConfigReader.readLine(1)); //Espa�ol = Linea 1 
    	englishText = new Text(Resources.FONT_MENU_PATH,50,Color.WHITE,3);
    	englishText.setText(languageConfigReader.readLine(2)); //Ingles = Linea 2 
    	
    	//Menu inferior
    	backText = new Text(Resources.FONT_MENU_PATH,28,Color.WHITE,3);
    	backText.setText(languageReader.readLine(2)); //Inicio = Linea 2
    	Language.setPosition(100,600);
    	spanishText.setPosition(100, 400);
    	englishText.setPosition(100, 300);
        backText.setPosition(100,100);
        back = new TextButton(languageReader.readLine(2));
        spanish = new TextButton(languageConfigReader.readLine(1));
        english = new TextButton(languageConfigReader.readLine(2));
        Logo = new Image("Logo_Blanco.png");
        Logo.setPosition(800,-50);
        Logo.setSize(500, 500);
        Logo.setTransparency(0.25f);
        
        stage.addActor(background);
        stage.addActor(Logo);
        stage.addActor(Language);
        stage.addActor(spanish);
        stage.addActor(english);
        stage.addActor(back);
        
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
        
        if(spanish.isPressed()) {
        	languageSettingWriter.escribirLinea(1, "esp/"); //La linea 1 de la configuracion contiene el idioma
        	Render.app.setScreen(Render.LANGUAGESCREEN);
        }
        if(english.isPressed()) {
        	languageSettingWriter.escribirLinea(1, "eng/"); //El nombre es el nombre del archivo txt de idioma
        	Render.app.setScreen(Render.LANGUAGESCREEN);
        }
        
        
        if(back.isPressed()){
            Render.app.setScreen(Render.CONFIGSCREEN);
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
