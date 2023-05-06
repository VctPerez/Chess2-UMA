package game.chess;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

import interaccionFichero.LineReader;
import utils.Render;
import utils.Settings;
import utils.TextButton;

public class ManualScreen extends AbstractMenuScreen{

	    LineReader languageReader, configReader;
	    
	    @Override
	    public void show() {
	    	
	    	//Abrir los ficheros de configuracion e idioma
	    	configReader = new LineReader("files/config.txt"); //Lector del txt configuracion para sacar el idioma
	    	languageReader = new LineReader("files/lang/"+ configReader.readLine(Settings.language) + "Manual.txt"); //Abrimos el idioma que toca del archivo configuracion
	    	
	    	super.show();
	    	
	    }

	    @Override
		protected void createTableElements() {
	    	
	    	textButton = new TextButton[3];
	    	
	    	title = new Label("Manual", Render.skin, "TitleStyle");//Manual
	    	
	    	textButton[0] = new TextButton(languageReader.readLine(2));//Classic
	    	textButton[1] = new TextButton(languageReader.readLine(3));//Modified
	    	textButton[2] = new TextButton(languageReader.readLine(1));//Back
	    	
	    	for(int i = 0; i < textButton.length; i++) {
	    		textButton[i].addAnimation();
	    		textButton[i].addSounds();
	    	}
	    }

		@Override
		protected void selectScreen(int button) {
	    	if(button == 0) {
	    		Render.app.setScreen(Render.CLASSICMANSCREEN);
	    	}else if(button == 1) {
	    		Render.app.setScreen(Render.MODIFIEDMANSCREEN);
	    	}else if(button == 2) {
	    		Render.app.setScreen(Render.MAINSCREEN);
	    	}
		}


	}

