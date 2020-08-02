package com.bat.fnd;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class MobileBlockManager {
	
	private static Texture texture;
	public static Animation meteoriteAnimation, meteoriteAnimationFlipped;
	public static TextureRegion[] textures;
	public static FixtureDef[] fixtureDefs;
	public static TextureRegion lilplaneTextureFlipped;
	public static FixtureDef lilplaneSecondFixtureDef, lilplaneSecondFixtureDefFlipped, lilplaneFixtureDefFlipped, 
					 balloonsSecondFixtureDef, balloonsThirdFixtureDef;
	public static float[] widths;
	public static float[] heights;
	private final static float[] ratios = { 15, 15, 5 };
	public final static float[] forces = { 5, 15, 15 };
	public final static int BALLOONS = 0, NEWSPAPER = 1, LILPLANE = 2; //BIGOPLANE = 3;
	public static int mobilesNr = 3;
	
	public static void initialize(){
		textures = new TextureRegion[3];
		fixtureDefs = new FixtureDef[3];
		widths = new float[3];
		heights = new float[3];
		createMeteorite();
		createBalloons();
		createNewspaper();
		createLilplane();
		//createBigoplane();
	}

	/*private static void createBigoplane() {
		texture = new Texture(Gdx.files.internal("lilplane.png"));
		textures[BIGOPLANE] = new TextureRegion(texture);
		bigoplaneTextureFlipped = new TextureRegion(texture);
		bigoplaneTextureFlipped.flip(true, false);
		
		//the method to get the height and width for bigoplane is inverse because the ratio is meant for the screen's height, not width
		heights[BIGOPLANE] = Util.generateWidth(ratios[BIGOPLANE]);
		widths[BIGOPLANE] = Util.generateHeight(heights[BIGOPLANE], textures[BIGOPLANE].getRegionHeight(), 
				textures[BIGOPLANE].getRegionWidth());
		
		float[] vertices = {
				widths[BIGOPLANE]*0.015f, heights[BIGOPLANE]*0.1628f,
    			widths[BIGOPLANE]*0.2433f, heights[BIGOPLANE]*0.0764f,
    			widths[BIGOPLANE]*0.7041f, heights[BIGOPLANE]*0.2708f,
    			widths[BIGOPLANE]*0.9791f, heights[BIGOPLANE]*0.3383f,
    			widths[BIGOPLANE]*0.2116f, heights[BIGOPLANE]*0.4383f,
    			widths[BIGOPLANE]*0.095f, heights[BIGOPLANE]*0.3708f,
    			widths[BIGOPLANE]*0.02f, heights[BIGOPLANE]*0.2357f
    	};
		float[] verticesFlipped = {
				widths[BIGOPLANE]*0.985f, heights[BIGOPLANE]*0.1628f,
    			widths[BIGOPLANE]*0.7566f, heights[BIGOPLANE]*0.0764f,
    			widths[BIGOPLANE]*0.2958f, heights[BIGOPLANE]*0.2708f,
    			widths[BIGOPLANE]*0.0208f, heights[BIGOPLANE]*0.3383f,
    			widths[BIGOPLANE]*0.7883f, heights[BIGOPLANE]*0.4383f,
    			widths[BIGOPLANE]*0.905f, heights[BIGOPLANE]*0.3708f,
    			widths[BIGOPLANE]*0.98f, heights[BIGOPLANE]*0.2357f
    	};
		fixtureDefs[BIGOPLANE] = new FixtureDef();
		bigoplaneFixtureDefFlipped = new FixtureDef();
    	PolygonShape shape = new PolygonShape();
    	PolygonShape shapeFlipped = new PolygonShape();
    	shape.set(vertices);
    	shapeFlipped.set(verticesFlipped);
    	fixtureDefs[BIGOPLANE].shape = shape;
    	bigoplaneFixtureDefFlipped.shape = shapeFlipped;
	}*/

	private static void createLilplane() {
		texture = new Texture(Gdx.files.internal("lilplane.png"));
		textures[LILPLANE] = new TextureRegion(texture);
		lilplaneTextureFlipped = new TextureRegion(texture);
		lilplaneTextureFlipped.flip(true, false);
		
		widths[LILPLANE] = Util.generateWidth(ratios[LILPLANE]);
		heights[LILPLANE] = Util.generateHeight(widths[LILPLANE], textures[LILPLANE].getRegionWidth(), 
				textures[LILPLANE].getRegionHeight());
		
		float[] vertices = {
				widths[LILPLANE]*0.015f, heights[LILPLANE]*0.1628f,
    			widths[LILPLANE]*0.2433f, heights[LILPLANE]*0.0764f,
    			widths[LILPLANE]*0.7041f, heights[LILPLANE]*0.2708f,
    			widths[LILPLANE]*0.9791f, heights[LILPLANE]*0.3383f,
    			widths[LILPLANE]*0.2116f, heights[LILPLANE]*0.4383f,
    			widths[LILPLANE]*0.095f, heights[LILPLANE]*0.3708f,
    			widths[LILPLANE]*0.02f, heights[LILPLANE]*0.2357f
    	};
		float[] verticesSecond = {
				widths[LILPLANE]*0.1625f, heights[LILPLANE]*0.3437f,
    			widths[LILPLANE]*0.3041f, heights[LILPLANE]*0.5868f,
    			widths[LILPLANE]*0.4008f, heights[LILPLANE]*0.5949f,
    			widths[LILPLANE]*0.5291f, heights[LILPLANE]*0.4464f,
    			widths[LILPLANE]*0.8783f, heights[LILPLANE]*0.3843f,
    			widths[LILPLANE]*0.9358f, heights[LILPLANE]*0.7434f,
    			widths[LILPLANE]*0.9566f, heights[LILPLANE]*0.765f,
    			widths[LILPLANE]*0.9583f, heights[LILPLANE]*0.3437f
    	};
		float[] verticesFlipped = {
				widths[LILPLANE]*0.985f, heights[LILPLANE]*0.1628f,
    			widths[LILPLANE]*0.7566f, heights[LILPLANE]*0.0764f,
    			widths[LILPLANE]*0.2958f, heights[LILPLANE]*0.2708f,
    			widths[LILPLANE]*0.0208f, heights[LILPLANE]*0.3383f,
    			widths[LILPLANE]*0.7883f, heights[LILPLANE]*0.4383f,
    			widths[LILPLANE]*0.905f, heights[LILPLANE]*0.3708f,
    			widths[LILPLANE]*0.98f, heights[LILPLANE]*0.2357f
    	};
		float[] verticesSecondFlipped = {
				widths[LILPLANE]*0.8375f, heights[LILPLANE]*0.3437f,
    			widths[LILPLANE]*0.6958f, heights[LILPLANE]*0.5868f,
    			widths[LILPLANE]*0.5991f, heights[LILPLANE]*0.5949f,
    			widths[LILPLANE]*0.4708f, heights[LILPLANE]*0.4464f,
    			widths[LILPLANE]*0.1216f, heights[LILPLANE]*0.3843f,
    			widths[LILPLANE]*0.0641f, heights[LILPLANE]*0.7434f,
    			widths[LILPLANE]*0.0433f, heights[LILPLANE]*0.765f,
    			widths[LILPLANE]*0.0416f, heights[LILPLANE]*0.3437f
    	};
		fixtureDefs[LILPLANE] = new FixtureDef();
		lilplaneSecondFixtureDef = new FixtureDef();
		lilplaneFixtureDefFlipped = new FixtureDef();
		lilplaneSecondFixtureDefFlipped = new FixtureDef();
    	PolygonShape shape = new PolygonShape();
    	PolygonShape shapeSecond = new PolygonShape();
    	PolygonShape shapeFlipped = new PolygonShape();
    	PolygonShape shapeSecondFlipped = new PolygonShape();
    	shape.set(vertices);
    	shapeSecond.set(verticesSecond);
    	shapeFlipped.set(verticesFlipped);
    	shapeSecondFlipped.set(verticesSecondFlipped);
    	fixtureDefs[LILPLANE].shape = shape;
    	lilplaneSecondFixtureDef.shape = shapeSecond;
    	lilplaneFixtureDefFlipped.shape = shapeFlipped;
    	lilplaneSecondFixtureDefFlipped.shape = shapeSecondFlipped;
	}

	private static void createNewspaper() {
		texture = new Texture(Gdx.files.internal("balloons.png"));
		textures[NEWSPAPER] = new TextureRegion(texture);
		
		widths[NEWSPAPER] = Util.generateWidth(ratios[NEWSPAPER]);
		heights[NEWSPAPER] = Util.generateHeight(widths[NEWSPAPER], textures[NEWSPAPER].getRegionWidth(), 
				textures[NEWSPAPER].getRegionHeight());
		
		float[] vertices = {
				widths[NEWSPAPER]*0.5417f, heights[NEWSPAPER]*0.01f,
    			widths[NEWSPAPER]*0.8715f, heights[NEWSPAPER]*0.93125f,
    			widths[NEWSPAPER]*0.2103f, heights[NEWSPAPER]*0.935f,
    			widths[NEWSPAPER]*0.2031f, heights[NEWSPAPER]*0.75f,
    			widths[NEWSPAPER]*0.0338f, heights[NEWSPAPER]*0.6637f,
    			widths[NEWSPAPER]*0.2798f, heights[NEWSPAPER]*0.4412f
    	};
		fixtureDefs[NEWSPAPER] = new FixtureDef();
    	PolygonShape shape = new PolygonShape();
    	shape.set(vertices);
    	fixtureDefs[NEWSPAPER].shape = shape;
	}

	private static void createBalloons() {
		texture = new Texture(Gdx.files.internal("balloons.png"));
		textures[BALLOONS] = new TextureRegion(texture);
		
		widths[BALLOONS] = Util.generateWidth(ratios[BALLOONS]);
		heights[BALLOONS] = Util.generateHeight(widths[BALLOONS], textures[BALLOONS].getRegionWidth(), 
				textures[BALLOONS].getRegionHeight());
		
		float[] vertices = {
				widths[BALLOONS]*0.5382f, heights[BALLOONS]*0.0112f,
    			widths[BALLOONS]*0.7093f, heights[BALLOONS]*0.4325f,
    			widths[BALLOONS]*0.8358f, heights[BALLOONS]*0.4637f,
    			widths[BALLOONS]*0.9f, heights[BALLOONS]*0.55375f,
    			widths[BALLOONS]*0.8982f, heights[BALLOONS]*0.6574f,
    			widths[BALLOONS]*0.957f, heights[BALLOONS]*0.7387f,
    			widths[BALLOONS]*0.9481f, heights[BALLOONS]*0.865f,
    			widths[BALLOONS]*0.8305f, heights[BALLOONS]*0.94f
    	};
		float[] verticesSecond = {
				widths[BALLOONS]*0.8287f, heights[BALLOONS]*0.94f,
    			widths[BALLOONS]*0.7235f, heights[BALLOONS]*0.9099f,
    			widths[BALLOONS]*0.6451f, heights[BALLOONS]*0.9625f,
    			widths[BALLOONS]*0.5489f, heights[BALLOONS]*0.9587f,
    			widths[BALLOONS]*0.4794f, heights[BALLOONS]*0.9137f,
    			widths[BALLOONS]*0.3938f, heights[BALLOONS]*0.9812f,
    			widths[BALLOONS]*0.2655f, heights[BALLOONS]*0.9787f,
    			widths[BALLOONS]*0.5364f, heights[BALLOONS]*0.01f
    	};
		float[] verticesThird = {
				widths[BALLOONS]*0.2655f, heights[BALLOONS]*0.9774f,
    			widths[BALLOONS]*0.1924f, heights[BALLOONS]*0.9f,
    			widths[BALLOONS]*0.2067f, heights[BALLOONS]*0.745f,
    			widths[BALLOONS]*0.0802f, heights[BALLOONS]*0.7249f,
    			widths[BALLOONS]*0.0374f, heights[BALLOONS]*0.5862f,
    			widths[BALLOONS]*0.1211f, heights[BALLOONS]*0.4712f,
    			widths[BALLOONS]*0.2815f, heights[BALLOONS]*0.4425f,
    			widths[BALLOONS]*0.5382f, heights[BALLOONS]*0.01f
    	};
		fixtureDefs[BALLOONS] = new FixtureDef();
		balloonsSecondFixtureDef = new FixtureDef();
		balloonsThirdFixtureDef = new FixtureDef();
    	PolygonShape shape = new PolygonShape();
    	PolygonShape shapeSecond = new PolygonShape();
    	PolygonShape shapeThird = new PolygonShape();
    	shape.set(vertices);
    	shapeSecond.set(verticesSecond);
    	shapeThird.set(verticesThird);
    	fixtureDefs[BALLOONS].shape = shape;
    	balloonsSecondFixtureDef.shape = shapeSecond;
    	balloonsThirdFixtureDef.shape = shapeThird;
	}

	private static void createMeteorite() {
		texture = new Texture(Gdx.files.internal("meteorite.png"));
		
		TextureRegion[][] tmp = TextureRegion.split(texture, 512, 620);
		TextureRegion[] aux = new TextureRegion[2];
		aux[0] = tmp[0][0];
		aux[1] = tmp[0][1];
		
		tmp = TextureRegion.split(texture, 512, 620);
		TextureRegion[] aux2 = new TextureRegion[2];
		aux2[0] = tmp[0][0];
		aux2[0].flip(true, false);
		aux2[1] = tmp[0][1];
		aux2[1].flip(true, false);
		
		meteoriteAnimation = new Animation(1f, aux);
    	meteoriteAnimationFlipped = new Animation(1f, aux2);
	}
	
	public static void dispose() {
		meteoriteAnimation.getKeyFrame(0).getTexture().dispose();
		meteoriteAnimationFlipped.getKeyFrame(0).getTexture().dispose();
		for(int i = 0; i <= LILPLANE; i++) textures[i].getTexture().dispose();
		fixtureDefs = null;
		lilplaneTextureFlipped = null;
		lilplaneSecondFixtureDef = null;
		lilplaneSecondFixtureDefFlipped = null;
		lilplaneFixtureDefFlipped = null;
		balloonsSecondFixtureDef = null;
		balloonsThirdFixtureDef = null;
	}
}
