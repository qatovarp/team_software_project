package com.team.game;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.team.entities.living.player.Player;
import com.team.levels.BaseLevel;
import com.team.menues.main.MainMenu;


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
	
	private int currentLvlID;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		this.loadHighScores();
		this.setScreen(new MainMenu(this));
		currentLvlID = 0;
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
	
	public int getCurrentLvlID() {
		return currentLvlID;
	}
	
	public void loadNextLevel() {
		currentLvlID += 1;
		
		if(currentLvlID < GameInfo.getNumOfLvls() ) {
			this.getScreen().dispose();
			this.setScreen(new BaseLevel(this));
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
