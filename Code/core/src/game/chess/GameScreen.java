package game.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import elements.*;
import elements.pieces.*;
import interaccionFichero.LectorLineas;
import utils.*;

import java.util.ArrayList;

public class GameScreen extends AbstractScreen {
	public static Stage stage;
	Background background;
	public static Board board;
	

	// Control selección de piezas
	protected boolean isPieceSelected = false;
	protected ArrayList<Vector2> currentTile_validMovements = new ArrayList<>();
	private int current_x, current_y;
	public Tile currentTile = null;
	public static Tile nextTile = null;

	//UI partida
	protected Table table;
	protected TextButton surrender;
	public TextButton draw;
	public DrawBox dbox;
	protected LectorLineas languageReader, configReader;
	private Timer TimerW, TimerB;

	// ----------------------------
	// CONTROL JAQUE
	// ----------------------------
	public static Vector2 whiteKing = new Vector2(5, 1);
	public static Vector2 blackKing = new Vector2(5, 8);
	private static boolean whiteCheck;
	private static boolean blackCheck;
	protected static boolean whiteCheckMate;
	public static boolean blackCheckMate;
	public static ArrayList<Piece> whitePieces;
	public static ArrayList<Piece> blackPieces;
	// ----------------------------
	// FIN CONTROL JAQUE
	// ----------------------------

	// Control captura al paso
	private Piece lastPawn;

	// Control turno
	public boolean PLAYER;

	//CONTROL MOVIMIENTO (ONLINE)
	public boolean moved =false;

	// Control promocion
	public static boolean promoting = false;

	// Cementerios
	public static Graveyard graveyardWhite;
	public static Graveyard graveyardBlack;

	// Pantalla ganador
	public static boolean showPopup;
	public static MatchResults results;

	// Modo depuracion
	private boolean debugMode = false;

	@Override
	public void show() {
		stage = new Stage(new FitViewport(1280, 720));
		stage.addActor(Render.menuBG);
		
		table = new Table();
		table.setFillParent(true);
		
		configReader = new LectorLineas("files/config.txt"); // Lector del txt configuracion para sacar el idioma
		languageReader = new LectorLineas("files/lang/" + configReader.leerLinea(Settings.language) + "Draft-Game.txt");

		Gdx.input.setInputProcessor(stage);

		PLAYER = true;
		

		whiteCheck = false;
		blackCheck = false;
		whiteCheckMate = false;
		blackCheckMate = false;

		board = new Board();
		board.setPosition(board.getX(), board.getY() + (-84*8));
		board.animationMovement(board.getX(), 24, 0.5f);
		
		
		whitePieces = new ArrayList<>();
		blackPieces = new ArrayList<>();
		graveyardWhite = new Graveyard(21, 21);
		graveyardBlack = new Graveyard(1280 -105, 21);
		

		// Crear mensaje emergente tras terminar partida
		showPopup = false;
		results = new MatchResults(stage);
		results.Hide();

		// -------------------------------
		stage.addActor(results);
		stage.addActor(board);
		stage.addActor(graveyardWhite);
		stage.addActor(graveyardBlack);
		
		dbox =new DrawBox();
		dbox.setVisible(false);
		stage.addActor(dbox);


		createTableElements();
		setupTable();
		if(Render.DraftController != 3)	stage.addActor(table);
		testDrafts();

		placeWhites(Render.player1Draft);
		placeBlacks(Render.player2Draft);
		addPiecesToStage(whitePieces);
		addPiecesToStage(blackPieces);
		addTilesToStage();
		
		System.out.println(whitePieces.toString());
		System.out.println(blackPieces.toString());
	}

	/**
	 * configura la tabla de GameScreen
	 */
	private void setupTable() {
		table.setFillParent(true);
		table.left().pad(50);
    	table.defaults().left().space(40);

		table.add(TimerW).top().left().pad(80).expandX().expandY();
		table.add(TimerB).top().right().pad(80).expandX().expandY();
		table.row();
		table.add(surrender).left().padLeft(70).expandX();
		if(Render.DraftController != 3) table.row();

	}

	/**
	 * crea los elementos de la tabla de GameScreen
	 */
	private void createTableElements() {
		TimerW = new Timer(300, "blanco", Render.skin, "default");
		TimerB = new Timer(300, "negro", Render.skin, "default");
		draw = new TextButton(languageReader.leerLinea(5), "SingleClickStyle");
		surrender = new TextButton(languageReader.leerLinea(4), "SingleClickStyle");
		surrender.getLabel().setFontScale(0.75f);
	}
	
	public void checkSurrender() {
		if(surrender.isPressed()) {
			if (PLAYER) {
				System.out.println("RENDICION BLANCA");
				results.setWinnerSurrender("NEGRO");
				showPopup = true;
				whiteCheckMate=true;
			} else {
				System.out.println("RENDICION NEGRA");
				results.setWinnerSurrender("BLANCO");
				showPopup = true;
				blackCheckMate=true;
			}
		}
	}
	

	@Override
	public void render(float delta){
		Render.clearScreen();
		checkSurrender();
		if (showPopup) {
			//Para que no se pueda interaccionar con nada despues de que se muestre el popup
			surrender.clearListeners();
			//------------------------------------------------------------------------------
			results.Show();
			results.render();
		} else {
			timersRender();
			checkTimerEnd();
		}
		
		stage.draw();
		stage.act();
	}

	public void update(Tile tile) {

		// Escape para volver al menÃº principal (Prueba)
		if (Render.inputs.justPressed(Keys.ESCAPE)) {
			Render.app.setScreen(Render.MAINSCREEN);
			// R para reiniciar la partida (Pruebas) -> no funciona
		} else if (Gdx.input.isKeyJustPressed(Keys.R)) {
			Render.app.setScreen(new GameScreen());
			debugMode = false;
			// G para modo debug, permite hacer movimientos ilegales
		} else if (Gdx.input.isKeyJustPressed(Keys.G)) {
			debugMode = !debugMode;
			System.out.println("Debug mode toggled");

		} else if (!whiteCheckMate && !blackCheckMate) {
			if (!isPieceSelected) {
				currentTile = tile;

				select(currentTile);

			} else {

				nextTile = tile;

				if (nextTile.getPiece() != null && currentTile.getPiece().sameColor(nextTile.getPiece()) && !kiCharge()) {
					currentTile = nextTile;

					lowlight();
					select(currentTile);

				} else {
					lowlight();
					makeMove(currentTile, nextTile);
					isPieceSelected = false;
				}
			}
		}
	}

	/**
	 * Comprueba si hay timers a 0
	 */
	private void checkTimerEnd() {
		if (TimerB.getTimeRemaining() == 0) {
			results.setWinner("BLANCO");
			showPopup = true;
		} else if (TimerW.getTimeRemaining() == 0) {
			results.setWinner("NEGRO");
			showPopup = true;
		}
	}

	/**
	 * Actualiza los Timers en función del jugador actual
	 */
	private void timersRender() {
		if (PLAYER)
			TimerW.render();
		else
			TimerB.render();
	}

	/**
	 * Resalta las casillas contenidas en el array de movimientos válidos.
	 * 
	 * @param color
	 */
	protected void highlight(Boolean color) {
		for (Vector2 vector : currentTile_validMovements) {
			Tile tile = board.getTile(vector.x, vector.y);
			// En caso de que haya una pieza enemiga la resalta en rojo
			if (tile.getPiece() != null && tile.getPiece().color() != color) {
				tile.attacked = true;
			} else {
				tile.highlight = true;
			}
		}
	}

	/**
	 * Elimina el resaltado de las casillas contenidas en el array de movimientos
	 * válidos.
	 */
	protected void lowlight() {

		for (Vector2 vector : currentTile_validMovements) {
			Tile tile = board.getTile(vector.x, vector.y);
			if (tile.getPiece() != null && !vector.equals(currentTile.getPos())) {
				tile.attacked = false;
			} else {
				tile.highlight = false;
			}
			if(tile.getPiece() instanceof Mage) {
				tile.highlight = false;
			}
		}
	}

	/**
	 * Selecciona la casilla pasada como parámetro, es decir, si tiene una pieza
	 * calcula los posibles movimientos y los resalta.
	 * @param tile
	 */
	protected void select(Tile tile) {
		if (tile.getPiece() != null && tile.getPiece().color() == PLAYER) {

			currentTile_validMovements = (tile.getPiece().getValidMovements());

			highlight(tile.getPiece().color());

			System.out.println(currentTile_validMovements.toString());
			isPieceSelected = true;
		}
		
	}

	private boolean kiCharge() {
		return currentTile.piece instanceof Mage && currentTile.equals(nextTile);
	}

	/**
	 * 
	 * @return true si alguno de los reyes está en mate.
	 */
	private static boolean isCheck() {
		return blackCheck || whiteCheck;
	}

	/**
	 * Actualiza los valores de jaque en el tablero
	 */
	private static boolean updateCheck() {
		int i = 0;
		if (nextTile.getPiece().color()) {
			while (i < whitePieces.size()) {
				Piece piece = whitePieces.get(i);
				i++;
				if (piece.getValidMovements().contains(blackKing)) {
					blackCheck = true;
					board.getTile(blackKing.x, blackKing.y).attacked = true;
				}
			}
		} else if (!nextTile.getPiece().color()) {
			while (i < blackPieces.size()) {
				Piece piece = blackPieces.get(i);
				i++;
				if (piece.getValidMovements().contains(whiteKing)) {
					whiteCheck = true;
					board.getTile(whiteKing.x, whiteKing.y).attacked = true;
				}
			}
		}
		return isCheck();
	}

	/**
	 * comprueba si un jaque es jaque mate
	 * @param pieces
	 * @return true si el color que está en mate no tiene movimientos disponibles
	 *         que hagan que el rey esté a salvo.
	 */
	private static boolean isCheckMate(boolean Mate, ArrayList<Piece> pieces) {
		boolean isCheckMate = false;
		if (Mate) {
			ArrayList<Vector2> validMovements = new ArrayList<>();
			int i = 0;
			while (i < pieces.size()) {
				validMovements.addAll(pieces.get(i).getValidMovements());
				i++;
			}

			System.out.println("LISTA DE MOVIMIMIENTOS LEGALES -> " + validMovements);
			if (validMovements.isEmpty()) {
				isCheckMate = true;
			}
		}
		return isCheckMate;
	}

	/**
	 * Elimina los jaques a cualquier rey ya que si estaba en jaque ya que si has
	 * podido hacer un movimiento, lo has puesto a salvo, si no se pudiera seria
	 * jaque mate
	 */
	public void resetMate() {
		blackCheck = false;
		whiteCheck = false;
		board.getTile(blackKing.x, blackKing.y).attacked = false;
		board.getTile(whiteKing.x, whiteKing.y).attacked = false;
	}

	/**
	 * controla el mate al hacer un movimiento 
	 * @param next_x
	 * @param next_y
	 */
	public static void mateControl(float next_x, float next_y) {
		// updateCheck devuelve isCheck al final para no tener que llamarlo
		if (updateCheck()) {
			whiteCheckMate = isCheckMate(whiteCheck, whitePieces);
			blackCheckMate = isCheckMate(blackCheck, blackPieces);
			if (whiteCheckMate) {
				System.out.println("JAQUE MATE AL REY BLANCO");
				results.setWinner("NEGRO");
				showPopup = true;
			} else if (blackCheckMate) {
				System.out.println("JAQUE MATE AL REY NEGRO");
				results.setWinner("BLANCO");
				showPopup = true;
			}
		}
	}

	/**
	 * Mueve la pieza que está en currentTile a la casilla nextTile, comprueba los casos de movimientos especiales
	 *
	 * @param currentTile
	 * @param nextTile
	 */
	public void makeMove(Tile currentTile, Tile nextTile) {
		current_x = (int)currentTile.getPos().x;
		current_y = (int)currentTile.getPos().y;
		if(Render.DraftController == 3){
			this.currentTile = currentTile;
			GameScreen.nextTile = nextTile;
			currentTile_validMovements = (currentTile.getPiece().getValidMovements());
		}

		int next_x = (int)nextTile.getPos().x;
		int next_y = (int)nextTile.getPos().y;
		if (currentTile_validMovements.contains(new Vector2(next_x, next_y)) || debugMode) {
			if (currentTile.getPiece().checkBomber(next_x, next_y)) {
				Bomber b = (Bomber) currentTile.getPiece();
				b.explode();
			} else if (!currentTile.getPiece().checkPaladin(next_x, next_y) && !currentTile.getPiece().checkCatapultAttack(next_x, next_y)) {

				checkMidas();
				checkCastling(next_x);
				updateKiCharge();

				currentTile.move(next_x, next_y);

				resetMate();

				if (nextTile.getPiece() instanceof Guardian) {
					checkGuardian(next_y);
				}

				if (nextTile.getPiece() instanceof Pawn || nextTile.getPiece() instanceof Lancer
						|| nextTile.getPiece() instanceof Guardian) {
					if(lastPawn!=null) checkPassant(next_x, next_y);
					checkPromotion(next_x, next_y);
					updateLastPawn(next_x, next_y);
				} else if (lastPawn != null) {
					lastPawn.isPassantable = false;
				}
				mateControl(next_x, next_y);
				stalemateControl();
			}

			if(Render.DraftController != 3)changeTurn();
			if(Render.hosting == PLAYER) moved = true;
		}
	}

	/**
	 * Actualiza el valor del ki actual del mago si corresponde
	 * <p>Solo se hace la parte de reducir en uno el ki porque el resto se hace en su clase</p>
	 */
	private void updateKiCharge() {
		Piece aux;
		if (PLAYER){ //Se inicializa aux con el rey
			aux = board.getTile(whiteKing.x, whiteKing.y).piece;
		} else {
			aux = board.getTile(blackKing.x, blackKing.y).piece;
		} //Se le quita una carga de ki al mago
		if (aux instanceof Mage){
			aux.kiCharge = Math.max(0,aux.kiCharge-1); //Se baja aunque no sea ki king porque no lo van a usar
			((Mage) aux).updateSprite(aux.kiCharge);
		}
	}
	/**
	 * Controla la propiedad especial del Rey Midas
	 */
	private void checkMidas() {
		if (currentTile.getPiece() instanceof Midas && nextTile.getPiece() != null) {
			currentTile.getPiece().ate++;
		}
	}

	/**
	 * controla el movimiento hacia atras del warden
	 * @param next_y
	 */
	private void checkGuardian(int next_y) {
		if (current_y - 1 == next_y && nextTile.getPiece().color()) {
			nextTile.getPiece().backed = true;
		} else if (current_y + 1 == next_y && !nextTile.getPiece().color()) {
			nextTile.getPiece().backed = true;
		} else {
			nextTile.getPiece().backed = false;
		}
	}

	/**
	 * Lleva a cabo los cálculos necesarios para saber si se ha llegado a un empate
	 * sabiendo que no es jaque mate
	 * <p>
	 * Se llega a empate si no hay movimientos legales pero no es jaque mate
	 * </p>
	 */
	private void stalemateControl() {
		if (!isCheck()) {
			if (!PLAYER && !hasMoves(whitePieces)) {
				System.out.println("Las negras han empatado");
				results.setDraw();
				showPopup = true;

			} else if (PLAYER && !hasMoves(blackPieces)) {
				System.out.println("Las blancas han empatado");
				results.setDraw();
				showPopup = true;
			}
		}
	}

	/**
	 * Devuelve {@code true} si con el conjunto de piezas {@code pieces} no se
	 * pueden hacer movimientos, si tiene devuelve {@code false}
	 * 
	 * @param pieces las piezas que se usan para la comprobación
	 */
	private boolean hasMoves(ArrayList<Piece> pieces) {
		boolean hasMoves = false;
		for (int i = 0; i < pieces.size() && !hasMoves; i++) { // Si una pieza tiene movimientos se sale
			hasMoves = !pieces.get(i).getValidMovements().isEmpty();
		}
		return hasMoves;
	}

	/**
	 * Comprueba si el último movimiento era un enroque para mover también la torre
	 * que corresponda
	 * 
	 * @param next_x
	 */
	private void checkCastling(float next_x) {
		if (currentTile.getPiece() instanceof King && next_x == current_x - 2) {
			board.getTile(1, current_y).move(current_x - 1, current_y);

		} else if (currentTile.getPiece() instanceof King && next_x == current_x + 2) {
			board.getTile(8, current_y).move(current_x + 1, current_y);
		}

	}

	/**
	 * Comprueba si el ultimo movimiento permite relizar una promoción (peon llega
	 * al lado contrario)
	 * 
	 * @param next_x
	 * @param next_y
	 */
	protected void checkPromotion(float next_x, float next_y) {
		if ((next_y == 8.0 && nextTile.getPiece().color()) || (next_y == 1.0 && !nextTile.getPiece().color())) {
			promoting = true;
			DropDownMenu menu = new DropDownMenu(nextTile, PLAYER, false);
			stage.addActor(menu);
		}
	}

	/**
	 * Comprueba si se ha realizado una captura al paso
	 * 
	 * @param next_x
	 * @param next_y
	 */
	private void checkPassant(float next_x, float next_y) {
		if (isEnPassant(next_x, next_y, nextTile.getPiece())) {
			board.getTile(lastPawn.getPos().x, lastPawn.getPos().y).sendPieceToGraveyard();
		}
	}

	/**
	 * 
	 * @param next_x
	 * @param next_y
	 * @return true si se ha realizado una captura al paso, false si no se ha hecho
	 */
	private boolean isEnPassant(float next_x, float next_y, Piece piece) {
		boolean res = false;
		if (piece instanceof Pawn && next_y == current_y + (piece.color() ? 1 : -1)
				&& (next_x == current_x + 1 || next_x == current_x - 1)) { // Si avanza a una casilla diagonal sin
																			// pieza, está tomando al paso
			if (board.getTile(next_x, current_y).getPiece() instanceof Pawn) {
				res = lastPawn.isPassantable; // Es en passant si se le puede hacer al peón objetivo
			}
		}else if (piece instanceof Lancer && next_y == current_y + (piece.color()?1:-1) && (next_x == current_x)){ //Si avanza a una casilla en linea recta sin pieza, está tomando al paso
			if (board.getTile(next_x + 1, current_y) != null && board.getTile(next_x + 1, current_y).getPiece() instanceof Lancer){
				res = lastPawn.isPassantable; //Es en passant si se le puede hacer al peón objetivo
			}
			if(!res && board.getTile(next_x - 1, current_y)!=null && board.getTile(next_x - 1, current_y).getPiece() instanceof Lancer){
				res = lastPawn.isPassantable; //Es en passant si se le puede hacer al peón objetivo
			}
		}
		return res;
	}

	/**
	 * actualiza el ultimo peon que se ha movido
	 * 
	 * @param next_x
	 * @param next_y
	 */
	private void updateLastPawn(float next_x, float next_y) {
		if (lastPawn != null) {
			lastPawn.isPassantable = false;
		}
		if (next_y == current_y + 2 || next_y == current_y - 2) {
			lastPawn = nextTile.getPiece();
			lastPawn.isPassantable = true;
		}
	}

	/**
	 * Alterna el turno entre un jugador y otro
	 */
	private void changeTurn() {
		PLAYER = !PLAYER;
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

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		stage.dispose();

	}

	// -------------- METODO TEMPORAL PARA PROBAR LOS CONSTRUCTORES (el draft se
	// rellena en DraftScreen) -------------------
	public void testDrafts() {

		// Para probar la pieza random
		Render.player1Draft.add(Resources.VALKYRIE_PATH);
		Render.player1Draft.add(Resources.KNIGHT_PATH);
		Render.player1Draft.add(Resources.MINER_PATH);
		Render.player1Draft.add(Resources.PALADIN_PATH);
		Render.player1Draft.add(Resources.WITCH_PATH);
		Render.player1Draft.add(Resources.MAGE_PATH);

		Render.player2Draft.add(Resources.PAWN_PATH);
		Render.player2Draft.add(Resources.RIDER_PATH);
		Render.player2Draft.add(Resources.ROOK_PATH);
		Render.player2Draft.add(Resources.RND_PATH);
		Render.player2Draft.add(Resources.WITCH_PATH);
		Render.player2Draft.add(Resources.MIDAS_PATH);
	}
	
	/**
	 * Coloca las piezas en player1Draft en el lado blanco del tablero
	 * @param player1Draft
	 */
	public void placeWhites(ArrayList<String> player1Draft) {
		Piece piece;
		for (int i = 1; i < 9; i++) {
			piece = Parser.getPieceFromPath(player1Draft.get(0), true, i, 2, board);
			whitePieces.add(piece);
			board.getTile(i, 2).setPiece(piece);
		}
		for (int i = 1; i < 9; i++) {
			if (i == 1 || i == 8) {
				piece = Parser.getPieceFromPath(player1Draft.get(2), true, i, 1, board);
				whitePieces.add(piece);
				board.getTile(i, 1).setPiece(piece);
			}
			if (i == 2 || i == 7) {
				piece = Parser.getPieceFromPath(player1Draft.get(1), true, i, 1, board);
				whitePieces.add(piece);
				board.getTile(i, 1).setPiece(piece);
			}
			if (i == 3 || i == 6) {
				piece = Parser.getPieceFromPath(player1Draft.get(3), true, i, 1, board);
				whitePieces.add(piece);
				board.getTile(i, 1).setPiece(piece);
			}
			if (i == 4) {
				piece = Parser.getPieceFromPath(player1Draft.get(4), true, i, 1, board);
				whitePieces.add(piece);
				board.getTile(i, 1).setPiece(piece);
			}
			if (i == 5) {
				piece = Parser.getPieceFromPath(player1Draft.get(5), true, i, 1, board);
				whitePieces.add(piece);
				board.getTile(i, 1).setPiece(piece);
			}
		}
	}
	/**
	 * Coloca las piezas en player2Draft en el lado negro del tablero
	 * @param player2Draft
	 */
	private void placeBlacks(ArrayList<String> player2Draft) {
		Piece piece;
		for (int i = 1; i < 9; i++) {
			piece = Parser.getPieceFromPath(player2Draft.get(0), false, i, 7, board);
			blackPieces.add(piece);
			board.getTile(i, 7).setPiece(piece);
		}
		for (int i = 1; i < 9; i++) {
			if (i == 1 || i == 8) {
				piece = Parser.getPieceFromPath(player2Draft.get(2), false, i, 8, board);
				blackPieces.add(piece);
				board.getTile(i, 8).setPiece(piece);
			}
			if (i == 2 || i == 7) {
				piece = Parser.getPieceFromPath(player2Draft.get(1), false, i, 8, board);
				blackPieces.add(piece);
				board.getTile(i, 8).setPiece(piece);
			}
			if (i == 3 || i == 6) {
				piece = Parser.getPieceFromPath(player2Draft.get(3), false, i, 8, board);
				blackPieces.add(piece);
				board.getTile(i, 8).setPiece(piece);
			}
			if (i == 4) {
				piece = Parser.getPieceFromPath(player2Draft.get(4), false, i, 8, board);
				blackPieces.add(piece);
				board.getTile(i, 8).setPiece(piece);
			}
			if (i == 5) {
				piece = Parser.getPieceFromPath(player2Draft.get(5), false, i, 8, board);
				blackPieces.add(piece);
				board.getTile(i, 8).setPiece(piece);
			}
		}
	}

	/**
	 * añade el array de piezas pasado como parametro al stage
	 * @param pieces
	 */
	public void addPiecesToStage(ArrayList<Piece> pieces) {
		 SequenceAction sequence = new SequenceAction();
		
		for (final Piece piece : pieces) {
			Action action = new Action(){
				public boolean act(float delta) {
					piece.setSize(0, 0);
					piece.setTouchable(Touchable.disabled);	
					piece.addAction(Actions.sizeTo(84, 84, 1.5f));
					stage.addActor(piece);
					return true;
				}
			};
			sequence.addAction(action);
		}
		stage.addAction(Actions.after(sequence));
	}

	/**
	 * añade todas las casillas del tablero al stage, antes se les añade a cada una un event listener para detectar cuando se interactia con ellas
	 */
	public void addTilesToStage() {
		for (int i = 1; i <= 8; i++) {
			for (int j = 1; j <= 8; j++) {
				final Tile tile = board.getTile(i, j);
				tile.addCaptureListener(new InputListener() {
					@Override
					public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
						if (!promoting) {
							if(tile.getPiece()!=null && tile.getPiece().color() == PLAYER) {
								Sound sound = Render.app.getManager().get(Resources.PIECESELECTION_SOUND, Sound.class);
								sound.play(0.5f);
							}
							update(tile);
						}
						return true;
					}
				});
				stage.addActor(tile);
			}
		}
	}
}
