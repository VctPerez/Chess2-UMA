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
import interaccionFichero.LectorLineas;
import utils.AnimationActor;
import utils.Render;
import utils.Resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Bomber extends Piece{
	boolean canExplode;

	public Bomber(Boolean color, int x, int y,Board board) {
		super(color, Resources.BOMBER_PATH, x ,y,board);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}
	
	@Override
	protected void simulateMovement(Tile currentTile, Vector2 move, ArrayList<Vector2> removeMovements) {
		Boolean hasExploded;
		Map<Vector2, Piece> simulatedExplosion = new HashMap<>();
		Tile nextTile = board.getTile((int) move.x, (int) move.y);
		Piece nextTilePiece = null;
		if (nextTile.getPiece() != null) {
			nextTilePiece = nextTile.getPiece();
		}
		if(nextTilePiece instanceof Colosus) {
			System.out.println("COLOSO");
			removeMovements.add(move);
			hasExploded = false;
		}else {
			if(checkBomber(move.x, move.y)) {
				simulateExplosion(simulatedExplosion);
				hasExploded = true;
			}else {
				currentTile.simulateMoveTo(nextTile);
				hasExploded = false;
			}
			
			if (color && !isKingSafe(Render.GameScreen.blackPieces, Render.GameScreen.whiteKing)) {
				removeMovements.add(move);
			} else if (!color && !isKingSafe(Render.GameScreen.whitePieces, Render.GameScreen.blackKing)) {
				removeMovements.add(move);
			}
			if(!hasExploded) {			
				undoLastMovement(currentTile, nextTile, nextTilePiece);
			}else {
				reviveExplosion(simulatedExplosion);
			}
		}
		
	}
	
	@Override
	public Boolean checkBomber(float next_x, float next_y) {
		Boolean explode = false;
		if(next_x == x && next_y == y + 1 && canExplode && board.getTile(next_x, next_y).getPiece()!=null) {
			explode=true;
		}else if(next_x == x && next_y == y - 1 && canExplode && board.getTile(next_x, next_y).getPiece()!=null) {
			explode=true;
		}else if(next_x == x + 1 && next_y == y && canExplode && board.getTile(next_x, next_y).getPiece()!=null) {
			explode=true;
		}else if(next_x == x - 1 && next_y == y && canExplode && board.getTile(next_x, next_y).getPiece()!=null) {
			explode=true;
		}
	return explode;
	}
	
	public void explode() {
		Action explosionSfx = new Action() {
			Sound sound = Render.app.getManager().get(Resources.EXPLOSION_SOUND, Sound.class);

			public boolean act(float delta) {
				AnimationActor explosion= new AnimationActor(0.13f, "explosion.png", 5);
				explosion.setPosition(getX()-board.getTile(x, y).getWidth(), getY()-board.getTile(x, y).getWidth());
				explosion.setSize(3*board.getTile(x, y).getWidth(), 3*board.getTile(x, y).getWidth());
				
				Render.GameScreen.stage.addActor(explosion);
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
				if(board.getTile(i, j)!=null && board.getTile(i, j).getPiece()!=null && !(board.getTile(i, j).getPiece() instanceof Colosus)) {
					board.getTile(i, j).sendPieceToGraveyard();
				}
			}
		}
	}
	
	public void simulateExplosion(Map<Vector2, Piece> simulatedSwing) {
		for(int i=x-1;i<=x+1;i++) {
			for(int j=y-1;j<=y+1;j++) {
				Tile tile = board.getTile(i,j);
				if(tile!=null && tile.getPiece()!=null && !(tile.getPiece() instanceof Colosus)) {
					simulatedSwing.put(new Vector2(i,j), tile.getPiece());
					if(tile.getPiece().color()) {
						GameScreen.whitePieces.remove(tile.getPiece());
					}else {
						GameScreen.blackPieces.remove(tile.getPiece());
					}
					tile.setPiece(null);
				}
			}
		}
	}
	
	
	public void reviveExplosion(Map<Vector2, Piece> simulatedExplosion) {//implementar un mapa donde la clave sea la posicion o la pieza y el valor lo otro, asi no hay q chekear para revivir, solo revivir
		for(Vector2 tile : simulatedExplosion.keySet()) {
			board.getTile(tile.x, tile.y).setPiece(simulatedExplosion.get(tile));
			if(simulatedExplosion.get(tile).color()) {
				GameScreen.whitePieces.add(simulatedExplosion.get(tile));
			}else {
				GameScreen.blackPieces.add(simulatedExplosion.get(tile));
			}
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