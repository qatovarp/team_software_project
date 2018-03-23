package objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.cgeschwendt.game.gameinfo.GameInfo;

import levelone.GenericLevel;
import levelone.LevelOne;

public abstract class Item extends Sprite {
    protected GenericLevel screen;
    protected World world;
    protected boolean toDestroy;
    protected boolean destroyed;
    protected Body body;

    public Item(GenericLevel screen, float x, float y){
        this.screen = screen;
        this.world = screen.getWorld();
        toDestroy = false;
        destroyed = false;

        setPosition(x, y);
        setBounds(getX(), getY(), 70 / GameInfo.PPM, 70 / GameInfo.PPM);
        defineItem();
    }

    public abstract void defineItem();

    public void update(float dt){
        if(toDestroy && !destroyed){
            world.destroyBody(body);
            destroyed = true;
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