package ru.geekbrains.base;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.utils.Rect;

// w/o multitouch
public abstract class ButtonSprite extends Sprite {

    private static final float SIZE_DOWN =  0.09f;
    private static final float SIZE_UP =  0.10f;

    private boolean pressed;

    protected Game game;

    public ButtonSprite(TextureRegion textureRegion, Game game) {
        super(textureRegion);
        this.game = game;
        setPressed(false);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.setSize();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if(isMe(touch) && !pressed) {
            setPressed(true);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if(isMe(touch) && pressed) {
            action();
        }
        setPressed(false);
        return false;
    }

    private void setPressed(boolean pressed) {
        this.pressed = pressed;
        setSize();
    }

    private void setSize() {
        if (this.pressed) {
            this.setHeightProportion(SIZE_DOWN);
        } else {
            this.setHeightProportion(SIZE_UP);
        }
    }

    public abstract void action();

}
