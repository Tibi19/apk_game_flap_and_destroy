package com.bat.fnd;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Earl{

	static public float camX, camY;
	
	private Vector2 position, jumpRight, jumpLeft, jump;
	private Animation animation;
    private Texture texture;
    private TextureRegion[] frames;
    private TextureRegion currentFrame;
    
    private Filter activeFilter, inactiveFilter;
    
    private final int textureWidth = 254, textureHeight = 256;
    private float stateTime = 0.00001f;
    private final float ratio = 16,
    		earlWidth = Util.generateWidth(ratio),
    		earlHeight = Util.generateHeight(earlWidth, textureWidth, textureHeight);
    private final float[] vertices = { 
    		earlWidth*0.0093f, earlHeight*0.76f,
    		earlWidth*0.0916f, earlHeight*0.6825f,
    		earlWidth*0.0605f, earlHeight*0.3525f,
    		earlWidth*0.1257f, earlHeight*0.2537f,
    		earlWidth*0.6832f, earlHeight*0.2375f,
    		earlWidth*0.32607f, earlHeight*0.6725f,
    		earlWidth*0.2857f, earlHeight*0.8524f,
    		earlWidth*0.1304f, earlHeight*0.905f
    	};
    private final float[] verticesFlipped = {
    		earlWidth*0.9906f, earlHeight*0.76f,
    		earlWidth*0.9083f, earlHeight*0.6825f,
    		earlWidth*0.9394f, earlHeight*0.3525f,
    		earlWidth*0.8742f, earlHeight*0.2537f,
    		earlWidth*0.3167f, earlHeight*0.2375f,
    		earlWidth*0.6739f, earlHeight*0.6725f,
    		earlWidth*0.7142f, earlHeight*0.8524f,
    		earlWidth*0.8695f, earlHeight*0.905f
    	};
    private int sign = 1, fixtureIndex = 1;
    public boolean dead = false;
    public boolean op = false;
    
    Body body;
    
    public Earl(World world){
    	initGraphicsPosition();
    	initPlayerPhysics(world);
    	initMapPhysics(world);
    }

		private void initMapPhysics(World world) {
			 BodyDef bodyDef = new BodyDef();
			 bodyDef.type = BodyType.StaticBody;
			 bodyDef.position.set(0, 0 + earlHeight*0.12f);
			 
			 Filter filter = new Filter();
			 filter.categoryBits = 4;
			 filter.maskBits = 2 | 64;
			 
			 EdgeShape shape = new EdgeShape();
			 shape.set(0, 0, 242.276f*3, 0);
			 
			 Body edgeBodyDown = world.createBody(bodyDef);
			 edgeBodyDown.createFixture(shape, 0).setFilterData(filter);
			 
			 Body edgeBodyLeft = world.createBody(bodyDef);
			 shape.set(0, 0, 0, 340);
			 edgeBodyLeft.createFixture(shape, 0).setFilterData(filter);
			 
			 bodyDef.position.set(242.276f * 3, 0);
			 Body edgeBodyRight = world.createBody(bodyDef);
			 edgeBodyRight.createFixture(shape, 0).setFilterData(filter);
			 
			 bodyDef.position.set(0, 340f);
			 Body edgeBodyUp = world.createBody(bodyDef);
			 shape.set(0, 0, 242.276f*3, 0);
			 edgeBodyUp.createFixture(shape, 0).setFilterData(filter);
			 
			 shape.dispose();
		}

		private void initPlayerPhysics(World world) {
			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyType.DynamicBody;
			bodyDef.position.set(position.x, position.y);
			
			activeFilter = new Filter();
			inactiveFilter = new Filter();
			activeFilter.categoryBits = 2;
			activeFilter.maskBits = 4 | 8 | 16 | 32 | 64;
			inactiveFilter.categoryBits = 0;
			
			PolygonShape shape = new PolygonShape();
			shape.set(vertices);
			
			MassData earlMass = new MassData();
			earlMass.mass = 0.1f;
			
			body = world.createBody(bodyDef);
			
			FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.shape = shape;
			fixtureDef.density = 10f;
			body.createFixture(fixtureDef).setFilterData(activeFilter);
			shape.set(verticesFlipped);
			fixtureDef.shape = shape;
			body.createFixture(fixtureDef).setFilterData(inactiveFilter);
			body.setLinearDamping(0f);
			body.setGravityScale(8f);
			body.setMassData(earlMass);
			
			jumpRight = new Vector2(150, 550);
			jumpLeft = new Vector2(-150, 550);
			jump = new Vector2(0, 1000);
			
			shape.dispose();
		}
	
		private void initGraphicsPosition() {
	    	texture = Util.earl;
	    	
	    	frames = new TextureRegion[4];
	    	TextureRegion[][] tmp = TextureRegion.split(texture, textureWidth, textureHeight);
	    	frames[0] = tmp[0][0];
	    	frames[1] = tmp[0][1];
	    	frames[2] = tmp[0][2]; 
	    	frames[3] = tmp[0][3];
	    	
	    	animation = new Animation(1f, frames);
	    	currentFrame = animation.getKeyFrame(0);
	    	
	    	camX = 363.414f;
	    	camY = 150f;
	    	position = new Vector2(camX, camY);
		}

	public void update(){
		updatePosition();
		updateGraphics();
    }
	
		private void updateGraphics() {
			stateTime += sign * Gdx.graphics.getDeltaTime() * 15;
	    	if(stateTime > 4){
	    		sign = -sign;
	    		stateTime = 4;
	    	} else if (stateTime < 0){
	    		sign = -sign;
	    		stateTime = 0;
	    	}
	    	
	    	currentFrame = animation.getKeyFrame(stateTime);
		}

		private void updatePosition() {
			position.x = body.getPosition().x;
			position.y = body.getPosition().y;
			
			if (position.x + earlWidth/2 <= Util.width/2) camX = Util.width/2;
			else if (position.x + earlWidth/2 >= Util.mapWidth - Util.width/2) camX = Util.mapWidth - Util.width/2;
			else camX = position.x + earlWidth/2;
			
			if (position.y + earlHeight/2 <= Util.height/2) camY = Util.height/2;
			else if (position.y + earlHeight/2 >= Util.mapHeight - Util.height/2) camY = Util.mapHeight - Util.height/2;
			else camY = position.y + earlHeight/2;
		}

	public void draw(Batch batch){
		batch.draw(currentFrame, position.x, position.y, earlWidth, earlHeight);
    }
	
	public void flip(){
		frames[0].flip(true, false);
		frames[1].flip(true, false);
		frames[2].flip(true, false);
		frames[3].flip(true, false);
		body.getFixtureList().get(fixtureIndex).setFilterData(activeFilter);
		fixtureIndex += (int) Math.signum(Math.tan((double) 1 + fixtureIndex));
		body.getFixtureList().get(fixtureIndex).setFilterData(inactiveFilter);
	}

	
	public void dispose(){
		
	}
    
	public Vector2 getPosition() {
		return position;
	}
	
	public float getCurrentFrameWidth() {
		return earlWidth;
	}
	
	public float getCurrentFrameHeight() {
		return earlHeight;
	}

	public Vector2 getJumpRight() {
		return jumpRight;
	}

	public Vector2 getJumpLeft() {
		return jumpLeft;
	}

	public Vector2 getJump() {
		return jump;
	}

	public TextureRegion getCurrentFrame() {
		return currentFrame;
	}

	public float getHeight() {
		return earlHeight;
	}
	
}
