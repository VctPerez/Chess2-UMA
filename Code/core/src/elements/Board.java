package elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Board extends Actor{
	public static Tile[][] board;
	//private final float X_OFFSET = 128;
	//private final float Tile_Size = (Gdx.graphics.getWidth() - 2*X_OFFSET)/8;
	private final float Y_OFFSET = 15;
	private final float Tile_Size = (Gdx.graphics.getHeight()-2*Y_OFFSET)/8;
	private final float X_OFFSET = Y_OFFSET + (Gdx.graphics.getWidth()-Gdx.graphics.getHeight())/2;
	
	public Board() {
		board = new Tile[8][8];
		int color = 1;
		
		for(int i = 0; i<8; i++) {
			for(int j = 0; j<8; j++) {
				board[i][j]= new Tile(X_OFFSET+(i*Tile_Size), Y_OFFSET+(j*Tile_Size), Tile_Size, color);
				color = -color;
			}
			color = -color;
		}
		
	}
	/**
	 * Devuelve la casilla en las posicion [x][y] del tablero
	 * @param x
	 * @param y
	 * @return
	 */
	public Tile getTile(int x, int y) {
		return board[x-1][y-1];
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		for(int i = 0; i<8; i++) {
			for(int j = 0; j<8; j++) {
				board[i][j].draw(batch, parentAlpha);
			}
		}
	}
	
	public void act(float delta) {
		super.act(delta);
	}
}
