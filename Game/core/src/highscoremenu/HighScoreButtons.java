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
	//----------------------- Score Labels----------------//
	private Label scoreLabel;
	private String highScore1;
	private int highScore1Value;
	private String highScore2;
	private int highScore2Value;
	private String highScore3;
	private int highScore3Value;
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
		stage.addActor(backBtn);
		stage.addActor(scoreLabel);
		
	}
	
	/**
	 * @Input: reads in highScores.txt and stores the data into the high score names and data.
	 *  IE: Does not output any data.
	 */
	private void loadInHighScores() {
		 String fileName = "highScores.txt";
		 try {
			FileReader fr = new FileReader(fileName);
			 BufferedReader bufferedReader = new BufferedReader(fr);
			this.highScore1 = bufferedReader.readLine();
			this.highScore1Value = Integer.parseInt(bufferedReader.readLine());
			this.highScore2 = bufferedReader.readLine();
			this.highScore2Value = Integer.parseInt(bufferedReader.readLine());
			this.highScore3 = bufferedReader.readLine();
			this.highScore3Value = Integer.parseInt(bufferedReader.readLine());
			fr.close();
		} catch (FileNotFoundException e) {
			System.out.println("Seek Programers Help. (cgeschwendt)");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Seek Programers Help. (cgeschwendt)");
			e.printStackTrace();
		}
	}


	void createAndLableButtons() {
		backBtn = new ImageButton( new SpriteDrawable(new Sprite(new Texture("HighScoreMenu/Back.png"))));
		backBtn.setPosition(GameInfo.WIDTH/2, 50, Align.center);
		
		FreeTypeFontGenerator generator= new FreeTypeFontGenerator(Gdx.files.internal(("Fonts/blow.ttf")));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size =50;
	
		BitmapFont scoreFont = generator.generateFont(parameter);
		
		scoreLabel=new Label(highScore1 +" "+ highScore1Value+"\n\n"+ highScore2 + " "+ highScore2Value + "\n\n"+ highScore3 + " "+ highScore3Value, 
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
