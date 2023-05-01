package test.java;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TileTest {
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
	
	@Test 
	public void moveToTest(){
		Piece mockPiece = mock(Pawn.class);
		Tile mockTile = mock(Tile.class, Mockito.CALLS_REAL_METHODS);
		tile.setPiece(mockPiece);
		tile.moveTo(mockTile);
		assertEquals(mockTile.getPiece() instanceof Pawn,true,"Se debe cambiar la pieza de una casilla a otra");
	
	}
	
	@Test // Reemplazable, lo puse por probar con otra forma de acceder a la pieza
	public void getPieceTest(){
		tile.setPiece(mock(Pawn.class));
		boolean cumple=(tile.getPiece() instanceof Pawn);
		assertEquals(cumple,true,"Se debe guardar la pieza en los atributos de Tile");
	
	}
	
	@Test
	public void equalsTest(){
		Tile tile2=mock(Tile.class);
		Vector2 pos = new Vector2(0, 0);
		when(tile2.getPos()).thenReturn(pos);
		when(tile.getPos()).thenReturn(pos);
		assertTrue(tile2 instanceof Tile && tile2.getPos().equals(tile.getPos()),"Deben ser iguales por sus posiciones");
	
	}
	
}