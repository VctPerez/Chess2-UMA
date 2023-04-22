package elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import utils.Render;
import utils.Resources;

public class Timer extends Table{
	private float timeRemaining;
	private float x,y;
	private String name;
	private Label timer;
	
	public Timer(float time,float x,float y,String name) {
		super();
		timeRemaining=time;
		this.x=x;
		this.y=y;
		this.name = name;

		timer = new Label(name + ": "+ this.toString(), Render.app.getManager().get(Resources.SKIN_PATH,Skin.class));
		this.setFillParent(true);
		//this.setPosition(x, y);
		//this.add(timer);
		this.left().pad(50);
    	this.add(timer).left().space(50);
		
	
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		System.out.println(name + ": "+ this.toString());
		timer.setText(name + ": "+ this.toString());
	}
	
	public void render() {
			this.timeRemaining-=Gdx.graphics.getDeltaTime();
		//NO tiempo negativo
		if(this.timeRemaining<0) {
			this.timeRemaining=0;
		}
	}
	
	public String toString() {
		int m = (int)(timeRemaining/60);
		int s = (int)(timeRemaining % 60);
		return m + "m" + s + "s";
	}

	public float getTimeRemaining() {
		return timeRemaining;
	}
	
}
