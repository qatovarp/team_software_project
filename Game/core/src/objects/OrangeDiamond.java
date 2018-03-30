package objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.cgeschwendt.game.gameinfo.GameInfo;

public class OrangeDiamond  extends Item{

	public OrangeDiamond(World world, MapObject object) {
		super(world, object);
		this.setTexture(new Texture("objects/gemOrange.png"));
		this.setRegion(this.getTexture());
	}

	@Override
	public void defineItem() {
		BodyDef bdef = new BodyDef();
		bdef.position.set((getX() + getWidth() / 2f) / GameInfo.PPM, (getY() + getHeight() / 2f) / GameInfo.PPM);
		bdef.type = BodyDef.BodyType.StaticBody;
		body = world.createBody(bdef);
		body.setUserData(this);
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(17f / GameInfo.PPM);

		fdef.shape = shape;
		fdef.isSensor = true;
		
		body.createFixture(fdef).setUserData("orange diamond");

		shape.dispose();
	}

	@Override
	public void update(float dt) {
		super.update(dt);
		setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
	}
}
