package elements;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import elements.pieces.*;
import utils.*;

public class PieceInfo extends Actor{
	
	private Text info;
	private ShapeRenderer background;
	public Board board;
	
	public PieceInfo() {
		background = new ShapeRenderer();
		board=new Board(84,790,75, 5);
        
        
	}
	
	public void getInfoFrom(String piecePath) { 
		infoFrom(Render.parser.getPieceFromPath(piecePath, true, 3, 3, board));
	}
	
	public void infoFrom(Piece piece) {
		clearBoard(board.getTile(3, 3).getPiece());
		
		piece.setSize(board.getTile(3, 3).getWidth(), board.getTile(3, 3).getHeight());
		board.getTile(3, 3).setPiece(piece);
		
		highlight(piece);
        
        info = new Text(Resources.FONT_MENU_PATH,20,Color.WHITE,3);
        info.setPosition(760, 685);
        info.setText(piece.getInfo());
	}
	
	public void clearBoard(Piece piece) {// usamos este método o sera más eficiente simplemente recorrer todo el tablero 5x5 y poner el highliht y eso a false?
		if(piece!=null) {
			for (Vector2 vector : piece.posibleMovements()) {
				Tile tile = board.getTile(vector.x, vector.y);
				if(tile.piece!=null && tile.piece.color()!=piece.color) {
					tile.attacked=false;
				}else {
					tile.highlight = false;
				}
			}
		}
	}
	
	/**
	 * Resalta las casillas contenidas en el array de movimientos válidos.
	 * @param b 
	 */
	private void highlight(Piece piece) {
		for (Vector2 vector : piece.posibleMovements()) {
			Tile tile = board.getTile(vector.x, vector.y);
			//En caso de que haya una pieza enemiga la resalta en rojo
			if(tile.getPiece()!=null && tile.getPiece().color()!=piece.color()) {
				tile.attacked=true;
			}else {
				tile.highlight = true;
			}
		}
	}

	
	public void draw(Batch batch, float parentAlpha) {
		batch.end();
		background.begin(ShapeType.Filled);
		background.setProjectionMatrix(batch.getProjectionMatrix());
		background.setTransformMatrix(batch.getTransformMatrix());
		background.rect(750, 40, 500, 665);
		background.setColor(212/255f,202/255f,182/255f,1f);
		background.end();
		batch.begin();
		info.draw(batch, parentAlpha);
		board.draw(batch, parentAlpha);

	}
}
