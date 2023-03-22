package elements;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import utils.Image;

public abstract class Piece extends Actor {
	protected String path;
	protected Image sprite;
	protected Boolean color;
	protected Vector2 movement;
	protected Boolean hasBeenMoved;

	public Piece() {
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		sprite.setPosition(getX(), getY());
		sprite.setSize(getWidth(), getHeight());
		sprite.setScale(getScaleX());
		sprite.draw(batch);
	}
	
	public Boolean color() {
		return color;
	}

	public void hasBeenMoved() {

	}
	/*
	 * public Boolean checkBoard(Board board, float x, float y) { return false; }
	 */

	public ArrayList<Vector2> getMovement(float x, float y) {
		return null;
	}

}
