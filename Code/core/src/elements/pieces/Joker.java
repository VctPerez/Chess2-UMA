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

public class Joker extends Piece{
	
	private Vector2 prev;
	public Piece current;
	public Random rnd = new Random();

	public Joker(Boolean color, int x, int y,Board board) {
		super(color, Render.app.getManager().get(Resources.RND_PATH, Texture.class), x, y,board);
		current = new Bishop(color,x,y,board);
		this.setSprite(Resources.RND_PATH);
		prev = new Vector2 (0,0);
	}
	
	public Piece Swap() {
		
		//Probabilidades:
		//Dama:15
		//Peón:15
		//Alfil:30
		//Caballo:20
		//Torre:20
		
		//Asegurarme que no se repitan piezas 
		int i=0;
		do {
			i = rnd.nextInt(100) + 1 ;
		} while (i>=prev.x && i<= prev.y);
		
		Piece nueva=null;
		
		if(i>=1 && i<=30) {
			nueva = new Bishop(this.color,this.x,this.y,this.board);
			//this.setSprite(Resources.RND_PATH);
			prev.x=1;
			prev.y=30;
		}else if(i>=31 && i<=45){
			nueva = new Pawn(this.color,this.x,this.y,this.board);
			//this.setSprite(Resources.RND_PAWN_PATH);
			prev.x=31;
			prev.y=45;
		}else if(i>=46 && i<=65){
			nueva = new Knight(this.color,this.x,this.y,this.board);
			//this.setSprite(Resources.RND_KNIGHT_PATH);
			prev.x=46;
			prev.y=65;
		}else if(i>=66 && i<=80){
			nueva = new Queen(this.color,this.x,this.y,this.board);
			//this.setSprite(Resources.RND_QUEEN_PATH);
			prev.x=66;
			prev.y=80;
		}else {
			nueva = new Rook(this.color,this.x,this.y,this.board);
			//this.setSprite(Resources.RND_ROOK_PATH);
			prev.x=81;
			prev.y=100;
		}
		
		return nueva;
	}
	
	public Joker() {
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
			current = Swap();
		}
		Vector2 pos = this.getPos();
		ArrayList<Vector2> movements = new ArrayList<>();
		System.out.println();pos.toString();
		this.hasBeenMoved=false;
		if(current.posibleMovements().isEmpty() && (this.color() && (int)pos.y==8)) {
			movements.add(new Vector2(pos.x,pos.y-1));
		}else if(current.posibleMovements().isEmpty() && (!this.color() && (int)pos.y==1)) {
			movements.add(new Vector2(pos.x,pos.y+1));
		}else {
			movements = current.posibleMovements();
		}
		return movements;
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
		Reader = new LectorLineas("files/lang/"+ config + "Modified.txt");
		switch (config){
			case "esp/":
				return Reader.leerTramo(1, 6);
			case "eng/":
				return Reader.leerTramo(1,5);
			default:
				throw new IllegalArgumentException("Configuración errónea");
		}
	}
	
	@Override
	public String toString() {
		String str = super.toString();
		return str.replace("X","Joker");
	}
	
	
	}
