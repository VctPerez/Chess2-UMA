package game.chess;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.utils.Align;

import utils.*;
import interaccionFichero.*;

public class EditProfileScreen extends AbstractMenuScreen {
	
    LectorLineas languageReader, configReader,dataReader;
    private SelectBox<String> selectBox;
    private TextField[] textField;
    
    @Override
    public void show() {
    	
    	configReader = new LectorLineas("files/config.txt"); //Lector del txt configuracion para sacar el idioma
    	languageReader = new LectorLineas("files/lang/"+ configReader.leerLinea(Settings.language) + "Profile.txt"); //Abrimos el idioma que toca del archivo configuracion
    	
    	super.show();
        
        Render.bgMusic = Render.app.getManager().get(Resources.MENU_THEME);
        Render.bgMusic.setLooping(true);
        Render.bgMusic.setVolume(Settings.musicVolume);
        Render.bgMusic.play();
        
    }
    @Override
    protected void createTableElements() {
    	
    	Text = new Label[3];
    	textField = new TextField[3];
    	title = new Label("chess 2", Render.skin, "TitleStyle");
    	
    	Text[0] = new Label(languageReader.leerLinea(1) ,Render.skin,"default");
    	Text[1] = new Label(languageReader.leerLinea(7) ,Render.skin,"default");
    	Text[2] = new Label(languageReader.leerLinea(8) ,Render.skin,"default");
    	
    	textButton = new TextButton[1];
    	
    	textButton[0] = new TextButton("Volver","SingleClickStyle");
    	
    	//Si mas botones luego se pone for
    		textButton[0].addAnimation();
        	textButton[0].addSounds();
        	
        textField[0] = new TextField("NOMBRE");
        textField[0].setAlignment(Align.center);
        textField[1] = new TextField("NOMBRE");
        textField[1].setAlignment(Align.center);
        textField[2] = new TextField("NOMBRE");
        textField[2].setAlignment(Align.center);
        
      //SELECTBOX
        selectBox = new SelectBox<String>(Render.skin);
    	selectBox.setItems("","");
    }
    
    /**
     * Dado el indice de un TextButton, realiza su accion correspondiente
     * @param button
     */
    @Override
    protected void selectScreen(int button) {
    	if(button == 0) {
    		Render.app.setScreen(Render.PROFILESCREEN);
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
    		table.add(textField[i]).width(600);
    		table.row();
    	}
    	table.add(textButton[0]).padTop(100);
    	table.row();
    }
    
}
