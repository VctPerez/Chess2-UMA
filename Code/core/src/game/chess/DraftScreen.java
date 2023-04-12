package game.chess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import elements.*;
import elements.pieces.*;
import utils.*;

public class DraftScreen extends AbstractScreen {
	public static Stage stage;
	
	private Map<Image, Piece> draft;
	
	
	Background background;
	PieceInfo info;
    TextButton end,next,back;

	@Override
	public void show() {
		stage = new Stage(new FitViewport(1280, 720));
		stage.clear();
		Gdx.input.setInputProcessor(stage);
		info=new PieceInfo(new Knight(true,3,3,new Board(70,615,-150)));
		background = new Background();
		background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		

		// -------------------------------

		stage.addActor(background);
		stage.addActor(info);	
		initDraft();
		initButtons();
	}
	
	private void initDraft() {

		draft = new HashMap<Image, Piece>();
		draft.put(new Image(Render.app.getManager().get(Resources.PAWN_PATH, Texture.class)), null);
		draft.put(new Image(Render.app.getManager().get(Resources.KNIGHT_PATH, Texture.class)), null);
		draft.put(new Image(Render.app.getManager().get(Resources.BISHOP_PATH, Texture.class)), null);
		draft.put(new Image(Render.app.getManager().get(Resources.ROOK_PATH, Texture.class)), null);
		draft.put(new Image(Render.app.getManager().get(Resources.QUEEN_PATH, Texture.class)), null);
		draft.put(new Image(Render.app.getManager().get(Resources.KING_PATH, Texture.class)), null);

		
		int i = 0;
		for(Image piece : draft.keySet()) {
			piece.setSize(84, 84);
			piece.setPosition(20 , 65 + (i*100));
			i++;
			stage.addActor(piece);
		}
	}
	
	private void initButtons() {
		Text endText,nextText,backText;
		endText = new Text(Resources.FONT_MENU_PATH,20,Color.WHITE,3);
    	endText.setText("End"); 
    	endText.setPosition(50, 50);
    	nextText = new Text(Resources.FONT_MENU_PATH,20,Color.WHITE,3);
    	nextText.setText("Next"); 
    	nextText.setPosition(600, 50);
    	backText = new Text(Resources.FONT_MENU_PATH,20,Color.WHITE,3);
    	backText.setText("Back"); 
    	backText.setPosition(300, 50);
    	
    	end = new TextButton(endText);
        next = new TextButton(nextText);
        back = new TextButton(backText);
        
        stage.addActor(end);
		stage.addActor(next);
		stage.addActor(back);
	}
	

	@Override
	public void render(float delta) {
		Render.clearScreen();
		Render.Batch.begin();
		stage.draw();
		stage.act();
		
		Render.Batch.end();
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
