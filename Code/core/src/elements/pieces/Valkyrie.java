package elements.pieces;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import elements.Board;
import elements.Piece;
import interaccionFichero.LectorLineas;
import utils.Render;
import utils.Resources;

public class Valkyrie extends Piece {
	
	private Boolean validDirection;

	public Valkyrie(Boolean color, int x, int y,Board board) {
		super(color, Render.app.getManager().get(Resources.VALKYRIE_PATH, Texture.class), x, y,board);
	}
	
	

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}

	/**
	 * A�ade a movements todos los movimientos posibles del caballo
	 * 
	 * @param x
	 * @param y
	 * @return
	 */

	@Override
	public ArrayList<Vector2> posibleMovements() {
		ArrayList<Vector2> movements = new ArrayList<>();
		
		//Primero tiene los movimientos del caballo
		addMovement(x + 2, y + 1, board, movements);
		addMovement(x + 1, y + 2, board, movements);
		addMovement(x + 2, y - 1, board, movements);
		addMovement(x + 1, y - 2, board, movements);
		addMovement(x - 1, y - 2, board, movements);
		addMovement(x - 2, y + 1, board, movements);
		addMovement(x - 2, y - 1, board, movements);
		addMovement(x - 1, y + 2, board, movements);
		
		//Luego los propios de la valkyria similares al alfil
		checkDirection(x, y, 3, 2, movements);
		checkDirection(x, y, -3, -2, movements);
		checkDirection(x, y, 3, -2, movements);
		checkDirection(x, y, -3, 2, movements);
		checkDirection(x, y, 2, 3, movements);
		checkDirection(x, y, -2, -3, movements);
		checkDirection(x, y, 2, -3, movements);
		checkDirection(x, y, -2, 3, movements);
		
		return movements;
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
		int k = 0;
		
		//útil para el cálculo
		int si = (int) Math.signum(i);
		int sj = (int) Math.signum(j);
		
		//Si la casilla anterior (salto caballo) ataca una pieza, no deberia seguir calculando movimientos detrás suya
		if(board.getTile(x+ (i - si), y+ (j - sj)) != null && board.getTile(x+ (i - si), y+ (j - sj)).piece==null) {
			//IMPORTANTE, MODIFICAR EL VALOR X DE K<X PARA AJUSTAR LA PIEZA YA QUE ESTA ROTA Y HAY QUE VER COMO AJUSTARLA
			while(validDirection && k<2) {
				mov = new Vector2(x + i + k*si, y + j + k*sj);
				if(checkBoard(board, mov.x, mov.y)) {
					movements.add(mov);
				}
				k++;
			}
		}
	}

	public void addMovement(float x, float y, Board board, ArrayList<Vector2> movements) {
		if (board.getTile(x, y) != null && !sameColor(board.getTile(x, y).getPiece())) {
			movements.add(new Vector2(x, y));
		}
	}
	
	public String getInfo() {
		 LectorLineas Reader, configReader;
		 configReader = new LectorLineas("files/config.txt");
		String config = configReader.leerLinea(1);
		Reader = new LectorLineas("files/lang/"+ config + "Modified.txt");
		switch (config){
			case "esp/":
				return Reader.leerTramo(43, 46);
			case "eng/":
				return Reader.leerTramo(44,48);
			default:
				throw new IllegalArgumentException("Configuración errónea");
		}
	}

	@Override
	public String toString() {
		String str = super.toString();
		return str.replace("X","Valkyrie");
	}

}

