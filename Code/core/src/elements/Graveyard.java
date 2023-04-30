package elements;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;

import game.chess.GameScreen;

public class Graveyard extends Actor {
	private ArrayList<Piece> graveyard;
	protected ShapeRenderer frame;
	private final float Y_OFFSET = 24;
	private final float X_OFFSET = 24;
	private int index = 0;

	public Graveyard(float x, float y) {
		graveyard = new ArrayList<>();
		frame = new ShapeRenderer();
		setPosition(x, y);
		setSize(42,42*16);
		setColor(Color.BLACK);
	}
	
	public void add(Piece piece) {
		piece.alive=false;
		if(piece.color) {//implementar método que haga esto en gamescreen
			GameScreen.whitePieces.remove(piece);
		}else {
			GameScreen.blackPieces.remove(piece);
		}
		
		ParallelAction par = new ParallelAction();
		par.addAction(Actions.moveTo(getX(), getY()+(42*index), 0.6f));
		par.addAction(Actions.sizeTo(42, 42, 0.6f));
		
		piece.addAction(Actions.sequence(Actions.sizeTo(84, 84), Actions.delay(0.2f) , par));
		
		graveyard.add(piece);
		index++;
	}
	
	public void simulateAdd(Piece piece) {
		piece.alive=false;
		if(piece.color) {//implementar método que haga esto en gamescreen
			GameScreen.whitePieces.remove(piece);
		}else {
			GameScreen.blackPieces.remove(piece);
		}
		
		graveyard.add(piece);
		index++;
	}
	
	public Piece reviveLastPiece() {
		Piece piece = null;
		if(!graveyard.isEmpty()) {
			piece = graveyard.get(graveyard.size()-1);
			piece.alive=true;
			graveyard.remove(graveyard.size()-1);
			
			if(piece.color) {//implementar método que haga esto en gamescreen
				GameScreen.whitePieces.add(piece);
			}else {
				GameScreen.blackPieces.add(piece);
			}
		}
		index--;
		return piece;
	}
	
	public void draw(Batch batch, float parentAlpha) {
		batch.end();
		frame.begin(ShapeType.Filled);
		frame.setProjectionMatrix(batch.getProjectionMatrix());
		frame.setTransformMatrix(batch.getTransformMatrix());
		frame.rect(getX(), getY(), getWidth(), getHeight());
		frame.setColor(212/255f,202/255f,182/255f,1f);
		
		frame.end();
		batch.begin();
		
		for(int i = 0; i< graveyard.size(); i++) {
			//graveyard.get(i).setPosition(getX(), getY() + (42 * i));
			//graveyard.get(i).setSize(42,  42);
			graveyard.get(i).draw(batch, parentAlpha);
		}
		
	}
}
