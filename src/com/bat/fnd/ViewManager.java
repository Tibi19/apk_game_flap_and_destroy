package com.bat.fnd;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class ViewManager {
	
	private static OrthographicCamera cam;
	private static Background bg;
	private static final float startX = 363.414f, startY = 150f;
	
	public static void initialize() {
		cam = new OrthographicCamera(Util.width, Util.height);
		bg = new Background();
	}
	
	public static OrthographicCamera getCam() {
		return cam;
	}
	
	public static Background getBg() {
		return bg;
	}
	
	public static void dispose() {
		bg.dispose();
		bg = null;
		cam = null;
	}

	public static float getStartx() {
		return startX;
	}

	public static float getStarty() {
		return startY;
	}
}
