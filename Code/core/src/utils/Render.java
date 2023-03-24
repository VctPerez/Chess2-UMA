package utils;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

import game.chess.AbstractScreen;
import game.chess.Chess2;
import game.chess.GameScreen;
import game.chess.MainScreen;

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

    public static int SCREEN_WIDTH = 1280;
    public static int SCREEN_HEIGHT = 720;
    
    public static AbstractScreen MAINSCREEN;
	public static AbstractScreen GAMESCREEN;
	public static AbstractScreen MANUALSCREEN;
	public static AbstractScreen CLASSICMANSCREEN;
	public static AbstractScreen LANGUAGESCREEN;
	public static AbstractScreen CONFIGSCREEN;

    /**
     * Este metodo se encarga de limpiar la pantalla al principio de cada Render.
     */
    public static void clearScreen(){
        Gdx.gl20.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
