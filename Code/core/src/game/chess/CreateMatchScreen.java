package game.chess;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import interaccionFichero.LectorLineas;
import multiplayer.Guest;
import utils.*;

import java.io.IOException;

public class CreateMatchScreen extends AbstractScreen{
    private Stage stage;
    private TextButton create, join;
    private boolean finding = false;
    @Override
    public void show() {
        stage = new Stage(new FitViewport(Render.SCREEN_WIDTH, Render.SCREEN_HEIGHT));

        LectorLineas configReader = new LectorLineas("files/config.txt"); //Lector del txt configuracion para sacar el idioma
        LectorLineas languageReader = new LectorLineas("files/lang/"+ configReader.leerLinea(1) + "main.txt"); //Abrimos el idioma que toca del archivo configuracion

        Text Titulo = new Text(languageReader.leerLinea(8),Resources.FONT_MENU_PATH,100,Color.WHITE,3);
        Titulo.setPosition(100,600);
        stage.addActor(Titulo);

        create = new TextButton("Create lobby");
        create.setPosition(100, 400);
        stage.addActor(create);

        join = new TextButton("Join lobby");
        join.setPosition(100, 200);
        stage.addActor(join);

        Image logo = new Image(Resources.LOGO_PATH);
        logo.setPosition(800,-50);
        logo.setSize(500, 500);
        logo.setTransparency(0.25f);
        stage.addActor(logo);

        Render.guest = new Guest();

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
            finding = true;
            System.out.println();
            Render.guest.start();
        }
        if(join.isPressed()){
            System.out.println(Render.guest.getStatus());
        }
        if(Render.guest.getStatus()){
            Render.LOBBYSCREEN.create(Render.guest.getPlayer2().getName(), false);
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

