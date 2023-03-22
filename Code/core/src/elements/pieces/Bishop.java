package elements.pieces;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import elements.Board;
import elements.Piece;
import game.chess.GameScreen;
import utils.Image;
import utils.Resources;

public class Bishop extends Piece{
	
	public Bishop() {
		this.path = Resources.BISHOP_PATH;
		//this.color = ;
		this.sprite = new Image(path);
	}
	
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}
	
	private Boolean sameColor(Piece piece) {
		return color==piece.color();
	}
	
	private Boolean checkBoard(Board board, float x, float y, Boolean validDirection) {
		Boolean res = true;
		
		if(board.getTile(x, y)==null) {
			res = false;
			validDirection = false;
		}else {
			if(board.getTile(x, y).getPiece()!=null && sameColor(board.getTile(x, y).getPiece())) {
				res = false;
				validDirection = false;
			}else if(board.getTile(x, y).getPiece()!=null && !sameColor(board.getTile(x, y).getPiece())) {
				res = true;
				validDirection = false;
			}
		}
		return res;
	}
	
	/**
	 * Anyade a movements todos los movimientos posibles del Alfil, en todas las direcciones, su maxima cantidad de movimientos
	 * @param x
	 * @param y
	 * @return
	 */
	@Override
	public ArrayList<Vector2> getMovement(float x, float y) {
		ArrayList<Vector2> movements = new ArrayList<>();
		Boolean validDirection = true;
		Vector2 mov;
		int i = 1;
		for(int j=1;i<8;i++) {
			movements.add(new Vector2(x+i,y+i));
			movements.add(new Vector2(x-i,y-i));
			movements.add(new Vector2(x+i,y-i));
			movements.add(new Vector2(x-i,y+i));
		}
		
		while(validDirection && i<8) {
			mov = new Vector2(x + i, y + i);
			if(checkBoard(GameScreen.board, mov.x, mov.y, validDirection)) {
				movements.add(mov);
			}
			i++;
		}
		
		
		return movements;
	}
	
	

}
