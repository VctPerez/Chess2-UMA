
package utils;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ImageButton extends Actor implements Button{

    private Image image;
    private boolean selected, pressed;
    private boolean initialPress;

    /**
     * Constructor de una imagen boton.
     * @param image Imagen a usar como botón.
     */
    public ImageButton(Image image){
        this.image = image;
        selected = false;
        pressed = false;
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
    public void draw(Batch batch, float parentAlpha) {
        image.draw(batch, 0);
        checkPress();
    }

    @Override
    public void checkPress() {
        if(initialPress) {
        	if(!Render.inputs.isMousePressed()) {
        		initialPress = false;
        		selected = false;
        	}
        }else{
	        if(Render.inputs.mouseX >= image.getPosition().x && Render.inputs.mouseX <= image.getPosition().x + image.getDimensions().x
	        && Render.inputs.mouseY >= image.getPosition().y && Render.inputs.mouseY <= image.getPosition().y + image.getDimensions().y
	        && Render.inputs.isMousePressed()) {
	        	selected = true;
	        	initialPress = true;
	        }
        }
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
    public boolean isPressed() {
        return pressed;
    }

    public boolean isSelected() {
        return selected;
    }

    @Override
    public void dispose() {
        image.dispose();
    }
}

