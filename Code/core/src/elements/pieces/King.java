package elements.pieces;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import elements.Piece;
import utils.Image;
import utils.Resources;

public class King extends Piece{
	public King() {
		this.path = Resources.KING_PATH;
		//this.color = ;
		this.sprite = new Image(path);
	}
	
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}
	/**
	 * Añade a movements todos los movimientos posibles del rey, en todas las direcciones, 1 sola casilla
	 * @param x
	 * @param y
	 * @return
	 */
	@Override
	public ArrayList<Vector2> getMovement(float x, float y) {
		ArrayList<Vector2> movements = new ArrayList<>();
		movements.add(new Vector2(x+1,y+1));
		movements.add(new Vector2(x+1,y));
		movements.add(new Vector2(x+1,y-1));
		movements.add(new Vector2(x,y+1));
		movements.add(new Vector2(x,y-1));
		movements.add(new Vector2(x-1,y+1));
		movements.add(new Vector2(x-1,y));
		movements.add(new Vector2(x-1,y-1));	
		return movements;
	}
}
