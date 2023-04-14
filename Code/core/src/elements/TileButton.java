package elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;

import utils.Image;
import utils.Render;
import utils.Resources;

public class TileButton extends Actor{
	private ShapeRenderer tile;
	private Image piece, frame;
	
	
	public TileButton(float x, float y, float tileSize) {
		tile = new ShapeRenderer();
		setPosition(x, y);
		setSize(tileSize, tileSize);
		setColor(Color.WHITE);
	
		
		
		frame = new Image(Render.app.getManager().get(Resources.FRAME_PATH, Texture.class));
		frame.setSize(getWidth(), getWidth());
		frame.setPosition(getX(), getY());
	}
	
	public void setPiece(String piece) {
		this.piece = new Image(Render.app.getManager().get(piece, Texture.class)); 
		this.piece.setSize(getWidth(), getHeight());
		this.piece.setPosition(getX(), getY());
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.end();
		tile.begin(ShapeType.Filled);
		tile.rect(getX(), getY(), getWidth(), getHeight());
		tile.end();
		batch.begin();
		if(piece!=null) {			
			piece.draw(batch, parentAlpha);
		}
	}
}
