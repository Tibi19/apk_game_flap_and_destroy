package com.bat.fnd;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class FoodManager {

	private static Texture texture;
	public static TextureRegion[] textures;
	public static FixtureDef[] fixtureDefs;
	public static float[] widths;
	public static float[] heights;
	private final static float[] ratios = { 40, 30, 20 };
	public final static float[] forces = { 5, 10, 20 };
	public final static int[] values = { 3, 5, 7 };
	public final static int BREAD = 0, DONUT = 1, NYFC = 2; // New York Fried Chicken
	
	public static void initialize(){
		textures = new TextureRegion[3];
		fixtureDefs = new FixtureDef[3];
		widths = new float[3];
		heights = new float[3];
		createBread();
		createDonut();
		createNYFC();
	}

	private static void createNYFC() {
		texture = new Texture(Gdx.files.internal("nyfc.png"));
		textures[NYFC] = new TextureRegion(texture);
		
		widths[NYFC] = Util.generateWidth(ratios[NYFC]);
		heights[NYFC] = Util.generateHeight(widths[NYFC], textures[NYFC].getRegionWidth(), textures[NYFC].getRegionHeight());
		
		float[] vertices = {
    			widths[NYFC]*0.0476f, heights[NYFC]*0.9037f,
    			widths[NYFC]*0.4537f, heights[NYFC]*0.0374f,
    			widths[NYFC]*0.5891f, heights[NYFC]*0.0425f,
    			widths[NYFC]*0.9435f, heights[NYFC]*0.9137f
    	};
		fixtureDefs[NYFC] = new FixtureDef();
    	PolygonShape shape = new PolygonShape();
    	shape.set(vertices);
    	fixtureDefs[NYFC].shape = shape;
    	fixtureDefs[NYFC].isSensor = true;
	}

	private static void createDonut() {
		texture = new Texture(Gdx.files.internal("donut.png"));
		textures[DONUT] = new TextureRegion(texture);
		
		widths[DONUT] = Util.generateWidth(ratios[DONUT]);
		heights[DONUT] = Util.generateHeight(widths[DONUT], textures[DONUT].getRegionWidth(), textures[DONUT].getRegionHeight());
		
		float[] vertices = {
    			widths[DONUT]*0.0143f, heights[DONUT]*0.4662f,
    			widths[DONUT]*0.2632f, heights[DONUT]*0.07f,
    			widths[DONUT]*0.6459f, heights[DONUT]*0.0462f,
    			widths[DONUT]*0.981f, heights[DONUT]*0.3862f,
    			widths[DONUT]*0.9011f, heights[DONUT]*0.7237f,
    			widths[DONUT]*0.5022f, heights[DONUT]*0.9799f,
    			widths[DONUT]*0.3189f, heights[DONUT]*0.9575f,
    			widths[DONUT]*0.0368f, heights[DONUT]*0.705f
    	};
		fixtureDefs[DONUT] = new FixtureDef();
    	PolygonShape shape = new PolygonShape();
    	shape.set(vertices);
    	fixtureDefs[DONUT].shape = shape;
    	fixtureDefs[DONUT].isSensor = true;
	}

	private static void createBread() {
		texture = new Texture(Gdx.files.internal("bread.png"));
		textures[BREAD] = new TextureRegion(texture);
		
		widths[BREAD] = Util.generateWidth(ratios[BREAD]);
		heights[BREAD] = Util.generateHeight(widths[BREAD], textures[BREAD].getRegionWidth(), textures[BREAD].getRegionHeight());
		
		float[] vertices = {
    			widths[BREAD]*0.1439f, heights[BREAD]*0.0187f,
    			widths[BREAD]*0.8113f, heights[BREAD]*0.0737f,
    			widths[BREAD]*0.9631f, heights[BREAD]*0.1437f,
    			widths[BREAD]*0.9356f, heights[BREAD]*0.8112f,
    			widths[BREAD]*0.7786f, heights[BREAD]*0.9375f,
    			widths[BREAD]*0.526f, heights[BREAD]*0.9699f,
    			widths[BREAD]*0.0379f, heights[BREAD]*0.7525f
    	};
		
		fixtureDefs[BREAD] = new FixtureDef();
    	PolygonShape shape = new PolygonShape();
    	shape.set(vertices);
    	fixtureDefs[BREAD].shape = shape;
    	fixtureDefs[BREAD].isSensor = true;
	}
	
	public static void dispose() {
		for(int i = 0; i <= NYFC; i++) textures[i].getTexture().dispose();
		textures = null;
		texture = null;
		fixtureDefs = null;
	}
}
