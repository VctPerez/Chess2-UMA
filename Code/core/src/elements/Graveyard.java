package elements;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Graveyard extends Actor {
	private ArrayList<Piece> graveyard;
	protected ShapeRenderer frame;

	public Graveyard(float x, float y) {
		graveyard = new ArrayList<>();
		frame = new ShapeRenderer();
		setPosition(x, y);
		setSize(42,42*16);
		setColor(Color.BLACK);
	}
	
	public void add(Piece piece) {
		graveyard.add(piece);
	}
	
	public void draw(Batch batch, float parentAlpha) {
		batch.end();
		frame.begin(ShapeType.Line);
		frame.rect(getX(), getY(), getWidth(), getHeight());
		frame.setColor(getColor());
		
		frame.end();
		batch.begin();
		
		for(int i = 0; i< graveyard.size(); i++) {
			graveyard.get(i).setPosition(getX(), getY() + (42 * i));
			graveyard.get(i).setSize(42,  42);
			graveyard.get(i).draw(batch, parentAlpha);		
		}
		
	}
}