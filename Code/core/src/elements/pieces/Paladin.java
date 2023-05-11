package elements.pieces;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import elements.Board;
import elements.Piece;
import elements.Tile;
import game.chess.GameScreen;
import interaccionFichero.LineReader;
import utils.AnimationActor;
import utils.Render;
import utils.Resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Paladin extends Piece{
	private Boolean validDirection;
	public Boolean canSwingUp = false, canSwingDown=false, canSwingRight=false, canSwingLeft=false;
	
	public Paladin(Boolean color, int x, int y, Board board) {
		super(color, Resources.PALADIN_PATH, x, y, board);
	}
	
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}
	
	@Override
	protected void simulateMovement(Tile currentTile, Vector2 move, ArrayList<Vector2> removeMovements) {
		Boolean hasSwang;
		Map<Vector2, Piece> simulatedSwing = new HashMap<>();
		Tile nextTile = board.getTile((int) move.x, (int) move.y);
		Piece nextTilePiece = null;
		if (nextTile.getPiece() != null) {
			nextTilePiece = nextTile.getPiece();
		}
		if(nextTilePiece instanceof Colosus) {
			System.out.println("COLOSO");
			removeMovements.add(move);
			hasSwang = false;
		}else {
			if(!simulateCheckPaladin(move.x, move.y, simulatedSwing)) {
				currentTile.simulateMoveTo(nextTile);
				hasSwang = false;
			}else {
				hasSwang = true;
			}
			
			if (color && !isKingSafe(Render.GameScreen.blackPieces, Render.GameScreen.whiteKing)) {
				removeMovements.add(move);
			} else if (!color && !isKingSafe(Render.GameScreen.whitePieces, Render.GameScreen.blackKing)) {
				removeMovements.add(move);
			}
			
			if(!hasSwang) {			
				undoLastMovement(currentTile, nextTile, nextTilePiece);
			}else {
				reviveSwing(simulatedSwing);
			}
		}
		
	}
	
	@Override
	public Boolean checkPaladin(float next_x, float next_y) {
		Boolean swing = false;
			if(next_x == x && next_y == y + 1 && canSwingUp) {
				paladinSwing(1, 0, next_x, next_y);
				swing=true;
			}else if(next_x == x && next_y == y - 1 && canSwingDown) {
				paladinSwing(1, 0, next_x, next_y);
				swing=true;
			}else if(next_x == x + 1 && next_y == y && canSwingRight) {
				paladinSwing(0, 1, next_x, next_y);
				swing=true;
			}else if(next_x == x - 1 && next_y == y && canSwingLeft) {
				paladinSwing(0, 1, next_x, next_y);
				swing=true;
			}
		return swing;
	}
	
	private void paladinSwing(float i, float j, float next_x, float next_y) {
		swingTile(-i, -j, next_x, next_y);
		swingTile(0, 0, next_x, next_y);
		swingTile(+i, +j, next_x, next_y);
		swingSound(next_x, next_y);
		
	}
	
	private void swingTile(float i, float j, float next_x, float next_y){
		Tile tile = board.getTile(next_x+i,next_y+j);
		if(tile!=null && tile.getPiece()!=null && !(tile.getPiece() instanceof Colosus)) {
			tile.sendPieceToGraveyard();
		}
	}
	
	public Boolean simulateCheckPaladin(float next_x, float next_y, Map<Vector2, Piece> simulatedSwing) {
		Boolean swing = false;
			if(next_x == x && next_y == y + 1 && canSwingUp) {
				simulateSwing(1, 0, next_x, next_y, simulatedSwing);
				swing=true;
			}else if(next_x == x && next_y == y - 1 && canSwingDown) {
				simulateSwing(1, 0, next_x, next_y, simulatedSwing);
				swing=true;
			}else if(next_x == x + 1 && next_y == y && canSwingRight) {
				simulateSwing(0, 1, next_x, next_y, simulatedSwing);
				swing=true;
			}else if(next_x == x - 1 && next_y == y && canSwingLeft) {
				simulateSwing(0, 1, next_x, next_y, simulatedSwing);
				swing=true;
			}
		return swing;
	}

	private void simulateSwing(float i, float j, float next_x, float next_y, Map<Vector2, Piece> simulatedSwing) {
		simulateSwingTile(-i, -j, next_x, next_y, simulatedSwing);
		simulateSwingTile(0, 0, next_x, next_y, simulatedSwing);
		simulateSwingTile(+i, +j, next_x, next_y, simulatedSwing);

	}
	private void simulateSwingTile(float i, float j, float next_x, float next_y,Map<Vector2, Piece> simulatedSwing){
		Tile tile = board.getTile(next_x+i,next_y+j);
		if(tile!=null) {
			Piece piece = tile.getPiece();
			if(piece!=null && !(piece instanceof Colosus)) {
				simulatedSwing.put(new Vector2(next_x+i,next_y+j), piece);
				tile.setPiece(null);
				if(piece.color()) {
					GameScreen.whitePieces.remove(piece);
				}else {
					GameScreen.blackPieces.remove(piece);
				}
			}
		}
	}
	
	public void reviveSwing(Map<Vector2, Piece> simulatedSwing) {//implementar un mapa donde la clave sea la posicion o la pieza y el valor lo otro, asi no hay q chekear para revivir, solo revivir
			for(Vector2 tile : simulatedSwing.keySet()) {
				board.getTile(tile.x, tile.y).setPiece(simulatedSwing.get(tile));
				if(simulatedSwing.get(tile).color()) {
					GameScreen.whitePieces.add(simulatedSwing.get(tile));
				}else {
					GameScreen.blackPieces.add(simulatedSwing.get(tile));
				}
			}
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
		
		canSwingUp = false;
		canSwingDown = false;
		canSwingRight = false;
		canSwingLeft = false;
		
		addMovement(x, y + 1, movements);
		addMovement(x, y - 1, movements);
		addMovement(x + 1, y, movements);
		addMovement(x - 1, y, movements);
		
		checkDirection(x, y, 1, 1, movements);
		checkDirection(x, y, -1, -1, movements);
		checkDirection(x, y, 1, -1, movements);
		checkDirection(x, y, -1, 1, movements);
		

		return movements;
	}
	
	private void addSwings(float x, float y) {
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
				addSwings(x, y);
			}
			movements.add(new Vector2(x, y));
		}
	}

	public void swingSound(final float next_x, final float next_y) {
		Action swingSfx = new Action() {
			Sound sound = Render.app.getManager().get(Resources.PALADINSWING_SOUND, Sound.class);
			
			public boolean act(float delta) {
				Tile tile = board.getTile(next_x, next_y);
				AnimationActor swing= new AnimationActor(0.13f, Resources.SWORD_SWING_PATH, 4);
				
				
				if(Render.hosting) {
					if(next_x == x && next_y == y + 1 && canSwingUp) {
						swing.setPosition(tile.getX()-tile.getWidth(), tile.getY());
						swing.rotateBy(0);
					}else if(next_x == x && next_y == y - 1 && canSwingDown) {
						swing.setPosition(tile.getX()+2*tile.getWidth(), tile.getY()+tile.getWidth());
						swing.rotateBy(180);
					}else if(next_x == x + 1 && next_y == y && canSwingRight) {
						swing.setPosition(tile.getX(), tile.getY()+2*tile.getWidth());
						swing.rotateBy(270);
					}else if(next_x == x - 1 && next_y == y && canSwingLeft) {
						swing.setPosition(tile.getX()+tile.getWidth(), tile.getY()-tile.getWidth());
						swing.rotateBy(90);
					}
				}else {
					if(next_x == x && next_y == y + 1 && canSwingUp) {
						swing.setPosition(tile.getX()+2*tile.getWidth(), tile.getY()+tile.getWidth());
						swing.rotateBy(180);
					}else if(next_x == x && next_y == y - 1 && canSwingDown) {
						swing.setPosition(tile.getX()-tile.getWidth(), tile.getY());
						swing.rotateBy(0);
					}else if(next_x == x + 1 && next_y == y && canSwingRight) {
						swing.setPosition(tile.getX()+tile.getWidth(), tile.getY()-tile.getWidth());
						swing.rotateBy(90);
					}else if(next_x == x - 1 && next_y == y && canSwingLeft) {
						swing.setPosition(tile.getX(), tile.getY()+2*tile.getWidth());
						swing.rotateBy(270);
					}
				}
				
				swing.setSize(3*tile.getWidth(), tile.getWidth());
				Render.GameScreen.stage.addActor(swing);
				
				sound.play(0.5f);
				Render.GameScreen.resetMate();
				Render.GameScreen.mateControl();
				return true;
			}
		};
		addAction(Actions.after(swingSfx));
	}

	
	public String getInfo() {
		 LineReader Reader, configReader;
		 configReader = new LineReader("files/config.txt");
		String config = configReader.readLine(1);
		Reader = new LineReader("files/lang/"+ config + "Modified.txt");
		switch (config){
			case "esp/":
				return Reader.readSection(68, 73);
			case "eng/":
				return Reader.readSection(70,75);
			default:
				throw new IllegalArgumentException("Configuración errónea");
		}
	}

	@Override
	public String toString() {
		String str = super.toString();
		return str.replace("X","Paladin");
	}
}