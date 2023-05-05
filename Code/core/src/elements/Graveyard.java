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
	private final float Y_OFFSET = 20;
	private final float X_OFFSET = 140;
	private int index = 0;
	private Image GraveDisposer;

	public Graveyard(float x, float y) {
		graveyard = new ArrayList<>();
		setPosition(x, y);
		setSize(42,42*16);
		setColor(Color.BLACK);
		GraveDisposer = new Image(Render.app.getManager().get(Resources.GRAVEYARD_PATH,Texture.class));
		GraveDisposer.setSize(500, 720);
		GraveDisposer.setPosition(x-X_OFFSET, y-Y_OFFSET);
		GraveDisposer.setTransparency(0.80f);
	}
	
	public void add(Piece piece) {
		piece.alive=false;
		if(piece.color) {//implementar método que haga esto en gamescreen
			GameScreen.whitePieces.remove(piece);
		}else {
			GameScreen.blackPieces.remove(piece);
		}
		
		ParallelAction par = new ParallelAction();
		par.addAction(Actions.moveTo(getX()+20, getY()+(38*index)+20, 0.6f));
		par.addAction(Actions.sizeTo(38, 38, 0.6f));
		
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
		batch.begin();
		GraveDisposer.draw(batch, parentAlpha);
		batch.end();
		batch.begin();
		
		for(int i = 0; i< graveyard.size(); i++) {
			graveyard.get(i).draw(batch, parentAlpha);
		}
		
	}
}
