package game.chess;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import utils.Render;

import java.awt.*;

public class Chess2 extends Game {
	@Override
	public void create () {
		Render.Batch = new SpriteBatch();
		Render.camera = new OrthographicCamera(Render.SCREEN_WIDTH, Render.SCREEN_HEIGHT);
		Render.app = this;

		Render.GAMESCREEN = new GameScreen();
		Render.MAINSCREEN = new MainScreen();
		Render.CONFIGSCREEN = new ConfigScreen();
		Render.LANGUAGESCREEN = new LanguageScreen();
		//Render.CLASSICMANSCREEN = new ClassicManScreen();
		Render.MANUALSCREEN = new ManualScreen();
		
		this.setScreen(new MainScreen());
		//this.setScreen(new GameScreen());
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {

	}
}
 