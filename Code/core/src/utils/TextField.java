package utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TextField extends Actor implements Button{

    ImageButton field;
    String text;

    public TextField(){
        // TODO: 23/03/2023
    }

    @Override
    public void resize(float width, float height) {
        // TODO: 23/03/2023
    }

    @Override
    public void draw(SpriteBatch batch) {
        // TODO: 23/03/2023
    }

    @Override
    public void checkPress(IOS input) {
        // TODO: 23/03/2023
    }

    @Override
    public void establish(IOS inputs, SpriteBatch batch) {
        // TODO: 23/03/2023
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