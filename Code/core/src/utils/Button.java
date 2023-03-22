package utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public interface Button{
    /**
     * Se encarga de cambiar el tamaño del boton
     * @param width Anchura.
     * @param height Altura.
     */
    void resize(float width, float height);

    /**
     * Se encarga de establecer una nueva posicion para el boton
     * @param x Coord x.
     * @param y Coord y.
     */
    void setPosition(float x, float y);

    /**
     * Dibuja el boton a partir del batch de entrada.
     * @param batch batch que renderiza los botones.
     */
    void draw(SpriteBatch batch);

    /**
     * Comprueba si se hace click en el boton, o si se presiona
     * @param input procesador de entradas (mouse y  teclado).
     */
    void checkPress(IOS input);

    /**
     * Establece el boton en la pantalla, es decir, lanza a la vez el checkpress y el draw.
     * @param inputs procesador de entradas (mouse y  teclado).
     * @param batch batch que renderiza los botones.
     */
    void establish(IOS inputs, SpriteBatch batch);

    /**
     *  Devuelve las coordenadas de la esquina inferior izquierda del botón.
     * @return Vector con su posicion
     */
    Vector2 getCoords();

    /**
     * Devuelve la anchura y altura que tendria el boton.
     * @return Vector de altura y anchura.
     */
    Vector2 getDimensions();

    /**
     * Devuelve un booleano diciendo si esta seleccionado, o presionado.
     * @return True o False
     */
    boolean isSelected();

    /**
     * Se encarga de liberar la memoria que se ha utilizado para los recursos del boton
     */
    void dispose();

}
