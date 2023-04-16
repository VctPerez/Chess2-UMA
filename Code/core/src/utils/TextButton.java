package utils;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class TextButton extends com.badlogic.gdx.scenes.scene2d.ui.TextButton{
	public TextButton(String text, String styleName) {
		super(text, Render.app.getManager().get(Resources.SKIN_PATH,Skin.class));
		this.addListener(new ClickListener() {
			
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				//System.out.println("entre");
			}
			
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				//System.out.println("salgo");
			}
			
//			@Override
//			public void clicked(InputEvent event, float x, float y) {
//				System.out.println("clique");
//			}
		});
	}
	
	public TextButton(String text) {
		this(text,"default");
	}
}
