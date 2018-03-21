package gameover;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cgeschwendt.game.GameMain;
import com.cgeschwendt.game.gameinfo.GameInfo;

import Mainmenu.MainMenuButtons;

public class GameOver implements Screen {

	private GameMain game;
	private OrthographicCamera mainCamera;
	private Viewport gameViewPort;
	private Texture background;
	private Texture newHighScore;
	private GameOverButtons buttons;
	private boolean NewHighScore = false;
	private int flashTimer;
	private int interval ;
	private String newName;
	
	

	public GameOver(GameMain game) {
		this.game = game;
		// sets up the main camera for the main menu.
		mainCamera = new OrthographicCamera(GameInfo.WIDTH, GameInfo.HEIGHT);
		mainCamera.position.set(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f, 0);
		// sets the mainmenu viewport.
		gameViewPort = new StretchViewport(GameInfo.WIDTH, GameInfo.HEIGHT, mainCamera);
		this.background = new Texture("gameover/gameoverbackground.png");
		this.newHighScore = new Texture("gameover/newhighscore.png");
		// creates game over buttons
		buttons = new GameOverButtons(game);
		
		this.madeHighScore(game.getplayer().getPlayerScore());
		flashTimer=0;
		//Ask for new high score name
		if(NewHighScore) {
			Gdx.input.getTextInput(new TextInputListener() {

				@Override
				public void input(String text) {
				
					
				}
				@Override
				public void canceled() {}
				
			}, "NEW HIGHSCORE", "NAME", "");
			
			// sets the new high scores internally
			this.setNewHighScore(game.getplayer().getPlayerScore(),newName);
		}
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		// clears screen and starts redraw.
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		flashTimer++;

		// renders background
		game.getBatch().begin();
		game.getBatch().draw(background, 0, 0);
		game.getBatch().end();

		// renders buttons
		game.getBatch().setProjectionMatrix(buttons.getStage().getCamera().combined);
		buttons.getStage().draw();
		
		if(NewHighScore) {
			this.flashHighScore();
		}
	}

	/** sets the new high score values and names if the new high score is in the top 3
	 * @author cgeschwendt
	 */
	void setNewHighScore(int score,String name) {
		if (score > game.highScore1) {
			// scores
			game.highScore3 = game.highScore2;
			game.highScore2 = game.highScore1;
			game.highScore1 = score;
			// names
			game.highScore3Name = game.highScore2Name;
			game.highScore2Name = game.highScore1Name;
			game.highScore1Name = name;

		} else if (score > game.highScore2) {
			// scores
			game.highScore3 = game.highScore2;
			game.highScore2 = score;
			// names
			game.highScore3Name = game.highScore2Name;
			game.highScore2Name = name;
		} else if (score > game.highScore3) {
			// scores
			game.highScore3 = score;
			// names
			game.highScore3Name = name;
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		this.saveScores(NewHighScore);
		NewHighScore = false;

	}

	/**
	 * Saves the high scores to the save file updates the save file if there is a
	 * new high score
	 * 
	 * @author cgeschwendt
	 */
	private void saveScores(boolean update) {
		if (update) {
			try {
				FileWriter writer = new FileWriter(new File("highScores.txt"));
				writer.write(game.highScore1Name + "\n");
				writer.write(Integer.toString(game.highScore1) + "\n");
				writer.write(game.highScore2Name + "\n");
				writer.write(Integer.toString(game.highScore2) + "\n");
				writer.write(game.highScore3Name + "\n");
				writer.write(Integer.toString(game.highScore3));
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * flashes high score on the screen
	 * @author cgeschwendt
	 */
	void flashHighScore(){
		if(flashTimer % 90==0)
			interval = 50;
		
		if(interval !=0) {
		game.getBatch().begin();
		game.getBatch().draw(this.newHighScore,GameInfo.WIDTH/2-115,GameInfo.HEIGHT/2-90);
		game.getBatch().end();
		interval--;
		}
	}

	void madeHighScore(int score) {
		if(score > game.highScore3) {
			this.NewHighScore=true;
		}
	}




}
