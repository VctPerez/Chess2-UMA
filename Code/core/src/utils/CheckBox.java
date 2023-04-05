package utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class CheckBox extends Actor implements Button{

    Image tick;
    Texture unselectedTexture,selectedTexture;
    Sprite button;
    boolean value;
    
    /**
     * Constructor de una Check Box
     * No tiene parametros.
     * La clase extiende de {@link Actor} e implementa la interfaz {@link Button}
     */
    public CheckBox(){
    	tick = new Image(Resources.CHECK_PATH);
    	
    	unselectedTexture = new Texture(Resources.CHECKBOX_UNSELECTED);
    	selectedTexture = new Texture(Resources.CHECKBOX_SELECTED);
    	
    	value = false;
    	
    	button = new Sprite(unselectedTexture);
    	button.setSize(100, 100);
    	
    	resize(100, 100);
    }

    @Override
    public void resize(float width, float height) {
        tick.setSize(width, height);
    }

    @Override
    public void setPosition(float x, float y) {
        tick.setPosition(x, y);
        button.setPosition(x, y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        button.draw(batch);
        if(value) {
        	tick.draw(batch, 0);
        }
        checkPress();
    }

    @Override
    public void checkPress() {
    	if(Render.inputs.mouseX>= tick.getPosition().x && Render.inputs.mouseX <= tick.getPosition().x + tick.getDimensions().x
        && Render.inputs.mouseY>= tick.getPosition().y && Render.inputs.mouseY <= tick.getPosition().y + tick.getDimensions().y) {
    		button.setTexture(selectedTexture);
	       	if(Render.inputs.isClicked()) {
	       		value = !value;
	       	}
       }else {
    	   button.setTexture(unselectedTexture);
       }
    }

    @Override
    public Vector2 getCoords() {
        return tick.getPosition();
    }

    @Override
    public Vector2 getDimensions() {
    	return tick.getDimensions();
    }

    @Override
    public boolean isPressed() {
        return false;
    }

    public boolean isSelected() {
    	return value;
    }

    @Override
    public void dispose() {
        tick.dispose();
    }
}
