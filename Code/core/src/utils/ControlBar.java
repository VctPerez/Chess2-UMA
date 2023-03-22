package utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ControlBar extends Actor implements Button{

    Image bar;
    ImageButton controller;

    public ControlBar(){
        bar = new Image(Resources.CONTROLBAR_PATH);
        controller = new ImageButton(new Image(Resources.UNSELECTEDBAR_PATH));
        resize(bar.getDimensions().x * 2, bar.getDimensions().y * 2);
    }
    private void centerController(){
        controller.setPosition(bar.getPosition().x + bar.getDimensions().x /2 - controller.getDimensions().x / 2,
               bar.getPosition().y + bar.getDimensions().y /2 - controller.getDimensions().y / 2);
    }

    @Override
    public void setPosition(float x, float y) {
        bar.setPosition(x,y);
        centerController();
    }

    @Override
    public void resize(float width, float height) {
        float wCont = controller.getDimensions().x * width / bar.getDimensions().x;
        float hCont = controller.getDimensions().y * height / bar.getDimensions().y;
        bar.setSize(width,height * 0.75f);
        controller.setSize(wCont,hCont);
    }

    @Override
    public void draw(SpriteBatch batch) {
        bar.draw(batch);
    }

    @Override
    public void checkPress(IOS input) {
        System.out.println(input.mouseX);
        if(controller.isSelected()){
            controller.setImage(new Image(Resources.SELECTEDBAR_PATH));
        }else{
            controller.setImage(new Image(Resources.UNSELECTEDBAR_PATH));
        }
    }

    @Override
    public void establish(IOS inputs, SpriteBatch batch) {
        draw(batch);
        checkPress(inputs);
        controller.establish(inputs, batch);
    }

    @Override
    public Vector2 getCoords() {
        return bar.getPosition();
    }

    @Override
    public Vector2 getDimensions() {
        return bar.getDimensions();
    }

    @Override
    public boolean isSelected() {
        return controller.isSelected();
    }

    @Override
    public void dispose() {
        bar.dispose();
        controller.dispose();
    }
}