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
		Board board = GameScreen.board;
		addMovement(x+1,y+1, board, movements);
		addMovement(x+1,y, board, movements);
		addMovement(x+1,y-1, board, movements);
		addMovement(x,y+1, board, movements);
		addMovement(x,y-1, board, movements);
		addMovement(x-1,y+1, board, movements);
		addMovement(x-1,y, board, movements);
		addMovement(x-1,y-1, board, movements);	
		return movements;
	}
	
	public void addMovement(float x, float y, Board board, ArrayList<Vector2> movements) {
		if(board.getTile(x, y)!=null && !sameColor(board.getTile(x, y).getPiece())) {
			movements.add(new Vector2(x, y));
		}
	}
	
	private Boolean sameColor(Piece piece) {
		boolean same=false;
		if(piece!=null) {
			same=color==piece.color();
		}
		return same;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
}
