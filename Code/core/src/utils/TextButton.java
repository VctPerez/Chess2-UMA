package utils;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class TextButton extends com.badlogic.gdx.scenes.scene2d.ui.TextButton{
	
	public TextButton(String text, String styleName) {
		super(text, Render.skin,styleName);
	}
	
	public TextButton(String text) {
		this(text,"default");
	}
	
	public void addAnimation() {
		final TextButton textButton = this;
		textButton.addListener(new ClickListener() {
    		@Override
    		public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
    			textButton.addAction(Actions.moveBy(10, 0, 0.1f));
    		}
    		
    		@Override
    		public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
    			textButton.addAction(Actions.moveBy(-10, 0, 0.1f));
    		}
    	});

    }
	
	
}
