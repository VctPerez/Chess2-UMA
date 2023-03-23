package utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class CheckBox extends Actor implements Button{

    Image tick;
    Texture unselectedTexture,selectedTexture;
    Sprite button;
    boolean selected;
    /**
     * Constructor de una Check Box
     * No tiene parámetros.
     * La clase extiende de {@link Actor} e implementa la interfaz {@link Button}
     */
    public CheckBox(){
    	tick = new Image(Resources.CHECK_PATH);
    	
    	unselectedTexture = new Texture(Resources.CHECKBOX_UNSELECTED);
    	selectedTexture = new Texture(Resources.CHECKBOX_SELECTED);
    	
    	selected = false;
    	
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
    public void draw(SpriteBatch batch) {
        button.draw(batch);
        if(selected) {
        	tick.draw(batch);
        }
    }

    @Override
    public void checkPress(IOS input) {
    	if(input.mouseX>= tick.getPosition().x && input.mouseX <= tick.getPosition().x + tick.getDimensions().x
        && input.mouseY>= tick.getPosition().y && input.mouseY <= tick.getPosition().y + tick.getDimensions().y) {
    		button.setTexture(selectedTexture);
	       	if(input.isClicked()) {
	       		selected = !selected;
	       	}
       }else {
    	   button.setTexture(unselectedTexture);
       }
    }

    @Override
    public void establish(IOS inputs, SpriteBatch batch) {
    	draw(batch);
    	checkPress(inputs);
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
    public boolean isSelected() {
    	return selected;
    }

    @Override
    public void dispose() {
        tick.dispose();
    }
}
