package com.bat.fnd;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Meteorite extends Misc {
	
	private float force;
	private boolean flipped;
	private float stateTime = 0.00001f;
	private int sign = 1;
	private Animation animation;
	
	public Meteorite(World world, float ratio, float force) {
		this.world = world;
		flipped = Util.random.nextBoolean();
		if (flipped) animation = MobileBlockManager.meteoriteAnimationFlipped;
		else animation = MobileBlockManager.meteoriteAnimation;
		width = Util.generateWidth(ratio);
		height = Util.generateHeight(width, animation.getKeyFrame(0).getRegionWidth(), animation.getKeyFrame(0).getRegionHeight());
		frame = animation.getKeyFrame(0);
		this.force = force;
				
		initPosition();
		initFixtureDef();
		initPhysics();
	}
	
		private void initFixtureDef() {
			if(flipped)
			{
				float[] verticesFlipped = {
						width*0.9909f, height*0.9774f,
		    			width*0.4838f, height*0.2974f,
		    			width*0.3612f, height*0.0875f,
		    			width*0.2022f, height*0.0262f,
		    			width*0.0206f, height*0.1362f,
		    			width*0.1387f, height*0.3962f,
		    			width*0.2991f, height*0.4562f,
		    			width*0.9682f, height*0.9849f
		    	};
				
				fixtureDef = new FixtureDef();
		    	PolygonShape shape = new PolygonShape();
		    	shape.set(verticesFlipped);
		    	fixtureDef.shape = shape;
			}
			else
			{
				float[] vertices = {
		    			width*0.009f, height*0.9774f,
		    			width*0.5161f, height*0.2974f,
		    			width*0.6387f, height*0.0875f,
		    			width*0.7977f, height*0.0262f,
		    			width*0.9793f, height*0.1362f,
		    			width*0.8612f, height*0.3962f,
		    			width*0.7008f, height*0.4562f,
		    			width*0.0317f, height*0.9849f
		    	};
				
				fixtureDef = new FixtureDef();
		    	PolygonShape shape = new PolygonShape();
		    	shape.set(vertices);
		    	fixtureDef.shape = shape;
			}
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
			body.getFixtureList().get(0).setFilterData(filter);
			if (flipped) body.setLinearVelocity(-force, -force);
			else body.setLinearVelocity(force, -force);
		}

		private void initPosition() {
			position = new Vector2();
			if (flipped) position.x = (float) Util.random.nextInt((int)(Util.mapWidth / 2)) + Util.mapWidth/2;
			else position.x = (float) Util.random.nextInt((int)(Util.mapWidth / 2));
			position.y = Util.mapHeight;
		}

	@Override
	public void update() {
		position.x = body.getPosition().x;
		position.y = body.getPosition().y;
		
		stateTime += sign * Gdx.graphics.getDeltaTime() * 20;
    	if(stateTime > animation.getKeyFrames().length){
    		sign = -sign;
    		stateTime = animation.getKeyFrames().length;
    	} else if (stateTime < 0){
    		sign = -sign;
    		stateTime = 0;
    	}
    	
    	frame = animation.getKeyFrame(stateTime);
	}

	@Override
	public void draw(Batch batch) {
		batch.draw(frame, position.x, position.y, width, height);
	}

	@Override
	public boolean shouldBeDisposed() {
		return position.y + height < 0;
	}
	
	@Override
	public void dispose() {
		world.destroyBody(body);
		MobileBlockManager.mobilesNr--;
	}

}
