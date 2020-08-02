package com.bat.fnd;

import com.badlogic.gdx.Game;

public class FlapAndDestroy extends Game {
	
	Game game;
	
	@Override
	public void create() {
		game = this;
		setScreen(new FndMenu(game));
	}

	@Override
	public void dispose() {
	}

	@Override
	public void render() {		
		super.render();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}