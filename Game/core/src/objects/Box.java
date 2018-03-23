package objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.cgeschwendt.game.gameinfo.GameInfo;

import levelone.GenericLevel;


public class Box extends Item {

	public Texture texture;
	
    public Box(GenericLevel screen, float x, float y) {
        super(screen, x, y);
		texture = new Texture("objects/boxAlt.png");
		this.setRegion(this.texture);
    }

    @Override
    public void defineItem() {
        BodyDef bdef = new BodyDef();
        bdef.position.set((getX()+getWidth()/2f)/ GameInfo.PPM, (getY()+getHeight()/2f)/ GameInfo.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);
        body.setUserData(this);
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
		shape.setAsBox(this.getWidth()/2.0f, this.getHeight()/2.0f);
        
        fdef.shape = shape;
        body.createFixture(fdef).setUserData("box");
		shape.dispose();
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    }
}