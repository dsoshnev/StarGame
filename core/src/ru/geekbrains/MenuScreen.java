package ru.geekbrains;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.ButtonExit;
import ru.geekbrains.sprite.ButtonPlay;
import ru.geekbrains.sprite.Spaceship;
import ru.geekbrains.utils.Rect;

public class MenuScreen extends BaseScreen {

    private TextureAtlas menuAtlas;
    private TextureAtlas gameAtlas;

    private Spaceship spaceship;

    private Background background;

    private ButtonPlay buttonPlay;
    private ButtonExit buttonExit;

    @Override
    public void show() {
        super.show();
        menuAtlas = new TextureAtlas("MenuAtlas.atlas");
        gameAtlas = new TextureAtlas("GameAtlas.atlas");
        spaceship = new Spaceship(gameAtlas);
        background = new Background(new Texture("stars.jpg"));
        buttonPlay = new ButtonPlay(menuAtlas);
        buttonExit = new ButtonExit(menuAtlas);
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        buttonPlay.resize(worldBounds);
        buttonExit.resize(worldBounds);
        spaceship.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        //Update
        spaceship.update(delta);

        //Draw
        batch.begin();
        background.draw(batch);
        spaceship.draw(batch);
        buttonPlay.draw(batch);
        buttonExit.draw(batch);
        batch.end();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        spaceship.touchDown(touch, pointer, button);
        buttonPlay.touchDown(touch, pointer, button);
        buttonExit.touchDown(touch, pointer, button);
        return super.touchDown(touch, pointer, button);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        spaceship.touchUp(touch, pointer, button);
        buttonPlay.touchUp(touch, pointer, button);
        buttonExit.touchUp(touch, pointer, button);
        return super.touchUp(touch, pointer, button);
    }

    @Override
    public void dispose() {
        background.dispose();
        menuAtlas.dispose();
        gameAtlas.dispose();
        super.dispose();
    }
}
