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

public class Hud{
	public Stage stage;
	private Viewport viewport;
	private OrthographicCamera mainCamera;
	
	private Integer worldTimer;
	private Integer score;
	
	Label scoreLabel;
	Label scoreWordLabel;
	Label timeWordLabel;
	Label currentTimeLabel;
	Label levelLabel;
	Label GameNameLabel;
	
	
	public Hud(GameMain game) {
		this.worldTimer = 50000;
		this.score = 0;
		
		mainCamera = new OrthographicCamera(GameInfo.WIDTH, GameInfo.HEIGHT);
		mainCamera.position.set(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f, 0);
		viewport = new StretchViewport(GameInfo.WIDTH, GameInfo.HEIGHT, mainCamera); 
		stage = new Stage(viewport,game.getBatch());
		
		
		
		FreeTypeFontGenerator generator= new FreeTypeFontGenerator(Gdx.files.internal(("Fonts/blow.ttf")));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size =50;
		BitmapFont Font = generator.generateFont(parameter);
		
		
		FreeTypeFontGenerator.FreeTypeFontParameter parameter2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size =70;
		BitmapFont Font2 = generator.generateFont(parameter);
		
		FreeTypeFontGenerator.FreeTypeFontParameter parameter3 = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size =30;
		BitmapFont Font3 = generator.generateFont(parameter);
		
		 scoreLabel = new Label(String.format("%07d",this.score ), new Label.LabelStyle(Font,Color.WHITE));
		 scoreWordLabel = new Label("score", new Label.LabelStyle(Font,Color.WHITE));
		 timeWordLabel = new Label("time", new Label.LabelStyle(Font,Color.WHITE));
		 currentTimeLabel = new Label(String.format("%03d",this.worldTimer ), new Label.LabelStyle(Font,Color.WHITE));
		 levelLabel =  new Label("level 1", new Label.LabelStyle(Font3,Color.WHITE));
		 GameNameLabel = new Label("GAME NAME", new Label.LabelStyle(Font2,Color.WHITE));
		
		 timeWordLabel.setPosition(GameInfo.WIDTH/7, GameInfo.HEIGHT-24,Align.center);
		 currentTimeLabel.setPosition(GameInfo.WIDTH/7, GameInfo.HEIGHT-70,Align.center);
		 
		 scoreWordLabel.setPosition((GameInfo.WIDTH/7) *6, GameInfo.HEIGHT-20,Align.center);
		 scoreLabel.setPosition((GameInfo.WIDTH/7) *6, GameInfo.HEIGHT-70,Align.center);
		 
		 GameNameLabel.setPosition((GameInfo.WIDTH/2) , GameInfo.HEIGHT-30,Align.center);
		 levelLabel.setPosition((GameInfo.WIDTH/2) , GameInfo.HEIGHT-65,Align.center);
		 
		 
		 stage.addActor(timeWordLabel);
		 stage.addActor(scoreWordLabel);
		 stage.addActor(currentTimeLabel);
		 stage.addActor(scoreLabel);
		 stage.addActor(GameNameLabel);
		 stage.addActor(levelLabel);
	}


	public void updateTime(float dt) {
		this.worldTimer = (this.worldTimer -1);
		currentTimeLabel.setText(String.format("%03d",this.worldTimer/100 ));
	}
	
	

}
