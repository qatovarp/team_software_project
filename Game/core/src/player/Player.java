package player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.cgeschwendt.game.GameMain;
import com.cgeschwendt.game.gameinfo.GameInfo;

public class Player {

	// PLayers vertical state.
	public enum State {
		FALLING, JUMPING, STANDING
	};

	private int lives;
	private State verticleState;

	private int playerScore;
	private Texture standing;
	private Texture jumpingL;
	private Texture jumpingR;
	public Sprite sprite;
	public boolean faceingRight;

	private TextureAtlas playeratlas;
	private Animation<TextureRegion> playerAnimation;
	
	private Sound jump;
	private Sound walking;
	
	public Body body;
	private World world;

	public boolean fellIntoLiquid;
	
	private float elapsedTime;
	public Hearts hearts;
	
	public boolean hittingWallLeft;
	public boolean hittingWallRight;
	public boolean headHitWall;
	public boolean playWalkingSounds;
	
	public Player(GameMain game) {
		playerScore = 0;
		faceingRight = true;
		fellIntoLiquid = false;
		elapsedTime = 0;
		hearts = new Hearts(game);
		hittingWallLeft = false;
		hittingWallRight = false;
		headHitWall = false;
		playWalkingSounds = true;
		this.setLifeQuantity();
	}
	
	public void playerConstruct(World world, MapObject playerSpawner) {
		playerConstruct(world, playerSpawner.getProperties().get("x", float.class), playerSpawner.getProperties().get("y", float.class));
	}

	/**
	 * Constructs the player into a x,y position on the screen in a given world of
	 * entities
	 * 
	 * @param world
	 *            : the world of entities for each level
	 * @param x
	 *            : the x position to create the player
	 * @param y
	 *            : the y position to create the player
	 * @author cgeschwendt
	 */
	public void playerConstruct(World world, float x, float y) {
		this.loadPlayerTexture();
		sprite = new Sprite(standing);
		jump = Gdx.audio.newSound(Gdx.files.internal("music/jump_08 .mp3"));	
		walking = Gdx.audio.newSound(Gdx.files.internal("music/footstep.wav"));

		this.world = world;
		sprite.setPosition(GameInfo.WIDTH / 2 - 33, y / GameInfo.PPM + 111);
		sprite.setSize(sprite.getWidth()/GameInfo.PPM, sprite.getHeight()/GameInfo.PPM);
		createbody(x, y);
	}
	
	public void renderPlayer(SpriteBatch batch) {
    	if(fellIntoLiquid == false) {
    		batch.begin();
    		if (isJumping()) {
    			batch.draw(getjumpIMG(), sprite.getX(), sprite.getY(), getjumpIMG().getWidth()/GameInfo.PPM, getjumpIMG().getHeight()/GameInfo.PPM);
    		} 
    		else if(!isXMoving()) {
    			elapsedTime += Gdx.graphics.getDeltaTime();
    			batch.draw(getAnimation().getKeyFrame(elapsedTime, true),sprite.getX(), sprite.getY(), getAnimation().getKeyFrame(elapsedTime, true).getRegionWidth()/GameInfo.PPM, getAnimation().getKeyFrame(elapsedTime, true).getRegionHeight()/GameInfo.PPM);
    		}
    		else {
    			sprite.draw(batch);
    		}
    		batch.end();
    	}
    	// draw nothing if player fell in. Gives a vanishing appearance
	}
	
	public void update() {
		sprite.setPosition(position().x - sprite.getWidth()/2f, position().y - sprite.getHeight()/2f/* - 0.48f*/);
	}

	private void createbody(float x, float y) {
		this.faceingRight = true;
		verticleState = State.STANDING;
		
        BodyDef bdef = new BodyDef();
		bdef.position.set(x / GameInfo.PPM, y / GameInfo.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);
        body.setUserData(this);
		
        createHeadRight();
        createHeadLeft();
        createSideRight();
        createSideLeft();
        createFeet();

	}
	
	private void createFeet() {
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
		shape.setAsBox(0.2935f, 0.001f, new Vector2(0f, -0.6491f), 0f);
		//shape.setAsBox(0.293f, 0.001f, new Vector2(0f, -0.6491f), 0f);
		fdef.friction = 1.5f;
        
        fdef.shape = shape;
		body.createFixture(fdef).setUserData("player_feet");
	}
	
	private void createHeadRight() {
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(32f / GameInfo.PPM);
		shape.setPosition(new Vector2(0.01f, 0.2f));
		fdef.friction = 0f;
		fdef.shape = shape;
		body.createFixture(fdef).setUserData("player_head_right");
	}
	
	private void createHeadLeft() {
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(32f / GameInfo.PPM);
		shape.setPosition(new Vector2(-0.01f, 0.2f));
		fdef.friction = 0f;
		fdef.shape = shape;
		body.createFixture(fdef).setUserData("player_head_left");
	}
	
	private void createSideRight() {
		FixtureDef fdef = new FixtureDef();
		//CircleShape shape = new CircleShape();
        PolygonShape shape = new PolygonShape();
		shape.setAsBox(0.1f, 0.4f, new Vector2(0.2f, -0.245f), 0f);
		//shape.setRadius(25f / GameInfo.PPM);
		//shape.setPosition(new Vector2(0f, -0.2f));
		fdef.friction = 0f;
		fdef.shape = shape;
		body.createFixture(fdef).setUserData("player_right");
	}
	
	private void createSideLeft() {
		FixtureDef fdef = new FixtureDef();
		//CircleShape shape = new CircleShape();
        PolygonShape shape = new PolygonShape();
		shape.setAsBox(0.1f, 0.4f, new Vector2(-0.2f, -0.245f), 0f);
		//shape.setRadius(25f / GameInfo.PPM);
		//shape.setPosition(new Vector2(0f, -0.2f));
		fdef.friction = 0f;
		fdef.shape = shape;
		body.createFixture(fdef).setUserData("player_left");
	}

	/**
	 * Flips the animation frames to switch the walking direction
	 * 
	 * @author cgeschwendt
	 */
	private void flipAnimation() {
		Array<TextureAtlas.AtlasRegion> frames = playeratlas.getRegions();
		for (TextureRegion frame : frames) {
			frame.flip(true, false);
		}
	}
	
	private void checkWalkingSonds() {
		this.playWalkingSounds = false;
		Timer.schedule(new Task(){
			@Override
			public void run() {
				playWalkingSounds = true;
			}
		}, 0.1f);
	}

	/**
	 * moves the players position to the right
	 * 
	 * @author cgeschwendt
	 */
	public void right() {
		if(!hittingWallRight) {
			if (body.getLinearVelocity().x <= 2 && !Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
				body.applyLinearImpulse(new Vector2(1.3f, 0f), body.getWorldCenter(), true);
				if(GameInfo.sound && !this.headHitWall && this.verticleState == State.STANDING && playWalkingSounds) {
					long id2 = walking.play();
					walking.setVolume(id2, .07f);
				}
				if (!faceingRight) {
					checkWalkingSonds();
					sprite.flip(true, false);
					faceingRight = true;
					this.flipAnimation();
				}
			}
		}
	}

	/**
	 * moves the players position to the left
	 * 
	 * @author cgeschwendt
	 */
	public void left() {
		if(!hittingWallLeft) {
			if (body.getLinearVelocity().x >= -2 && !Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
				body.applyLinearImpulse(new Vector2(-1.3f, 0f), body.getWorldCenter(), true);
				if(GameInfo.sound && !this.headHitWall && this.verticleState == State.STANDING && playWalkingSounds) {
					long id2 = walking.play();
					walking.setVolume(id2, .07f);
				}
				if (faceingRight) {
					checkWalkingSonds();
					sprite.flip(true, false);
					faceingRight = false;
					this.flipAnimation();
				}
			}
		}
	}

	public void pushPlayer(float x) {
		body.applyLinearImpulse(new Vector2(x, 0f), body.getWorldCenter(), true);
	}
	/**
	 * gives the player the jumping operation only if they have not jumped once
	 * before. ALLOWS FOR ONLY ONE JUMP AT A TIME.
	 * 
	 * @author cgeschwendt
	 */
	public void jump() {
		if (verticleState == State.JUMPING || verticleState == State.FALLING) {
		} else {
			body.applyLinearImpulse(new Vector2(0, 6.5f), body.getWorldCenter(), true);
			verticleState = State.JUMPING;
			if(GameInfo.sound) {
				long id = jump.play();
				jump.setVolume(id, .14f);
			}
		}
	}
	
	public void springJump() {
		body.applyLinearImpulse(new Vector2(0, 5.5f), body.getWorldCenter(), true);
	}
	
	public void spikeHurt(){
		body.applyLinearImpulse(new Vector2(0, 6.5f), body.getWorldCenter(), true);
	}

	/**
	 * Sets the state of the player to either falling, standing or jumping.
	 * 
	 * @author cgeschwendt
	 */
	public void setVerticleState() {
		if (body.getLinearVelocity().y < 0)
			verticleState = State.FALLING;
		else if (body.getLinearVelocity().y > 0)
			verticleState = State.JUMPING;
		else
			verticleState = State.STANDING;
	}

	/**
	 * Return the vector of the players body(body which physics gets applied to)
	 * 
	 * @return players body Vector 2
	 * @author cgeschwendt
	 */
	public Vector2 position() {
		return body.getPosition();
	}

	/**
	 * Resets the players position within the given level base on the x and y
	 * parameters.
	 * 
	 * @param x
	 * @param y
	 * @author cgeschwendt
	 */
	public void resetPosition(float x, float y) {
		verticleState = State.STANDING;
		body.setTransform(x / GameInfo.PPM, y / GameInfo.PPM, 0);
		//Jumpstart gravity
		body.applyLinearImpulse(new Vector2(0f, -0.01f), body.getWorldCenter(), true);
		
	}

	/**
	 * Sets the life quantity of the player based on the game setting Difficulty;
	 * normal : 6 difficult : 4 extream: 2
	 * 
	 * @author cgeschwendt
	 */
	void setLifeQuantity() {
		if (GameInfo.normal)
			this.lives = 6;
		else if (GameInfo.difficult)
			this.lives = 4;
		else if (GameInfo.extream)
			this.lives = 2;
	}

	/**
	 * removes one of the players life
	 * 
	 * @author cgeschwendt
	 */
	public void playerLoseLife() {
		this.lives = this.lives - 1;
	}

	/**
	 * @return: the number of lives that the player has
	 * @author cgeschwendt
	 */
	public int getNumLives() {
		return this.lives;
	}

	/**
	 * @return true if the player has no lives left else false
	 * @author cgeschwendt
	 */
	public boolean playerDead() {
		if (this.lives > 0)
			return false;
		else
			return true;
	}

	/**
	 * Checks if the player is moving along in a horizontal motion.
	 * 
	 * @return true or false
	 * @author cgeschwendt
	 */
	public boolean isXMoving() {
		if (body.getLinearVelocity().x == 0)
			return true;
		else
			return false;
	}

	/**
	 * Gets the verticle state of the player
	 * 
	 * @return FALLING. JUMPING, STANDING
	 * @author cgeschwendt
	 */
	public State getVerticleState() {
		return this.verticleState;
	}

	public int getPlayerScore() {
		return this.playerScore;
	}

	/**
	 * Fetches and returns the payers walk animation
	 * 
	 * @author cgeschwendt
	 * @return Animation<TextureRegion>
	 */
	public Animation<TextureRegion> getAnimation() {
		playerAnimation = new Animation<TextureRegion>(1f / 22f, playeratlas.getRegions());
		return this.playerAnimation;
	}

	/**
	 * Loads the players color based on the settings window GREEN,PINK,BLUE
	 * 
	 * @author cgeschwendt
	 */
	public void loadPlayerTexture() {
		if (GameInfo.playerColor == GameInfo.COLOR.BLUE) {
			standing = new Texture("player/p2_stand.png");
			jumpingL = new Texture("player/p2_jump.png");
			jumpingR = new Texture("player/p2_jump2.png");
			playeratlas = new TextureAtlas("player/bluePlayer.atlas");
		} else if (GameInfo.playerColor == GameInfo.COLOR.GREEN) {
			standing = new Texture("player/p1_stand.png");
			jumpingL = new Texture("player/p1_jump.png");
			jumpingR = new Texture("player/p1_jump2.png");
			playeratlas = new TextureAtlas("player/greenPlayer.atlas");
		} else {
			standing = new Texture("player/p3_stand.png");
			jumpingL = new Texture("player/p3_jump.png");
			jumpingR = new Texture("player/p3_jump2.png");
			playeratlas = new TextureAtlas("player/pinkPlayer.atlas");
		}
	}

	/**
	 * States if the player is jumping
	 */
	public boolean isJumping() {
		if (body.getLinearVelocity().y > 0)
			return true;
		else
			return false;
	}

	/**
	 * Fetches the jumping player animation.
	 * 
	 * @author cgeschwendt
	 * @return Texture
	 */
	public Texture getjumpIMG() {
		if (this.faceingRight)
			return this.jumpingL;
		else
			return this.jumpingR;
	}

	public void setPlayerScore(int points) {
		this.playerScore  = this.playerScore + points;	
	}

}
