package com.team.menues.gameover;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.team.game.GameInfo;
import com.team.game.GameMain;
import com.team.menues.main.MainMenu;

public class GameOverButtons {
	private GameMain game;
	private Stage stage;
	private Viewport viewport;
	
	//----------------Buttons----------------//
	private ImageButton mainMenu;
	private ImageButton quit;
	private Label Scorelabel;
	//---------------------------------------//
	public GameOverButtons(GameMain game) {
	 this.game = game;
		
		viewport = new FitViewport(GameInfo.WIDTH,GameInfo.HEIGHT,new OrthographicCamera());
		stage = new Stage(viewport, game.getBatch());
		Gdx.input.setInputProcessor(stage);
		
		this.createAndPositionButtons();
		this.addAllListners();
		
		//adds each button to a stage.
		stage.addActor(mainMenu);
		stage.addActor(quit);
		stage.addActor(Scorelabel);
		}
	private void createAndPositionButtons() {
		this.quit = new ImageButton( new SpriteDrawable(new Sprite(new Texture("gameover/quitV2.png"))));
		this.mainMenu = new ImageButton( new SpriteDrawable(new Sprite(new Texture("gameover/mainmenu.png"))));
		
		this.quit.setPosition(10, 20);
		this.mainMenu.setPosition(10, 125);
		this.createScore();
		
	}
	private void addAllListners() {
		this.quit.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.exit();
			}	
		});
		this.mainMenu.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				GameInfo.firstMainMenu = false;
				game.getScreen().dispose();
				game.setScreen(new MainMenu(game));
			}	
		});
		
	}	
	
	void createScore() {
		FreeTypeFontGenerator generator= new FreeTypeFontGenerator(Gdx.files.internal(("Fonts/blow.ttf")));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 50;
	
		BitmapFont scoreFont = generator.generateFont(parameter);
		Scorelabel=new Label(Integer.toString(game.getplayer().getPlayerScore()),new Label.LabelStyle(scoreFont,Color.BLACK));
		Scorelabel.setPosition(GameInfo.WIDTH/2-25, GameInfo.HEIGHT/2+15);
	}
	
	public Stage getStage() {
		return this.stage;
	}
	
	
}
