package io.github.phod.musicRunner.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import io.github.phod.musicRunner.MusicRunner;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Music Runner";
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new MusicRunner(), config);
	}
}
