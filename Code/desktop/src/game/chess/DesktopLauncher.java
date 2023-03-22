package game.chess;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import game.chess.Chess2;
import utils.Render;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		
		config.setForegroundFPS(60);
		config.setTitle("Chess2");
		config.setWindowedMode(Render.SCREEN_WIDTH, Render.SCREEN_HEIGHT);
		new Lwjgl3Application(new Chess2(), config);
	}
}
