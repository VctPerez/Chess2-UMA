package game.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import utils.*;

public class ConfigScreen extends AbstractScreen {

    IOS inputs = new IOS();
    TextButton home,exit;
    Text homeText,exitText,Titulo,volumeText;
    Image background,Logo,news;
    
    @Override
    public void show() {
    	//Fuente Arial para probar
    	Titulo = new Text(Resources.FONT_MENU_PATH,100,Color.WHITE,5);
    	Titulo.setText("Configuración");
    	homeText = new Text(Resources.FONT_MENU_PATH,50,Color.WHITE,5);
    	homeText.setText("Inicio");
    	volumeText = new Text(Resources.FONT_MENU_PATH,50,Color.WHITE,5);
    	volumeText.setText("Volumen");
    	exitText = new Text(Resources.FONT_MENU_PATH,50,Color.WHITE,5);
    	exitText.setText("Salir");
    	Titulo.setPosition(100,600);
        homeText.setPosition(100,400);
        volumeText.setPosition(100,300);
        exitText.setPosition(100,200);
        home = new TextButton(homeText);
        exit = new TextButton(exitText);
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
        home.establish(inputs, Render.Batch);
        volumeText.draw();
        exit.establish(inputs, Render.Batch);
        
        if(home.isSelected()){
            Render.app.setScreen(new MainScreen());
        }
        if(exit.isSelected()) {
        	Gdx.app.exit();
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
