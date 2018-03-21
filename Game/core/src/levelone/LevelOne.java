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
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cgeschwendt.game.CustomContactListener;
import com.cgeschwendt.game.GameMain;
import com.cgeschwendt.game.Hud;
import com.cgeschwendt.game.gameinfo.GameInfo;

import Mainmenu.MainMenu;
import gameover.GameOver;
import pausemenu.PauseMenu;
import player.Hearts;
import player.Player;
import player.Player.State;
import sound.sound;

public class LevelOne implements Screen {
	private GameMain game;
	private Hud hud;
	private OrthographicCamera mainCamera;
	private Viewport gameViewPort;
	private sound music;

	 //tiled map variables
	private TmxMapLoader maploader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer maprenderer;
	
	// hearts for player
	private Hearts hearts;
	
	private Player player ;
	private float ElapsedTime = 0;
	 
	//Box 2D variables
	private World world;
	private Box2DDebugRenderer b2dr;
	
	public boolean deleteAnObject;

	public Player getPlayer() {
		return player;
	}
	
	public LevelOne(GameMain game) {
		this.game = game;
		this.player = game.getplayer();
		
		this.hud = new Hud(game);
		hearts= new Hearts(this.game);
		
		deleteAnObject = false;
		
		//music setup
		this.music = game.getmusic();
		music.switchSong("music/music2.mp3");

		// sets up the main camera for the main menu.
		mainCamera = new OrthographicCamera(GameInfo.WIDTH / GameInfo.PPM , GameInfo.HEIGHT/GameInfo.PPM);
		mainCamera.position.set((GameInfo.WIDTH / 2f)/GameInfo.PPM ,( GameInfo.HEIGHT / 2f)/GameInfo.PPM , 0);
		// sets the mainmenu viewport.
		gameViewPort = new StretchViewport(GameInfo.WIDTH , GameInfo.HEIGHT , mainCamera);
		
		maploader = new TmxMapLoader();
		map = maploader.load("LevelOne.tmx");
		maprenderer = new OrthogonalTiledMapRenderer(map,(1f/GameInfo.PPM));
		
		
		b2dr = new Box2DDebugRenderer();
		
		world = new World(new Vector2(0,-9.8f), true);
		World.setVelocityThreshold(0.0f);
		player.playerConstruct(world,128f,950f );
		

		
		BodyDef bdef;
		Shape shape = new PolygonShape();
		FixtureDef fdef;
		Body body;
		
		// Load ALL map-objects collision boxes
		for(int i = 0; i < map.getLayers().getCount(); i++) {
			if(map.getLayers().get(i).getName().equals("background")) {
				continue;
			}
			
			for(MapObject object : map.getLayers().get(i).getObjects()) {
				bdef = new BodyDef();
				fdef = new FixtureDef();
				
				// Define the shape of the object
				if(object instanceof RectangleMapObject) {
					shape = new PolygonShape();
					Rectangle rect = ((RectangleMapObject) object).getRectangle();
					bdef.position.set((rect.getX() + rect.getWidth() / 2)/GameInfo.PPM,  (rect.getY() + rect.getHeight()/2)/GameInfo.PPM);
					((PolygonShape) shape).setAsBox(rect.getWidth()/2/GameInfo.PPM, rect.getHeight()/2/GameInfo.PPM);
				}
				else if(object instanceof PolygonMapObject) {
					shape = new PolygonShape();
					Polygon poly = ((PolygonMapObject) object).getPolygon();
					bdef.position.set((poly.getOriginX())/GameInfo.PPM,  (poly.getOriginY())/GameInfo.PPM);
					
					float[] vertices = poly.getTransformedVertices();
				    float[] worldVertices = new float[vertices.length];
				    
				    for (int j = 0; j < vertices.length; j++) {
				        worldVertices[j] = vertices[j] / GameInfo.PPM;
				    }
				    
				    ((PolygonShape) shape).set(worldVertices);
				}
				else if(object instanceof EllipseMapObject) {
					shape = new CircleShape();
					Ellipse ellipse = ((EllipseMapObject) object).getEllipse();
					bdef.position.set((ellipse.x + ellipse.width / 2) / GameInfo.PPM, (ellipse.y + ellipse.height / 2) / GameInfo.PPM);
					((CircleShape) shape).setRadius(ellipse.width/2/GameInfo.PPM);
				}

				fdef.shape = shape;
				//Set Objects to Static by default. 
				bdef.type = BodyDef.BodyType.StaticBody;
				body = world.createBody(bdef);
				
				/*=========== Change object properties based on name ==========*/
				String objName = object.getName();
				
				if(objName != null) {
					body.setUserData(objName);
					
					if(objName.equals("LvlExit")) {
						fdef.isSensor = true;
					}
					else if(objName.equals("coin")) {
						fdef.isSensor = true;
					}
					else if(objName.equals("wall")) {
						fdef.friction = 0.01f;
					}
					else if(objName.equals("slope")) {
						fdef.friction -= 0.2f;
					}
				}
				/*=============================================================*/
				
				//Finish creating the object
				body.createFixture(fdef);
				
			}
			
		}
		
		shape.dispose();
	}


	

	/** Handles the input of keys to the game
	 * 	KEYS IN OPERATION:
	 * 	up,left,right
	 * 
	 * @param dt
	 * @author cgeschwendt
	 */
	public void handleInput(float dt) {
		game.getplayer().setVerticleState();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT ) && game.getplayer().getVerticleState() != State.FALLING ) 
			game.getplayer().right();
	
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT ) &&  game.getplayer().getVerticleState() != State.FALLING) 
			game.getplayer().left();
	
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.UP )) {
			if(player.atLvlExit == true) {
				player.atLvlExit = false;
				this.game.loadNextLevel();
			}
			else {
				game.getplayer().jump();
				music.playereffect("music/jump1.mp3",false);
			}
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			game.setScreen(new PauseMenu(game));
		}
	}
	
	/**
	 * Created update method to handle specific update
	 * @author cgeschwendt
	 * @param dt
	 */
	public void update(float dt) {
		
		if(deleteAnObject == true)
	    {
	        Array<Body> bodies = new Array<Body>();
	        world.getBodies(bodies);
	        for(int i = 0; i < bodies.size; i++)
	        {
	            if(!world.isLocked()) {
	            	if(bodies.get(i).getUserData() != null && bodies.get(i).getUserData().equals("delete")) {
	            		world.destroyBody(bodies.get(i));
	            	}
	            }
	        }
	        deleteAnObject = false;
	    }
		
		handleInput(dt);
		this.offmapCheck();
		hud.updateTime();
		
        world.step(1 / 60f, 6, 2);
		mainCamera.position.set(game.getplayer().position().x,game.getplayer().position().y+150f/GameInfo.PPM, 0);
		mainCamera.update();
		maprenderer.setView(mainCamera);
		
	}
	
	@Override
	public void render(float delta) {
		update(delta);
		
	//clears and redraws the screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
	//Renders in the map and objects 
		maprenderer.render();
	b2dr.render(world, mainCamera.combined);
		
	//draws onto the screen the HUD
		game.getBatch().setProjectionMatrix(hud.stage.getCamera().combined);
		hud.stage.draw();
		
	//draws hearts to player screen.	
		game.getBatch().begin();
		hearts.updateHearts();
		game.getBatch().end();
		
	//draws the player to the screen
		game.getBatch().begin();
		this.drawPlayerAnimation();
		game.getBatch().end();


	}

private void drawPlayerAnimation() {
	if(!player.isXMoving()) {
	ElapsedTime += Gdx.graphics.getDeltaTime();
	game.getBatch().draw(player.getAnimation().getKeyFrame(ElapsedTime, true),player.sprite.getX(),player.sprite.getY());
	}
	else
		player.sprite.draw(game.getBatch());	
	}




/**
 * Checks if the position of the player has fallen below the map living line
 * LINE = X cordinate -> 0
 * @author cgeschwendt	
 */
	void offmapCheck() {
		if(game.getplayer().position().y < 0) {
			game.getplayer().playerLoseLife();
			game.getplayer().resetPosition( 128f, 950f);
			this.playerDied();
		}		
	}
	//procedes to move game as if the player had died.
	void playerDied() {
		if(game.getplayer().playerDead()) {
			game.getScreen().dispose();
			game.setScreen(new GameOver(game));
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
