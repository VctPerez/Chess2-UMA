package elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Background extends Actor{
	private ShapeRenderer fondo;
	private Color c;
	
	public Background() {
		fondo = new ShapeRenderer();
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.end();
		fondo.setProjectionMatrix(batch.getProjectionMatrix());
		fondo.setTransformMatrix(batch.getTransformMatrix());
		
		fondo.begin(ShapeType.Filled);
	
		if(c==null) {
			fondo.rect(getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation(), Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN);
		}else {
			fondo.rect(getX(), getY(), getWidth(), getHeight());
			fondo.setColor(this.c);
		}
		
		fondo.end();
		batch.begin();
	}

	public void setColor(Color c) {
		this.c=c;
	}

}
