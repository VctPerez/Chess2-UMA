package game.chess;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Fondo extends Actor{
	private ShapeRenderer fondo;
	
	public Fondo() {
		fondo = new ShapeRenderer();
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.end();
		fondo.setProjectionMatrix(batch.getProjectionMatrix());
		fondo.setTransformMatrix(batch.getTransformMatrix());
		
		fondo.begin(ShapeType.Filled);
	
		fondo.rect(getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation(), Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN);
		//fondo.rect(0, 0, 100, 200);
		//fondo.setColor(Color.RED);
		fondo.end();
		batch.begin();
	}
	
	

}
