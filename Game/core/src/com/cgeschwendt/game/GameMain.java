package com.cgeschwendt.game;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cgeschwendt.game.gameinfo.GameInfo;

import Mainmenu.MainMenu;
import gameover.GameOver;
import levelone.LevelOne;
import player.Player;
import sound.sound;





public class GameMain extends Game {
	// the one and only SpriteBatch for the game;
	private SpriteBatch batch;
	// the one player for the game;
	private Player player = new Player();
	private Screen prevScreen;
	private Music music;
	
		

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
	public Music getMusic() {
		return this.music;
	}
	public void setMusic(String name) {
		music.dispose();
		music = Gdx.audio.newMusic(Gdx.files.internal(name));	
		music.play();
		music.setLooping(true);
		music.setVolume(.06f);
	}
	
	
	public int getCurrentLvlID() {
		return currentLvlID;
	}
	
	public void loadNextLevel() {
		currentLvlID += 1;
		
		if(currentLvlID < GameInfo.getNumOfLvls() ) {
			this.getScreen().dispose();
			this.setScreen(new LevelOne(this));
		}
		else {
			currentLvlID = 0;
			//change screen to winning! woooo
			// currently set it to main menu
			this.getScreen().dispose();
			this.setScreen(new MainMenu(this));
		}
	}
}
