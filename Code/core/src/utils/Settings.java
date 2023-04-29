package utils;

import exceptions.SettingsException;

/**
 * Clase est�tica que contiene los valores de configuraci�n del usuario.
 */
public class Settings {

	public static float musicVolume;
	public static float sfxVolume;
	public static int language;
	
	public static void setLanguage(int value) {
		if(value < 0 || value > 1){
			throw new SettingsException("El lenguaje seleccionado no esta disponible.");
		}
		language = value;
	}
	
	public static void setSfxVolume(float value) {
//		sfxVolume = (float) Math.pow((value/10),2);
		if(value > 100 || value < 0){
			throw new SettingsException("El volumen que desea establecer no esta permitido.");
		}
		sfxVolume = (float) Math.pow((value/100),2);
	}
	
	public static void setMusicVolume(Float value) {
//		musicVolume = (float) Math.pow((value/10),2);
		if(value > 100 || value < 0){
			throw new SettingsException("El volumen que desea establecer no esta permitido.");
		}
		musicVolume = (float) Math.pow((value/100),2);
		if(Render.bgMusic != null)Render.bgMusic.setVolume(musicVolume);
	}
	
	public static float reconvertAudioToText(float value) {
		return (float) Math.sqrt((double) value) * 100;
	}
	
	public static void updateSettings(float musicVolume, float sfxVolume, int language) {
		setMusicVolume(musicVolume);
		setSfxVolume(sfxVolume);
		setLanguage(language);
	}
}
