package elements.pieces;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import elements.Piece;
import utils.Image;
import utils.Resources;

public class Rook extends Piece{
	public Rook() {
		this.path = Resources.ROOK_PATH;
		//this.color = ;
		this.sprite = new Image(path);
	}
	
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}
	/**
	 * Añade a movements todos los movimientos posibles de la torre, en todas las direcciones, su maxima cantidad de movimientos
	 * @param x
	 * @param y
	 * @return
	 */
	@Override
	public ArrayList<Vector2> getMovement(float x, float y) {
		ArrayList<Vector2> movements = new ArrayList<>();
		for(int i=1;i<8;i++) {
			movements.add(new Vector2(x+i,y));
		}
		for(int i=1;i<8;i++) {
			movements.add(new Vector2(x-i,y));
		}
		for(int i=1;i<8;i++) {
			movements.add(new Vector2(x,y-i));
		}
		for(int i=1;i<8;i++) {
			movements.add(new Vector2(x,y+i));
		}	
		return movements;
	}
}
