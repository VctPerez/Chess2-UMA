package elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Board extends Actor{
	public static Tile[][] board;
	//private final float X_OFFSET = 128;
	//private final float Tile_Size = (Gdx.graphics.getWidth() - 2*X_OFFSET)/8;
	private final float Y_OFFSET = 24;
	private final float Tile_Size = (Gdx.graphics.getHeight()-2*Y_OFFSET)/8;
	private final float X_OFFSET = Y_OFFSET + (Gdx.graphics.getWidth()-Gdx.graphics.getHeight())/2;
	
	public Board() {
		System.out.println(Tile_Size);
		board = new Tile[8][8];
		int color = 1;
		
		for(int i = 0; i<8; i++) {
			for(int j = 0; j<8; j++) {
				board[i][j]= new Tile(i+1, j+1, X_OFFSET+(i*Tile_Size), Y_OFFSET+(j*Tile_Size), Tile_Size, color);
				color = -color;
			}
			color = -color;
		}
		
	}
	/**
	 * Devuelve la casilla en las posicion [x][y] del tablero ([x-1][y-1] en la matriz de casillas)
	 * @param x
	 * @param y
	 * @return
	 */
	public Tile getTile(float x, float y) {
		Tile res = null;
		if(x>0 && x<=8 && y>0 && y<=8) {
			res = board[(int)x-1][(int)y-1];
		}
		return res;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		for(int i = 0; i<8; i++) {
			for(int j = 0; j<8; j++) {
				board[i][j].draw(batch, parentAlpha);
			}
		}
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		for(int i = 0; i<8; i++) {
			for(int j = 0; j<8; j++) {
				board[i][j].act(delta);
			}
		}
	}
}
