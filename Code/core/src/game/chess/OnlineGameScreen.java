package game.chess;

import elements.Tile;
import utils.Parser;
import utils.Render;

import java.io.IOException;

import com.badlogic.gdx.Gdx;

public class OnlineGameScreen extends GameScreen {
	@Override
	public void render(float delta) {
		super.render(delta);

		updateOnlineBoard();
	}
	
	@Override
	public void checkSurrender() {
		if(super.surrender.isPressed()) {
			if (Render.hosting) {
				System.out.println("JAQUE MATE AL REY BLANCO");
				super.results.setWinner("NEGRO");
				super.showPopup = true;
			} else {
				System.out.println("JAQUE MATE AL REY NEGRO");
				super.results.setWinner("BLANCO");
				super.showPopup = true;
			}
		}
	}
	
	
	@Override
	public void update(Tile tile) {
		super.update(tile);
		System.out.println(moved);
		try {
			if (Render.hosting == PLAYER) {//meter estos metodos en las clases host y guest y que se llamen sendMovement
				if(Render.hosting) {
					if(moved) {
						String movement = Parser.parseMovementToString(currentTile, nextTile);
						Render.host.sendMessage(movement);
						System.out.println("mensaje enviado from host");
						System.out.println("mensaje enviado con: "+ movement);
						PLAYER = false;
						moved = false;
					}
				}else{
					if(moved){
						String movement = Parser.parseMovementToString(currentTile, nextTile);
						Render.guest.sendMessage(movement);
						System.out.println("mensaje enviado from guest");
						System.out.println("mensaje enviado con: "+ movement);
						PLAYER = true;
						moved = false;
					}
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void updateOnlineBoard(){
		if(Render.hosting != PLAYER){//meter estos metodos en las clases host y guest y que se llamen recieveMovement?
			if(!Render.hosting) {
				if(!Render.guest.getMessage().equals("")){
					System.out.println("movimiento de blancas: " +Render.guest.getMessage());
					Parser.parseStringToMovement(Render.guest.getMessage());
					PLAYER = false;
					Render.guest.resetMessage();
				}
			}else{
				if(!Render.host.getMessage().equals("")){
					System.out.println("movimiento de negras: " + Render.host.getMessage());
					Parser.parseStringToMovement(Render.host.getMessage());
					PLAYER = true;
					Render.host.resetMessage();
				}
			}
		}
	}
	
	@Override
	protected void select(Tile tile) {
		if (tile.getPiece() != null && tile.getPiece().color() == Render.hosting && Render.hosting == PLAYER) {

			currentTile_validMovements = (tile.getPiece().getValidMovements());

			highlight(tile.getPiece().color());

			System.out.println(currentTile_validMovements.toString());
			isPieceSelected = true;
		}
	}

}