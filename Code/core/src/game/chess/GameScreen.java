package game.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.viewport.*;

import elements.Background;
import elements.Board;
import elements.DropDownMenu;
import elements.Graveyard;
import elements.Piece;
import elements.Tile;
import elements.Timer;
import elements.matchResults;
import elements.pieces.Bishop;
import elements.pieces.King;
import elements.pieces.Knight;
import elements.pieces.Pawn;
import elements.pieces.Queen;
import elements.pieces.Rook;
import utils.Image;
import utils.Render;
import utils.Resources;
import utils.Text;
import utils.TextButton;

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
	private Tile nextTile = null;
	
	//Timer para las partidas 
	private Timer TimerW,TimerB;
	
	//----------------------------
	//CONTROL JAQUE
	//----------------------------
	
	//Guardamos en todo momento donde esta el rey blanco y el negro (ÚTIL)
	//No sé si habrá que controlar que pieza está poniendo el jaque y guardarla, posiciones interesantes a guardar en caso de jaque...
	public static Vector2 whiteKing = new Vector2(5,1), blackKing = new Vector2(5,8);
	
	//Saber para cada rey si está en jaque o no
	private boolean  whiteMate=false,blackMate=false, whiteCheckMate = false, blackCheckMate=false;
	public static ArrayList<Piece> whitePieces;
	public static ArrayList<Piece> blackPieces;
	
	//----------------------------
	//FIN CONTROL JAQUE
	//----------------------------
	
	
	//Control captura al paso
	private Pawn lastPawn;
	
	//Control turno
	private boolean PLAYER = true;
	
	//Control promocion
	public static boolean promoting = false;

	//Cementerios
	public static Graveyard graveyardWhite;
	public static Graveyard graveyardBlack;
	
	//Pantalla ganador
	private boolean showPopup;
	private matchResults results;

	@Override
	public void show() {
		stage = new Stage(new FitViewport(1280, 720));
		Gdx.input.setInputProcessor(Render.inputs);

		board = new Board();
		whitePieces = new ArrayList<>();
		blackPieces = new ArrayList<>();
		graveyardWhite = new Graveyard(21,21);
		graveyardBlack = new Graveyard(Gdx.graphics.getWidth()-63,21);
		background = new Background();
		TimerB = new Timer(300,1100,650,"NEGRO");
		TimerW = new Timer(300,80,150,"BLANCO");
		background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		placeWhites();
		placeBlacks();
		
		// Crear mensaje emergente tras terminar partida
		showPopup=false;
		results = new matchResults();
		results.Hide();
        
        
        //-------------------------------
        stage.addActor(results);
		stage.addActor(background);
		stage.addActor(board);
		stage.addActor(graveyardWhite);
		stage.addActor(graveyardBlack);
		stage.addActor(TimerB);
		stage.addActor(TimerW);
	}

	@Override
	public void render(float delta) {
		Render.clearScreen();
		
		timersRender();
		checkTimerEnd();
		
		if(!promoting) {	
		update(delta);
		}
		
		
		if (showPopup) {
			results.Show();
			results.toFront();
			results.render();
		}
		
		stage.draw();
		stage.act();
	}

	public void update(float delta) {
        // Escape para volver al menÃº principal (Prueba)
        if (Render.inputs.justPressed(Keys.ESCAPE)) {
        	 Render.app.setScreen(Render.MAINSCREEN);
        // R para reiniciar la partida (Pruebas) -> no funciona
        }else if(Gdx.input.isKeyJustPressed(Keys.R)) {
       	 Render.app.setScreen(new GameScreen());
        }else if(isBoardClicked() && !whiteCheckMate && !blackCheckMate) {

            if (!isPieceSelected) {
                current_x = calculateX();
                current_y = calculateY();
                currentTile = board.getTile(current_x, current_y);
                         
                select(currentTile);
                   
            } else if (isPieceSelected) {

                int next_x = calculateX();
                int next_y = calculateY();
                nextTile = board.getTile(next_x, next_y);
                

                if (nextTile.getPiece() != null && currentTile.getPiece().sameColor(nextTile.getPiece())) {
                    currentTile = nextTile;
                    current_x = next_x;
                    current_y = next_y;

                    lowlight();
                    select(currentTile);
            
                } else {
                	lowlight();
					moveCurrentPieceTo(next_x,next_y);
					
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
			if(tile.piece!=null && tile.piece.color()!=b) {
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
			if(tile.piece!=null) {
				tile.attacked=false;
			}else {
				tile.highlight = false;
			}
		}
	}

	
	/**
	 * Selecciona la casilla pasada como parámetro, es decir, si tiene una pieza calcula los posibles movimientos y los resalta.
	 * @param tile
	 */
	private void select(Tile tile) {
		if (currentTile.getPiece() != null && currentTile.getPiece().color()==PLAYER) {
			
			currentTile_validMovements = (currentTile.getPiece().getValidMovements());

			castleCancel();

			highlight(currentTile.piece.color());
			
			System.out.println(currentTile_validMovements.toString());
			isPieceSelected = true;
		}
	}

	/**
	 * 
	 * @return true si alguno de los reyes está en mate.
	 */
	private boolean isMate() {
		return blackMate || whiteMate;
	}
	
	/**
	 * Comprueba si la pieza en la posición [next_x, next_y] pone en peligro al rey con alguno de sus posibles movimientos.
	 * @param next_x
	 * @param next_y
	 */
	private void isMate(float next_x, float next_y) {
		ArrayList<Vector2> nextTile_validMovements = nextTile.getPiece().getValidMovements();
		if(nextTile.getPiece().color() && nextTile_validMovements.contains(blackKing)) {
			blackMate=true;
			board.getTile(blackKing.x,blackKing.y ).attacked = true;
			
			System.out.println("REY NEGRO EN JAQUE");
			
		}else if(!nextTile.getPiece().color() && nextTile_validMovements.contains(whiteKing)){
			whiteMate=true;
			board.getTile(whiteKing.x, whiteKing.y).attacked = true;
			
			System.out.println("REY BLANCO EN JAQUE");
		}
	}
	
	/**
	 * 
	 * @param Mate
	 * @param pieces
	 * @return true si el color que está en mate no tiene movimientos disponibles que hagan que el rey esté a salvo.
	 */
	private boolean isCheckMate(boolean Mate, ArrayList<Piece> pieces) {
		boolean isCheckMate = false;
		if(Mate) {
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
		blackMate=false;
        whiteMate=false;
        board.getTile(blackKing.x,blackKing.y ).attacked = false;
        board.getTile(whiteKing.x, whiteKing.y).attacked = false;
	}
	
	private void mateControl(float next_x, float next_y) {
		isMate(next_x, next_y);
        
        if(isMate()) {
        	whiteCheckMate = isCheckMate(whiteMate, whitePieces);
        	blackCheckMate = isCheckMate(blackMate, blackPieces);
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
        if (currentTile_validMovements.contains(new Vector2(next_x, next_y))) {

        	checkCastling(next_x);
        	
            currentTile.move(next_x, next_y);
            
            resetMate();
        	 
            if(nextTile.getPiece() instanceof Pawn) {
            	System.out.println("PASSANT");
            	checkPassant(next_x, next_y);
        		
        		updateLastPawn(next_x, next_y);      
        		
        		checkPromotion(next_x, next_y);
            }
			
            mateControl(next_x, next_y);

            changeTurn();

        }
    }

	/**
	 * Comprueba si el ultimo movimiento era un enroque para mover tambien la torre que corresponda
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
		if (isMate() && currentTile.getPiece() instanceof King){
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
		if ((next_y == 8.0 || next_y == 1.0)) {//Implementar que se pueda escoger entre todas las piezas posibles
			System.out.println("A");
			promoting = true;
			DropDownMenu menu = new DropDownMenu(nextTile);
			stage.addActor(menu);
			
			
			/*
			whitePieces.remove(nextTile.getPiece());
			nextTile.setPiece(new Queen(nextTile.getPiece().color(), (int)next_x, (int)next_y));
			whitePieces.add(nextTile.getPiece());
			*/
		}
	}
	/**
	 * Comprueba si se ha realizado una captura al paso
	 * @param next_x
	 * @param next_y
	 */
	private void checkPassant(float next_x, float next_y) {
		if (isEnPassant(next_x, next_y, (Pawn)nextTile.getPiece())){
			board.getTile(next_x,next_y + (nextTile.getPiece().color()?-1:1)).sendPieceToGraveyard();
		}
	}
	
	/**
	 * 
	 * @param next_x
	 * @param next_y
	 * @param pawn
	 * @return true si se ha realizado una captura al paso, false si no se ha hecho
	 */
	private boolean isEnPassant(float next_x, float next_y, Pawn pawn) {
		boolean res = false;
		if (next_y == current_y + (pawn.color()?1:-1) && (next_x == current_x + 1 || next_x == current_x -1)){ //Si avanza a una casilla diagonal sin pieza, está tomando al paso
			if (board.getTile(next_x,current_y).getPiece() instanceof Pawn){
				res = ((Pawn)board.getTile(next_x,current_y).getPiece()).isPassantable; //Es en passant si se le puede hacer al peón objetivo
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
			lastPawn = ((Pawn) nextTile.getPiece());
			lastPawn.isPassantable = true;
		}
	}
	
	/**
	 * Alterna el turno entre un jugador y otro
	 */
	private void changeTurn() {
		PLAYER = !PLAYER;
	}
	private int calculateX() {
		return (int) Math.ceil((Render.inputs.mouseX - board.getTile(1, 1).getX()) / 84);
	}

	private int calculateY() {
		return (int) Math.ceil((Render.inputs.mouseY - board.getTile(1, 1).getY()) / 84);
	}

	private boolean isBoardClicked() {
		return Render.inputs.isClicked() && Render.inputs.mouseX >= board.getTile(1, 1).getX()
				&& Render.inputs.mouseX <= (board.getTile(8, 1).getWidth() + board.getTile(8, 1).getX())
				&& Render.inputs.mouseY >= board.getTile(1, 1).getY()
				&& Render.inputs.mouseY <= board.getTile(1, 8).getHeight() + board.getTile(1, 8).getY();
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

	public void placeWhites() {
		Piece piece;
		for (int i = 1; i < 9; i++) {
			piece =  new Pawn(true, i, 2);
			whitePieces.add(piece);
			board.getTile(i, 2).setPiece(piece);
		}
		for (int i = 1; i < 9; i++) {
			if (i == 1 || i == 8) {
				
				piece = new Rook(true, i, 1);
				whitePieces.add(piece);
				board.getTile(i, 1).setPiece(piece);
			}
			if (i == 2 || i == 7) {
				
				piece = new Knight(true, i, 1);
				whitePieces.add(piece);
				board.getTile(i, 1).setPiece(piece);
			}
			if (i == 3 || i == 6) {
				
				piece = new Bishop(true, i, 1);
				whitePieces.add(piece);
				board.getTile(i, 1).setPiece(piece);
			}
			if (i == 4) {
				
				piece = new Queen(true, i, 1);
				whitePieces.add(piece);
				board.getTile(i, 1).setPiece(piece);
			}
			if (i == 5) {
				
				piece = new King(true, i, 1);
				whitePieces.add(piece);
				board.getTile(i, 1).setPiece(piece);
			}
		}
	}
	
	private void placeBlacks() {
		Piece piece;
		for (int i = 1; i < 9; i++) {
			piece =  new Pawn(false, i, 7);
			blackPieces.add(piece);
			board.getTile(i, 7).setPiece(piece);
		}
		for (int i = 1; i < 9; i++) {
			if (i == 1 || i == 8) {
				
				piece = new Rook(false, i, 8);
				blackPieces.add(piece);
				board.getTile(i, 8).setPiece(piece);
			}
			if (i == 2 || i == 7) {
				
				piece = new Knight(false, i, 8);
				blackPieces.add(piece);
				board.getTile(i, 8).setPiece(piece);
			}
			if (i == 3 || i == 6) {
				
				piece = new Bishop(false, i, 8);
				blackPieces.add(piece);
				board.getTile(i, 8).setPiece(piece);
			}
			if (i == 4) {
				
				piece = new Queen(false, i, 8);
				blackPieces.add(piece);
				board.getTile(i, 8).setPiece(piece);
			}
			if (i == 5) {
				
				piece = new King(false, i, 8);
				blackPieces.add(piece);
				board.getTile(i, 8).setPiece(piece);
			}
		}		
	}

}
