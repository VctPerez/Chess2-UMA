package elements.pieces;

import java.util.ArrayList;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

import elements.Board;
import elements.Piece;
import elements.Tile;
import interaccionFichero.LectorLineas;
import utils.AnimationActor;
import utils.Render;
import utils.Resources;

public class Miner extends Piece {

	public Miner(Boolean color, int x, int y,Board board) {
		super(color, Render.app.getManager().get(Resources.MINER_PATH, Texture.class), x, y,board);
	}
	
	@Override
	protected void updateXY(int dest_x, int dest_y) {
		Action dig = new Action() {
			public boolean act(float delta) {
				AnimationActor dig= new AnimationActor(0.13f, "dig.png", 3);
				dig.setPosition(getX(), getY());
				dig.setSize(board.getTile(x, y).getWidth(), board.getTile(x, y).getWidth());
				
				Render.GameScreen.stage.addActor(dig);
				return true;
			}
		};
		this.x = dest_x;
		this.y = dest_y;
		Tile tile = Render.GameScreen.board.getTile(x, y);
		Action digSfx = new Action() {
			Sound sound = Render.app.getManager().get(Resources.MINER_SOUND, Sound.class);
			public boolean act(float delta) {
				sound.play(0.5f);
				return true;
			}
		};
		
		
		Action digAnimation = new Action() {
			public boolean act(float delta) {
				AnimationActor digAnimation= new AnimationActor(0.13f, "digAnimation.png", 3);
				digAnimation.setPosition(getX(), getY());
				digAnimation.setSize(board.getTile(x, y).getWidth(), board.getTile(x, y).getWidth());
				
				Render.GameScreen.stage.addActor(digAnimation);
				return true;
			}
		};
		
		
		Action animation_2= new Action() {
			public boolean act(float delta) {
				sprite.setImage(Resources.VALKYRIE_ANIMATION2_PATH);
				return true;
			}
		};
		
		Action moveSfx = new Action() {
			Sound sound = Render.app.getManager().get(Resources.PIECEMOVE_SOUND, Sound.class);
			public boolean act(float delta) {
				sound.play();
				return true;
			}
		};
		SequenceAction sequence = new SequenceAction();
		
		sequence.addAction(digSfx);
		sequence.addAction(Actions.parallel(dig, Actions.sizeTo(0, 0, 0.5f)));
		
		sequence.addAction(Actions.parallel(Actions.moveTo(tile.getX(), tile.getY(), 0.6f)));
		sequence.addAction(Actions.parallel(dig, Actions.sizeTo(84, 84, 0.3f)));
		
		addAction(sequence);
		
	}
	

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}

	/**
	 * A�ade a movements todos los movimientos posibles del minero
	 * 
	 * @param x
	 * @param y
	 * @return
	 */

	@Override
	public ArrayList<Vector2> posibleMovements() {
		ArrayList<Vector2> movements = new ArrayList<>();
		
		if (color && !hasBeenMoved) {
			for (int i=1; i<9; i++) {
				for (int j=5;j<7;j++) {
					addMovement(i, j, board, movements);
				}
			}
			
			
		}else if (!color && !hasBeenMoved)  {
			for (int i=1; i<9; i++) {
				for (int j=3;j<5;j++) {
					addMovement(i, j, board, movements);
				}
			}
			
			
		}else {
			
			addMovement(x + 2, y , board, movements);
			addMovement(x - 2, y , board, movements);
			addMovement(x , y + 2, board, movements);
			addMovement(x , y - 2, board, movements);
			addMovement(x + 2 , y - 2, board, movements);
			addMovement(x + 2, y + 2, board, movements);
			addMovement(x - 2, y - 2, board, movements);
			addMovement(x - 2, y + 2, board, movements);
			
			
		}
		
		
		
		return movements;
	}

	public void addMovement(float x, float y, Board board, ArrayList<Vector2> movements) {
		
		if (!hasBeenMoved && board.getTile(x, y) != null && !sameColor(board.getTile(x, y).getPiece()) && board.getTile(x, y).getPiece()==null ) {
			movements.add(new Vector2(x,y) );
		}else if (board.getTile(x, y) != null && !sameColor(board.getTile(x, y).getPiece()) && hasBeenMoved){
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
				return Reader.leerTramo(48, 53);
			case "eng/":
				return Reader.leerTramo(50,55);
			default:
				throw new IllegalArgumentException("Configuración errónea");
		}
	}

	@Override
	public String toString() {
		String str = super.toString();
		return str.replace("X","Miner");
	}

}
