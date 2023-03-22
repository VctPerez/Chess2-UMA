package elements.pieces;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

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
	/**
	 * A�ade a movements todos los movimientos posibles del caballo
	 * @param x
	 * @param y
	 * @return
	 */
	@Override
	public ArrayList<Vector2> getMovement(float x, float y) {
		ArrayList<Vector2> movements = new ArrayList<>();
		movements.add(new Vector2(x+2,y+1));
		movements.add(new Vector2(x+1,y+2));
		movements.add(new Vector2(x+2,y-1));
		movements.add(new Vector2(x+1,y-2));
		movements.add(new Vector2(x-1,y-2));
		movements.add(new Vector2(x-2,y+1));
		movements.add(new Vector2(x-2,y-1));
		movements.add(new Vector2(x-1,y+2));
		return movements;
	}
	
	
	
	
	
	
	

}
