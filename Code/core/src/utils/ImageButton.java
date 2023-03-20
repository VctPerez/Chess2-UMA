package utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import utils.IOS;

public class ImageButton implements Button{

    private Image image;

    /**
     * Constructor de una imagen boton.
     * @param image Imagen a usar como botón.
     */
    public ImageButton(Image image){
        this.image = image;
    }

    @Override
    public void resize(float width, float height) {
        //TODO
    }

    @Override
    public void setPosition(float x, float y) {
        //TODO
    }

    @Override
    public void draw(SpriteBatch batch) {
        //TODO
    }

    @Override
    public void checkPress(IOS input) {
        //TODO
    }

    @Override
    public void establish(IOS inputs, SpriteBatch batch) {
        //TODO
    }

    @Override
    public Vector2 getCoords() {
        return null;
    }

    @Override
    public Vector2 getDimensions() {
        return null;
    }

    @Override
    public boolean isSelected() {
        return false;
    }

    @Override
    public void dispose() {

    }
}

