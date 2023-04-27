package tests;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.PixmapTextureData;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import elements.Piece;
import elements.Tile;
import elements.pieces.Rook;
import utils.Image;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;

public class pieceTest {
   	Piece pieza;

   	@BeforeEach
	public void init() {
		pieza = mock(Piece.class,Mockito.CALLS_REAL_METHODS);
	}
	
	@AfterEach
	public void terminate() {
		pieza = null;
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
	
    @Test
    public void colorTest(){
    	assertEquals(pieza.color(),null,"Si no se construye una pieza que derive de Piece es un valor nulo");
    }
    
    @Test
    public void getPosTest()
    {
    	Vector2 vector = mock(Vector2.class);
    	vector.x = 0;
    	vector.y = 0;
    	assertEquals(pieza.getPos().x,vector.x,"El valor de x es el adecuado por defecto");
    	assertEquals(pieza.getPos().y,vector.y,"El valor de y es el adecuado por defecto");
    }
    
    @Test
    public void setAliveTest()
    {
    	pieza.setAlive(true);
    	assertEquals(pieza.alive,true,"En un principio debe estar viva");
    	pieza.setAlive(false);
    	assertEquals(pieza.alive,false,"Despues se pone la pieza en dead");
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
    
    
    
}