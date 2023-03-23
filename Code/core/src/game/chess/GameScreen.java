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

public class GameScreen extends AbstractScreen {
	private Stage stage;
	public static Board board;
	private boolean isPieceSelected = false;
	private ArrayList<Vector2> currentTile_validMovements = new ArrayList<>();
	private int current_x, current_y;
	private Tile currentTile = null;

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
		// Escape para volver al menú principal (Prueba)
		if (inputs.justPressed(Keys.ESCAPE)) {
			Render.app.setScreen(Render.MAINSCREEN);

		} else if (isBoardClicked()) {

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
					moveCurrentPieceTo(next_x, next_y);
					isPieceSelected = false;
				}
				
			}
		}
	}

	
	/**
	 * Resalta las casillas contenidas en el array de movimientos válidos
	 */
	private void highlight() {
		for (Vector2 vector : currentTile_validMovements) {
			board.getTile(vector.x, vector.y).highlight = true;
		}
	}
	
	/**
	 * Elimina el resaltado de las casillas contenidas en el array de movimientos válidos
	 */
	private void lowlight() {
		for (Vector2 vector : currentTile_validMovements) {
			board.getTile(vector.x, vector.y).highlight = false;
		}
	}
	
	/**
	 * Selecciona la casilla pasada como parámetro, es decir, si tiene una pieza calcula los posibles movimientos y los resalta
	 * @param tile
	 */
	private void select(Tile tile) {
		if (currentTile.getPiece() != null) {
			
			currentTile_validMovements = currentTile.getPiece().getMovement(current_x, current_y);
			highlight();
			
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
			if (next_y == 8.0 && currentTile.getPiece() instanceof Pawn) {
				currentTile.move(next_x, next_y);
				board.getTile(next_x, next_y).setPiece(new Queen(board.getTile(next_x, next_y).getPiece().color()));
			} else {
				currentTile.move(next_x, next_y);
			}
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
