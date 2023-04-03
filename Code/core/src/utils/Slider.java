package utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Slider extends Actor implements Button{

    Image bar;
    ImageButton controller;

    int value= 50;

    /**
     * Crea la barra con su controlador
     */
    public Slider(){
        bar = new Image(Render.app.getManager().get(Resources.SLIDER_PATH, Texture.class));
        controller = new ImageButton(new Image(Render.app.getManager().get(Resources.UNSELECTEDBAR_PATH, Texture.class)));
        resize(bar.getDimensions().x * 1.5f, bar.getDimensions().y * 1.5f);
    }

    /**
     * Centra el controlador con la barra
     */
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
    public void draw(Batch batch, float parentAlpha) {
        bar.draw(batch, 0);
        checkPress();
        controller.draw(Render.Batch,0);
    }

    @Override
    public void checkPress() {
        if(controller.isSelected() || Render.inputs.isClicked()){
            controller.setImage(new Image(Render.app.getManager().get(Resources.SELECTEDBAR_PATH, Texture.class)));
            if(Render.inputs.mouseX  >= bar.getPosition().x
                    && Render.inputs.mouseX - controller.getDimensions().x / 2 <= bar.getPosition().x + bar.getDimensions().x - controller.getDimensions().x / 2){
                controller.setPosition(Render.inputs.mouseX - controller.getDimensions().x / 2, controller.getCoords().y);
                if(Render.inputs.mouseX - controller.getDimensions().x / 2 == bar.getPosition().x){
                    value = 0;
                }else{
                    value = (int) ((controller.getCoords().x + controller.getDimensions().x /2 - bar.getPosition().x) * 100 / bar.getDimensions().x);
                }
            }
        }else{
            controller.setImage(new Image(Render.app.getManager().get(Resources.UNSELECTEDBAR_PATH, Texture.class)));
        }
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

    public float getValue() {
        return value;
    }

    @Override
    public void dispose() {
        bar.dispose();
        controller.dispose();
    }
}
