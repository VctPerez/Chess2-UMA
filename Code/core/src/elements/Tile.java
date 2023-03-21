package elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;

import game.chess.GameScreen;

public class Tile extends Actor{
	protected ShapeRenderer sprite;
	
	public Piece piece;
	
	public Tile(float x, float y, float tileSize, int color) {
		
		sprite = new ShapeRenderer();
		setPosition(x,y);
		setSize(tileSize,tileSize);
		if(color == 1) {
			setColor(Color.BLACK);
			
		}else {
			setColor(Color.WHITE);
		}
		piece = null;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.end();
		sprite.begin(ShapeType.Filled);
		sprite.rect(getX(), getY(), getWidth(), getHeight());
		sprite.setColor(getColor());
		sprite.end();
		batch.begin();
		
		if(piece != null) {
			piece.setPosition(getX(), getY());
			piece.setSize(getWidth(),  getHeight());
			piece.draw(batch, parentAlpha);
		}
	}
	
	public void move(int x, int y) {
		GameScreen.board.getTile(x, y).piece = this.piece;
		this.piece = null;
	}
	
	public void dispose() {
		sprite.dispose();
		//piece.dispose();
	}
	
	public void act(float delta) {
		super.act(delta);
	}
}
