package levelone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
import objects.BlueFlag;
import objects.BlueKey;
import objects.BlueLock;
import objects.Box;
import objects.BronzeCoin;
import objects.ExitDoor;
import objects.GoldCoin;
import objects.GreenDiamond;
import objects.GreenFlag;
import objects.GreenKey;
import objects.GreenLock;
import objects.Item;
import objects.OrangeDiamond;
import objects.OrangeFlag;
import objects.OrangeKey;
import objects.OrangeLock;
import objects.SilverCoin;
import objects.Spring;
import objects.YellowDiamond;
import objects.YellowFlag;
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
	private boolean respawnPlayer;
	private boolean moved = false; 

	public GenericLevel(GameMain game) {
		this.game = game;

		this.player = game.getplayer();
		this.hud = new Hud(game);

		hud.resetTimer(25000);

		respawnPlayer = false;
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
					if (object.getProperties().get("type").equals("yellow"))
						new YellowFlag(world, object);
					if (object.getProperties().get("type").equals("green"))
						new GreenFlag(world,object);
					if (object.getProperties().get("type").equals("orange"))
						new OrangeFlag(world,object);
					if (object.getProperties().get("type").equals("blue"))
						new BlueFlag(world,object);
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
					if (object.getProperties().get("type").equals("green"))
						new GreenLock(world, object);
					if (object.getProperties().get("type").equals("yellow"))
						new YellowLock(world, object);
					if (object.getProperties().get("type").equals("blue"))
						new BlueLock(world, object);
					if (object.getProperties().get("type").equals("orange"))
						new OrangeLock(world, object);
					continue;
				} else if (objName.equals("exit")) {
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
				if (objName.equals("exit") || objName.equals("water")) {
					fdef.isSensor = true;
				} else if (objName.equals("wall")) {
					fdef.friction = 0.3f;
				} else if (objName.equals("slope")) {
					fdef.friction -= 0.2f;
				} else if (objName.equals("red enemy")) {
					
				}
			}
			
			/* ============================================================= */

			body.createFixture(fdef).setUserData(objName);
			shape.dispose();
		}
	}

	public void handleInput(float dt) {
		game.getplayer().setVerticleState();
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !player.fellIntoLiquid) {
			game.getplayer().right();
		}

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && !player.fellIntoLiquid) {
			game.getplayer().left();
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && !player.fellIntoLiquid) {
			if (GameInfo.atLvlExit == true) {
				GameInfo.atLvlExit = false;

				this.checkGemBonus();
				this.resetCollectables();
				Sound X = Gdx.audio.newSound(Gdx.files.internal("music/door.mp3"));
				if (GameInfo.sound) {
					long id = X.play();
					X.setVolume(id, .14f);
				}
				if (GameInfo.levelNum == GameInfo.levels.length - 1) {
					game.setScreen(new GameOver(game));
					
					//gives player score bonus based on difficulty:
					if(GameInfo.difficult)
						this.player.setPlayerScore(50000);
					if(GameInfo.extream)
						this.player.setPlayerScore(200000);
					if(GameInfo.normal)
						this.player.setPlayerScore(25000);
					
				} else {
					this.game.loadNextLevel();
				}
			} else {
				game.getplayer().jump();
			}
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			game.setScreen(new PauseMenu(game));
		}
		
	}
	

	public void update(float dt) {

		handleInput(dt);
		offmapCheck();
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

		if (this.respawnPlayer && !world.isLocked()) {
			player.body.setLinearVelocity(0f, 0f);
			player.resetPosition(playerSpawner.getProperties().get("x", float.class),
					playerSpawner.getProperties().get("y", float.class));
			respawnPlayer = false;
			player.headHitWall = false;
			player.hittingWallLeft = false;
			player.hittingWallRight = false;
		}
		player.update();
		this.playerDied();
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
		this.drawKeys();
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

	public void inWaterCheck() {
		if (player.fellIntoLiquid) {
			player.playerLoseLife();
			player.playerLoseLife();
			player.fellIntoLiquid = false;
			this.respawnPlayer = true;
			this.playerDied();
		}
	}
	
	private void offmapCheck() {
		if(player.position().y < -5) {
			player.playerLoseLife();
			this.respawnPlayer = true;
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

	private void resetCollectables() {
		GameInfo.HASGREENKEY = false;
		GameInfo.HASBLUEKEY = false;
		GameInfo.HASORANGEKEY = false;
		GameInfo.HASYELLOWKEY = false;

		GameInfo.HASGREENGEM = false;
		GameInfo.HASBLUEGEM = false;
		GameInfo.HASORANGEGEM = false;
		GameInfo.HASYELLOWGEM = false;
	}
	public void drawKeys() {
		game.getBatch().begin();
		if(GameInfo.HASGREENKEY) {	
		game.getBatch().draw(new Texture("objects/keyGreen.png"),230,570,45,45);	
		}
		if(GameInfo.HASYELLOWKEY) {
			game.getBatch().draw(new Texture("objects/keyYellow.png"),280,570,45,45);		
		}
		if(GameInfo.HASORANGEKEY) {
			game.getBatch().draw(new Texture("objects/keyOrange.png"),330,570,45,45);
		}
		if(GameInfo.HASBLUEKEY) {
			game.getBatch().draw(new Texture("objects/keyBlue.png"),380,570,45,45);
		}
		game.getBatch().end();
	}

	private void checkGemBonus() {
		if (GameInfo.HASBLUEGEM && GameInfo.HASGREENGEM && GameInfo.HASORANGEGEM && GameInfo.HASYELLOWGEM) {
			game.getplayer().setPlayerScore(10000);
		}
	}
	
	public void setPlayerSpanner(float x, float y) {
		this.playerSpawner.getProperties().put("x", x);
		this.playerSpawner.getProperties().put("y", y);
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