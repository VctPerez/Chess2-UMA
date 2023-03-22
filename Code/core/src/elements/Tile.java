package elements;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import game.chess.GameScreen;

public class Tile extends Actor{
	protected ShapeRenderer tile;
	protected Vector2 pos;	
	public Piece piece;
	
	public Tile(int matrix_x, int matrix_y, float coord_x, float coord_y, float tileSize, int color) {
		pos = new Vector2(matrix_x, matrix_y);
		tile = new ShapeRenderer();
		setPosition(coord_x, coord_y);
		setSize(tileSize, tileSize);
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
		tile.begin(ShapeType.Filled);
		tile.rect(getX(), getY(), getWidth(), getHeight());
		tile.setColor(getColor());
		tile.end();
		batch.begin();
		
		if(piece != null) {
			piece.setPosition(getX(), getY());
			piece.setSize(getWidth(),  getHeight());
			piece.draw(batch, parentAlpha);
		}
	}
	
	public void move(int x, int y) {
		if(piece!=null) {
			ArrayList<Vector2> movements = piece.getMovement(pos.x, pos.y);
		
		}
		GameScreen.board.getTile(x, y).piece = this.piece;
		this.piece = null;
	}
	
	public Piece getPiece() {
		return piece;
	}
	
	public void dispose() {
		tile.dispose();
		//piece.dispose();
	}
	
	public void act(float delta) {
		super.act(delta);
	}
}
