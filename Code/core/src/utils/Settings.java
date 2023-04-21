package utils;
/**
 * Clase est�tica que contiene los valores de configuraci�n del usuario.
 */
public class Settings {

	public static float musicVolume;
	public static float sfxVolume;
	public static int language;
	
	public static void setLanguage(int value) {
		language = value;
	}
	
	public static void setSfxVolume(float value) {
//		sfxVolume = (float) Math.pow((value/10),2);
		sfxVolume = value;
	}
	
	public static void setMusicVolume(Float value) {
//		musicVolume = (float) Math.pow((value/10),2);
		musicVolume = value;
	}
	
	public static void updateSettings(float musicVolume, float sfxVolume, int language) {
		setMusicVolume(musicVolume);
		setSfxVolume(sfxVolume);
		setLanguage(language);
	}
}
