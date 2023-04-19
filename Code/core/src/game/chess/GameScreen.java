package game.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.*;

import elements.Background;
import elements.Board;
import elements.DropDownMenu;
import elements.Graveyard;
import elements.Piece;
import elements.Tile;
import elements.Timer;
import elements.MatchResults;
import elements.pieces.*;
import utils.Parser;
import utils.Render;
import utils.Resources;

import java.util.ArrayList;

public class GameScreen extends AbstractScreen {
	public static Stage stage;
	Background background;
	public static Board board;
	
	
	//Control selección de piezas
	private boolean isPieceSelected = false;
	private ArrayList<Vector2> currentTile_validMovements = new ArrayList<>();
	private int current_x, current_y;
	private Tile currentTile = null;
	private static Tile nextTile = null;
	
	//Timer para las partidas 
	private Timer TimerW,TimerB;
	
	//----------------------------
	//CONTROL JAQUE
	//----------------------------
	
	//Guardamos en todo momento donde esta el rey blanco y el negro (ÚTIL)
	//No sé si habrá que controlar que pieza está poniendo el jaque y guardarla, posiciones interesantes a guardar en caso de jaque...
	public static Vector2 whiteKing = new Vector2(5,1), blackKing = new Vector2(5,8);
	
	//Saber para cada rey si está en jaque o no
	private static boolean whiteCheck;
	private static boolean blackCheck;
	private static boolean whiteCheckMate;
	private static boolean blackCheckMate;
	public static ArrayList<Piece> whitePieces;
	public static ArrayList<Piece> blackPieces;
	
	//----------------------------
	//FIN CONTROL JAQUE
	//----------------------------
	
	
	//Control captura al paso
	private Piece lastPawn;
	
	//Control turno
	private boolean PLAYER;
	
	//Control promocion
	public static boolean promoting = false;

	//Cementerios
	public static Graveyard graveyardWhite;
	public static Graveyard graveyardBlack;
	
	//Pantalla ganador
	private static boolean showPopup;
	private static MatchResults results;

	//Modo depuracion
	private boolean debugMode = false;

	//Control bombarderos
	private boolean WHasBomb = true, BHasBomb = false; //Guardan si tienen bombarderos, se actualiza en select

	@Override
	public void show() {
		stage = new Stage(new FitViewport(1280, 720));
		stage.clear();
		
		
		
		Gdx.input.setInputProcessor(stage);
		
		Render.hosting=true;
		
		PLAYER = true;
		
		whiteCheck =false;
		blackCheck =false;
		whiteCheckMate = false;
		blackCheckMate=false;

		board = new Board();
		whitePieces = new ArrayList<>();
		blackPieces = new ArrayList<>();
		graveyardWhite = new Graveyard(21,21);
		graveyardBlack = new Graveyard(Gdx.graphics.getWidth()-63,21);
		background = new Background();
		TimerB = new Timer(300,1100,650,"NEGRO");
		TimerW = new Timer(300,80,150,"BLANCO");
		background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		
		// Crear mensaje emergente tras terminar partida
		showPopup=false;
		results = new MatchResults();
		results.Hide();
        
        
        //-------------------------------
        stage.addActor(results);
		stage.addActor(background);
		stage.addActor(board);
		stage.addActor(graveyardWhite);
		stage.addActor(graveyardBlack);
		stage.addActor(TimerB);
		stage.addActor(TimerW);
		
		testDrafts();
		
		placeWhites(Render.player1Draft);
		placeBlacks(Render.player2Draft);
		addPiecesToStage(whitePieces);
		addPiecesToStage(blackPieces);
		addTilesToStage();
	}

	@Override
	public void render(float delta) {
		Render.clearScreen();
		
		if (showPopup) {
			Gdx.input.setInputProcessor(Render.inputs);
			results.Show();
			results.toFront();
			results.render();
		}else {
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
        }else if(Gdx.input.isKeyJustPressed(Keys.R)) {
			Render.app.setScreen(new GameScreen());
			debugMode = false;
			//G para modo debug, permite hacer movimientos ilegales
		} else if (Gdx.input.isKeyJustPressed(Keys.G)){
			debugMode = !debugMode;
			System.out.println("Debug mode toggled");
			
		}else if(!whiteCheckMate && !blackCheckMate) {
			
            if (!isPieceSelected) {
                currentTile = tile;
                
                current_x = (int)currentTile.getPos().x;
                current_y = (int)currentTile .getPos().y;
                         
                select(currentTile);
                   
            } else if (isPieceSelected) {

                nextTile = tile;
                
                int next_x = (int)nextTile.getPos().x;
                int next_y = (int)nextTile.getPos().y;
                

                if (nextTile.getPiece() != null && currentTile.getPiece().sameColor(nextTile.getPiece())) {
                    currentTile = nextTile;
                    current_x = next_x;
                    current_y = next_y;

                    lowlight();
                    select(currentTile);
            
                } else {
                	lowlight();
					moveCurrentPieceTo((int)nextTile.getPos().x,(int)nextTile.getPos().y);
					
                    isPieceSelected = false;
                    }       
                }
            }
        }


	/**
	 * Comprueba si hay timers a 0
	 */
	private void checkTimerEnd() {
		if(TimerB.getTimeRemaining()==0) {
			results.setWinner("BLANCO");
			showPopup=true;
		}
		else if(TimerW.getTimeRemaining()==0) {
			results.setWinner("NEGRO");
			showPopup=true;
		}
	}
	
	/**
	 * Actualiza los Timers en función del jugador actual
	 */
	private void timersRender() {
		if(PLAYER) 
			TimerW.render();
		else 
			TimerB.render();
	}
	
	/**
	 * Resalta las casillas contenidas en el array de movimientos válidos.
	 * @param b 
	 */
	private void highlight(Boolean b) {
		for (Vector2 vector : currentTile_validMovements) {
			Tile tile = board.getTile(vector.x, vector.y);
			//En caso de que haya una pieza enemiga la resalta en rojo
			if(tile.getPiece()!=null && tile.getPiece().color()!=b) {
				tile.attacked=true;
			}else {
				tile.highlight = true;
			}
		}
	}
	
	/**
	 * Elimina el resaltado de las casillas contenidas en el array de movimientos válidos.
	 */
	private void lowlight() {
		
		for (Vector2 vector : currentTile_validMovements) {
			Tile tile = board.getTile(vector.x, vector.y);
			if(tile.getPiece()!=null) {
				tile.attacked=false;
			}else {
				tile.highlight = false;
			}
		}
	}

	
	/**
	 * Selecciona la casilla pasada como parámetro, es decir, si tiene una pieza calcula los posibles movimientos y los resalta.
	 * <p>No se usa el parámetro, se usa currentTile</p>
	 * @param tile
	 */
	private void select(Tile tile) { //Me gustaría cambiar currentTile, pero se usan sus movimientos válidos
		if (currentTile.getPiece() != null && currentTile.getPiece().color()==PLAYER) {

			currentTile_validMovements = (currentTile.getPiece().getValidMovements());

			removeRareCasesIDidntFixProperlySoNowWeDoItAtTheLastSecond();

			highlight(currentTile.getPiece().color());

			System.out.println(currentTile_validMovements.toString());
			isPieceSelected = true;
		}
	}

	/**
	 * Hace lo que dice el nombre
	 * <p>Se llama así porque es demasiado cierto</p>
	 * <p>Ignorad que ambos métodos son míos</p>
	 */
	private void removeRareCasesIDidntFixProperlySoNowWeDoItAtTheLastSecond() {
		castleCancel();

		dontWalkIntoBomberCheck();
		
		dontIgnoreBomberCheck();
	}

	private void dontIgnoreBomberCheck() {
	}

	/**
	 * Elimina los movimientos que permitan a un bombardero enemigo explotar al rey
	 */
	private void dontWalkIntoBomberCheck() {
		Bomber[] enemigos = new Bomber[2]; //enemigos guarda los bombarderos enemigos
		if (PLAYER && BHasBomb || !PLAYER && WHasBomb){ //Si el enemigo tiene bombarderos
			enemigos = getBombers();
		}
		for (int i = 0; i < enemigos.length && enemigos[i] != null && bomberInRangeOfKing(enemigos[i]); i++) {
			float x = enemigos[i].getPos().x,y = enemigos[i].getPos().y;
			if (currentTile.piece instanceof King && bomberCanExplode(enemigos[i])){ //Si puede explotar el rey no puede ir al radio de explosión
				for (int j = -1; j <= 1; j += 2) {
					for (int k = 1; k >= -1; k -= 2) {
						currentTile_validMovements.remove(new Vector2(x +j,y + k));
					}
				}
			}
			ArrayList<Vector2> movimientos = enemigos[i].getValidMovements();
			for (Vector2 v :
					movimientos) {
				currentTile_validMovements.remove(v); //Elimina los movimientos donde puede ser capturado por el bombardero
			}
		}
	}

	/**
	 * Devuelve los bombarderos del tablero en un array
	 */
	private Bomber[] getBombers() {
		Bomber[] enemigos = new Bomber[2];
		int num = 0;
		for (int i = 0; i < Board.board.length; i++) { //Busca los bombarderos enemigos
			for (int j = 0; j < Board.board.length; j++) {
				if (Board.board[i][j].piece instanceof Bomber){
					Bomber au = (Bomber) Board.board[i][j].piece;
					if (au.color() != PLAYER){
						enemigos[num] = au;
						num++;
					}
				}
			}
		}
		if (num == 0){ //Si el enemigo no tiene bombarderos se guarda para no comprobar sabiendo que no hay
			if (PLAYER){
				BHasBomb = false;
			} else {
				WHasBomb = false;
			}
		}
		return enemigos;
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

		if(nextTile.getPiece().color()) {
			for(Piece piece: whitePieces) {
				if (piece.getValidMovements().contains(blackKing) || piece instanceof Bomber && bomberAttacksKing((Bomber) piece)) {
					blackCheck = true;
					board.getTile(blackKing.x, blackKing.y).attacked = true;

					System.out.println("REY NEGRO EN JAQUE");
				}
			}
			
		}else if(!nextTile.getPiece().color()){
			for(Piece piece: blackPieces) {
				if(piece.getValidMovements().contains(whiteKing) || piece instanceof Bomber && bomberAttacksKing((Bomber) piece)) {
					whiteCheck =true;
					board.getTile(whiteKing.x, whiteKing.y).attacked = true;
					
					System.out.println("REY BLANCO EN JAQUE");
				}
			}
		}
		return isCheck();
	}



	/**
	 * 
	 * @param check
	 * @param pieces
	 * @return true si el color que está en mate no tiene movimientos disponibles que hagan que el rey esté a salvo.
	 */
	private static boolean isCheckMate(boolean check, ArrayList<Piece> pieces) {
		boolean isCheckMate = false;
		if(check) {
			ArrayList<Vector2> validMovements = new ArrayList<>();
			for(Piece piece: pieces) {
				validMovements.addAll(piece.getValidMovements());
			}
			System.out.println("LISTA DE MOVIMIMIENTOS LEGALES -> " + validMovements);
			if(validMovements.isEmpty()) {
				isCheckMate=true;
			}
		}
		return isCheckMate;
	}
	/**
	 * Elimina los jaques a cualquier rey ya que si estaba en jaque ya que si has podido hacer un movimiento, lo has puesto a salvo, si no se pudiera seria jaque mate
	 */
	private void resetMate() {
		blackCheck =false;
        whiteCheck =false;
        board.getTile(blackKing.x,blackKing.y ).attacked = false;
        board.getTile(whiteKing.x, whiteKing.y).attacked = false;
	}

	public static void mateControl(float next_x, float next_y) {
		//updateCheck devuelve isCheck al final para no tener que llamarlo
		if(updateCheck()) {
        	whiteCheckMate = isCheckMate(whiteCheck, whitePieces);
        	blackCheckMate = isCheckMate(blackCheck, blackPieces);
        	if(whiteCheckMate) {
        		System.out.println("JAQUE MATE AL REY BLANCO");
        		results.setWinner("NEGRO");
    			showPopup=true;
        	}else if(blackCheckMate) {
        		System.out.println("JAQUE MATE AL REY NEGRO");
        		results.setWinner("BLANCO");
    			showPopup=true;
        	}
        }
	}

	
	/**
	 * Mueve la pieza que está en currentTile a la casilla con coordenadas (next_x, next_y), comprueba los casos de movimientos especiales
	 * @param next_x
	 * @param next_y
	 */
	private void moveCurrentPieceTo(int next_x, int next_y) {
        if (currentTile_validMovements.contains(new Vector2(next_x, next_y)) || debugMode) {
        	
        	//Si se da el caso bomba no se mueve ninguna pieza por lo que no se comprueba nada simplemente explota
        	if(checkBomber()) {
            	explosion(current_x,current_y);
            }else {
            	
            	checkCastling(next_x);

                currentTile.move(next_x, next_y);

                resetMate();
                
                if(nextTile.getPiece() instanceof Guardian) {                	
                	checkGuardian(next_y);
                }
                	
                
            	
                
                if(nextTile.getPiece() instanceof Pawn || nextTile.getPiece() instanceof Lancer || nextTile.getPiece() instanceof Guardian) {
                	checkPassant(next_x, next_y);    
            		
            		checkPromotion(next_x, next_y);
                }else if (lastPawn != null){ 
        			lastPawn.isPassantable = false; 
        		}
        		
                mateControl(next_x, next_y);
     			stalemateControl();
            }
			
            changeTurn();

        }
    }

	/**
	 * Comprueba si el bombardero piece ataca al rey opuesto
	 * <p>Ataca si está adyacente o cuando hay una pieza enemiga adyacente y el rey está en su radio de explosión
	 */
	private static boolean bomberAttacksKing(Bomber bomb) {
		boolean res = false,puedeAtacar = false;

		ArrayList<Vector2> movimientos = bomb.getValidMovements();
		if (!movimientos.isEmpty()) { //Si no se puede mover no puede atacar
			if (bomb.color()) {
				res = movimientos.contains(blackKing);
			} else {
				res = movimientos.contains(whiteKing);
			}
			if (!res){ //Si no está adyacente
				Piece pp;
				for (Vector2 v :
						movimientos) {
					pp = board.getTile(v.x,v.y).piece;
					if (pp != null && pp.color() != bomb.color()){
						puedeAtacar = true;
					}
				}
				if (puedeAtacar){ //Tengo que mirar si estos statics hacen falta porque no ayudan en nada, tengo que copiar el código
					float x =  bomb.getPos().x,y = bomb.getPos().y;
					for(float i = x-1; i <= x+1 &&!res; i++) { //Mira si el rey está en su rango de explosión
						for(float j = y-1; j <= y+1 && !res; j++) {
							if(i>=1 && i<=8 && j>=1 && j<=8) {
								if (bomb.color()){
									res = board.getTile(i,j).equals(board.getTile(blackKing.x,blackKing.y));
								} else {
									res = board.getTile(i,j).equals(board.getTile(whiteKing.x,whiteKing.y));
								}

							}
						}
					}
				}
			}
		}
		return res;
	}

	private boolean bomberInRangeOfKing(Bomber bomb){
		boolean res = false;
		float x =  bomb.getPos().x,y = bomb.getPos().y;
		for(float i = x-1; i <= x+1 &&!res; i++) { //Mira si el rey está en su rango de explosión
			for(float j = y-1; j <= y+1 && !res; j++) {
				if(i>=1 && i<=8 && j>=1 && j<=8) {
					if (bomb.color()){
						res = board.getTile(i,j).equals(board.getTile(blackKing.x,blackKing.y));
					} else {
						res = board.getTile(i,j).equals(board.getTile(whiteKing.x,whiteKing.y));
					}

				}
			}
		}
		return res;
	}

	private boolean bomberCanExplode(Bomber bomb){
		boolean res = false;
		for (Vector2 v:
			 bomb.getValidMovements()) {
			Piece au = board.getTile(v.x,v.y).piece;
			if (au != null && au.color() != bomb.color()){
				res = true;
			}
		}
		return res;
	}

	private void checkGuardian(int next_y) {
		if(current_y - 1 == next_y && nextTile.getPiece().color()){
			nextTile.getPiece().backed=true;
		}else if(current_y + 1 == next_y && !nextTile.getPiece().color()) {
			nextTile.getPiece().backed=true;
	    }else {
	    	nextTile.getPiece().backed=false;
	    }
	}

	private void explosion(int x, int y) {
		for(int i=x-1;i<=x+1;i++) {
			for(int j=y-1;j<=y+1;j++) {
				if(correctTile(i,j) && board.getTile(i, j).piece!=null) {
					board.getTile(i, j).sendPieceToGraveyard();
				}
			}
		}
	}

	private boolean correctTile(int i, int j) {
		return i>=1 && i<=8 && j>=1 && j<=8;
	}

	private boolean checkBomber() {
		//Comprobar redundancias
		return currentTile.piece instanceof Bomber && nextTile.piece!= null;
	}

	/**
	 * Lleva a cabo los cálculos necesarios para saber si se ha llegado a un empate sabiendo que no es jaque mate
	 * <p>Se llega a empate si no hay movimientos legales pero no es jaque mate</p>
	 */
	private void stalemateControl() {
		if (!isCheck()){
			if (!PLAYER && !hasMoves(whitePieces)){
				System.out.println("Las negras han empatado");
				results.setDraw();
				showPopup = true;

			} else if (PLAYER && !hasMoves(blackPieces)){
				System.out.println("Las blancas han empatado");
				results.setDraw();
				showPopup = true;

			}
		}
	}

	/**
	 * Devuelve {@code true} si con el conjunto de piezas {@code pisses} no se pueden hacer movimientos, si tiene devuelve {@code false}
	 * @param pisses las piezas que se usan para la comprobación
	 */
	private boolean hasMoves(ArrayList<Piece> pisses) {
		boolean tieneMov = false;
		for (int i = 0; i < pisses.size() && !tieneMov; i++) { //Si una pieza tiene movimientos se sale
			tieneMov = !pisses.get(i).getValidMovements().isEmpty();
		}
		return tieneMov;
	}

	/**
	 * Comprueba si el último movimiento era un enroque para mover también la torre que corresponda
	 * @param next_x
	 */
	private void checkCastling(float next_x) {
		if(currentTile.getPiece() instanceof King && next_x==current_x-2) {
			board.getTile(1, current_y).move(current_x-1,current_y);
			
		}else if(currentTile.getPiece() instanceof King && next_x==current_x+2){
			board.getTile(8, current_y).move(current_x+1,current_y);
		}

	}

	/**
	 * Elimina el enroque si se ha seleccionado el rey y está en jaque
	 */
	private void castleCancel(){
		if (isCheck() && currentTile.getPiece() instanceof King){
			//Si es jaque se le quita al rey su habilidad de enrocar por la fuerza
			currentTile_validMovements.remove(new Vector2(current_x - 2, current_y));
			currentTile_validMovements.remove(new Vector2(current_x + 2, current_y));
		}
	}
	
	/**
	 * Comprueba si el ultimo movimiento permite relizar una promoción (peon llega al lado contrario)
	 * @param next_x
	 * @param next_y
	 */
	private void checkPromotion(float next_x, float next_y) {
		if ((next_y == 8.0 && nextTile.getPiece().color()) || (next_y == 1.0 && !nextTile.getPiece().color())) {//Implementar que se pueda escoger entre todas las piezas posibles
			System.out.println("Ascensión");
			promoting = true;
			DropDownMenu menu = new DropDownMenu(nextTile);
			stage.addActor(menu);
		}
	}
	/**
	 * Comprueba si se ha realizado una captura al paso
	 * @param next_x
	 * @param next_y
	 */
	private void checkPassant(float next_x, float next_y) {
		if (isEnPassant(next_x, next_y, nextTile.getPiece())){
			board.getTile(lastPawn.getPos().x,lastPawn.getPos().y).sendPieceToGraveyard();
		}
		updateLastPawn(next_x, next_y);  
	}
	
	/**
	 * 
	 * @param next_x
	 * @param next_y
	 * @return true si se ha realizado una captura al paso, false si no se ha hecho
	 */
	
	private boolean isEnPassant(float next_x, float next_y, Piece piece) {
		boolean res = false;
		if (piece instanceof Pawn &&next_y == current_y + (piece.color()?1:-1) && (next_x == current_x + 1 || next_x == current_x -1)){ //Si avanza a una casilla diagonal sin pieza, está tomando al paso
			if (board.getTile(next_x, current_y).getPiece() instanceof Pawn){
				res = lastPawn.isPassantable; //Es en passant si se le puede hacer al peón objetivo
			}
		}else if (piece instanceof Lancer && next_y == current_y + (piece.color()?1:-1) && (next_x == current_x)){ //Si avanza a una casilla en linea recta sin pieza, está tomando al paso
			if (!res && board.getTile(next_x +1 , current_y).getPiece() instanceof Lancer){
				res = lastPawn.isPassantable; //Es en passant si se le puede hacer al peón objetivo
			}
			if(!res && board.getTile(next_x - 1, current_y).getPiece() instanceof Lancer){
				res = lastPawn.isPassantable; //Es en passant si se le puede hacer al peón objetivo
			}
		}
		return res;
	}
	
	/**
	 * actualiza el ultimo peon que se ha movido
	 * @param next_x
	 * @param next_y
	 */
	private void updateLastPawn(float next_x, float next_y) {
		if (lastPawn != null){ 
			lastPawn.isPassantable = false; 
		}
		if (next_y == current_y + 2 || next_y == current_y - 2){	
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
	
	
	//-------------- METODO TEMPORAL PARA PROBAR LOS CONSTRUCTORES (el draft se rellena en DraftScreen) -------------------
	public void testDrafts() {
		
		//Para probar la pieza random
		Render.player1Draft.add(Resources.PAWN_PATH);
		Render.player1Draft.add(Resources.KNIGHT_PATH);
		Render.player1Draft.add(Resources.ROOK_PATH);
		Render.player1Draft.add(Resources.RND_PATH);
		Render.player1Draft.add(Resources.QUEEN_PATH);
		Render.player1Draft.add(Resources.KING_PATH);
		
		Render.player2Draft.add(Resources.PAWN_PATH);
		Render.player2Draft.add(Resources.KNIGHT_PATH);
		Render.player2Draft.add(Resources.ROOK_PATH);
		Render.player2Draft.add(Resources.RND_PATH);
		Render.player2Draft.add(Resources.QUEEN_PATH);
		Render.player2Draft.add(Resources.KING_PATH);
	}
	
	public void placeWhites(ArrayList<String> player1Draft) {
		Piece piece;
		for (int i = 1; i < 9; i++) {
			piece =  Parser.getPieceFromPath(player1Draft.get(0), true, i, 2,board);
			whitePieces.add(piece);
			board.getTile(i, 2).setPiece(piece);
		}
		for (int i = 1; i < 9; i++) {
			if (i == 1 || i == 8) {
				
				piece = Parser.getPieceFromPath(player1Draft.get(2), true, i, 1,board);
				whitePieces.add(piece);
				board.getTile(i, 1).setPiece(piece);
			}
			if (i == 2 || i == 7) {
				
				piece = Parser.getPieceFromPath(player1Draft.get(1), true, i, 1,board);
				whitePieces.add(piece);
				board.getTile(i, 1).setPiece(piece);
			}
			if (i == 3 || i == 6) {
				
				piece = Parser.getPieceFromPath(player1Draft.get(3), true, i, 1,board);
				whitePieces.add(piece);
				board.getTile(i, 1).setPiece(piece);
			}
			if (i == 4) {
				
				piece = Parser.getPieceFromPath(player1Draft.get(4), true, i, 1,board);
				whitePieces.add(piece);
				board.getTile(i, 1).setPiece(piece);
			}
			if (i == 5) {
				
				piece = Parser.getPieceFromPath(player1Draft.get(5), true, i, 1,board);
				whitePieces.add(piece);
				board.getTile(i, 1).setPiece(piece);
			}
		}
	}
	
	
	private void placeBlacks(ArrayList<String> player2Draft) {
		Piece piece;
		for (int i = 1; i < 9; i++) {
			piece =  Parser.getPieceFromPath(player2Draft.get(0), false, i, 7,board);
			blackPieces.add(piece);
			board.getTile(i, 7).setPiece(piece);
		}
		for (int i = 1; i < 9; i++) {
			if (i == 1 || i == 8) {
				
				piece = Parser.getPieceFromPath(player2Draft.get(2), false, i, 8,board);
				blackPieces.add(piece);
				board.getTile(i, 8).setPiece(piece);
			}
			if (i == 2 || i == 7) {
				
				piece = Parser.getPieceFromPath(player2Draft.get(1), false, i, 8,board);
				blackPieces.add(piece);
				board.getTile(i, 8).setPiece(piece);
			}
			if (i == 3 || i == 6) {
				
				piece = Parser.getPieceFromPath(player2Draft.get(3), false, i, 8,board);
				blackPieces.add(piece);
				board.getTile(i, 8).setPiece(piece);
			}
			if (i == 4) {
				
				piece = Parser.getPieceFromPath(player2Draft.get(4), false, i, 8,board);
				blackPieces.add(piece);
				board.getTile(i, 8).setPiece(piece);
			}
			if (i == 5) {
				piece = Parser.getPieceFromPath(player2Draft.get(5), false, i, 8,board);
				blackPieces.add(piece);
				board.getTile(i, 8).setPiece(piece);
			}
		}
	}
	
	public void addPiecesToStage(ArrayList<Piece> pieces) {
		for(Piece piece:pieces) {
			stage.addActor(piece);
		}
	}
	
	public void addTilesToStage() {
		for(int i=1; i<=8; i++) {
			for(int j=1; j<=8; j++) {
				final Tile tile = board.getTile(i, j);
				tile.addCaptureListener(new InputListener() { 
					@Override
					    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					
						if(!promoting) {							
							update(tile);
						}
						
					    return true;
					    }
					} );
				stage.addActor(tile);
			}
		}
	}

}
