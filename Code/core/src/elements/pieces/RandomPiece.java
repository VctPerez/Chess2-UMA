package elements.pieces;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import elements.Board;
import elements.Piece;
import interaccionFichero.LectorLineas;
import utils.Render;
import utils.Resources;

public class RandomPiece extends Piece{
	
	private int prev;
	
	public Piece actual;
	public Random rnd = new Random();

	public RandomPiece(Boolean color, int x, int y,Board board) {
		super(color, Render.app.getManager().get(Resources.RND_PATH, Texture.class), x, y,board);
		actual = new Pawn(color,x,y,board);
		this.setSprite(Resources.RND_PATH);
		prev=0;
	}
	
	public Piece Swap() {
		
		//Asegurarme que no se repitan piezas 
		int i;
		do {
			i = rnd.nextInt(5);
		} while (i == prev);
		
		prev=i;
		Piece nueva=null;
		
		switch(i) {
		case 0 : 
		nueva = new Pawn(this.color,this.x,this.y,this.board);
		this.setSprite(Resources.RND_PATH);
		break;
		case 1 : 
		nueva = new Bishop(this.color,this.x,this.y,this.board);
		this.setSprite(Resources.RND_BISHOP_PATH);
		break;
		case 2 : 
		nueva = new Knight(this.color,this.x,this.y,this.board);
		this.setSprite(Resources.RND_KNIGHT_PATH);
		break;
		case 3 : 
		nueva = new Queen(this.color,this.x,this.y,this.board);
		this.setSprite(Resources.RND_QUEEN_PATH);
		break;
		case 4 : 
		nueva = new Rook(this.color,this.x,this.y,this.board);
		this.setSprite(Resources.RND_QUEEN_PATH);
		break;
		}
		
		return nueva;
	}
	
	public RandomPiece() {
		super(Render.app.getManager().get(Resources.PAWN_PATH, Texture.class));
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}
	
	/**
	 * A�ade a movements todos los movimientos posibles del caballo
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	
	@Override
	public ArrayList<Vector2> posibleMovements() {
		if(this.hasBeenMoved) {
			actual = Swap();
		}
		this.hasBeenMoved=false;
		return actual.posibleMovements();
	}
	
	public void addMovement(float x, float y, Board board, ArrayList<Vector2> movements) {
		if (board.getTile(x, y) != null && !sameColor(board.getTile(x, y).getPiece())) {
			movements.add(new Vector2(x, y));
		}
	}
	
	
	public String getInfo() {
		 LectorLineas Reader, configReader;
		 configReader = new LectorLineas("files/config.txt");
		String config = configReader.leerLinea(1);
		Reader = new LectorLineas("files/lang/"+ config + "Clasicas.txt");
		switch (config){
			case "esp/":
				return Reader.leerTramo(56, 60);
			case "eng/":
				return Reader.leerTramo(41,45);
			default:
				throw new IllegalArgumentException("Configuración errónea");
		}
	}
	
	@Override
	public String toString() {
		String str = super.toString();
		return str.replace("X","RandomPiece");
	}
	
	
	}
