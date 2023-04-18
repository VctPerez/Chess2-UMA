package utils;

import com.badlogic.gdx.graphics.Texture;

import elements.Board;
import elements.Piece;
import elements.pieces.*;

public class Parser {

	public Parser() {
		
	}
	
	public static Piece getPieceFromPath(String piecePath, boolean color, int x, int y, Board board) {
		Piece piece = null;
			
		switch (piecePath) {
		case Resources.PAWN_PATH:
			piece = new Bomber(color, x, y, board);
			break;
		case Resources.LANCER_PATH:
			piece = new Lancer(color, x, y, board);
			break;
		case Resources.RND_PATH:
			piece = new RandomPiece(color,x,y,board);
			break;
		case Resources.KNIGHT_PATH:
			piece = new Knight(color, x, y, board);
			break;
		case Resources.RIDER_PATH:
			piece = new Rider(color, x, y, board);
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
	
	public static Image getImageFromPath(String piecePath) {
		return new Image(Render.app.getManager().get(piecePath, Texture.class));
	}
}
