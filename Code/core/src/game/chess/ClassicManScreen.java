package game.chess;
	
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
	
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
			
		String text = "Aqui deberia de ir un texto sobre las instrucciones del juego en el modo clásico";
		text += " deberiamos poner un texto de instruccion de ejemplos  y ya luego adaptarlo, además hay que probar esto con ficheros (así se "
				+ "traduce también, por lo";
		text += " demás funciona bien, para cuando tengamos la version modificada pues igual, pero dios nos cuide para ese entonces mientras";
		text += " aquí viene relleno para probar el scroll: aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		text += "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		text += "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		text += "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		text += "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		text += "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		text += "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		text += "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		text += "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		text += "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		text += "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		text += "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		text += "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		text += "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		text += "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		text += "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		text += "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		text += "Que Don Vega bendiga este trabajo y que Troya sobreviva la proxima vez que le mandemos el informe, viva cristo y Quislant";
		text += " del Barrio, pal próximo juego sexo2 porfavor 🤑";
			
		Label label = new Label(text, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		label.setWrap(true);
		ScrollPane scrollPane = new ScrollPane(label);
		scrollPane.setSize(700f, 400f);
		scrollPane.setPosition(100f, 120f);
			
		// Agrega el ScrollPane al Stage
		stage.addActor(scrollPane);
			
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
	    stage.act(delta);
	    stage.draw();
	          
	    if(volver.isSelected()){
	    	stage.dispose();
	         Render.app.setScreen(Render.MANUALSCREEN);
	    }
	        
	    Render.Batch.end();
	    }
	}
