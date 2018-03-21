package com.cgeschwendt.game.gameinfo;

public class GameInfo {
<<<<<<< HEAD
public static final int WIDTH = 1024;
public static final int HEIGHT = 625;
public static final int PPM = 100;
public static final String GAMENAME = "COLOR WORLD";


public static boolean firstMainMenu = true;

public static boolean normal = true;
public static boolean difficult = false;
public static boolean extream = false;

public static enum COLOR {BLUE,GREEN,PINK };
public static COLOR playerColor = COLOR.BLUE;



=======
>>>>>>> 2a2fed57180b5cd16d6019b4b45383d54b27ccc3

	public static final int WIDTH = 1024;
	public static final int HEIGHT = 625;
	public static final int PPM = 100;
	public static final String GAMENAME = "COLOR WORLD";

	public static boolean firstMainMenu = true;

	public static boolean normal = true;
	public static boolean difficult = false;
	public static boolean extream = false;

	private static String[] levels = {
			"LevelOne.tmx",
			"LevelOne.tmx"
	};

	public static String getLvlFileName(int i) {
		return levels[i];
	}
	
	public static int getNumOfLvls() {
		return levels.length;
	}
}
