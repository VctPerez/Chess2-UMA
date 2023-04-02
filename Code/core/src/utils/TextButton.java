package utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TextButton extends Actor implements Button {

    private Text text;
    private boolean isSelected = false;
    private boolean initalMouseOver = false;
    private Sound mouseOverSound;
    private Sound clickSound;
    private Color remarked;
    

    /**
     * Constructor de un Boton Texto
     * @param txt texto a usar como boton.
     */
    public TextButton(Text txt){
        text = txt;
        mouseOverSound = Gdx.audio.newSound(Gdx.files.internal(Resources.TEXTBUTTON_HOVERSOUND));
        clickSound = Gdx.audio.newSound(Gdx.files.internal(Resources.TEXTBUTTON_CLICKSOUND));
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
    /*
     * No vuelvas a tocar este codigo adri por favor
     */
    @Override
    public void checkPress() {
        if( Render.inputs.mouseX >= text.getX() && Render.inputs.mouseX <= text.getX() + text.getWidth()
                && Render.inputs.mouseY <= text.getY()
                && Render.inputs.mouseY >= text.getY() - text.getHeight()){
            if(Render.inputs.isClicked()){
                if(!isSelected) text.setColor(remarked);
                else text.setColor(Color.WHITE);
                isSelected = !isSelected;
            }

        }else{
            if(Render.inputs.isClicked() && isSelected){
                text.setColor(Color.WHITE);
                isSelected = false;
            }
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
    public boolean isSelected() {
        return isSelected;
    }
    
    /*public boolean mouseOver(IOS input) {
    	return  input.mouseX >= text.getX() && input.mouseX <= text.getX() + text.getWidth() && input.mouseY <= text.getY()
    			&& input.mouseY >= text.getY() - text.getHeight();
    }*/

    public void setRemarked(Color remarked) {
        this.remarked = remarked;
    }

    @Override
    public void dispose() {
        text.dispose();
        mouseOverSound.dispose();
        clickSound.dispose();
    }
}

