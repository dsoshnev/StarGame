package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;


public class Spaceship extends Sprite {

    final private float SPEED = 0.01f;
    final private float SIZE =  0.25f;

    private Texture texture;

    public Spaceship(Texture texture) {
        super(new TextureRegion(texture));
        this.texture = texture;
        this.setHeightProportion(SIZE);
    }

    public void update(Vector2 toPosition) {
        if(!isMe(toPosition)) {
            Vector2 spriteDirection = toPosition.cpy().sub(pos).nor().scl(SPEED);
            this.pos.add(spriteDirection);
        }
    }

    public void dispose() {
        texture.dispose();
    }
}
