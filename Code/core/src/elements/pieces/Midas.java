package elements.pieces;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import elements.Board;
import elements.Piece;
import elements.Tile;
import interaccionFichero.LineReader;
import utils.Image;
import utils.Render;
import utils.Resources;

import java.util.ArrayList;

public class Midas extends Leader {
	
	private boolean validDirection;

	public Midas(Boolean color, int x, int y, Board board) {
		super(color, Resources.MIDAS_PATH, x, y, board);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}

	@Override
	protected void updateXY(int x, int y) {
		board.getTile(this.x, this.y).attacked = false;
		
		updateSprite(ate);
		super.updateXY(x, y);
		
		
		if(color) {
			Render.GameScreen.whiteKing.set(x, y);
		}else {
			
			Render.GameScreen.blackKing.set(x, y);
		}
	}
	public void updateSprite(int ate) {
        if (ate > 0 ){
        	if(ate<3) { //El replace cambia el . por el numero y el punto
            sprite = new Image(Render.app.getManager().get(getSpritePath().replace(".","1."),Texture.class));
        	}
            if(ate>=3) {
                sprite = new Image(Render.app.getManager().get(getSpritePath().replace(".","2."),Texture.class));
            }
        }
        	else {
            sprite = new Image(Render.app.getManager().get(getSpritePath(),Texture.class));
        }
	}
    
	
	protected void simulateMovement(Tile currentTile, Vector2 move, ArrayList<Vector2> removeMovements) {
		Tile nextTile = board.getTile((int)move.x,(int) move.y);
		Piece nextTilePiece = null;
		if(nextTile.getPiece()!=null) {
			nextTilePiece = nextTile.getPiece();
		}
		currentTile.simulateMoveTo(nextTile);
		
			//check king
			if(color && !isKingSafe(Render.GameScreen.blackPieces, new Vector2(move.x, move.y))) {
				removeMovements.add(move);
			}else if(!color && !isKingSafe(Render.GameScreen.whitePieces, new Vector2(move.x, move.y))) {
				removeMovements.add(move);
			}
			
		undoLastMovement(currentTile,  nextTile, nextTilePiece);
	}

	@Override
	public ArrayList<Vector2> posibleMovements() {
		ArrayList<Vector2> movements = new ArrayList<>();
		int direction;
		if (color) {
			direction = 1;
		} else {
			direction = -1;
		}

		if (super.ate == 0) {
			checkDirection(x,y,0,1,1,movements);
			checkDirection(x,y,0,-1,1,movements);
			checkDirection(x,y,1,0,1,movements);
			checkDirection(x,y,-1,0,1,movements);
		}else if (super.ate == 1 || super.ate == 2) {
			checkDirection(x,y,0,1,1,movements);
			checkDirection(x,y,0,-1,1,movements);
			checkDirection(x,y,1,0,1,movements);
			checkDirection(x,y,-1,0,1,movements);
			checkDirection(x,y,1,1,1,movements);
			checkDirection(x,y,-1,-1,1,movements);
			checkDirection(x,y,-1,1,1,movements);
			checkDirection(x,y,1,-1,1,movements);	
		}else if (super.ate >= 3) {
			checkDirection(x,y,0,1,2,movements);
			checkDirection(x,y,0,-1,2,movements);
			checkDirection(x,y,1,0,2,movements);
			checkDirection(x,y,-1,0,2,movements);
			checkDirection(x,y,1,1,2,movements);
			checkDirection(x,y,-1,-1,2,movements);
			checkDirection(x,y,-1,1,2,movements);
			checkDirection(x,y,1,-1,2,movements);
	
			
		}

		return movements;
	}

	public void addMovement(float x, float y, Board board, ArrayList<Vector2> movements) {
		if (board.getTile(x, y) != null && !sameColor(board.getTile(x, y).getPiece())) {
			movements.add(new Vector2(x, y));
		}
	}
	
	private void checkDirection(float x, float y, int i, int j,int n, ArrayList<Vector2> movements) {
		validDirection = true;
		Vector2 mov;
		int k = 1;
		
		while(validDirection && k<=n) {
			mov = new Vector2(x + i*k, y + j*k);
			if(checkBoard(board, mov.x, mov.y)) {
				movements.add(mov);
			}
			k++;
		}
	}
	
	private Boolean checkBoard(Board board, float x, float y) {
		Boolean res = true;
		
		if(board.getTile(x, y)==null) {
			res = false;
			validDirection = false;
		}else {
			if(board.getTile(x, y).getPiece()!=null && sameColor(board.getTile(x, y).getPiece())) {
				res = false;
				validDirection = false;
			}else if(board.getTile(x, y).getPiece()!=null && !sameColor(board.getTile(x, y).getPiece())) {
				res = true;
				validDirection = false;
			}
		}
		return res;
	}

	public void dispose() {
		sprite.dispose();
	}

	@Override
	public String getInfo() {
		LineReader Reader, configReader;
		configReader = new LineReader("files/config.txt");
		String config = configReader.readLine(1);
		Reader = new LineReader("files/lang/" + config + "Modified.txt");
		switch (config) {
		case "esp/":
			return Reader.readSection(34, 41);
		case "eng/":
			return Reader.readSection(35, 42);
		default:
			throw new IllegalArgumentException("Configuración errónea");
		}
	}
	
	private boolean isSafe(float start, float y, float dest) {
		boolean res=true;
			for(int i=(int) start+1; i<dest;i++) {
				if(board.getTile(i, y).piece!=null) {
					res=false;
				}else if (color && !isTileSafe(Render.GameScreen.blackPieces, new Vector2(i, y))) {
					res = false;
				} else if (!color && !isTileSafe(Render.GameScreen.whitePieces, new Vector2(i, y))) {
					res = false;
				}
			}
		return res;
	}
	
	protected boolean isTileSafe(ArrayList<Piece> pieces, Vector2 pos) {
		boolean isSafe = true;
		for (Piece piece : pieces) {
			if(piece.hasBeenMoved || !(piece instanceof King)) {
				if (piece.posibleMovements().contains(pos)) {
					isSafe = false;
				}
			}
		}
		return isSafe;
	}

	/**
	 * Devuelve el path de Midas con su color, pero en fase 1
	 * @return path del sprite
	 */
	@Override
	public String getSpritePath(){
		return color?Resources.MIDAS_PATH:Resources.BLACK_MIDAS_PATH;
	}
	
	@Override
	public Boolean sameColor(Piece piece) {
		boolean same=false;
		if(piece!=null) {
			same=color==piece.color();
		}
		return same;
	}

	@Override
	public String toString() {
		String str = super.toString();
		return str.replace("X", "Marshal");
	}

}
