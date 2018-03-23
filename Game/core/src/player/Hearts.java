package player;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cgeschwendt.game.GameMain;
import com.cgeschwendt.game.gameinfo.GameInfo;

public class Hearts {
	private GameMain game;
	private Texture emptyHeart;
	private Texture halfHeart;
	private Texture fullHeart;
	
	private OrthographicCamera mainCamera;

	
	/**
	 * Hearts Constructor creates the Heart textures and camera.
	 * 
	 * @param game : needs to be given the MainGame to create inside of the game.
	 * @author cgeschwendt
	 */
	public Hearts(GameMain game) {
		this.game = game;
		//HEART .PNG files;
		emptyHeart = new Texture("Hearts/hud_heartEmpty.png");
		halfHeart = new Texture("Hearts/hud_heartHalf.png");
		fullHeart = new Texture("Hearts/hud_heartFull.png");
		
		//SCREEN CAMERAS FOR HEARTS
		mainCamera = new OrthographicCamera(GameInfo.WIDTH, GameInfo.HEIGHT);
		mainCamera.position.set(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f, 0);
		
	}

	/**
	 * Updates hearts on the level screen;
	 * @author cgeschwendt
	 */
	public void updateHearts() {
		int lives = game.getplayer().getNumLives();
		SpriteBatch batch = game.getBatch();
		batch.begin();
		if (GameInfo.normal) {
			switch (lives) {
			case 6:
				batch.draw(fullHeart, GameInfo.WIDTH / 2 - 20f, GameInfo.HEIGHT - 90, 40f, 40f);
				batch.draw(fullHeart, GameInfo.WIDTH / 2 - 60f, GameInfo.HEIGHT - 90, 40f, 40f);
				batch.draw(fullHeart, GameInfo.WIDTH / 2 + 20f, GameInfo.HEIGHT - 90, 40f, 40f);
				break;
			case 5:
				batch.draw(fullHeart, GameInfo.WIDTH / 2 - 20f, GameInfo.HEIGHT - 90, 40f, 40f);
				batch.draw(fullHeart, GameInfo.WIDTH / 2 - 60f, GameInfo.HEIGHT - 90, 40f, 40f);
				batch.draw(halfHeart, GameInfo.WIDTH / 2 + 20f, GameInfo.HEIGHT - 90, 40f, 40f);
				break;
			case 4:
				batch.draw(fullHeart, GameInfo.WIDTH / 2 - 20f, GameInfo.HEIGHT - 90, 40f, 40f);
				batch.draw(fullHeart, GameInfo.WIDTH / 2 - 60f, GameInfo.HEIGHT - 90, 40f, 40f);
				batch.draw(emptyHeart, GameInfo.WIDTH / 2 + 20f, GameInfo.HEIGHT - 90, 40f, 40f);
				break;
			case 3:
				batch.draw(halfHeart, GameInfo.WIDTH / 2 - 20f, GameInfo.HEIGHT - 90, 40f, 40f);
				batch.draw(fullHeart, GameInfo.WIDTH / 2 - 60f, GameInfo.HEIGHT - 90, 40f, 40f);
				batch.draw(emptyHeart, GameInfo.WIDTH / 2 + 20f, GameInfo.HEIGHT - 90, 40f, 40f);
				break;
			case 2:
				batch.draw(emptyHeart, GameInfo.WIDTH / 2 - 20f, GameInfo.HEIGHT - 90, 40f, 40f);
				batch.draw(fullHeart, GameInfo.WIDTH / 2 - 60f, GameInfo.HEIGHT - 90, 40f, 40f);
				batch.draw(emptyHeart, GameInfo.WIDTH / 2 + 20f, GameInfo.HEIGHT - 90, 40f, 40f);
				break;
			case 1:
				batch.draw(emptyHeart, GameInfo.WIDTH / 2 - 20f, GameInfo.HEIGHT - 90, 40f, 40f);
				batch.draw(halfHeart, GameInfo.WIDTH / 2 - 60f, GameInfo.HEIGHT - 90, 40f, 40f);
				batch.draw(emptyHeart, GameInfo.WIDTH / 2 + 20f, GameInfo.HEIGHT - 90, 40f, 40f);
				break;
			}
		} else if (GameInfo.difficult) {
			switch (lives) {
			case 4:
				batch.draw(fullHeart, GameInfo.WIDTH / 2 - 40f, GameInfo.HEIGHT - 90, 40f, 40f);
				batch.draw(fullHeart, GameInfo.WIDTH / 2 , GameInfo.HEIGHT - 90, 40f, 40f);
				break;
			case 3:
				batch.draw(fullHeart, GameInfo.WIDTH / 2 - 40f, GameInfo.HEIGHT - 90, 40f, 40f);
				batch.draw(halfHeart, GameInfo.WIDTH / 2 , GameInfo.HEIGHT - 90, 40f, 40f);
				break;
			case 2:
				batch.draw(fullHeart, GameInfo.WIDTH / 2 - 40f, GameInfo.HEIGHT - 90, 40f, 40f);
				batch.draw(emptyHeart, GameInfo.WIDTH / 2 , GameInfo.HEIGHT - 90, 40f, 40f);
				break;
			case 1:
				batch.draw(halfHeart, GameInfo.WIDTH / 2 - 40f, GameInfo.HEIGHT - 90, 40f, 40f);
				batch.draw(emptyHeart, GameInfo.WIDTH / 2 , GameInfo.HEIGHT - 90, 40f, 40f);
				break;
			}
		} else if (GameInfo.extream) {
			switch (lives) {
			case 2:
				batch.draw(fullHeart, GameInfo.WIDTH / 2 - 20f, GameInfo.HEIGHT - 90, 40f, 40f);
				break;
			case 1:
				batch.draw(halfHeart, GameInfo.WIDTH / 2 - 20f, GameInfo.HEIGHT - 90, 40f, 40f);
				break;
			}	
		}
		batch.end();
	}
}
