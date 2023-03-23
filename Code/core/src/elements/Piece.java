package elements;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import utils.Image;

public abstract class Piece extends Actor {
	protected String path;
	protected Image sprite;
	protected Boolean color;
	protected Vector2 movement;
	public Boolean hasBeenMoved;
	protected Boolean selected;

	public Piece(Boolean color, String path) {
		this.path = path;
		this.sprite = new Image(path);
		this.hasBeenMoved=false;
		this.color = color;
		
		if(color) {
			setColor(Color.WHITE);
		}else {
			setColor(0.25f, 0.25f, 0.25f, 1f);
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		sprite.setPosition(getX(), getY());
		sprite.setSize(getWidth(), getHeight());
		sprite.setScale(getScaleX());
		sprite.sprt.setColor(getColor());
		sprite.draw(batch);
	}
	
	public Boolean color() {
		return color;
	}

	public void hasBeenMoved() {
		hasBeenMoved=true;
	}
	
	public void setSprite(String path) {
		this.sprite = new Image(path);
	}
	
	/*
	 * public Boolean checkBoard(Board board, float x, float y) { return false; }
	 */
	
	public Boolean sameColor(Piece piece) {
		return color==piece.color();
	}

	
	public void dispose() {
		sprite.dispose();
	}
	
	public ArrayList<Vector2> getMovement(float x, float y) {
		return null;
	}


}
