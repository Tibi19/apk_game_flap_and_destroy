package com.bat.fnd;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class FndMenu implements Screen {

	private Stage stage;
	private Label startLabel, howLabel;
	private LabelStyle style;
	private BitmapFont font;
	private SpriteBatch batch;
	private Game game;
	private Button startButton, howButton;
	private ButtonStyle buttonStyle;
	private OrthographicCamera cam;
	private Background bg;
	
	public FndMenu(Game game){
		this.game = game;
	}
	
	@Override
	public void show() {
		
		initializeStatics();
		
		cam = ViewManager.getCam();
		bg = ViewManager.getBg();
		
		stage = new Stage();
		
		font = new BitmapFont(Gdx.files.internal("font.fnt"), false);
		font.setScale(1.35f);
		style = new LabelStyle(font, Color.BLACK);
		startLabel = new Label("Start", style);
		howLabel = new Label("How to Play", style);
		stage.addActor(startLabel);
		stage.addActor(howLabel);

		buttonStyle = new ButtonStyle();
		startButton = new Button(startLabel, buttonStyle);
		startButton.setPosition(Gdx.graphics.getWidth() / 2 - startLabel.getWidth() / 2,
				Gdx.graphics.getHeight() / 2 + startLabel.getHeight() / 2);
		stage.addActor(startButton);
		howButton = new Button(howLabel, buttonStyle);
		howButton.setPosition(Gdx.graphics.getWidth() / 2 - howLabel.getWidth() / 2,
				Gdx.graphics.getHeight() / 2 - howLabel.getHeight());
		stage.addActor(howButton);
		
		Gdx.input.setInputProcessor(stage);
		startButton.addListener(new InputListener(){

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					Util.disposable = false;
					dispose();
					game.setScreen(new FndPlayScreen(game));
					return true;
				}
		});
		howButton.addListener(new InputListener(){

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					Util.disposable = false;
					dispose();
					game.setScreen(new FndHowScreen(game));
					return true;
				}
		});

		batch = new SpriteBatch();
	}
	
	private void initializeStatics() {
		if (!Util.initialized)
		{
			Util.initialize();
			ViewManager.initialize();
			EnemyManager.initialize();
			FoodManager.initialize();
			MobileBlockManager.initialize();
			EggManager.Initialize();
		}
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
		font.dispose();
		batch.dispose();
		stage.dispose();
		game.dispose();
		if (Util.disposable)
		{
			Util.dispose();
			ViewManager.dispose();
			EnemyManager.dispose();
			FoodManager.dispose();
			MobileBlockManager.dispose();
			EggManager.dispose();
		} else 
			Util.disposable = true;
	}

}
