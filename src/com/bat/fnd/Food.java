package com.bat.fnd;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;

public class Food extends Misc{
	
	private int value;
	private float force;
	private boolean dispose = false;
	
	public Food(World world, int index) {
		this.world = world;
		frame = FoodManager.textures[index];
		value = FoodManager.values[index];
		force = FoodManager.forces[index];
		fixtureDef = FoodManager.fixtureDefs[index];
		width = FoodManager.widths[index];
		height = FoodManager.heights[index];
		
		initPosition();
		initPhysics();
	}
	
		private void initPhysics() {
			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyType.KinematicBody;
			bodyDef.position.set(position.x, position.y);
			
			body = world.createBody(bodyDef);
			Filter filter = new Filter();
			filter.categoryBits = 16;
			filter.maskBits = 2;
			body.createFixture(fixtureDef).setUserData(this);
			body.getFixtureList().get(0).setFilterData(filter);
			body.setLinearVelocity(0f, -force);
		}

		private void initPosition() {
			position = new Vector2();
			position.x = (float) (Earl.camX - Util.width/2 + Util.random.nextInt((int)Util.width));
			position.y = Earl.camY + Util.height;
		}

	@Override
	public void update() {
		position.x = body.getPosition().x;
		position.y = body.getPosition().y;
	}

	@Override
	public void draw(Batch batch) {
		batch.draw(frame, position.x, position.y, width, height);
	}
	
	public void deactivate(){
		dispose = true;
	}

	@Override
	public boolean shouldBeDisposed() {
		return dispose;
	}
	
	@Override
	public void dispose() {
		world.destroyBody(body);
	}

	public int getValue() {
		return value;
	}

}
