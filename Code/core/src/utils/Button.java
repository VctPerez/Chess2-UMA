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
     * Comprueba si se hace click en el boton, o si se presiona
     */
    void checkPress();
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
    boolean isPressed();

    /**
     * Se encarga de liberar la memoria que se ha utilizado para los recursos del boton
     */
    void dispose();

}
