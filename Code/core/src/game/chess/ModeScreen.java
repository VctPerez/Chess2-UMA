package game.chess;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import interaccionFichero.LineReader;
import utils.Render;
import utils.Settings;
import utils.TextButton;

public class ModeScreen extends AbstractMenuScreen{
    private LineReader languageReader, configReader;
    
    @Override
    public void show() {
    	//Abrir los ficheros de configuracion e idioma
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
        				Render.DraftController = 1;
        			}
        		}
        	});
        }
        
    }
    
    @Override
    protected void createTableElements() {
    	
    	textButton = new TextButton[3];
    	
    	title = new Label(languageReader.readLine(5), Render.skin, "TitleStyle");//Manual
    	
    	textButton[0] = new TextButton(languageReader.readLine(6));//Modified
    	textButton[1] = new TextButton(languageReader.readLine(7));//Classic
    	textButton[2] = new TextButton(languageReader.readLine(4));//Return
    	
    	for(int i = 0; i < textButton.length; i++) {
    		textButton[i].addAnimation();
    		textButton[i].addSounds();
    	}
    }
    
	@Override
	protected void selectScreen(int button) {
    	if(button == 0) {
    		Render.player1Draft.clear();
    		Render.player2Draft.clear();
    		Render.DRAFTSCREEN=new DraftScreen();
    		Render.app.setScreen(Render.DRAFTSCREEN);
    	}else if(button == 1) {
    		Render.player1Draft.clear();
    		Render.player2Draft.clear();
    		Render.app.setScreen(new GameScreen());
    	}else if(button == 2) {
    		Render.app.setScreen(Render.MATCHMAKINGSCREEN);
    	}
	}
    
    
}
