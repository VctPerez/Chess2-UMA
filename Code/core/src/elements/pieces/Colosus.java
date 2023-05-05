package elements.pieces;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import elements.Board;
import elements.Piece;
import interaccionFichero.LectorLineas;
import utils.Resources;

import java.util.ArrayList;

public class Colosus extends Piece{
	private Boolean validDirection;

	public Colosus(Boolean color, int x, int y, Board board) {
		super(color, Resources.COLOSUS_PATH, x, y, board);
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
			if(board.getTile(x, y).getPiece()!=null) {
				res = false;
				validDirection = false;
			}
		}
		return res;
	}
	
	private void checkDirection(float x, float y, int i, int j, ArrayList<Vector2> movements) {
	    validDirection = true;
		Vector2 mov;
		int k = 1;
		
		while(validDirection && k<4) {
			mov = new Vector2(x + i*k, y + j*k);
			if(checkBoard(board, mov.x, mov.y)) {
				movements.add(mov);
			}
			k++;
		}
	}
	
	
	/**
	 * Añade a movements todos los movimientos posibles de la torre, en todas las direcciones, su maxima cantidad de movimientos
	 * @param x
	 * @param y
	 * @return
	 */
	@Override
	public ArrayList<Vector2> posibleMovements() {
		ArrayList<Vector2> movements = new ArrayList<>();
		
		checkDirection(x, y, 1, 0, movements);
		checkDirection(x, y, 0, 1, movements);
		checkDirection(x, y, -1, 0, movements);
		checkDirection(x, y, 0, -1, movements);
		
		return movements;
	}
	
	public String getInfo() {
		 LectorLineas Reader, configReader;
		 configReader = new LectorLineas("files/config.txt");
		String config = configReader.leerLinea(1);
		Reader = new LectorLineas("files/lang/"+ config + "Modified.txt");
		switch (config){
			case "esp/":
				return Reader.leerTramo(62, 66);
			case "eng/":
				return Reader.leerTramo(64,68);
			default:
				throw new IllegalArgumentException("Configuración errónea");
		}
	}

	@Override
	public String toString() {
		String str = super.toString();
		return str.replace("X","Colosus");
	}

}
