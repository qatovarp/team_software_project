package pausemenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.cgeschwendt.game.GameMain;
import com.cgeschwendt.game.gameinfo.GameInfo;

public class PauseMenu implements Screen {
	private GameMain game;
	private OrthographicCamera mainCamera;
	private PauseMenuButtons buttons;

	
	public PauseMenu(GameMain game) {
		this.game = game;
		game.setPrevScreen(game.getScreen());
		// sets up the main camera for the main menu.
		mainCamera = new OrthographicCamera(GameInfo.WIDTH, GameInfo.HEIGHT);
		mainCamera.position.set(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f, 0);

		// TODO: set pause background path.
	//	background = new Texture("");
		buttons = new PauseMenuButtons(game);

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		// clears screen and starts redraw.
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.getBatch().begin();
	//	game.getBatch().draw(background, 0, 0);
		game.getBatch().end();

		game.getBatch().setProjectionMatrix(buttons.getStage().getCamera().combined);
		buttons.getStage().draw();

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

	}

}
