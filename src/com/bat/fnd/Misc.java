package com.bat.fnd;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Misc {

	protected Vector2 position;
	protected TextureRegion frame;
	protected float width, height;
	
	protected World world;
	protected Body body;
	protected FixtureDef fixtureDef;

	public abstract void update();
	public abstract void draw(Batch batch);
	public abstract void dispose();
	public abstract boolean shouldBeDisposed();
	
	
	public Vector2 getPosition() {
		return position;
	}
	public Body getBody() {
		return body;
	}
	
	
}
