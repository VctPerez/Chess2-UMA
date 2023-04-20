package game.chess;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import elements.Background;
import interaccionFichero.EscritorLineas;
import interaccionFichero.LectorLineas;
import utils.Render;
import utils.Resources;
import utils.Settings;
import utils.TextButton;
import utils.TextField;

public class ConfigScreen extends AbstractScreen {
	public static Stage stage;
	Background background;	
	
    Label[] label;
    Slider[] slider;
    TextField[] textField;
    TextButton[] textButton;
    SelectBox<String> selectBox;
    
    Table rootTable;
    Table optionsTable;
    
    LectorLineas languageReader, configReader;
    EscritorLineas languageWriter, configWriter;
    
    
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
    	configReader = new LectorLineas("files/config.txt"); //Lector del txt configuracion para sacar el idioma
    	configWriter = new EscritorLineas("files/config.txt");
    	languageReader = new LectorLineas("files/lang/"+ configReader.leerLinea(Settings.language) + "settings.txt"); //Abrimos el idioma que toca del archivo configuracion
    	
    	
    	
    	background = new Background();
    	background.setColor(new Color(60/255f, 60/255f,60/255f,1f));
    	background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    	
    	//Inicializar los elementos de la escena
    	createTableElements();

    	//Añadir las acciones a los botones
		slider[0].addListener(new ClickListener(){
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				writeSettings(0);
			}
		});
		slider[1].addListener(new ClickListener(){
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				writeSettings(1);
			}
		});

    	textButton[0].addListener(new ClickListener() {
    		@Override
    		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
    			//Aplicar cambios
    			writeSettings(2);
				//configReader = new LectorLineas("files/config.txt");
				readSettings();
				//setLanguage(configReader.leerINTLinea(7));
    			return true;
    		}
    	});
    	
    	textButton[1].addListener(new ClickListener() {
    		@Override
    		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
    			//Volver al menu
    			Render.app.setScreen(Render.MAINSCREEN);
    			return true;
    		}
    	});
    	
    	
    	
    	//Introducir los elementos en la table
    	setupTable();
    	//Añadir todos los actores a la escena;
    	addActors();
    	//Leer y aplicar configuración inicial
    	readSettings();
    	
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
        	if(slider[i].isDragging() || stage.keyDown(Keys.ANY_KEY)) {
        		setVolume(slider[i].getValue(), i);
        	}
        }
        
        
        //---------------

    }
    
    /**
     * Método para inicializar todos los elementos de la escena.
     */
    private void createTableElements() {
	
    	label = new Label[3];
    	slider = new Slider[2];
    	textField = new TextField[2];
    	textButton = new TextButton[2]; 
    	
    	//LABELS
    	for(int i = 0; i < label.length ; i++) {
    		label[i] = new Label("", Render.app.getManager().get(Resources.SKIN_PATH,Skin.class), "ConfigStyle");
    	}
    	
    	//SLIDERS - TEXTFIELDS - TEXTBUTTONS
    	for(int i = 0; i < slider.length ; i++) {
    		slider[i] = new Slider(0, 100, 1, false, Render.app.getManager().get(Resources.SKIN_PATH,Skin.class));
    		
    		textField[i] = new TextField("0");
    		textField[i].setAlignment(Align.center);
    		textField[i].setDisabled(true);
    		
    		textButton[i] = new TextButton("");
    	}
    	
    	//SELECTBOX
    	selectBox = new SelectBox<String>(Render.app.getManager().get(Resources.SKIN_PATH,Skin.class));
    	selectBox.setItems("","");
    	
    }
    /**
     * Método que añade los actores a la escena
     */
    private void addActors() {
        stage.addActor(background);
        stage.addActor(rootTable);
        stage.addActor(optionsTable);
    }
    /**
     * Método que crea la estructura de las tablas. Cada "párrafo" hace referencia a
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
    	rootTable.add(selectBox).space(25).fillX().colspan(2);
    	rootTable.row();
    	
    	//SETUP OPTIONS TABLE
    	optionsTable.right().bottom().padBottom(50).padRight(150);
    	optionsTable.add(textButton[0]).space(25);
    	optionsTable.add(textButton[1]).space(25);
    }
    
    /**
     * Establece los valores y el idioma de los textos en función de
     * los parámetros establecidos en el archivo de configuración "config.txt"
     */
    private void readSettings() {
    	for(int i = 0; i < slider.length ; i++) {
    		setVolume(Float.parseFloat(configReader.leerLinea(5 + i)), i);
    	}
    	
    	setLanguage(Integer.parseInt(configReader.leerLinea(7)));
    	
    }
    /**
     * Escribe los valores obtenidos de los elementos de la UI
     * en el archivo de configuración "config.txt"
     */
    private void writeSettings(int index) {
		switch (index){
			case 0:
				configWriter.escribirLinea(5, Float.toString(slider[0].getValue()));
				break;
			case 1:
				configWriter.escribirLinea(6, Float.toString(slider[1].getValue()));
				break;
			case 2:
				configWriter.escribirLinea(7, Integer.toString(selectBox.getSelectedIndex() + 1));
				break;
		}


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
     * el número de fila del lenguaje en el archivo "config.txt".
     * @param value Idioma a establecer (1_ENG 2_ESP)
     */
	private void setLanguage(int value) {
		System.out.println("idioma: " + configReader.leerLinea(value) + "\tlinea: " + value);
    	languageReader.setNombreFichero("files/lang/"+ configReader.leerLinea(value) + "settings.txt");
    	
    	Settings.setLanguage(value);

    	for(int i = 0; i < label.length ; i++) {
    		label[i].setText(languageReader.leerLinea(i+1));
    	}
    	
    	for(int i = 0; i < textButton.length; i++) {
    		textButton[i].setText(languageReader.leerLinea(i+6));
    	}
		System.out.println("se deberia haber cambiado el idioma");
    	selectBox.setItems(languageReader.leerLinea(4),languageReader.leerLinea(5));
    	selectBox.setSelectedIndex(value-1);
    }

    @Override
    public void resize(int width, int height) {
        Render.SCREEN_WIDTH = width;
        Render.SCREEN_HEIGHT = height;
        Render.camera.setToOrtho(false, width, height);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
