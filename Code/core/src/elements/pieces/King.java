package elements.pieces;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import elements.Board;
import elements.Piece;
import game.chess.GameScreen;
import utils.Image;
import utils.Resources;

public class King extends Piece{
	public King() {
		this.path = Resources.KING_PATH;
		this.color = true;
		this.sprite = new Image(path);
		this.hasBeenMoved=false;
	}
	
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}
	/**
	 * A�ade a movements todos los movimientos posibles del rey, en todas las direcciones, 1 sola casilla
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
		return getValidMovements(movements,GameScreen.board);
	}
	
	private Boolean sameColor(Piece piece) {
		boolean same=false;
		if(piece!=null) {
			same=color==piece.color();
		}
		return same;
	}
	
	public ArrayList<Vector2> getValidMovements(ArrayList<Vector2> movements,Board board){
		for(int i=0;i<movements.size();i++) {
			if(board.getTile(movements.get(i).x, movements.get(i).y)==null 
				|| sameColor(board.getTile(movements.get(i).x, movements.get(i).y).getPiece())) {
				movements.remove(i);
			}
		}
		return movements;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
