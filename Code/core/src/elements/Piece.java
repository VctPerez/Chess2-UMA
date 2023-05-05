package elements;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import elements.pieces.Colosus;
import utils.Image;
import utils.Render;
import utils.Resources;

import java.util.ArrayList;
import java.util.Objects;

public abstract class Piece extends Actor {
	protected Image sprite;
	protected Boolean color;
	public Boolean hasBeenMoved;
	protected Boolean selected;
	public Boolean alive;
	protected int x;
	protected int y;
	public Board board;
	public Boolean isPassantable = false;
	public Boolean backed;
	public int ate;
	public int kiCharge;
	
	public Piece(boolean color, int x, int y, Board board) {
		this.hasBeenMoved = false;
		this.color = color;
		this.alive = true;
		this.backed = false;
		this.ate=0;
		this.x = x;
		this.y = y;
		this.kiCharge=0;
		this.board = board;
	}

	public Piece(Boolean color, Texture texture, int x, int y, Board board) {
		this(color,x,y,board);
		this.sprite = new Image(texture);
		

		if (color) {
			setColor(Color.WHITE);
		} else {
			setColor(0.25f, 0.25f, 0.25f, 1f);
		}
		setPosition(board.getTile(x, y).getX(), board.getTile(x, y).getY());
	}
	
	public Piece(boolean color, String path, int x, int y, Board board){
		this(color,x,y,board);
		String blackPath = path.replaceFirst("White", "Black");
		this.sprite = new Image(Render.app.getManager().get(color?path:blackPath, Texture.class));
		setPosition(board.getTile(x, y).getX(), board.getTile(x, y).getY());
	}

	public Piece(Texture texture) {
		this.sprite = new Image(texture);
	}
	
	public Image getImage() {
		return sprite;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		this.toFront();
		if(board.boardAnimation) {
			setPosition(board.getTile(x, y).getX(), board.getTile(x, y).getY());
		}
		
		sprite.setPosition(getX(), getY());			
		
		sprite.setSize(getWidth(), getHeight());
		sprite.setScale(getScaleX());
		sprite.sprt.setColor(getColor());
		sprite.draw(batch, 0);
	}

	/**
	 * 
	 * @return true si la pieza es blanca false si es negra
	 */
	public Boolean color() {
		return color;
	}
	
	public void setColor(Boolean color) {
		this.color=color;
	}

	/**
	 * Actualiza el booleano que determina si la pieza ha sido movida o no a true;
	 */
	public void hasBeenMoved() {
		hasBeenMoved = true;
	}
	
	public Vector2 getPos() {
		return new Vector2(x, y);
	}

	/**
	 * Actualiza la posiicón de la pieza
	 * 
	 * @param x
	 * @param y
	 */
	protected void updateXY(int x, int y) {
		this.x = x;
		this.y = y;
		Tile tile = Render.GameScreen.board.getTile(x, y);

		Action moveSfx = new Action() {
			Sound sound = Render.app.getManager().get(Resources.PIECEMOVE_SOUND, Sound.class);

			public boolean act(float delta) {
				sound.play();
				return true;
			}
		};
		addAction(Actions.moveTo(tile.getX(), tile.getY(), 0.5f));// hacer que la animación sea más consistente
		addAction(Actions.after(moveSfx));
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
	 * @return true si la pieza pasada como parámetro es del mismo color, false si
	 *         no lo es
	 */
	public Boolean sameColor(Piece piece) {
		boolean same = false;
		if (piece != null) {
			same = color == piece.color();
		}
		return same;
	}
	public void dispose() {
		//Se borra memoria de la imagen y se pone su referencia a null
		sprite.dispose();
		sprite=null;
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
	
	public Boolean checkPaladin(float next_x, float next_y) {
		return false;
	}
	
	public Boolean checkBomber(float next_x, float next_y) {
		return false;
	}
	
	public Boolean checkWitchAttack(float next_x, float next_y) {
		return false;
	}

	/**
	 * Filtra todos los posibles movimientos de una pieza y solo permite aquellos
	 * que sean legales. Se hace borrando los movimientos ilegales de la lista de
	 * posibles movimientos
	 * 
	 * @return un arry de posibles movimientos legales (solo aquellos que dejen al
	 *         rey a salvo)
	 */
	public ArrayList<Vector2> getValidMovements() {
		ArrayList<Vector2> validMovements = posibleMovements();
		ArrayList<Vector2> removeMovements = new ArrayList<>();
		Tile currentTile = board.getTile(x, y);
			
		
		for (Vector2 move : validMovements) {
			// System.out.println("SIMULANDO MOVIMIENTO A [ "+move.x+", "+move.y+"]");

			simulateMovement(currentTile, move, removeMovements);
		}
		
		validMovements.removeAll(removeMovements);
		return validMovements;
	}

	/**
	 * Simula el movimiento de la casilla actual a la indicada por el move y si este
	 * movimiento no dejara al rey de su color a salvo se añade a la lista de
	 * movimientos a borrar
	 * 
	 * @param currentTile
	 * @param move
	 * @param removeMovements
	 */
	protected void simulateMovement(Tile currentTile, Vector2 move, ArrayList<Vector2> removeMovements) {
		Tile nextTile = board.getTile((int) move.x, (int) move.y);
		Piece nextTilePiece = null;
		if (nextTile.getPiece() != null) {
			nextTilePiece = nextTile.getPiece();
		}
		if(nextTilePiece instanceof Colosus) {
			removeMovements.add(move);
		}else {
			currentTile.simulateMoveTo(nextTile);

			// check king
			if (color && !isKingSafe(Render.GameScreen.blackPieces, Render.GameScreen.whiteKing)) {
				removeMovements.add(move);
			} else if (!color && !isKingSafe(Render.GameScreen.whitePieces, Render.GameScreen.blackKing)) {
				removeMovements.add(move);
			}
			

			undoLastMovement(currentTile, nextTile, nextTilePiece);
		}
		
	}

	/**
	 * 
	 * @param pieces
	 * @param kingPos
	 * @return true su el rey está seguro, false si no lo está
	 */
	protected boolean isKingSafe(ArrayList<Piece> pieces, Vector2 kingPos) {
		boolean isSafe = true;
		for (Piece piece : pieces) {
			if (piece.posibleMovements().contains(kingPos)) {

				isSafe = false;
			}
		}
		return isSafe;
	}

	/**
	 * Deshace el ultimo movimiento que se haya simulado, devolviendo la pieza a su
	 * casilla original y reviviendo si es que se había comido alguna pieza
	 * 
	 * @param lastTile
	 * @param nextTile
	 * @param nextTilePiece
	 */
	protected void undoLastMovement(Tile lastTile, Tile nextTile, Piece nextTilePiece) {

		nextTile.moveTo(lastTile);

		if (nextTilePiece != null && !nextTilePiece.alive()) {
			if (nextTilePiece.color()) {
				nextTile.setPiece(Render.GameScreen.graveyardWhite.reviveLastPiece());
			} else if (!nextTilePiece.color()) {
				nextTile.setPiece(Render.GameScreen.graveyardBlack.reviveLastPiece());
			}
		}
	}

	/**
	 * En las clases no abstractas devolverá su path según su color
	 * @return null porque esto es el Piece genérico
	 */
	public String getSpritePath(){
		return null;
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}

	public String getInfo() {
		return "";
	}

	/**
	 * Devuelve el nombre del tipo de la pieza y su posición si está viva en un String
	 * <p>Se debe reemplazar la x por el nombre de verdad en sus subclases</p>
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{X");
		if (alive) {
			sb.append(",(").append(x).append(",").append(y).append(")}");
		} else {
			sb.append(",Dead}");
		}
		return sb.toString();
	}

	/**
	 * Devuelve true si ambas están muertas o ambas están en la misma posición
	 */
	@Override
	public boolean equals(Object obj) {
		boolean res = false;
		if (obj instanceof Piece){
			Piece aux = (Piece) obj;
			if (alive && aux.alive){
				res = x == aux.x && y == aux.y;
			} else {
				res = !(alive || aux.alive);
			}
		}
		return res;
	}

	@Override
	public int hashCode() {
		return Objects.hash(alive,x,y);
	}
}
