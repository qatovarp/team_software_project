package com.cgeschwendt.game;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;

import objects.Item;

public class CustomQueryCallback implements QueryCallback {
	
	private String objName;
	private boolean foundOne;

	public CustomQueryCallback(String objName) {
		super();
		this.objName = objName;
		this.foundOne = false;
	}
	
	@Override
	public boolean reportFixture(Fixture fixture) {
		if(fixture.getUserData() != null) {
			if(fixture.getUserData().equals(this.objName)) {
				((Item)fixture.getBody().getUserData()).destroy();
				foundOne = true;
			}
			else {
				//Remove this deletion doesn't work properly.
				return false;
			}
		}
		return true;
	}
	
	public boolean foundOne() {
		return foundOne;
	}

}
