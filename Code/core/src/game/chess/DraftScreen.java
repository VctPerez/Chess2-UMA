package game.chess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import elements.*;
import elements.pieces.*;
import utils.*;

public class DraftScreen extends AbstractScreen {
	public static Stage stage;
	
	private Map<String, Piece> draft;
	
	
	Background background;
	PieceInfo info;

	@Override
	public void show() {
		stage = new Stage(new FitViewport(1280, 720));
		stage.clear();
		Gdx.input.setInputProcessor(Render.inputs);
		info=new PieceInfo(new Knight(true,3,3,new Board(70,615,-150)));
		
		initDraft();
		
		
		background = new Background();
		background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		// -------------------------------

		stage.addActor(background);
		stage.addActor(info);
	}
	
	private void initDraft() {
		draft = new HashMap<String, Piece>();
		draft.put("Pawn", null);
		draft.put("Knight", null);
		draft.put("Rook", null);
		draft.put("Bishop", null);
		draft.put("Queen", null);
		draft.put("King", null);
	}
	

	@Override
	public void render(float delta) {
		Render.clearScreen();

		stage.draw();
		stage.act();
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
