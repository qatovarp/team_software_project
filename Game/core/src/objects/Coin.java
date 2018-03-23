package objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.cgeschwendt.game.gameinfo.GameInfo;

import levelone.GenericLevel;


public class Coin extends Item {

	public Texture goldCoin;
	
    public Coin(GenericLevel screen, float x, float y) {
        super(screen, x, y);
		goldCoin = new Texture("objects/coinGold.png");
		this.setRegion(this.goldCoin);
    }

    @Override
    public void defineItem() {
        BodyDef bdef = new BodyDef();
        bdef.position.set((getX()+getWidth()/2f)/ GameInfo.PPM, (getY()+getHeight()/2f)/ GameInfo.PPM);
        bdef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bdef);
        body.setUserData(this);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(17f/GameInfo.PPM);
        
        fdef.shape = shape;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("coin");
		shape.dispose();
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    }
}