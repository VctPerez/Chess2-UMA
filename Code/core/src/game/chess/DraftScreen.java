package game.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.StringBuilder;
import com.badlogic.gdx.utils.viewport.FitViewport;
import elements.PieceInfo;
import elements.TileButton;
import interaccionFichero.LectorLineas;
import utils.*;

import java.io.IOException;
import java.util.*;

public class DraftScreen extends AbstractScreen {
	public static Stage stage;

	private Map<String, String> draft;
	private ArrayList<Image> pieces;
	private ArrayList<String> pawns, knights, rooks, bishops, queens, kings;
	private String currentPieceSelection;
	
	protected Label title;

	private PieceInfo info;
	private TextButton next, back;
	
	private Image arrow,pieceDisposer;

	private int previousCont = 0;
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
		
		if(Render.DraftController==1) {
			title = new Label("P", Render.skin, "TitleStyle");
		}else if(Render.DraftController==2) {
			title = new Label("P2", Render.skin, "TitleStyle");
		}
		
		
		
		configReader = new LectorLineas("files/config.txt"); // Lector del txt configuracion para sacar el idioma
		languageReader = new LectorLineas("files/lang/" + configReader.leerLinea(Settings.language) + "Draft-Game.txt");

		info = new PieceInfo();
		info.setPosition(1280, 40);
		info.addAction(Actions.moveTo(750, 40, 0.5f));

		// -------------------------------
		stage.addActor(Render.menuBG);
		stage.addActor(info);
		stage.addActor(title);
		
		title.addAction(Actions.moveTo(550, 600));
		
		pieceDisposer = new Image(Render.app.getManager().get(Resources.PIECE_DISPOSER_PATH,Texture.class));
		pieceDisposer.setSize(150, 700);
		pieceDisposer.setPosition(-114, 20);
		stage.addActor(pieceDisposer);

		initPieceClasses();
		initTileButtons();
		initButtons();
		initDraft();
	}

	private void initTileButtons() {
		tile1 = new TileButton(325f, 720f, 168f);
		tile2 = new TileButton(325f, -168f, 168f);
		
		tile1.addAction(Actions.moveTo(325f, 450f, 0.5f));
		tile2.addAction(Actions.moveTo(325f, 150f, 0.5f));

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
		queens.add(Resources.WITCH_PATH);

		Collections.shuffle(queens);

		kings.add(Resources.KING_PATH);
		kings.add(Resources.MIDAS_PATH);
		kings.add(Resources.MAGE_PATH);
		Collections.shuffle(kings);
	}

	private void initDraft() {

		// Podemos hacer que al principio tengan la imagen de la primera pieza de cada
		// tipo, de las piezas básicas o una nueva que sea una silueta o con una
		// interrogacion o algo
		pieces = new ArrayList<>();
		draft = new LinkedHashMap<String, String>();
		draft.put("Pawn", Resources.PAWN_PATH);
		draft.put("Knight", Resources.KNIGHT_PATH);
		draft.put("Rook", Resources.ROOK_PATH);
		draft.put("Bishop", Resources.BISHOP_PATH);
		draft.put("Queen", Resources.QUEEN_PATH);
		draft.put("King", Resources.KING_PATH);
		
		

		int i = 0;
		for (String key : draft.keySet()) {
			Image piece = Parser.getImageFromPath(draft.get(key));
			piece.setName(key);
			piece.setSize(84, 84);
			piece.setPosition(-84, 85 + ((5 - i) * 100));
			piece.addAction(Actions.moveBy(104, 0, 0.5f));

			pieces.add(piece);
			stage.addActor(piece);

			i++;
		}
		
		pieceDisposer.addAction(Actions.moveBy(104, 0, 0.5f));
		
		Action action = new Action() {
			public boolean act(float delta) {
				pieces.get(cont).addAction(Actions.moveBy(20, 0, 0.3f));
				return true;
			}
		};

		stage.addAction(Actions.sequence(Actions.delay(0.6f), action));

		arrow = new Image(Render.app.getManager().get(Resources.ARROW_PATH, Texture.class));
		arrow.setSize(100, 100);
		stage.addActor(arrow);
		changePiece();
	}
	
	private void endDraft() {
		for(Image piece: pieces) {
			piece.addAction(Actions.moveBy(-150, 0, 0.5f));
		}
		pieceDisposer.addAction(Actions.moveBy(-150, 0, 0.5f));
		
		title.addAction(Actions.moveTo(325f, 720f, 0.5f));
		tile1.addAction(Actions.moveTo(325f, 720f, 0.5f));
		tile2.addAction(Actions.moveTo(325f, -168f, 0.5f));
		next.addAction(Actions.moveTo(480, -50, 0.5f));
		back.addAction(Actions.moveTo(175, -50, 0.5f));
		info.addAction(Actions.moveTo(1280, 40, 0.5f));
	}

	private void updateDraft() {
		String key = (String) draft.keySet().toArray()[cont];
		draft.put(key, currentPieceSelection);
		pieces.get(cont).setImage(currentPieceSelection);
		
	}

	/**
	 * Se inicializan los botones, el boton next se convierte en finalizar y dependiendo del modo de juego se realiza otro draft y se empieza una partida en local
	 * o se envian y reciben los drafts con otro jugador
	 */
	private void initButtons() {
		// Botones con nombre momentaneos, se cambiara y se a�adira a los ficheros de
		// idiomas

		next = new TextButton(languageReader.leerLinea(2), "SingleClickStyle");
		next.setPosition(480, -50);
		next.addAction(Actions.moveTo(480, 50, 0.5f));
		next.addCaptureListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if (cont < 5) {
					previousCont=cont;
					cont++;
					changePiece();
				} else {
					if (Render.DraftController == 1) {
						Action draftAction = new Action() {
							public boolean act(float delta) {
								Render.hosting = true;
								Render.player1Draft.addAll(draft.values());
								Render.DraftController++;
								Render.app.setScreen(new DraftScreen());								
								return true;
							}
						};
						endDraft();
						stage.addAction(Actions.delay(1f));
						stage.addAction(Actions.after(draftAction));
					} else if (Render.DraftController == 2) {
						Action draftAction = new Action() {
							public boolean act(float delta) {
								Render.player2Draft.addAll(draft.values());
								Render.GameScreen = new GameScreen();								
								Render.app.setScreen(Render.GameScreen);
								return true;
							}
						};
						endDraft();
						stage.addAction(Actions.delay(1f));
						stage.addAction(Actions.after(draftAction));

					} else if (Render.DraftController == 3) {
						Action draftAction = new Action() {
							public boolean act(float delta) {
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
								return true;
							}
						};
						endDraft();
						stage.addAction(Actions.delay(1f));
						stage.addAction(Actions.after(draftAction));
					}

				}

				if (cont == 5) {
					next.setText(languageReader.leerLinea(3));
				}
				return true;
			}
		});

		back = new TextButton(languageReader.leerLinea(1), "SingleClickStyle");
		back.setPosition(175, -50);
		back.addAction(Actions.moveTo(175, 50, 0.5f));
		back.addCaptureListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if (cont > 0) {
					previousCont=cont;
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
		arrow.setPosition(90, 70 + 100 * (5 - cont));

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
		pieces.get(previousCont).addAction(Actions.moveBy(-20f, 0, 0.3f));
		pieces.get(cont).addAction(Actions.moveBy(20f, 0, 0.3f));
		
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
