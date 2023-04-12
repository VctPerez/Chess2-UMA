package utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TextButton extends Actor implements Button {

    private Text text;
    private boolean isSelected = false, isPressed = false;
    //private boolean initalMouseOver = false;
    private Sound mouseOverSound, clickSound;
    private Color remarked = null;
    private final Color normalColor;
    

    /**
     * Constructor de un Boton Texto
     * @param txt texto a usar como boton.
     */
    public TextButton(Text txt){
        text = txt;
        normalColor = new Color(txt.getColor());
        mouseOverSound = Render.app.getManager().get(Resources.TEXTBUTTON_HOVERSOUND);
        clickSound = Render.app.getManager().get(Resources.TEXTBUTTON_CLICKSOUND);
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
    public void draw(Batch batch, float parentAlpha) {
        text.draw(Render.Batch, 0);
        checkPress();
    }

    public boolean mouseOver() {
        return  Render.inputs.mouseX >= text.getX() && Render.inputs.mouseX <= text.getX() + text.getWidth()
                && Render.inputs.mouseY <= text.getY()
                && Render.inputs.mouseY >= text.getY() - text.getHeight();
    }
    @Override
    public void checkPress() {
        if(mouseOver()){
            if(!isSelected){
                mouseOverSound.play(0.2f);
                isSelected = true;
            }
            if(Render.inputs.isClicked()){
                clickSound.play(0.2f);
                if(!isPressed && remarked != null)text.setColor(remarked);
                else text.setColor(normalColor);
                isPressed = !isPressed;
            }
        }else {
            isSelected = false;
        }
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
    public boolean isPressed(){
        return isPressed;
    }

    public boolean isSelected() {
        return isSelected;
    }
    


    public void setRemarked(Color remarked) {
        this.remarked = remarked;
    }

    public void setClickSound(Sound clickSound) {
        this.clickSound = clickSound;
    }

    public void setMouseOverSound(Sound mouseOverSound) {
        this.mouseOverSound = mouseOverSound;
    }
    public void dispose() {
        text.dispose();
        mouseOverSound.dispose();
        clickSound.dispose();
    }
}

