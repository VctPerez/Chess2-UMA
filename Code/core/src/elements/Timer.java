package elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Null;

import utils.Render;
import utils.Resources;

public class Timer extends Label{
	private float timeRemaining;
	private String name;

	
	public Timer(float timeRemaining, String name, Skin skin, String styleName) {
		super(name, skin, styleName);
		this.name = name;
		this.timeRemaining=timeRemaining;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		setText(name + ": "+ this.toString());
		super.draw(batch, parentAlpha);
	}
	
	public void render() {
			this.timeRemaining-=Gdx.graphics.getDeltaTime();
		//NO tiempo negativo
		if(this.timeRemaining<0) {
			this.timeRemaining=0;
		}
	}
	
	@Override
	public String toString() {
		int m = (int)(timeRemaining/60);
		int s = (int)(timeRemaining % 60);
		return m + "m : " + s + "s";
	}

	public float getTimeRemaining() {
		return timeRemaining;
	}
	
}
