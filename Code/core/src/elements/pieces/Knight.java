package elements.pieces;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import elements.Board;
import elements.Piece;
import game.chess.GameScreen;
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
	
	public ArrayList<Vector2> getMovements(float x, float y) {
		ArrayList<Vector2> movements = new ArrayList<>();
		movements.add(new Vector2(x+2,y+1));
		movements.add(new Vector2(x+1,y+2));
		movements.add(new Vector2(x+2,y-1));
		movements.add(new Vector2(x+1,y-2));
		movements.add(new Vector2(x-1,y-2));
		movements.add(new Vector2(x-2,y+1));
		movements.add(new Vector2(x-2,y-1));
		movements.add(new Vector2(x-1,y+2));
		return getValidMovements(movements,GameScreen.board);
	}
	
	private Boolean sameColor(Piece piece) {
		return color==piece.color();
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
