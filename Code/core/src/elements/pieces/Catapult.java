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
import elements.Tile;
import interaccionFichero.LectorLineas;
import utils.AnimationActor;
import utils.Render;
import utils.Resources;

public class Catapult extends Piece{
	ArrayList<Vector2> attacks;

	public Catapult(Boolean color, int x, int y,Board board) {
		super(color, Render.app.getManager().get(Resources.CATAPULT_PATH, Texture.class), x ,y,board);
		attacks = new ArrayList<>();
		}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}
	
	@Override
	protected void simulateMovement(Tile currentTile, Vector2 move, ArrayList<Vector2> removeMovements) {
		Boolean hasAttacked = false;
		Tile nextTile = board.getTile((int) move.x, (int) move.y);
		Piece nextTilePiece = null;
		if (nextTile.getPiece() != null) {
			nextTilePiece = nextTile.getPiece();
		}
		if(nextTilePiece instanceof Colosus) {
			System.out.println("COLOSO");
			removeMovements.add(move);
		}else {
			if(attacks.contains(move)) {
				simulateAttack(nextTile);
				hasAttacked = true;
			}else {
				currentTile.simulateMoveTo(nextTile);
				hasAttacked = false;
			}
			
			
			if (color && !isKingSafe(Render.GameScreen.blackPieces, Render.GameScreen.whiteKing)) {
				removeMovements.add(move);
			} else if (!color && !isKingSafe(Render.GameScreen.whitePieces, Render.GameScreen.blackKing)) {
				removeMovements.add(move);
			}
			
			if(!hasAttacked) {				
				undoLastMovement(currentTile, nextTile, nextTilePiece);
			}else {
				undoAttack(currentTile, nextTile, nextTilePiece);
			}
		}
	}
	
	@Override
	public Boolean checkCatapultAttack(float next_x, float next_y) {
		Boolean attack = false;
		if(attacks.contains(new Vector2(next_x, next_y))) {
			attack = true;
			attack(next_x, next_y);
		}
		return attack;
	}
	
	public void attack(final float next_x, final float next_y) {
		Action attackSfx = new Action() {
			Sound sound = Render.app.getManager().get(Resources.EXPLOSION_SOUND, Sound.class);

			public boolean act(float delta) {
				AnimationActor attack= new AnimationActor(0.13f, "explosion.png", 5);
				attack.setPosition(getX()-board.getTile(x, y).getWidth(), getY()-board.getTile(x, y).getWidth());
				attack.setSize(3*board.getTile(x, y).getWidth(), 3*board.getTile(x, y).getWidth());
				
				Render.GameScreen.stage.addActor(attack);
				sound.play(0.5f);
				return true;
			}
		};Action attackTile = new Action() {
			public boolean act(float delta) {
				board.getTile(next_x, next_y).sendPieceToGraveyard();
				return true;
			}
		};
		addAction(Actions.after(attackSfx));
		addAction(Actions.after(attackTile));
	}
	
	public void simulateAttack(Tile nextTile) {
		nextTile.simulateSendPieceToGraveyard();
	}
	
	public void undoAttack(Tile lastTile, Tile nextTile, Piece nextTilePiece) {//implementar un mapa donde la clave sea la posicion o la pieza y el valor lo otro, asi no hay q chekear para revivir, solo revivir
		if (nextTilePiece != null && !nextTilePiece.alive()) {
			if (nextTilePiece.color()) {
				nextTile.setPiece(Render.GameScreen.graveyardWhite.reviveLastPiece());
			} else if (!nextTilePiece.color()) {
				nextTile.setPiece(Render.GameScreen.graveyardBlack.reviveLastPiece());
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
		attacks.clear();
		
		for(int i = -1; i<=1; i++) {
			for(int j = -1; j<=1; j++) {
				if(i!=0 || j!=0) {
					addNonAtackMovement(x + i, y + j, movements);
				}
			}
		}
		
		addAttack(x, y+4, attacks);
		addAttack(x+1, y+3, attacks);
		addAttack(x+2, y+2, attacks);
		addAttack(x+3, y+1, attacks);
		addAttack(x+4, y, attacks);
		
		addAttack(x+3, y-1, attacks);
		addAttack(x+2, y-2, attacks);
		addAttack(x+1, y-3, attacks);
		addAttack(x, y-4, attacks);
		
		addAttack(x-1, y-3, attacks);
		addAttack(x-2, y-2, attacks);
		addAttack(x-3, y-1, attacks);
		addAttack(x-4, y, attacks);
		
		addAttack(x-3, y+1, attacks);
		addAttack(x-2, y+2, attacks);
		addAttack(x-1, y+3, attacks);

		movements.addAll(attacks);
		return movements;
	}
	
	

	public void addAttack(float x, float y, ArrayList<Vector2> movements) {
		if (board.getTile(x, y) != null && board.getTile(x, y).getPiece() != null &&  !sameColor(board.getTile(x, y).getPiece())) {
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
		return str.replace("X","Catapult");
	}
}
