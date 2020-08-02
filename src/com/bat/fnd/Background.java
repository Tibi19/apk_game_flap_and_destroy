package com.bat.fnd;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Background {
	
	private Texture bg1, bg2, bg3, bg4, bgBugFix;
	private Sprite bgSprite1, bgSprite2, bgSprite3, bgSprite4, bgBugFixSprite;
	private Color bloodLust, bgBloodLust;
	
	public Background(){
		bg1 = new Texture(Gdx.files.internal("background1.jpg"));
		bg2 = new Texture(Gdx.files.internal("background2.jpg"));
		bg3 = new Texture(Gdx.files.internal("background3.jpg"));
		bg4 = new Texture(Gdx.files.internal("background4.jpg"));
		bgBugFix = new Texture(Gdx.files.internal("backgroundBugFix.jpg"));
    	
    	bgSprite1 = new Sprite(bg1);
    	bgSprite1.setBounds(0, 0, Util.mapWidth/2, Util.mapHeight/2);
    	bgSprite2 = new Sprite(bg2);
    	bgSprite2.setBounds(Util.mapWidth/2, 0, Util.mapWidth/2, Util.mapHeight/2);
    	bgSprite3 = new Sprite(bg3);
    	bgSprite3.setBounds(0, Util.mapHeight/2, Util.mapWidth/2, Util.mapHeight/2);
    	bgSprite4 = new Sprite(bg4);
    	bgSprite4.setBounds(Util.mapWidth/2, Util.mapHeight/2, Util.mapWidth/2, Util.mapHeight/2);
    	bgBugFixSprite = new Sprite(bgBugFix);
    	bgBugFixSprite.setBounds(Util.mapWidth/2 - 1, 0, 4, Util.mapHeight);

		bloodLust = new Color(Color.valueOf("46eaee"));
		bgBloodLust = new Color(Color.valueOf("b91511"));
	}
	
	public void draw(Batch batch){
		bgBugFixSprite.draw(batch);
		bgSprite1.draw(batch);
		bgSprite2.draw(batch);
		bgSprite3.draw(batch);
		bgSprite4.draw(batch);
	}
	
	public void bloodlustMode(Batch batch){
		batch.setColor(bloodLust);
		bgSprite1.setColor(bgBloodLust);
		bgSprite2.setColor(bgBloodLust);
		bgSprite3.setColor(bgBloodLust);
		bgSprite4.setColor(bgBloodLust);
		bgBugFixSprite.setColor(bgBloodLust);
	}
	
	public void normalMode(Batch batch){
		batch.setColor(Color.WHITE);
		bgSprite1.setColor(Color.WHITE);
		bgSprite2.setColor(Color.WHITE);
		bgSprite3.setColor(Color.WHITE);
		bgSprite4.setColor(Color.WHITE);
		bgBugFixSprite.setColor(Color.WHITE);
	}
	
	public void dispose(){
		bg1.dispose();
		bg2.dispose();
		bg3.dispose();
		bg4.dispose();
		bgBugFix.dispose();
	}
}
