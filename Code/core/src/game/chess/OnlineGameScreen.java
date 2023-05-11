package game.chess;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import elements.DrawBox;
import elements.DropDownMenu;
import elements.Tile;
import elements.pieces.King;
import elements.pieces.Mage;
import elements.pieces.Midas;
import utils.Parser;
import utils.Render;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import utils.TextButton;

public class OnlineGameScreen extends GameScreen {
	@Override
	protected void createTableElements() {
		super.createTableElements();
		drawButton = new TextButton(languageReader.readLine(5), "SingleClickStyle");
		drawButton.getLabel().setFontScale(0.75f);
		drawButton.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				try {
					Render.player.sendMessage("EMPATE");
					drawButton.setTouchable(Touchable.disabled);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;
			}
		});
	}

	@Override
	protected void setUpTable() {
		super.setUpTable();
		table.add(drawButton).right().padRight(60).expandX();
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		checkGraveyard();
		try {
			if (!whiteCheckMate && !blackCheckMate && !Render.player.getMessage().equals("")) {
				updateOnlineBoard();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void makeMove(Tile currentTile, Tile nextTile) {
		this.currentTile = currentTile;
		GameScreen.nextTile = nextTile;
		currentTile_validMovements = currentTile.getPiece().getValidMovements();
		super.makeMove(currentTile, nextTile);
		if (Render.hosting == PLAYER && currentTile_validMovements.contains(new Vector2((int) nextTile.getPos().x, (int) nextTile.getPos().y)))
			moved = true;
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
	protected void setUpSurrenderButton() {
		surrenderButton = new TextButton(languageReader.readLine(4), "SingleClickStyle");
		surrenderButton.getLabel().setFontScale(0.75f);
		surrenderButton.addCaptureListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				try {
					Render.player.sendMessage("RENDICION");
					results.setWinnerSurrender(!Render.hosting);
					showPopup = true;
					if (Render.hosting) {
						whiteCheckMate = true;
					} else {
						blackCheckMate = true;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

				return true;
			}
		});
	}

	@Override
	public void update(Tile tile) {
		super.update(tile);
		if (!promoting) {
			try {
				if (Render.hosting == PLAYER && moved) {
					System.out.println("EEEEEEEEEEEEE");
					String movement = Parser.parseMovementToString(currentTile, nextTile);
					Render.player.sendMessage(movement);
					PLAYER = !PLAYER;
					moved = false;
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private void updateOnlineBoard() throws IOException {
		if (Render.player.getMessage().equals("RENDICION")) {
			System.out.println("Rendicion Blanca");
			results.setWinnerSurrender(Render.hosting);
			showPopup = true;
			blackCheckMate = true;	
		} else if (Render.player.getMessage().equals("EMPATE")) {
			askDraw();
		} else if (Render.player.getMessage().equals("ACEPTAR")) {
			results.setDraw();
			showPopup = true;
			blackCheckMate = true;
			drawButton.clearListeners();
		} else if (Render.player.getMessage().equals("RECHAZAR")) {
			drawButton.setTouchable(Touchable.enabled);
		} else if (Render.player.getMessage().equals("SUICIDIO")) {
			results.setWinnerKingKilled(Render.hosting);
			showPopup = true;
			blackCheckMate = true;
		} else if (Render.hosting != PLAYER) {
			System.out.println("movimiento de blancas: " + Render.player.getMessage());
			Parser.parseStringToMovement(Render.player.getMessage());
			PLAYER = !PLAYER;
		}
		Render.player.resetMessage();
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
	private void askDraw() {
		drawButtonBox = new DrawBox();
		drawButtonBox.toFront();
		stage.addActor(drawButtonBox);
		drawButton.setTouchable(Touchable.disabled);
		stage.addActor(drawButtonBox.getCheck());
		stage.addActor(drawButtonBox.getCross());
		drawButtonBox.remove();
	}

}