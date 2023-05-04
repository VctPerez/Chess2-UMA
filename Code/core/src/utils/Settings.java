package utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;

import exceptions.SettingsException;

/**
 * Clase est�tica que contiene los valores de configuraci�n del usuario.
 */
public class Settings {

	public static float musicVolume;
	public static float sfxVolume;
	public static boolean fullscreen;
	public static int language;
	
	public static void setLanguage(int value) {
		if(value < 1 || value > 2){
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
	
	public static void setFullscreen(boolean value) {
		if(value) {
			DisplayMode displayMode = Gdx.graphics.getDisplayMode(Render.monitor);
			Gdx.graphics.setFullscreenMode(displayMode);
		}else {
			Gdx.graphics.setWindowedMode(1280, 720);
		}
	}
	
	public static float reconvertAudioToText(float value) {
		return (float) Math.sqrt((double) value) * 100;
	}
	
	public static void updateSettings(float musicVolume, float sfxVolume, boolean fullscreen ,int language) {
		setMusicVolume(musicVolume);
		setSfxVolume(sfxVolume);
		setLanguage(language);
		setFullscreen(fullscreen);
	}
}
