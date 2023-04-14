package elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import utils.Render;
import utils.Resources;
import utils.Text;
import utils.TextButton;

public class MatchResults extends Actor{

	private Text matchres,goMenuText;
	private Background fondoRes;
	private TextButton goMenu;
	
	public MatchResults() {
		fondoRes = new Background();
		fondoRes.setColor(new Color(60/255f, 60/255f,60/255f,1f));
        fondoRes.setPosition(400, 100);
        fondoRes.setSize(500, 500);
        
        matchres = new Text(Resources.FONT_MENU_PATH,30,Color.WHITE,3);
        matchres.setPosition(450, 400);
        matchres.setText("-");
        
        goMenuText= new Text(Resources.FONT_MENU_PATH,20,Color.WHITE,3);
        goMenuText.setPosition(500, 250);
        goMenuText.setText("Volver al Menú Principal");
        goMenu = new TextButton(goMenuText);
        goMenu.setName("goMenu");
        
	}
	
	public void setWinner(String Winner) {
		matchres.setText("HA GANADO EL " + Winner);
	}

	public void setDraw() {
		matchres.setText("Empate");
	}
	
	public void draw(Batch batch, float parentAlpha) {
		fondoRes.draw(batch, parentAlpha);
		matchres.draw(batch, parentAlpha);
		
		
		//Por algun motivo solo puedo dibujar el boton así, de cualquier otra forma me da errores (Revisable)
		Render.Batch.begin();
		goMenu.draw(Render.Batch, 0); 
		Render.Batch.end();
	}
	
	public void act(float delta) {
		super.act(delta);
	}
	
	public void Show() {
		setVisible(true);
	}
	
	public void Hide() {
		setVisible(false);
	}
	
	public void render() {
		if(goMenu.isPressed()) {
			Render.app.setScreen(Render.MAINSCREEN);
		}
	}
	
}
