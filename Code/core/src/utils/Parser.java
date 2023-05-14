package utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.StringBuilder;

import elements.Board;
import elements.Piece;
import elements.Tile;
import elements.pieces.*;

import java.util.ArrayList;

public class Parser {
	
	public static Piece getPieceFromPath(String piecePath, Boolean color, int x, int y, Board board) {
		Piece piece = null;
			
		switch (piecePath) {
		case Resources.PAWN_PATH:
			piece = new Pawn(color, x, y, board);
			break;
		case Resources.LANCER_PATH:
			piece = new Lancer(color, x, y, board);
			break;
		case Resources.JOKER_PATH:
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
		case Resources.MINER_PATH:
			piece = new Miner(color, x, y, board);
			break;
		case Resources.WITCH_PATH:
			piece = new Witch(color, x, y, board);
			break;
		case Resources.VALKYRIE_PATH:
			piece = new Valkyrie(color,x,y,board);
			break;
		case Resources.MAGE_PATH:
			piece = new Mage(color,x,y,board);
			break;
		}
		return piece;
	}
	
	public static String parseDraftToString(ArrayList<String> draft) {
		StringBuilder sb = new StringBuilder();
		for (String piece : draft) {
			sb.append(piece).append("#");
		}
		return sb.toString();
	}
	
	public static Image getImageFromPath(String piecePath) {
		return new Image(Render.app.getManager().get(piecePath, Texture.class));
	}
	
	public static String parseMovementToString(Tile currentTile, Tile nextTile) {

		return currentTile.getPos().x + "," + currentTile.getPos().y + "-" + nextTile.getPos().x + "," + nextTile.getPos().y;
	}
	
	public static void parseStringToMovement(String movement){
		System.out.println("Parsed "+movement);
		final String[] params = movement.split("-");
		String[] ogTile = params[0].split(",");
		String[] nxtTile = params[1].split(",");
		
		float current_x = Float.parseFloat(ogTile[0]);
		float current_y = Float.parseFloat(ogTile[1]);
		float next_x = Float.parseFloat(nxtTile[0]);
		float next_y = Float.parseFloat(nxtTile[1]);
		Tile currentTile = Render.GameScreen.board.getTile(current_x, current_y);
		final Tile nextTile = Render.GameScreen.board.getTile(next_x, next_y);

		Render.GameScreen.makeMove(currentTile, nextTile);

		
		if(params.length==3) {
			Action promote = new Action() {
				public boolean act(float delta) {
					promotePiece(nextTile, params[2]);
					return true;
				}
			};
			nextTile.addAction(Actions.delay(0.5f, promote));
		}
	}

	/**
	 * Devuelve un color según el string indicado
	 * <p>Soporta rojo,verde,azul y negro en inglés</p>
	 * <p>Si {@code str} no esta soportado, devuelve {@code null}</p>
	 * @param str nombre del color
	 * @return objeto de la clase {@code Color}
	 */
	public static Color parseStringToColor(String str){
		switch (str){
			case "RED":
				//Significado de los bits:--rrggbbaa
				return new Color(0x7f3a4cff);
			case "GREEN":
				return new Color(0x2c7f4cff);
			case "BLUE":
				return new Color(0x2c314cff);
			case "BLACK":
				return new Color(0x000000ff);
			default:
				return null;
		}
	}
	
	private static void promotePiece(Tile tile, String newPiecePath) {
		ArrayList<Piece> pieces;
		Piece formerPiece = tile.getPiece();
        Piece newPiece = Parser.getPieceFromPath(newPiecePath, formerPiece.color(), (int)tile.getPos().x, (int)tile.getPos().y, Render.GameScreen.board);
        
        if(formerPiece.color()) {
        	pieces = Render.GameScreen.whitePieces;
        }else {
        	pieces = Render.GameScreen.blackPieces;
        }
        pieces.remove(formerPiece);
        formerPiece.remove();
        newPiece.setTouchable(Touchable.disabled);	
		newPiece.setSize(84, 84);
		tile.setPiece(newPiece);
		pieces.add(newPiece);            	
        Render.GameScreen.stage.addActor(newPiece);
        Render.GameScreen.promoting = false;
        Render.GameScreen.mateControl();
	}
}
