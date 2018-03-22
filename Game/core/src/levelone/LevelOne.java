package levelone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.cgeschwendt.game.GameMain;
import com.cgeschwendt.game.Hud;
import com.cgeschwendt.game.gameinfo.GameInfo;

import gameover.GameOver;
import layers.coin;
import layers.ground;
import pausemenu.PauseMenu;
import player.Hearts;
import player.Player;
import player.Player.State;


public class LevelOne implements Screen {
	private GameMain game;
	private Hud hud;
	private OrthographicCamera mainCamera;

	// tiled map variables
	private TmxMapLoader maploader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer maprenderer;

	// hearts for player
	private Hearts hearts;

	private Player player;
	//timer for player animation
	private float ElapsedTime = 0;

	// Box 2D variables
	private World world;
	private Box2DDebugRenderer b2dr;

	public boolean deleteAnObject;


	public LevelOne(GameMain game) {
		this.game = game;
		this.player = game.getplayer();
		
		//sets the level music
		game.setMusic("music/Waltz.mp3");

		//sets the level HUD and timer for the level
		this.hud = new Hud(game);
		this.hud.resetTimer(25000);	//250 seconds
		hearts = new Hearts(this.game);

		deleteAnObject = false;

		// sets up the main camera for the main menu.
		mainCamera = new OrthographicCamera(GameInfo.WIDTH / GameInfo.PPM, GameInfo.HEIGHT / GameInfo.PPM);
		// loads the map to the screen.
		maploader = new TmxMapLoader();
		map = maploader.load("levelOneV2.tmx");
		maprenderer = new OrthogonalTiledMapRenderer(map, (1f / GameInfo.PPM));
		
		//collision line renderer
		b2dr = new Box2DDebugRenderer();

		world = new World(new Vector2(0, -9.8f), true);
		
		//creates the player in the world at the position given.
		player.playerConstruct(world, 260f, 1200f);
		mainCamera.position.set(game.getplayer().position().x, game.getplayer().position().y + 150f / GameInfo.PPM, 0);

		//creates the ground		
		new ground(this.world,map);
		//creates the coin
		new coin(this.world,map);
		
//				/* =========== Change object properties based on name ========== */
//				String objName = object.getName();
//
//				if (objName != null) {
//					body.setUserData(objName);
//
//					if (objName.equals("LvlExit")) {
//						fdef.isSensor = true;
//					} else if (objName.equals("coin")) {
//						fdef.isSensor = true;
//					} else if (objName.equals("wall")) {
//						fdef.friction = 0.01f;
//					} else if (objName.equals("slope")) {
//						fdef.friction -= 0.2f;
//					}
//				}
//				/* ============================================================= */
	}

	/**
	 * Handles the input of keys to the game KEYS IN OPERATION: up,left,right
	 * 
	 * @param dt
	 * @author cgeschwendt
	 */
	public void handleInput(float dt) {
		game.getplayer().setVerticleState();
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && game.getplayer().getVerticleState() != State.FALLING) {
			game.getplayer().right();
		}

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && game.getplayer().getVerticleState() != State.FALLING)
			game.getplayer().left();

		if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			game.getplayer().jump();
		}
		 if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			game.setScreen(new PauseMenu(game));
		}
		 
	}

	/**
	 * Created update method to handle specific update
	 * 
	 * @author cgeschwendt
	 * @param dt
	 */
	public void update(float dt) {

		if (deleteAnObject == true) {
			Array<Body> bodies = new Array<Body>();
			world.getBodies(bodies);
			for (int i = 0; i < bodies.size; i++) {
				if (!world.isLocked()) {
					if (bodies.get(i).getUserData() != null && bodies.get(i).getUserData().equals("delete")) {
						world.destroyBody(bodies.get(i));
					}
				}
			}
			deleteAnObject = false;
		}

		handleInput(dt);
		this.offmapCheck();
		this.timerCheck();
		hud.updateTime();

		world.step(1 / 60f, 6, 2);
		
		this.updateCamera();
		
		mainCamera.update();
		maprenderer.setView(mainCamera);

	}

	@Override
	public void render(float delta) {
		update(delta);

		// clears and redraws the screen
		Gdx.gl.glClearColor( .2f,.3f,.8f,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Renders in the map and objects
		maprenderer.render();
		b2dr.render(world, mainCamera.combined);

		// draws onto the screen the HUD
		game.getBatch().setProjectionMatrix(hud.stage.getCamera().combined);
		hud.stage.draw();

		// draws hearts to player screen.
		game.getBatch().begin();
		hearts.updateHearts();
		game.getBatch().end();

		// draws the player to the screen
		game.getBatch().begin();
		this.drawPlayerAnimation();
		game.getBatch().end();
	
	}

	private void updateCamera() {
		mainCamera.position.set(game.getplayer().position().x, game.getplayer().position().y + 150f / GameInfo.PPM, 0);
	}
	
	/**
	 * draws the desired player animation 
	 * Based on running jumping and standing
	 * @author cgeschwendt
	 */
	private void drawPlayerAnimation() {
		if (player.isJumping()) {
			game.getBatch().draw(player.getjumpIMG(), player.sprite.getX(),player.sprite.getY());
		} else if (!player.isXMoving()) {
			ElapsedTime += Gdx.graphics.getDeltaTime();
			game.getBatch().draw(player.getAnimation().getKeyFrame(ElapsedTime, true),player.sprite.getX(),
					player.sprite.getY());
		} else
			player.sprite.draw(game.getBatch());
	}

	/**
	 * Checks if the position of the player has fallen below the map living line
	 * LINE = X cordinate -> 0
	 * 
	 * @author cgeschwendt
	 */
	void offmapCheck() {
		if(mainCamera.position.y < 3) {
			game.getplayer().playerLoseLife();
			game.getplayer().resetPosition(128f, 950f);
			this.playerDied();
		}
	}

	// Proceeds to move game as if the player had died.
	void playerDied() {
		if (game.getplayer().playerDead()) {
			game.getScreen().dispose();
			game.setScreen(new GameOver(game));
		}
	}
	/**
	 * check if the level has run out of time if it has the player is reset to the start with the same amount 
	 * of time but two lives left
	 * @author cgeschwendt
	 */
	void timerCheck() {
		if(hud.getTime() < 0) {
			player.resetPosition(128f, 950f);
			hud.resetTimer(250);
			player.playerLoseLife();
			player.playerLoseLife();
			this.playerDied();
		}
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
	}

	@Override
	public void dispose() {

	}

}
