package utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class CheckBox extends Actor implements  Button{

    Image tick;
    boolean selected;
    ShapeRenderer box;

    public CheckBox(){
        // TODO: 22/03/2023     
    }

    @Override
    public void resize(float width, float height) {
        // TODO: 22/03/2023  
    }

    @Override
    public void setPosition(float x, float y) {
        // TODO: 22/03/2023  
    }

    @Override
    public void draw(SpriteBatch batch) {
        // TODO: 22/03/2023  
    }

    @Override
    public void checkPress(IOS input) {
        // TODO: 22/03/2023  
    }

    @Override
    public void establish(IOS inputs, SpriteBatch batch) {
        // TODO: 22/03/2023  
    }

    @Override
    public Vector2 getCoords() {
        // TODO: 22/03/2023  
        return null;
    }

    @Override
    public Vector2 getDimensions() {
//      TODO: 22/03/2023      
        return null;
    }

    @Override
    public boolean isSelected() {
        // TODO: 22/03/2023  
        return false;
    }

    @Override
    public void dispose() {
        // TODO: 22/03/2023  
    }
}
