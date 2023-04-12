package elements.pieces;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import elements.Board;
import elements.Piece;
import game.chess.GameScreen;
import interaccionFichero.LectorLineas;
import utils.Render;
import utils.Resources;

public class Bishop extends Piece{
	private Boolean validDirection;
	
	public Bishop(Boolean color, int x, int y) {
		super(color, Render.app.getManager().get(Resources.BISHOP_PATH, Texture.class), x, y);
	}
	
	public Bishop() {
		super(Render.app.getManager().get(Resources.BISHOP_PATH, Texture.class));
	}
	
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}
	
	private Boolean checkBoard(Board board, float x, float y) {
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
		 validDirection = true;
		Vector2 mov;
		int k = 1;
		
		while(validDirection && k<8) {
			mov = new Vector2(x + i*k, y + j*k);
			if(checkBoard(GameScreen.board, mov.x, mov.y)) {
				movements.add(mov);
			}
			k++;
		}
	}
	
	/**
	 * Anyade a movements todos los movimientos posibles del Alfil, en todas las direcciones, su maxima cantidad de movimientos
	 * @param x
	 * @param y
	 * @return
	 */
	@Override
	public ArrayList<Vector2> posibleMovements() {
		ArrayList<Vector2> movements = new ArrayList<>();
		
		checkDirection(x, y, 1, 1, movements);
		checkDirection(x, y, -1, -1, movements);
		checkDirection(x, y, 1, -1, movements);
		checkDirection(x, y, -1, 1, movements);

		return movements;
	}
	public String getInfo() {
		 LectorLineas Reader, configReader;
		 configReader = new LectorLineas("files/config.txt");
	     Reader = new LectorLineas("files/lang/"+ configReader.leerLinea(1) + "Clasicas.txt");
	     return Reader.leerTramo(7, 10);
	}
	
	

}
