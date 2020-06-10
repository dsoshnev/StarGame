package ru.geekbrains.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

import ru.geekbrains.utils.Rect;
import ru.geekbrains.utils.Regions;

public class Sprite extends Rect implements Pool.Poolable {

    private float angle;
    private float scale = 1f;
    protected TextureRegion[] regions;
    protected int frame = 0;
    private boolean active;
    protected Rect worldBounds;
    private Sprite owner;

    public Sprite() { }

    public Sprite(TextureRegion region) {
        regions = new TextureRegion[1];
        regions[frame] = region;
        setActive(false);
    }

    public Sprite(TextureRegion region, int rows, int cols, int frames) {
        regions = Regions.split(region, rows, cols, frames);
    }


    protected void setHeightProportion(float height) {
        setHeight(height);
        float aspect = regions[frame].getRegionWidth() / (float) regions[frame].getRegionHeight();
        setWidth(height * aspect);
    }

    public void update(float delta) {}

    public void draw(SpriteBatch batch) {
        batch.draw(
                regions[frame],
                getLeft(), getBottom(),
                halfWidth, halfHeight,
                getWidth(), getHeight(),
                scale, scale,
                angle
        );
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    public boolean touchDown(Vector2 touch, int pointer, int button) {
        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer, int button) {
        return false;
    }

    public boolean keyDown(int keycode) { return false; }

    public boolean keyUp(int keycode) { return false; }

    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void destroy() {
        setActive(false);
    }

    @Override
    public void reset() {
        setActive(false);
    }

    public void dispose() {}

    public boolean collided(Sprite sprite) {
        float minDst = this.getHalfWidth() + sprite.getHalfWidth();
        return this.pos.dst(sprite.pos) < minDst;
    }

    public Sprite getOwner() {
        return owner;
    }

    public void setOwner(Sprite owner) {
        this.owner = owner;
    }
}
