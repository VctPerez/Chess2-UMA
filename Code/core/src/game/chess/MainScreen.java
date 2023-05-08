package game.chess;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import utils.*;
import interaccionFichero.*;

public class MainScreen extends AbstractMenuScreen {
	
    LectorLineas languageReader, configReader;
    
    @Override
    public void show() {
    	
    	configReader = new LectorLineas("files/config.txt"); //Lector del txt configuracion para sacar el idioma
    	languageReader = new LectorLineas("files/lang/"+ configReader.leerLinea(Settings.language) + "main.txt"); //Abrimos el idioma que toca del archivo configuracion
    	
    	super.show();


		if(!Render.bgMusic.isPlaying()){
			Render.bgMusic.setLooping(true);
			Render.bgMusic.setVolume(Settings.musicVolume);
			Render.bgMusic.play();
		}
        
    }
    @Override
    protected void createTableElements() {
    	
    	textButton = new TextButton[4];
    	
    	title = new Label("chess 2", Render.skin, "TitleStyle");
    	
    	textButton[0] = new TextButton(languageReader.leerLinea(1));//Jugar = Linea 1
    	textButton[1] = new TextButton(languageReader.leerLinea(4));//Configuracion = Linea 4
    	textButton[2] = new TextButton(languageReader.leerLinea(7));//Reglas = Linea 7
    	textButton[3] = new TextButton(languageReader.leerLinea(3));//Salir = Linea 3;
    	    	
    	
    	for(int i = 0; i < textButton.length; i++) {
    		textButton[i].addAnimation();
    		textButton[i].addSounds();
    	}
    }
    
    /**
     * Dado el indice de un TextButton, realiza su accion correspondiente
     * @param button
     */
    @Override
    protected void selectScreen(int button) {
    	if(button == 0) {
    		Render.app.setScreen(Render.MATCHMAKINGSCREEN);
    	}else if(button == 1) {
    		Render.app.setScreen(Render.CONFIGSCREEN);
    	}else if(button == 2) {
    		Render.app.setScreen(Render.MANUALSCREEN);
    	}else if(button == 3) {
    		Gdx.app.exit();
    	}
    }

    
}
