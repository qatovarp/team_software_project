package optionsmenu;

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

import Mainmenu.MainMenu;

public class OptionMenuButtons {
	private GameMain game;
	private Stage stage;
	private Viewport viewport;
	
	private ImageButton backBtn;
	private ImageButton normalBtn;
	private ImageButton difficultBtn;
	private ImageButton extreamBtn;
	private ImageButton checkedBtn;
	
	private ImageButton pinkPlayerBtn;
	private ImageButton bluePlayerBtn;
	private ImageButton greenPlayerBtn;
	private ImageButton checkedBtn2;
	
	
	public OptionMenuButtons(GameMain game) {
		this.game = game;
		
		viewport = new FitViewport(GameInfo.WIDTH,GameInfo.HEIGHT,new OrthographicCamera());
		stage = new Stage(viewport, game.getBatch());
		Gdx.input.setInputProcessor(stage);
		
		this.createAndLableButtons();
		this.AddListners();
		//adds buttons to the stage.
		stage.addActor(backBtn);
		stage.addActor(normalBtn);
		stage.addActor(difficultBtn);
		stage.addActor(extreamBtn);
		stage.addActor(checkedBtn);
		stage.addActor(pinkPlayerBtn);
		stage.addActor(bluePlayerBtn);
		stage.addActor(greenPlayerBtn);
		stage.addActor(checkedBtn2);
	}
	
	/**
	 * Creates screens buttons with positions and images
	 * @author cgeschwendt
	 */
	void createAndLableButtons() {
		backBtn = new ImageButton( new SpriteDrawable(new Sprite(new Texture("optionsmenu/back.png"))));
		normalBtn = new ImageButton( new SpriteDrawable(new Sprite(new Texture("optionsmenu/normalBtn.png"))));
		difficultBtn = new ImageButton( new SpriteDrawable(new Sprite(new Texture("optionsmenu/difficultBtn.png"))));
		extreamBtn = new ImageButton( new SpriteDrawable(new Sprite(new Texture("optionsmenu/reallyBtn.png"))));
		checkedBtn = new ImageButton( new SpriteDrawable(new Sprite(new Texture("optionsmenu/checkmark.png"))));
		
		backBtn.setPosition(GameInfo.WIDTH/2, 42, Align.center);
		normalBtn.setPosition(665, 270);
		difficultBtn.setPosition(665,170);
		extreamBtn.setPosition(665,60);
		
		pinkPlayerBtn = new ImageButton( new SpriteDrawable(new Sprite(new Texture("optionsmenu/p3_front.png"))));
		greenPlayerBtn = new ImageButton( new SpriteDrawable(new Sprite(new Texture("optionsmenu/p1_front.png"))));
		bluePlayerBtn = new ImageButton( new SpriteDrawable(new Sprite(new Texture("optionsmenu/p2_front.png"))));
		checkedBtn2 = new ImageButton( new SpriteDrawable(new Sprite(new Texture("optionsmenu/checkmark.png"))));
		
		pinkPlayerBtn.setPosition(250, 75);
		bluePlayerBtn.setPosition(175, 77);
		greenPlayerBtn.setPosition(100, 75);
		
		
		if(GameInfo.playerColor == GameInfo.COLOR.BLUE)
			checkedBtn2.setPosition(170, 85);
		
		if(GameInfo.playerColor == GameInfo.COLOR.GREEN)
			checkedBtn2.setPosition(95, 85);
		
		if(GameInfo.playerColor == GameInfo.COLOR.PINK)
			checkedBtn2.setPosition(245, 85);
		
		this.setCheckedPosition();
	}
	
	/**
	 * Adds all of the listeners to each button.
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
		normalBtn.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				//moves check mark
			checkedBtn.setPosition(normalBtn.getX()+normalBtn.getWidth()-checkedBtn.getWidth()-11, normalBtn.getY()+19);
			//changes game difficulty.
			GameInfo.normal =true;
			GameInfo.difficult =false;
			GameInfo.extream =false;
			
			}	
		});
		difficultBtn.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				//moves check mark
				checkedBtn.setPosition(difficultBtn.getX()+difficultBtn.getWidth()-checkedBtn.getWidth()-11, difficultBtn.getY()+19);
				//changes game difficulty.
				GameInfo.normal =false;
				GameInfo.difficult =true;
				GameInfo.extream =false;
			}	
		});
		extreamBtn.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				//moves check mark
				checkedBtn.setPosition(extreamBtn.getX()+extreamBtn.getWidth()-checkedBtn.getWidth()-11, extreamBtn.getY()+19);
				//changes game difficulty.
				GameInfo.normal =false;
				GameInfo.difficult =false;
				GameInfo.extream =true;
			}
		});
		pinkPlayerBtn.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
			GameInfo.playerColor = GameInfo.COLOR.PINK;
			checkedBtn2.setPosition(245, 85);
			}
		});
		greenPlayerBtn.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				GameInfo.playerColor = GameInfo.COLOR.GREEN;
				checkedBtn2.setPosition(95, 85);
			}
		});
		bluePlayerBtn.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				GameInfo.playerColor = GameInfo.COLOR.BLUE;
				checkedBtn2.setPosition(170, 85);
			}
		});
	}
	
	
	public Stage getStage() {
		return this.stage;
	}
	
	/**
	 * Sets the position of the checked difficulty based on games difficulty:
	 * @author cgeschwendt
	 */
	private void setCheckedPosition() {
		if(GameInfo.normal)
			checkedBtn.setPosition(normalBtn.getX()+normalBtn.getWidth()-checkedBtn.getWidth()-11, normalBtn.getY()+19);
		if(GameInfo.difficult)
			checkedBtn.setPosition(difficultBtn.getX()+difficultBtn.getWidth()-checkedBtn.getWidth()-11, difficultBtn.getY()+19);
		if(GameInfo.extream)
			checkedBtn.setPosition(extreamBtn.getX()+extreamBtn.getWidth()-checkedBtn.getWidth()-11, extreamBtn.getY()+19);
	}
	
}
