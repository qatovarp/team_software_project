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

import Mainmenu.MainMenu;

public class HighScoreButtons {
	
	private GameMain game;
	private Stage stage;
	private Viewport viewport;
	
	private ImageButton backBtn;
	
	private Label scoreLabel;
	private String highScore1Name = "Name ";
	private int highScore1Value = 1000;
	private String highScore2 = "Name2 ";
	private int highScore2Value =500;
	private String highScore3 = "name3 ";
	private int highScore3Value = 10;
	
	
	public HighScoreButtons(GameMain game) {
		this.game = game;
		
		viewport = new FitViewport(GameInfo.WIDTH,GameInfo.HEIGHT,new OrthographicCamera());
		stage = new Stage(viewport, game.getBatch());
		Gdx.input.setInputProcessor(stage);
		
		this.createAndLableButtons();
		this.AddListners();
		stage.addActor(backBtn);
		stage.addActor(scoreLabel);
		
	}
	
	
	void createAndLableButtons() {
		backBtn = new ImageButton( new SpriteDrawable(new Sprite(new Texture("HighScoreMenu/Back.png"))));
		backBtn.setPosition(GameInfo.WIDTH/2, 50, Align.center);
		
		FreeTypeFontGenerator generator= new FreeTypeFontGenerator(Gdx.files.internal(("Fonts/blow.ttf")));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size =50;
	
		BitmapFont scoreFont = generator.generateFont(parameter);
		
		scoreLabel=new Label(highScore1Name + highScore1Value+"\n\n"+ highScore2+highScore2Value + "\n\n"+ highScore3 + highScore3Value, 
				new Label.LabelStyle(scoreFont,Color.BLACK));
		
		scoreLabel.setPosition(GameInfo.WIDTH/2, GameInfo.HEIGHT/3);
		
		
	}
	
	void AddListners() {
			backBtn.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
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
