package ru.geekbrains.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.group.SpriteFactory;
import ru.geekbrains.utils.Rect;
import ru.geekbrains.utils.Regions;

public class EnemyShip extends Sprite {

    private static final float SPEED0 = -0.01f;
    private static final float SPEED = -0.001f;
    private static final float BULLET_SPEED = -0.5f;
    private static final float SIZE =  0.2f;
    private static final float RELOAD_INTERVAL = 1f;

    private Vector2 v0;
    private Vector2 v;

    private float passingTime;
    private float reloadInterval;

    // Bullets
    private SpriteFactory bullets;
    private Sound bulletSound;
    private TextureRegion bulletRegion;
    private Vector2 bulletV;

    public EnemyShip() {
        regions = new TextureRegion[1];
        this.v = new Vector2(0, SPEED);
        this.v0 = new Vector2(0, SPEED0);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        bullets.resize(worldBounds);
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

        passingTime +=delta;
        if (passingTime >= reloadInterval) {
            shoot();
            passingTime = 0;
        }

        bullets.update(delta);
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        bullets.draw(batch);
    }

    public void setup(TextureAtlas atlas, SpriteFactory bullets, Vector2 pos0, Rect worldBounds, int type) {
        switch (type) {
            case 0:
                this.regions = Regions.split(atlas.findRegion("enemy0"), 1, 2, 2);
                this.v.set(0, SPEED * 2f);
                this.reloadInterval = RELOAD_INTERVAL * 0.5f;
                setHeightProportion(SIZE * 0.5f);
                break;
            case 1:
                this.regions = Regions.split(atlas.findRegion("enemy1"), 1, 2, 2);
                this.v.set(0, SPEED * 1.5f);
                this.reloadInterval = RELOAD_INTERVAL * 0.7f;
                setHeightProportion(SIZE * 0.7f);
                break;
            case 2:
            default:
                this.regions = Regions.split(atlas.findRegion("enemy2"), 1, 2, 2);
                this.v.set(0, SPEED);
                this.reloadInterval = RELOAD_INTERVAL;
                setHeightProportion(SIZE);
                break;
        }
        this.bulletV = new Vector2(0, BULLET_SPEED);
        this.bulletRegion = atlas.findRegion("bulletEnemy");
        // TODO
        this.bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        this.worldBounds = worldBounds;
        this.pos.set(pos0);
        this.bullets = bullets;
        setActive(true);
    }

    private void shoot() {
        Bullet bullet = (Bullet) bullets.obtain();
        bullet.setup(this, bulletRegion, pos, bulletV, 0.01f, worldBounds, 1);
        bulletSound.play();
    }

}
