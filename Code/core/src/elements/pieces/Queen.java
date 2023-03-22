package elements.pieces;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import elements.Piece;
import utils.Image;
import utils.Resources;

public class Queen extends Piece{
	public Queen() {
		this.path = Resources.QUEEN_PATH;
		this.color = true;
		this.sprite = new Image(path);
	}
	
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}
	/**
	 * Agrega a movements todos los movimientos posibles de la reina, en todas las direcciones, su maxima cantidad de movimientos, la union de el movimiento del alfin y de la torre
	 * @param x
	 * @param y
	 * @return
	 */
	@Override
	public ArrayList<Vector2> getMovement(float x, float y) {
		ArrayList<Vector2> movements = (new Rook()).getMovement(x, y);
		movements.addAll((new Bishop()).getMovement(x, y));
		
		return movements;
	}
	
	
	
	
	
	
	
	
	
	
	
}
