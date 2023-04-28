package elements;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;


import utils.Image;
import utils.Render;
import utils.Resources;

import java.util.Objects;

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
		frame.setColor(1f, 0.87f, 0.09f, 1f);
		highlight = false;
		attacked=false;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.end();
		tile.begin(ShapeType.Filled);
		tile.setProjectionMatrix(batch.getProjectionMatrix());
		tile.setTransformMatrix(batch.getTransformMatrix());
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
			frame.draw(batch, 0);
		}else if(attacked) {
			frame.sprt.setColor(Color.RED);
			frame.draw(batch, 0);
		}
		
		if(piece != null) {
			piece.draw(batch, parentAlpha);
		}
	}
	
	public void simulateMoveTo(Tile nextTile) {
		if(nextTile.getPiece()!=null) {
			nextTile.simulateSendPieceToGraveyard();
		}
		
		nextTile.setPiece(this.piece);
		this.piece = null;
	}
	
	public void moveTo(Tile nextTile) {
		if(nextTile.getPiece()!=null) {
			nextTile.sendPieceToGraveyard();
		}
		
		nextTile.setPiece(this.piece);
		this.piece = null;
	}
	
	public void move(int x, int y) {
		Tile nextTile = Render.GameScreen.board.getTile(x, y);
		piece.hasBeenMoved();
		
		piece.updateXY(x, y);
		
		moveTo(nextTile);
	}
	
	
	public void sendPieceToGraveyard() {
		if(piece!=null) {
			if(piece.color) {
				Render.GameScreen.graveyardWhite.add(piece);
			}else {
				Render.GameScreen.graveyardBlack.add(piece);
			}
			this.piece=null;
		}
	}
	
	public void simulateSendPieceToGraveyard() {
		if(piece!=null) {
			if(piece.color) {
				Render.GameScreen.graveyardWhite.simulateAdd(piece);
			}else {
				Render.GameScreen.graveyardBlack.simulateAdd(piece);
			}
			this.piece=null;
		}
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
	
	public Vector2 getPos() {
		return pos;
	}
	
	public void dispose() {
		tile.dispose();
		//piece.dispose();
	}
	@Override
	public void act(float delta) {
		if(piece!=null) {
			piece.act(delta);
		}
		super.act(delta);
	}

	@Override
	public String toString() {
		return "Tile " + pos.toString();
	}

	/**
	 * Mira si los Tiles están en el mismo sitio
	 */
	@Override
	public boolean equals(Object obj) {
		
		return(obj instanceof Tile && ((Tile) obj).getPos().equals(this.getPos())) ;
	}

	@Override
	public int hashCode() {
		return Objects.hash(pos);
	}
}
