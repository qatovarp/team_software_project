package highscoremenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cgeschwendt.game.GameMain;
import com.cgeschwendt.game.gameinfo.GameInfo;
import java.io.*;

import Mainmenu.MainMenu;

public class HighScoreButtons {
	
	private GameMain game;
	private Stage stage;
	private Viewport viewport;
	
	private ImageButton backBtn;
	//----------------------- Score Label-----------------//
	private Label scoreLabel;
	//----------------------------------------------------//
	
	
	
	/**
	 * Constructor for the class.
	 * @param game : A instance of the GameMain.
	 * 
	 * sets up the viewport, stage for buttons and labels, sets the input processor,
	 * loads in the high scores, and creates and sets all buttons and labels.
	 */
	public HighScoreButtons(GameMain game) {
		this.game = game;
		
		viewport = new FitViewport(GameInfo.WIDTH,GameInfo.HEIGHT,new OrthographicCamera());
		stage = new Stage(viewport, game.getBatch());
		Gdx.input.setInputProcessor(stage);
		
		this.loadInHighScores();
		this.createAndLableButtons();
		this.AddListners();
		//adds buttons to the stage
		stage.addActor(backBtn);
		stage.addActor(scoreLabel);
		
	}
	
	/**
	 * @Input: reads in highScores.txt and stores the data into the high score names and data.
	 *  IE: Does not output any data.
	 *  @author cgeschwendt
	 */
	private void loadInHighScores() {
	
	}

/**
 * Creates the back button as well as the label which contains the high scores.
 * @author cgeschwendt
 */
	void createAndLableButtons() {
		backBtn = new ImageButton( new SpriteDrawable(new Sprite(new Texture("HighScoreMenu/back.png"))));
		backBtn.setPosition(GameInfo.WIDTH/2, 50, Align.center);
		
		FreeTypeFontGenerator generator= new FreeTypeFontGenerator(Gdx.files.internal(("Fonts/blow.ttf")));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size =50;
	
		BitmapFont scoreFont = generator.generateFont(parameter);
		
		scoreLabel=new Label(game.highScore1Name +" "+ game.highScore1+"\n\n"+ game.highScore2Name + " "+ game.highScore2 + "\n\n"+ game.highScore3Name + " "+ game.highScore3, 
				new Label.LabelStyle(scoreFont,Color.BLACK));
		
		scoreLabel.setPosition(GameInfo.WIDTH/2, GameInfo.HEIGHT/3);
	}
	
	/**
	 * add the back button listner to the back button
	 * @author cgeschwendt
	 */
	void AddListners() {
			backBtn.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					game.getScreen().dispose();
					game.setScreen(new MainMenu(game));
				}	
			});
		}
	
	public Stage getStage() {
		return this.stage;
	}
	
	public void setNewHighScore(String name, int score) {
	//TODO: finished checking and setting new scores if they are better.
	}
	
	
}
