package levelone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cgeschwendt.game.GameMain;
import com.cgeschwendt.game.Hud;
import com.cgeschwendt.game.gameinfo.GameInfo;

import Mainmenu.MainMenu;
import player.Player;

public class LevelOne implements Screen {
	private GameMain game;
	private Hud hud;
	private OrthographicCamera mainCamera;
	private Viewport gameViewPort;

	 //tiled map variables
	private TmxMapLoader maploader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	
	
	//Box 2D variables
	private World world;
	private Box2DDebugRenderer b2dr;
	
	private Player player;
	
	public LevelOne(GameMain game) {
		this.game = game;
		this.hud = new Hud(game);

		// sets up the main camera for the main menu.
		mainCamera = new OrthographicCamera(GameInfo.WIDTH / GameInfo.PPM , GameInfo.HEIGHT/GameInfo.PPM);
		mainCamera.position.set((GameInfo.WIDTH / 2f)/GameInfo.PPM ,( GameInfo.HEIGHT / 2f)/GameInfo.PPM , 0);
		// sets the mainmenu viewport.
		gameViewPort = new StretchViewport(GameInfo.WIDTH , GameInfo.HEIGHT , mainCamera);
		
		maploader = new TmxMapLoader();
		map = maploader.load("Test Level..tmx");
		renderer = new OrthogonalTiledMapRenderer(map,(1f/GameInfo.PPM));
		
		
		b2dr = new Box2DDebugRenderer();
		
		world = new World(new Vector2(0,-9.8f), true);
		
		player = new Player(world);
		
		
	
		BodyDef bdef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		Body body;
		
		
		// this creates ground body
		for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			bdef.type = BodyDef.BodyType.KinematicBody;
			bdef.position.set((rect.getX() + rect.getWidth() / 2)/GameInfo.PPM,  (rect.getY() + rect.getHeight()/2)/GameInfo.PPM)  ;
			body = world.createBody(bdef);
			shape.setAsBox(rect.getWidth()/2/GameInfo.PPM, rect.getHeight()/2/GameInfo.PPM);
			fdef.shape = shape;
			body.createFixture(fdef);
		}	
	
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	/** Handles the input of keys to the game
	 * 	KEYS IN OPERATION:
	 * 	up,left,right
	 * 
	 * @param dt
	 * @author cgeschwendt
	 */
	public void handleInput(float dt) {
		player.setVerticleState();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT )) 
			player.right();
	
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT )) 
		player.left();
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.UP )) 
			player.jump();	
		
		
	}
	
	
	public void update(float dt) {
		handleInput(dt);
		this.offmapCheck();
		hud.updateTime(dt);
		
        world.step(1 / 60f, 6, 2);
		mainCamera.position.set(player.position().x,player.position().y+150f/GameInfo.PPM, 0);
		mainCamera.update();
		renderer.setView(mainCamera);
		
	}
	
	@Override
	public void render(float delta) {
		update(delta);
		hud.updateTime(delta);
	
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		renderer.render();
		b2dr.render(world, mainCamera.combined);
		
		game.getBatch().setProjectionMatrix(hud.stage.getCamera().combined);
		hud.stage.draw();

	}

/**
 * Checks if the position of the player has fallen below the map living line
 * LINE = X cordinate -> 0
 * @author cgeschwendt	
 */
	void offmapCheck() {
		if(player.position().y <0) {
			player.playerLoseLife();
			if(player.playerDead()) {
				Gdx.app.exit();
			}
			player.resetPosition();
		}		
	}
		
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

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
	public void dispose() {

	}

}
