package elements.pieces;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import elements.Board;
import elements.Piece;
import interaccionFichero.LineReader;
import utils.Resources;

import java.util.ArrayList;

public class Queen extends Piece{
	private Boolean validDirection;
	
	public Queen(Boolean color, int x, int y,Board board) {
		super(color, Resources.QUEEN_PATH, x, y,board);
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
			if(checkBoard(board, mov.x, mov.y)) {
				movements.add(mov);
			}
			k++;
		}
	}
	
	/**
	 * Agrega a movements todos los movimientos posibles de la reina, en todas las direcciones, su maxima cantidad de movimientos, la union de el movimiento del alfil y de la torre
	 * @param x
	 * @param y
	 * @return
	 */
	@Override
	public ArrayList<Vector2> posibleMovements() {
		ArrayList<Vector2> movements = new ArrayList<>();
		//HORIZONTAL
		checkDirection(x, y, 1, 0, movements);
		checkDirection(x, y, 0, 1, movements);
		checkDirection(x, y, -1, 0, movements);
		checkDirection(x, y, 0, -1, movements);
		
		//DIAGONAL
		checkDirection(x, y, 1, 1, movements);
		checkDirection(x, y, -1, -1, movements);
		checkDirection(x, y, 1, -1, movements);
		checkDirection(x, y, -1, 1, movements);
		
		return movements;
	}
	
	public String getInfo() {
		 LineReader Reader, configReader;
		 configReader = new LineReader("files/config.txt");
		String config = configReader.readLine(1);
		Reader = new LineReader("files/lang/"+ config + "Clasicas.txt");
		switch (config){
			case "esp/":
				return Reader.readSection(34, 38);
			case "eng/":
				return Reader.readSection(27,31);
			default:
				throw new IllegalArgumentException("Configuración errónea");
		}
	}

	@Override
	public String toString() {
		String str = super.toString();
		return str.replace("X","Queen");
	}
	
	
	
	
	
	
	
	
	
}
