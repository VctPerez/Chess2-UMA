package game.chess;

import elements.Tile;

import utils.Render;

import java.io.IOException;

public class OnlineGameScreen extends GameScreen {
	@Override
	public void update(Tile tile) {
		super.update(tile);
		System.out.println(moved);
		try {
			if (Render.hosting == PLAYER) {
				if(Render.hosting) {
					if(moved) {
						String movement = currentTile.getPos().x + "," + currentTile.getPos().y + "-" + nextTile.getPos().x + "," + nextTile.getPos().y;
						Render.host.sendMessage(movement);
						System.out.println("mensaje enviado from host");
						PLAYER = false;
						moved = false;
					}
				}else{
					if(moved){
						String movement = tile.getPos().x + "," + tile.getPos().y + "-" + nextTile.getPos().x + "," + nextTile.getPos().y;
						Render.guest.sendMessage(movement);
						System.out.println("mensaje enviado con: "+ movement);
						System.out.println("mensaje enviado from guest");
						PLAYER = true;
						moved = false;
					}
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}