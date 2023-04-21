package game.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import interaccionFichero.LectorLineas;
import multiplayer.Guest;
import utils.*;

import java.io.IOException;
import java.util.Objects;

public class CreateMatchScreen extends AbstractScreen{
    private Stage stage;
    private TextButton[] textbuttons; // 0 - crear; 1 - buscar; 2 - volver
    private TextField textField;
    private Label titulo;
    private Table table;
    private LectorLineas languageReader, configReader;
    private boolean finding = false;
    @Override
    public void show() {
        stage = new Stage(new FitViewport(Render.SCREEN_WIDTH, Render.SCREEN_HEIGHT));
        table = new Table();
        table.setFillParent(true);

        configReader = new LectorLineas("files/config.txt"); //Lector del txt configuracion para sacar el idioma
        //System.out.println(configReader.leerLinea(1));
        languageReader = new LectorLineas("files/lang/"+ configReader.leerLinea(Settings.language) + "matchmaking.txt"); //Abrimos el idioma que toca del archivo configuracion

        createElements();
        addListeners();
        setupTable();
        stage.addActor(table);
        stage.addActor(textField);


        Image logo = new Image(Resources.LOGO_PATH);
        logo.setPosition(800,-50);
        logo.setSize(500, 500);
        logo.setTransparency(0.25f);
        stage.addActor(logo);

        Gdx.input.setInputProcessor(stage);

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
    /*
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
        }*/
        if(finding) {
            if (Render.guest.isConnected()) {
                Render.guest.setReceiving(true);
                Render.guest.start();
                Render.LOBBYSCREEN.create(Render.guest.getPlayer2().getName(), false);
                Render.app.setScreen(Render.LOBBYSCREEN);
            }
        }
    }
    private void createElements(){
        textbuttons = new TextButton[3];
        titulo = new Label(languageReader.leerLinea(1)
                , Render.app.getManager().get(Resources.SKIN_PATH, Skin.class), "TitleStyle");
        for(int i =  0; i < textbuttons.length-1; i++){
            textbuttons[i] = new TextButton(languageReader.leerLinea(8+i));
        }
        textbuttons[2] = new TextButton(languageReader.leerLinea(4));
        textField = new TextField(languageReader.leerLinea(10));
        textField.setVisible(false);
        textField.setBounds(500, 235, 500, textField.getHeight());
    }
    private void setupTable(){
        table.left().pad(50);
        table.add(titulo).left().space(50);
        for(int i = 0; i < textbuttons.length; i++){
            table.row();
            table.add(textbuttons[i]).left().space(25);
            //if(i == 1) table.add(textField).left();
        }
    }

    private void addListeners(){
        textbuttons[0].addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Render.LOBBYSCREEN.create("Victor", true);
                Render.app.setScreen(Render.LOBBYSCREEN);
                return true;
            }
        });textbuttons[1].addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                finding = true;
                Render.guest = new Guest("Vega");
                textField.setVisible(!textField.isVisible());
                return true;
            }
        });textbuttons[2].addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Render.app.setScreen(Render.MATCHMAKINGSCREEN);
                return true;
            }
        });
        textField.addListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if(keycode == Input.Keys.ENTER && !textField.getText().equals("")){

                    try {
                        Render.guest.connect(textField.getMessageText());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                return true;
            }
        });
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

