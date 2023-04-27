package tests;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

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
}