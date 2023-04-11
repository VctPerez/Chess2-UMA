package game.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;

import elements.Background;
import interaccionFichero.LectorLineas;
import multiplayer.Guest;
import multiplayer.Joiner;
import utils.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class CreateMatchScreen extends AbstractScreen{
    private Stage stage;
    Background background;	
    private TextButton create, join;
    private boolean finding = false;
    @Override
    public void show() {
        stage = new Stage(new FillViewport(Render.SCREEN_WIDTH, Render.SCREEN_HEIGHT));
        background = new Background();
    	background.setColor(new Color(60/255f, 60/255f,60/255f,1f));
    	background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        LectorLineas configReader = new LectorLineas("files/config.txt"); //Lector del txt configuracion para sacar el idioma
        LectorLineas languageReader = new LectorLineas("files/lang/"+ configReader.leerLinea(1) + "main.txt"); //Abrimos el idioma que toca del archivo configuracion

        Text Titulo = new Text(languageReader.leerLinea(8),Resources.FONT_MENU_PATH,100,Color.WHITE,3);
        Titulo.setPosition(100,600);
        stage.addActor(Titulo);

        create = new TextButton(new Text("Create lobby", Resources.FONT_MENU_PATH, 50, Color.WHITE, 3));
        create.setPosition(100, 400);
        stage.addActor(create);

        join = new TextButton(new Text("Join lobby", Resources.FONT_MENU_PATH, 50, Color.WHITE, 3));
        join.setPosition(100, 200);
        stage.addActor(join);

        Image logo = new Image(Resources.LOGO_PATH);
        logo.setPosition(800,-50);
        logo.setSize(500, 500);
        logo.setTransparency(0.25f);
        stage.addActor(background);
        stage.addActor(logo);

    }

    @Override
    public void render(float delta) {
        Render.clearScreen();
        Render.Batch.begin();
        stage.act();
        stage.draw();
        Render.Batch.end();

        try{
            update();
        }catch(IOException | InterruptedException e){
            System.err.println(e.getMessage());
        }

    }
    public void update() throws IOException, InterruptedException {
        if(create.isPressed()){
            Render.LOBBYSCREEN.create("Victor", true);
            Render.app.setScreen(Render.LOBBYSCREEN);
        } else if (join.isPressed() && !finding) {
            //TODO con textField cosa que me da miedo
            finding = true;
            System.out.println("Introduce tu nombre (espacio) la ip: ");
            Joiner joiner = new Joiner();
            joiner.start();
            joiner.join();
            System.out.println(joiner.getPName() + " " + joiner.getIp());
            Render.guest = new Guest(joiner.getPName(), joiner.getIp());
            Render.LOBBYSCREEN.create("jugador2", false);
            Render.app.setScreen(Render.LOBBYSCREEN);
        }
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}

