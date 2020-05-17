package ru.geekbrains;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.sprite.ButtonSprite;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Spaceship;
import ru.geekbrains.utils.Rect;

public class MenuScreen extends BaseScreen {

    private Spaceship spaceship;
    private Background background;
    private ButtonSprite buttonPlay;
    private ButtonSprite buttonExit;

    private Vector2 touchPosition = new Vector2();

    @Override
    public void show() {
        super.show();
        spaceship = new Spaceship(new Texture("badlogic.jpg"));
        background = new Background(new Texture("stars.jpg"));
        buttonPlay = new ButtonSprite(new Texture("ButtonPlay.png"));
        buttonExit = new ButtonSprite(new Texture("ButtonExit.png"));
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        buttonPlay.resize(worldBounds);
        buttonExit.resize(worldBounds);
        spaceship.resize(worldBounds);

        buttonPlay.setRight(worldBounds.getRight() - 0.01f);
        buttonPlay.setTop(worldBounds.getTop() - 0.01f);

        buttonExit.setRight(worldBounds.getRight() - 0.01f);
        buttonExit.setTop(worldBounds.getTop() - 0.12f);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        spaceship.update(touchPosition);
        batch.begin();
        background.draw(batch);
        spaceship.draw(batch);
        buttonPlay.draw(batch);
        buttonExit.draw(batch);
        batch.end();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchPosition.set(screenX, Gdx.graphics.getHeight() - screenY).mul(screenToWorld);
        buttonPlay.update(true, touchPosition);
        buttonExit.update(true, touchPosition);
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touchPosition.set(screenX, Gdx.graphics.getHeight() - screenY).mul(screenToWorld);
        buttonPlay.update(false, touchPosition);
        buttonExit.update(false, touchPosition);
        return super.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean keyDown(int keycode) {
        touchPosition.set(0,0);
        return super.keyDown(keycode);
    }

    @Override
    public void dispose() {
        spaceship.dispose();
        background.dispose();
        buttonPlay.dispose();
        buttonExit.dispose();
        super.dispose();
    }
}
