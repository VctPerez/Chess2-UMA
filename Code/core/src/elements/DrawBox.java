package elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;


import utils.*;

public class DrawBox extends Actor{
	protected Vector2 pos;	
	private ImageButton check=new ImageButton(new Image(Resources.TICK_PATH));
	private ImageButton cross=new ImageButton(new Image(Resources.CROSS_PATH));
	
	public DrawBox() {
		check.setPosition(10, 10);
		cross.setPosition(20, 10);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.end();
		check.draw(batch, parentAlpha);
		cross.draw(batch, parentAlpha);
		batch.begin();
		
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
	}
}
