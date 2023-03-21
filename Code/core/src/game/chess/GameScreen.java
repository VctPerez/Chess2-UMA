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
import elements.pieces.Knight;
import elements.pieces.Pawn;
import utils.IOS;
import utils.Render;

public class GameScreen extends ScreenAdapter{
	private Stage stage;
	public static Board board;
	
	int x = 2, y = 2;
	IOS inputs = new IOS();
	
	@Override
	public void show() {
		stage = new Stage(new FitViewport(1280, 720));
		Gdx.input.setInputProcessor(inputs);

		board = new Board();
		Background fondo = new Background();
		fondo.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());


		board.getTile(x, y).piece = new Pawn();
		board.getTile(7, 5).piece = new Knight();

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
		if(inputs.justPressed(Keys.RIGHT)) {
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

}
