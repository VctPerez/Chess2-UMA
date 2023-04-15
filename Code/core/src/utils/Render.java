package utils;

import java.util.ArrayList;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.Viewport;

import game.chess.*;
import multiplayer.Guest;
import multiplayer.Host;

/**
 * Esta clase principalmente es usada para declarar aquellas variables relacionadas con la ejecución
 * que nos sirvan de uso auxiliar, tal y como son el Batch para renderizar y la app en si , (por ej.)
 * para ir cambiando de pantallas.
 */
public class Render {
    public static SpriteBatch Batch;

    public static Audio audio;
    public static Music bgMusic;
    public static OrthographicCamera camera;

    public static Viewport viewport;

    public static Chess2 app;

    public static IOS inputs;

    public static int SCREEN_WIDTH = 1280;
    public static int SCREEN_HEIGHT = 720;
    
    public static AbstractScreen MAINSCREEN;
	public static AbstractScreen MANUALSCREEN;
	public static AbstractScreen LANGUAGESCREEN;
	public static AbstractScreen CONFIGSCREEN;
    public static LobbyScreen LOBBYSCREEN;
    public static LoadingScreen LOADINGSCREEN;
    public static CreateMatchScreen CREATEMATCHSCREEN;
    public static DraftScreen DRAFTSCREEN;

    public static Host host;
    public static Guest guest;
    public static boolean hosting;
    
    public static Parser parser;
    
    //DRAFTS DE LOS DOS JUGADORES (puede que esten mejor en otra clase, es temporal)
    public static ArrayList<String> player1Draft = new ArrayList<>();
    public static ArrayList<String> player2Draft = new ArrayList<>();

    /**
     * Este metodo se encarga de limpiar la pantalla al principio de cada Render.
     */
    public static void clearScreen(){
        Gdx.gl20.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
