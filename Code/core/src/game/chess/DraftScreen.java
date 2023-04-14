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
	Image arrow;
	int cont = 5;
	
	Tile tile1, tile2;

	@Override
	public void show() {
		stage = new Stage(new FitViewport(1280, 720));
		stage.clear();
		
		Gdx.input.setInputProcessor(stage);
		
		background = new Background();
		background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		info = new PieceInfo();
		piece = new Pawn(true, 3, 3, info.board);
		info.infoFrom(piece);
		
		tile1= new Tile(1,1,325f, 450f, 168f, 0);
		tile2= new Tile(1,1,325f, 150f, 168f, 0);
		

		// -------------------------------

		stage.addActor(background);
		stage.addActor(info);
		stage.addActor(tile1);
		stage.addActor(tile2);
		initButtons();
		initDraft();

	}

	private void initDraft() {

		draft = new LinkedHashMap<String, Image>();
		draft.put("King", new Image(Render.app.getManager().get(Resources.KING_PATH, Texture.class)));
		draft.put("Queen", new Image(Render.app.getManager().get(Resources.QUEEN_PATH, Texture.class)));
		draft.put("Rook", new Image(Render.app.getManager().get(Resources.ROOK_PATH, Texture.class)));
		draft.put("Bishop", new Image(Render.app.getManager().get(Resources.BISHOP_PATH, Texture.class)));
		draft.put("Knight", new Image(Render.app.getManager().get(Resources.KNIGHT_PATH, Texture.class)));
		draft.put("Pawn", new Image(Render.app.getManager().get(Resources.PAWN_PATH, Texture.class)));

		int i = 0;
		for (Image piece : draft.values()) {
			piece.setSize(84, 84);
			piece.setPosition(20, 85 + (i * 100));
			i++;
			stage.addActor(piece);
		}
		
		arrow=new Image(Render.app.getManager().get(Resources.ARROW_PATH, Texture.class));
		arrow.setPosition(80, 100+100*cont);
		stage.addActor(arrow);
	}

	private void initButtons() {
		Text nextText, backText;// Botones con nombre momentaneos, se cambiara y se a�adira a los ficheros de idiomas

		
		nextText = new Text(Resources.FONT_MENU_PATH, 20, Color.WHITE, 3);
		nextText.setText("Siguiente");
		nextText.setPosition(550, 50);
		
		backText = new Text(Resources.FONT_MENU_PATH, 20, Color.WHITE, 3);
		backText.setText("Atras");
		backText.setPosition(175, 50);

		next = new TextButton(nextText);
		back = new TextButton(backText);

		stage.addActor(next);
		stage.addActor(back);

		Gdx.input.setInputProcessor(Render.inputs);
	}

	public void changePiece() {
		info.clearBoard(piece);
		switch (cont) {
		case 5:
			
			//se crean dos casillas en medio de la pantalla (puede que las casillas sea mejor crearlas antes y solo cambiar las piezas con setPiece) la info se relaciona con la casilla en la que hagas click no con el change piece
			//change piece cambia la clase de piezas que salen en las casillas, solo eso.
			
			//método que dependiendo de la clave añada unas piezas u otras
			
			piece = new Pawn(true, 3, 3, info.board);

			break;

		case 4:
			
			piece = new Knight(true, 3, 3, info.board);

			break;

		case 3:
			
			piece = new Bishop(true, 3, 3, info.board);

			break;

		case 2:
			
			piece = new Rook(true, 3, 3, info.board);

			break;

		case 1:
			
			piece = new Queen(true, 3, 3, info.board);

			break;

		case 0:
			
			piece = new King(true, 3, 3, info.board);

			break;
		}
		arrow.setPosition(80, 100+100*cont);
		info.infoFrom(piece);
	}

	public void update() {
		if (next.isClicked()) {// hacer que se cambie el valor de la clave actual del mapa
			if (cont > 0) {
				cont--;
				changePiece();
			}else {
				Render.app.setScreen(new GameScreen());
			}
		}
		if (back.isClicked()) {
			if (cont < 5) {
				cont++;
				changePiece();
			}
		}
		
		if(cont==0){
			next.setText("Finalizar");
		}else {
			next.setText("Siguiente");
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
