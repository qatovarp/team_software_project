package com.cgeschwendt.game;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cgeschwendt.game.gameinfo.GameInfo;

import Mainmenu.MainMenu;
import gameover.GameOver;
import levelone.GenericLevel;
import player.Hearts;
import player.Player;


public class GameMain extends Game {
	// the one and only SpriteBatch for the game;
	private SpriteBatch batch;
	// the one player for the game;
	private Player player;
	private Screen prevScreen;
	private Music music;
	private Sprite backgroundSprite;		

	public int highScore1;
	public int highScore2;
	public int highScore3;
	public String highScore1Name;
	public String highScore2Name;
	public String highScore3Name;

	private int currentLvlID;
	
	@Override
	public void create () {
		music = Gdx.audio.newMusic(Gdx.files.internal("music/Lost-Jungle.mp3"));	
		music.setVolume(.2f);
		music.setLooping(true);
		music.play();
	
		batch = new SpriteBatch();
		this.loadHighScores();
		this.setScreen(new MainMenu(this));
		currentLvlID = 0;
		player = new Player(this);
	}

	private void loadHighScores() {
		 String fileName = "highScores.txt";
		 try {
			FileReader fr = new FileReader(fileName);
			 BufferedReader bufferedReader = new BufferedReader(fr);
			highScore1Name = bufferedReader.readLine();
			highScore1 = Integer.parseInt(bufferedReader.readLine());
			highScore2Name = bufferedReader.readLine();
			highScore2 = Integer.parseInt(bufferedReader.readLine());
			highScore3Name = bufferedReader.readLine();
			highScore3 = Integer.parseInt(bufferedReader.readLine());
			fr.close();
		} catch (FileNotFoundException e) {
			System.out.println("Seek Programers Help. (cgeschwendt)");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Seek Programers Help. (cgeschwendt)");
			e.printStackTrace();
		}
		
	}

	@Override
	public void render () {
	super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
	public SpriteBatch getBatch() {
		return this.batch;
	}
	public Player getplayer() {
		return this.player;
	}

	public void setPrevScreen(Screen sc) {
		this.prevScreen = sc;
	}

	public Screen getPrevScreen() {
		return this.prevScreen;
	}

	/* ==================== Music ==================== */
	public Music getMusic() {
		return this.music;
	}
	
	public void setMusic(String name) {
		music.dispose();
		music = Gdx.audio.newMusic(Gdx.files.internal("music/"+name));	
		music.play();
		music.setLooping(true);
		music.setVolume(.06f);
	}
	
	/* =============================================== */
	

	
	public void loadNextLevel() {
		GameInfo.levelNum += 1;
		
		if(currentLvlID < GameInfo.levels.length ) {
			this.getScreen().dispose();
			this.setScreen(new GenericLevel(this));
		}
		else {
			currentLvlID = 0;
			//change screen to winning! woooo
			// currently set it to gameover
			this.getScreen().dispose();
			this.setScreen(new GameOver(this));
		}
	}
	
	/* ================= Background ================== */
	public void setBackground() {
		backgroundSprite = new Sprite(new Texture(GameInfo.backgrounds[GameInfo.levelNum])) ;
		backgroundSprite.setY(backgroundSprite.getY()-70f);
	}
	
	public void renderBackground() {
		batch.begin();
		this.backgroundSprite.draw(batch);
		batch.end();
	}
	/* =============================================== */
	
	public void newGameSettings() {
		player = new Player(this);
	}

}
