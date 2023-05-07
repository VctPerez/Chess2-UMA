package elements.pieces;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import elements.Board;
import elements.Piece;
import interaccionFichero.LineReader;
import utils.Render;
import utils.Resources;

public class Knight extends Piece {

	public Knight(Boolean color, int x, int y,Board board) {
		super(color, Resources.KNIGHT_PATH, x, y,board);
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
		addMovement(x + 2, y + 1, board, movements);
		addMovement(x + 1, y + 2, board, movements);
		addMovement(x + 2, y - 1, board, movements);
		addMovement(x + 1, y - 2, board, movements);
		addMovement(x - 1, y - 2, board, movements);
		addMovement(x - 2, y + 1, board, movements);
		addMovement(x - 2, y - 1, board, movements);
		addMovement(x - 1, y + 2, board, movements);
		return movements;
	}

	public void addMovement(float x, float y, Board board, ArrayList<Vector2> movements) {
		if (board.getTile(x, y) != null && !sameColor(board.getTile(x, y).getPiece())) {
			movements.add(new Vector2(x, y));
		}
	}
	
	public String getInfo() {
		 LineReader Reader, configReader;
		 configReader = new LineReader("files/config.txt");
		String config = configReader.readLine(1);
		Reader = new LineReader("files/lang/"+ config + "Clasicas.txt");
		switch (config){
			case "esp/":
				return Reader.readSection(12, 21);
			case "eng/":
				return Reader.readSection(10,17);
			default:
				throw new IllegalArgumentException("Configuración errónea");
		}
	}

	@Override
	public String toString() {
		String str = super.toString();
		return str.replace("X","Knight");
	}

}
