package elements.pieces;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import elements.Board;
import elements.Piece;
import interaccionFichero.LectorLineas;
import utils.Resources;

import java.util.ArrayList;

public class Rider extends Piece{

	public Rider(Boolean color, int x, int y,Board board) {
		super(color, Resources.RIDER_PATH, x ,y,board);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}

	/**
	 * A�ade a movements todos los movimientos posibles del jinete
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	
	@Override
	public ArrayList<Vector2> posibleMovements() {
		ArrayList<Vector2> movements = new ArrayList<>();
		int direction;
		if(color) {
			direction = 1;
		}else {
			direction = -1;
		}
		
			addMovement(x + 2, y + direction, board, movements);
			addMovement(x + 2, y + 2*direction, board, movements);
			addMovement(x + 1, y + 2*direction, board, movements);
			addMovement(x + 1, y - direction, board, movements);
			addMovement(x - 2, y + direction, board, movements);
			addMovement(x - 2, y + 2*direction, board, movements);
			addMovement(x - 1, y + 2*direction, board, movements);
			addMovement(x - 1, y - direction, board, movements);

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
		Reader = new LectorLineas("files/lang/"+ config + "Modified.txt");
		switch (config){
			case "esp/":
				return Reader.leerTramo(8, 12);
			case "eng/":
				return Reader.leerTramo(7,11);
			default:
				throw new IllegalArgumentException("Configuración errónea");
		}
	}

	@Override
	public String toString() {
		String str = super.toString();
		return str.replace("X","Rider");
	}
	
}