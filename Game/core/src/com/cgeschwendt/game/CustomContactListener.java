package com.cgeschwendt.game;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

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
			if(faData.equals("player") && fbData.equals("coin")) {
				contact.getFixtureB().setUserData("delete");
				parent.deleteAnObject = true;
				parent.getPlayer().collectCoin();
			}
			else if(fbData.equals("player") && faData.equals("coin")) {
				contact.getFixtureA().setUserData("delete");
				parent.deleteAnObject = true;
				parent.getPlayer().collectCoin();
			}
			else if(faData.equals("player") && fbData.equals("LvlExit")
				 || fbData.equals("player") && faData.equals("LvlExit")) {
				parent.getPlayer().atLvlExit = true;
			}
			else if(faData.equals("player") && fbData.equals("drown")
				 || fbData.equals("player") && faData.equals("drown")) {
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