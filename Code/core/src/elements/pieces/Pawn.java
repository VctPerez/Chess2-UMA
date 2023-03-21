package elements.pieces;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import elements.Board;
import elements.Piece;
import game.chess.GameScreen;
import utils.Image;
import utils.Resources;

public class Pawn extends Piece{
	
	
	public Pawn() {
		this.path = Resources.PAWN_PATH;
		this.hasBeenMoved = false;
		//this.color = ;
		this.sprite = new Image(path);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}
	
	@Override
	public void hasBeenMoved() {
		hasBeenMoved = true;
	}
	
	/**
	 * Devuelve un ArrayList de Vector2 con las posibles casillas a las que puede moverse el peon con posición (x,y)
	 * @param x
	 * @param y
	 * @return
	 */
	@Override
	public ArrayList<Vector2> getMovement(float x, float y) {//para implementar esta función en cada pieza habrá que hacerlo de forma 
		ArrayList<Vector2> movements = new ArrayList<>();
		Vector2 mov;
		for(int i = -1; i<=1; i++) {
			mov = new Vector2(x + i, y + 1);
			if(i!=0 && GameScreen.board.getTile(mov.x, mov.y).piece!=null) {//implimentar un método que compruebe las condicones?
				movements.add(mov);
			}
		}
		if(!hasBeenMoved) {
			movements.add(new Vector2(x , y + 2));
		}
		
		return movements;
	}

}
