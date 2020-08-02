package com.bat.fnd;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class FndHowScreen implements Screen {

	private Game game;
	private SpriteBatch batch;
	private Stage stage;
	private OrthographicCamera cam;
	private Background bg;
	
	private LabelStyle style;
	private BitmapFont font;
	private Label flap1Lbl, jump2Lbl, egg3Lbl, beware4Lbl, comb5Lbl, drag6Lbl;
	private TextureRegion earl12, footballer12, egg3, lilplane4, drag6;
	private Texture dragTexture;
	
	private int fballX, fballY;
	
	public FndHowScreen(Game game){
		this.game = game;
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		cam = ViewManager.getCam();
		bg = ViewManager.getBg();
		batch = new SpriteBatch();
		
		stage = new Stage();
		font = new BitmapFont(Gdx.files.internal("font.fnt"), false);
		font.setScale(Gdx.graphics.getWidth() / 3000f);
		style = new LabelStyle(font, Color.BLACK);
		
		flap1Lbl = new Label("Touch the screen (he likes it) in the direction you want to flap.", style);
		jump2Lbl = new Label("Earl learned the art of fighting from his italian master." +
				" Jump on an enemy's head to take him down!", style);
		egg3Lbl = new Label("Swipe down to poop out an explosive egg. Yes, Earl is a boy and he lays eggs, don't ask... " +
				"Eat bread, donuts and New York Fried Chicken to recover your egg!", style); 
		beware4Lbl = new Label("Beware of toy planes and balloons, one man's toy is a pigeon's nemesis.", style);
		comb5Lbl = new Label("Take down as many enemies without flaping more than once between killing " +
				"blows to build combos and get more points.", style); 
		drag6Lbl = new Label("The more enemies you kill, the more enemies will appear. " +
				"Kill enough of them to bring out the dragon. Now don't you think you can kill it by " +
				"petting him with your strong feathers and air-filled claws. Only explosives will work!", style);
		
		final float lblWidth = Gdx.graphics.getWidth()/4;
		
		flap1Lbl.setWidth(lblWidth);
		flap1Lbl.setWrap(true);
		jump2Lbl.setWidth(lblWidth);
		jump2Lbl.setWrap(true);
		egg3Lbl.setWidth(lblWidth);
		egg3Lbl.setWrap(true);
		beware4Lbl.setWidth(lblWidth);
		beware4Lbl.setWrap(true);
		comb5Lbl.setWidth(lblWidth);
		comb5Lbl.setWrap(true);
		drag6Lbl.setWidth(lblWidth);
		drag6Lbl.setWrap(true);
		
		final float xStart = Gdx.graphics.getWidth() * 0.1f;
		final float xEnd =  Gdx.graphics.getWidth() * 0.9f - jump2Lbl.getWidth();
		
		System.out.println(Gdx.graphics.getHeight());
		flap1Lbl.setPosition(xStart, Gdx.graphics.getHeight() - flap1Lbl.getHeight() * 5f);
		jump2Lbl.setPosition(xEnd, Gdx.graphics.getHeight() - flap1Lbl.getHeight() * 5f);
		egg3Lbl.setPosition(xStart, Gdx.graphics.getHeight() / 2 + egg3Lbl.getHeight());
		beware4Lbl.setPosition(xEnd, Gdx.graphics.getHeight() / 2 + beware4Lbl.getHeight());
		comb5Lbl.setPosition(xStart, Gdx.graphics.getHeight() * 0.2f);
		drag6Lbl.setPosition(xEnd, Gdx.graphics.getHeight() * 0.2f);
		
		earl12 = new TextureRegion(Util.earl, 254, 256);
		earl12.setRegionWidth((int) Util.generateWidth(16f));
		earl12.setRegionWidth((int) Util.generateHeight((float) earl12.getRegionWidth(), 254, 256));
		footballer12 = EnemyManager.animations[EnemyManager.FBALL].getKeyFrame(1);
		footballer12.setRegionWidth();
		footballer12.setRegionHeight();
		
		egg3 = EggManager.eggTexture;
		egg3.setRegionWidth((int) EggManager.eggWidth);
		egg3.setRegionHeight((int) EggManager.eggHeight);
		lilplane4 = new TextureRegion(MobileBlockManager.textures[MobileBlockManager.LILPLANE]);
		lilplane4.setRegionWidth((int) MobileBlockManager.widths[MobileBlockManager.LILPLANE]);
		lilplane4.setRegionHeight((int) MobileBlockManager.heights[MobileBlockManager.LILPLANE]);
		dragTexture = new Texture(Gdx.files.internal("dragonblured.png"));
		drag6 = new TextureRegion(dragTexture, EnemyManager.animations[EnemyManager.DRAG].getKeyFrame(0).getRegionWidth(),
					EnemyManager.animations[EnemyManager.DRAG].getKeyFrame(0).getRegionHeight());
		
		fballX = (int) Util.width/2 - footballer12.getRegionWidth()/2;
		fballY = (int) flap1Lbl.getHeight() - footballer12.getRegionHeight();
		
		stage.addActor(flap1Lbl);
		stage.addActor(jump2Lbl);
		stage.addActor(egg3Lbl);
		stage.addActor(beware4Lbl);
		stage.addActor(comb5Lbl);
		stage.addActor(drag6Lbl);
	}
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		cam.position.set(ViewManager.getStartx(), ViewManager.getStarty(), 0);
		batch.setProjectionMatrix(cam.combined);
		cam.update();
		
		batch.begin();
		bg.draw(batch);
		batch.draw(footballer12, fballX, fballY);
		batch.draw(earl12, fballX * 0.95f, fballY + footballer12.getRegionHeight());
		batch.end();
		
		stage.act();
		stage.draw();

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		dragTexture.dispose();
		batch.dispose();
		stage.dispose();
		game.dispose();
	}

}
