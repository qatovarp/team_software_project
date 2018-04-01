package com.cgeschwendt.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.cgeschwendt.game.gameinfo.GameInfo;

import objects.Item;
import player.Player;
import levelone.GenericLevel;


 
public class CustomContactListener implements ContactListener {
 
	private GenericLevel parent;
	
	public CustomContactListener(GenericLevel parent) {
		this.parent = parent;
	}
	
	/**
	 * Check if a contact is between two specific objects
	 * 
	 * @param contact - the contact
	 * @param a - the name of the first object (assigned in Tiled)
	 * @param b - the name of the second object (assigned in Tiled)
	 * @return true if the contact is between the named objects
	 */
	private boolean contactBetween(Contact contact, String a, String b) {
		if(a.equals("player")) {
			if(((String)contact.getFixtureA().getUserData()).split("_")[0].equals(a) && contact.getFixtureB().getUserData().equals(b)
			|| contact.getFixtureA().getUserData().equals(b) && ((String)contact.getFixtureB().getUserData()).split("_")[0].equals(a)) {
				return true;
			}
			else {
				return false;
			}
		}
		else
		{
			if(contact.getFixtureA().getUserData().equals(a) && contact.getFixtureB().getUserData().equals(b)
			|| contact.getFixtureA().getUserData().equals(b) && contact.getFixtureB().getUserData().equals(a)) {
				return true;
			}
			else {
				return false;
			}
		}
	}
	
	/**
	 * Get the Item Obj from a contact.
	 * 
	 * THIS ASSUMES THE OTHER CONTACT IS THE PLAYER
	 * AND THAT THIS IS AN ITEM.
	 * 
	 * @param contact - the contact
	 * @return the item from a contact bewteen an item and the player
	 */
	private Item getItem(Contact contact) {
		if(((String)contact.getFixtureA().getUserData()).split("_")[0].equals("player")) {
			return ((Item)contact.getFixtureB().getBody().getUserData());
		}
		else {
			return ((Item)contact.getFixtureA().getBody().getUserData());
		}
	}
	
	/**
	 * Check if user data exist to prevent null errors
	 * 
	 * @param contact - contact
	 * @return true if user data exist for BOTH fixtures
	 */
	private boolean dataExist(Contact contact) {
		if(contact.getFixtureA().getUserData() != null && contact.getFixtureB().getUserData() != null) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Destroys all Items with fixture user data set as objName
	 * 
	 * @param objName - name of the item to destroy
	 */
	private void destroyAll(String objName) {
		Array<Fixture> fixture = new Array<Fixture>();
		parent.world.getFixtures(fixture);
		for(Fixture fix: fixture) {
			if(fix.getUserData().equals(objName))
				((Item)fix.getBody().getUserData()).destroy();
		}
	}
 
	@Override
	public void beginContact(Contact contact) {
		
		if(!dataExist(contact)) {
			return;
		}

		Player player = parent.getPlayer();
		
		if(contactBetween(contact, "player", "gold coin")) {
			getItem(contact).destroy();
			parent.getPlayer().setPlayerScore(250);
			this.playSound("collectcoin.wav");
		}
		else if(contactBetween(contact, "player", "silver coin")) {
			getItem(contact).destroy();
			parent.getPlayer().setPlayerScore(100);
			this.playSound("collectcoin.wav");
		}
		else if(contactBetween(contact, "player", "bronze coin")) {
			getItem(contact).destroy();
			parent.getPlayer().setPlayerScore(25);
			this.playSound("collectcoin.wav");
		}
		else if(contactBetween(contact, "player", "blue key")) {
			getItem(contact).destroy();
			GameInfo.HASBLUEKEY = true;
			this.playSound("collectkey.wav");
		}
		else if(contactBetween(contact, "player", "green key")) {
			getItem(contact).destroy();
			GameInfo.HASGREENKEY = true;
			this.playSound("collectkey.wav");
		}	
		else if(contactBetween(contact, "player", "yellow key")) {
			getItem(contact).destroy();
			GameInfo.HASYELLOWKEY = true;
			this.playSound("collectkey.wav");
		}
		else if(contactBetween(contact, "player", "orange key")) {
			getItem(contact).destroy();
			GameInfo.HASORANGEKEY = true;
			this.playSound("collectkey.wav");
		}
		else if(contactBetween(contact, "player", "green lock") && GameInfo.HASGREENKEY) {
			getItem(contact).destroy();
		}
		else if(contactBetween(contact, "player", "yellow lock") && GameInfo.HASYELLOWKEY) {
			getItem(contact).destroy();
		}
		else if(contactBetween(contact, "player", "blue lock") && GameInfo.HASBLUEKEY) {
			getItem(contact).destroy();
		}
		else if(contactBetween(contact, "player", "orange lock") && GameInfo.HASORANGEKEY) {
			getItem(contact).destroy();
		}
		else if(contactBetween(contact, "player", "yellow diamond")) {
			getItem(contact).destroy();
			parent.getPlayer().setPlayerScore(2500);
			GameInfo.HASYELLOWGEM = true;
			this.playSound("collect diamond.wav");
		}
		else if(contactBetween(contact, "player", "green diamond")) {
			getItem(contact).destroy();
			parent.getPlayer().setPlayerScore(2500);
			GameInfo.HASGREENGEM = true;
			this.playSound("collect diamond.wav");
		}
		else if(contactBetween(contact, "player", "orange diamond")) {
			getItem(contact).destroy();
			parent.getPlayer().setPlayerScore(2500);
			GameInfo.HASORANGEGEM = true;
			this.playSound("collect diamond.wav");
		}
		else if(contactBetween(contact, "player", "blue diamond")) {
			getItem(contact).destroy();
			parent.getPlayer().setPlayerScore(2500);
			GameInfo.HASBLUEGEM = true;
			this.playSound("collect diamond.wav");
		}
		else if(contactBetween(contact, "player", "exit")) {
			GameInfo.atLvlExit = true;
		} 
		else if(contactBetween(contact, "player", "spike")) {
			parent.getPlayer().spikeHurt();
			parent.getPlayer().playerLoseLife();
		} 
		else if(contactBetween(contact, "player", "water")) {
			parent.getPlayer().fellIntoLiquid = true;
			Timer.schedule(new Task(){
				@Override
				public void run() {
					parent.inWaterCheck();
				}
			}, 0.7f);
		}
		else if(contactBetween(contact, "player_head_right", "wall")) {
			player.headHitWall = true;
			player.hittingWallRight = true;
		}

		else if(contactBetween(contact, "player", "spring")) {
			this.getItem(contact).setTexture(new Texture("objects/springBoardUp.png"));
			player.springJump();
		}

		else if(contactBetween(contact, "player_head_left", "wall")) {
			player.headHitWall = true;
			player.hittingWallLeft = true;
		}
		
	}
 
	@Override
	public void endContact(Contact contact) {
		
		if(!dataExist(contact)) {
			return;
		}

		Player player = parent.getPlayer();
		
		if(contactBetween(contact, "player", "exit")) {
			GameInfo.atLvlExit = false;
		}
		else if(contactBetween(contact, "player_head_right", "wall")) {
			player.headHitWall = false;
			Timer.schedule(new Task(){
				@Override
				public void run() {
					parent.getPlayer().hittingWallRight = false;
				}
			}, 0.08f);
		}
		else if(contactBetween(contact, "player_head_left", "wall")) {
			player.headHitWall = false;
			Timer.schedule(new Task(){
				@Override
				public void run() {
					parent.getPlayer().hittingWallLeft = false;
				}
			}, 0.08f);
		}
		
		else if(contactBetween(contact, "player", "spring")) {
			this.getItem(contact).setTexture(new Texture("objects/springBoardDown.png"));		
		}
	}
	
	private void playSound (String s) {
		Sound X =  Gdx.audio.newSound(Gdx.files.internal("music/" +s));	
		if(GameInfo.sound) {
			long id = X.play();
			X.setVolume(id, .14f);
		}
	}
 
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
	}
 
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {		
	}
 
}