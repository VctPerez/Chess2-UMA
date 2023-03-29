package game.chess;
	
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import interaccionFichero.LectorLineas;
import utils.IOS;
import utils.Image;
import utils.Render;
import utils.Resources;
import utils.Text;
import utils.TextButton;
	
	
public class ClassicManScreen extends AbstractScreen{
	
	IOS inputs = new IOS();
	Stage stage;
	Image Logo;
	Text volverText,Titulo;
	TextButton volver;
	LectorLineas languageReader, configReader;
		
	public ClassicManScreen() {
		this.stage=new Stage();
	}
		
	@Override
	public void show() {
			
		//Abrir los ficheros de configuracion e idioma
	    configReader = new LectorLineas("files/config.txt"); //Lector del txt configuracion para sacar el idioma
	    languageReader = new LectorLineas("files/lang/"+ configReader.leerLinea(1) + "Manual.txt"); //Abrimos el idioma que toca del archivo configuracion
	    	
	    //Fuente Arial para probar
	    Titulo = new Text(Resources.FONT_MENU_PATH,50,Color.WHITE,5);
	    Titulo.setText(languageReader.leerLinea(5));
	    volverText = new Text(Resources.FONT_MENU_PATH,28,Color.WHITE,5);
	    volverText.setText(languageReader.leerLinea(1));
	    	
	    volverText.setPosition(100,100);
	    volver = new TextButton(volverText);
	    Titulo.setPosition(100,600);
	    
	    String text="";
			
	    if(configReader.leerLinea(1).equals("esp/")) {
				text+=languageReader.leerTramo(6, 187);
	    }else {
				text+=languageReader.leerTramo(6, 184);
	    }
			
		Label label = new Label(text, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		label.setWrap(true);
		ScrollPane scrollPane = new ScrollPane(label);
		scrollPane.setSize(700f, 400f);
		scrollPane.setPosition(100f, 140f);
			
		// Agrega el ScrollPane al Stage
		stage.addActor(scrollPane);
		
		scrollPane.setScrollingDisabled(false, false);
		
		scrollPane.setScrollbarsOnTop(true);
		scrollPane.setScrollbarsVisible(true);
		
		ScrollPaneStyle style = new ScrollPaneStyle();
		//style.vScroll = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("White.png"))));
		style.vScrollKnob = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("White.png"))));
		scrollPane.setStyle(style);
		
			
		Logo = new Image("Logo_Blanco.png");
		Logo.setPosition(800,-50);
		Logo.setSize(500, 500);
		Logo.setTransparency(0.25f);
		     
		//Para tener dos inputs:
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(stage);
		inputMultiplexer.addProcessor(inputs);
		     
		Gdx.input.setInputProcessor(inputMultiplexer);
	
		}
		
@Override
public void render(float delta) {
		Render.clearScreen();
	
		Render.camera.update();
		Render.Batch.setProjectionMatrix(Render.camera.combined);
		
		Render.Batch.begin();
		      
		Titulo.draw();
		Logo.draw(Render.Batch);
		volver.establish(inputs, Render.Batch);
		      
	    // Actualiza y dibuja el Stage
	    stage.act();
	    stage.draw();
	          
	    if(volver.isSelected()){
	    	stage.dispose();
	         Render.app.setScreen(Render.MANUALSCREEN);
	    }
	        
	    Render.Batch.end();
	    }
	}
