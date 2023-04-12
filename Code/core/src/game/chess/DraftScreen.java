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

	private Map<String, Image> draft;

	Background background;
	PieceInfo info;
	TextButton end, next, back;
	Piece piece;
	Image Arrow;
	int cont = 0;

	@Override
	public void show() {
		stage = new Stage(new FitViewport(1280, 720));
		stage.clear();
		Gdx.input.setInputProcessor(stage);
		info = new PieceInfo();
		background = new Background();
		background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		piece = new Pawn(true, 3, 3, info.board);
		info.infoFrom(piece);

		// -------------------------------

		stage.addActor(background);
		stage.addActor(info);
		initButtons();
		initDraft();

	}

	private void initDraft() {

		draft = new LinkedHashMap<String, Image>();
		draft.put("Peon", new Image(Render.app.getManager().get(Resources.KING_PATH, Texture.class)));
		draft.put("Caballo", new Image(Render.app.getManager().get(Resources.QUEEN_PATH, Texture.class)));
		draft.put("Alfil", new Image(Render.app.getManager().get(Resources.ROOK_PATH, Texture.class)));
		draft.put("Torre", new Image(Render.app.getManager().get(Resources.BISHOP_PATH, Texture.class)));
		draft.put("Reina", new Image(Render.app.getManager().get(Resources.KNIGHT_PATH, Texture.class)));
		draft.put("Rey", new Image(Render.app.getManager().get(Resources.PAWN_PATH, Texture.class)));

		int i = 0;
		for (Image piece : draft.values()) {
			piece.setSize(84, 84);
			piece.setPosition(20, 85 + (i * 100));
			i++;
			stage.addActor(piece);
		}
		Arrow=new Image(Render.app.getManager().get(Resources.ARROW_PATH, Texture.class));
		Arrow.setPosition(80, 100+100*5);
		stage.addActor(Arrow);
	}

	private void initButtons() {
		Text endText, nextText, backText;
		endText = new Text(Resources.FONT_MENU_PATH, 20, Color.WHITE, 3);
		endText.setText("Finalizar"); // Botones con nombre momentaneos, se cambiara y se añadira a los ficheros de
										// idiomas
		endText.setPosition(50, 50);
		nextText = new Text(Resources.FONT_MENU_PATH, 20, Color.WHITE, 3);
		nextText.setText("Siguiente");
		nextText.setPosition(600, 50);
		backText = new Text(Resources.FONT_MENU_PATH, 20, Color.WHITE, 3);
		backText.setText("Atras");
		backText.setPosition(300, 50);

		end = new TextButton(endText);
		next = new TextButton(nextText);
		back = new TextButton(backText);

		stage.addActor(end);
		stage.addActor(next);
		stage.addActor(back);

		Gdx.input.setInputProcessor(Render.inputs);
	}

	public void changePiece() {
		switch (cont) {
		case 0:
			info.cleanBoard(piece);
			piece = new Pawn(true, 3, 3, info.board);
			Arrow.setPosition(80, 100+100*5);
			info.infoFrom(piece);
			break;

		case 1:
			info.cleanBoard(piece);
			piece = new Knight(true, 3, 3, info.board);
			Arrow.setPosition(80, 100+100*4);
			info.infoFrom(piece);
			break;

		case 2:
			info.cleanBoard(piece);
			piece = new Bishop(true, 3, 3, info.board);
			Arrow.setPosition(80, 100+100*3);
			info.infoFrom(piece);
			break;

		case 3:
			info.cleanBoard(piece);
			piece = new Rook(true, 3, 3, info.board);
			Arrow.setPosition(80, 100+100*2);
			info.infoFrom(piece);
			break;

		case 4:
			info.cleanBoard(piece);
			piece = new Queen(true, 3, 3, info.board);
			Arrow.setPosition(80, 100+100*1);
			info.infoFrom(piece);
			break;

		case 5:
			info.cleanBoard(piece);
			piece = new King(true, 3, 3, info.board);
			Arrow.setPosition(80, 100+100*0);
			info.infoFrom(piece);
			break;
		}
	}

	public void update() {
		if (next.isClicked()) {
			if (cont < 5) {
				cont++;
				changePiece();
			}
		}
		if (back.isClicked()) {
			if (cont > 0) {
				cont--;
				changePiece();
			}
		}
		if (end.isPressed()) {
			Render.app.setScreen(new GameScreen());
		}
	}

	@Override
	public void render(float delta) {
		Render.clearScreen();
		Render.Batch.begin();
		update();
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
