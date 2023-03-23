package game.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
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
import utils.Resources;

import javax.print.attribute.standard.RequestingUserName;

import static utils.Render.app;

import java.util.ArrayList;

public class GameScreen extends AbstractScreen{
	private Stage stage;
	public static Board board;
	private boolean seleccionada=false;
	private ArrayList<Vector2> currentTile_validMovements = new ArrayList<>();
	private int current_x,current_y,original;
	private Tile currentTile = null;
	
	
	IOS inputs = new IOS();
	@Override
	public void show() {
		stage = new Stage(new FitViewport(1280, 720));
		Gdx.input.setInputProcessor(inputs);

		board = new Board();
		Background fondo = new Background();
		fondo.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		drawWhites();

		stage.addActor(fondo);
		stage.addActor(board);
	}
	
	

	@Override
	public void render(float delta) {
		Render.clearScreen();
		
		update(delta);
		stage.draw();

	}
	
	private void select(Tile tile) {
		if(currentTile.getPiece()!=null) {
			seleccionada=true;
			
			currentTile_validMovements = currentTile.getPiece().getMovement(current_x, current_y);
			
			highlight();
			
			System.out.println(currentTile_validMovements.toString());
			
		}
	}
	
	private void highlight() {
		for(Vector2 vector : currentTile_validMovements) {
			board.getTile(vector.x, vector.y).highlight=true;
		}
	}
	
	private void lowlight() {
		for(Vector2 vector : currentTile_validMovements) {
			board.getTile(vector.x, vector.y).highlight=false;
		}
	}
	
	public void update(float delta) {
		//Escape para volver al menú principal (Prueba)
		if(inputs.justPressed(Keys.ESCAPE)) {
			Render.app.setScreen(Render.MAINSCREEN);
		
		}else if(enTablero()) { // si se ha clicado dentro del tablero
	
			if(!seleccionada) {
				current_x = calcularX();
				current_y = calcularY();
				currentTile = board.getTile(current_x, current_y);
				
				select(currentTile);
				
			}else if(seleccionada){ //Si hay pieza seleccionada
				
				int next_x= calcularX();
				int next_y= calcularY();
				Tile nextTile = board.getTile(next_x, next_y);
				
				if(nextTile.getPiece()!=null && currentTile.getPiece().sameColor(nextTile.getPiece())) {
					currentTile = nextTile;
					current_x = next_x;
					current_y = next_y;
					lowlight();
					select(currentTile);
					
				}else {
					
					lowlight();
					
					if(currentTile_validMovements.contains(new Vector2(next_x, next_y))) {
						
						if(next_y==8.0 && currentTile.piece instanceof Pawn) {
							currentTile.getPiece().Moved();
							currentTile.move(next_x, next_y);
							board.getTile(next_x, 8).piece = new Queen(true);
						}else {
							currentTile.getPiece().Moved();
							currentTile.move(next_x, next_y);
						}
					}
					
					seleccionada=false;
				}
					
				}
				
		}		
		
	
		
	}
	
	private int calcularX() {
		return (int) Math.ceil((inputs.mouseX-board.getTile(1, 1).getX())/84);
	}
	
	private int calcularY() {
		return (int) Math.ceil((inputs.mouseY-board.getTile(1, 1).getY())/84);
	}

	private int Color(Tile tile) {
		return tile.getColor().equals(Color.BLACK) ? -1 : 1;

	}
	
	//Devuelve true si se clica en el tablero
	private boolean enTablero() {
		return inputs.isClicked() && inputs.mouseX>=board.getTile(1,1).getX()
				&& inputs.mouseX<=(board.getTile(8, 1).getWidth()+board.getTile(8, 1).getX())
				&& inputs.mouseY>=board.getTile(1, 1).getY()
				&& inputs.mouseY<=board.getTile(1, 8).getHeight()+board.getTile(1, 8).getY();
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
	
	public void drawWhites() {
		for(int i=1;i<9;i++) {
			board.getTile(i, 2).piece = new Pawn(true);
		}	
		for(int i=1;i<9;i++) {
			if(i==1 || i==8) {
				board.getTile(i, 1).piece = new Rook(true);
			}
			if(i==2 || i==7) {
				board.getTile(i, 1).piece = new Knight(true);
			}
			if(i==3 || i==6) {
				board.getTile(i, 1).piece = new Bishop(true);
			}
			if(i==4) {
				board.getTile(i, 1).piece = new Queen(true);
			}
			if(i==5) {
				board.getTile(i, 1).piece = new King(true);
			}
		}
	}

}
