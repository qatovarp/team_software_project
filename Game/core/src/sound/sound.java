package sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.cgeschwendt.game.GameMain;


/**
 * 
 * @author TokyoJosh
 * 
 * 	handles all sound effects and music 
 *
 */
public class sound {
	
	//private GameMain game;
	private Music music=null;
	private String song;
	private Sound playersound;
	private String effect;
	private long soundid;
	private boolean muted=true;
	
	/**
	 * not sure if i'm going to need this
	 * 
	 */
	public sound() {
		
		//this.game = game;
		//song = "music/music1.mp3";
		//music = Gdx.audio.newMusic(Gdx.files.internal("music/music1.mp3"));
		//music.setVolume(.75f);
		//music.play();
	}
	
	/**
	 * switches songs in the game
	 * @param path : the path to the song 
	 * 
	 * 
	 */
	public void switchSong(String path){
		if(muted) {
			if(music==null) {
				song=path;
				music = Gdx.audio.newMusic(Gdx.files.internal(path));
				//music.setVolume(.0f);
				music.play();
			}else if(path==song) {
				//do nothing
			}else {
				song = path;
				music.stop();
				music.dispose();
				music = null;
				music = Gdx.audio.newMusic(Gdx.files.internal(path));
				//music.setVolume(.0f);
				music.play();
			}
		}
	}
	
	/**
	 * 
	 * @param eff : path to sound file
	 * @param islooping : sets to looping// not sure if going to use
	 */
	public void playereffect(String eff,boolean islooping) {
		playersound = Gdx.audio.newSound(Gdx.files.internal(eff));
		soundid = playersound.play();
		if(islooping)
			playersound.setLooping(soundid,true);
		
	}
	
	/**
	 * stop currently playing effect
	 */
	public void stopplayereffect() {
		playersound.stop(soundid);
	}
	
	
	/**
	 * set volume for music 
	 * @param f : float of volume ie .50f
	 */
	public void setVolume(float f) {
		music.setVolume(f);		
	}
	

	
	
	public void dispose() {
		music.dispose();
	
	}
}
