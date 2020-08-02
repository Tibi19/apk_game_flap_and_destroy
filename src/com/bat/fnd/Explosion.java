package com.bat.fnd;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;

public class Explosion extends Misc{
	
	private Animation animation;
	private float stateTime = 0.00001f;
	private boolean dispose = false;
	
	public Explosion(World world) {
		this.world = world;
		animation = EggManager.explosionAni;
		fixtureDef = EggManager.explosionFD;
		width = EggManager.exploWidth;
		height = EggManager.exploHeight;
		position = EggManager.explosionPos;
		frame = animation.getKeyFrame(0);		
		
		initPhysics();
	}

		private void initPhysics() {
			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyType.DynamicBody;
			bodyDef.position.set(position.x, position.y);
			
			body = world.createBody(bodyDef);
			Filter filter = new Filter();
			filter.categoryBits = 128;
			filter.maskBits = 8;
			body.createFixture(fixtureDef).setUserData(this);
			body.getFixtureList().get(0).setFilterData(filter);
			body.setLinearDamping(0f);
			body.setGravityScale(0f);
			body.setBullet(true);
		}

	@Override
	public void update() {
		position.x = body.getPosition().x;
		position.y = body.getPosition().y;
		
		stateTime += Gdx.graphics.getDeltaTime() * 10;
    	if(stateTime > animation.getKeyFrames().length){
    		dispose = true;
    		stateTime = animation.getKeyFrames().length;
    	}
    	
    	frame = animation.getKeyFrame(stateTime);
	}

	@Override
	public void draw(Batch batch) {
		batch.draw(frame, position.x, position.y, width, height);
	}

	@Override
	public boolean shouldBeDisposed() {
		return dispose;
	}
	
	@Override
	public void dispose() {
		world.destroyBody(body);
	}
}
