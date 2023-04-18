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
	private String piecePath;
	private Image piece, frame;
	private boolean showFrame;
	
	
	public TileButton(float x, float y, float tileSize) {
		tile = new ShapeRenderer();
		setPosition(x, y);
		setSize(tileSize, tileSize);
		tile.setColor(new Color(1f, 0.9921f, 0.90196f,1f));
	
		
		
		frame = new Image(Render.app.getManager().get(Resources.FRAME_PATH, Texture.class));
		frame.setSize(getWidth(), getWidth());
		frame.setPosition(getX(), getY());
		frame.setColor(1f, 0.87f, 0.09f, 1f);
		showFrame = false;
	}
	
	public void setPiece(String piecePath) {
		this.piecePath=piecePath;
		this.piece = new Image(Render.app.getManager().get(piecePath, Texture.class)); 
		this.piece.setSize(getWidth(), getHeight());
		this.piece.setPosition(getX(), getY());
	}
	
	public String getPiece() {
		return piecePath;
	}
	
	public void showFrame() {
		showFrame=true;
	}
	
	public void hideFrame() {
		showFrame=false;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.end();
		tile.begin(ShapeType.Filled);
		tile.rect(getX(), getY(), getWidth(), getHeight());
		tile.end();
		batch.begin();
		if(showFrame) {
			frame.draw(batch, parentAlpha);
		}
		
		if(piece!=null) {			
			piece.draw(batch, parentAlpha);
		}
	}
}
