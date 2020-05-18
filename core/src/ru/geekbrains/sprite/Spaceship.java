package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.utils.Rect;


public class Spaceship extends Sprite {

    private static final float SPEED = 0.01f;
    private static final float SIZE =  0.25f;

    private Vector2 touchPosition;
    private Vector2 v;

    public Spaceship(TextureAtlas atlas) {
        super(atlas.findRegion("Spaceship1"));
        this.touchPosition = new Vector2();
        this.v = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(SIZE);
    }

    @Override
    public void update(float delta) {

        // move to center w/o temp vector
        v.set(touchPosition).sub(pos);
        if(v.len() > SPEED) {
            v.setLength(SPEED);
        }
        pos.add(v);

        // move to border by set length
        /*if(!isMe(touchPosition)) {
            //v.set(touchPosition).sub(pos).nor().scl(SPEED);
            v.set(touchPosition).sub(pos).setLength(SPEED);
            pos.add(v);
        }*/
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        touchPosition.set(touch);
        return super.touchDown(touch, pointer, button);
    }

}
