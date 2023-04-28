package elements.pieces;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import elements.Board;
import elements.Piece;
import elements.Tile;
import interaccionFichero.LectorLineas;
import utils.Render;
import utils.Resources;

import java.util.ArrayList;

public class King extends Piece{
	public King(Boolean color, int x, int y,Board board) {
		super(color, Render.app.getManager().get(Resources.KING_PATH, Texture.class), x, y,board);
	}
	
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}
	
	@Override
	protected void updateXY(int x, int y) {
		Render.GameScreen.board.getTile(this.x, this.y).attacked = false;

		super.updateXY(x, y);
		
		
		if(color) {
			Render.GameScreen.whiteKing.set(x, y);
		}else {
			
			Render.GameScreen.blackKing.set(x, y);
		}
	}
	
	@Override
	protected void simulateMovement(Tile currentTile, Vector2 move, ArrayList<Vector2> removeMovements) {
		Tile nextTile = Render.GameScreen.board.getTile((int)move.x,(int) move.y);
		Piece nextTilePiece = null;
		if(nextTile.getPiece()!=null) {
			nextTilePiece = nextTile.getPiece();
		}
		currentTile.simulateMoveTo(nextTile);
		
			//check king
			if(color && !isKingSafe(Render.GameScreen.blackPieces, new Vector2(move.x, move.y))) {
				removeMovements.add(move);
			}else if(!color && !isKingSafe(Render.GameScreen.whitePieces, new Vector2(move.x, move.y))) {
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
		addMovement(x+1,y+1, board, movements);
		addMovement(x+1,y, board, movements);
		addMovement(x+1,y-1, board, movements);
		addMovement(x,y+1, board, movements);
		addMovement(x,y-1, board, movements);
		addMovement(x-1,y+1, board, movements);
		addMovement(x-1,y, board, movements);
		addMovement(x-1,y-1, board, movements);	
		
		if (!hasBeenMoved && board.dim==8) {
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
		if (board.getTile(1, y).getPiece() instanceof Rook && board.getTile(1, y).getPiece().hasBeenMoved == false && isSafe(1, y, x)) {
			movements.add(new Vector2(x - 2, y));
		}
		if (board.getTile(8, y).piece instanceof Rook && board.getTile(8, y).piece.hasBeenMoved == false && isSafe(x, y, 8)) {
				movements.add(new Vector2(x + 2, y));
		}
	}
	
	/**
	 * Comprueba que el espacio que hay entre las casillas de start y dest en la fila y este vacio, devuelve true si lo esta y false si no lo esta
	 * @param start
	 * @param dest
	 * @return
	 */
	private boolean isSafe(float start, float y, float dest) {
		boolean res=true;
			for(int i=(int) start+1; i<dest;i++) {
				if(board.getTile(i, y).piece!=null) {
					res=false;
				}else if (color && !isTileSafe(Render.GameScreen.blackPieces, new Vector2(i, y))) {
					res = false;
				} else if (!color && !isTileSafe(Render.GameScreen.whitePieces, new Vector2(i, y))) {
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

	public String getInfo() {
		 LectorLineas Reader, configReader;
		 configReader = new LectorLineas("files/config.txt");
		String config = configReader.leerLinea(1);
		Reader = new LectorLineas("files/lang/"+ config + "Clasicas.txt");
		switch (config){
			case "esp/":
				return Reader.leerTramo(40, 48);
			case "eng/":
				return Reader.leerTramo(33,39);
			default:
				throw new IllegalArgumentException("Configuración errónea");
		}
	}

	@Override
	public String toString() {
		String str = super.toString();
		return str.replace("X","King");
	}
	
	
	
	
	
	
	
	
	
	
}
