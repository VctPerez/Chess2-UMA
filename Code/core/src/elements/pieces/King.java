package elements.pieces;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import elements.Board;
import elements.Piece;
import elements.Tile;
import game.chess.GameScreen;
import interaccionFichero.LectorLineas;
import utils.Render;
import utils.Resources;

public class King extends Piece{
	public King(Boolean color, int x, int y) {
		super(color, Render.app.getManager().get(Resources.KING_PATH, Texture.class), x, y);
	}
	
	public King() {
		super(Render.app.getManager().get(Resources.KING_PATH, Texture.class));
	}
	
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}
	
	@Override
	protected void updateXY(int x, int y) {
		GameScreen.board.getTile(this.x, this.y).attacked = false;

		super.updateXY(x, y);
		
		
		if(color) {
			GameScreen.whiteKing.set(x, y);
		}else {
			
			GameScreen.blackKing.set(x, y);
		}
	}
	
	@Override
	protected void simulateMovement(Tile currentTile, Vector2 move, ArrayList<Vector2> removeMovements) {
		Tile nextTile = GameScreen.board.getTile((int)move.x,(int) move.y);
		Piece nextTilePiece = null;
		if(nextTile.getPiece()!=null) {
			nextTilePiece = nextTile.getPiece();
		}
		currentTile.simulateMoveTo(nextTile);
		
			//check king
			if(color && !isKingSafe(GameScreen.blackPieces, new Vector2(move.x, move.y))) {
				removeMovements.add(move);
			}else if(!color && !isKingSafe(GameScreen.whitePieces, new Vector2(move.x, move.y))) {
				removeMovements.add(move);
			}
			
		undoLastMovement(currentTile,  nextTile, nextTilePiece);
	}

	
	/**
	 * A�ade a movements todos los movimientos posibles del rey, en todas las direcciones, 1 sola casilla
	 * @param x
	 * @param y
	 * @return
	 */
	@Override
	public ArrayList<Vector2> posibleMovements() {
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
		
		if (!hasBeenMoved) {
			castling(x, y, movements);
		}
		
		return movements;
	}
	
	public void addMovement(float x, float y, Board board, ArrayList<Vector2> movements) {
		if(board.getTile(x, y)!=null && !sameColor(board.getTile(x, y).getPiece())) {
			movements.add(new Vector2(x, y));
		}
	}
	
	
	public void castling(float x, float y, ArrayList<Vector2> movements) {//falta implementar q sea un movimiento seguro
		if (GameScreen.board.getTile(1, y).getPiece() instanceof Rook && GameScreen.board.getTile(1, y).getPiece().hasBeenMoved == false && isFreeSpace(1, y, x)) {
			movements.add(new Vector2(x - 2, y));
		}
		if (GameScreen.board.getTile(8, y).piece instanceof Rook && GameScreen.board.getTile(8, y).piece.hasBeenMoved == false && isFreeSpace(x, y, 8)) {
				movements.add(new Vector2(x + 2, y));
		}
	}
	
	/**
	 * Comprueba que el espacio que hay entre las casillas de start y dest en la fila y este vacio, devuelve true si lo esta y false si no lo esta
	 * @param start
	 * @param dest
	 * @return
	 */
	private boolean isFreeSpace(float start, float y, float dest) {
		boolean res=true;
			for(int i=(int) start+1; i<dest;i++) {

				if(GameScreen.board.getTile(i, y).piece!=null) {
					res=false;
				}
			}
		return res;
	}
	
	
	@Override
	public Boolean sameColor(Piece piece) {
		boolean same=false;
		if(piece!=null) {
			same=color==piece.color();
		}
		return same;
	}

	public String getInfo() {
		 LectorLineas Reader, configReader;
		 configReader = new LectorLineas("files/config.txt");
	     Reader = new LectorLineas("files/lang/"+ configReader.leerLinea(1) + "Clasicas.txt");
	     return Reader.leerTramo(40, 48);
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
