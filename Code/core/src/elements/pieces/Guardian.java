package elements.pieces;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import elements.Board;
import elements.Piece;
import interaccionFichero.LineReader;
import utils.Resources;

import java.util.ArrayList;

public class Guardian extends Piece {

	public Guardian(Boolean color, int x, int y, Board board) {
		super(color,Resources.WARDEN_PATH, x ,y,board);
		
	}
	
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
	public ArrayList<Vector2> posibleMovements() {//para implementar esta función en cada pieza habrá que hacerlo de forma diferente
		ArrayList<Vector2> movements = new ArrayList<>();
		Vector2 mov;
		int direction = 1;
		if(!color) {
			direction = -1;
		}

		if(backed) {
			mov = new Vector2(x, y + 2*direction);
			if(checkBoard(board, 0, mov.x, mov.y) && board.getTile(x, y+direction).getPiece()==null) {
				movements.add(mov);
			}
		}else {
			for(int i = -1; i<=1; i++) {
				mov = new Vector2(x + i, y + direction);
				if(checkBoard(board, i, mov.x, mov.y)) {
					movements.add(mov);
				}
			}
			mov = new Vector2(x , y - direction); 
			if(checkBoard(board, 0, mov.x, mov.y)) {
				movements.add(mov);
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
				 return Reader.readSection(20, 26);
			 case "eng/":
				 return Reader.readSection(20,26);
			 default:
				 throw new IllegalArgumentException("Configuración errónea");
		 }
	}

	@Override
	public String toString() {
		String str = super.toString();
		return str.replace("X","Guardian");
	}

}
