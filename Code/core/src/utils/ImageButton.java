package utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import utils.IOS;

public class ImageButton extends Actor implements Button{

    private Image image;
    private boolean selected;
    private boolean initialPress;

    /**
     * Constructor de una imagen boton.
     * @param image Imagen a usar como botÃ³n.
     */
    public ImageButton(Image image){
        this.image = image;
        selected = false;
    }

    public void setImage(Image img){
        img.setSize(image.getDimensions().x, image.getDimensions().y);
        img.setPosition(image.getPosition().x, image.getPosition().y);
        image = img;
    }

    @Override
    public void setSize(float width, float height) {
        image.setSize(width, height);
    }

    @Override
    public void resize(float width, float height) {
        image.setSize(width,height);
    }

    @Override
    public void setPosition(float x, float y) {
        image.setPosition(x,y);
    }

    @Override
    public void draw(SpriteBatch batch) {
        image.draw(batch);
    }

    @Override
    /**
     * CheckPress comprueba en cada frame si el ImageButton esta pulsado.
     * Para mantener la pulsacion fuera de los límites del boton se utiliza la variable initialPress. Mientras este valor se mantenga
     * a true, la pieza seguirá seleccionada.
     */
    public void checkPress(IOS input) {
        if(initialPress) {
        	if(!input.isMousePressed()) {
        		initialPress = false;
        		selected = false;
        	}
        }else{
	        if(input.mouseX >= image.getPosition().x && input.mouseX <= image.getPosition().x + image.getDimensions().x
	        && input.mouseY >= image.getPosition().y && input.mouseY <= image.getPosition().y + image.getDimensions().y
	        && input.isMousePressed()) {
	        	selected = true;
	        	initialPress = true;
	        }
        }
    }

    @Override
    public void establish(IOS inputs, SpriteBatch batch) {
        draw(batch);
        checkPress(inputs);
    }

    @Override
    public Vector2 getCoords() {
        return image.getPosition();
    }

    @Override
    public Vector2 getDimensions() {
        return image.getDimensions();
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public void dispose() {
        image.dispose();
    }
}

