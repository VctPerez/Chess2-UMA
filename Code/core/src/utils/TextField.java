package utils;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Clase que extiende de {@link com.badlogic.gdx.scenes.scene2d.ui.TextField} para adaptarla a los inputs, acciones
 * y permitir ligeros cambios.
 * <p>
 * Se recomienda el uso de {@link Table} y {@link Stage}.
 */
public class TextField extends com.badlogic.gdx.scenes.scene2d.ui.TextField{
	
	public TextField(String defaultText) {
		super(defaultText, Render.app.getManager().get(Resources.SKIN_PATH,Skin.class));
		this.addListener(new ClickListener() {
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				System.out.println("entre");
			}
			
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				System.out.println("salgo");
			}
		});
		
	}
}

