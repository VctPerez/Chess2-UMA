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

public class Miner extends Piece {

	public Miner(Boolean color, int x, int y,Board board) {
		super(color, Render.app.getManager().get(Resources.MINER_PATH, Texture.class), x, y,board);
	}
	
	

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}

	/**
	 * A�ade a movements todos los movimientos posibles del minero
	 * 
	 * @param x
	 * @param y
	 * @return
	 */

	@Override
	public ArrayList<Vector2> posibleMovements() {
		ArrayList<Vector2> movements = new ArrayList<>();
		
		if (color && !hasBeenMoved) {
			for (int i=1; i<9; i++) {
				for (int j=5;j<7;j++) {
					addMovement(i, j, board, movements);
				}
			}
			
			
		}else if (!color && !hasBeenMoved)  {
			for (int i=1; i<9; i++) {
				for (int j=3;j<5;j++) {
					addMovement(i, j, board, movements);
				}
			}
			
			
		}else {
			
			addMovement(x + 2, y , board, movements);
			addMovement(x - 2, y , board, movements);
			addMovement(x , y + 2, board, movements);
			addMovement(x , y - 2, board, movements);
			addMovement(x + 2 , y - 2, board, movements);
			addMovement(x + 2, y + 2, board, movements);
			addMovement(x - 2, y - 2, board, movements);
			addMovement(x - 2, y + 2, board, movements);
			
			
		}
		
		
		
		return movements;
	}

	public void addMovement(float x, float y, Board board, ArrayList<Vector2> movements) {
		
		if (!hasBeenMoved && board.getTile(x, y) != null && !sameColor(board.getTile(x, y).getPiece()) && board.getTile(x, y).getPiece()==null ) {
			movements.add(new Vector2(x,y) );
		}else if (board.getTile(x, y) != null && !sameColor(board.getTile(x, y).getPiece()) && hasBeenMoved){
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
				return Reader.leerTramo(48, 53);
			case "eng/":
				return Reader.leerTramo(50,55);
			default:
				throw new IllegalArgumentException("Configuración errónea");
		}
	}

	@Override
	public String toString() {
		String str = super.toString();
		return str.replace("X","Miner");
	}

}
