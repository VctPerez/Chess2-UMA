package elements;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import utils.*;

public class PieceInfo extends Actor{
	
	private Text info;
	private Background background;
	private Board board;
	
	public PieceInfo(Piece piece) {
		background = new Background();
		background.setColor(new Color(90/255f, 90/255f,90/255f,1f));
		background.setPosition(750, 50);
		background.setSize(500, 625);
		
		
		board=piece.board;
		board.getTile(3, 3).setPiece(piece);
		
		for (Vector2 vector : piece.posibleMovements()) {
			Tile tile = board.getTile(vector.x, vector.y);
			if(tile.piece!=null && tile.piece.color()!=piece.color) {
				tile.attacked=true;
			}else {
				tile.highlight = true;
			}
		}
		

        
        info = new Text(Resources.FONT_MENU_PATH,20,Color.WHITE,3);
        info.setPosition(760, 665);
        info.setText(piece.getInfo());
        
        
	}

	
	public void draw(Batch batch, float parentAlpha) {
		background.draw(batch, parentAlpha);
		info.draw(batch, parentAlpha);
		board.draw(batch, parentAlpha);

	}
}
