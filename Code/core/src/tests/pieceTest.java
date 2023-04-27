package tests;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import elements.Board;
import elements.Piece;
import elements.Tile;
import elements.pieces.Rook;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;

public class pieceTest {
   	Piece pieza;

   	@BeforeEach
	public void init() {
		pieza = mock(Piece.class, Mockito.CALLS_REAL_METHODS);
	}
	
	@AfterEach
	public void terminate() {
		pieza = null;
	}
	
    @Test
    public void colorTest(){
    	Tile tileMock = mock(Tile.class);
    	Board boardMock = mock(Board.class);
    	when(boardMock.getTile(0, 0)).thenReturn(tileMock);
    	when(tileMock.getX()).thenReturn(0f);
    	when(tileMock.getY()).thenReturn(0f);
    	
    	pieza = new Rook(true,0,0,boardMock, mock(Texture.class));
    	
    	assertEquals(pieza.color(),true,"Si se construye una pieza que deriva de Piece se describe el atributo color correctamente");
    }
    
    @Test
    public void aliveTest()
    {
    	Tile tileMock = mock(Tile.class);
    	Board boardMock = mock(Board.class);
    	when(boardMock.getTile(0, 0)).thenReturn(tileMock);
    	when(tileMock.getX()).thenReturn(0f);
    	when(tileMock.getY()).thenReturn(0f);
    	
    	pieza = new Rook(true,0,0,boardMock, mock(Texture.class));
    	
    	assertEquals(pieza.alive,true,"En un principio debe estar viva");
    }
    
    @Test
    public void getPosTest()
    {
    	Tile tileMock = mock(Tile.class);
    	Board boardMock = mock(Board.class);
    	when(boardMock.getTile(1, 1)).thenReturn(tileMock);
    	when(tileMock.getX()).thenReturn(0f);
    	when(tileMock.getY()).thenReturn(0f);
    	
    	pieza = new Rook(true,1,1,boardMock, mock(Texture.class));
    	
    	assertEquals(pieza.getPos().x,1,"El valor de x es el adecuado al del constructor");
    	assertEquals(pieza.getPos().y,1,"El valor de y es el adecuado al del constructor");
    }
}