package player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.cgeschwendt.game.gameinfo.GameInfo;

public class Player {

	// PLayers vertical state.
	public enum State {
		FALLING, JUMPING, STANDING
	};

	private int lives;
	private State verticleState;

	private int playerScore = 10000;
	private Texture standing;
	private Texture jumpingL;
	private Texture jumpingR;
	public Sprite sprite;
	private boolean faceingRight = true;

	private TextureAtlas playeratlas;
	private Animation<TextureRegion> playerAnimation;

	public Body body;
	private World world;

	

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

		this.world = world;
		createbody(x, y);
		sprite.setPosition(GameInfo.WIDTH / 2 - 33, y / GameInfo.PPM + 111);
		this.setLifeQuantity();
	}

	private void createbody(float x, float y) {
		this.faceingRight = true;
		BodyDef bdef = new BodyDef();
		bdef.position.set(x / GameInfo.PPM, y / GameInfo.PPM);
		bdef.type = BodyDef.BodyType.DynamicBody;
		body = world.createBody(bdef);
		body.setUserData("player");
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();

		shape.setRadius(33f / GameInfo.PPM);
		fdef.friction = 1.5f;
		fdef.shape = shape;
		body.createFixture(fdef);
		verticleState = State.STANDING;
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

	/**
	 * moves the players position to the right
	 * 
	 * @author cgeschwendt
	 */
	public void right() {
		if (body.getLinearVelocity().x <= 2 && !Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			body.applyLinearImpulse(new Vector2(1.6f, -1.9f), body.getWorldCenter(), true);	
			if (!faceingRight) {
				sprite.flip(true, false);
				faceingRight = true;
				this.flipAnimation();
			}
		}
	}

	/**
	 * moves the players position to the left
	 * 
	 * @author cgeschwendt
	 */
	public void left() {
		if (body.getLinearVelocity().x >= -2 && !Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			body.applyLinearImpulse(new Vector2(-1.6f, -1.9f), body.getWorldCenter(), true);
			if (faceingRight) {
				sprite.flip(true, false);
				faceingRight = false;
				this.flipAnimation();
			}
		}
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
			body.applyLinearImpulse(new Vector2(0, 5.8f), body.getWorldCenter(), true);
			verticleState = State.JUMPING;
		}
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

	public void collectCoin() {
		this.playerScore += 50;
	}

}
