package elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import utils.Render;
import utils.TextButton;

public class MatchResults extends Actor{
	
	private Table table;
	private Label matchres;
	private TextButton goMenu;
	private Stage stage;
	
	public MatchResults(Stage stage) {
		this.stage=stage;
		table = new Table();
		table.setBounds(350, 100, 550, 550);

		Texture texture = new Texture("Black.png");
		TextureRegionDrawable drawable = new TextureRegionDrawable(texture);
		table.setBackground(drawable);
		
    	createTableElements();
	    setupTable();
    	addActors();
	}

	private void addActors() {
		stage.addActor(table);
	}

	private void setupTable() {
	    table.add(matchres).center();
	    table.row();
	    table.add(goMenu).center().padTop(50);
	}

	private void createTableElements() {
		matchres = new Label("-", Render.skin, "TitleStyle");
		matchres.setFontScale(0.35f, 0.35f);
    	goMenu = new TextButton("Volver al Menú Principal","SingleClickStyle");
    	goMenu.getLabel().setFontScale(0.35f,0.35f);
	}

	public void setWinner(String Winner) {
		matchres.setText("HA GANADO EL " + Winner);
	}
	
	public void setWinnerSurrender(String Winner) {
		matchres.setText("HA GANADO EL " + Winner+" \n    POR RENDICION");
	}

	public void setDraw() {
		matchres.setText("Empate");
	}
	
	public void Show() {
		table.setVisible(true);
	}
	
	public void Hide() {
		table.setVisible(false);
	}
	
	public void toFront() {
		table.toFront();
	}
	
	
	
	public void render() {
		if(goMenu.isPressed()) {
			if(Render.hosting){
				Render.host.setReceiving(false);
				Render.host = null;
			}else{
				Render.guest.setReceiving(false);
				Render.guest = null;
			}
			Render.app.setScreen(Render.MAINSCREEN);
		}
	}
	
}
