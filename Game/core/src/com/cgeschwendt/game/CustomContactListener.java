package com.cgeschwendt.game;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
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
			if(faData.equals("player") && fbData.equals("coin")) {
				((Item)contact.getFixtureB().getBody().getUserData()).destroy();
				parent.getPlayer().collectCoin();
			}
			else if(fbData.equals("player") && faData.equals("coin")) {
				((Item)contact.getFixtureA().getBody().getUserData()).destroy();
				parent.getPlayer().collectCoin();
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