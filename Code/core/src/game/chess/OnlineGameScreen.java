package game.chess;

import elements.DropDownMenu;
import elements.Tile;
import utils.Parser;
import utils.Render;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class OnlineGameScreen extends GameScreen {
	private boolean finished = false;
	
	@Override
	public void show() {
		super.show();
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(stage);
		inputMultiplexer.addProcessor(Render.inputs);
		     
		Gdx.input.setInputProcessor(inputMultiplexer);
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		checkDraw();
		try {
			if (!finished) {
				if (!whiteCheckMate && !blackCheckMate)
					updateOnlineBoard();
//				} else {
//					finished = true;
//					if (Render.hosting) {
//						Render.host.stopHosting();
//					} else {
//						Render.guest.disconnect();
//					}
//				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void checkDraw() {
		
		
	}

	@Override
	protected void checkPromotion(float next_x, float next_y) {
		if (next_y == 8 && nextTile.getPiece().color()) {
			if (Render.hosting) {
				promoting = true;
				DropDownMenu menu = new DropDownMenu(nextTile, Render.hosting, true);
				stage.addActor(menu);
			}
		} else if (next_y == 1 && !nextTile.getPiece().color()) {
			if (!Render.hosting) {
				promoting = true;
				DropDownMenu menu = new DropDownMenu(nextTile, Render.hosting, true);
				stage.addActor(menu);
			}
		}

	}

	@Override
	public void checkSurrender() {
		try {
			if (super.surrender.isPressed()) {
				if (Render.hosting) {
					Render.host.sendMessage("RENDICION");
					System.out.println("Rendicion Blanca");
					results.setWinnerSurrender("NEGRO");
					showPopup = true;
					whiteCheckMate = true;
				} else {
					Render.guest.sendMessage("RENDICION");
					System.out.println("Rendicion Negra");
					results.setWinnerSurrender("BLANCO");
					showPopup = true;
					blackCheckMate = true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Tile tile) {
		super.update(tile);
		if (!promoting) {
			try {
				if (Render.hosting == PLAYER && moved) {// meter estos metodos en las clases host y guest y que se
														// llamen sendMovement
					String movement = Parser.parseMovementToString(currentTile, nextTile);
					if (Render.hosting) {
						Render.host.sendMessage(movement);
//						System.out.println("mensaje enviado from host");
//						System.out.println("mensaje enviado con: "+ movement);
						PLAYER = false;
					} else {
						Render.guest.sendMessage(movement);
//						System.out.println("mensaje enviado from guest");
//						System.out.println("mensaje enviado con: "+ movement);
						PLAYER = true;
					}
					moved = false;
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private void updateOnlineBoard() throws IOException {
		if (Render.hosting != PLAYER) {// meter estos metodos en las clases host y guest y que se llamen
										// recieveMovement?
			if (!Render.hosting) {
				if (!Render.guest.getMessage().equals("")) {
					if (Render.guest.getMessage().equals("RENDICION")) {
						System.out.println("Rendicion Blanca");
						results.setWinnerSurrender("NEGRO");
						showPopup = true;
						whiteCheckMate = true;
					} else if (Render.guest.getMessage().equals("EMPATE")) {
						System.out.println("GUEST RECIBE EMPATE");
						dbox.toFront();
						draw.setTouchable(Touchable.disabled);
						stage.addActor(dbox.getCheck()); 
						stage.addActor(dbox.getCross());
					} else if(Render.guest.getMessage().equals("ACEPTAR")) {
						results.setDraw();
						showPopup = true;	
						blackCheckMate=true;
					}else if(Render.guest.getMessage().equals("RECHAZAR")){
						draw.setTouchable(Touchable.enabled);
					}else {
						System.out.println("movimiento de blancas: " + Render.guest.getMessage());
						Parser.parseStringToMovement(Render.guest.getMessage());
						PLAYER = false;
					}
					Render.guest.resetMessage();

				}
			} else {
				if (!Render.host.getMessage().equals("")) {
					if (Render.host.getMessage().equals("RENDICION")) {
						System.out.println("Rendicion Negra");

						results.setWinnerSurrender("BLANCO");
						showPopup = true;
						blackCheckMate = true;
					} else if(Render.host.getMessage().equals("EMPATE")) {
						System.out.println("HOST RECIBE EMPATE");
						dbox.toFront();
						draw.setTouchable(Touchable.disabled);
						stage.addActor(dbox.getCheck()); 
						stage.addActor(dbox.getCross());
					}else if(Render.host.getMessage().equals("ACEPTAR")) {
						results.setDraw();
						showPopup = true;
						blackCheckMate = true;
					}else if(Render.host.getMessage().equals("RECHAZAR")){
						
					}else {
						System.out.println("movimiento de negras: " + Render.host.getMessage());
						Parser.parseStringToMovement(Render.host.getMessage());
						PLAYER = true;
					}
					Render.host.resetMessage();

				}
			}
		} else {
			if (!Render.hosting) {
				if (!Render.guest.getMessage().equals("")) {
					if (Render.guest.getMessage().equals("RENDICION")) {
						System.out.println("Rendicion Blanca");
						results.setWinnerSurrender("NEGRO");
						showPopup = true;
						blackCheckMate = true;
					}else if(Render.guest.getMessage().equals("EMPATE")) {
						System.out.println("GUEST RECIBE EMPATE");
						dbox.toFront();
						draw.setTouchable(Touchable.disabled);
						stage.addActor(dbox.getCheck()); 
						stage.addActor(dbox.getCross());
					}else if(Render.guest.getMessage().equals("ACEPTAR")) {
						results.setDraw();
						showPopup = true;
						blackCheckMate = true;
					}
				}
				Render.guest.resetMessage();
			} else {
				if (!Render.host.getMessage().equals("")) {
					if (Render.host.getMessage().equals("RENDICION")) {
						System.out.println("Rendicion Negra");
						results.setWinnerSurrender("Blanco");
						showPopup = true;
						whiteCheckMate = true;
					}else if(Render.host.getMessage().equals("EMPATE")) {
						System.out.println("GUEST RECIBE EMPATE");
						dbox.toFront();
						draw.setTouchable(Touchable.disabled);
						stage.addActor(dbox.getCheck()); 
						stage.addActor(dbox.getCross());
					}else if(Render.host.getMessage().equals("ACEPTAR")) {
						results.setDraw();
						showPopup = true;
						blackCheckMate = true;
					}
				}
				Render.host.resetMessage();

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