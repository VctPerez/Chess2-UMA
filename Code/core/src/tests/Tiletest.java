package tests;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.badlogic.gdx.math.Vector2;

import elements.Board;
import elements.Piece;
import elements.Tile;
import elements.pieces.Pawn;
import utils.Settings;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Tiletest {
	Tile tile;
	
	@BeforeEach
	public void init() {
		tile=mock(Tile.class, Mockito.CALLS_REAL_METHODS);
	}
	
	@AfterEach
	public void terminate() {
		tile=null;
	}
	
	@Test
	public void setPieceTest(){
		tile.setPiece(mock(Pawn.class));
		boolean cumple=(tile.piece instanceof Pawn);
		assertEquals(cumple,true,"Se debe guardar la pieza en los atributos de Tile");
	
	}
	
	@Test // Reemplazable, lo puse por probar con otra forma de acceder a la pieza
	public void getPieceTest(){
		tile.setPiece(mock(Pawn.class));
		boolean cumple=(tile.getPiece() instanceof Pawn);
		assertEquals(cumple,true,"Se debe guardar la pieza en los atributos de Tile");
	
	}
	
	@Test
	public void equalsTest(){
		Tile tile2=mock(Tile.class,Mockito.CALLS_REAL_METHODS);
		Vector2 pos = new Vector2(0, 0);
		when(tile2.getPos()).thenReturn(pos);
		when(tile.getPos()).thenReturn(pos);
		assertEquals(tile.equals(tile2),true,"Deben ser iguales por sus posiciones");
	
	}
	
}