package game.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.*;

public class GameScreen implements Screen{
	protected Chess2 game;
	private Stage stage;
	private Background fondo;
	public static Board board;
	
	int x = 2, y = 2;

	public GameScreen(Chess2 game) {
		this.game = game;
		stage = new Stage(new FitViewport(1280, 720));
		//Gdx.input.setInputProcessor(stage);
		
		board = new Board();
		fondo = new Background();
		fondo.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		
		board.getTile(x, y).piece = new Piece();
		
		stage.addActor(fondo);
		stage.addActor(board);
		
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		update(delta);
		stage.draw();

	}
	public void update(float delta) {
		if(Gdx.input.isKeyJustPressed(Keys.RIGHT)) {
			board.getTile(x, y).move(x+1, y);
			x++;
			
		}else if(Gdx.input.isKeyJustPressed(Keys.LEFT)) {
			board.getTile(x, y).move(x-1, y);
			x--;
		}else if(Gdx.input.isKeyJustPressed(Keys.UP)) {
			board.getTile(x, y).move(x, y+1);
			y++;
		}else if(Gdx.input.isKeyJustPressed(Keys.DOWN)) {
			board.getTile(x, y).move(x, y-1);
			y--;
		}
	}

	@Override
	public void resize(int width, int height) {
		System.out.println("Ancho: "+width+", Alto: "+height);
		System.out.println("Ancho: "+Gdx.graphics.getWidth()+", Alto: "+Gdx.graphics.getHeight());
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
