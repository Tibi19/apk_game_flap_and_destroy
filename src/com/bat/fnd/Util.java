package com.bat.fnd;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Filter;

public class Util {
	
	public static final float width = 170.66665f, height = 100f, mapWidth = 242.276f*3f, mapHeight = height*3f;
	public static final Random random = new Random();
	public static final Filter voidFilter = new Filter();
	private static Rectangle A = new Rectangle(), B = new Rectangle(), CAM = new Rectangle();
	public static TextureRegion textureGone;
	public static Texture earl;
	public static Animation animationGone;
	public static boolean initialized = false, disposable = false;
	
	public static void initialize(){
		Texture texture = new Texture(Gdx.files.internal("gone.png"));
		textureGone = new TextureRegion(texture);
		earl = new Texture(Gdx.files.internal("earl.png"));
		animationGone = new Animation(1f, textureGone);
		CAM.setSize(width, height);
		initialized = true;
		disposable = true;
	}
	
	public static float generateRadius (float width, float height) {
		return Math.max(width, height) / 2;
	}
	
	public static float generateWidth (float ratio) {
		return width / ratio;
	}
	
	public static float generateHeight (float width, int textureWidth, int textureHeight) {
		return width * ((float) textureHeight / textureWidth);
	}
	
	//generates a Y coordinate without touching the ground or the top
	//intended for enemies
	public static float generateYenemy (float obHeight) {
		return random.nextInt((int)(mapHeight - obHeight*2)) + obHeight;
	}
	
	public static boolean overlapsCamera(float x, float y, float obWidth, float obHeight) {
		CAM.setPosition(Earl.camX - width/2, Earl.camY - height/2);
		B.set(x, y, obWidth, obHeight);
		return CAM.overlaps(B);
	}
	
	public static void setRectangle(float x, float y, float obWidth, float obHeight) {
		A.set(x, y, obWidth, obHeight);
	}
	
	public static boolean overlapsPreSet(float x, float y, float obWidth, float obHeight) {
		B.set(x, y, obWidth, obHeight);
		return A.overlaps(B);
	}
	
	public static void dispose() {
		A = null;
		B = null;
		CAM = null;
		textureGone.getTexture().dispose();
		animationGone.getKeyFrame(0).getTexture().dispose();
		textureGone = null;
		animationGone = null;
		earl.dispose();
	}
}
