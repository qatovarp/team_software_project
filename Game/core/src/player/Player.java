package player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.cgeschwendt.game.gameinfo.GameInfo;

public class Player extends Sprite {
	private enum State {
		FALLING, JUMPING, STANDING
	};
	private int lives;
	private State verticleState;

	private Body body;
	private World world;
	


	public Player() {}

	public void playerConstruct(World world, float x, float y) {
		this.world = world;
		BodyDef bdef = new BodyDef();
		bdef.position.set(x / GameInfo.PPM,  y / GameInfo.PPM);
		bdef.type = BodyDef.BodyType.DynamicBody;
		body = world.createBody(bdef);
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();

		shape.setRadius(35f / GameInfo.PPM);
		fdef.friction = 1.5f;
		fdef.shape = shape;
		body.createFixture(fdef);
		verticleState = State.STANDING;

		this.setLifeQuantity();
	}

	/**
	 * moves the players position to the right
	 * 
	 * @author cgeschwendt
	 */
	public void right() {
		if (body.getLinearVelocity().x <= 2&& !Gdx.input.isKeyPressed(Input.Keys.LEFT))
			body.applyLinearImpulse(new Vector2(1.6f, -.9f), body.getWorldCenter(), true);
	}

	/**
	 * moves the players position to the left
	 * 
	 * @author cgeschwendt
	 */
	public void left() {
		if (body.getLinearVelocity().x >= -2 && !Gdx.input.isKeyPressed(Input.Keys.RIGHT))
			body.applyLinearImpulse(new Vector2(-1.6f, -.9f), body.getWorldCenter(), true);
	}

	/**
	 * gives the player the jumping operation only if they have not jumped once
	 * before. ALLOWS FOR ONLY ONE JUMP AT A TIME.
	 * 
	 * @author cgeschwendt
	 */
	public void jump() {
		//if (verticleState == State.JUMPING || verticleState == State.FALLING) {
		//} else {
			body.applyLinearImpulse(new Vector2(0, 4.8f), body.getWorldCenter(), true);
			
		//verticleState = State.JUMPING;
		//}
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

	public Vector2 position() {
		return body.getPosition();
	}

	public void resetPosition(float x, float y) {
		BodyDef bdef = new BodyDef();
		bdef.position.set(x / GameInfo.PPM, y / GameInfo.PPM);
		bdef.type = BodyDef.BodyType.DynamicBody;
		body = world.createBody(bdef);
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();

		shape.setRadius(35f / GameInfo.PPM);
		fdef.friction = 1.5f;
		fdef.shape = shape;
		body.createFixture(fdef);
		verticleState = State.STANDING;

	}

	/**
	 * Sets the life quantity of the player based on the game setting Diffuclty;
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

}
