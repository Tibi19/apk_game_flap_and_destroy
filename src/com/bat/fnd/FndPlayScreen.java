package com.bat.fnd;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class FndPlayScreen implements Screen, ContactListener{

	private SpriteBatch batch;
	private OrthographicCamera cam;
	private Game game;
	private World world;
	private Box2DDebugRenderer renderer;
	private InputMultiplexer imp;
	private InputProcessor ip;
	private Label scoreLabel, enemiesLabel, highScoreLabel, comboLabel, maxComboLabel;
	private Button eggButton;
	private ButtonStyle eggStyle, shadStyle;
	private InputListener eggListen;
	private Stage stage;
	private Earl player;
	private Background bg;
	private ArrayList<Enemy> enemies;
	private ArrayList<Misc> objects;
	private Egg egg = null;
	
	private int score = 0, highscore = 0, maxCombo = 0, maxEnemies = 30, maxBlocks = 4, enemyPool = 2, combo = 0, flaps = 0;
	private boolean eggPressed = false, spawnExplosion = false, loaded = false;
	
	public FndPlayScreen(Game game){
		this.game = game;
	}
	
	@Override
	public void show() {
		initBasicStuff();
		initialize();
		initStage();
	}
	
		private void initStage() {
			stage = new Stage();
			highscore = loadHighscore();
			maxCombo = loadMaxCombo();
			
			BitmapFont font = new BitmapFont(Gdx.files.internal("font.fnt"), false);
			font.setScale(0.65f);
			
			scoreLabel = new Label("" + score, new LabelStyle(font, Color.BLACK));
			scoreLabel.setPosition(Gdx.graphics.getWidth()/2 - scoreLabel.getWidth()/2, Gdx.graphics.getHeight() 
					- scoreLabel.getHeight());
			stage.addActor(scoreLabel);
			
			enemiesLabel = new Label("maxEnemies:" + maxEnemies + " enemies:" + enemies.size() + " enemyPool:" + enemyPool + 
					" op:" + String.valueOf(player.op), new LabelStyle(font, Color.BLACK));
			enemiesLabel.setPosition(0, 0);
			//stage.addActor(enemiesLabel);
			
			BitmapFont highscoreFont = new BitmapFont(Gdx.files.internal("font.fnt"), false);
			highscoreFont.setScale(0.55f);
			highScoreLabel = new Label("Highscore  " + highscore, new LabelStyle(highscoreFont, Color.BLACK));
			highScoreLabel.setPosition(Gdx.graphics.getWidth()/80, Gdx.graphics.getHeight() - scoreLabel.getHeight());
			stage.addActor(highScoreLabel);
			
			if (maxCombo > 9)
				maxComboLabel = new Label("Max Combo  X" + maxCombo, new LabelStyle(highscoreFont, Color.BLACK));
			else maxComboLabel = new Label("Max Combo  X" + maxCombo + " ", new LabelStyle(highscoreFont, Color.BLACK));
			maxComboLabel.setPosition(Gdx.graphics.getWidth() - maxComboLabel.getWidth() - Gdx.graphics.getWidth()/80,
					Gdx.graphics.getHeight() - scoreLabel.getHeight());
			stage.addActor(maxComboLabel);
			
			comboLabel = new Label("", new LabelStyle(font, Color.BLACK));
			comboLabel.setPosition(0, Gdx.graphics.getHeight()/5 * 4);
			stage.addActor(comboLabel);
			
			initListener();
			TextureAtlas eggAtlas = new TextureAtlas();
			eggAtlas.addRegion("eggUp", EggManager.eggTexture);
			eggAtlas.addRegion("eggDown", EggManager.eggDownTexture);
			eggAtlas.addRegion("shadow", EggManager.shadowTexture);
			Skin eggSkin = new Skin();
			eggSkin.addRegions(eggAtlas);
			eggStyle = new ButtonStyle();
			eggStyle.up = eggSkin.getDrawable("eggUp");
			eggStyle.down = eggSkin.getDrawable("eggDown");
			eggStyle.checked = eggSkin.getDrawable("eggDown");
			shadStyle = new ButtonStyle();
			shadStyle.up = eggSkin.getDrawable("shadow");
			eggButton = new Button(eggStyle);
			eggButton.setSize(EggManager.buttWidth, EggManager.buttHeight);
			eggButton.setPosition(Gdx.graphics.getWidth() - eggButton.getWidth() - Gdx.graphics.getWidth()/80, 
					0 + Gdx.graphics.getWidth()/80);
			eggButton.addListener(eggListen);
			stage.addActor(eggButton);
			
			imp.addProcessor(stage);
		}

			private void initListener() {
				eggListen = new InputListener(){

					@Override
					public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
							eggPressed = true;
							eggButton.setStyle(shadStyle);
							eggButton.setDisabled(true);
							egg = new Egg(world, player.getPosition());
							return true;
						}
				};
			}

			private int loadMaxCombo() {
				int n = 0;
				if (Gdx.files.local("maxcombo.dat").exists())
					n = FileManager.loadInt("maxcombo.dat");
				return n;
			}

			private int loadHighscore() {
				int n = 0;
				if (Gdx.files.local("highscore.dat").exists())
					n = FileManager.loadInt("highscore.dat");
				return n;
			}

		private void initialize() {
			world.setContactListener(this);
			enemies = new ArrayList<Enemy>();
			objects = new ArrayList<Misc>();
			//staticBlocks = new ArrayList<Misc>();
			MobileBlockManager.mobilesNr = 4;
			bg = ViewManager.getBg();
			player = new Earl(world);
			for(int index = Util.random.nextInt(enemyPool), i = 0; i < maxEnemies; i++){
				enemies.add(new Enemy(index, world));
				index = Util.random.nextInt(enemyPool);
			}
			for(int i = 0; i < 4; i++) objects.add(getNewMobileBlock());
			//staticBlocks.add(getNewStaticBlockOnStart());
		}
	
			/*private Misc getNewStaticBlockOnStart() {
				switch (Util.random.nextInt(4))
				{
				case 0: miscAux = new Pole(world);
					break;
				case 1: miscAux = new Kite(world, Util.random.nextBoolean());
					break;
				case 2: miscAux = new Heli(world, Util.random.nextBoolean());
					break;
				case 3: miscAux = new Hotair(world, Util.random.nextBoolean());
					break;
				}
				return miscAux;
			}*/
		
			/*private Misc getNewStaticBlock() {
				switch (Util.random.nextInt(4))
				{
				case 0: miscAux = new Pole(world);
					break;
				case 1: miscAux = new Kite(world);
					break;
				case 2: miscAux = new Heli(world);
					break;
				case 3: miscAux = new Hotair(world);
					break;
				}
				return miscAux;
			}*/

			private Misc getNewMobileBlock() {
				Misc miscAux = null;
				//if (Util.random.nextInt(20) == 0) miscAux = new Bigoplane(world);
				//else 
				switch(Util.random.nextInt(4))
					{
					case 0: miscAux = new Lilplane(world);
						break;
					case 1: miscAux = new Balloons(world);
						break;
					case 2: miscAux = new Newspaper(world);
						break;
					case 3: miscAux = new Meteorite(world, (float) 9 - Util.random.nextInt(enemyPool), 
							 (float) 5 + Util.random.nextInt(3*enemyPool));
						break;
					}
				return miscAux;
			}

		private void initBasicStuff() {
			cam = ViewManager.getCam();
			//cam = new OrthographicCamera(Util.mapWidth, Util.mapHeight);
			batch = new SpriteBatch();
			world = new World(new Vector2(0f, -20f), true);
			renderer = new Box2DDebugRenderer();
			initInputProcessor();
		}

		
	@Override
	public void render(float delta) {
		basicRender();
		batchRender();
		updateRender();
	}

		private void batchRender() {
			batch.begin();
			
			bg.draw(batch);
			if (egg != null) egg.draw(batch);
			player.draw(batch);
			for(Enemy enemy : enemies)
			{
				if (Util.overlapsCamera(enemy.getPosition().x, enemy.getPosition().y, enemy.getWidth(), enemy.getHeight())) 
					enemy.draw(batch);
			}
			for(Misc misc : objects) 
			{
				if (Util.overlapsCamera(misc.getPosition().x, misc.getPosition().y, misc.width, misc.height)) 
					misc.draw(batch);
			}
			
			batch.end();
			
			stage.act();
			stage.draw();
			
			//renderer.render(world, cam.combined);
		}
	
		private void updateRender() {
			cam.update();
			world.step(1f/60f, 6, 2);
			player.update();
			if (egg != null) egg.update();
			for(Enemy enemy : enemies) enemy.update();
			for(Misc misc : objects) misc.update();
			spawnExplosion();
			removeObjects();
			updateStage();
		}

			private void updateStage() {
				scoreLabel.setText("" + score);
				//enemiesLabel.setText("maxEnemies:" + maxEnemies + " enemies:" + enemies.size() + " enemyPool:" + enemyPool + 
				//		" op:" + String.valueOf(player.op));
				highScoreLabel.setText("Highscore  " + highscore);
				maxComboLabel.setText("Max Combo  X" + maxCombo);
				if (combo > 1) 
				{	
					comboLabel.setText(combo + "X COMBO!!!"); 
					comboLabel.setX(Gdx.graphics.getWidth()/2 - comboLabel.getTextBounds().width/3);
				}
				else comboLabel.setText("");
			}
			
			private void spawnExplosion() {
				if (spawnExplosion)
				{
					objects.add(new Explosion(world)); 
					spawnExplosion = false;
					egg.dispose();
					egg = null;
				}
			}
			
			private void spawnEnemies() {
				if (enemyPool < 8 && score > Math.pow(enemyPool, 2.722)) {enemyPool++; maxBlocks = enemyPool*2;}
				
				while (enemies.size() < maxEnemies)
					enemies.add(new Enemy(generateIndex(0, enemyPool - 1), world,  player.getCurrentFrame().isFlipX()));
			}
			
				private int generateIndex(int a, int z) {
					if (a == z) return a;
					else if (Util.random.nextInt(f(a, z)) != 1)
						return generateIndex(a, (a+z)/2);
					else return generateIndex((a+z)/2 + 1, z);
				}
				
					private int f(int a, int z){ return 2 * Math.min(g(a, z), 1) + 2; }
					
					private int g(int a, int z){ return 1 - (int) ((a + 1) / (z * z)); }

			private void removeEnemies() {
				for(int i = 0; i < enemies.size(); i++)
					if (enemies.get(i).shouldBeDead()) {
						enemies.get(i).dispose(world);
						enemies.remove(i);
						continue;
					}
			}
			
			private void removeObjects() {
				for(int i = 0; i < objects.size(); i++)
					if (objects.get(i).shouldBeDisposed()) {
						objects.get(i).dispose();
						objects.remove(i);
						continue;
					}
			}
			
			/*private void removeStatics() {
				if (Util.random.nextInt(33) == 0)
				{
					i = Util.random.nextInt(staticBlocks.size());
					if (staticBlocks.get(i).shouldBeDisposed()) 
					{
						staticBlocks.get(i).dispose();
						staticBlocks.remove(i);
					}
				}
			}*/
			
			private void spawnFood() {
				if (Util.random.nextInt(33) == 0) objects.add(new Food(world, Util.random.nextInt(3)));
			}
			
			private void spawnMobiles() {
				maxBlocks = enemyPool*2; //this can be deleted once cheat codes are taken out
				while (MobileBlockManager.mobilesNr < maxBlocks) 
				{
					objects.add(getNewMobileBlock()); 
					MobileBlockManager.mobilesNr++;
				}
			}
			
			/*private void spawnStatics() {
				while (staticBlocks.size() < maxBlocks) 
				{
					staticBlocks.add(getNewStaticBlock());
					miscAux = staticBlocks.get(staticBlocks.size() - 1);
					Util.setRectangle(miscAux.getPosition().x, miscAux.getPosition().y, miscAux.width, miscAux.height);
					for (i = 0; i < staticBlocks.size() - 1; i++)
					{
						miscAux = staticBlocks.get(i);
						if (Util.overlapsPreSet(miscAux.getPosition().x, miscAux.getPosition().y, miscAux.width, miscAux.height))
						{
							staticBlocks.get(staticBlocks.size() - 1).dispose();
							staticBlocks.remove(staticBlocks.size() - 1);
							break;
						}
					}
				}
			}*/

		private void basicRender() {
			Gdx.gl.glClearColor(1, 1, 1, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			cam.position.set(Earl.camX, Earl.camY, 0);
			batch.setProjectionMatrix(cam.combined);
		}

		
	@Override
	public void dispose() {
		for(Enemy enemy : enemies){
			enemy.dispose(world);
			enemy = null;
		}
		enemies.clear();
		enemies = null;
		for(Misc misc : objects){
			misc.dispose();
			misc = null;
		}
		objects.clear();
		objects = null;
		player.dispose();
		player = null;
		ip = null;
		imp = null;
		scoreLabel = null;
		enemiesLabel = null;
		highScoreLabel = null;
		comboLabel = null;
		maxComboLabel = null;
		eggButton = null;
		eggStyle = null;
		shadStyle = null;
		eggListen = null;
		world.dispose();
		world = null;
		cam = null;
		stage.dispose();
		stage = null;
		batch.dispose();
		batch = null;
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
		
		FileManager.saveInt(highscore, "highscore.dat");
		FileManager.saveInt(maxCombo, "maxcombo.dat");
	}
	
	
	private void initInputProcessor() {
		imp = new InputMultiplexer();
		ip = new InputProcessor() {
			
			int y;
			
			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {
				if(screenY > y + Gdx.graphics.getHeight()/9 && !eggButton.isDisabled() && loaded)
				{
					eggButton.setStyle(shadStyle);
					eggButton.setDisabled(true);
					egg = new Egg(world, player.getPosition());
				}
				else if(!player.dead && !eggPressed)
				{
					if (screenX < Gdx.graphics.getWidth()/2)
					{
						player.body.setLinearVelocity(0f, 0f);
						player.body.applyForceToCenter(player.getJumpLeft(), true);
						if (player.getCurrentFrame().isFlipX()) player.flip();
					}else{
						player.body.setLinearVelocity(0f, 0f);
						player.body.applyForceToCenter(player.getJumpRight(), true);
						if (!player.getCurrentFrame().isFlipX()) player.flip();
					}
					
					if (flaps++ > 0)
					{
						combo = 0;
						bg.normalMode(batch);
					}
					
					if (!loaded) loaded = true;
				}
				else if(eggPressed) eggPressed = false;
				else if(player.getPosition().y + player.getHeight() < 0)
				{
					Util.disposable = false;
					dispose();
					game.setScreen(new FndMenu(game));
				}

				if (y - Gdx.graphics.getHeight()/3 > screenY)
					if (screenX > Gdx.graphics.getWidth()/2) player.op = !player.op;
					else enemyPool = 8;
				
				return false;
			}

			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				return false;
			}
					
			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				y = screenY;
				removeEnemies();
				spawnEnemies();
				spawnFood();
				spawnMobiles();
				return false;
			}
					
			@Override
			public boolean scrolled(int amount) {
				return false;
			}
					
			@Override
			public boolean mouseMoved(int screenX, int screenY) {
				return false;
			}
					
			@Override
			public boolean keyUp(int keycode) {
				return false;
			}
					
			@Override
			public boolean keyTyped(char character) {
				return false;
			}
					
			@Override
			public boolean keyDown(int keycode) {
				if (keycode == Keys.A) player.body.applyForceToCenter(new Vector2(-100f, 0f), true);
				else if (keycode == Keys.D) player.body.applyForceToCenter(new Vector2(100f, 0f), true);
				else if (keycode == Keys.X){int i= Util.random.nextInt(enemies.size()); enemies.get(i).dispose(world); enemies.remove(i);}
				else if (keycode == Keys.C) {score++; System.out.println(cam.position.x + " " + cam.position.y);}
				else if (keycode == Keys.G) objects.add(new Explosion(world));
				else if (keycode == Keys.F) {
					eggPressed = true;
					eggButton.setStyle(shadStyle);
					eggButton.setDisabled(true);
					egg = new Egg(world, player.getPosition());
				}
				else if (keycode == Keys.W) {maxEnemies++; spawnEnemies();}
				else if (keycode == Keys.ESCAPE) dispose();
				return true;
			}
		};
		imp.addProcessor(ip);
		Gdx.input.setInputProcessor(imp);
	}
	

	@Override
	public void beginContact(Contact contact) {
		switch((int)contact.getFixtureB().getFilterData().categoryBits)
		{
		case 8:
			Enemy enemyAux = (Enemy) contact.getFixtureB().getUserData();
			if (player.getPosition().y > enemyAux.getPosition().y && enemyAux.getIndex() != 7)
			{	
				score += (enemyAux.getValue() + combo++);
				flaps = 0;
				player.body.setLinearVelocity(0, 0);
				enemyAux.body.setLinearVelocity(0, -350f);
				contact.getFixtureB().setFilterData(Util.voidFilter);
				maxEnemies += 2;
				
				if (score > highscore) highscore = score;
				if (combo > maxCombo) maxCombo = combo;
				if (combo > 9) bg.bloodlustMode(batch);
			}
			else if (!player.op) //op for cheat code
			{
				player.dead = true;
				contact.getFixtureA().getBody().getFixtureList().get(0).setFilterData(Util.voidFilter);
				contact.getFixtureA().getBody().getFixtureList().get(1).setFilterData(Util.voidFilter);
				player.body.applyForceToCenter(player.getJump(), true);
				
				FileManager.saveInt(highscore, "highscore.dat");
				FileManager.saveInt(maxCombo, "maxcombo.dat");
				bg.normalMode(batch);
			}
			break;
		case 16:
			Food foodAux = (Food) contact.getFixtureB().getUserData();
			score += foodAux.getValue();
			foodAux.deactivate();
			contact.getFixtureB().setFilterData(Util.voidFilter);
			eggButton.setStyle(eggStyle);
			eggButton.setDisabled(false);
			break;
		case 32:
			if (!player.op) //op for cheat code
			{
				player.dead = true;
				contact.getFixtureA().getBody().getFixtureList().get(0).setFilterData(Util.voidFilter);
				contact.getFixtureA().getBody().getFixtureList().get(1).setFilterData(Util.voidFilter);
				player.body.applyForceToCenter(player.getJump(), true);
				
				FileManager.saveInt(highscore, "highscore.dat");
				FileManager.saveInt(maxCombo, "maxcombo.dat");
				bg.normalMode(batch);
			}
			break;
		case 64:
			EggManager.explosionPos.set(egg.position.x + egg.width/2 - EggManager.exploWidth/2, 
					egg.position.y + egg.height/2 - EggManager.exploHeight/2);
			spawnExplosion = true;
			break;
		case 128:
			Misc miscAux = (Misc) contact.getFixtureB().getUserData();
			Util.setRectangle(miscAux.getPosition().x, miscAux.getPosition().y + EggManager.exploBodyPos,
					miscAux.width, EggManager.exploBodyH - EggManager.exploBodyPos);
			for (Enemy e : enemies) 
			{
				if (Util.overlapsPreSet(e.getPosition().x, e.getPosition().y + e.getBodyPos(), 
						e.getWidth(), e.getBodyHeight() - e.getBodyPos()))
				{
					combo++;
					score += e.getValue();
					e.deactivate();
					e.body.setLinearVelocity(0, -350f);
					e.body.getFixtureList().get(0).setFilterData(Util.voidFilter);
					maxEnemies += 2;
				}
			}
			flaps = 0;
			score += combo;
		}
	}

	@Override
	public void endContact(Contact contact) {
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
	}	
	
	@Override
	public void resize(int width, int height) {
		
	}

	
	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		FileManager.saveInt(highscore, "highscore.dat");
		FileManager.saveInt(maxCombo, "maxcombo.dat");
	}

	@Override
	public void resume() {
		
	}

}
