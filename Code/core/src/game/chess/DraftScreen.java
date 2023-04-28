package game.chess;

import java.io.IOException;
import java.util.*;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.StringBuilder;
import com.badlogic.gdx.utils.viewport.FitViewport;

import elements.*;
import interaccionFichero.LectorLineas;
import utils.*;

public class DraftScreen extends AbstractScreen {
	public static Stage stage;

	private Map<String, String> draft;
	private ArrayList<String> pawns, knights, rooks, bishops, queens, kings;
	private String currentPieceSelection;

	private PieceInfo info;
	private TextButton next, back;
	private Image arrow;
	private int cont = 0;
	private LectorLineas languageReader, configReader;

	TileButton tile1, tile2;
	private boolean draftCompleted = false; // controla que ya se han enviado ambos los drafts
	private boolean p1Finished = false, p2Finished = false, msgSent = false; //controla para el inicio de la partida y para no enviar el mensaje 2 veces

	@Override
	public void show() {
		if(Render.DraftController==3) {
			if(Render.hosting){
				Render.host.resetMessage();
			}else{
				Render.guest.resetMessage();
			}
		}

		System.out.println("El modo de draft es " + Render.DraftController);
		stage = new Stage(new FitViewport(1280, 720));
		Gdx.input.setInputProcessor(stage);

		configReader = new LectorLineas("files/config.txt"); // Lector del txt configuracion para sacar el idioma
		languageReader = new LectorLineas("files/lang/" + configReader.leerLinea(Settings.language) + "Draft-Game.txt");

		info = new PieceInfo();

		// -------------------------------
		stage.addActor(Render.menuBG);
		stage.addActor(info);

		initPieceClasses();
		initTileButtons();
		initButtons();
		initDraft();
	}

	private void initTileButtons() {
		tile1 = new TileButton(325f, 450f, 168f);
		tile2 = new TileButton(325f, 150f, 168f);

		tile1.addCaptureListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

				tile1.showFrame();
				tile2.hideFrame();

				currentPieceSelection = tile1.getPiece();
				info.getInfoFrom(currentPieceSelection);
				updateDraft();

				return true;
			}
		});
		tile2.addCaptureListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

				tile2.showFrame();
				tile1.hideFrame();

				currentPieceSelection = tile2.getPiece();
				info.getInfoFrom(currentPieceSelection);
				updateDraft();

				return true;
			}
		});
		stage.addActor(tile1);
		stage.addActor(tile2);
	}

	private void initPieceClasses() {
		pawns = new ArrayList<>();
		knights = new ArrayList<>();
		rooks = new ArrayList<>();
		bishops = new ArrayList<>();
		queens = new ArrayList<>();
		kings = new ArrayList<>();

		pawns.add(Resources.WARDEN_PATH);
		pawns.add(Resources.LANCER_PATH);
		pawns.add(Resources.PAWN_PATH);
		Collections.shuffle(pawns);

		knights.add(Resources.KNIGHT_PATH);
		knights.add(Resources.RIDER_PATH);
		knights.add(Resources.BOMBER_PATH);
		Collections.shuffle(knights);

		rooks.add(Resources.ROOK_PATH);
		rooks.add(Resources.COLOSUS_PATH);
		rooks.add(Resources.MINER_PATH);
		Collections.shuffle(rooks);

		bishops.add(Resources.BISHOP_PATH);
		bishops.add(Resources.RND_PATH);
		bishops.add(Resources.PALADIN_PATH);
		Collections.shuffle(bishops);

		queens.add(Resources.QUEEN_PATH);
		queens.add(Resources.VALKYRIE_PATH);
		queens.add(Resources.CATAPULT_PATH);

		Collections.shuffle(queens);

		kings.add(Resources.KING_PATH);
		kings.add(Resources.MIDAS_PATH);
		Collections.shuffle(kings);
	}

	private void initDraft() {

		// Podemos hacer que al principio tengan la imagen de la primera pieza de cada
		// tipo, de las piezas básicas o una nueva que sea una silueta o con una
		// interrogacion o algo

		draft = new LinkedHashMap<String, String>();
		draft.put("Pawn", Resources.PAWN_PATH);
		draft.put("Knight", Resources.KNIGHT_PATH);
		draft.put("Rook", Resources.ROOK_PATH);
		draft.put("Bishop", Resources.BISHOP_PATH);
		draft.put("Queen", Resources.QUEEN_PATH);
		draft.put("King", Resources.KING_PATH);

		int i = 0;
		for (String key : draft.keySet()) {
			addDraftPieceToStage(key, i);
			i++;
		}

		arrow = new Image(Render.app.getManager().get(Resources.ARROW_PATH, Texture.class));
		stage.addActor(arrow);
		changePiece();
	}

	private void addDraftPieceToStage(String key, int pos) {
		Image piece = Parser.getImageFromPath(draft.get(key));
		piece.setName(key);
		piece.setSize(84, 84);
		piece.setPosition(20, 85 + ((5 - pos) * 100));
		stage.addActor(piece);
	}

	private void updateDraft() {
		String key = (String) draft.keySet().toArray()[cont];
		stage.getRoot().findActor(key).remove();

		// -------¿CAMBIAR? Ahora mismo la pieza del draft no cambia hasta q pulses una
		// casilla pero entonces hay que controlar que hayas pulsado
		// al menos una vez todas las casillas para que no te quedes con una pieza en el
		// draft que no te ha salido para seeleccionar?
		draft.put(key, currentPieceSelection);
		addDraftPieceToStage(key, cont);
	}

	/**
	 * Se inicializan los botones, el boton next se convierte en finalizar y dependiendo del modo de juego se realiza otro draft y se empieza una partida en local
	 * o se envian y reciben los drafts con otro jugador
	 */
	private void initButtons() {
		// Botones con nombre momentaneos, se cambiara y se a�adira a los ficheros de
		// idiomas

		next = new TextButton(languageReader.leerLinea(2), "SingleClickStyle");
		next.setPosition(480, 50);
		next.addCaptureListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if (cont < 5) {
					cont++;
					changePiece();
				} else {
					if (Render.DraftController == 1) {
						Render.hosting = true;
						Render.player1Draft.addAll(draft.values());
						Render.DraftController++;
						Render.app.setScreen(new DraftScreen());
					} else if (Render.DraftController == 2) {
						Render.player2Draft.addAll(draft.values());
						Render.GameScreen = new GameScreen();
						Render.app.setScreen(Render.GameScreen);
					} else if (Render.DraftController == 3) {
						try {
							if (Render.hosting) {//metodos en host y guest
								if(!msgSent){
									Render.player1Draft.addAll(draft.values());
									Render.host.sendMessage(draftMessage());
									msgSent = true;
									p1Finished = true;
								}
							} else {
								if(!msgSent){
									Render.player2Draft.addAll(draft.values());
									Render.guest.sendMessage(draftMessage());
									msgSent = true;
									p2Finished = true;
								}
							}
						} catch (IOException e) {
							throw new RuntimeException(e);
						}
					}

				}

				if (cont == 5) {
					next.setText(languageReader.leerLinea(3));
				}
				return true;
			}
		});

		back = new TextButton(languageReader.leerLinea(1), "SingleClickStyle");
		back.setPosition(175, 50);
		back.addCaptureListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if (cont > 0) {
					cont--;
					changePiece();
				}

				if (cont == 4) {
					next.setText("Siguiente");
				}
				return true;
			}
		});

		stage.addActor(next);
		stage.addActor(back);
	}

	private void updateTileButtons(ArrayList<String> pieceClass) {
		tile1.setPiece(pieceClass.get(0));
		tile2.setPiece(pieceClass.get(1));

		tile1.showFrame();
		tile2.hideFrame();
		currentPieceSelection = tile1.getPiece();
		info.getInfoFrom(currentPieceSelection);
		updateDraft();
		arrow.setPosition(80, 100 + 100 * (5 - cont));

	}

	public void changePiece() {
		switch (cont) {
			case 0:
				updateTileButtons(pawns);
				break;
			case 1:
				updateTileButtons(knights);
				break;
			case 2:
				updateTileButtons(rooks);
				break;
			case 3:
				updateTileButtons(bishops);
				break;
			case 4:
				updateTileButtons(queens);
				break;
			case 5:
				updateTileButtons(kings);
				break;
		}
	}

	private String draftMessage() {
		StringBuilder sb = new StringBuilder();
		for (String piece : draft.values()) {
			sb.append(piece).append("#");
		}
		return sb.toString();
	}

	private void saveOpponentDraft(String draft) {
		if (Render.hosting) {
			Render.player2Draft.addAll(Arrays.asList(draft.split("#")));
		} else {
			Render.player1Draft.addAll(Arrays.asList(draft.split("#")));
		}
	}

	@Override
	public void render(float delta) {
		Render.clearScreen();
		Render.Batch.begin();

		stage.draw();
		stage.act();
		
		if(Render.DraftController==3)checkEnd();
		Render.Batch.end();
	}

	private void checkEnd() {
		checkMessage();
		if(p1Finished && p2Finished){
			Render.GameScreen = new OnlineGameScreen();
			Render.app.setScreen(Render.GameScreen);
		}
	}

	private void checkMessage() {
		if (!Render.hosting && !Render.guest.getMessage().equals("")) {
			saveOpponentDraft(Render.guest.getMessage());
			Render.guest.resetMessage();
			p1Finished = true;
		}else if(Render.hosting && !Render.host.getMessage().equals("")){
			System.out.println("Mensaje: " +Render.host.getMessage());
			saveOpponentDraft(Render.host.getMessage());
			Render.host.resetMessage();
			p2Finished = true;
		}
	}

	@Override
	public void resize(int width, int height) {
		Render.SCREEN_HEIGHT = height;
		Render.SCREEN_WIDTH = width;
		stage.getViewport().update(width, height);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		super.pause();
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		super.resume();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		super.hide();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
	}

}
