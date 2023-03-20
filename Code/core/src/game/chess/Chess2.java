package game.chess;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import utils.Render;

public class Chess2 extends ApplicationAdapter {
	Texture img;
	
	@Override
	public void create () {
		Render.Batch = new SpriteBatch();
		Render.camara = new OrthographicCamera();
		Render.app = this;
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {

	}
}
