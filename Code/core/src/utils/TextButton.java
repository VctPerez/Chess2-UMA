package utils;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class TextButton extends com.badlogic.gdx.scenes.scene2d.ui.TextButton{
	
	public TextButton(String text, String styleName) {
		super(text, Render.app.getManager().get(Resources.SKIN_PATH,Skin.class),styleName);
		this.addListener(new ClickListener() {
			
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				//System.out.println("mouseOver");
				//A�adir sonido mouseOver
			}
			
//			@Override
//			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
//				System.out.println("salgo");
//			}
//			
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("touch down");
				//A�adir sonido mouseClick
				return true;
			}
			
			
		});
	
	}
	
	public TextButton(String text) {
		this(text,"default");
	}
	
}
