package elements.pieces;

import java.util.ArrayList;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import elements.Board;
import elements.Piece;
import interaccionFichero.LectorLineas;
import utils.Render;
import utils.Resources;

public class Paladin extends Piece{
	private Boolean validDirection;
	public Boolean canSwingUp, canSwingDown, canSwingRight, canSwingLeft;
	
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
		
		while(validDirection && k<4) {
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
		
		
		checkDirection(x, y, 1, 1, movements);
		checkDirection(x, y, -1, -1, movements);
		checkDirection(x, y, 1, -1, movements);
		checkDirection(x, y, -1, 1, movements);
		
		canSwingUp = false;
		canSwingDown = false;
		canSwingRight = false;
		canSwingLeft = false;
		
		addMovement(x, y + 1, movements);
		addMovement(x, y - 1, movements);
		addMovement(x + 1, y, movements);
		addMovement(x - 1, y, movements);

		return movements;
	}
	
	private void addSwings(float x, float y, ArrayList<Vector2> movements) {
		if(x == this.x && y == this.y+1) {
			canSwingUp = true;
		}
		if(x == this.x && y == this.y-1) {
			canSwingDown = true;
		}
		if(x == this.x + 1 && y == this.y) {
			canSwingRight = true;
		}
		if(x == this.x - 1 && y == this.y) {
			canSwingLeft = true;
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
	
	public void swingSound() {
		Action swingSfx = new Action() {
			Sound sound = Render.app.getManager().get(Resources.PALADINSWING_SOUND, Sound.class);

			public boolean act(float delta) {
				sound.play(0.5f);
				return true;
			}
		};
		addAction(Actions.after(swingSfx));
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