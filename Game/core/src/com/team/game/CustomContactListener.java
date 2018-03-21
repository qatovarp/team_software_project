package com.team.game;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.team.entities.living.player.Player.State;
import com.team.levels.BaseLevel;
 
public class CustomContactListener implements ContactListener {
 
	private BaseLevel parent;
	
	public CustomContactListener(BaseLevel parent) {
		this.parent = parent;
	}
 
	@Override
	public void beginContact(Contact contact) {
		Object faData = contact.getFixtureA().getBody().getUserData();
		Object fbData = contact.getFixtureB().getBody().getUserData();
		
		if(faData != null && fbData != null) {
			if(faData.equals("player") && fbData.equals("coin")) {
				contact.getFixtureB().getBody().setUserData("delete");
				parent.deleteAnObject = true;
			}
			else if(fbData.equals("player") && faData.equals("coin")) {
				contact.getFixtureA().getBody().setUserData("delete");
				parent.deleteAnObject = true;
			}
			else if(faData.equals("player") && fbData.equals("LvlExit")
				 || fbData.equals("player") && faData.equals("LvlExit")) {
				parent.getPlayer().atLvlExit = true;
			}
		}
	}
 
	@Override
	public void endContact(Contact contact) {
		Object faData = contact.getFixtureA().getBody().getUserData();
		Object fbData = contact.getFixtureB().getBody().getUserData();
		
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