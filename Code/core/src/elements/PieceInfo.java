package elements;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import utils.*;

public class PieceInfo extends Actor{
	
	private Text info;
	private Background fondoRes;
	
	public PieceInfo() {
		fondoRes = new Background();
		fondoRes.setColor(new Color(60/255f, 60/255f,60/255f,1f));
        fondoRes.setPosition(400, 100);
        fondoRes.setSize(500, 500);
        
        info = new Text(Resources.FONT_MENU_PATH,30,Color.WHITE,3);
        info.setPosition(450, 400);
        info.setText("-");
        
        
	}

	
	public void draw(Batch batch, float parentAlpha) {
		fondoRes.draw(batch, parentAlpha);

	}
}
