package elements.pieces;

import com.badlogic.gdx.graphics.g2d.Batch;

import elements.Piece;
import utils.Image;
import utils.Resources;

public class Knight extends Piece{
	
	
	public Knight() {
		this.path = Resources.KNIGHT_PATH;
		//this.Movement = ;
		//this.color = ;
		this.sprite = new Image(path);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}

}
