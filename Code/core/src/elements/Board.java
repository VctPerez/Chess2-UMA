package elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import utils.Render;

public class Board extends Actor {
	public int dim;
	public static Tile[][] board;
	// private final float X_OFFSET = 128;
	// private final float Tile_Size = (Gdx.graphics.getWidth() - 2*X_OFFSET)/8;
	private float Y_OFFSET = 24;
	private float Tile_Size = (Gdx.graphics.getHeight() - 2 * Y_OFFSET) / 8;
	private float X_OFFSET = Y_OFFSET + (Gdx.graphics.getWidth() - Gdx.graphics.getHeight()) / 2;

	public void createFromDim(int dim, float size, float x_offset, float y_offset) {
		int color = 1;
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				if (Render.hosting || dim==5) {
					board[i][j] = new Tile(i + 1, j + 1, x_offset + (i * size), y_offset + (j * size),
							size, color);
				} else {
					board[i][j]= new Tile(i+1, j+1, x_offset+(7*size - i*size), y_offset+(7*size - j*size), size, color);
					
				}
				color = -color;
			}
			if(dim%2==0) {
				color = -color;
			}
			
		}
	}

	public Board() {
		System.out.println(Tile_Size);
		dim = 8;
		board = new Tile[dim][dim];
		setPosition(X_OFFSET, Y_OFFSET);
		setSize(dim * Tile_Size, dim * Tile_Size);
		createFromDim(dim, Tile_Size, X_OFFSET, Y_OFFSET);
	}

	public Board(float size, float x_offset, float y_offset) {
		dim = 5;
		board = new Tile[dim][dim];
		setPosition(x_offset, y_offset);
		setSize(dim * size, dim * size);
		createFromDim(dim, size, x_offset, y_offset);
	}

	/**
	 * Devuelve la casilla en las posicion [x][y] del tablero ([x-1][y-1] en la
	 * matriz de casillas)
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public Tile getTile(float x, float y) {
		Tile res = null;
		if (x > 0 && x <= dim && y > 0 && y <= dim) {
			res = board[(int) x - 1][(int) y - 1];
		}
		return res;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				board[i][j].draw(batch, parentAlpha);
			}
		}
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				board[i][j].act(delta);
			}
		}
	}
}
