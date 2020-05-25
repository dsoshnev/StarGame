package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.utils.Rect;
import ru.geekbrains.utils.Regions;

public class EnemyShip extends Sprite {

    private static final float SPEED0 = -0.01f;
    private static final float SPEED1 = -0.001f;
    private static final float SIZE =  0.2f;
    private Vector2 v0;
    private Vector2 v;

    private TextureAtlas atlas;

    public EnemyShip(TextureAtlas atlas) {
        regions = new TextureRegion[1];

        // init Ship
        this.atlas = atlas;
        this.v = new Vector2(0, SPEED1);
        this.v0 = new Vector2(0, SPEED0);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
    }

    @Override
    public void update(float delta) {
        if(isActive()) {
            if (getBottom() < worldBounds.getTop() - 0.15f) {
                pos.add(v);
                //pos.mulAdd(v, delta);
            } else {
                pos.add(v0);
            }
        }

        if (getTop() < worldBounds.getBottom()) {
            destroy();
        }
    }

    public void setup(Vector2 pos0, Vector2 v0, Rect worldBounds) {
        this.regions = Regions.split(atlas.findRegion("enemy1"), 1, 2, 2);
        this.worldBounds = worldBounds;
        this.pos.set(pos0);
        //this.v.set(v0);
        setHeightProportion(SIZE);
        setActive(true);
    }

}
