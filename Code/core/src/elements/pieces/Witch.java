package elements.pieces;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import elements.Board;
import elements.Piece;
import elements.Tile;
import interaccionFichero.LineReader;
import utils.AnimationActor;
import utils.Render;
import utils.Resources;
import utils.Settings;

import java.util.ArrayList;

public class Witch extends Piece{
	ArrayList<Vector2> attacks;

	public Witch(Boolean color, int x, int y,Board board) {
		super(color, Resources.WITCH_PATH, x ,y,board);
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
	public Boolean checkWitchAttack(float next_x, float next_y) {
		Boolean attack = false;
		if(attacks.contains(new Vector2(next_x, next_y))) {
			System.out.println(this.toString() + "  ATACA");
			attack = true;
			attack(next_x, next_y);
		}else {
			System.out.println(this.toString() + "  NO ATACA?");
		}
		return attack;
	}
	
	public void attack(final float next_x, final float next_y) {
		Action fireBall = new Action() {
			public boolean act(float delta) {
				Tile tile = board.getTile(next_x, next_y);
				AnimationActor attack= new AnimationActor(0.13f, Resources.FIREBALL_PATH, 5);
				attack.setOrigin(attack.getWidth()/2, attack.getHeight()/2);
		
				float rotation = (float) Math.atan((tile.getY() -  getY())/(tile.getX() - getX()));
				rotation = (float)(180*rotation/Math.PI);
				
				if(tile.getX()>=getX() ) {
					rotation+=180;
					if(tile.getY() <= getY()) {
						attack.setPosition(getX()+5*tile.getWidth()/6, getY());
						attack.addAction(Actions.moveTo(tile.getX()+5*tile.getWidth()/6, tile.getY()+3*tile.getWidth()/4, 0.6f));
					}else {
						attack.setPosition(getX()+tile.getWidth()/6, getY()+3*tile.getWidth()/4);
						attack.addAction(Actions.moveTo(tile.getX()+tile.getWidth()/6, tile.getY()+3*tile.getWidth()/4, 0.6f));
					}
				}else {
					if(tile.getY() <= getY()) {
						attack.setPosition(getX()+5*tile.getWidth()/6, getY());
						attack.addAction(Actions.moveTo(tile.getX()+5*tile.getWidth()/6, tile.getY()+tile.getWidth()/4, 0.6f));
					}else {
						attack.setPosition(getX()+tile.getWidth()/6, getY()+3*tile.getWidth()/4);
						attack.addAction(Actions.moveTo(tile.getX()+tile.getWidth()/6, tile.getY()+tile.getWidth()/4, 0.6f));						
					}
				}
				
				attack.rotateBy(rotation);
				attack.setSize(8*21, 8*7);
				Render.GameScreen.stage.addActor(attack);
				return true;
			}
		};
		
		Action explosionSfx = new Action() {
			Sound sound = Render.app.getManager().get(Resources.EXPLOSION_SOUND, Sound.class);

			public boolean act(float delta) {
				Tile tile = board.getTile(next_x, next_y);
				AnimationActor explosion= new AnimationActor(0.16f, Resources.EXPLOSION_PATH, 5);
				explosion.setPosition(tile.getX(), tile.getY());
				explosion.setSize(board.getTile(x, y).getWidth(), board.getTile(x, y).getWidth());
				
				Render.GameScreen.stage.addActor(explosion);
				sound.play(Settings.sfxVolume/2f);
				return true;
			}
		};
		
		Action attackTile = new Action() {
			public boolean act(float delta) {
				board.getTile(next_x, next_y).sendPieceToGraveyard();
				Render.GameScreen.resetMate();
				Render.GameScreen.mateControl();
				return true;
			}
		};
		addAction(Actions.after(fireBall));
		addAction(Actions.sequence(Actions.delay(1.5f), explosionSfx));
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
		if (board.getTile(x, y) != null && board.getTile(x, y).getPiece() != null &&  !sameColor(board.getTile(x, y).getPiece()) && !(board.getTile(x, y).getPiece() instanceof Colosus)) {
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
		 LineReader Reader, configReader;
		 configReader = new LineReader("files/config.txt");
		String config = configReader.readLine(1);
		Reader = new LineReader("files/lang/"+ config + "Modified.txt");
		switch (config){
			case "esp/":
				return Reader.readSection(75, 79);
			case "eng/":
				return Reader.readSection(77,81);
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
