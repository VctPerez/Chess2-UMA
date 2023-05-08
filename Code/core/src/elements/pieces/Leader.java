package elements.pieces;

import elements.Board;
import elements.Piece;
import utils.Resources;

public abstract class Leader extends Piece{

	public Leader(boolean color, String path, int x, int y, Board board) {
		super(color, path, x, y,board);
	}

}
