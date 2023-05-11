package game.chess;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import interaccionFichero.LineReader;
import utils.Render;
import utils.Settings;
import utils.TextButton;

public class MatchMakingScreen extends AbstractMenuScreen{
	
    private LineReader languageReader, configReader;

    
    @Override
    public void show() {
    	configReader = new LineReader("files/config.txt"); //Lector del txt configuracion para sacar el idioma
    	languageReader = new LineReader("files/lang/"+ configReader.readLine(Settings.language) + "matchmaking.txt"); //Abrimos el idioma que toca del archivo configuracion
    	
    	super.show();
        
        //Inputs de los botones
        for(int i = 0 ; i < textButton.length ; i++) {
        	textButton[i].addListener(new ClickListener() {
        		@Override
        		public void clicked(InputEvent event, float x, float y) {
        			super.clicked(event, x, y);	
        			if(activatedTextButton == 0) {
        				Render.DraftController = 3;
        			}else if(activatedTextButton == 1) {
        				Render.DraftController = 1;
        			}
        		}
        	});
        }
        
    }


    @Override
    protected void createTableElements() {
    	
    	textButton = new TextButton[3];
    	
    	title = new Label(languageReader.readLine(1), Render.skin, "TitleStyle");//Manual
    	
    	textButton[0] = new TextButton(languageReader.readLine(2));//Online
    	textButton[1] = new TextButton(languageReader.readLine(3));//Local
    	textButton[2] = new TextButton(languageReader.readLine(4));//Return
    	
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
    		Render.DraftController=3;
    		Render.app.setScreen(Render.CREATEMATCHSCREEN);
    	}else if(button == 1) {
    		Render.DraftController=1;
    		Render.app.setScreen(Render.MODESCREEN);
    	}else if(button == 2) {
    		Render.app.setScreen(Render.MAINSCREEN);
    	}
    }
    
    
    @Override
    public void resize(int width, int height) {
        Render.SCREEN_WIDTH = width;
        Render.SCREEN_HEIGHT = height;
        Render.camera.setToOrtho(false, width, height);
        stage.getViewport().update(width, height);
    }

}
