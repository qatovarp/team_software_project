package pausemenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cgeschwendt.game.GameMain;
import com.cgeschwendt.game.gameinfo.GameInfo;

import Mainmenu.MainMenu;
import levelone.LevelOne;

public class PauseMenuButtons {

	private GameMain game;
	private Stage stage;
	private Viewport viewport;

	private ImageButton resumeBtn;
	private ImageButton optionsBtn;
	private ImageButton quitToMenuBtn;
	private ImageButton exitGameBtn;
	
	private ImageButton musicBtn;
	private ImageButton musicOffBtn;

	public PauseMenuButtons(GameMain game) {
		this.game = game;

		viewport = new FitViewport(GameInfo.WIDTH, GameInfo.HEIGHT, new OrthographicCamera());
		stage = new Stage(viewport, game.getBatch());
		Gdx.input.setInputProcessor(stage);

		this.createAndLableButtons();
		this.AddListners();
		stage.addActor(this.resumeBtn);
		stage.addActor(this.optionsBtn);
		stage.addActor(this.quitToMenuBtn);
		stage.addActor(this.exitGameBtn);
		stage.addActor(musicBtn);
		stage.addActor(musicOffBtn);
		
		//set the appropriate music button
		if(game.getMusic().isPlaying()) {
			this.musicOffBtn.setVisible(false);
		}else {
			this.musicBtn.setVisible(false);
		}
	
	}

	private void createAndLableButtons() {
		this.resumeBtn = new ImageButton( new SpriteDrawable(new Sprite(new Texture("pausemenu/ResumeButton.png"))));
		this.optionsBtn = new ImageButton( new SpriteDrawable(new Sprite(new Texture("pausemenu/Optionsbutton.png"))));
		this.quitToMenuBtn = new ImageButton( new SpriteDrawable(new Sprite(new Texture("pausemenu/MainMenuButton.png"))));
		this.exitGameBtn = new ImageButton( new SpriteDrawable(new Sprite(new Texture("pausemenu/exitgamebutton.png"))));
		this.musicBtn = new ImageButton( new SpriteDrawable(new Sprite(new Texture("mainmenu/Music On.png"))));
		this.musicOffBtn = new ImageButton( new SpriteDrawable(new Sprite(new Texture("mainmenu/Music Off.png"))));
		
		this.resumeBtn.setPosition(GameInfo.WIDTH/2, (GameInfo.HEIGHT/7)*5, Align.center);
		this.optionsBtn.setPosition(GameInfo.WIDTH/2, resumeBtn.getY() -50, Align.center);
		this.quitToMenuBtn.setPosition(GameInfo.WIDTH/2, optionsBtn.getY() -50, Align.center);
		this.exitGameBtn.setPosition(GameInfo.WIDTH/2, quitToMenuBtn.getY()-50, Align.center);
		this.musicBtn.setPosition(GameInfo.WIDTH -10 - musicBtn.getWidth(), 5);
		this.musicOffBtn.setPosition(GameInfo.WIDTH -10 - musicBtn.getWidth(), 5);
	}

	private void AddListners() {
		this.resumeBtn.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.getScreen().dispose();
				game.setScreen(game.getPrevScreen());
			}
		});

		this.optionsBtn.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {

			}
		});

		this.quitToMenuBtn.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.getScreen().dispose();
				game.setScreen(new MainMenu(game));
			}
		});
		this.exitGameBtn.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.getScreen().dispose();
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
