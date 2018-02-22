package com.cgeschwendt.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import Mainmenu.MainMenu;


public class GameMain extends Game {
	// the one and only SpriteBatch for the game;
	SpriteBatch batch;
	
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new MainMenu(this));
	}

	@Override
	public void render () {
	super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
	public SpriteBatch getBatch() {
		return this.batch;
	}
}
