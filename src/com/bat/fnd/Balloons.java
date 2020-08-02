package com.bat.fnd;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Balloons extends Misc {
	
	private float force;
	private final int index = MobileBlockManager.BALLOONS;
	private FixtureDef secondFD, thirdFD;
	
	public Balloons(World world) {
		this.world = world;
		frame = MobileBlockManager.textures[index];
		fixtureDef = MobileBlockManager.fixtureDefs[index];
		secondFD = MobileBlockManager.balloonsSecondFixtureDef;
		thirdFD = MobileBlockManager.balloonsThirdFixtureDef;
		width = MobileBlockManager.widths[index];
		height = MobileBlockManager.heights[index];
		force = MobileBlockManager.forces[index];
				
		initPosition();
		initPhysics();
	}

		private void initPhysics() {
			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyType.KinematicBody;
			bodyDef.position.set(position.x, position.y);
			
			body = world.createBody(bodyDef);
			Filter filter = new Filter();
			filter.categoryBits = 32;
			filter.maskBits = 2;
			body.createFixture(fixtureDef).setUserData(this);
			body.createFixture(secondFD).setUserData(this);
			body.createFixture(thirdFD).setUserData(this);
			body.getFixtureList().get(0).setFilterData(filter);
			body.getFixtureList().get(1).setFilterData(filter);
			body.getFixtureList().get(2).setFilterData(filter);
			body.setLinearVelocity(0, force);
		}

		private void initPosition() {
			position = new Vector2();
			position.x = Util.random.nextInt((int)(Util.mapWidth - width));
			position.y = 0 - height;
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

	@Override
	public boolean shouldBeDisposed() {
		return position.y > Util.mapHeight;
	}
	
	@Override
	public void dispose() {
		world.destroyBody(body);
		MobileBlockManager.mobilesNr--;
	}
}
