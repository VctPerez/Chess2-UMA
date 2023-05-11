package elements;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;

import game.chess.GameScreen;
import utils.Image;
import utils.Render;
import utils.Resources;

public class Graveyard extends Actor {
	public ArrayList<Piece> graveyard;
	private final float Y_OFFSET = 10;
	private final float X_OFFSET = 30;
	private int index = 0;
	private Image grave;

	public Graveyard(float x, float y) {
		graveyard = new ArrayList<>();
		setPosition(x, y);
		setSize(42, 42 * 16);
		setColor(Color.BLACK);
		grave = new Image(Render.app.getManager().get(Resources.GRAVEYARD_PATH, Texture.class));
		grave.setSize(grave.getDimensions().x, grave.getDimensions().y);
		grave.setPosition(x - X_OFFSET, y - Y_OFFSET);

		grave.setTransparency(0.80f);
	}

	public void add(Piece piece) {
		piece.alive = false;
		if (piece.color) {
			GameScreen.whitePieces.remove(piece);
		} else {
			GameScreen.blackPieces.remove(piece);
		}

		ParallelAction par = new ParallelAction();
		par.addAction(Actions.moveTo(getX() + 54, getY() + (38 * index) + 25, 0.6f));
		par.addAction(Actions.sizeTo(42, 42, 0.6f));

		piece.addAction(Actions.sequence(Actions.sizeTo(84, 84), Actions.delay(0.2f), par));

		graveyard.add(piece);
		index++;
	}

	public void simulateAdd(Piece piece) {
		piece.alive = false;
		if (piece.color) {// implementar método que haga esto en gamescreen
			GameScreen.whitePieces.remove(piece);
		} else {
			GameScreen.blackPieces.remove(piece);
		}

		graveyard.add(piece);
		index++;
	}

	public Piece reviveLastPiece() {
		Piece piece = null;
		if (!graveyard.isEmpty()) {
			piece = graveyard.get(graveyard.size() - 1);
			piece.alive = true;
			graveyard.remove(graveyard.size() - 1);

			if (piece.color) {// implementar método que haga esto en gamescreen
				GameScreen.whitePieces.add(piece);
			} else {
				GameScreen.blackPieces.add(piece);
			}
		}
		index--;
		return piece;
	}

	public void draw(Batch batch, float parentAlpha) {
		grave.setPosition(getX(), getY());
		grave.draw(batch, parentAlpha);
		

		for (int i = 0; i < graveyard.size(); i++) {
			graveyard.get(i).draw(batch, parentAlpha);
		}

	}
	
	public void flipImage() {
		grave.flip(true, false);
	}
}
