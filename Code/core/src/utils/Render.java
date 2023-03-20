/*
 * Esta clase principalmente es usada para declara ciertas variables relacionadas con la ejecucion
 * que me sirvan de uso auxiliar, tal y como son el Batch para renderizar y la app en si , (por ej.)
 * para ir cambiando de pantallas.
 */
package utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import game.chess.Chess2;

public class Render {
    public static SpriteBatch Batch;

    public static OrthographicCamera camara;

    public static Viewport viewport;

    public static Chess2 app;

    public static int PANTALLA_ANCHO = 1920;
    public static int PANTALLA_ALTURA = 1080;

    public static void clearScreen(){
        Gdx.gl20.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
