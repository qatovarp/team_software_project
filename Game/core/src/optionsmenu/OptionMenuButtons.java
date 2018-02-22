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
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
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
	
	public OptionMenuButtons(GameMain game) {
		this.game = game;
		
		viewport = new FitViewport(GameInfo.WIDTH,GameInfo.HEIGHT,new OrthographicCamera());
		stage = new Stage(viewport, game.getBatch());
		Gdx.input.setInputProcessor(stage);
		
		this.createAndLableButtons();
		this.AddListners();
		stage.addActor(backBtn);
		stage.addActor(normalBtn);
		stage.addActor(difficultBtn);
		stage.addActor(extreamBtn);
		stage.addActor(checkedBtn);
	}
	
	void createAndLableButtons() {
		
		backBtn = new ImageButton( new SpriteDrawable(new Sprite(new Texture("optionsmenu/Back.png"))));
		normalBtn = new ImageButton( new SpriteDrawable(new Sprite(new Texture("optionsmenu/Easy.png"))));
		difficultBtn = new ImageButton( new SpriteDrawable(new Sprite(new Texture("optionsmenu/Medium.png"))));
		extreamBtn = new ImageButton( new SpriteDrawable(new Sprite(new Texture("optionsmenu/Hard.png"))));
		checkedBtn = new ImageButton( new SpriteDrawable(new Sprite(new Texture("optionsmenu/Check Sign.png"))));
		
		backBtn.setPosition(GameInfo.WIDTH/2, 50, Align.center);
		normalBtn.setPosition(GameInfo.WIDTH/2, GameInfo.HEIGHT/3+50);
		difficultBtn.setPosition(GameInfo.WIDTH/2, normalBtn.getY() -65);
		extreamBtn.setPosition(GameInfo.WIDTH/2, difficultBtn.getY() -65);
		checkedBtn.setPosition(normalBtn.getX()+normalBtn.getWidth()-checkedBtn.getWidth()-15, normalBtn.getY()+10);
		
		
	}
	
	void AddListners() {
		backBtn.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.setScreen(new MainMenu(game));
			}	
		});
		normalBtn.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
			checkedBtn.setPosition(normalBtn.getX()+normalBtn.getWidth()-checkedBtn.getWidth()-15, normalBtn.getY()+10);
			}	
		});
		difficultBtn.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				checkedBtn.setPosition(difficultBtn.getX()+difficultBtn.getWidth()-checkedBtn.getWidth()-15, difficultBtn.getY()+10);
			}	
		});
		extreamBtn.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				checkedBtn.setPosition(extreamBtn.getX()+extreamBtn.getWidth()-checkedBtn.getWidth()-15, extreamBtn.getY()+10);
			}
		});
	}
	
	
	public Stage getStage() {
		return this.stage;
	}
	
}
