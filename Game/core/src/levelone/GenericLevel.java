package levelone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.cgeschwendt.game.CustomContactListener;
import com.cgeschwendt.game.GameMain;
import com.cgeschwendt.game.Hud;
import com.cgeschwendt.game.gameinfo.GameInfo;

import gameover.GameOver;
import objects.BlueDiamond;
import objects.BlueKey;
import objects.BlueLock;
import objects.Box;
import objects.BronzeCoin;
import objects.ExitDoor;
import objects.GoldCoin;
import objects.GreenDiamond;
import objects.GreenKey;
import objects.GreenLock;
import objects.Item;
import objects.OrangeDiamond;
import objects.OrangeKey;
import objects.OrangeLock;
import objects.SilverCoin;
import objects.Spring;
import objects.YellowDiamond;
import objects.YellowKey;
import objects.YellowLock;
import objects.spike;
import pausemenu.PauseMenu;
import player.Player;
import player.Player.State;

public class GenericLevel implements Screen {
	private GameMain game;
	private Hud hud;
	private OrthographicCamera mainCamera;
	private CustomContactListener cl;
	private TiledMap map;
	private OrthogonalTiledMapRenderer maprenderer;
	private Player player;
	public World world;
	private Box2DDebugRenderer b2dr;
	private MapObject playerSpawner;

	public GenericLevel(GameMain game) {
		this.game = game;

		this.player = game.getplayer();
		this.hud = new Hud(game);
		
		game.setBackground();
		// Sets the levels music
		// CHANGE to GameInfo ARRAY.levelID
		game.setMusic(GameInfo.music[GameInfo.levelNum]);
		if (!GameInfo.sound)
			game.getMusic().pause();

		// sets up the main camera for the main menu.
		mainCamera = new OrthographicCamera(GameInfo.WIDTH / GameInfo.PPM, GameInfo.HEIGHT / GameInfo.PPM);

		// loads the map to the screen.

		map = new TmxMapLoader().load(GameInfo.levels[GameInfo.levelNum]);

		maprenderer = new OrthogonalTiledMapRenderer(map, (1f / GameInfo.PPM));
		b2dr = new Box2DDebugRenderer();
		world = new World(new Vector2(0, -9.8f), true);
		cl = new CustomContactListener(this);
		world.setContactListener(cl);
		// World.setVelocityThreshold(0.0f);

		hud.resetTimer(25000);
		game.setBackground();

		// creates the player in the world
		playerSpawner = map.getLayers().get("objectsAndHitboxes").getObjects().get("player");
		player.playerConstruct(world, playerSpawner);
		this.resetCollectable();
		mainCamera.position.set(game.getplayer().position().x, game.getplayer().position().y + 150f / GameInfo.PPM, 0);

		this.loadMapObjects();

	}

	private void loadMapObjects() {
		BodyDef bdef;
		PolygonShape shape;
		FixtureDef fdef;
		Body body;

		for (MapObject object : map.getLayers().get("objectsAndHitboxes").getObjects()) {
			String objName = object.getName();

			/* ====================== Create Objects ======================= */

			if (objName != null) {
				if (objName.equals("coin")) {
					if (object.getProperties().get("type").equals("gold"))
						new GoldCoin(world, object);
					if (object.getProperties().get("type").equals("silver"))
						new SilverCoin(world, object);
					if (object.getProperties().get("type").equals("bronze"))
						new BronzeCoin(world, object);
					continue;
				} else if (objName.equals("button")) {
					continue;
				} else if (objName.equals("spike")) {
					new spike(world, object);
					continue;
				} else if (objName.equals("flag")) {
					continue;
				} else if (objName.equals("diamond")) {
					if (object.getProperties().get("type").equals("yellow"))
						new YellowDiamond(world, object);
					if (object.getProperties().get("type").equals("green"))
						new GreenDiamond(world, object);
					if (object.getProperties().get("type").equals("orange"))
						new OrangeDiamond(world, object);
					if (object.getProperties().get("type").equals("blue"))
						new BlueDiamond(world, object);
					continue;
				} else if (objName.equals("spring")) {
					new Spring(world, object);
					continue;
				} else if (objName.equals("key")) {
					if (object.getProperties().get("type").equals("yellow"))
						new YellowKey(world, object);
					if (object.getProperties().get("type").equals("green"))
						new GreenKey(world, object);
					if (object.getProperties().get("type").equals("orange"))
						new OrangeKey(world, object);
					if (object.getProperties().get("type").equals("blue"))
						new BlueKey(world, object);
					continue;

				} else if (objName.equals("player")) {
					// don't draw the player spawner
					continue;
				} else if (objName.equals("keyBlock")) {
					if(object.getProperties().get("type").equals("green"))
						new GreenLock(world, object);
					if(object.getProperties().get("type").equals("blue"))
						new BlueLock(world, object);
					if(object.getProperties().get("type").equals("orange"))
						new OrangeLock(world, object);
					if(object.getProperties().get("type").equals("yellow"))
						new YellowLock(world, object);
					continue;
				}
				else if (objName.equals("exit")) {
					new ExitDoor(world, object);
					continue;
				}

			}

			/* ============================================================= */

			bdef = new BodyDef();
			fdef = new FixtureDef();
			shape = new PolygonShape();

			if (object instanceof RectangleMapObject) {
				Rectangle rect = ((RectangleMapObject) object).getRectangle();
				bdef.position.set((rect.x + rect.width / 2f) / GameInfo.PPM,
						(rect.y + rect.height / 2f) / GameInfo.PPM);
				shape.setAsBox(rect.width / 2f / GameInfo.PPM, rect.height / 2f / GameInfo.PPM);
			} else if (object instanceof PolygonMapObject) {
				Polygon poly = ((PolygonMapObject) object).getPolygon();
				bdef.position.set(poly.getOriginX() / GameInfo.PPM, poly.getOriginY() / GameInfo.PPM);

				float[] vertices = poly.getTransformedVertices();
				float[] worldVertices = new float[vertices.length];

				for (int i = 0; i < vertices.length; i++) {
					worldVertices[i] = vertices[i] / GameInfo.PPM;
				}

				shape.set(worldVertices);
			}

			fdef.shape = shape;
			bdef.type = BodyDef.BodyType.StaticBody;
			body = world.createBody(bdef);

			/* =========== Change "Room" properties based on name ========== */

			if (objName != null) {
				if (objName.equals("water")) {
					fdef.isSensor = true;
				} else if (objName.equals("wall")) {
					fdef.friction = 0.1f;
				} else if (objName.equals("slope")) {
					fdef.friction -= 0.2f;
				}
			}
			/* ============================================================= */

			body.createFixture(fdef).setUserData(objName);
			shape.dispose();
		}
	}

	public void handleInput(float dt) {
		game.getplayer().setVerticleState();
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && game.getplayer().getVerticleState() != State.FALLING) {
			game.getplayer().right();
		}

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && game.getplayer().getVerticleState() != State.FALLING) {
			game.getplayer().left();
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			if (GameInfo.atLvlExit == true) {
				GameInfo.atLvlExit = false;
				if(	GameInfo.HASBLUEGEM  && GameInfo.HASGREENGEM  && GameInfo.HASYELLOWGEM && GameInfo.HASORANGEGEM ) {
					player.setPlayerScore(10000);
				}
				if(GameInfo.sound) {
					Sound s = Gdx.audio.newSound(Gdx.files.internal("music/door.mp3"));	
					long id2 = s.play();
					s.setVolume(id2, .07f);
				}
				GameInfo.levelNum = GameInfo.levelNum++;
				if(GameInfo.levelNum == GameInfo.levels.length -1)
					
					game.setScreen( new GameOver(game));
				else	
					this.game.loadNextLevel();
			} else {
				game.getplayer().jump();
			}
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			game.setScreen(new PauseMenu(game));
		}
	}

	public void update(float dt) {
		this.playerDied();
		this.timerCheck();

		handleInput(dt);
		this.offmapCheck();
		hud.updateTime();

		world.step(1 / 60f, 6, 2);

		// Update ALL objects
		Array<Body> bodies = new Array<Body>();
		world.getBodies(bodies);
		for (Body body : bodies) {
			if (body.getUserData() instanceof Item) {
				((Item) body.getUserData()).update(dt);
			}
		}
		player.update();
		updateCamera();
	}


	@Override
	public void render(float delta) {
		update(delta);
		SpriteBatch batch = game.getBatch();
		// clears and redraws the screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.renderBackground();
		maprenderer.render();
		b2dr.render(world, mainCamera.combined);

		batch.setProjectionMatrix(mainCamera.combined);

		// Render ALL objects
		batch.begin();
		Array<Body> bodies = new Array<Body>();
		world.getBodies(bodies);
		for (Body body : bodies) {
			if (body.getUserData() instanceof Item) {
				((Item) body.getUserData()).draw(batch);
			}
		}
		batch.end();

		player.renderPlayer(batch);

		batch.setProjectionMatrix(hud.stage.getCamera().combined);

		hud.stage.draw();
		player.hearts.updateHearts();

	}

	public Player getPlayer() {
		return this.player;
	}

	private void updateCamera() {
		if (player.fellIntoLiquid) {
			mainCamera.position.set(mainCamera.position.x, mainCamera.position.y, 0);
		} else if (game.getplayer().position().y > 3f) {
			mainCamera.position.set(game.getplayer().position().x, game.getplayer().position().y + 150f / GameInfo.PPM,
					0);
		} else {
			mainCamera.position.set(game.getplayer().position().x, mainCamera.position.y, 0);
		}
		mainCamera.update();
		maprenderer.setView(mainCamera);
	}

	private void offmapCheck() {
		if (game.getplayer().position().y < -5) {
			game.getplayer().playerLoseLife();
			game.getplayer().playerLoseLife();
			player.fellIntoLiquid = false;
			player.resetPosition(playerSpawner.getProperties().get("x", float.class),
					playerSpawner.getProperties().get("y", float.class));

			this.playerDied();
		}
	}

	// procedes to move game as if the player had died.
	private void playerDied() {
		if (game.getplayer().playerDead()) {
			game.getScreen().dispose();
			game.setScreen(new GameOver(game));
		}
	}


	private void resetCollectable() {
		GameInfo.HASBLUEKEY = false;
		GameInfo.HASGREENKEY = false;
		GameInfo.HASYELLOWKEY = false;
		GameInfo.HASORANGEKEY = false;
		GameInfo.HASBLUEGEM = false;
		GameInfo.HASGREENGEM = false;
		GameInfo.HASYELLOWGEM = false;
		GameInfo.HASORANGEGEM = false;
		
	}
	
	private void timerCheck() {
	if(hud.getTime() == 0) {
		player.resetPosition(playerSpawner.getProperties().get("x", float.class),
				playerSpawner.getProperties().get("y", float.class));
		hud.resetTimer(25000);
		game.getplayer().playerLoseLife();
		game.getplayer().playerLoseLife();
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
	}

	@Override
	public void show() {
	}

	@Override
	public void dispose() {
		
	}

}