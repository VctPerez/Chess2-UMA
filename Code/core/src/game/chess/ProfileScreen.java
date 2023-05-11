package game.chess;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import utils.*;
import interaccionFichero.*;

public class ProfileScreen extends AbstractMenuScreen {
	
    LineReader languageReader, configReader,dataReader;
    LineWriter dataWriter;
    private TextField textField;
    private SelectBox<String> selectBox;
    private Table editTable;
    
    @Override
    public void show() {
    	
    	configReader = new LineReader("files/config.txt"); //Lector del txt configuracion para sacar el idioma
    	languageReader = new LineReader("files/lang/"+ configReader.readLine(Settings.language) + "Profile.txt"); //Abrimos el idioma que toca del archivo configuracion
    	dataReader = new LineReader("files/Datos.txt"); //Abrimos los datos
    	dataWriter = new LineWriter("files/Datos.txt"); //Para escribir los datos

    	editTable = new Table();
    	editTable.setFillParent(true);
    	
    	super.show();
    	//editTable.debug();
    	
       // Render.bgMusic = Render.app.getManager().get(Resources.MENU_THEME);
        Render.bgMusic.setLooping(true);
        Render.bgMusic.setVolume(Settings.musicVolume);
        Render.bgMusic.play();
        
    }
    @Override
    protected void createTableElements() {
    	
    	Text = new Label[8];
    	title = new Label(languageReader.readLine(7), Render.skin, "TitleStyle");
    	
    	for(int i=0;i<Text.length-1;i++) {
    		String aux = languageReader.readLine(i+1) + " " + dataReader.readLine(i+1);
    		Text[i] = new Label(aux ,Render.skin,"SmallTextStyle");
    	}
    	
    	Text[6] = new Label(languageReader.readLine(12),Render.skin,"SmallTextStyle");
    	Text[6].setVisible(false);
    	Text[7] = new Label(languageReader.readLine(10),Render.skin,"SmallTextStyle");
    	Text[7].setVisible(false);
    	
    	textButton = new TextButton[2];
    	
    	textButton[0] = new TextButton(languageReader.readLine(9),"SingleClickStyle");
    	textButton[1] = new TextButton(languageReader.readLine(8),"SingleClickStyle");
    	
    	//Si mas botones luego se pone for
    	for(int i=0;i<textButton.length;i++) {
        	textButton[i].addSounds();
    	}
    	
    	textField = new TextField(dataReader.readLine(1));
        textField.setAlignment(Align.center);
    	textField.setVisible(false);
    	
    	//SELECTBOX
    	String[] items = {"Azul", "Negro", "Rojo","Verde"};
    	selectBox = new SelectBox<String>(Render.skin);
    	selectBox.setItems(items);
    	selectBox.setVisible(false);
    	selectBox.setAlignment(Align.center);
        
    }
    
    protected void addActors() {
    	super.addActors();
    	super.stage.addActor(editTable);
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
    protected void addExitAnimation(float distance, float delay, float time) {
    	editTable.addAction(Actions.fadeOut(0.25f));
    	table.addAction(Actions.fadeOut(0.25f));
    }
    
    @Override
    protected void setupTable() {
    	table.setFillParent(true);
    	table.left().pad(50);
    	table.defaults().left().space(30);
    	table.add(title);
    	table.row();
    	for (int i = 0 ; i < Text.length-1 ; i++) {
    		table.add(Text[i]);
    		if(i%2!=0) {
    			table.row();
    		}
    	}
    	table.padBottom(300);
    	
    	editTable.left().bottom().padBottom(50).padLeft(50);
    	editTable.add(Text[6]).padBottom(150);
    	editTable.add(selectBox).space(25).fillX().colspan(3).padBottom(130).padLeft(15);
    	editTable.add(Text[7]).padBottom(150).padLeft(50);
    	editTable.add(textField).width(450).padBottom(150).padLeft(15);
    	editTable.row();
    	editTable.add(textButton[1]);
    	editTable.add(textButton[0]).padLeft(15);
    	editTable.row();

    	
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
         			selectBox.setVisible(true);
         			Text[6].setVisible(true);
         			Text[7].setVisible(true);
         		}
         	});
    	
		textField.addListener(new InputListener() {
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				if(keycode == Input.Keys.ENTER && !textField.getText().equals("") && !(textField.getText().length()>12)){
						textField.setVisible(false);
						selectBox.setVisible(false);
						Text[6].setVisible(false);
						Text[7].setVisible(false);
						Label prev = Text[0];
						dataWriter.escribirLinea(1, textField.getText()); //Nombre
						updateName(prev);
				}else if(keycode == Input.Keys.ENTER && !textField.getText().equals("") && textField.getText().length()>12) {
						textField.setText(languageReader.readLine(11));
				}
				return true;
			}
		});
		
		selectBox.addListener(new ChangeListener() {
		    @Override
		    public void changed(ChangeEvent event, Actor actor) {
		        //String selectedOption = selectBox.getSelected();
		        if(selectBox.getSelectedIndex()==0) {
		        	dataWriter.escribirLinea(7, "0.1745f");
		        	dataWriter.escribirLinea(8, "0.23f");
		        	dataWriter.escribirLinea(9, "0.3f" );
		        	dataWriter.escribirLinea(10, "1f");
		        }else if(selectBox.getSelectedIndex()==1) {
		        	dataWriter.escribirLinea(7, "0f");
		        	dataWriter.escribirLinea(8, "0f");
		        	dataWriter.escribirLinea(9, "0f" );
		        	dataWriter.escribirLinea(10, "1f");
		        }else if(selectBox.getSelectedIndex()==2){
		        	dataWriter.escribirLinea(7, "0.5");
		        	dataWriter.escribirLinea(8, "0.23f");
		        	dataWriter.escribirLinea(9, "0.3f" );
		        	dataWriter.escribirLinea(10, "1f");
		        }else{
		        	dataWriter.escribirLinea(7, "0.1745f");
		        	dataWriter.escribirLinea(8, "0.5f");
		        	dataWriter.escribirLinea(9, "0.3f" );
		        	dataWriter.escribirLinea(10, "1f");
		        }
		        selectBox.setVisible(false);
		        textField.setVisible(false);
		        Text[6].setVisible(false);
		        Text[7].setVisible(false);
		    }
		});
    }
    
    protected void updateName(Label prev) {
		String aux = languageReader.readLine(1) + " " + dataReader.readLine(1);
		Text[0] = new Label(aux ,Render.skin,"SmallTextStyle");
		table.getCell(prev).setActor(Text[0]);
	}
    
}