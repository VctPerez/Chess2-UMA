package elements.pieces;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import elements.Board;
import elements.Piece;
import interaccionFichero.LineReader;
import utils.Resources;

import java.util.ArrayList;

public class Lancer extends Piece{
	
	public Boolean isPassantable = false;

	public Lancer(Boolean color, int x, int y,Board board) {
		super(color, Resources.LANCER_PATH, x ,y,board);
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
		if(i==0 && board.getTile(x, y)!=null && board.getTile(x, y).getPiece()!=null && !sameColor(board.getTile(x, y).getPiece())) {
			res = true;
		}else if(i!=0 && board.getTile(x, y)!=null && board.getTile(x, y).getPiece()==null) {
			res = true;
		}
		return res;
	}
	
	public void addMovement(float x, float y, Board board, ArrayList<Vector2> movements) {
		if (board.getTile(x, y) != null && !sameColor(board.getTile(x, y).getPiece())) {
			movements.add(new Vector2(x, y));
		}
	}
	
	/**
	 * Devuelve un ArrayList de Vector2 con las posibles casillas a las que puede moverse el peon con posición (x,y)
	 * @param x
	 * @param y
	 * @return
	 */
	@Override
	public ArrayList<Vector2> posibleMovements() {//para implementar esta función en cada pieza habrá que hacerlo de forma diferente
		ArrayList<Vector2> movements = new ArrayList<>();
		Vector2 mov;
		int direction = 1;
		if(!color) {
			direction = -1;
		}
		
		for(int i = -1; i<=1; i++) {
			mov = new Vector2(x + i, y + direction);
			if(checkBoard(board, i, mov.x, mov.y)) {
				movements.add(mov);
			}
		}
		
		if(!hasBeenMoved && board.dim==8) {
			mov = new Vector2(x + 2 , y + 2*direction);
			if(checkBoard(board, -1, mov.x, mov.y) &&  board.getTile(x+1, y+direction) != null && board.getTile(x+1, y+direction).getPiece()==null) {
				movements.add(mov);
			}
			mov = new Vector2(x - 2 , y + 2*direction);
			if(checkBoard(board, 1, mov.x, mov.y) &&  board.getTile(x-1, y+direction) != null  && board.getTile(x-1, y+direction).getPiece()==null) {
				movements.add(mov);
			}
		}

		if (y == 5 - (color?0:1)){ //Si está en la fila donde puede hacer en passant(5 para blancas, 4 para negras)
			Piece aux;
			if (board.getTile(x-1,y) != null){
				aux = board.getTile(x-1,y).getPiece(); //Mira a la izquierda

				if (aux != null && aux instanceof Lancer && aux.isPassantable) { //Si la pieza puede tomarse al paso(habrá que cambiar el if si se incluyen más)
					mov = new Vector2(x,y+direction);
					movements.add(mov);
				}
			}
			if (board.getTile(x+1,y) != null){
				aux = board.getTile(x+1,y).getPiece(); //Mira a la derecha

				if (aux != null && aux instanceof Lancer && aux.isPassantable){
					mov = new Vector2(x,y+direction);
					movements.add(mov);
				}
			}
		}

		return movements;
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
				 return Reader.readSection(28, 32);
			 case "eng/":
				 return Reader.readSection(28,33);
			 default:
				 throw new IllegalArgumentException("Configuración errónea");
		 }
	}

	@Override
	public String toString() {
		String str = super.toString();
		return str.replace("X","Joker");
	}
}