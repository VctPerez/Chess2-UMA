package utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TextButton extends Actor implements Button {

    private Text text;
    private boolean isSelected = false;
    private boolean initalMouseOver = false;
    private Sound mouseOverSound;
    private Sound clickSound;
    

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
    public void draw(SpriteBatch batch) {
        text.draw();
    }

    @Override
    public void checkPress(IOS input) {
    	if(initalMouseOver) {
    		if(!mouseOver(input)){
    			text.setColor(Color.WHITE);
    			initalMouseOver = false;
    		}else if(input.isClicked()) {
    			clickSound.play(0.4f);
    			isSelected = true;
    		}
        }else{
            if(mouseOver(input)) {
            	initalMouseOver = true;
            	mouseOverSound.play(0.4f);
            	text.setColor(Color.WHITE);
            }
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
    
    public boolean mouseOver(IOS input) {
    	return  input.mouseX >= text.getX() && input.mouseX <= text.getX() + text.getWidth() && input.mouseY <= text.getY()
    			&& input.mouseY >= text.getY() - text.getHeight();
    }

    @Override
    public void dispose() {
        text.dispose();
        mouseOverSound.dispose();
        clickSound.dispose();
    }
}

