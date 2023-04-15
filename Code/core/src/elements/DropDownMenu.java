package elements;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import elements.pieces.Bishop;
import elements.pieces.Knight;
import elements.pieces.Queen;
import elements.pieces.Rook;
import game.chess.LocalGameScreen;
import utils.Image;
import utils.Render;
import utils.Resources;

public class DropDownMenu extends Actor{
	
	private final float Y_OFFSET = 24;
	private final float Tile_Size = (Gdx.graphics.getHeight()-2*Y_OFFSET)/8;
	
	private Tile tile;
	private ShapeRenderer menu;
	private ArrayList<Image> pieces;
	public boolean promoted = false;
	
	//private ArrayList<Tile> menu;
	
	public DropDownMenu(Tile tile) {
		this.tile = tile;
		
		if(!(((tile.getPiece().color() || Render.hosting)) && !(tile.getPiece().color() && Render.hosting))) {
			setPosition(tile.getX(), tile.getY() - 4 * tile.getWidth());
			setSize(tile.getWidth(), 4 * tile.getWidth());
			setColor(new Color(0.3745f, 0.23f, 0.3f,1f));			
		}else {
			setPosition(tile.getX(), tile.getY() + tile.getWidth());
			setSize(tile.getWidth(), 4 * tile.getWidth());
			setColor(new Color(0.3745f, 0.23f, 0.3f,1f));

		}
		
		menu = new ShapeRenderer();
		pieces = new ArrayList<>();
		
		pieces.add(new Image(Render.app.getManager().get(Resources.BISHOP_PATH, Texture.class)));
		pieces.add(new Image(Render.app.getManager().get(Resources.ROOK_PATH, Texture.class)));
		pieces.add(new Image(Render.app.getManager().get(Resources.KNIGHT_PATH, Texture.class)));
		pieces.add(new Image(Render.app.getManager().get(Resources.QUEEN_PATH, Texture.class)));
		
		addCaptureListener(new InputListener() { 
			@Override
			    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
						
					update(y);
				
			    return true;
			    }
			} );

	
	}
	
	private void update(float y) {
            int current_y = (int) Math.ceil(y  / 84);
            ArrayList<Piece> pieces;
            Piece formerPiece = tile.getPiece();
            Piece newPiece = null;
            
            if(formerPiece.color()) {
            	pieces = LocalGameScreen.whitePieces;
            }else {
            	pieces = LocalGameScreen.blackPieces;
            }
            formerPiece.remove();
            
            switch (current_y) {
			case 4:
				pieces.remove(formerPiece);
				newPiece = new Queen(formerPiece.color(), (int)tile.getPos().x, (int)tile.getPos().y,LocalGameScreen.board);
				tile.setPiece(newPiece);
				pieces.add(formerPiece);            	
            	LocalGameScreen.stage.addActor(newPiece);
				break;
			case 3:
				pieces.remove(formerPiece);
				newPiece = new Knight(formerPiece.color(), (int)tile.getPos().x, (int)tile.getPos().y,LocalGameScreen.board);
				tile.setPiece(newPiece);
				pieces.add(formerPiece);            	
            	LocalGameScreen.stage.addActor(newPiece);
				break;
			case 2:
				pieces.remove(formerPiece);
				newPiece = new Rook(formerPiece.color(), (int)tile.getPos().x, (int)tile.getPos().y,LocalGameScreen.board);
				tile.setPiece(newPiece);
				pieces.add(formerPiece);            	
            	LocalGameScreen.stage.addActor(newPiece);
				break;
			case 1:
				pieces.remove(formerPiece);
				newPiece = new Bishop(formerPiece.color(), (int)tile.getPos().x, (int)tile.getPos().y,LocalGameScreen.board);
				tile.setPiece(newPiece);
				pieces.add(formerPiece);            	
            	LocalGameScreen.stage.addActor(newPiece);
				break;
			default:
				break;
			}
            LocalGameScreen.promoting = false;
            LocalGameScreen.mateControl(tile.getPos().x, tile.getPos().y);
            remove();
		
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		this.toFront();

		batch.end();
		menu.begin(ShapeType.Filled);
		menu.rect(getX(), getY(), getWidth(), getHeight());
		menu.setColor(getColor());
		menu.end();
		batch.begin();
		for(int i = 0; i<4; i++) {
			Image piece = pieces.get(i);
			piece.setPosition(getX(), (getY()+i*Tile_Size));
			piece.setSize(Tile_Size, Tile_Size);
			piece.draw(batch, 0);
		}
	}
	
}
