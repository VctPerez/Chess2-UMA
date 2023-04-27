package tests;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.badlogic.gdx.graphics.Texture;

import elements.Board;
import elements.Piece;

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
   	
	
	//Pruebas
	//color
	
    @Test
    public void crearPieza(){
    	
    }
}