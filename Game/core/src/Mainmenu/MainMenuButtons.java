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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cgeschwendt.game.GameMain;
import com.cgeschwendt.game.gameinfo.GameInfo;

import highscoremenu.HighScoreMenu;
import levelone.LevelOne;
import optionsmenu.OptionsMenu;

public class MainMenuButtons {

	private final int DISTANCE_BETWEEN_BUTTONS = 60;
	
	private GameMain game;
	private Stage stage;
	private Viewport viewport;
	
	//----------------Buttons----------------//
	private ImageButton playBtn;
	private ImageButton highScoreBtn;
	private ImageButton optionsBtn;
	private ImageButton quitBtn;
	private ImageButton musicBtn;
	//---------------------------------------//
	
	
	
	
	public MainMenuButtons(GameMain game) {
		this.game = game;
		
		viewport = new FitViewport(GameInfo.WIDTH,GameInfo.HEIGHT,new OrthographicCamera());
		stage = new Stage(viewport, game.getBatch());
		Gdx.input.setInputProcessor(stage);
		
		this.createAndPositionButtons();
		this.addAllListners();
		stage.addActor(highScoreBtn);
		stage.addActor(musicBtn);
		stage.addActor(optionsBtn);
		stage.addActor(playBtn);
		stage.addActor(quitBtn);
	
	}
	
	
	void createAndPositionButtons() {
		playBtn = new ImageButton( new SpriteDrawable(new Sprite(new Texture("mainmenu/Start Game.png"))));
		highScoreBtn = new ImageButton( new SpriteDrawable(new Sprite(new Texture("mainmenu/Highscore.png"))));
		optionsBtn = new ImageButton( new SpriteDrawable(new Sprite(new Texture("mainmenu/Options.png"))));
		quitBtn = new ImageButton( new SpriteDrawable(new Sprite(new Texture("mainmenu/Quit.png"))));
		musicBtn = new ImageButton( new SpriteDrawable(new Sprite(new Texture("mainmenu/Music On.png"))));
		
		playBtn.setPosition(GameInfo.WIDTH/2 - 325, GameInfo.HEIGHT/2);
		optionsBtn.setPosition(playBtn.getX(), playBtn.getY()-DISTANCE_BETWEEN_BUTTONS);
		highScoreBtn.setPosition(playBtn.getX(), optionsBtn.getY() - DISTANCE_BETWEEN_BUTTONS);
		quitBtn.setPosition(playBtn.getX(), highScoreBtn.getY() - DISTANCE_BETWEEN_BUTTONS);
		musicBtn.setPosition(GameInfo.WIDTH -50 - musicBtn.getWidth(), 50);
	}
	
	void addAllListners() {
		playBtn.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.getScreen().dispose();
				game.setScreen(new LevelOne(game));
			}	
		});
		
		highScoreBtn.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.getScreen().dispose();
				game.setScreen(new HighScoreMenu(game));
			
			}	
		});
		optionsBtn.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
			game.getScreen().dispose();
			game.setScreen(new OptionsMenu(game));
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
			
			}	
		});
	}
	
	
	public Stage getStage() {
		return this.stage;
	}
	
}
