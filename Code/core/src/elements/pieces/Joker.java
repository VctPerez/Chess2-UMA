package elements.pieces;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import elements.Board;
import elements.Piece;
import interaccionFichero.LectorLineas;
import utils.Image;
import utils.Parser;
import utils.Render;
import utils.Resources;

import java.util.ArrayList;
import java.util.Random;

public class Joker extends Piece {

	private Vector2 prev;
	public Piece current;
	public Random rnd = new Random();
	private Image mask;

	public Joker(Boolean color, int x, int y, Board board) {
		super(color, Resources.RND_PATH, x, y, board);
		current = new Bishop(color, x, y, board);

		mask = new Image(Resources.BISHOP_PATH);

		this.setSprite(Resources.RND_PATH);
		prev = new Vector2(0, 0);
	}

	public Piece Swap() {
		ArrayList<String> draft;
		// Probabilidades:
		// Dama:15
		// Peón:15
		// Alfil:30
		// Caballo:20
		// Torre:20

		// Asegurarme que no se repitan piezas
		int i, tam = (int) (prev.y - prev.x);
		i = (int) (rnd.nextInt(100 - tam) + prev.y + 1) % 100; // Hace que los números no estén en el área del anterior
		// Hace que empiezen fuera del rango del anterior y con el módulo se asegura que
		// es < 100 y con 100 tam esta no entra en el rango
		/*
		 * do { //Sin ofender, pero esto me dio un infarto y medio
		 * i = rnd.nextInt(100) + 1 ;
		 * } while (i>=prev.x && i<= prev.y);
		 */

		Piece nueva;

		if (i >= 1 && i <= 30) {
			nueva = Parser.getPieceFromPath(Resources.BISHOP_PATH, color, this.x, this.y, board);
			mask.setImage(Resources.BISHOP_PATH);
			prev.x = 1;
			prev.y = 30;
		} else if (i >= 31 && i <= 45) {
			nueva = Parser.getPieceFromPath(Resources.PAWN_PATH, color, this.x, this.y, board);
			nueva.hasBeenMoved = true;
			mask.setImage(Resources.PAWN_PATH);
			prev.x = 31;
			prev.y = 45;
		} else if (i >= 46 && i <= 65) {
			nueva = Parser.getPieceFromPath(Resources.KNIGHT_PATH, color, this.x, this.y, board);
			mask.setImage(Resources.KNIGHT_PATH);
			prev.x = 46;
			prev.y = 65;
		} else if (i >= 66 && i <= 80) {
			nueva = Parser.getPieceFromPath(Resources.QUEEN_PATH, color, this.x, this.y, board);
			mask.setImage(Resources.QUEEN_PATH);
			prev.x = 66;
			prev.y = 80;
		} else {
			nueva = Parser.getPieceFromPath(Resources.ROOK_PATH, color, this.x, this.y, board);
			mask.setImage(Resources.ROOK_PATH);
			prev.x = 81;
			prev.y = 100;
		}

		return nueva;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		if (mask == null)
			return;
		mask.setPosition(getX() + getWidth() / 2, getY());
		mask.setSize(getWidth() / 2, getHeight() / 2);
		mask.draw(batch, parentAlpha);
	}

	/**
	 * A�ade a movements todos los movimientos posibles del caballo
	 *
	 */

	@Override
	public ArrayList<Vector2> posibleMovements() {
		if (this.hasBeenMoved) {
			current = Swap();
		}
		Vector2 pos = this.getPos();
		ArrayList<Vector2> movements = new ArrayList<>();
		// System.out.println();pos.toString(); // Esto que hace aqui?
		this.hasBeenMoved = false;
		if (current.posibleMovements().isEmpty() && (this.color() && (int) pos.y == 8)) {
			movements.add(new Vector2(pos.x, pos.y - 1));
		} else if (current.posibleMovements().isEmpty() && (!this.color() && (int) pos.y == 1)) {
			movements.add(new Vector2(pos.x, pos.y + 1));
		} else {
			movements = current.posibleMovements();
		}
		return movements;
	}

	public void addMovement(float x, float y, Board board, ArrayList<Vector2> movements) {
		if (board.getTile(x, y) != null && !sameColor(board.getTile(x, y).getPiece())) {
			movements.add(new Vector2(x, y));
		}
	}

	public String getInfo() {
		LectorLineas Reader, configReader;
		configReader = new LectorLineas("files/config.txt");
		String config = configReader.leerLinea(1);
		Reader = new LectorLineas("files/lang/" + config + "Modified.txt");
		switch (config) {
			case "esp/":
				return Reader.leerTramo(1, 6);
			case "eng/":
				return Reader.leerTramo(1, 5);
			default:
				throw new IllegalArgumentException("Configuración errónea");
		}
	}

	/**
	 * Devuelve el path del joker con su color
	 * 
	 * @return path del sprite
	 */
	@Override
	public String getSpritePath() {
		return color ? Resources.RND_PATH : Resources.BLACK_RND_PATH;
	}

	@Override
	public String toString() {
		String str = super.toString();
		return str.replace("X", "Joker");
	}

}
