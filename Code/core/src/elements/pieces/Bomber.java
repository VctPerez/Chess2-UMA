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
import game.chess.GameScreen;
import interaccionFichero.LectorLineas;
import utils.Image;
import utils.Render;
import utils.Resources;
import utils.Text;

public class Bomber extends Piece{
	boolean canExplode;

	public Bomber(Boolean color, int x, int y,Board board) {
		super(color, Render.app.getManager().get(Resources.BOMBER_PATH, Texture.class), x ,y,board);
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
		
		canExplode = false;
		addMovement(x, y + direction, board, movements);
		addMovement(x, y - direction, board, movements);
		addMovement(x + direction, y, board, movements);
		addMovement(x - direction, y, board, movements);

		if(canExplode) {
			System.out.println("Puede  explotar");
			addMovement(x + direction, y + direction, board, movements);
			addMovement(x + direction, y - direction, board, movements);
			addMovement(x - direction, y + direction, board, movements);
			addMovement(x - direction, y - direction, board, movements);		
		}else {
			addNonAtackMovement(x + direction, y + direction, movements);
			addNonAtackMovement(x + direction, y - direction, movements);
			addNonAtackMovement(x - direction, y + direction, movements);
			addNonAtackMovement(x - direction, y - direction, movements);
		}
		
		if(board.getTile(x, y+direction) != null && board.getTile(x, y+direction).getPiece()== null) {
			addNonAtackMovement(x, y + 2*direction, movements);			
		}
		if(board.getTile(x, y-direction) != null && board.getTile(x, y-direction).getPiece()== null) {
			addNonAtackMovement(x, y - 2*direction, movements);			
		}
		if(board.getTile(x + direction, y) != null && board.getTile(x + direction, y).getPiece()== null) {
			addNonAtackMovement(x + 2*direction, y, movements);			
		}
		if(board.getTile(x - direction, y) != null && board.getTile(x - direction, y).getPiece()== null) {
			addNonAtackMovement(x - 2*direction, y, movements);			
		}
			
		return movements;
	}

	public void addMovement(float x, float y, Board board, ArrayList<Vector2> movements) {
		if (board.getTile(x, y) != null && !sameColor(board.getTile(x, y).getPiece())) {
			if(board.getTile(x, y).getPiece()!=null) {
				canExplode = true;
			}
			movements.add(new Vector2(x, y));
		}
	}
	
	private void addNonAtackMovement(float x, float y, ArrayList<Vector2> movements) {
		if (board.getTile(x, y) != null && board.getTile(x, y).getPiece()==null) {
			movements.add(new Vector2(x, y));
		}
	}
	
	public void explode() {
		Action explosionSfx = new Action() {
			Sound sound = Render.app.getManager().get(Resources.EXPLOSION_SOUND, Sound.class);

			public boolean act(float delta) {
				sound.play(0.5f);
				return true;
			}
		};
		addAction(Actions.after(explosionSfx));
		explosion();
	}
	
	private void explosion() {
		for(int i=x-1;i<=x+1;i++) {
			for(int j=y-1;j<=y+1;j++) {
				if(board.getTile(i, j)!=null && board.getTile(i, j).piece!=null) {
					board.getTile(i, j).sendPieceToGraveyard();
				}
			}
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
				return Reader.leerTramo(14, 17);
			case "eng/":
				return Reader.leerTramo(13,18);
			default:
				throw new IllegalArgumentException("Configuración errónea");
		}
	}

	@Override
	public String toString() {
		String str = super.toString();
		return str.replace("X","Bomber");
	}
	
}