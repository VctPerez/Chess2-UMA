package elements.pieces;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import elements.Board;
import elements.Piece;
import elements.Tile;
import interaccionFichero.LineReader;
import utils.Render;
import utils.Resources;
import utils.Settings;

import java.util.ArrayList;

public class Valkyrie extends Piece {
	
	private Boolean validDirection;

	public Valkyrie(Boolean color, int x, int y,Board board) {
		super(color, Resources.VALKYRIE_PATH, x, y,board);
	}
	
	@Override
	protected void updateXY(int x, int y) {
		this.x = x;
		this.y = y;
		Tile tile = Render.GameScreen.board.getTile(x, y);
		
		Action stance = new Action() {
			public boolean act(float delta) {
				sprite.setImage(getSpritePath());
				return true;
			}
		};
		
		Action animation_1= new Action() {
			public boolean act(float delta) {
				sprite.setImage(getSpritePath().replace("1.","2."));
				return true;
			}
		};
		
		Action animation_2= new Action() {
			public boolean act(float delta) {
				sprite.setImage(getSpritePath().replace("1.","3."));
				return true;
			}
		};
		Action flySfx = new Action() {
			Sound sound = Render.app.getManager().get(Resources.VALKYRIEFLY_SOUND, Sound.class);
			public boolean act(float delta) {
				sound.play(Settings.sfxVolume*2);
				return true;
			}
		};
		
		Action moveSfx = new Action() {
			Sound sound = Render.app.getManager().get(Resources.PIECEMOVE_SOUND, Sound.class);
			public boolean act(float delta) {
				sound.play(Settings.sfxVolume*2);
				return true;
			}
		};
		SequenceAction sequence = new SequenceAction();
		
		sequence.addAction(animation_1);
		sequence.addAction(Actions.delay(0.2f));
		sequence.addAction(animation_2);
		sequence.addAction(Actions.delay(0.8f));
		sequence.addAction(animation_1);
		sequence.addAction(Actions.delay(0.2f));
		sequence.addAction(stance);
		
		addAction(flySfx);
		addAction(Actions.parallel(Actions.moveTo(tile.getX(), tile.getY(), 1.2f), sequence));
		addAction(Actions.after(moveSfx));
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
		 LineReader Reader, configReader;
		 configReader = new LineReader("files/config.txt");
		String config = configReader.readLine(1);
		Reader = new LineReader("files/lang/"+ config + "Modified.txt");
		switch (config){
			case "esp/":
				return Reader.readSection(43, 46);
			case "eng/":
				return Reader.readSection(44,48);
			default:
				throw new IllegalArgumentException("Configuración errónea");
		}
	}

	/**
	 * Devuelve el path de la valkiria
	 * @return path del sprite
	 */
	@Override
	public String getSpritePath(){
		return color?Resources.VALKYRIE_PATH:Resources.BLACK_VALKYRIE_PATH;
	}

	@Override
	public String toString() {
		String str = super.toString();
		return str.replace("X","Valkyrie");
	}

}

