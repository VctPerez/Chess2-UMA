package game.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.*;

import elements.Background;
import elements.Board;
import elements.Piece;
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

public class GameScreen extends AbstractScreen{
	private Stage stage;
	public static Board board;
	boolean seleccionada=false;
	
	int x,y;
	IOS inputs = new IOS();
	@Override
	public void show() {
		stage = new Stage(new FitViewport(1280, 720));
		Gdx.input.setInputProcessor(inputs);

		board = new Board();
		Background fondo = new Background();
		fondo.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		board.getTile(2, 2).piece = new Pawn();
		board.getTile(7, 5).piece = new Knight();
		//drawWhites();


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
		//Escape para volver al menú principal (Prueba)
		if(inputs.justPressed(Keys.ESCAPE)) {
			Render.app.setScreen(Render.MAINSCREEN);
		}else if(inputs.justPressed(Keys.RIGHT)) {
			board.getTile(x, y).move(x+1, y);
			x++;
			
		}else if(inputs.justPressed(Keys.LEFT)) {
			board.getTile(x, y).move(x-1, y);
			x--;
		}else if(inputs.justPressed(Keys.UP)) {
			board.getTile(x, y).move(x, y+1);
			y++;
		}else if(inputs.justPressed(Keys.DOWN)) {
			board.getTile(x, y).move(x, y-1);
			y--;
		}else if(enTablero()) { // si se ha clicado dentro del tablero
			
			//Si no hay una pieza seleccionada
			if(!seleccionada) {
				int xp= (int) Math.ceil((inputs.mouseX-board.getTile(1, 1).getX())/82);
				int yp= (int) Math.ceil((inputs.mouseY-board.getTile(1, 1).getY())/82);
				//Ahora se las coordenadas donde he pulsado
				if(board.getTile(xp, yp).piece!=null) {
					seleccionada=true;
					//Cambio a la imagen de la misma pieza seleccionada
					board.getTile(xp, yp).piece.setSprite("piece.png");
					x=xp;
					y=yp;
				}
			}else { //Si hay pieza seleccionada
				int xp= (int) Math.ceil((inputs.mouseX-board.getTile(1, 1).getX())/82);
				int yp= (int) Math.ceil((inputs.mouseY-board.getTile(1, 1).getY())/82);
				//Ahora se las coordenadas donde he pulsado
				
					//Si no has seleccionado la misma casilla ya seleccionada
					if(!(x==xp && y==yp)) {
						board.getTile(x, y).move(xp, yp);
						x=xp;
						y=yp;
					}
				//Cambio a la imagen de la misma pieza sin seleccionar
				board.getTile(xp, yp).piece.setSprite("piece2.png");
				seleccionada=false;
			}
			
		}
		
		
		
		
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
			board.getTile(i, 2).piece = new Knight();
		}	
		for(int i=1;i<9;i++) {
			if(i==1 || i==8) {
				board.getTile(i, 1).piece = new Rook();
			}
			if(i==2 || i==7) {
				board.getTile(i, 1).piece = new Bishop();
			}
			if(i==3 || i==6) {
				board.getTile(i, 1).piece = new Knight();
			}
			if(i==4) {
				board.getTile(i, 1).piece = new Queen();
			}
			if(i==5) {
				board.getTile(i, 1).piece = new King();
			}
		}
	}

}
