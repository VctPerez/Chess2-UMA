package elements.pieces;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import elements.Board;
import elements.Piece;
import elements.Tile;
import game.chess.GameScreen;
import interaccionFichero.LectorLineas;
import utils.Render;
import utils.Resources;

public class Midas extends Piece {

	public Midas(Boolean color, int x, int y, Board board) {
		super(color, Render.app.getManager().get(Resources.MIDAS_PATH, Texture.class), x, y, board);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}

	@Override
	protected void updateXY(int x, int y) {
		board.getTile(this.x, this.y).attacked = false;

		super.updateXY(x, y);
		
		
		if(color) {
			Render.game_screen.whiteKing.set(x, y);
		}else {
			
			Render.game_screen.blackKing.set(x, y);
		}
	}
	
	protected void simulateMovement(Tile currentTile, Vector2 move, ArrayList<Vector2> removeMovements) {
		Tile nextTile = board.getTile((int)move.x,(int) move.y);
		Piece nextTilePiece = null;
		if(nextTile.getPiece()!=null) {
			nextTilePiece = nextTile.getPiece();
		}
		currentTile.simulateMoveTo(nextTile);
		
			//check king
			if(color && !isKingSafe(Render.game_screen.blackPieces, new Vector2(move.x, move.y))) {
				removeMovements.add(move);
			}else if(!color && !isKingSafe(Render.game_screen.whitePieces, new Vector2(move.x, move.y))) {
				removeMovements.add(move);
			}
			
		undoLastMovement(currentTile,  nextTile, nextTilePiece);
	}

	@Override
	public ArrayList<Vector2> posibleMovements() {
		ArrayList<Vector2> movements = new ArrayList<>();
		int direction;
		if (color) {
			direction = 1;
		} else {
			direction = -1;
		}

		if (super.ate >= 0) {
			addMovement(x, y + direction, board, movements);
			addMovement(x, y - direction, board, movements);
			addMovement(x + direction, y, board, movements);
			addMovement(x - direction, y, board, movements);
		}
		if (super.ate >= 1) {
			addMovement(x + direction, y + direction, board, movements);
			addMovement(x - direction, y - direction, board, movements);
			addMovement(x + direction, y - direction, board, movements);
			addMovement(x - direction, y + direction, board, movements);
		}
		if (super.ate >= 3) {
			addMovement(x, y + 2*direction, board, movements);
			addMovement(x, y - 2*direction, board, movements);
			addMovement(x + 2*direction, y, board, movements);
			addMovement(x - 2*direction, y, board, movements);
			addMovement(x + 2*direction, y + 2*direction, board, movements);
			addMovement(x - 2*direction, y - 2*direction, board, movements);
			addMovement(x + 2*direction, y - 2*direction, board, movements);
			addMovement(x - 2*direction, y + 2*direction, board, movements);
		}

		return movements;
	}

	public void addMovement(float x, float y, Board board, ArrayList<Vector2> movements) {
		if (board.getTile(x, y) != null && !sameColor(board.getTile(x, y).getPiece())) {
			movements.add(new Vector2(x, y));
		}
	}

	public void dispose() {
		sprite.dispose();
	}

	@Override
	public String getInfo() {
		LectorLineas Reader, configReader;
		configReader = new LectorLineas("files/config.txt");
		String config = configReader.leerLinea(1);
		Reader = new LectorLineas("files/lang/" + config + "Modified.txt");
		switch (config) {
		case "esp/":
			return Reader.leerTramo(34, 41);
		case "eng/":
			return Reader.leerTramo(35, 42);
		default:
			throw new IllegalArgumentException("Configuración errónea");
		}
	}
	
	private boolean isSafe(float start, float y, float dest) {
		boolean res=true;
			for(int i=(int) start+1; i<dest;i++) {
				if(board.getTile(i, y).piece!=null) {
					res=false;
				}else if (color && !isTileSafe(Render.game_screen.blackPieces, new Vector2(i, y))) {
					res = false;
				} else if (!color && !isTileSafe(Render.game_screen.whitePieces, new Vector2(i, y))) {
					res = false;
				}
			}
		return res;
	}
	
	protected boolean isTileSafe(ArrayList<Piece> pieces, Vector2 pos) {
		boolean isSafe = true;
		for (Piece piece : pieces) {
			if(piece.hasBeenMoved || !(piece instanceof King)) {
				if (piece.posibleMovements().contains(pos)) {
					isSafe = false;
				}
			}
		}
		return isSafe;
	}
	
	
	@Override
	public Boolean sameColor(Piece piece) {
		boolean same=false;
		if(piece!=null) {
			same=color==piece.color();
		}
		return same;
	}

	@Override
	public String toString() {
		String str = super.toString();
		return str.replace("X", "Marshal");
	}

}
