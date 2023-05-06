package game.chess;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import interaccionFichero.EscritorLineas;
import interaccionFichero.LectorLineas;
import utils.*;

public class EditProfileScreen extends AbstractMenuScreen {
	
    LectorLineas languageReader, configReader;
	EscritorLineas dataWriter;
    private SelectBox<String> selectBox;
    private TextField[] textField;
    
    @Override
    public void show() {
    	
    	configReader = new LectorLineas("files/config.txt"); //Lector del txt configuracion para sacar el idioma
    	languageReader = new LectorLineas("files/lang/"+ configReader.leerLinea(Settings.language) + "Profile.txt"); //Abrimos el idioma que toca del archivo configuracion
    	dataWriter = new EscritorLineas("files/Datos.txt"); //Para escribir los datos

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

	@Override
	protected void addListeners() {
		super.addListeners();

		//Se pone listeners en todos
		textField[0].addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				if(!textField[0].getText().isBlank() && !textField[0].getText().equals("NOMBRE")
						&& !textField[0].getText().equals(languageReader.leerLinea(9))
						&& !textField[0].getText().equals(languageReader.leerLinea(10))) { //No guarda los textos por defecto
					if (textField[0].getText().length() > 30){
						textField[0].setText(languageReader.leerLinea(9));
					} else {
						dataWriter.escribirLinea(1, textField[0].getText()); //Nombre
						System.out.println("Se ha modificado el nombre de usuario");
						textField[0].setText(languageReader.leerLinea(10));
					}
				}
			}
		});

		textField[1].addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				if(!textField[1].getText().isBlank() && !textField[1].getText().equals("NOMBRE")
						&& !textField[1].getText().equals(languageReader.leerLinea(9))
						&& !textField[1].getText().equals(languageReader.leerLinea(10))) { //No guarda los textos por defecto

					if (textField[1].getText().length() > 30){
						textField[1].setText(languageReader.leerLinea(9));
					} else {
						dataWriter.escribirLinea(7, textField[1].getText()); //Pieza favorita
						System.out.println("Se ha modificado la pieza favorita");
						textField[1].setText(languageReader.leerLinea(10));
					}
				}
			}
		});

		textField[2].addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				if(!textField[2].getText().isBlank() && !textField[2].getText().equals("NOMBRE")
						&& !textField[2].getText().equals(languageReader.leerLinea(9))
						&& !textField[2].getText().equals(languageReader.leerLinea(10))) { //No guarda los textos por defecto
					if (textField[2].getText().length() > 30){
						textField[2].setText(languageReader.leerLinea(9));
					} else {
						dataWriter.escribirLinea(8,textField[2].getText()); //Modo favorito
						System.out.println("Se ha modificado el modo favorito");
						textField[2].setText(languageReader.leerLinea(10));
					}
				}
			}
		});
	}
}
