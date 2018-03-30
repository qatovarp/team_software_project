package com.cgeschwendt.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
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
		if(contact.getFixtureA().getUserData().equals(a) && contact.getFixtureB().getUserData().equals(b)
		|| contact.getFixtureA().getUserData().equals(b) && contact.getFixtureB().getUserData().equals(a)) {
			return true;
		}
		else {
			return false;
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
		if(contact.getFixtureA().getUserData().equals("player")) {
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
			this.playSound("collectcoin.wav");
			getItem(contact).destroy();
			player.setPlayerScore(250);
		}
		else if(contactBetween(contact, "player", "silver coin")) {
			this.playSound("collectcoin.wav");
			getItem(contact).destroy();
			player.setPlayerScore(100);
		}
		else if(contactBetween(contact, "player", "bronze coin")) {
			this.playSound("collectcoin.wav");
			getItem(contact).destroy();
			player.setPlayerScore(25);
		}
		else if(contactBetween(contact, "player", "blue key")) {
			this.playSound("collectkey.wav");
			getItem(contact).destroy();
			GameInfo.HASBLUEKEY = true;
		}
		else if(contactBetween(contact, "player", "green key")) {
			this.playSound("collectkey.wav");
			getItem(contact).destroy();
			GameInfo.HASGREENKEY = true;
		}
		else if(contactBetween(contact, "player", "orange key")) {
			this.playSound("collectkey.wav");
			getItem(contact).destroy();
			GameInfo.HASORANGEKEY = true;
		}
		else if(contactBetween(contact, "player", "yellow key")) {
			this.playSound("collectkey.wav");
			getItem(contact).destroy();
			GameInfo.HASYELLOWKEY = true;
		}
		else if(contactBetween(contact, "player", "green lock") && GameInfo.HASGREENKEY) {
			//destroyAll("green lock");
			getItem(contact).destroy();
		}
		else if(contactBetween(contact, "player", "blue lock") && GameInfo.HASBLUEKEY) {
			//destroyAll("blue lock");
			getItem(contact).destroy();
		}
		else if(contactBetween(contact, "player", "yellow lock") && GameInfo.HASYELLOWKEY) {
			//destroyAll("yellow lock");
			getItem(contact).destroy();
		}
		else if(contactBetween(contact, "player", "orange lock") && GameInfo.HASORANGEKEY) {
			//destroyAll("orange lock");
			  getItem(contact).destroy();
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
		
		else if(contactBetween(contact, "player", "exit")) {
			GameInfo.atLvlExit = true;
		 }
	
		else if(contactBetween(contact,"player","spike")) {
			player.playerLoseLife();
			player.spikeHurt();
		}
		else if(contactBetween(contact, "player", "blue diamond")) {
			this.playSound("collect diamond.wav");
			getItem(contact).destroy();
			player.setPlayerScore(2500);
			GameInfo.HASBLUEGEM = true;
		}
		else if(contactBetween(contact, "player", "green diamond")) {
			this.playSound("collect diamond.wav");
			getItem(contact).destroy();
			player.setPlayerScore(2500);
			GameInfo.HASGREENGEM = true;
		}
		else if(contactBetween(contact, "player", "orange diamond")) {
			this.playSound("collect diamond.wav");
			getItem(contact).destroy();
			player.setPlayerScore(2500);
			GameInfo.HASORANGEGEM = true;
		}
		else if(contactBetween(contact, "player", "yellow diamond")) {
			this.playSound("collect diamond.wav");
			getItem(contact).destroy();
			player.setPlayerScore(2500);
			GameInfo.HASYELLOWGEM = true;
		}
		else if(contactBetween(contact, "player", "spring")) {
			parent.getPlayer().spikeHurt();
			
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
		else if(contactBetween(contact,"player","spike")) {
		
		}
	}
 
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
	}
 
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {		
	}
	
	private void playSound(String s) {
		if(GameInfo.sound) {
			Sound coin =Gdx.audio.newSound(Gdx.files.internal("music/"+s));	
			long id2 = coin.play();
			coin.setVolume(id2, .07f);
		}
	}
 
}