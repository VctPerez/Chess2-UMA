package game.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.*;

import elements.Background;
import elements.Board;
import elements.Graveyard;
import elements.Tile;
import elements.pieces.Bishop;
import elements.pieces.King;
import elements.pieces.Knight;
import elements.pieces.Pawn;
import elements.pieces.Queen;
import elements.pieces.Rook;
import utils.IOS;
import utils.Render;

import java.util.ArrayList;

public class GameScreen extends AbstractScreen {
	private Stage stage;
	public static Board board;
	
	//Control selección de piezas
	private boolean isPieceSelected = false;
	private ArrayList<Vector2> currentTile_validMovements = new ArrayList<>();
	private int current_x, current_y;
	private Tile currentTile = null;
	private Tile nextTile = null;

	//----------------------------
	//CONTROL JAQUE
	//----------------------------
	
	//Guardamos en todo momento donde esta el rey blanco y el negro (ÚTIL)
	//No sé si habrá que controlar que pieza está poniendo el jaque y guardarla, posiciones interesantes a guardar en caso de jaque...
	private Vector2 kingW = new Vector2(5,1), kingB = new Vector2(5,8);
	
	//Saber para cada rey si está en jaque o no
	private boolean  jaqueW=false,jaqueB=false;
	
	//----------------------------
	//FIN CONTROL JAQUE
	//----------------------------
	
	
	//Control captura al paso
	private Pawn lastPawn;
	
	//Control turno
	private boolean PLAYER1 = true;

	//Cementerios
	
	public static Graveyard graveyardWhite;
	public static Graveyard graveyardBlack;
	
	IOS inputs = new IOS();

	@Override
	public void show() {
		stage = new Stage(new FitViewport(1280, 720));
		Gdx.input.setInputProcessor(inputs);

		board = new Board();
		graveyardWhite = new Graveyard(0,21);
		graveyardBlack = new Graveyard(Gdx.graphics.getWidth()-42,21);
		Background fondo = new Background();
		fondo.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		placeWhites();
		placeBlacks();

		stage.addActor(fondo);
		stage.addActor(board);
		stage.addActor(graveyardWhite);
		stage.addActor(graveyardBlack);
	}

	@Override
	public void render(float delta) {
		Render.clearScreen();

		update(delta);
		stage.draw();

	}


	public void update(float delta) {
        // Escape para volver al menÃº principal (Prueba)
        if (inputs.justPressed(Keys.ESCAPE)) {
        	 Render.app.setScreen(Render.MAINSCREEN);
        // R para reiniciar la partida (Pruebas)
        }else if(inputs.justPressed(Keys.R)) {
       	 Render.app.setScreen(new GameScreen());
        }else if(isBoardClicked()) {

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
	
	private void Jaque(ArrayList<Vector2> mov,boolean team) {
		if(team==true) {
			if(mov.contains(kingB)) {
				jaqueB=true;
				jaqueW=false;
				board.getTile(kingB.x,kingB.y ).attacked = true;
				board.getTile(kingW.x, kingW.y).attacked = false;
			}
		}else {
			if(mov.contains(kingW)) {
				jaqueW=true;
				jaqueB=false;
				board.getTile(kingB.x,kingB.y ).attacked = false;
				board.getTile(kingW.x, kingW.y).attacked = true;
			}
		}
		
	}


	/**
	 * Resalta las casillas contenidas en el array de movimientos válidos
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
	 * Elimina el resaltado de las casillas contenidas en el array de movimientos válidos
	 */
	private void lowlight() {
		board.getTile(kingB.x, kingB.y).attacked=false;
		board.getTile(kingW.x, kingW.y).attacked=false;
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
	 * Selecciona la casilla pasada como parámetro, es decir, si tiene una pieza calcula los posibles movimientos y los resalta
	 * @param tile
	 */
	private void select(Tile tile) {
		if (currentTile.getPiece() != null && currentTile.getPiece().color()==PLAYER1) {
			
			
			//TODO if(jaque){... en funcion de si hay jaque o no y si eres el rey o una pieza defensora los calculos de movimientos van variando
			//Si se mueve el rey se actualiza si sigue en jaque o no... esto es durísimo
			
			currentTile_validMovements = (currentTile.getPiece().getMovement(current_x, current_y));
			
			highlight(currentTile.piece.color());
			
			System.out.println(currentTile_validMovements.toString());
			isPieceSelected = true;
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
        	
        	if(nextTile.getPiece() instanceof Pawn) {
   
        		checkPassant(next_x, next_y);
        		
        		updateLastPawn(next_x, next_y);      
        		
        		checkPromotion(next_x, next_y);
        	}
        	
        	
        	//Tras moverla se comprueba si hay jaque
        	if(nextTile.getPiece() instanceof King) {
        		if(nextTile.getPiece().color()) {
        			kingW.set(next_x, next_y);
        		}else {
        			kingB.set(next_x, next_y);
        		}
        	}
        	
        	//Calcula los siguientes movimientos tras mover para saber si este movimiento ha puesto en jaque al rey
        	currentTile_validMovements = (board.getTile(next_x, next_y).getPiece().getMovement(next_x, next_y));
			Jaque(currentTile_validMovements,nextTile.getPiece().color());
			
		/*
			//Para probar visualmente que el rey estará atacado a partir de este movimiento
			if(jaqueB) {
				board.getTile(kingB.x, kingB.y).attacked=true;
			}else if(jaqueW) {
				board.getTile(kingW.x, kingW.y).attacked=true;
			}else {
				board.getTile(kingB.x,kingB.y ).attacked = false;
				board.getTile(kingW.x, kingW.y).attacked = false;
			}
			
        	*/
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
	 * Comprueba si el ultimo movimiento permite relizar una promoción (peon llega al lado contrario)
	 * @param next_x
	 * @param next_y
	 */
	private void checkPromotion(float next_x, float next_y) {
		if ((next_y == 8.0 || next_y == 1.0)) {//Implementar que se pueda escoger entre todas las piezas posibles
			nextTile.setPiece(new Queen(board.getTile(next_x, next_y).getPiece().color()));
		}
	}
	
	private void checkPassant(float next_x, float next_y) {
		if (isEnPassant(next_x, next_y, (Pawn)nextTile.getPiece())){
			board.getTile(next_x,next_y + (nextTile.getPiece().color()?-1:1)).setPiece(null);
		}
	}
	
	private boolean isEnPassant(float next_x, float next_y, Pawn pawn) {
		boolean res = false;
		if (next_y == current_y + (pawn.color()?1:-1) && (next_x == current_x + 1 || next_x == current_x -1)){ //Si avanza a una casilla diagonal sin pieza, está tomando al paso
			if (board.getTile(next_x,current_y).getPiece() instanceof Pawn){
				res = ((Pawn)board.getTile(next_x,current_y).getPiece()).isPassantable; //Es en passant si se le puede hacer al peón objetivo
			}
		}
		return res;
	}
	
	private void updateLastPawn(float next_x, float next_y) {
		if (lastPawn != null){ 
			lastPawn.isPassantable = false; 
		}
		if (next_y == current_y + 2 || next_y == current_y - 2){
			lastPawn = ((Pawn) nextTile.getPiece());
			lastPawn.isPassantable = true;
		}
	}
	
	private void changeTurn() {
		PLAYER1 = !PLAYER1;
	}
	private int calculateX() {
		return (int) Math.ceil((inputs.mouseX - board.getTile(1, 1).getX()) / 84);
	}

	private int calculateY() {
		return (int) Math.ceil((inputs.mouseY - board.getTile(1, 1).getY()) / 84);
	}

	private boolean isBoardClicked() {
		return inputs.isClicked() && inputs.mouseX >= board.getTile(1, 1).getX()
				&& inputs.mouseX <= (board.getTile(8, 1).getWidth() + board.getTile(8, 1).getX())
				&& inputs.mouseY >= board.getTile(1, 1).getY()
				&& inputs.mouseY <= board.getTile(1, 8).getHeight() + board.getTile(1, 8).getY();
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
		for (int i = 1; i < 9; i++) {
			board.getTile(i, 2).piece = new Pawn(true);
		}
		for (int i = 1; i < 9; i++) {
			if (i == 1 || i == 8) {
				board.getTile(i, 1).piece = new Rook(true);
			}
			if (i == 2 || i == 7) {
				board.getTile(i, 1).piece = new Knight(true);
			}
			if (i == 3 || i == 6) {
				board.getTile(i, 1).piece = new Bishop(true);
			}
			if (i == 4) {
				board.getTile(i, 1).piece = new Queen(true);
			}
			if (i == 5) {
				board.getTile(i, 1).piece = new King(true);
			}
		}
	}
	
	private void placeBlacks() {
		for (int i = 1; i < 9; i++) {
			board.getTile(i, 7).piece = new Pawn(false);
		}
		for (int i = 1; i < 9; i++) {
			if (i == 1 || i == 8) {
				board.getTile(i, 8).piece = new Rook(false);
			}
			if (i == 2 || i == 7) {
				board.getTile(i, 8).piece = new Knight(false);
			}
			if (i == 3 || i == 6) {
				board.getTile(i, 8).piece = new Bishop(false);
			}
			if (i == 4) {
				board.getTile(i, 8).piece = new Queen(false);
			}
			if (i == 5) {
				board.getTile(i, 8).piece = new King(false);
			}
		}
	}

}
