package Mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cgeschwendt.game.GameMain;
import com.cgeschwendt.game.gameinfo.GameInfo;

import highscoremenu.HighScoreMenu;
import levelone.GenericLevel;

import optionsmenu.OptionsMenu;

public class MainMenuButtons {


	private GameMain game;
	private Stage stage;
	private Viewport viewport;
	
	//----------------Buttons----------------//
	private ImageButton playBtn;
	private ImageButton highScoreBtn;
	private ImageButton optionsBtn;
	private ImageButton quitBtn;
	private ImageButton musicBtn;
	private ImageButton musicOffBtn;
	//---------------------------------------//

	
	
	public MainMenuButtons(GameMain game) {
		this.game = game;
		
		
		viewport = new FitViewport(GameInfo.WIDTH,GameInfo.HEIGHT,new OrthographicCamera());
		stage = new Stage(viewport, game.getBatch());
		Gdx.input.setInputProcessor(stage);
		
		this.createAndPositionButtons();
		this.addAllListners();
		
		
		//adds each button to a stage.
		stage.addActor(highScoreBtn);
		stage.addActor(musicBtn);
		stage.addActor(optionsBtn);
		stage.addActor(playBtn);
		stage.addActor(quitBtn);
		stage.addActor(musicOffBtn);
		
		//set the appropreate music button
		if(game.getMusic().isPlaying()) {
			this.musicOffBtn.setVisible(false);
		}else {
			this.musicBtn.setVisible(false);
		}
	
	}
	

	/**
	 * Creates the buttons by setting the position and loading in a png to display as the image
	 * @author cgeschwendt
	 */
	void createAndPositionButtons() {
		playBtn = new ImageButton( new SpriteDrawable(new Sprite(new Texture("mainmenu/play.png"))));
		highScoreBtn = new ImageButton( new SpriteDrawable(new Sprite(new Texture("mainmenu/highscores.png"))));
		optionsBtn = new ImageButton( new SpriteDrawable(new Sprite(new Texture("mainmenu/options.png"))));
		quitBtn = new ImageButton( new SpriteDrawable(new Sprite(new Texture("mainmenu/quit.png"))));
		musicBtn = new ImageButton( new SpriteDrawable(new Sprite(new Texture("mainmenu/Music On.png"))));
		musicOffBtn =new ImageButton( new SpriteDrawable(new Sprite(new Texture("mainmenu/Music Off.png"))));
		
		playBtn.setPosition(120, GameInfo.HEIGHT/2.5f);
		optionsBtn.setPosition(playBtn.getX()-48, playBtn.getY()-180);
		highScoreBtn.setPosition(playBtn.getX()-100, playBtn.getY()-120 );
		quitBtn.setPosition(playBtn.getX(), playBtn.getY()-250);
		musicBtn.setPosition(GameInfo.WIDTH -10 - musicBtn.getWidth(), 5);
		musicOffBtn.setPosition(GameInfo.WIDTH -10 - musicBtn.getWidth(), 5);
	}
	
	/**
	 * Adds the listeners to the buttons
	 * @author cgeschwendt
	 */
	void addAllListners() {
		playBtn.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.getScreen().dispose();
				game.newGameSettings();
				game.setScreen(new GenericLevel(game));
				GameInfo.firstMainMenu =false;
			}	
		});
		
		highScoreBtn.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.getScreen().dispose();
				game.setScreen(new HighScoreMenu(game));
				GameInfo.firstMainMenu =false;
			}	
		});
		optionsBtn.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
			game.getScreen().dispose();
			game.setScreen(new OptionsMenu(game));
			GameInfo.firstMainMenu =false;
			}	
		});
		quitBtn.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
			Gdx.app.exit();
			}	
		});
		musicBtn.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
			if(game.getMusic().isPlaying()) {
				game.getMusic().pause();
				switchMusicBtn();
				GameInfo.sound = false;
			}
			}	
		});
		musicOffBtn.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
			if(!game.getMusic().isPlaying()) {
				game.getMusic().play();
				game.getMusic().setPosition(3);
				switchMusicBtn();
				GameInfo.sound = true;
			}
			}	
		});
	}
	
	public void switchMusicBtn() {
		if(this.musicBtn.isVisible()) {
			this.musicBtn.setVisible(false);
			this.musicOffBtn.setVisible(true);
			this.musicOffBtn.setDisabled(false);
		}
		else if(this.musicOffBtn.isVisible()) {
			this.musicBtn.setVisible(true);
			this.musicOffBtn.setVisible(false);
		}
	}
	
	public Stage getStage() {
		return this.stage;
	}
	
}
