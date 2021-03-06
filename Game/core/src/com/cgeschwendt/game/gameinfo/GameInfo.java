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
	
	public static boolean gameStart = false;

	public static enum COLOR {BLUE,GREEN,PINK };
	public static COLOR playerColor = COLOR.GREEN;
	
	public static boolean HASGREENKEY = false;
	public static boolean HASBLUEKEY = false;
	public static boolean HASORANGEKEY = false;
	public static boolean HASYELLOWKEY = false;
	
	public static boolean HASGREENGEM = false;
	public static boolean HASBLUEGEM = false;
	public static boolean HASORANGEGEM = false;
	public static boolean HASYELLOWGEM = false;
	
	
	public static boolean GREENKEY = false;
	
	
	public static boolean sound = true;

	public static int levelNum = 0;
	public  static boolean atLvlExit = false;

	final public static String[] levels = {
			"TiledLevels/Level_QTP_1.tmx",
			"TiledLevels/levelTwo.tmx",
	};
	
	final public static String[] backgrounds = {
			"backgrounds/uncolored_castle.png",
			"backgrounds/uncolored_castle.png",
			"backgrounds/uncolored_castle.png"
	};
	
	final public static String[] music = {
			"Lost-Jungle.mp3",
			"Waltz.mp3",
			"Waltz.mp3"
	};
	
	final public static String MAINMENUMUSIC = "music/Lost-Jungle.mp3";

}
