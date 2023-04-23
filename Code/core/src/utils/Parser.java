package utils;

import com.badlogic.gdx.graphics.Texture;

import elements.Board;
import elements.Piece;
import elements.Tile;
import elements.pieces.*;

public class Parser {
	
	public static Piece getPieceFromPath(String piecePath, boolean color, int x, int y, Board board) {
		Piece piece = null;
			
		switch (piecePath) {
		case Resources.PAWN_PATH:
			piece = new Pawn(color, x, y, board);
			break;
		case Resources.LANCER_PATH:
			piece = new Lancer(color, x, y, board);
			break;
		case Resources.RND_PATH:
			piece = new Joker(color,x,y,board);
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
		case Resources.BOMBER_PATH:
			piece = new Bomber(color,x,y,board);
			break;
		case Resources.WARDEN_PATH:
			piece = new Guardian(color,x,y,board);
			break;
		case Resources.PALADIN_PATH:
			piece = new Paladin(color,x,y,board);
			break;
		case Resources.MIDAS_PATH:
			piece = new Midas(color,x,y,board);
			break;
		case Resources.COLOSUS_PATH:
			piece = new Colosus(color,x,y,board);
			break;	
		
		}
		return piece;
	}
	
	public static Image getImageFromPath(String piecePath) {
		return new Image(Render.app.getManager().get(piecePath, Texture.class));
	}
	
	public static String parseMovementToString(Tile currentTile, Tile nextTile) {
		return currentTile.getPos().x + "," + currentTile.getPos().y + "-" + nextTile.getPos().x + "," + nextTile.getPos().y;
	}
	
	public static void parseStringToMovement(String movement){
		String[] params = movement.split("-");
		String[] ogTile = params[0].split(",");
		String[] nxtTile = params[1].split(",");

		Render.GameScreen.makeMove(Render.GameScreen.board.getTile(Float.parseFloat(ogTile[0]), Float.parseFloat(ogTile[1])),
				Render.GameScreen.board.getTile(Float.parseFloat(nxtTile[0]), Float.parseFloat(nxtTile[1])));
		System.out.println("movimiento parseado");
	}
}
