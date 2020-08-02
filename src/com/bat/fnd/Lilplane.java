package com.bat.fnd;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Lilplane extends Misc {
	
	private float force;
	private boolean flipped;
	private final int index = MobileBlockManager.LILPLANE;
	private FixtureDef secondFD;
	
	public Lilplane(World world) {
		this.world = world;
		flipped = Util.random.nextBoolean();
		if (flipped) 
		{
			frame = MobileBlockManager.lilplaneTextureFlipped;
			fixtureDef = MobileBlockManager.lilplaneFixtureDefFlipped;
			secondFD = MobileBlockManager.lilplaneSecondFixtureDefFlipped;
		}
		else 
		{
			frame = MobileBlockManager.textures[index];
			fixtureDef = MobileBlockManager.fixtureDefs[index];
			secondFD = MobileBlockManager.lilplaneSecondFixtureDef;
		}
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
			body.getFixtureList().get(0).setFilterData(filter);
			body.getFixtureList().get(1).setFilterData(filter);
			if (flipped) body.setLinearVelocity(force, 0);
			else body.setLinearVelocity(-force, 0);
		}

		private void initPosition() {
			position = new Vector2();
			if (flipped) position.x = 0 - width;
			else position.x = Util.mapWidth;
			position.y = Util.height/2 + Util.random.nextInt((int)(Util.mapHeight - Util.height/2 - height));
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
		return position.x > Util.mapWidth || position.x + width < 0;
	}
	
	@Override
	public void dispose() {
		world.destroyBody(body);
		MobileBlockManager.mobilesNr--;
	}
}
