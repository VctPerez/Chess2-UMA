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

public class Knight extends Piece {

	public Knight(Boolean color, int x, int y) {
		super(color, Render.app.getManager().get(Resources.KNIGHT_PATH, Texture.class), x, y);
	}
	
	public Knight() {
		super(Render.app.getManager().get(Resources.KNIGHT_PATH, Texture.class));
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
		Board board = GameScreen.board;
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
	@Override
	public Boolean sameColor(Piece piece) {
		boolean same = false;
		if (piece != null) {
			same = color == piece.color();
		}
		return same;
	}
	
	public String getInfo() {
		 LectorLineas Reader, configReader;
		 configReader = new LectorLineas("files/config.txt");
	     Reader = new LectorLineas("files/lang/"+ configReader.leerLinea(1) + "Clasicas.txt");
	     return Reader.leerTramo(12, 21);
	}

}
