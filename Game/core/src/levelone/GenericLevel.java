package levelone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.cgeschwendt.game.CustomContactListener;
import com.cgeschwendt.game.GameMain;
import com.cgeschwendt.game.Hud;
import com.cgeschwendt.game.gameinfo.GameInfo;

import gameover.GameOver;
import objects.Box;
import objects.Coin;
import objects.Item;
import pausemenu.PauseMenu;
import player.Hearts;
import player.Player;
import player.Player.State;

public class GenericLevel implements Screen {
	private GameMain game;
	private Hud hud;
	private OrthographicCamera mainCamera;
	private CustomContactListener cl;

	// tiled map variables
	private TmxMapLoader maploader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer maprenderer;

	private Player player;

	// Box 2D variables
	private World world;
	private Box2DDebugRenderer b2dr;
	
	private MapObject playerSpawner;
	public boolean deleteAnObject;
	
	public GenericLevel(GameMain game) {
		this.game = game;
		this.player = game.getplayer();
		this.hud = new Hud(game);
		this.deleteAnObject = false;
		
		hud.resetTimer(25000);
		
		game.setBackground();
		game.setMusic();

		// sets up the main camera for the main menu.
		mainCamera = new OrthographicCamera(GameInfo.WIDTH / GameInfo.PPM, GameInfo.HEIGHT / GameInfo.PPM);
		
		// loads the map to the screen.
		maploader = new TmxMapLoader();
		map = maploader.load(GameInfo.getLvlFileName(game.getCurrentLvlID()));
		maprenderer = new OrthogonalTiledMapRenderer(map, (1f / GameInfo.PPM));

		//collision renderer
		b2dr = new Box2DDebugRenderer();

		world = new World(new Vector2(0, -9.8f), true);
		cl = new CustomContactListener(this);
		world.setContactListener(cl);
		//World.setVelocityThreshold(0.0f);

		//creates the player in the world
		playerSpawner = map.getLayers().get("objectsAndHitboxes").getObjects().get("player");
		player.playerConstruct(world, playerSpawner.getProperties().get("x", float.class), playerSpawner.getProperties().get("y", float.class));
		mainCamera.position.set(game.getplayer().position().x, game.getplayer().position().y + 150f / GameInfo.PPM, 0);

		BodyDef bdef;
		Shape shape = new PolygonShape();
		FixtureDef fdef;
		Body body;
		
		// Load ALL map-objects collision boxes
		for(int i = 0; i < map.getLayers().getCount(); i++) {
			if(map.getLayers().get(i).getName().equals("background") || map.getLayers().get(i).getName().equals("foreground")) {
				continue;
			}
			
			for(MapObject object : map.getLayers().get(i).getObjects()) {
				String objName = object.getName();
				
				if(objName != null) {
					if(objName.equals("coin")) {
						new Coin(this, object.getProperties().get("x", float.class), object.getProperties().get("y", float.class));
						continue;
					}
					else if(objName.equals("player")) {
						// don't draw the player spawner
						continue;
					}
					else if(objName.equals("box")) {
						new Box(this, object.getProperties().get("x", float.class), object.getProperties().get("y", float.class));
						continue;
					}
				}
				bdef = new BodyDef();
				fdef = new FixtureDef();
				
				// Define the shape of the object
				if(object instanceof RectangleMapObject) {
					shape = new PolygonShape();
					Rectangle rect = ((RectangleMapObject) object).getRectangle();
					bdef.position.set((rect.getX() + rect.getWidth() / 2)/GameInfo.PPM,  (rect.getY() + rect.getHeight()/2)/GameInfo.PPM);
					((PolygonShape) shape).setAsBox(((float)rect.getWidth())/2.0f/GameInfo.PPM, ((float)rect.getHeight())/2.0f/GameInfo.PPM);
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
				else if(object instanceof TiledMapTileMapObject){
					shape = new CircleShape();
					bdef.position.set(((TiledMapTileMapObject) object).getX() / GameInfo.PPM,  ((TiledMapTileMapObject) object).getY() / GameInfo.PPM);
					shape.setRadius(33f / GameInfo.PPM);
				}
				else if(object instanceof PolylineMapObject) {
					shape = new ChainShape();
					float[] vertices = ((PolylineMapObject) object).getPolyline().getTransformedVertices();
				    Vector2[] worldVertices = new Vector2[vertices.length / 2];

				    for (int j = 0; j < vertices.length / 2; ++j) {
				    	worldVertices[j] = new Vector2();
				        worldVertices[j].x = vertices[j * 2] / GameInfo.PPM;
				        worldVertices[j].y = vertices[j * 2 + 1] / GameInfo.PPM;
				    }
				     
				    ((ChainShape) shape).createChain(worldVertices);
				}

				fdef.shape = shape;
				//Set Objects to Static by default. 
				bdef.type = BodyDef.BodyType.StaticBody;
				body = world.createBody(bdef);
				
				/*=========== Change object properties based on name ==========*/
				
				if(objName != null) {					
					if(objName.equals("LvlExit")) {
						fdef.isSensor = true;
					}
					else if(objName.equals("drown")) {
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
				body.createFixture(fdef).setUserData(objName);
				
			}
			
		}
		
		shape.dispose();
	}

	public World getWorld() {
		return this.world;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public void spawnPlayer() {
		player.fellIntoLiquid = false;
		player.resetPosition(playerSpawner.getProperties().get("x", float.class), playerSpawner.getProperties().get("y", float.class));
	}
	
	public void handleInput(float dt) {
		game.getplayer().setVerticleState();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT ) && game.getplayer().getVerticleState() != State.FALLING ) {
			game.getplayer().right();
		}
	
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT ) &&  game.getplayer().getVerticleState() != State.FALLING) {
			game.getplayer().left();
		}
	
		if(Gdx.input.isKeyJustPressed(Input.Keys.UP )) {
			if(player.atLvlExit == true) {
				player.atLvlExit = false;
				this.game.loadNextLevel();
			}
			else {
				game.getplayer().jump();
			}
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			game.setScreen(new PauseMenu(game));
		}
	}
	
	public void update(float dt) {
		
		handleInput(dt);
		handleObjectDeletion();
		this.offmapCheck();
		hud.updateTime();
		
        world.step(1 / 60f, 6, 2);

		Array<Body> bodies = new Array<Body>();
		world.getBodies(bodies);
		for(Body body : bodies) {
			if(body.getUserData() instanceof Item) {
				((Item)body.getUserData()).update(dt);
			}
		}

		player.update();
		updateCamera();
		
	}
	
	public void updateCamera() {
		if(player.fellIntoLiquid) {
        	mainCamera.position.set(mainCamera.position.x, mainCamera.position.y, 0);
		}
		else if(game.getplayer().position().y > 3f) {
        	mainCamera.position.set(game.getplayer().position().x, game.getplayer().position().y+150f/GameInfo.PPM, 0);
        }
        else {
        	mainCamera.position.set(game.getplayer().position().x, mainCamera.position.y, 0);
        }
		mainCamera.update();
		maprenderer.setView(mainCamera);
	}
	
	public void handleObjectDeletion() {
		if(deleteAnObject == true)
	    {
	        Array<Fixture> fixtures = new Array<Fixture>();
	        world.getFixtures(fixtures);
	        for(int i = 0; i < fixtures.size; i++)
	        {
	            if(!world.isLocked()) {
	            	if(fixtures.get(i).getUserData() != null && fixtures.get(i).getUserData().equals("delete")) {
	            		world.destroyBody(fixtures.get(i).getBody());
	            	}
	            }
	        }
	        deleteAnObject = false;
	    }
	}
	
	@Override
	public void render(float delta) {
		update(delta);
		SpriteBatch batch = game.getBatch();
	//clears and redraws the screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.renderBackground();
		
		//Draw the map
		maprenderer.render();
		// Draw the Collision boxes (DUBUG)
	    //b2dr.render(world, mainCamera.combined);

	    batch.setProjectionMatrix(mainCamera.combined);
		
	    batch.begin();
		Array<Body> bodies = new Array<Body>();
		world.getBodies(bodies);
		for(Body body : bodies) {
			if(body.getUserData() instanceof Item) {
				((Item)body.getUserData()).draw(batch);
			}
		}
		batch.end();

		// Draw the player
		player.renderPlayer(batch);
		
		//draws onto the screen the HUD
		batch.setProjectionMatrix(hud.stage.getCamera().combined);
		hud.stage.draw();	
		player.hearts.updateHearts();

	}

	private void offmapCheck() {
		if(game.getplayer().position().y < -5) {
			game.getplayer().playerLoseLife();
			this.spawnPlayer();
			this.playerDied();
		}		
	}
	//procedes to move game as if the player had died.
	private void playerDied() {
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