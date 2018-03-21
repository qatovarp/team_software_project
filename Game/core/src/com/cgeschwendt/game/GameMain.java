package com.cgeschwendt.game;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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

	public int highScore1;
	public int highScore2;
	public int highScore3;
	public String highScore1Name;
	public String highScore2Name;
	public String highScore3Name;

	private sound music;
	private int currentLvlID;

	
	@Override
	public void create () {
		music = new sound();
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
		music.dispose();
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
	
	public sound getmusic() {
		return this.music;
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
			player.resetScore();
			this.getScreen().dispose();
			this.setScreen(new MainMenu(this));
		}
	}
}
