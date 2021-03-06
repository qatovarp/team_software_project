package wingamescreen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.cgeschwendt.game.GameMain;
import com.cgeschwendt.game.gameinfo.GameInfo;

import pausemenu.PauseMenuButtons;

public class WinGameScreen implements Screen {
	private GameMain game;
	private OrthographicCamera mainCamera;
	private PauseMenuButtons buttons;
	private Texture background;
	private Texture winText;
	private Texture newHighScore;
	
	private TextureAtlas playeratlas;
	private Animation<TextureRegion> playerAnimation;
	private float elapsedTime =0;
	private winScreenButtons BTN;
	
	private boolean NewHighScore = false;
	private int flashTimer;
	private int interval ;
	
	
	
	public WinGameScreen(final GameMain game) {
		this.game = game;
		game.setPrevScreen(game.getScreen());
		// sets up the main camera for the main menu.
		mainCamera = new OrthographicCamera(GameInfo.WIDTH, GameInfo.HEIGHT);
		mainCamera.position.set(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f, 0);

		// TODO: set pause background path.
		background = new Texture("winscreen/Winbackground.png");
		winText = new Texture("winscreen/winTexr.png");
		BTN = new winScreenButtons(game);
		
		this.newHighScore = new Texture("gameover/newhighscore.png");
		playeratlas = new TextureAtlas("player/bluePlayer.atlas");
		playerAnimation = new Animation<TextureRegion>(1f / 22f, playeratlas.getRegions());
		Array<TextureAtlas.AtlasRegion> frames = playeratlas.getRegions();
		for (TextureRegion frame : frames) {
			frame.flip(true, false);
		}
		
		
		
		this.madeHighScore(game.getplayer().getPlayerScore());
		flashTimer=0;
		//Ask for new high score name
		if(NewHighScore) {
			Gdx.input.getTextInput(new TextInputListener() {

				@Override
				public void input(String text) {
					setNewHighScore(game.getplayer().getPlayerScore(),text);
				}
				@Override
				public void canceled() {}
				
			}, "NEW HIGHSCORE", "NAME", "");
				
		}

	}
	
	

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		// clears screen and starts redraw.
		
				elapsedTime += Gdx.graphics.getDeltaTime();
				Gdx.gl.glClearColor(1, 1, 1, 1);
				Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

				game.getBatch().begin();
				game.getBatch().draw(background, 0, 0);
				game.getBatch().draw(winText,330,380,425,225);
			
				
				game.getBatch().end();
				
				this.drawPlayer();
				
				game.getBatch().setProjectionMatrix(BTN.getStage().getCamera().combined);
				BTN.getStage().draw();
		
				
				if(NewHighScore) {
					this.flashHighScore();
				}
	}
	
	
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

	int ypos = 1000;
	private void drawPlayer() {
		ypos-=4;
		if(ypos >= 500) {
			game.getBatch().begin();
			game.getBatch().draw(playerAnimation.getKeyFrame(elapsedTime,true),ypos,69, 55,65);
			game.getBatch().end();
		}
	}
	
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
		// TODO Auto-generated method stub
		
	}

}
