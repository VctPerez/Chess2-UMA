package utils;

import elements.Board;
import elements.Piece;
import elements.pieces.Bishop;
import elements.pieces.King;
import elements.pieces.Knight;
import elements.pieces.Pawn;
import elements.pieces.Queen;
import elements.pieces.Rook;

public class Parser {

	public Parser() {
		
	}
	
	public Piece getPieceFromPath(String piecePath, boolean color, int x, int y, Board board) {
		Piece piece = null;
			
		switch (piecePath) {
		case Resources.PAWN_PATH:
			piece = new Pawn(color, x, y, board);
			break;
		case Resources.KNIGHT_PATH:
			piece = new Knight(color, x, y, board);
			break;
		case Resources.ROOK_PATH:
			piece = new Rook(color, x, y, board);
			break;
		case Resources.BISHOP_PATH:
			piece = new Bishop(color, x, y, board);
			break;
		case Resources.QUEEN_PATH:
			piece = new Queen(color, x, y, board);
			break;
		case Resources.KING_PATH:
			piece = new King(color, x, y, board);
			break;
		}
		return piece;
	}
}
