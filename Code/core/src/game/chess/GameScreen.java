package game.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.*;

import elements.Background;
import elements.Board;
import elements.Piece;
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
	private boolean isPieceSelected = false;
	private ArrayList<Vector2> currentTile_validMovements = new ArrayList<>();
	private int current_x, current_y;
	private Tile currentTile = null;
	
	private boolean PLAYER1 = true;

	IOS inputs = new IOS();

	@Override
	public void show() {
		stage = new Stage(new FitViewport(1280, 720));
		Gdx.input.setInputProcessor(inputs);

		board = new Board();
		Background fondo = new Background();
		fondo.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		placeWhites();
		placeBlacks();

		stage.addActor(fondo);
		stage.addActor(board);
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
                Tile nextTile = board.getTile(next_x, next_y);
                

                if (nextTile.getPiece() != null && currentTile.getPiece().sameColor(nextTile.getPiece())) {
                    currentTile = nextTile;
                    current_x = next_x;
                    current_y = next_y;

                    lowlight();
                    select(currentTile);
            
                } else {
                    lowlight();
                    if(currentTile.getPiece() instanceof King && next_x==current_x-2 && board.getTile(1, current_y).piece!=null) {
                    	moveCurrentPieceTo(next_x, next_y);
                    	System.out.println("siguiente x: " + next_x);
                    	board.getTile(1, current_y).move(current_x-1,current_y);
						noEnPassant(); //Siempre se pone porque un peón se puede tomar al paso un movimiento después de moverse
                    }else if(currentTile.getPiece() instanceof King && next_x==current_x+2 && board.getTile(8, current_y).piece!=null){
                    	moveCurrentPieceTo(next_x, next_y);
                    	board.getTile(8, current_y).move(current_x+1,current_y);
						noEnPassant();
                    }else if (currentTile.getPiece() instanceof Pawn && (next_y==current_y + 2 || next_y==current_y-2)){
                    	moveCurrentPieceTo(next_x, next_y);
						noEnPassant();
						((Pawn) nextTile.getPiece()).enPassantable = true;
						System.out.println(((Pawn)nextTile.getPiece()).enPassantable);
                    }else if (currentTile.getPiece() instanceof Pawn && isEnPassant(current_x,current_y,next_x,next_y,(Pawn)currentTile.getPiece())){
						moveCurrentPieceTo(next_x,next_y);
						board.getTile(next_x,next_y + (nextTile.getPiece().color()?-1:1)).setPiece(null);
						noEnPassant();
					} else {
						moveCurrentPieceTo(next_x,next_y);
						noEnPassant();
					}
                    isPieceSelected = false;
                    }
                    
                }

            }
        }

	private boolean isEnPassant(int current_x, int current_y, int next_x, int next_y, Pawn pawn) {
		boolean res = false;
		if (next_y == current_y + (pawn.color()?1:-1) && (next_x == current_x + 1 || next_x == current_x -1)){ //Si avanza a una casilla diagonal sin pieza, está tomando al paso
			if (board.getTile(next_x,next_y).getPiece() == null){ //Suponemos que hay un peón atrás o si no no se movería ahí
				res = true;
			}
		}
		return res;
	}


	//Comprueba que se pueda enrocar, queda comprobar que el camino no esté amenazado, que será implementado en este método también
	private boolean freetocast(int x, int y,int dest) {
		boolean res=true; 
		if(dest>x) {
			for(int i=x+1; i<dest;i++) {
				System.out.println(i);
				if(board.getTile(i, y).piece!=null) {
					res=false;
				}
			}
		}else {
			for(int i=x-1; i>dest;i--) {
				if(board.getTile(i, y).piece!=null) {
					res=false;
				}
			}
		}
		return res;
	}

	/**
	 * Hace que ningún peón pueda ser tomado al paso
	 * <p>(Usado tras decidir el movimiento)
	 */
	private void noEnPassant(){
		Tile atajo;
		for (int x = 1; x < 9; x++){
			atajo = board.getTile(x,4);
			if (atajo != null && atajo.getPiece() instanceof Pawn p){
				p.enPassantable = false;
			}
		}
		for (int x = 1; x < 9; x++){
			atajo = board.getTile(x,5);
			if (atajo != null && atajo.getPiece() instanceof Pawn p){
				p.enPassantable = false;
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
			
			currentTile_validMovements = (currentTile.getPiece().getMovement(current_x, current_y));
			
			castling();
			highlight(currentTile.piece.color());
			
			System.out.println(currentTile_validMovements.toString());
			isPieceSelected = true;
		}
	}
	
	//Caso Especial Enroque
	private void castling() {
        if(currentTile.getPiece() instanceof King && currentTile.getPiece().hasBeenMoved==false) {
        		
        	//Se prueba tanto el enroque largo como el corto, se aplica en los dos equipos
        	if(board.getTile(1,current_y).piece instanceof Rook && board.getTile(1, current_y).piece.hasBeenMoved==false){
            	if(freetocast(current_x,current_y,1)) {
            		currentTile_validMovements.add(new Vector2(current_x-2,current_y));
            	}
        	}
        	if(board.getTile(8,current_y).piece instanceof Rook && board.getTile(8, current_y).piece.hasBeenMoved==false) {
        		if(freetocast(current_x,current_y,8))
            		currentTile_validMovements.add(new Vector2(current_x+2,current_y));
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
            if ((next_y == 8.0 || next_y == 1.0) && currentTile.getPiece() instanceof Pawn) {
                currentTile.move(next_x, next_y);
                board.getTile(next_x, next_y).setPiece(new Queen(board.getTile(next_x, next_y).getPiece().color()));
            }else {
                currentTile.move(next_x, next_y);
            }
            PLAYER1 = !PLAYER1;
        }
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
