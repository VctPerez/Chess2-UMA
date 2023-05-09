package game.chess;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import interaccionFichero.LineWriter;
import interaccionFichero.LineReader;
import utils.Render;
import utils.Resources;
import utils.Settings;
import utils.TextButton;
import utils.TextField;

public class ConfigScreen extends AbstractScreen {
	public static Stage stage;
	
    private Label[] label;
    private Slider[] slider;
    private TextField[] textField;
    private TextButton[] textButton;
    private SelectBox<String> selectBox;
    private CheckBox checkBox;
    
    private Table rootTable;
    private Table optionsTable;
    
    private LineReader languageReader, configReader;
    private LineWriter configWriter;
    
    private boolean animationStarted;
    
    
    @Override
    public void show() {
    	
    	stage = new Stage(new FitViewport(1280, 720));
    	
    	rootTable = new Table();
    	optionsTable = new Table();
    	
    	rootTable.setFillParent(true);
    	optionsTable.setFillParent(true);
    	
//    	rootTable.debug();
//    	optionsTable.debug();
    	
    	//Abrir los ficheros de configuracion e idioma
    	configReader = new LineReader("files/config.txt"); //Lector del txt configuracion para sacar el idioma
    	configWriter = new LineWriter("files/config.txt");
    	languageReader = new LineReader("files/lang/"+ configReader.readLine(Settings.language) + "settings.txt"); //Abrimos el idioma que toca del archivo configuracion
    	
    	
    	//Inicializar los elementos de la escena
    	createTableElements();

    	//Aï¿½adir las acciones a los botones
    	
    	//APPLY BUTTON
    	textButton[0].addListener(new ClickListener() {
    		@Override
    		public void clicked(InputEvent event, float x, float y) {
    			super.clicked(event, x, y);
    			//Aplicar cambios
    			writeSettings();
				readSettings();
				textButton[2].setVisible(false);
    		}
    	});
    	
    	//RESET BUTTON
		textButton[2].addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				readSettings();
			}
		});
		
		//BACK BUTTON
    	textButton[1].addListener(new ClickListener() {    		
    		@Override
    		public void clicked(InputEvent event, float x, float y) {
    			super.clicked(event, x, y);
    			readSettings();
    			Render.app.getManager().get(Resources.BUTTON_CLICKSOUND,Sound.class).play(Settings.sfxVolume);
    			addExitAnimation();
    		}
    	});
    	
    	
    	
    	//Introducir los elementos en la table
    	setupTable();
    	//Añadir todos los actores a la escena;
    	addActors();
    	//Leer y aplicar configuracion inicial
    	readSettings();
    	//Añade animacion de entrada
    	addEnterAnimation();
    	animationStarted = false;
    	
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Render.clearScreen();

        Render.camera.update();
        Render.Batch.setProjectionMatrix(Render.camera.combined);

        //---------------
        
        stage.act();
        stage.draw();
        
        //Manage Inputs
        for(int i = 0; i < slider.length ; i++) {
        	if(slider[i].isDragging()) {
				setVolume(slider[i].getValue(),i);
        	}
        }
        
        
        checkChanges();
        
        //Boton volver fue pulsado
        if(animationStarted && !rootTable.hasActions()) {
			Render.app.setScreen(new MainScreen());
        }
        
        //---------------

    }
    
    /**
     * Mï¿½todo para inicializar todos los elementos de la escena.
     */
    private void createTableElements() {
	
    	label = new Label[4];
    	slider = new Slider[2];
    	textField = new TextField[2];
    	textButton = new TextButton[3];
    	
    	
    	//LABELS
    	for(int i = 0; i < label.length ; i++) {
    		label[i] = new Label("", Render.skin, "ConfigStyle");
    	}
    	
    	//SLIDERS - TEXTFIELDS - TEXTBUTTONS
    	for(int i = 0; i < slider.length ; i++) {
    		slider[i] = new Slider(0, 100, 1, false, Render.skin);
    		
    		textField[i] = new TextField("0");
    		textField[i].setAlignment(Align.center);
    		textField[i].setDisabled(true);
    		
    		textButton[i] = new TextButton("","SingleClickStyle");
    		textButton[i].addSounds();
    	}
    	textButton[2] = new TextButton("","SingleClickStyle");
		textButton[2].setVisible(false);
		textButton[2].addSounds();
		
		checkBox = new CheckBox("", Render.skin);
		
    	//SELECTBOX
    	selectBox = new SelectBox<String>(Render.skin);
    	selectBox.setItems("","");
    	
    }
    /**
     * Mï¿½todo que aï¿½ade los actores a la escena
     */
    private void addActors() {
    	stage.addActor(Render.menuBG);
        stage.addActor(rootTable);
        stage.addActor(optionsTable);
    }
    /**
     * Mï¿½todo que crea la estructura de las tablas. Cada "pï¿½rrafo" hace referencia a
     * una fila de la tabla.
     */
    private void setupTable() {
    	//SETUP ROOT TABLE
    	rootTable.add(label[0]).left().spaceBottom(50);
    	rootTable.add(slider[0]).space(25).width(250).padBottom(15).fillX();
    	rootTable.add(textField[0]).space(25).width(125);
    	rootTable.row();
    	
    	rootTable.add(label[1]).left().spaceBottom(50);
    	rootTable.add(slider[1]).space(25).width(250).padBottom(15).fillX();
    	rootTable.add(textField[1]).space(25).width(125);
    	rootTable.row();
    	
    	rootTable.add(label[2]).left().spaceBottom(50);
    	rootTable.add(checkBox).space(25).colspan(2).top();
    	rootTable.row();
    	
    	rootTable.add(label[3]).left().spaceBottom(50);
    	rootTable.add(selectBox).space(25).fillX().colspan(2);
    	rootTable.row();
    	
    	//SETUP OPTIONS TABLE
    	optionsTable.right().bottom().padBottom(50).padRight(150);
		optionsTable.add(textButton[2]).space(25);
    	optionsTable.add(textButton[0]).space(25);
    	optionsTable.add(textButton[1]).space(25);
    }
    
    /**
     * Establece los valores y el idioma de los textos en funciï¿½n de
     * los parï¿½metros establecidos en el archivo de configuraciï¿½n "config.txt"
     */
    private void readSettings() {
    	for(int i = 0; i < slider.length ; i++) {
    		setVolume(Float.parseFloat(configReader.readLine(5 + i)), i);
    	}
    	
    	//FULLSCREEN
    	setFullscreen(Boolean.parseBoolean(configReader.readLine(7)));
    	
    	setLanguage(Integer.parseInt(configReader.readLine(8)));
    	
    }
    /**
     * Escribe los valores obtenidos de los elementos de la UI
     * en el archivo de configuraciï¿½n "config.txt"
     */
    private void writeSettings() {
		configWriter.escribirLinea(5, String.valueOf(Settings.reconvertAudioToText(Settings.musicVolume)));
		configWriter.escribirLinea(6, String.valueOf(Settings.reconvertAudioToText(Settings.sfxVolume)));
		configWriter.escribirLinea(7, Boolean.toString(checkBox.isChecked()));
		configWriter.escribirLinea(8, Integer.toString(selectBox.getSelectedIndex() + 1));
		// + 1 porque en el archivo de configuracion los idiomas se representan desde la fila 1
		// En el archivo config.txt cada valor representa un idioma(1_ENG 2_ESP)
	}

    /**
     * Metodo para actualizar el valor de la Slider y la TextField
     * de alguna fila de la rootTable
     * @param value Valor a actualizar
     * @param index Indice de fila
     */
    private void setVolume(float value,int index) {
    	slider[index].setValue(value);
    	textField[index].setText(String.format("%.0f", value));
    	
    	switch (index) {
		case 0:
			Settings.setMusicVolume(value);
			break;
		case 1:
			Settings.setSfxVolume(value);
			break;
    	}
    }
    
    /**
     * Metodo para actualizar el Idioma de los textos. El valor de entrada es
     * el nï¿½mero de fila del lenguaje en el archivo "config.txt".
     * @param value Idioma a establecer (1_ENG 2_ESP)
     */
	private void setLanguage(int value) {
		System.out.println("idioma: " + configReader.readLine(value) + "\tlinea: " + value);
    	languageReader.setFileName("files/lang/"+ configReader.readLine(value) + "settings.txt");
    	
    	Settings.setLanguage(value);

    	for(int i = 0; i < label.length ; i++) {
    		label[i].setText(languageReader.readLine(i+1));
    	}
    	
    	for(int i = 0; i < textButton.length; i++) {
    		textButton[i].setText(languageReader.readLine(i+7));
    	}
		System.out.println("se deberia haber cambiado el idioma");
    	selectBox.setItems(languageReader.readLine(5),languageReader.readLine(6));
    	selectBox.setSelectedIndex(value-1);
    }
	/**
	 * Actualiza los valores para establecer la pantalla completa
	 * @param value booleano a establecer
	 */
	private void setFullscreen(boolean value) {
		checkBox.setChecked(value);
		Settings.setFullscreen(value);
	}
	
	/**
	 * Comprueba si ha habido alguna modificacion de las configuraciones para activar
	 * el boton de RESET o esconderlo.
	 */
	private void checkChanges(){
		if(slider[0].getValue() != configReader.leerFLOATLinea(5) ||
			slider[1].getValue() != configReader.leerFLOATLinea(6) ||
				selectBox.getSelectedIndex() + 1 != configReader.leerINTLinea(8) ||
				checkBox.isChecked() != Boolean.parseBoolean(configReader.readLine(7))){

			textButton[2].setVisible(true);
		}else{
			textButton[2].setVisible(false);
		}
	}
	
	/**
	 * Añade a los elementos de la escena su animación de salida de pantalla.
	 * Activa el booleano de animationStarted para comprobar que no cambie de pantalla
	 * hasta que se ha terminado la animacion.
	 */
	private void addExitAnimation() {
		rootTable.addAction(Actions.fadeOut(0.25f));
		optionsTable.addAction(Actions.fadeOut(0.25f));
		animationStarted = true;
	}
	
	/**
	 * Añade a los elementos de la escena su animacion de salida de la pantalla.
	 */
	private void addEnterAnimation() {
		rootTable.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.5f)));
		optionsTable.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.5f)));
	}

    @Override
    public void resize(int width, int height) {
        Render.SCREEN_WIDTH = width;
        Render.SCREEN_HEIGHT = height;
        Render.camera.setToOrtho(false, width, height);
        
        stage.getViewport().update(width, height);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
