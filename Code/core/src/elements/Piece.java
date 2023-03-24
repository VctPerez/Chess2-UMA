package elements;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import utils.Image;

public abstract class Piece extends Actor {
	protected Image sprite;
	protected Boolean color;
	public Boolean hasBeenMoved;
	protected Boolean selected;

	public Piece(Boolean color, Texture texture) {
		this.sprite = new Image(texture);
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
	private void checkDirection(float x, float y, int i, int j, ArrayList<Vector2> movements) {
		
	}
	
	private Boolean checkBoard(Board board, float x, float y) {
		return null;
	}


}
