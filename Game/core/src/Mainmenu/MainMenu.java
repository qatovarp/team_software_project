package Mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.cgeschwendt.game.GameMain;
import com.cgeschwendt.game.gameinfo.GameInfo;

import pausemenu.PauseMenu;
import player.Player.State;

public class MainMenu implements Screen {

	private GameMain game;
	private OrthographicCamera mainCamera;

	private Texture background;
	private MainMenuButtons buttons;

	private Texture legend;
	private Texture of;
	private Texture Aeson;
	private int screenTimer;
	
	private int konami;
	
	private Music music;

	public MainMenu(GameMain game) {
		this.game = game;
		konami = 0;
		// sets up the main camera for the main menu.
		mainCamera = new OrthographicCamera(GameInfo.WIDTH, GameInfo.HEIGHT);
		mainCamera.position.set(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f, 0);
		// loads in the buttons and background png.
		background = new Texture("mainmenu/background1.png");
		legend = new Texture("mainmenu/legend.png");
		of = new Texture("mainmenu/of.png");
		Aeson = new Texture("mainmenu/Aeson.png");
		buttons = new MainMenuButtons(game);
		screenTimer = 490;
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		checkKonami();
		// clears screen and starts redraw.
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Draws the background of the screen
		game.getBatch().begin();
		game.getBatch().draw(background, 0, 0, GameInfo.WIDTH, GameInfo.HEIGHT);
		this.drawTitle();
		game.getBatch().end();

		// Draws the buttons on the screen in either waiting order of instantly based on if its the first main menu.
		if (GameInfo.firstMainMenu == true) {
			if (screenTimer <= 390) {
				game.getBatch().setProjectionMatrix(buttons.getStage().getCamera().combined);
				buttons.getStage().draw();
			}
		} else {
			game.getBatch().setProjectionMatrix(buttons.getStage().getCamera().combined);
			buttons.getStage().draw();
		}

		// Counts the timer down each render
		if (screenTimer != 0)
			screenTimer = screenTimer - 1;
	}
	
	public void checkKonami() {
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			if(konami == 0 || konami == 1) konami ++;
			else konami = 0;
		}
		else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
			if(konami == 2 || konami == 3) konami ++;
			else konami = 0;
		}
		else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
			if(konami == 4 || konami == 6) konami ++;
			else konami = 0;
		}
		else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
			if(konami == 5 || konami == 7) konami ++;
			else konami = 0;
		}
		else if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
			if(konami == 8) konami ++;
			else konami = 0;
		}
		else if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
			if(konami == 9) konami ++;
			else konami = 0;
		}
		else if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)){
			konami = 0;
		}

		if(konami == 10) {
			GameInfo.konami = !GameInfo.konami;
			game.konami();
			konami = 0;
		}
		 
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

	/**
	 * Draws the title on the main menu in a loading in sequence
	 * 
	 * @author cgeschwendt
	 */
	private void drawTitle() {
		if (GameInfo.firstMainMenu == true) {
			if (screenTimer <= 450)
				game.getBatch().draw(legend, GameInfo.WIDTH / 4, GameInfo.HEIGHT - 155, 250, 150);
			if (screenTimer <= 435)
				game.getBatch().draw(of, GameInfo.WIDTH / 3 + 125, GameInfo.HEIGHT - 155, 75, 125);
			if (screenTimer <= 420)
				game.getBatch().draw(Aeson, GameInfo.WIDTH / 2 - 200, GameInfo.HEIGHT / 2, 400, 250);
		} else {
			game.getBatch().draw(legend, GameInfo.WIDTH / 4, GameInfo.HEIGHT - 155, 250, 150);
			game.getBatch().draw(of, GameInfo.WIDTH / 3 + 125, GameInfo.HEIGHT - 155, 75, 125);
			game.getBatch().draw(Aeson, GameInfo.WIDTH / 2 - 200, GameInfo.HEIGHT / 2, 400, 250);
		}
	}

	public Music getMusic() {
		return this.music;
	}
}
