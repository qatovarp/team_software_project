package layers;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.cgeschwendt.game.gameinfo.GameInfo;
public class ground {
private World world;

	public ground(World world, TiledMap map) {
		this.world = world;
		
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		Body body;
		
		
		for(MapObject object : map.getLayers().get(3).getObjects()){

	        Shape shape;
	        if (object instanceof RectangleMapObject) {
	            shape = getRectangle((RectangleMapObject)object);
	        }
	        else if (object instanceof PolygonMapObject) {
	            shape = getPolygon((PolygonMapObject)object);
	        }
	        else if (object instanceof PolylineMapObject) {
	            shape = getPolyline((PolylineMapObject)object);
	        }
	        else if (object instanceof CircleMapObject) {
	            shape = getCircle((EllipseMapObject)object);
	        }
	        else {
	            continue;
	        }
	            bdef.type = BodyDef.BodyType.StaticBody;
	            body = world.createBody(bdef);
	            fdef.shape = shape;
	            body.createFixture(fdef);
	        }

	}
	
	
	
	private static PolygonShape getRectangle(RectangleMapObject rectangleObject) {
        Rectangle rectangle = rectangleObject.getRectangle();
        PolygonShape polygon = new PolygonShape();
        Vector2 size = new Vector2((rectangle.x + rectangle.width * 0.5f) / GameInfo.PPM,
                (rectangle.y + rectangle.height * 0.5f ) / GameInfo.PPM);
        polygon.setAsBox(rectangle.width * 0.5f /GameInfo.PPM,
                rectangle.height * 0.5f / GameInfo.PPM,
                size,
                0.0f);
        return polygon;
    }

    private static CircleShape getCircle(EllipseMapObject circleObject) {
    		 Ellipse circle = circleObject.getEllipse();
         CircleShape circleShape = new CircleShape();
         circleShape.setRadius(circle.width / 2 / GameInfo.PPM);
         circleShape.setPosition(new Vector2((circle.x + circle.width/2 )/ GameInfo.PPM, (circle.y+circle.height/2 )/ GameInfo.PPM));
         return circleShape;
    }

    private static PolygonShape getPolygon(PolygonMapObject polygonObject) {
        PolygonShape polygon = new PolygonShape();
        float[] vertices = polygonObject.getPolygon().getTransformedVertices();

        float[] worldVertices = new float[vertices.length];

        for (int i = 0; i < vertices.length; ++i) {
            worldVertices[i] = vertices[i] / GameInfo.PPM;
        }

        polygon.set(worldVertices);
        return polygon;
    }

    private static ChainShape getPolyline(PolylineMapObject polylineObject) {
        float[] vertices = polylineObject.getPolyline().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < vertices.length / 2; ++i) {
            worldVertices[i] = new Vector2();
            worldVertices[i].x = vertices[i * 2] / GameInfo.PPM;
            worldVertices[i].y = vertices[i * 2 + 1] / GameInfo.PPM;
        }

        ChainShape chain = new ChainShape();
        chain.createChain(worldVertices);
        return chain;
    }
	
}



