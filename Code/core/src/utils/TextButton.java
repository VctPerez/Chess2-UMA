package utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TextButton extends Actor implements Button {

    private Text text;
    private boolean isSelected = false;

    /**
     * Constructor de un Boton Texto
     * @param txt texto a usar como boton.
     */
    public TextButton(Text txt){
        text = txt;
    }

    @Override
    public void resize(float width, float height) {
        //TODO
    }

    @Override
    public void setPosition(float x, float y) {
        text.setPosition(x,y);
    }

    @Override
    public void draw(SpriteBatch batch) {
        text.draw();
    }

    @Override
    public void checkPress(IOS input) {
        //System.out.println(input.mouseX + " " + input.mouseY);
        if( input.mouseX >= text.getX() && input.mouseX <= text.getX() + text.getWidth() && input.mouseY <= text.getY()
                && input.mouseY >= text.getY() - text.getHeight()){
            text.setColor(Color.YELLOW);
            if(input.isClicked()) isSelected = true;
        }else{
            text.setColor(Color.WHITE);
            isSelected = false;
        }
    }
    @Override
    public void establish(IOS inputs, SpriteBatch batch) {
        draw(batch);
        checkPress(inputs);
    }

    @Override
    public Vector2 getCoords() {
        return new Vector2(text.getX(), text.getY());
    }

    @Override
    public Vector2 getDimensions() {
        return text.getDimension();
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public void dispose() {
        text.dispose();
    }
}

