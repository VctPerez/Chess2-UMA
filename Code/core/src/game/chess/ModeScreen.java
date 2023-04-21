package game.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;

import interaccionFichero.LectorLineas;
import utils.Render;
import utils.Resources;
import utils.Settings;
import utils.TextButton;

public class ModeScreen extends AbstractScreen{
	public static Stage stage;
	private Table table;

    private TextButton[] textButton;
    private Label title;
    private LectorLineas languageReader, configReader;
    
    @Override
    public void show() {
    	stage = new Stage(new FitViewport(1280, 720));
    	table = new Table();
    	table.setFillParent(true);
//    	table.debug();
    	
    	//Abrir los ficheros de configuracion e idioma
    	configReader = new LectorLineas("files/config.txt"); //Lector del txt configuracion para sacar el idioma
    	languageReader = new LectorLineas("files/lang/"+ configReader.leerLinea(Settings.language) + "matchmaking.txt"); //Abrimos el idioma que toca del archivo configuracion
    	
    	createTableElements();
        setupTable();
        addActors();
        
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
        
        if(textButton[0].isPressed()) {
//        	Render.app.setScreen(Render.CREATEMATCHSCREEN);
        }else if(textButton[1].isPressed()) {
        	Render.app.setScreen(new GameScreen());
        }else if(textButton[2].isPressed()) {
        	Render.app.setScreen(Render.MATCHMAKINGSCREEN);
        }
        
        //-----------------

    }
    
    private void createTableElements() {
    	
    	textButton = new TextButton[3];
    	
    	title = new Label(languageReader.leerLinea(5), Render.app.getManager().get(Resources.SKIN_PATH,Skin.class), "TitleStyle");//Manual
    	
    	textButton[0] = new TextButton(languageReader.leerLinea(6));//Modified
    	textButton[1] = new TextButton(languageReader.leerLinea(7));//Classic
    	textButton[2] = new TextButton(languageReader.leerLinea(4));//Return
    }
    
    private void addActors() {
    	stage.addActor(table);
    }
    
    private void setupTable() {
    	table.left().pad(50);
    	table.add(title).left().space(50);
    	table.row();
    	for (int i = 0 ; i < textButton.length ; i++) {
    		table.add(textButton[i]).left().space(25);
        	table.row();
    	}
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
