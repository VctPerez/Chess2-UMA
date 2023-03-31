package elements;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import game.chess.GameScreen;
import utils.Image;

public abstract class Piece extends Actor {
	protected Image sprite;
	protected Boolean color;
	public Boolean hasBeenMoved;
	protected Boolean selected;
	public Boolean alive;
	protected int x;
	protected int y;

	public Piece(Boolean color, Texture texture, int x, int y) {
		this.sprite = new Image(texture);
		this.hasBeenMoved=false;
		this.color = color;
		this.alive = true;
		this.x = x;
		this.y = y;
		
		if(color) {
			setColor(Color.WHITE);
		}else {
			setColor(0.25f, 0.25f, 0.25f, 1f);
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		sprite.setPosition(getX(), getY());
		sprite.setSize(getWidth(), getHeight());
		sprite.setScale(getScaleX());
		sprite.sprt.setColor(getColor());
		sprite.draw(batch);
	}
	
	/**
	 * 
	 * @return true si la pieza es blanca false si es negra
	 */
	public Boolean color() {
		return color;
	}

	/**
	 * Actualiza el booleano que determina si la pieza ha sido movida o no a true;
	 */
	public void hasBeenMoved() {
		hasBeenMoved=true;
	}
	
	/**
	 * Actualiza la posiicón de la pieza
	 * @param x
	 * @param y
	 */
	protected void updateXY(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setSprite(String path) {
		this.sprite = new Image(path);
	}
	
	/**
	 * 
	 * @return true si la ìeza está viva false si no lo está
	 */
	public boolean alive() {
		return alive;
	}
	
	/**
	 * 
	 * @param piece
	 * @return true si la pieza pasada como parámetro es del mismo color, false si no lo es
	 */
	public Boolean sameColor(Piece piece) {
		return color==piece.color();
	}

	
	public void dispose() {
		sprite.dispose();
	}
	/**
	 * 
	 * @return todos los posibles movimientos de una pieza, ya sean válidos o no
	 */
	public ArrayList<Vector2> posibleMovements() {
		return null;
	}
	
	@SuppressWarnings("unused")
	private void checkDirection(float x, float y, int i, int j, ArrayList<Vector2> movements) {
		
	}
	
	@SuppressWarnings("unused")
	private Boolean checkBoard(Board board, float x, float y) {
		return null;
	}

	/**
	 * Filtra todos los posibles movimientos de una pieza y solo permite aquellos que sean legales. Se hace borrando los movimientos ilegales de la lista de posibles movimientos 
	 * @return un arry de posibles movimientos legales (solo aquellos que dejen al rey a salvo)
	 */
	public ArrayList<Vector2> getValidMovements() {
		ArrayList<Vector2> validMovements = posibleMovements();
		ArrayList<Vector2> removeMovements = new ArrayList<>();
		Tile currentTile = GameScreen.board.getTile(x, y);
		
		for(Vector2 move: validMovements) {
			//System.out.println("SIMULANDO MOVIMIENTO A [ "+move.x+", "+move.y+"]");
			
			simulateMovement(currentTile, move, removeMovements);		
		}
		validMovements.removeAll(removeMovements);
		return validMovements;
	}
	
	/**
	 * Simula el movimiento de la casilla actual a la indicada por el move y si este movimiento no dejara al rey de su color a salvo se añade a la lista de movimientos a borrar
	 * @param currentTile
	 * @param move
	 * @param removeMovements
	 */
	protected void simulateMovement(Tile currentTile, Vector2 move, ArrayList<Vector2> removeMovements) {
		Tile nextTile = GameScreen.board.getTile((int)move.x,(int) move.y);
		Piece nextTilePiece = null;
		if(nextTile.getPiece()!=null) {
			nextTilePiece = nextTile.getPiece();
		}
		currentTile.moveTo(nextTile);
		
		//check king
		if(color && !isKingSafe(GameScreen.blackPieces, GameScreen.whiteKing)) {
			removeMovements.add(move);
		}else if(!color && !isKingSafe(GameScreen.whitePieces, GameScreen.blackKing)) {
			removeMovements.add(move);
		}
		
		undoLastMovement(currentTile,  nextTile, nextTilePiece);
	}
	/**
	 * 
	 * @param pieces
	 * @param kingPos
	 * @return true su el rey está seguro, false si no lo está
	 */
	protected boolean isKingSafe(ArrayList<Piece> pieces, Vector2 kingPos) {
		boolean isSafe = true;
		for(Piece piece: pieces) {
			if(piece.posibleMovements().contains(kingPos)) {
				
				isSafe=false;
			}
		}
		return isSafe;
	}

	/**
	 * Deshace el ultimo movimiento que se haya simulado, devolviendo la pieza a su casilla original y reviviendo si es que se había comido alguna pieza
	 * @param lastTile
	 * @param nextTile
	 * @param nextTilePiece
	 */
	protected void undoLastMovement(Tile lastTile, Tile nextTile, Piece nextTilePiece) {
		
		nextTile.moveTo(lastTile);
		
		if(nextTilePiece!=null && !nextTilePiece.alive()) {
			if(nextTilePiece.color()) {
				nextTile.setPiece(GameScreen.graveyardWhite.reviveLastPiece());
			}else if(!nextTilePiece.color()) {
				nextTile.setPiece(GameScreen.graveyardBlack.reviveLastPiece());
			}
		}		
	}
}
