package utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class CheckBox extends Actor implements  Button{

    Image tick;
    boolean selected;
    ShapeRenderer box;

    public CheckBox(){
    	tick = new Image(Resources.CHECK_PATH);
    	selected = false;
    	box = new ShapeRenderer();
    	resize(100, 100);
    }

    @Override
    public void resize(float width, float height) {
        tick.setSize(width, height);
    }

    @Override
    public void setPosition(float x, float y) {
        tick.setPosition(x, y);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.end();
        box.begin(ShapeType.Line);
        box.rect(tick.getPosition().x, tick.getPosition().y, tick.getDimensions().x, tick.getDimensions().y);
        box.end();
        batch.begin();
        if(selected) {
        	tick.draw(batch);
        }
    }

    @Override
    public void checkPress(IOS input) {
    	if(input.mouseX>= tick.getPosition().x && input.mouseX <= tick.getPosition().x + tick.getDimensions().x
        && input.mouseY>= tick.getPosition().y && input.mouseY <= tick.getPosition().y + tick.getDimensions().y) {
    		box.setColor(Color.WHITE);
	       	if(input.isClicked()) {
	       		selected = !selected;
	       	}
       }else {
    	   box.setColor(Color.YELLOW);
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
        box.dispose();
    }
}
