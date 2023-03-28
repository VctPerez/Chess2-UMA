package elements;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import game.chess.GameScreen;
import utils.Image;
import utils.Render;
import utils.Resources;

public class Tile extends Actor{
	protected ShapeRenderer tile;
	protected Vector2 pos;	
	public Piece piece;
	public Boolean highlight,attacked;
	private Image frame;
	
	public Tile(int matrix_x, int matrix_y, float coord_x, float coord_y, float tileSize, int color) {
		
		pos = new Vector2(matrix_x, matrix_y);
		tile = new ShapeRenderer();
		setPosition(coord_x, coord_y);
		setSize(tileSize, tileSize);
		if(color == 1) {
			setColor(new Color(0.1745f, 0.23f, 0.3f,1f));//color azul apagado que queda bastante bien
			//setColor(new Color(0.1745f, 0.1745f, 0.1745f,1f)); //color gris mas oscuro que las piezas
			
		}else {
			setColor(Color.WHITE);
		}
		piece = null;
		
		frame = new Image(Render.app.getManager().get(Resources.FRAME_PATH, Texture.class));
		frame.setSize(getWidth(), getWidth());
		frame.setPosition(getX(), getY());
		highlight = false;
		attacked=false;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.end();
		tile.begin(ShapeType.Filled);
		tile.rect(getX(), getY(), getWidth(), getHeight());
		
		if(highlight) {
			tile.setColor(0.97f, 0.9f, 0.33f, 1f);
		}else if(attacked) {			
			tile.setColor(Color.RED);
		}else {
			tile.setColor(getColor());
		}
		
		tile.end();
		batch.begin();
		
		if(highlight) {
			frame.sprt.setColor(Color.YELLOW);
			frame.draw(batch);
		}else if(attacked) {
			frame.sprt.setColor(Color.RED);
			frame.draw(batch);
		}
		
		if(piece != null) {
			piece.setPosition(getX(), getY());
			piece.setSize(getWidth(),  getHeight());
			piece.draw(batch, parentAlpha);
		}
	}
	
	public void move(int x, int y) {
		Tile nextTile = GameScreen.board.getTile(x, y);
		
		if(nextTile.getPiece()!=null) {
			nextTile.sendPieceToGraveyard();
		}
		
		this.piece.hasBeenMoved();
		
		
		nextTile.setPiece(this.piece);
		this.piece = null;
	}
	
	public void sendPieceToGraveyard() {
		if(piece.color) {
			GameScreen.graveyardBlack.add(piece);
		}else {
			GameScreen.graveyardWhite.add(piece);
		}
		this.piece=null;
	}
	
	/**
	 * Devuelve la pieza contenida en la casilla
	 * @return
	 */
	public Piece getPiece() {
		return piece;
	}
	
	public void setPiece(Piece piece) {
		this.piece = piece;
	}
	
	public void dispose() {
		tile.dispose();
		//piece.dispose();
	}
	
	public void act(float delta) {
		super.act(delta);
	}
}
