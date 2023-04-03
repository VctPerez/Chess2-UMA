package elements;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;

import elements.pieces.Bishop;
import elements.pieces.Knight;
import elements.pieces.Queen;
import elements.pieces.Rook;
import game.chess.GameScreen;
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
		
		if(tile.getPiece().color()) {
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

	
	}
	
	private void update(float parentAlpha) {
		if(isClicked()) {
            int current_y = calculateY();
            ArrayList<Piece> pieces;
            
            if(tile.getPiece().color()) {
            	pieces = GameScreen.whitePieces;
            }else {
            	pieces = GameScreen.blackPieces;
            }
            tile.getPiece().remove();
            
            switch (current_y) {
			case 4:
				pieces.remove(tile.getPiece());
				tile.setPiece(new Queen(tile.getPiece().color(), (int)tile.getPos().x, (int)tile.getPos().y));
				pieces.add(tile.getPiece());
				break;
			case 3:
				pieces.remove(tile.getPiece());
				tile.setPiece(new Knight(tile.getPiece().color(), (int)tile.getPos().x, (int)tile.getPos().y));
				pieces.add(tile.getPiece());
				break;
			case 2:
				pieces.remove(tile.getPiece());
				tile.setPiece(new Rook(tile.getPiece().color(), (int)tile.getPos().x, (int)tile.getPos().y));
				pieces.add(tile.getPiece());
				break;
			case 1:
				pieces.remove(tile.getPiece());
				tile.setPiece(new Bishop(tile.getPiece().color(), (int)tile.getPos().x, (int)tile.getPos().y));
				pieces.add(tile.getPiece());
				break;
			default:
				break;
			}
            GameScreen.promoting = false;
            remove();
		}
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		this.toFront();
		update(parentAlpha);
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
			piece.draw(batch);
		}
	}
	
	private boolean isClicked() {
		return Render.inputs.isClicked() && Render.inputs.mouseX >= getX()
				&& Render.inputs.mouseX <= getX() + getWidth()
				&& Render.inputs.mouseY >= getY()
				&& Render.inputs.mouseY <= getY() + getHeight();
	}

	private int calculateY() {
		return (int) Math.ceil((Render.inputs.mouseY - getY()) / 84);
	}
}
