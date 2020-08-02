package com.bat.fnd;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class EggManager {

	private static Texture texture;
	public static Animation explosionAni;
	public static TextureRegion eggTexture, eggDownTexture, shadowTexture;
	public static Vector2 explosionPos;
	public static FixtureDef eggFD, explosionFD;
	public static float eggWidth, eggHeight, exploWidth, exploHeight, buttWidth, buttHeight, exploRadius, exploBodyPos, exploBodyH;
	public final static MassData eggMass = new MassData();
	private final static float eggRatio = 30, exploRatio = 3, buttRatio = 24;
	
	public static void Initialize() {
		createEgg();
		createButton();
		createExplosion();
	}

	private static void createExplosion() {
		explosionPos = new Vector2();
		texture = new Texture(Gdx.files.internal("explosion.png"));
		
		TextureRegion[][] tmp = TextureRegion.split(texture, 341, 467);
		TextureRegion[] aux = new TextureRegion[3];
		aux[0] = tmp[0][0];
		aux[1] = tmp[0][1];
		aux[2] = tmp[0][2];
		explosionAni = new Animation(1f, aux);
		
		exploWidth = Util.generateWidth(exploRatio);
		exploHeight = Util.generateHeight(exploWidth, explosionAni.getKeyFrame(0).getRegionWidth(), 
				explosionAni.getKeyFrame(0).getRegionHeight());
		exploBodyPos = exploHeight * 0.1237f;
		exploBodyH = exploHeight * 0.8412f;
		
		float[] vertices = {
				exploWidth*0.0462f, exploHeight*0.8325f,
    			exploWidth*0.0393f, exploHeight*0.565f,
    			exploWidth*0.3475f, exploHeight*0.1275f,
    			exploWidth*0.8508f, exploHeight*0.1237f,
    			exploWidth*0.9809f, exploHeight*0.5162f,
    			exploWidth*0.9141f, exploHeight*0.7349f,
    			exploWidth*0.4176f, exploHeight*0.8412f
    	};
		explosionFD = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		shape.set(vertices);
		explosionFD.shape = shape;
		explosionFD.isSensor = true;
	}
	
	private static void createButton() {
		texture = new Texture(Gdx.files.internal("shadow.png"));
		shadowTexture = new TextureRegion(texture);
		texture = new Texture(Gdx.files.internal("eggDown.png"));
		eggDownTexture = new TextureRegion(texture);
		
		buttWidth = Gdx.graphics.getWidth() / buttRatio;
		buttHeight = Util.generateHeight(buttWidth, eggTexture.getRegionWidth(), eggTexture.getRegionHeight());
	}

	private static void createEgg() {
		eggMass.mass = 0.1f;
		texture = new Texture(Gdx.files.internal("egg.png"));
		eggTexture = new TextureRegion(texture);
		
		eggWidth = Util.generateWidth(eggRatio);
		eggHeight = Util.generateHeight(eggWidth, eggTexture.getRegionWidth(), eggTexture.getRegionHeight());
		
		float[] vertices = {
				eggWidth*0.0292f, eggHeight*0.4849f,
    			eggWidth*0.1495f, eggHeight*0.1499f,
    			eggWidth*0.3334f, eggHeight*0.0425f,
    			eggWidth*0.6909f, eggHeight*0.0562f,
    			eggWidth*0.9573f, eggHeight*0.3237f,
    			eggWidth*0.9298f, eggHeight*0.6975f,
    			eggWidth*0.641f, eggHeight*0.9575f,
    			eggWidth*0.3471f, eggHeight*0.955f
    	};
		eggFD = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		shape.set(vertices);
		eggFD.shape = shape;
	}
	
	public static void dispose() {
		explosionAni.getKeyFrame(0).getTexture().dispose();
		eggTexture.getTexture().dispose();
		eggDownTexture.getTexture().dispose();
		shadowTexture.getTexture().dispose();
		explosionAni = null;
		eggTexture = null;
		eggDownTexture = null;
		shadowTexture = null;
		texture = null;
		explosionPos = null;
		eggFD = null;
		explosionFD = null;
	}
}
