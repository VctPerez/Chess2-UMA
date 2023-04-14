package game.chess;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import utils.IOS;
import utils.Render;
import utils.Resources;

public class Chess2 extends Game {
	public static boolean hosting;
	
	private AssetManager manager;

	public AssetManager getManager() {
		return manager;
	}

	/**
	 * Se encarga de cargar los (posibles) recursos que posteriormente utilizaremos en le juego
	 */
	private void loadResources(){
		loadLoadingScreen();
		//CHESS PIECES
		manager.load(Resources.PAWN_PATH, Texture.class);
		manager.load(Resources.BISHOP_PATH, Texture.class);
		manager.load(Resources.QUEEN_PATH, Texture.class);
		manager.load(Resources.KING_PATH, Texture.class);
		manager.load(Resources.ROOK_PATH, Texture.class);
		manager.load(Resources.KNIGHT_PATH, Texture.class);
		manager.load(Resources.ARROW_PATH, Texture.class);
		
		manager.load(Resources.FRAME_PATH, Texture.class);

		//CUSTOMS BUTTONS
		//manager.load(Resources.CHECK_PATH, Texture.class);
		manager.load(Resources.CHECKBOX_UNSELECTED, Texture.class);
		manager.load(Resources.CHECKBOX_SELECTED, Texture.class);
		manager.load(Resources.SLIDER_PATH, Texture.class);
		manager.load(Resources.SELECTEDBAR_PATH, Texture.class);
		manager.load(Resources.UNSELECTEDBAR_PATH, Texture.class);

		manager.load(Resources.SKIN_PATH,Skin.class);
		
		//MUSIC & SOUNDS
		manager.load(Resources.MENU_THEME, Music.class);
		manager.load(Resources.PIECEMOVE_SOUND, Sound.class);
		manager.load(Resources.TEXTBUTTON_HOVERSOUND, Sound.class);
		manager.load(Resources.TEXTBUTTON_CLICKSOUND, Sound.class);

		//ETC
		manager.load(Resources.LOGO_PATH, Texture.class);
		
		/*manager.load(Resources.MENU_BACKGROUND_PATH, Texture.class);
		manager.load(Resources.PANTALLACARGA_PATH, Texture.class);*/


		manager.finishLoading();
	}
	private void loadLoadingScreen(){
		manager.load(Resources.LODINGSOUND1, Sound.class);
		manager.load(Resources.LODINGSOUND2, Sound.class);
		manager.load(Resources.LODINGSOUND3, Sound.class);
		manager.load(Resources.LODINGSOUND4, Sound.class);
		manager.load(Resources.LOADINGSCREEN_PATH, Texture.class);
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
		//while(!manager.isFinished());
		Render.LOADINGSCREEN = new LoadingScreen();
//		this.setScreen(Render.CONFIGSCREEN);

		//Cargamos en Render solo pantallas que sean necesarias crear una sola vez (Para ahorrarnos tener que crear nuevas innecesariamente), 
		//otras como el GameScreen necesita ser creadas de nuevo al volverse a usar
		Render.MAINSCREEN = new MainScreen();
		Render.CONFIGSCREEN = new ConfigScreen();
		Render.LANGUAGESCREEN = new LanguageScreen();
		Render.MANUALSCREEN = new ManualScreen();
		Render.LOBBYSCREEN = new LobbyScreen();
		Render.CREATEMATCHSCREEN = new CreateMatchScreen();
		Render.DRAFTSCREEN = new DraftScreen();

		this.setScreen(new GameScreen());

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
 