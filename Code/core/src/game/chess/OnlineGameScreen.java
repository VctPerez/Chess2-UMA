package game.chess;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import elements.DropDownMenu;
import elements.Tile;
import utils.Parser;
import utils.Render;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import utils.TextButton;

public class OnlineGameScreen extends GameScreen {


	@Override
	public void show() {
		super.show();
		draw = new TextButton(languageReader.leerLinea(5), "SingleClickStyle");
		draw.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				try {
					if (Render.hosting) {
						System.out.println("HOST ENVIA EMPATE");
						Render.host.sendMessage("EMPATE");
						draw.setTouchable(Touchable.disabled);
					} else {
						System.out.println("GUEST ENVIA EMPATE");
						Render.guest.sendMessage("EMPATE");
					}

				}catch (IOException e) {
					e.printStackTrace();
				}
				return true;
			}
		});
		table.add(draw).right().padRight(47).expandX();
		table.row();
		stage.addActor(table);
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		if(showPopup) draw.clearListeners();
		try {
			if (!whiteCheckMate && !blackCheckMate)
				updateOnlineBoard();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
					results.setWinnerSurrender(PLAYER);
					showPopup = true;
					whiteCheckMate = true;
				} else {
					Render.guest.sendMessage("RENDICION");
					System.out.println("Rendicion Negra");
					results.setWinnerSurrender(PLAYER);
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
						results.setWinnerSurrender(PLAYER);
						showPopup = true;
						whiteCheckMate = true;
					} else if (Render.guest.getMessage().equals("EMPATE")) {
						System.out.println("GUEST RECIBE EMPATE");
						askDraw();
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

						results.setWinnerSurrender(PLAYER);
						showPopup = true;
						blackCheckMate = true;
					} else if(Render.host.getMessage().equals("EMPATE")) {
						System.out.println("HOST RECIBE EMPATE");
						askDraw();
					}else if(Render.host.getMessage().equals("ACEPTAR")) {
						results.setDraw();
						showPopup = true;
						blackCheckMate = true;
					}else if(Render.host.getMessage().equals("RECHAZAR")){
						draw.setTouchable(Touchable.enabled);
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
						results.setWinnerSurrender(PLAYER);
						showPopup = true;
						blackCheckMate = true;
					}else if(Render.guest.getMessage().equals("EMPATE")) {
						System.out.println("GUEST RECIBE EMPATE");
						askDraw();
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
						results.setWinnerSurrender(PLAYER);
						showPopup = true;
						whiteCheckMate = true;
					}else if(Render.host.getMessage().equals("EMPATE")) {
						System.out.println("GUEST RECIBE EMPATE");
						askDraw();
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

	/**
	 * Establece los botones para apectar o rechazar el empate
	 */
	private void askDraw(){
		dbox.toFront();
		draw.setTouchable(Touchable.disabled);
		stage.addActor(dbox.getCheck());
		stage.addActor(dbox.getCross());
	}

}