package tests;

import elements.Board;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    @AfterEach
    public void resetState(){
        Board.board = null;
        Mock mock;
    }

    @Test
    public void emptyBoardHasNoTiles(){
        new Board(0,0,0,0);
        //El Tile[][] es una variable de clase asi que solo necesitamos inicializarla y no tenerla en una variable
        //Los tres primeros parametros no importan en estas pruebas, el cuarto es la cantidad de casillas for fila y columna
        assertEquals(0,Board.board.length);
    }

    @Test
    public void cannotCreateNegativeBoard(){
        assertThrows(NegativeArraySizeException.class,() -> new Board(0,0,0,-1));
    }

    @Test
    public void columnHasNTiles(){
        for (int i = 0; i < 10; i++) {
            new Board(0,0,0,i);
            assertEquals(i,Board.board.length);
        }
    }
}
