package game.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import utils.*;

public class MainScreen extends AbstractScreen {

    IOS inputs = new IOS();
    TextButton play,exit,confg;
    Text playText,exitText,confgText,Titulo;
    Image background,Logo,news;
    
    @Override
    public void show() {
    	//Fuente Arial para probar
    	Titulo = new Text(Resources.FONT_MENU_PATH,100,Color.WHITE,5);
    	Titulo.setText("Chess2");
    	playText = new Text(Resources.FONT_MENU_PATH,50,Color.WHITE,5);
    	playText.setText("Jugar");
    	exitText = new Text(Resources.FONT_MENU_PATH,50,Color.WHITE,5);
    	exitText.setText("Salir");
    	confgText = new Text(Resources.FONT_MENU_PATH,50,Color.WHITE,5);
    	confgText.setText("Configuración");
    	Titulo.setPosition(100,600);
        playText.setPosition(100,400);
        exitText.setPosition(100,200);
        confgText.setPosition(100,300);
        play = new TextButton(playText);
        exit = new TextButton(exitText);
        confg = new TextButton(confgText);
        Logo = new Image("Logo.png");
        Logo.setPosition(800,-50);
        Logo.setSize(500, 500);
        //news = new Image("prueba.jpg");
        //news.setPosition(850,400);
        //news.setSize(300, 200);
        Gdx.input.setInputProcessor(inputs);
    }

    @Override
    public void render(float delta) {
        Render.clearScreen();

        Render.camera.update();
        Render.Batch.setProjectionMatrix(Render.camera.combined);

        Render.Batch.begin();
        //---------------
        
        Titulo.draw();
        Logo.draw(Render.Batch);
        //news.draw(Render.Batch);
        play.establish(inputs, Render.Batch);
        exit.establish(inputs, Render.Batch);
        confg.establish(inputs, Render.Batch);
        
        if(play.isSelected()){
            Render.app.setScreen(new GameScreen());
        }
        if(exit.isSelected()) {
        	Gdx.app.exit();
        }
        if(confg.isSelected()) {
        	//TO DO
        }
        
        //-----------------
        Render.Batch.end();
    }

    @Override
    public void resize(int width, int height) {
        Render.SCREEN_WIDTH = width;
        Render.SCREEN_HEIGHT = height;
        Render.camera.setToOrtho(false, width, height);
    }

    @Override
    public void dispose() {
        // TODO: 21/03/2023  
    }
}
