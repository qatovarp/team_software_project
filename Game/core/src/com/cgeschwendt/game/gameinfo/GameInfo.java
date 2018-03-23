package com.cgeschwendt.game.gameinfo;

public class GameInfo {


	public static final int WIDTH = 1024;
	public static final int HEIGHT = 625;
	public static final float PPM = 70f;
	public static final String GAMENAME = "COLOR WORLD";

	public static boolean firstMainMenu = true;

	public static boolean normal = true;
	public static boolean difficult = false;
	public static boolean extream = false;
	

	public static enum COLOR {BLUE,GREEN,PINK };
	public static COLOR playerColor = COLOR.GREEN;
	
	public static boolean sound = true;
	public static boolean konami = false;

	private static String[] levels = {
			"TiledLevels/Demo.tmx",
			"TiledLevels/LevelOne.tmx"
	};
	
	private static String[] backgrounds = {
			"backgrounds/uncolored_castle.png"
	};
	
	private static String[] music = {
			"music/Lost-Jungle.mp3",
			"music/Waltz.mp3"
	};
	
	private static String[] konamiMusic = {
			"music/music1.mp3",
			"music/music2.mp3"
	};

	public static String getLvlFileName(int i) {
		return levels[i];
	}
	
	public static String getBgFileName(int currentLevelID) {
		switch (currentLevelID) {
		//case 0:
			//return backgrounds[0];
		default:
			return backgrounds[0];
		}
	}
	
	public static String getMusicFileName(int currentLevelID) {
		int i;
		
		switch (currentLevelID) {
		case -1: // main menu
			i = 0;
			break;
		default:
			i = 1;
		}

		if(konami) {
			return konamiMusic[i];
		}
		else {
			return music[i];
		}
	}
	
	public static int getNumOfLvls() {
		return levels.length;
	}
}
