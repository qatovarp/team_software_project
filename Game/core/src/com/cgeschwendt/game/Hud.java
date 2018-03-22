package com.cgeschwendt.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cgeschwendt.game.gameinfo.GameInfo;

public class Hud {
	public Stage stage;
	private Viewport viewport;
	private OrthographicCamera mainCamera;

	private Integer worldTimer;
	private Integer score;

	private Label scoreLabel;
	private	Label scoreWordLabel;
	private Label timeWordLabel;
	private Label currentTimeLabel;
	private Label levelLabel;
	
	private GameMain game;

	
	/**
	 * HUD constructor : creates all fonts and lables for the HUD screen
	 * Involves timer, score, level 
	 * 
	 * @param game : requires the GameMain to be given, 
	 * @author cgeschwendt
	 */
	public Hud(GameMain game) {
		this.game = game;
		this.worldTimer = 500; //converts to 500 seconds per level
		this.score = game.getplayer().getPlayerScore();

		mainCamera = new OrthographicCamera(GameInfo.WIDTH, GameInfo.HEIGHT);
		mainCamera.position.set(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f, 0);
		viewport = new StretchViewport(GameInfo.WIDTH, GameInfo.HEIGHT, mainCamera);
		stage = new Stage(viewport, game.getBatch());

		// FONT HUD STYLE
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(("Fonts/blow.ttf")));

		// FONT SIZE FOR TIMER AND SCORE
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 35;
		BitmapFont Font = generator.generateFont(parameter);

		// FONT SIZE FOR LEVEL
		FreeTypeFontGenerator.FreeTypeFontParameter parameter3 = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 35;
		BitmapFont Font3 = generator.generateFont(parameter);

		//ALL LABLES FOR HUD SET UP ( POSITION AND STRINGS/NUMBERS)
		scoreLabel = new Label(String.format("%07d", this.score), new Label.LabelStyle(Font, Color.WHITE));
		scoreWordLabel = new Label("score", new Label.LabelStyle(Font, Color.WHITE));
		timeWordLabel = new Label("time", new Label.LabelStyle(Font, Color.WHITE));
		currentTimeLabel = new Label(String.format("%03d", this.worldTimer), new Label.LabelStyle(Font, Color.WHITE));
		levelLabel = new Label("level 1", new Label.LabelStyle(Font3, Color.WHITE));
	
		timeWordLabel.setPosition(GameInfo.WIDTH / 7, GameInfo.HEIGHT - 24, Align.center);
		currentTimeLabel.setPosition(GameInfo.WIDTH / 7+ 10, GameInfo.HEIGHT - 70, Align.center);

		scoreWordLabel.setPosition((GameInfo.WIDTH / 7) * 6, GameInfo.HEIGHT - 20, Align.center);
		scoreLabel.setPosition((GameInfo.WIDTH / 7) * 6 , GameInfo.HEIGHT - 70, Align.center);

		levelLabel.setPosition((GameInfo.WIDTH / 2), GameInfo.HEIGHT - 25, Align.center);

		
		//ADDING ALL LABELS TO THE STAGE.
		stage.addActor(timeWordLabel);
		stage.addActor(scoreWordLabel);
		stage.addActor(currentTimeLabel);
		stage.addActor(scoreLabel);
		stage.addActor(levelLabel);
	}

	
	/**
	 * Update the HUD timer baset on the games update render time should be every second;
	 * @author cgeschwendt
	 */
	public void updateTime() {
		this.worldTimer = (this.worldTimer - 1);
		this.score = game.getplayer().getPlayerScore();
		scoreLabel.setText(String.format("%d", this.score));
		currentTimeLabel.setText(String.format("%03d", this.worldTimer / 100));
	}
	public int getTime() {
		return this.worldTimer;
	}
	public void resetTimer(int x) {
		this.worldTimer = x;
	}

}
