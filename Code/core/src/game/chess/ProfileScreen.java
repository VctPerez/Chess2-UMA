package game.chess;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import utils.*;
import interaccionFichero.*;

public class ProfileScreen extends AbstractMenuScreen {
	
    LineReader languageReader, configReader,dataReader;
    LineWriter dataWriter;
    private TextField textField;
    
    @Override
    public void show() {
    	
    	configReader = new LineReader("files/config.txt"); //Lector del txt configuracion para sacar el idioma
    	languageReader = new LineReader("files/lang/"+ configReader.readLine(Settings.language) + "Profile.txt"); //Abrimos el idioma que toca del archivo configuracion
    	dataReader = new LineReader("files/Datos.txt"); //Abrimos los datos
    	dataWriter = new LineWriter("files/Datos.txt"); //Para escribir los datos

    	super.show();
    	
        Render.bgMusic = Render.app.getManager().get(Resources.MENU_THEME);
        Render.bgMusic.setLooping(true);
        Render.bgMusic.setVolume(Settings.musicVolume);
        Render.bgMusic.play();
        
    }
    @Override
    protected void createTableElements() {
    	
    	Text = new Label[6];
    	title = new Label(languageReader.readLine(7), Render.skin, "TitleStyle");
    	
    	for(int i=0;i<Text.length;i++) {
    		String aux = languageReader.readLine(i+1) + " " + dataReader.readLine(i+1);
    		Text[i] = new Label(aux ,Render.skin,"SmallTextStyle");
    	}
    	
    	textButton = new TextButton[2];
    	
    	textButton[0] = new TextButton(languageReader.readLine(9),"SingleClickStyle");
    	textButton[1] = new TextButton(languageReader.readLine(8),"SingleClickStyle");
    	
    	//Si mas botones luego se pone for
    	for(int i=0;i<textButton.length;i++) {
    		textButton[i].addAnimation();
        	textButton[i].addSounds();
    	}
    	
    	textField = new TextField(languageReader.readLine(10));
        textField.setAlignment(Align.center);
    	textField.setVisible(false);
        
    }
    
    /**
     * Dado el indice de un TextButton, realiza su accion correspondiente
     * @param button
     */
    @Override
    protected void selectScreen(int button) {
    	if(button == 0) {
    		Render.app.setScreen(Render.MAINSCREEN);
    	}
    }
    
    @Override
    protected void setupTable() {
    	table.setFillParent(true);
    	table.left().pad(50);
    	table.defaults().left().space(30);
    	table.add(title);
    	table.row();
    	for (int i = 0 ; i < Text.length ; i++) {
    		table.add(Text[i]);
    		if(i%2!=0) {
    			table.row();
    		}
    	}
    	table.add(textButton[0]).padTop(50);
    	table.add(textField).width(300).padTop(50);
    	table.row();
    	table.add(textButton[1]);
    	table.row();
    }
    
    @Override
	protected void addListeners() {
		
         	textButton[1].addListener(new ClickListener() {
         		@Override
         		public void clicked(InputEvent event, float x, float y) {
         			super.clicked(event, x, y);
         			addExitAnimation(800f,0.25f,0.5f);
         			animationStarted = true;
         		}
         	});
         	
         	textButton[0].addListener(new ClickListener() {
         		@Override
         		public void clicked(InputEvent event, float x, float y) {
         			super.clicked(event, x, y);
         			textField.setVisible(true);
         		}
         	});
    	
		textField.addListener(new InputListener() {
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				if(keycode == Input.Keys.ENTER && !textField.getText().equals("")){
						textField.setVisible(false);
						Label prev = Text[0];
						dataWriter.escribirLinea(1, textField.getText()); //Nombre
						updateName(prev);
				}
				return true;
			}
		});
    }
    
    protected void updateName(Label prev) {
		String aux = languageReader.readLine(1) + " " + dataReader.readLine(1);
		Text[0] = new Label(aux ,Render.skin,"SmallTextStyle");
		table.getCell(prev).setActor(Text[0]);
	}
    
}

