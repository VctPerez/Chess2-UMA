package utils;
/**
 * Clase estática que contiene los valores de configuración del usuario.
 */
public class Settings {

	public static float musicVolume;
	public static float sfxVolume;
	public static int language;
	
	public static void setLanguage(int value) {
		language = value;
	}
	
	public static void setSfxVolume(float value) {
		sfxVolume = value;
	}
	
	public static void setMusicVolume(Float value) {
		musicVolume = value;
	}
	
	public static void updateSettings(float musicVolume, float sfxVolume, int language) {
		setMusicVolume(musicVolume);
		setSfxVolume(sfxVolume);
		setLanguage(language);
	}
}
