package Mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cgeschwendt.game.GameMain;
import com.cgeschwendt.game.gameinfo.GameInfo;

public class MainMenu implements Screen {
	
	private GameMain game;
	private OrthographicCamera mainCamera;
	private Viewport gameViewPort;
	private Texture background;
	private MainMenuButtons buttons;
	
	
	public MainMenu(GameMain game) {
		this.game = game;
		//sets up the main camera for the main menu.
		mainCamera = new OrthographicCamera(GameInfo.WIDTH,GameInfo.HEIGHT);
		mainCamera.position.set(GameInfo.WIDTH/2f,GameInfo.HEIGHT/2f,0);
		// sets the mainmenu viewport.
		gameViewPort= new StretchViewport(GameInfo.WIDTH,GameInfo.HEIGHT, mainCamera);
		//loads in the buttons and background png.
		background = new Texture("mainmenu/MainMenuBackground.png");
		buttons = new MainMenuButtons(game);
	}
	
	
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		//clears screen and starts redraw.
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//Draws the background of the screen
		game.getBatch().begin();
		game.getBatch().draw(background,0,0);
		game.getBatch().end();
		
		//Draws the buttons on the screen.
		game.getBatch().setProjectionMatrix(buttons.getStage().getCamera().combined);
		buttons.getStage().draw();
		
	}

	@Override
	public void resize(int width, int height) {	
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		background.dispose();
		buttons.getStage().dispose();
	
	}

}
