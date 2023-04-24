package game.chess;
	
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import elements.Background;
import interaccionFichero.LectorLineas;
import utils.Image;
import utils.Render;
import utils.Resources;
import utils.Settings;
import utils.Text;
import utils.TextButton;
	
	
public class ClassicManScreen extends AbstractScreen{
	public static Stage stage;

	TextButton textButton;
	Label title;
	Table table;
	LectorLineas languageReader, configReader;
	ScrollPane scrollPane;
	
	@Override
	public void show() {
    	stage = new Stage(new FitViewport(1280, 720));
    	
    	table = new Table();
    	table.setFillParent(true);
		
		//Abrir los ficheros de configuracion e idioma
	    configReader = new LectorLineas("files/config.txt"); //Lector del txt configuracion para sacar el idioma
	    languageReader = new LectorLineas("files/lang/"+ configReader.leerLinea(Settings.language) + "Manual.txt"); //Abrimos el idioma que toca del archivo configuracion
	    	
	    //Inicializar los elementos de la escena
    	createTableElements();
	    	
	    //Introducir los elementos en la table
	    setupTable();
	    //Añadir todos los actores a la escena;
    	addActors();
		     
		//Para tener dos inputs:
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(stage);
		inputMultiplexer.addProcessor(Render.inputs);
		     
		Gdx.input.setInputProcessor(inputMultiplexer);
		}
		
	private void addActors() {
		stage.addActor(table);
	}

	private void setupTable() {
		table.left().pad(50);
	    table.add(title).left().space(50);
	    table.row();
	    table.add(scrollPane).width(scrollPane.getWidth()).height(scrollPane.getHeight()).left();
	    table.row();
	    table.add(textButton).left().space(25);
	}

	private void createTableElements() {
    	title = new Label("Manual", Render.skin, "TitleStyle");
    	textButton = new TextButton(languageReader.leerLinea(1));
    	
    	String text="";
		
	    if(configReader.leerLinea(1).equals("esp/")) {
				text+=languageReader.leerTramo(5, 187);
	    }else {
				text+=languageReader.leerTramo(5, 184);
	    }
			
		Label label = new Label(text, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		label.setWrap(true);
		scrollPane = new ScrollPane(label);
		scrollPane.setSize(700f, 400f);
		scrollPane.setPosition(100f, 140f);
		scrollPane.setScrollingDisabled(false, false);
		scrollPane.setScrollbarsOnTop(true);
		scrollPane.setScrollbarsVisible(true);
		
		ScrollPaneStyle style = new ScrollPaneStyle();
		//style.vScroll = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("White.png"))));
		style.vScrollKnob = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("White.png"))));
		scrollPane.setStyle(style);
	}

	@Override
	public void render(float delta) {
			Render.clearScreen();
		
			Render.camera.update();
			Render.Batch.setProjectionMatrix(Render.camera.combined);
			
			Render.Batch.begin();
			      
		    // Actualiza y dibuja el Stage
		    stage.act();
		    stage.draw();
		          
		    if(textButton.isPressed()){
		    	stage.dispose();
		         Render.app.setScreen(Render.MANUALSCREEN);
		    }
		        
		    Render.Batch.end();
		    }
		}
