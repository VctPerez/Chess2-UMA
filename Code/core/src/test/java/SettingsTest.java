import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Settings;

import static org.junit.jupiter.api.Assertions.*;

public class SettingsTest {
    float sfx, music, language;

    @BeforeEach
    public void setUp(){
        sfx = 50;
        music = 50;
        language = 0;
    }

    @Test
    public void subirEfectos(){
        Settings.setSfxVolume(sfx+1);
        assertEquals(sfx+1, Settings.reconvertAudioToText(Settings.sfxVolume), " El volumen de efectos deberia haber subido.");
    }

    @Test
    public void bajarEfectos(){
        Settings.setSfxVolume(sfx-1);
        assertEquals(sfx-1, Settings.reconvertAudioToText(Settings.sfxVolume), " El volumen de efectos deberia haber bajado.");
    }

    @Test
    public void subirMusica(){
        Settings.setMusicVolume(music + 1);
        assertEquals(music + 1, Settings.reconvertAudioToText(Settings.musicVolume), "El volumen de la musica deberia haber subido.");
    }

    @Test
    public void bajarMusica(){
        Settings.setMusicVolume(sfx-1);
        assertEquals(sfx-1, Settings.reconvertAudioToText(Settings.musicVolume), " El volumen de la musica deberia haber bajado.");
    }

    @Test
    public void cambiarIdioma(){
        Settings.setLanguage((int) (language+1)%2);
        assertNotEquals(language, Settings.language, "El idioma deberia ser distinto");
    }

    /*
    @Test
    public void sfxException(){
        Exception exception = assertThrows(SettingsException.class, () -> Settings.setSfxVolume(-1));
        assertEquals("El volumen que desea establecer no esta permitido.", exception.getMessage());
        Exception exception2 = assertThrows(SettingsException.class, () -> Settings.setSfxVolume(105));
        assertEquals("El volumen que desea establecer no esta permitido.", exception2.getMessage());
    }
    @Test
    public void musicException(){
        Exception exception = assertThrows(SettingsException.class, () -> Settings.setMusicVolume(-1f));
        assertEquals("El volumen que desea establecer no esta permitido.", exception.getMessage());
        Exception exception2 = assertThrows(SettingsException.class, () -> Settings.setMusicVolume(150f));
        assertEquals("El volumen que desea establecer no esta permitido.", exception2.getMessage());
    }
    @Test
    public void languageException(){
        Exception exception = assertThrows(SettingsException.class, () -> Settings.setLanguage(-1));
        assertEquals("El lenguaje seleccionado no esta disponible.", exception.getMessage());
        Exception exception2 = assertThrows(SettingsException.class, () -> Settings.setLanguage(2));
        assertEquals("El lenguaje seleccionado no esta disponible.", exception2.getMessage());
    }*/
}
