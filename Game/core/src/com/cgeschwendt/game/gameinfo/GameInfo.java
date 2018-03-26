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
	
	public static boolean HASGREENKEY = false;
	public static boolean HASBLUEKEY = false;
	public static boolean HASORANGEKEY = false;
	public static boolean HASYELLOWKEY = false;
	
	
	public static boolean GREENKEY = false;
	
	
	public static boolean sound = true;

	public static int levelNum = 0;

	final public static String[] levels = {
			"TiledLevels/levelTwo.tmx",
		//	"TiledLevels/Demo.tmx",
		//	"TiledLevels/LevelOne.tmx"
	};
	
	final public static String[] backgrounds = {
			"backgrounds/uncolored_castle.png",
			"backgrounds/uncolored_castle.png"
	};
	
	final public static String[] music = {
			"music/Lost-Jungle.mp3",
			"music/Waltz.mp3"
	};
	
	final public static String MAINMENUMUSIC = "music/Lost-Jungle.mp3";

}
