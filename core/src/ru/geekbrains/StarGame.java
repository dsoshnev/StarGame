package ru.geekbrains;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.screen.GameScreen;
import ru.geekbrains.screen.MenuScreen;

public class StarGame extends Game {

	private Screen menuScreen;
	private Screen gameScreen;

	private TextureAtlas menuAtlas;
	private TextureAtlas gameAtlas;

	@Override
	public void create () {
		setScreen(getMenuScreen());
	}

	@Override
	public void dispose() {
		try {
			menuScreen.dispose();
			gameScreen.dispose();
			menuAtlas.dispose();
			gameAtlas.dispose();
		} catch (NullPointerException ignored) {}
		super.dispose();
	}

	public Screen getMenuScreen() {
		if (menuScreen == null) {
			menuScreen = new MenuScreen(this);
		}
		return menuScreen;
	}

	public Screen getGameScreen() {
		if (gameScreen == null) {
			gameScreen = new GameScreen(this);
		}
		return gameScreen;
	}


	public TextureAtlas getMenuAtlas() {
		if (menuAtlas == null) {
			menuAtlas = new TextureAtlas("MenuAtlas.atlas");
		}
		return menuAtlas;
	}

	public TextureAtlas getGameAtlas() {
		if (gameAtlas == null) {
			gameAtlas = new TextureAtlas("GameAtlas.atlas");
		}
		return gameAtlas;
	}

}
