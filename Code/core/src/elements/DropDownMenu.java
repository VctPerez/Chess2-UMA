package elements;

import java.io.IOException;
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
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import elements.pieces.Bishop;
import elements.pieces.Knight;
import elements.pieces.Queen;
import elements.pieces.Rook;
import utils.Image;
import utils.Parser;
import utils.Render;
import utils.Resources;

public class DropDownMenu extends Actor{
	
	private final float Y_OFFSET = 24;
	private final float Tile_Size = 84;
	
	private Tile tile;
	private ShapeRenderer menu;
	private ArrayList<Image> pieces;
	public boolean promoted = false;
	public boolean online = false;
	public boolean player;
	
	//private ArrayList<Tile> menu;
	
	public DropDownMenu(Tile tile, final boolean player, boolean online) {
		this.tile = tile;
		this.online = online;
		this.player = player;
		
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
		
		final ArrayList<String> draft;
		if(player) {						
			draft = Render.player1Draft;
		}else {
			draft = Render.player2Draft;
		}
		
		
		pieces.add(new Image(Render.app.getManager().get(draft.get(3), Texture.class)));
		pieces.add(new Image(Render.app.getManager().get(draft.get(2), Texture.class)));
		pieces.add(new Image(Render.app.getManager().get(draft.get(1), Texture.class)));
		pieces.add(new Image(Render.app.getManager().get(draft.get(4), Texture.class)));
		
		addCaptureListener(new InputListener() { 
			@Override
			    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
						update(y, draft);
			    return true;
			    }
			} );

	
	}
	
	private void update(float y, ArrayList<String> draft) {
            int current_y = (int) Math.ceil(y  / 84);
            ArrayList<Piece> pieces;
            Piece formerPiece = tile.getPiece();
            Piece newPiece = null;
            String newPiecePath = "";
            
            if(formerPiece.color()) {
            	pieces = Render.GameScreen.whitePieces;
            }else {
            	pieces = Render.GameScreen.blackPieces;
            }
            pieces.remove(formerPiece);
            formerPiece.remove();
            
            switch (current_y) {
			case 4:
				newPiece = Parser.getPieceFromPath(draft.get(4), formerPiece.color(), (int)tile.getPos().x, (int)tile.getPos().y,Render.GameScreen.board);
				newPiecePath = draft.get(4);
				break;
			case 3:
				newPiece = Parser.getPieceFromPath(draft.get(1), formerPiece.color(), (int)tile.getPos().x, (int)tile.getPos().y,Render.GameScreen.board);
				newPiecePath = draft.get(1);
				break;
			case 2:
				newPiece = Parser.getPieceFromPath(draft.get(2), formerPiece.color(), (int)tile.getPos().x, (int)tile.getPos().y,Render.GameScreen.board);
				newPiecePath = draft.get(2);
				break;
			case 1:
				newPiece = Parser.getPieceFromPath(draft.get(3), formerPiece.color(), (int)tile.getPos().x, (int)tile.getPos().y,Render.GameScreen.board);
				newPiecePath = draft.get(3);
				break;
			default:
				break;
			}
            tile.setPiece(newPiece);
            pieces.add(newPiece);
            newPiece.setTouchable(Touchable.disabled);	
			newPiece.setSize(84, 84);
            Render.GameScreen.stage.addActor(newPiece);
            Render.GameScreen.promoting = false;
            Render.GameScreen.mateControl(tile.getPos().x, tile.getPos().y);
            
            if(online) {
            	sendPiece(newPiecePath);
            }
            remove();	
	}
	
	private void sendPiece(String path) {
		try {//meter este metodo en host y guest y que se llame sendPromotion
			String movement = Parser.parseMovementToString(Render.GameScreen.currentTile, Render.GameScreen.nextTile);
			movement += "-";
			movement += path;
			if(player) {
				Render.host.sendMessage(movement);				
//				System.out.println("mensaje DE PROMOCION enviado from host");
				Render.GameScreen.PLAYER = false;
				Render.GameScreen.moved=false;
			}else {
				Render.guest.sendMessage(movement);
//				System.out.println("mensaje DE PROMOCION enviado from guest");
				Render.GameScreen.PLAYER = true;
				Render.GameScreen.moved=false;

			}
			String[] params = movement.split("-");
//			System.out.println("mensaje enviado con: "+ movement + " | tamaño: "+params.length);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		this.toFront();

		batch.end();
		menu.begin(ShapeType.Filled);
		menu.setProjectionMatrix(batch.getProjectionMatrix());
		menu.setTransformMatrix(batch.getTransformMatrix());
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
