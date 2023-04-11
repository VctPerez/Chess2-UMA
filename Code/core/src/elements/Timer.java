package elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Timer extends Actor{
	private float timeRemaining;
	private float x,y;
	private String Etiqueta;
	private BitmapFont font;
	private boolean running;
	
	public Timer(float time,float x,float y,String nombre) {
		timeRemaining=time;
		this.x=x;
		this.y=y;
		Etiqueta=nombre;
		running=true;
		
		font = new BitmapFont();
	}
	
	public void draw(Batch batch, float parentAlpha) {
		batch.end();
		batch.begin();
		font.draw(batch,Etiqueta + ": " + this.toString(), x, y);
	}
	
	public void render() {
		if(running) {
			this.timeRemaining-=Gdx.graphics.getDeltaTime();
		}
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
	
	public void Stop() {
		running=false;
	}
	
	public void Run() {
		running=true;
	}

	public float getTimeRemaining() {
		return timeRemaining;
	}
	
}
