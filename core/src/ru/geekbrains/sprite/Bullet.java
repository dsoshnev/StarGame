package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.utils.Rect;

public class Bullet extends Sprite {
    private Rect worldBounds;
    private Vector2 v;
    private int damage;

    public Bullet() {
        regions = new TextureRegion[1];
        v = new Vector2();
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        if(v.y > 0) {
            if (getBottom() > worldBounds.getTop()) {
                destroy();
            }
        } else {
            if (getTop() < worldBounds.getBottom()) {
                destroy();
            }
        }
    }

    public void setup(
            Sprite owner,
            TextureRegion region,
            Vector2 pos0,
            Vector2 v0,
            float height,
            Rect worldBounds,
            int damage
    ) {
        setOwner(owner);
        this.regions[0] = region;
        this.pos.set(pos0);
        this.v.set(v0);
        setHeightProportion(height);
        this.worldBounds = worldBounds;
        this.damage = damage;
        setActive(true);
    }

    public int getDamage() {
        return damage;
    }

}
