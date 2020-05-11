package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.utils.Rect;

public class ButtonSprite extends Sprite {
    private Texture texture;
    private boolean pressed;

    public ButtonSprite(Texture texture) {
        super(new TextureRegion(texture));
        this.texture = texture;
        this.setPressed(false);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.setSize();
    }

    public void update(boolean pressed, Vector2 position) {
        if(isMe(position)) {
            this.setPressed(pressed);
        }
    }

    private void setPressed(boolean pressed) {
        this.pressed = pressed;
        setSize();
    }

    private void setSize() {
        if (this.pressed) {
            this.setHeightProportion(0.09f);
        } else {
            this.setHeightProportion(0.10f);
        }
    }

    public void dispose() {
        texture.dispose();
    }
}
