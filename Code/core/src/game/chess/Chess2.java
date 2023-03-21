package game.chess;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import utils.Render;

public class Chess2 extends Game {
	Texture img;
	private GameScreen pantalla;
	@Override
	public void create () {
		Render.Batch = new SpriteBatch();
		Render.camara = new OrthographicCamera();
		Render.app = this;

		this.setScreen(new GameScreen());
		
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {

	}
}
 