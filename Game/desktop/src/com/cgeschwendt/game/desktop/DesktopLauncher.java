package com.cgeschwendt.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.cgeschwendt.game.GameMain;
import com.cgeschwendt.game.gameinfo.GameInfo;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new GameMain(), config);
		config.width=GameInfo.WIDTH;
		config.height = GameInfo.HEIGHT;
		config.title = GameInfo.GAMENAME;
	}
}
