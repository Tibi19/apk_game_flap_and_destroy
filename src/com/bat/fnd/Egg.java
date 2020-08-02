package com.bat.fnd;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.World;

public class Egg extends Misc{
	
	private MassData eggMass;
	
	public Egg(World world, Vector2 playerPos) {
		this.world = world;
		this.eggMass = EggManager.eggMass;
		frame = EggManager.eggTexture;
		fixtureDef = EggManager.eggFD;
		width = EggManager.eggWidth;
		height = EggManager.eggHeight;
		position = new Vector2(playerPos);
		
		initPhysics();
	}

		private void initPhysics() {
			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyType.DynamicBody;
			bodyDef.position.set(position.x, position.y);
			
			body = world.createBody(bodyDef);
			Filter filter = new Filter();
			filter.categoryBits = 64;
			filter.maskBits = 4 | 8;
			body.createFixture(fixtureDef).setUserData(this);
			body.getFixtureList().get(0).setFilterData(filter);
			body.setLinearDamping(0f);
			body.setGravityScale(8f);
			body.setMassData(eggMass);
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
		return false;
	}
	
	@Override
	public void dispose() {
		world.destroyBody(body);
	}

}
