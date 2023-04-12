package elements;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import utils.*;

public class PieceInfo extends Actor{
	
	private Text info;
	private Background background;
	
	public PieceInfo(Piece piece) {
		background = new Background();
		background.setColor(new Color(60/255f, 60/255f,60/255f,1f));
		background.setPosition(750, 50);
		background.setSize(500, 625);
        
        info = new Text(Resources.FONT_MENU_PATH,20,Color.WHITE,3);
        info.setPosition(760, 665);
        info.setText(piece.getInfo());
        
        
	}

	
	public void draw(Batch batch, float parentAlpha) {
		background.draw(batch, parentAlpha);
		info.draw(batch, parentAlpha);

	}
}
