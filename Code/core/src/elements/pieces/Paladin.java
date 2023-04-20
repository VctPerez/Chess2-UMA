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

public class Paladin extends Piece{
	private Boolean validDirection;
	public Boolean canSwingUp, canSwingDown, canSwingRight, canSwingLeft;
	int direction;
	
	public Paladin(Boolean color, int x, int y, Board board) {
		super(color, Render.app.getManager().get(Resources.PALADIN_PATH, Texture.class), x, y, board);
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
		
		while(validDirection && k<3) {
			mov = new Vector2(x + i*k, y + j*k);
			if(checkBoard(board, mov.x, mov.y)) {
				movements.add(mov);
			}
			k++;
		}
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
		
		if(color) {
			direction = 1;
		}else {
			direction = -1;
		}
		
		checkDirection(x, y, 1, 1, movements);
		checkDirection(x, y, -1, -1, movements);
		checkDirection(x, y, 1, -1, movements);
		checkDirection(x, y, -1, 1, movements);
		
		canSwingUp = false;
		canSwingDown = false;
		canSwingRight = false;
		canSwingLeft = false;
		
		addMovement(x, y + direction, movements);
		addMovement(x, y - direction, movements);
		addMovement(x + direction, y, movements);
		addMovement(x - direction, y, movements);

		
		
		
		return movements;
	}
	
	private void addSwings(float x, float y, ArrayList<Vector2> movements) {
		if(x == this.x && y == this.y+direction) {
			canSwingUp = true;
			addMovement(x - 1, y + direction, movements);
			addMovement(x + 1, y + direction, movements);
		}
		if(x == this.x && y == this.y-direction) {
			canSwingDown = true;
			addMovement(x - 1, y - direction, movements);
			addMovement(x + 1, y - direction, movements);
		}
		if(x == this.x + direction && y == this.y) {
			canSwingRight = true;
			addMovement(x + direction, y - 1, movements);
			addMovement(x + direction, y + 1, movements);
		}
		if(x == this.x - direction && y == this.y) {
			canSwingLeft = true;
			addMovement(x + direction, y - 1, movements);
			addMovement(x + direction, y + 1, movements);
		}
	}

	public void addMovement(float x, float y, ArrayList<Vector2> movements) {
		if (board.getTile(x, y) != null && !sameColor(board.getTile(x, y).getPiece())) {
			if(board.getTile(x, y).getPiece()!=null) {
				System.out.println("PUEDE CORTAR");
				addSwings(x, y, movements);
			}
			movements.add(new Vector2(x, y));
		}
	}
	
	private void addNonAtackMovement(float x, float y, ArrayList<Vector2> movements) {
		if (board.getTile(x, y) != null && board.getTile(x, y).getPiece()==null) {
			movements.add(new Vector2(x, y));
		}
	}
	
	public String getInfo() {
		 LectorLineas Reader, configReader;
		 configReader = new LectorLineas("files/config.txt");
		String config = configReader.leerLinea(1);
		Reader = new LectorLineas("files/lang/"+ config + "Clasicas.txt");
		switch (config){
			case "esp/":
				return Reader.leerTramo(7, 10);
			case "eng/":
				return Reader.leerTramo(6,8);
			default:
				throw new IllegalArgumentException("Configuración errónea");
		}
	}

	@Override
	public String toString() {
		String str = super.toString();
		return str.replace("X","Bishop");
	}
}
