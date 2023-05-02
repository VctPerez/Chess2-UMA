package test.java;
import com.badlogic.gdx.graphics.Texture;
import elements.Board;
import elements.Piece;
import elements.Tile;
import elements.pieces.Rook;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
public class PieceTest {
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
    public void SameColor() {
    	Tile tileMock = mock(Tile.class);
    	Board boardMock = mock(Board.class);
    	when(boardMock.getTile(0, 0)).thenReturn(tileMock);
    	when(boardMock.getTile(0, 0)).thenReturn(tileMock);
    	when(tileMock.getX()).thenReturn(0f);
    	when(tileMock.getY()).thenReturn(0f);
    	
    	Piece auxT = new Rook(true,0,0,boardMock,mock(Texture.class));
    	Piece auxF = new Rook(false,0,0,boardMock,mock(Texture.class));
    	pieza = new Rook(true,0,0,boardMock,mock(Texture.class));
    	
    	assertEquals(pieza.sameColor(auxT),true);
    	assertEquals(pieza.sameColor(auxF),false);
    } 	
    
    @Test
    public void correctDispose() {
    	Tile tileMock = mock(Tile.class);
    	Board boardMock = mock(Board.class);
    	
    	when(boardMock.getTile(0, 0)).thenReturn(tileMock);
    	when(boardMock.getTile(0, 0)).thenReturn(tileMock);
    	when(tileMock.getX()).thenReturn(0f);
    	when(tileMock.getY()).thenReturn(0f); 
    	
    	pieza = new Rook(true,0,0,boardMock,mock(Texture.class));
    	pieza.dispose();
    	assertNull(pieza.getImage());
    }
    
    @Test
    public void String() {
        Tile tileMock = mock(Tile.class);
        Board boardMock = mock(Board.class);
        when(boardMock.getTile(1, 1)).thenReturn(tileMock);
        when(tileMock.getX()).thenReturn(0f);
        when(tileMock.getY()).thenReturn(0f);

        pieza = new Rook(true,1,1,boardMock, mock(Texture.class));
        assertEquals(pieza.toString().equalsIgnoreCase("{Rook,(1,1)}"),true,"La salida en consola de la posicion de la pieza es correcta");

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
    public void equals() {
        Tile tileMock = mock(Tile.class);
        Board boardMock = mock(Board.class);
        when(boardMock.getTile(1, 1)).thenReturn(tileMock);
        when(tileMock.getX()).thenReturn(0f);
        when(tileMock.getY()).thenReturn(0f);

        pieza = new Rook(true,1,1,boardMock, mock(Texture.class));
        Piece aux = new Rook(true,1,1,boardMock,mock(Texture.class));

        assertTrue(pieza.equals(aux),"Las piezas son iguales");

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