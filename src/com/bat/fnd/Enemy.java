package com.bat.fnd;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Enemy {
	
	Body body;
	private Vector2 position;
	private Animation animation;
	private FixtureDef fixtureDef;
	private TextureRegion currentFrame;
	private float stateTime = 0.00001f, enemyWidth, enemyHeight, force, bodyPos, bodyHeight;
	private int sign = 1, value, frameSpeed, index;

	public Enemy(int index, World world, boolean isFlipped){
		
		this.index = index;
		value = EnemyManager.values[index];
		bodyPos = EnemyManager.bodyPos[index];
		bodyHeight = EnemyManager.bodyHeight[index];
		enemyWidth = EnemyManager.widths[index];
		enemyHeight = EnemyManager.heights[index];
		frameSpeed = EnemyManager.frSpeeds[index];
		initPositionAnimationForce(isFlipped);
		currentFrame = animation.getKeyFrame(0);
		initEnemyPhysics(world);
		
	}
	
	public Enemy(int index, World world){
		
		this.index = index;
		value = EnemyManager.values[index];
		bodyPos = EnemyManager.bodyPos[index];
		bodyHeight = EnemyManager.bodyHeight[index];
		enemyWidth = EnemyManager.widths[index];
		enemyHeight = EnemyManager.heights[index];
		frameSpeed = EnemyManager.frSpeeds[index];
		initPositionAnimationForce();
		currentFrame = animation.getKeyFrame(0);
		initEnemyPhysics(world);
		
	}
	
		private void initPositionAnimationForce(boolean isFlipped) {
			position = new Vector2();
			if (isFlipped && (Earl.camX + Util.width/2 + enemyWidth*2 < Util.mapWidth)){
				
				position.x = (float) Earl.camX + Util.width/2 + enemyWidth + Util.random.nextInt((int) 
						(Util.mapWidth - Earl.camX - Util.width/2));
				animation = EnemyManager.animations[index];
				fixtureDef = EnemyManager.fixtureDefs[index];
				if (EnemyManager.forces[index] < 100) force = -EnemyManager.forces[index];
				else force = (float) -Util.random.nextInt(30);
				
			} else if (Earl.camX - Util.width/2 > enemyWidth*2){
				
				position.x = (float) Util.random.nextInt((int) (Earl.camX - Util.width/2 - enemyWidth));
				animation = EnemyManager.animationsFlipped[index];
				fixtureDef = EnemyManager.fixtureDefsFlipped[index];
				if (EnemyManager.forces[index] < 100) force = EnemyManager.forces[index];
				else force = (float) Util.random.nextInt(30);
				
			} else {
				
				position.x = (float) Earl.camX + Util.width/2 + enemyWidth + Util.random.nextInt((int) 
						(Util.mapWidth - Earl.camX - Util.width/2));
				animation = EnemyManager.animations[index];
				fixtureDef = EnemyManager.fixtureDefs[index];
				if (EnemyManager.forces[index] < 100) force = -EnemyManager.forces[index];
				else force = (float) -Util.random.nextInt(30);
				
			}
			
			position.y = Util.generateYenemy(enemyHeight);
			//System.out.println(position.x);
		}

		private void initPositionAnimationForce() {
			position = new Vector2();
			if (Util.random.nextBoolean()) {
				
				position.x = (float) Earl.camX + Util.width/2 + enemyWidth + Util.random.nextInt((int) 
						(Util.mapWidth - enemyWidth*2 - Earl.camX - Util.width/2));
				animation = EnemyManager.animations[index];
				fixtureDef = EnemyManager.fixtureDefs[index];
				if (EnemyManager.forces[index] < 100) force = -EnemyManager.forces[index];
				else force = (float) -Util.random.nextInt(30);
				
			}else{
				
				position.x = (float) Util.random.nextInt((int) (Earl.camX - Util.width/2 - enemyWidth + 1));
				animation = EnemyManager.animationsFlipped[index];
				fixtureDef = EnemyManager.fixtureDefsFlipped[index];
				if (EnemyManager.forces[index] < 100) force = EnemyManager.forces[index];
				else force = (float) Util.random.nextInt(30);
				
			}
			
			position.y = Util.generateYenemy(enemyHeight);
		}

		public void initEnemyPhysics(World world) {
			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyType.KinematicBody;
			bodyDef.position.set(position.x, position.y);
			
			body = world.createBody(bodyDef);
			Filter filter = new Filter();
			filter.categoryBits = 8;
			filter.maskBits = 2 | 64 | 128;
			body.createFixture(fixtureDef).setUserData(this);
			body.getFixtureList().get(0).setFilterData(filter);
			body.setLinearVelocity(force, 0f);
		}
	
	public void update(){
		position.x = body.getPosition().x;
		position.y = body.getPosition().y;
		
		stateTime += sign * Gdx.graphics.getDeltaTime() * frameSpeed;
    	if(stateTime > animation.getKeyFrames().length){
    		sign = -sign;
    		stateTime = animation.getKeyFrames().length;
    	} else if (stateTime < 0){
    		sign = -sign;
    		stateTime = 0;
    	}
    	
    	currentFrame = animation.getKeyFrame(stateTime);
	}
	
	public void deactivate(){
		sign = 0;
		stateTime = 0;
		animation = Util.animationGone;
	}
	
	public boolean shouldBeDead(){
		return position.x > Util.mapWidth || position.x + enemyWidth < 0 || position.y + enemyHeight < 0;
	}
	
	public void draw(Batch batch){
		batch.draw(currentFrame, position.x, position.y, enemyWidth, enemyHeight);
	}
	
	public void dispose(World world){
		world.destroyBody(body);
	}
	
	public Vector2 getPosition() {
		return position;
	}

	public float getWidth() {
		return enemyWidth;
	}

	public float getHeight() {
		return enemyHeight;
	}

	public float getBodyPos() {
		return bodyPos;
	}

	public float getBodyHeight() {
		return bodyHeight;
	}

	public int getValue() {
		return value;
	}

	public int getIndex() {
		return index;
	}
}

/*
		private void initPositionAnimationForce(boolean isFlipped, Animation animation, Animation animationFlipped, 
				FixtureDef fixtureDef, FixtureDef fixtureDefFlipped, float force, OrthographicCamera cam, Utility.random Utility.random) {
			position = new Vector2();
			if (isFlipped && (cam.position.x + width/2 + enemyWidth*2 < 727.828)){
				position.x = (float) cam.position.x + width/2 + enemyWidth + Utility.random.nextInt((int) 
						(727.828 - enemyWidth*2 - cam.position.x - width/2));
				this.animation = animation;
				this.fixtureDef = fixtureDef;
				if (force < 100) this.force = -force;
				else this.force = (float) -Utility.random.nextInt(30);
			} else if (cam.position.x - width/2 > enemyWidth){
				position.x = (float) Utility.random.nextInt((int) (cam.position.x - width/2 - enemyWidth + 2));
				this.animation = animationFlipped;
				this.fixtureDef = fixtureDefFlipped;
				if (force < 100) this.force = force;
				else this.force = (float) Utility.random.nextInt(30);
			} else {
				position.x = (float) cam.position.x + width/2 + enemyWidth + Utility.random.nextInt((int) 
						(727.828 - enemyWidth*2 - cam.position.x - width/2));
				this.animation = animation;
				this.fixtureDef = fixtureDef;
				if (force < 100) this.force = -force;
				else this.force = (float) -Utility.random.nextInt(30);
			}
			position.y = (float) Utility.random.nextInt(299 - (int)(enemyHeight*2)) + enemyHeight;
			System.out.println(position.x);
		}
*/
