package highscoremenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cgeschwendt.game.GameMain;
import com.cgeschwendt.game.gameinfo.GameInfo;

public class HighScoreMenu implements Screen {
	private GameMain game;
	private OrthographicCamera mainCamera;
	private Viewport gameViewPort;
	private Texture background;
	
	private HighScoreButtons buttons;

	public HighScoreMenu(GameMain game) {
		this.game = game;

		// sets up the main camera for the main menu.
		mainCamera = new OrthographicCamera(GameInfo.WIDTH, GameInfo.HEIGHT);
		mainCamera.position.set(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f, 0);
		// sets the mainmenu viewport.
		gameViewPort = new StretchViewport(GameInfo.WIDTH, GameInfo.HEIGHT, mainCamera);
		
		background = new Texture("HighScoreMenu/HighScoreBackground.png");
		buttons = new HighScoreButtons(this.game);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.getBatch().begin();
		game.getBatch().draw(background,0,0);
		game.getBatch().end();
		
		//draws the buttons and score with names onto the screen.
		game.getBatch().setProjectionMatrix(buttons.getStage().getCamera().combined);
		buttons.getStage().draw();

	}

	@Override
	public void resize(int width, int height) {
		this.gameViewPort.update(width, height);

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
		this.background.dispose();
		this.buttons.getStage().dispose();

	}

}
