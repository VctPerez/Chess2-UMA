package game.chess;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.Hinting;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import elements.Background;
import interaccionFichero.LectorLineas;
import utils.IOS;
import utils.Render;
import utils.Resources;
import utils.Settings;

public class Chess2 extends Game {
	public static boolean hosting;

	private AssetManager manager;

	public AssetManager getManager() {
		return manager;
	}

	/**
	 * Se encarga de cargar los (posibles) recursos que posteriormente utilizaremos
	 * en le juego
	 */
	private void loadResources() {
		loadLoadingScreen();
		// CHESS PIECES
		manager.load(Resources.PAWN_PATH, Texture.class);
		manager.load(Resources.LANCER_PATH, Texture.class);
		manager.load(Resources.BISHOP_PATH, Texture.class);
		manager.load(Resources.QUEEN_PATH, Texture.class);
		manager.load(Resources.KING_PATH, Texture.class);
		manager.load(Resources.ROOK_PATH, Texture.class);
		manager.load(Resources.KNIGHT_PATH, Texture.class);
		manager.load(Resources.RIDER_PATH, Texture.class);
		manager.load(Resources.ARROW_PATH, Texture.class);
		manager.load(Resources.PIECE_DISPOSER_PATH,Texture.class);
		manager.load(Resources.GRAVEYARD_PATH,Texture.class);
		manager.load(Resources.BOMBER_PATH, Texture.class);
		manager.load(Resources.WARDEN_PATH, Texture.class);
		manager.load(Resources.PALADIN_PATH, Texture.class);
		manager.load(Resources.MIDAS_PATH, Texture.class);
		manager.load(Resources.MIDAS_PATH2, Texture.class);
		manager.load(Resources.MIDAS_PATH3, Texture.class);
		manager.load(Resources.PALADIN_PATH, Texture.class);
		manager.load(Resources.COLOSUS_PATH, Texture.class);
		manager.load(Resources.MINER_PATH, Texture.class);
		manager.load(Resources.WITCH_PATH, Texture.class);
		manager.load(Resources.MAGE_PATH,Texture.class);
		manager.load(Resources.MAGE_PATH2,Texture.class);
		manager.load(Resources.RND_PATH,Texture.class);
		
		manager.load(Resources.VALKYRIE_PATH, Texture.class);
		manager.load(Resources.VALKYRIE_ANIMATION1_PATH, Texture.class);
		manager.load(Resources.VALKYRIE_ANIMATION2_PATH, Texture.class);
		//Black pieces
		manager.load(Resources.BLACK_PAWN_PATH, Texture.class);
		manager.load(Resources.BLACK_LANCER_PATH, Texture.class);
		manager.load(Resources.BLACK_BISHOP_PATH, Texture.class);
		manager.load(Resources.BLACK_QUEEN_PATH, Texture.class);
		manager.load(Resources.BLACK_KING_PATH, Texture.class);
		manager.load(Resources.BLACK_ROOK_PATH, Texture.class);
		manager.load(Resources.BLACK_KNIGHT_PATH, Texture.class);
		manager.load(Resources.BLACK_RIDER_PATH, Texture.class);
		manager.load(Resources.BLACK_BOMBER_PATH, Texture.class);
		manager.load(Resources.BLACK_WARDEN_PATH, Texture.class);
		manager.load(Resources.BLACK_PALADIN_PATH, Texture.class);
		manager.load(Resources.BLACK_MIDAS_PATH, Texture.class);
		manager.load(Resources.BLACK_MIDAS_PATH2, Texture.class);
		manager.load(Resources.BLACK_MIDAS_PATH3, Texture.class);
		manager.load(Resources.BLACK_PALADIN_PATH, Texture.class);
		manager.load(Resources.BLACK_COLOSUS_PATH, Texture.class);
		manager.load(Resources.BLACK_MINER_PATH, Texture.class);
		manager.load(Resources.BLACK_WITCH_PATH, Texture.class);
		manager.load(Resources.BLACK_MAGE_PATH,Texture.class);
		manager.load(Resources.BLACK_MAGE_PATH2,Texture.class);
		manager.load(Resources.BLACK_RND_PATH,Texture.class);
		
		manager.load(Resources.BLACK_VALKYRIE_PATH, Texture.class);
		manager.load(Resources.BLACK_VALKYRIE_ANIMATION1_PATH, Texture.class);
		manager.load(Resources.BLACK_VALKYRIE_ANIMATION2_PATH, Texture.class);

		manager.load(Resources.FRAME_PATH, Texture.class);

		// CUSTOMS BUTTONS
		// manager.load(Resources.CHECK_PATH, Texture.class);
		manager.load(Resources.SLIDER_PATH, Texture.class);
		manager.load(Resources.SELECTEDBAR_PATH, Texture.class);
		manager.load(Resources.UNSELECTEDBAR_PATH, Texture.class);

		// MUSIC & SOUNDS
		manager.load(Resources.MENU_THEME, Music.class);
		manager.load(Resources.PIECESELECTION_SOUND, Sound.class);
		manager.load(Resources.PIECEMOVE_SOUND, Sound.class);
		manager.load(Resources.EXPLOSION_SOUND, Sound.class);
		manager.load(Resources.PALADINSWING_SOUND, Sound.class);
		manager.load(Resources.VALKYRIEFLY_SOUND, Sound.class);
		manager.load(Resources.MINER_SOUND, Sound.class);
		manager.load(Resources.BUTTON_HOVERSOUND, Sound.class);
		manager.load(Resources.BUTTON_CLICKSOUND, Sound.class);

		// ETC
		manager.load(Resources.LOGO_PATH, Texture.class);
		manager.load(Resources.RESULTS_BACKGROUND_PATH,Texture.class);
		manager.load(Resources.BLACK_OPACITY_PATH,Texture.class);

		/*
		 * manager.load(Resources.MENU_BACKGROUND_PATH, Texture.class);
		 * manager.load(Resources.PANTALLACARGA_PATH, Texture.class);
		 */

		manager.finishLoading();
	}

	private void loadLoadingScreen() {
		manager.load(Resources.LODINGSOUND1, Sound.class);
		manager.load(Resources.LODINGSOUND2, Sound.class);
		manager.load(Resources.LODINGSOUND3, Sound.class);
		manager.load(Resources.LODINGSOUND4, Sound.class);
		manager.load(Resources.LOADINGSCREEN_PATH, Texture.class);
	}
	


	private void loadSettings() {
		LectorLineas configReader = new LectorLineas("files/config.txt");
		Settings.updateSettings(configReader.leerFLOATLinea(5), configReader.leerFLOATLinea(6),
				configReader.leerBOOLEANLinea(7),configReader.leerINTLinea(8));
	}

	@Override
	public void create() {

		Render.Batch = new SpriteBatch();
		Render.camera = new OrthographicCamera(Render.SCREEN_WIDTH, Render.SCREEN_HEIGHT);
		Render.app = this;
		Render.inputs = new IOS();
		Render.monitor = Gdx.graphics.getMonitor();
		Render.menuBG = new Background();
		
		Gdx.input.setInputProcessor(Render.inputs);
		manager = new AssetManager();

		loadSettings();

		loadSkin();

		loadResources();

		// while(!manager.isFinished());
		Render.LOADINGSCREEN = new LoadingScreen();
		// this.setScreen(Render.CONFIGSCREEN);

		// Cargamos en Render solo pantallas que sean necesarias crear una sola vez
		// (Para ahorrarnos tener que crear nuevas innecesariamente),
		// otras como el GameScreen necesita ser creadas de nuevo al volverse a usar
		Render.MAINSCREEN = new MainScreen();
		Render.CONFIGSCREEN = new ConfigScreen();
		Render.LANGUAGESCREEN = new LanguageScreen();
		Render.MATCHMAKINGSCREEN = new MatchMakingScreen();
		Render.MODESCREEN = new ModeScreen();
		Render.MANUALSCREEN = new ManualScreen();
		Render.CLASSICMANSCREEN = new ClassicManScreen();
		Render.MODIFIEDMANSCREEN = new ModifiedManScreen();
		Render.LOBBYSCREEN = new LobbyScreen();
		Render.CREATEMATCHSCREEN = new CreateMatchScreen();
		Render.DRAFTSCREEN = new DraftScreen();
		
		
		Render.DraftController=1;
		Render.GameScreen = new GameScreen();
		Render.hosting=true;

		//this.setScreen(Render.MAINSCREEN);
		this.setScreen(Render.GameScreen);

	}

	private void loadSkin() {
		Render.skin = new Skin(Gdx.files.internal(Resources.SKIN_PATH)) {
			// Override json loader to process FreeType fonts from skin JSON
			@Override
			protected Json getJsonLoader(final FileHandle skinFile) {
				Json json = super.getJsonLoader(skinFile);
				final Skin skin = this;

				json.setSerializer(FreeTypeFontGenerator.class, new Json.ReadOnlySerializer<FreeTypeFontGenerator>() {
					@Override
					public FreeTypeFontGenerator read(Json json,
							JsonValue jsonData, Class type) {
						String path = json.readValue("font", String.class, jsonData);
						jsonData.remove("font");

						Hinting hinting = Hinting.valueOf(json.readValue("hinting",
								String.class, "AutoMedium", jsonData));
						jsonData.remove("hinting");

						TextureFilter minFilter = TextureFilter.valueOf(
								json.readValue("minFilter", String.class, "Nearest", jsonData));
						jsonData.remove("minFilter");

						TextureFilter magFilter = TextureFilter.valueOf(
								json.readValue("magFilter", String.class, "Nearest", jsonData));
						jsonData.remove("magFilter");

						FreeTypeFontParameter parameter = json.readValue(FreeTypeFontParameter.class, jsonData);
						parameter.hinting = hinting;
						parameter.minFilter = minFilter;
						parameter.magFilter = magFilter;
						FreeTypeFontGenerator generator = new FreeTypeFontGenerator(skinFile.parent().child(path));
						BitmapFont font = generator.generateFont(parameter);
						skin.add(jsonData.name, font);
						if (parameter.incremental) {
							generator.dispose();
							return null;
						} else {
							return generator;
						}
					}
				});

				return json;
			}
		};
	}
	

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}
