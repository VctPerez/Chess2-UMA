package game.chess;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import game.chess.Chess2;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		
		config.setForegroundFPS(60);
		config.setTitle("Chess2");
		//config.setWindowedMode(1366, 768);
		config.setWindowedMode(1280, 720);
		//config.setWindowedMode(1920, 1080);
		new Lwjgl3Application(new Chess2(), config);
	}
}
