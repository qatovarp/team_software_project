package player;

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
	private enum State {FALLING, JUMPING, STANDING};
	private int lives;
	
	private State verticleState;
	
	private World world;
	private Body body;


	public Player(World world) {
		this.world = world;
		BodyDef bdef = new BodyDef();
		bdef.position.set(100f / GameInfo.PPM, 350f / GameInfo.PPM);
		bdef.type = BodyDef.BodyType.DynamicBody;
		body = world.createBody(bdef);
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();

		shape.setRadius(25f / GameInfo.PPM);
		fdef.friction =1.5f;
		fdef.shape = shape;
		body.createFixture(fdef);
		verticleState = State.STANDING;
		
		this.setLifeQuantity();
	}

	/**
	 * moves the players position to the right
	 * @author cgeschwendt
	 */
	public void right() {
		if (body.getLinearVelocity().x <= 2)
			body.applyLinearImpulse(new Vector2(1.6f, -.9f), body.getWorldCenter(), true);
	}
	/**
	 * moves the players position to the left
	 * @author cgeschwendt
	 */
	public void left() {
		if (body.getLinearVelocity().x >= -2)
			body.applyLinearImpulse(new Vector2(-1.6f, -.9f), body.getWorldCenter(), true);
	}
	/**
	 * gives the player the jumping operation only if they have not jumped once before.
	 * ALLOWS FOR ONLY ONE JUMP AT A TIME.
	 * @author cgeschwendt
	 */
	public void jump() {
		if (verticleState == State.JUMPING || verticleState == State.FALLING) {
		}else{
			body.applyLinearImpulse(new Vector2(0, 3f), body.getWorldCenter(), true);
			verticleState = State.JUMPING;
		}
	}
	

	
	/**
	 * Sets the state of the player to either falling, standing or jumping.
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

	
	public void resetPosition() {
		BodyDef bdef = new BodyDef();
		bdef.position.set(100f / GameInfo.PPM, 350f / GameInfo.PPM);
		bdef.type = BodyDef.BodyType.DynamicBody;
		body = world.createBody(bdef);
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();

		shape.setRadius(25f / GameInfo.PPM);
		
		fdef.friction= 1.5f;
		fdef.shape = shape;
		body.createFixture(fdef);
	}
	
	void setLifeQuantity() {
		if(GameInfo.normal)
			this.lives = 6;
		else if(GameInfo.difficult)
			this.lives = 4;
		else if(GameInfo.extream)
			this.lives = 2;
	}
	
	public void playerLoseLife() {
		this.lives = this.lives-1;
	}
	
	public boolean playerDead() {
		if(this.lives > 0)
			return false;
		else
			return true;
	}
}
