package elements.pieces;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import elements.Board;
import elements.Piece;
import game.chess.GameScreen;
import utils.Image;
import utils.Resources;

public class Rook extends Piece{
	public Rook() {
		this.path = Resources.ROOK_PATH;
		this.color = true;
		this.sprite = new Image(path);
	}
	
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}
	
	private Boolean sameColor(Piece piece) {
		boolean same=false;
		if(piece!=null) {
			same=color==piece.color();
		}
		return same;
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
	
	private void checkDirection(float x, float y, int i, int j, ArrayList<Vector2> movements) {
		Boolean validDirection = true;
		Vector2 mov;
		int k = 1;
		
		while(validDirection && k<8) {
			mov = new Vector2(x + i*k, y + j*k);
			if(checkBoard(GameScreen.board, mov.x, mov.y, validDirection)) {
				movements.add(mov);
			}
			k++;
		}
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
		
		checkDirection(x, y, 1, 0, movements);
		checkDirection(x, y, 0, 1, movements);
		checkDirection(x, y, -1, 0, movements);
		checkDirection(x, y, 0, -1, movements);
		
		return movements;
	}
}
