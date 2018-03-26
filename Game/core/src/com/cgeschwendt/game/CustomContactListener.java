package com.cgeschwendt.game;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;
import com.cgeschwendt.game.gameinfo.GameInfo;

import objects.Item;

import levelone.GenericLevel;


 
public class CustomContactListener implements ContactListener {
 
	private GenericLevel parent;
	
	public CustomContactListener(GenericLevel parent) {
		this.parent = parent;
	}
 
	@Override
	public void beginContact(Contact contact) {
		Object faData = contact.getFixtureA().getUserData();
		Object fbData = contact.getFixtureB().getUserData();
		
		if(faData != null && fbData != null) {
			if(faData.equals("player") && fbData.equals("gold coin")) {
				((Item)contact.getFixtureB().getBody().getUserData()).destroy();
				parent.getPlayer().setPlayerScore(250);
			}
			else if(fbData.equals("player") && faData.equals("gold coin")) {
				((Item)contact.getFixtureA().getBody().getUserData()).destroy();
				parent.getPlayer().setPlayerScore(250);
			}
			else if(faData.equals("player") && fbData.equals("silver coin")) {
				((Item)contact.getFixtureB().getBody().getUserData()).destroy();
				parent.getPlayer().setPlayerScore(100);
			}
			else if(fbData.equals("player") && faData.equals("silver coin")) {
				((Item)contact.getFixtureA().getBody().getUserData()).destroy();
				parent.getPlayer().setPlayerScore(100);
			}
			else if(faData.equals("player") && fbData.equals("bronze coin")) {
				((Item)contact.getFixtureB().getBody().getUserData()).destroy();
				parent.getPlayer().setPlayerScore(25);
			}
			else if(fbData.equals("player") && faData.equals("bronze coin")) {
				((Item)contact.getFixtureA().getBody().getUserData()).destroy();
				parent.getPlayer().setPlayerScore(25);
			}
			
			
			else if(faData.equals("player") && fbData.equals("blue key")) {
				((Item)contact.getFixtureB().getBody().getUserData()).destroy();
				GameInfo.HASBLUEKEY = true;
			}
			else if(fbData.equals("player") && faData.equals("blue key")) {
				((Item)contact.getFixtureA().getBody().getUserData()).destroy();
				GameInfo.HASBLUEKEY = true;
			}
			else if(faData.equals("player") && fbData.equals("green key")) {
				((Item)contact.getFixtureB().getBody().getUserData()).destroy();
				GameInfo.HASGREENKEY = true;
			}
			else if(fbData.equals("player") && faData.equals("green key")) {
				((Item)contact.getFixtureA().getBody().getUserData()).destroy();
				GameInfo.HASGREENKEY = true;
			}
			
			else if(faData.equals("player") && fbData.equals("green lock") && GameInfo.HASGREENKEY) {
				Array<Fixture> fixture = new Array<Fixture>();
				parent.world.getFixtures(fixture);
				for(Fixture fix: fixture) {
					if(fix.getUserData().equals("green lock"))
						((Item)fix.getBody().getUserData()).destroy();
			}
			}
			else if(fbData.equals("player") && faData.equals("green lock") && GameInfo.HASGREENKEY) {
				Array<Fixture> fixture = new Array<Fixture>();
				parent.world.getFixtures(fixture);
				for(Fixture fix: fixture) {
					if(fix.getUserData().equals("green lock"))
						((Item)fix.getBody().getUserData()).destroy();
				
			}
			}
			
			
			else if(faData.equals("player") && fbData.equals("exit")
				 || fbData.equals("player") && faData.equals("exit")) {
				parent.getPlayer().atLvlExit = true;
			}
			else if(faData.equals("player") && fbData.equals("water")
				 || fbData.equals("player") && faData.equals("water")) {
					parent.getPlayer().fellIntoLiquid = true;
			}
		}
	}
 
	@Override
	public void endContact(Contact contact) {
		Object faData = contact.getFixtureA().getUserData();
		Object fbData = contact.getFixtureB().getUserData();
		
		if(faData != null && fbData != null) {
			if(faData.equals("player") && fbData.equals("LvlExit")
			|| fbData.equals("player") && faData.equals("LvlExit")) {
				parent.getPlayer().atLvlExit = false;
			}
		}	
	}
 
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
	}
 
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {		
	}
 
}