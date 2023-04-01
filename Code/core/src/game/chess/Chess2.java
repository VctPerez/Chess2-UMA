package game.chess;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import utils.IOS;
import utils.Render;
import utils.Resources;

public class Chess2 extends Game {

	private AssetManager manager;

	public AssetManager getManager() {
		return manager;
	}

	/**
	 * Se encarga de cargar los (posibles) recursos que posteriormente utilizaremos en le juego
	 */
	private void loadResources(){

		//CHESS PIECES
		manager.load(Resources.PAWN_PATH, Texture.class);
		manager.load(Resources.BISHOP_PATH, Texture.class);
		manager.load(Resources.QUEEN_PATH, Texture.class);
		manager.load(Resources.KING_PATH, Texture.class);
		manager.load(Resources.ROOK_PATH, Texture.class);
		manager.load(Resources.KNIGHT_PATH, Texture.class);
		
		manager.load(Resources.FRAME_PATH, Texture.class);

		//CUSTOMS BUTTONS
		manager.load(Resources.CHECK_PATH, Texture.class);
		manager.load(Resources.CHECKBOX_UNSELECTED, Texture.class);
		manager.load(Resources.CHECKBOX_SELECTED, Texture.class);
		manager.load(Resources.SLIDER_PATH, Texture.class);
		manager.load(Resources.SELECTEDBAR_PATH, Texture.class);
		manager.load(Resources.UNSELECTEDBAR_PATH, Texture.class);

		//MUSIC & SOUNDS
		manager.load(Resources.MENU_THEME, Music.class);
		//ETC
		manager.load(Resources.LOGO_PATH, Texture.class);
		/*
		manager.load(Resources.MENU_BACKGROUND_PATH, Texture.class);
		manager.load(Resources.PANTALLACARGA_PATH, Texture.class);*/


		manager.finishLoading();
	}
	@Override
	public void create () {

		Render.Batch = new SpriteBatch();
		Render.camera = new OrthographicCamera(Render.SCREEN_WIDTH, Render.SCREEN_HEIGHT);
		Render.app = this;
		Render.inputs = new IOS();
		Gdx.input.setInputProcessor(Render.inputs);

		manager = new AssetManager();
		loadResources();
		//this.setScreen(new LobbyScreen());

		Render.GAMESCREEN = new GameScreen();
		Render.MAINSCREEN = new MainScreen();
		Render.CONFIGSCREEN = new ConfigScreen();
		Render.LANGUAGESCREEN = new LanguageScreen();
		Render.CLASSICMANSCREEN = new ClassicManScreen();
		Render.MANUALSCREEN = new ManualScreen();

		this.setScreen(Render.GAMESCREEN);

	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
	}
}
 