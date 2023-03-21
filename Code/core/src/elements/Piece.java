package elements;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import utils.Image;


public abstract class Piece extends Actor{
	protected String path;
	protected Image sprite;
	protected Boolean color;
	protected Vector2 Movement;
	
	public Piece() {
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		sprite.setPosition(getX(), getY());
		sprite.setSize(getWidth(), getHeight());
		sprite.setScale(getScaleX());
		sprite.draw(batch);
	}

}
