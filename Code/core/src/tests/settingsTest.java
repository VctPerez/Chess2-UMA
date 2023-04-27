package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import elements.Board;
import utils.Settings;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class settingsTest {
    int sfx = 50, sound = 50;

    @Test
    public void subirvolumen(){
    	Board mockTablero = mock(Board.class);
        Settings.setSfxVolume(sfx+1);
        assertEquals(sfx + 1, Settings.sfxVolume, " El volumen deberia haber subido.");
    }
}
