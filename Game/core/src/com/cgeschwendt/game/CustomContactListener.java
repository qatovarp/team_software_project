package com.cgeschwendt.game;

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
			player.setPlayerScore(250);
		}
		else if(contactBetween(contact, "player", "silver coin")) {
			getItem(contact).destroy();
			player.setPlayerScore(100);
		}
		else if(contactBetween(contact, "player", "bronze coin")) {
			getItem(contact).destroy();
			player.setPlayerScore(25);
		}
		else if(contactBetween(contact, "player", "blue key")) {
			getItem(contact).destroy();
			GameInfo.HASBLUEKEY = true;
		}
		else if(contactBetween(contact, "player", "green key")) {
			getItem(contact).destroy();
			GameInfo.HASGREENKEY = true;
		}
		else if(contactBetween(contact, "player", "green lock") && GameInfo.HASGREENKEY) {
			destroyAll("green lock");
		}
		else if(contactBetween(contact, "player", "exit")) {
			parent.getPlayer().atLvlExit = true;
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
		
		if(contactBetween(contact, "player", "LvlExit")) {
			player.atLvlExit = false;
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
	}
 
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
	}
 
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {		
	}
 
}