package game.chess;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

import utils.*;
import interaccionFichero.*;

public class ProfileScreen extends AbstractMenuScreen {
	
    LineReader languageReader, configReader,dataReader;
    
    @Override
    public void show() {
    	
    	configReader = new LineReader("files/config.txt"); //Lector del txt configuracion para sacar el idioma
    	languageReader = new LineReader("files/lang/"+ configReader.readLine(Settings.language) + "Profile.txt"); //Abrimos el idioma que toca del archivo configuracion
    	dataReader = new LineReader("files/Datos.txt"); //Abrimos los datos

    	
    	super.show();
        
        Render.bgMusic = Render.app.getManager().get(Resources.MENU_THEME);
        Render.bgMusic.setLooping(true);
        Render.bgMusic.setVolume(Settings.musicVolume);
        Render.bgMusic.play();
        
    }
    @Override
    protected void createTableElements() {
    	
    	Text = new Label[8];
    	title = new Label("chess 2", Render.skin, "TitleStyle");
    	
    	for(int i=0;i<Text.length;i++) {
    		String aux = languageReader.readLine(i+1) + " " + dataReader.readLine(i+1);
    		Text[i] = new Label(aux ,Render.skin,"default");
    	}
    	
    	textButton = new TextButton[2];
    	
    	textButton[0] = new TextButton("Editar","SingleClickStyle");
    	textButton[1] = new TextButton("Volver","SingleClickStyle");
    	
    	//Si mas botones luego se pone for
    	for(int i=0;i<textButton.length;i++) {
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
    		Render.app.setScreen(Render.EDITPROFILESCREEN);
    	}else if(button == 1) {
    		Render.app.setScreen(Render.MAINSCREEN);
    	}
    }
    
    @Override
    protected void setupTable() {
    	table.setFillParent(true);
    	table.left().pad(50);
    	table.defaults().left().space(40);
    	table.add(title);
    	table.row();
    	for (int i = 0 ; i < Text.length ; i++) {
    		table.add(Text[i]);
    		if(i%2!=0) {
    			table.row();
    		}
    	}
    	table.add(textButton[0]).padTop(100);
    	table.row();
    	table.add(textButton[1]);
    	table.row();
    }
    
}
