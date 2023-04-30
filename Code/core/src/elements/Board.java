package elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

import utils.Render;
import utils.Resources;

public class Board extends Actor {
	public int dim;
	public static Tile[][] board;
	public static Boolean boardAnimation = true;
	
//	private float Y_OFFSET = 24;
//	private float Tile_Size = (Gdx.graphics.getHeight() - 2 * Y_OFFSET) / 8;
//	private float X_OFFSET = Y_OFFSET + (Gdx.graphics.getWidth() - Gdx.graphics.getHeight()) / 2;
	
	private float Y_OFFSET = 24;
	private float Tile_Size = 84;
	private float X_OFFSET = 304;
	
	private float x_offset, y_offset, size;
	

	public void createFromDim(int dim, float size, float x_offset, float y_offset) {
		this.x_offset = x_offset;
		this.y_offset = y_offset;
		this.size =size; 
		
		int color = 1;
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				if (Render.hosting || dim!=8) {
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

	public Board(float size, float x_offset, float y_offset, int tam) {
		dim = tam;
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
				if(boardAnimation) {					
					if (Render.hosting || dim!=8) {
						board[i][j].setPosition(getX()+ (i * size), getY() + (j * size));
					} else {
						board[i][j].setPosition(getX()+(7*size - i*size), getY()+(7*size - j*size));
					}
				}
				board[i][j].draw(batch, parentAlpha);
			}
		}
	}
	
	public void animationMovement(float dest_x, float dest_y, float time) {
		Action endAnimation = new Action() {
			public boolean act(float delta) {
				boardAnimation = false;
				return true;
			}
		};
		SequenceAction sequence = new SequenceAction(Actions.moveTo(dest_x, dest_y, time), endAnimation);
		addAction(sequence);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				board[i][j].act(delta);
			}
		}
	}
}
