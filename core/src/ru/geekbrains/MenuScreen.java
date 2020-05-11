package ru.geekbrains;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class MenuScreen extends BaseScreen {
    private Texture sprite;
    private Texture background;

    private Vector2 touchUpPosition = new Vector2();
    private Vector2 spriteDirection = new Vector2();
    private Vector2 spritePosition = new Vector2();
    private int SPRITE_SIZE = 100;

    @Override
    public void show() {
        super.show();
        sprite = new Texture("badlogic.jpg");
        background = new Texture("stars.jpg");
    }

    private boolean inSprite(Vector2 pos) {
        return spritePosition.x <= pos.x
                && spritePosition.x + SPRITE_SIZE >= pos.x
                && spritePosition.y <= pos.y
                && spritePosition.y + SPRITE_SIZE >= pos.y;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(!inSprite(touchUpPosition)) {
            spriteDirection.set(touchUpPosition).sub(spritePosition).nor();
            spritePosition.add(spriteDirection);
        }
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(sprite, spritePosition.x, spritePosition.y, SPRITE_SIZE, SPRITE_SIZE);
        batch.end();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchUpPosition.set(screenX, Gdx.graphics.getHeight() - screenY);
        return super.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean keyDown(int keycode) {
        spritePosition.set(0,0);
        return super.keyUp(keycode);
    }

    @Override
    public void dispose() {
        sprite.dispose();
        background.dispose();
        super.dispose();
    }
}
