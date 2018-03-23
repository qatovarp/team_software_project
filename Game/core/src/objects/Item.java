package objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.cgeschwendt.game.gameinfo.GameInfo;

import levelone.GenericLevel;
import levelone.LevelOne;

public abstract class Item extends Sprite {
    protected World world;
    protected boolean toDestroy;
    protected boolean destroyed;
    protected Body body;

    public Item(World world, MapObject object){
        this.world = world;
        toDestroy = false;
        destroyed = false;
        
        setPosition( object.getProperties().get("x", float.class), object.getProperties().get("y", float.class));
        setBounds(getX(), getY(), 70 / GameInfo.PPM, 70 / GameInfo.PPM);
        defineItem();
    }

    public abstract void defineItem();

    public void update(float dt){
    	if(body.getPosition().y < -10) {
            toDestroy = true;
    	}
        if(toDestroy && !destroyed && !world.isLocked()){
            world.destroyBody(body);
            destroyed = true;
            //Debug code
            //System.out.println("Destroyed "+this.getClass().getName());
        }        
    }

    public void draw(Batch batch){
        if(!destroyed)
            super.draw(batch);
    }

    public void destroy(){
        toDestroy = true;
    }
}