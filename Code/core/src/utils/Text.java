package utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Text extends Actor {

    BitmapFont font;
    GlyphLayout layout;

    String text;
    float x,y; // Entre 0 y 1.


    /**
     * Constructor de un texto, sin frase.
     * @param path Ruta de la fuente a utilizar.
     * @param size Tamaño de la letra.
     * @param color Color de las letras.
     * @param borderWidth Anchura del borde de las letras.
     */
    public Text(String path, int size, Color color, int borderWidth){
        font = setupFont(path, size, color, borderWidth);
        layout = new GlyphLayout();
    }

    /**
     * Constructor de un texto.
     * @param text Texto a crear.
     * @param path Ruta de la fuente a utilizar.
     * @param size Tamaño de la letra.
     * @param color Color de las letras.
     * @param borderWidth Anchura del borde de las letras.
     */
    public Text(String text, String path, int size, Color color, int borderWidth){
        font = setupFont(path, size, color, borderWidth);
        this.text = text;
        layout = new GlyphLayout();
        layout.setText(font, text);
    }

    /**
     * Este metodo se encarga de crear una fuente
     * @param path Ruta de la fuente a utilizar.
     * @param size Tamaño de la letra.
     * @param color Color de las letras.
     * @param borderWidth Anchura del borde de las letras.
     * @return Fuente creada
     */
    public static BitmapFont setupFont(String path, int size, Color color, int borderWidth){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(path));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();

        parameter.size = size;
        parameter.color = color;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = borderWidth;

        return generator.generateFont(parameter);
    }

    /**
     * Dibuja el texto
     */
    @Override
    public void draw(Batch batch, float parentAlpha){
        font.draw(batch, text, getX(), getY());
    }

    /**
     * Cambiar el texto por el del parametro
     * @param text Texto nuevo
     */
    public void setText(String text) {
        this.text = text;
        layout.setText(font, text);
    }

    /**
     * Devuelve el texto usado
     * @return texto como string
     */
    public String getText() {
        return text;
    }

    /**
     * Cambiar la coordenada y.
     * @param y Coord. y
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Devuelve la coordenada (esquina superior).
     *
     * @return Coord y
     */
    public float getY() {
        return (int) y;
    }
    /**
     * Cambiar la coordenada x.
     * @param x Coord. x
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Devuelve la coordenada x.
     *
     * @return Coord. x
     */
    public float getX() {
        return (int) x;
    }

    /**
     * Cambia la posicion del texto
     * @param x Coord x
     * @param y Coord y
     */
    public void setPosition(float x, float y){
        this.x = x ;
        this.y = y ;
    }

    /**
     * Devuelve la anchura
     * @return anchura del texto
     */
    public float getWidth(){
        return layout.width;
    }

    /**
     * Devuelve la altura del texto
     * @return altura del texto
     */
    public float getHeight(){
        return layout.height;
    }

    /**
     * Cambia el color de las letras
     * @param color (CLASE COLOR).
     */
    public void setColor(Color color){
        font.setColor(color);
    }

    /**
     * Devuelve las dimensiones del texto
     * @return Vector con las dimensiones
     */
    public Vector2 getDimension(){
        return new Vector2(getWidth(),getHeight());
    }

    /**
     * Libera los recursos usados
     */
    public void dispose(){
        font.dispose();
    }


    @Override
    public String toString() {
        return "Texto: " + text + " - Coords: "+ x + ", " + y;
    }
}

