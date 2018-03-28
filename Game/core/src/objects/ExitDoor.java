package objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.cgeschwendt.game.gameinfo.GameInfo;

public class ExitDoor extends Item {

	public ExitDoor(World world, MapObject object) {
        super(world, object);
		this.setTexture(new Texture("objects/door_closed.png"));
		this.setRegion(this.getTexture());
		this.setBounds(getX(), getY(), 70 / GameInfo.PPM, 110 / GameInfo.PPM);

		
     
    }

    @Override
    public void defineItem() {
        BodyDef bdef = new BodyDef();
        bdef.position.set((getX() / GameInfo.PPM) + (getWidth() / 2f), (getY() / GameInfo.PPM) + (getHeight() / 2f));
        bdef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bdef);
        body.setUserData(this);
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
		shape.setAsBox(this.getWidth()/2.0f, this.getHeight()/2.0f);
		fdef.isSensor = true;
        fdef.shape = shape;
        body.createFixture(fdef).setUserData("exit");
		shape.dispose();
	
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2 +20/GameInfo.PPM);
    }

}
