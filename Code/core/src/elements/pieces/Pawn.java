package elements.pieces;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import elements.Board;
import elements.Piece;
import elements.Tile;
import game.chess.GameScreen;
import utils.Image;
import utils.Render;
import utils.Resources;

public class Pawn extends Piece{
	
	public Boolean enPassantable = false;

	public Pawn(Boolean color) {
		super(color, Render.app.getManager().get(Resources.PAWN_PATH, Texture.class));
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}

	/**
	 * Comprueba que las casillas a las que el peon pueda moverse estan dentro del tablero y si tienen alguna pieza dentro
	 * @param board
	 * @param x
	 * @param y
	 * @return
	 */
	private Boolean checkBoard(Board board, int i, float x, float y) {
		Boolean res = false;
		if(i!=0 && board.getTile(x, y)!=null && board.getTile(x, y).getPiece()!=null && !sameColor(board.getTile(x, y).getPiece())) {
			res = true;
		}else if(i==0 && board.getTile(x, y)!=null && board.getTile(x, y).getPiece()==null) {
			res = true;
		}
		return res;
	}
	
	/**
	 * Devuelve un ArrayList de Vector2 con las posibles casillas a las que puede moverse el peon con posición (x,y)
	 * @param x
	 * @param y
	 * @return
	 */
	@Override
	public ArrayList<Vector2> getMovement(float x, float y) {//para implementar esta función en cada pieza habrá que hacerlo de forma diferente
		ArrayList<Vector2> movements = new ArrayList<>();
		Vector2 mov;
		int direction = 1;
		if(!color) {
			direction = -1;
		}
		
		
		for(int i = -1; i<=1; i++) {
			mov = new Vector2(x + i, y + direction);
			if(checkBoard(GameScreen.board, i, mov.x, mov.y)) {
				movements.add(mov);
				enPassantable = false;
			}
		}
		if(!hasBeenMoved) {
			mov = new Vector2(x , y + 2*direction);
			if(checkBoard(GameScreen.board, 0, mov.x, mov.y) && GameScreen.board.getTile(x, y+direction).getPiece()==null) {
				movements.add(mov);
			}
		}

		if (y == 5 - (color?0:1)){ //Si está en la fila donde puede hacer en passant(5 para blancas, 4 para negras)
			Piece aux;
			if (GameScreen.board.getTile(x-1,y) != null){ //Para que no de error
				aux = GameScreen.board.getTile(x-1,y).getPiece(); //Mira a la izquierda
				if (aux instanceof Pawn && ((Pawn) aux).enPassantable){ //Si la pieza puede tomarse al paso(habrá que cambiar el if si se incluyen más)
					mov = new Vector2(x-1,y+direction);
					movements.add(mov);
				}
			}
			if (GameScreen.board.getTile(x+1,y) != null){
				aux = GameScreen.board.getTile(x+1,y).getPiece(); //Mira a la derecha
				if (aux instanceof Pawn && ((Pawn) aux).enPassantable){
					mov = new Vector2(x+1,y+direction);
					movements.add(mov);
				}
			}
		}

		return movements;
	}

}
