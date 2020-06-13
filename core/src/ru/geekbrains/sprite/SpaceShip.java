package ru.geekbrains.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.group.SpriteFactory;
import ru.geekbrains.utils.Rect;
import ru.geekbrains.utils.Regions;


public class SpaceShip extends Sprite {

    private static final float SPEED = 0.005f;
    private static final float SIZE =  0.1f;
    private static final float RELOAD_INTERVAL = 0.25f;
    private static final float INERTIA = 0.25f;
    private static final int HIP_POINT = 30;

    private static final float BULLET_SPEED = 0.5f;
    private static final float BULLET_SIZE = 0.05f;
    private static final int BULLET_DAMAGE = 10;

    private Vector2 touchPosition;
    private Vector2 v;

    private TextureAtlas atlas;

    private float passingTime;
    private float reloadInterval;

    //Bullets
    private SpriteFactory bullets;
    private Vector2 bulletV;
    private TextureRegion bulletRegion;
    private Sound bulletSound;
    public int bulletDamage;

    //Music
    private Music music;

    //Status
    private boolean shootUp;
    private boolean shieldUp;

    public SpaceShip(TextureAtlas atlas, SpriteFactory bullets) {
        super(atlas.findRegion("spaceship"), 1, 2, 2);
        this.atlas = atlas;

        // init Spaceship
        this.touchPosition = new Vector2();
        this.v = new Vector2();
        this.reloadInterval = RELOAD_INTERVAL;

        // Music
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/zx-hate.mp3"));
        music.setVolume(0.1f);
        music.setLooping(true);
        music.play();

        // Bullets
        this.bullets = bullets;
        this.bulletV = new Vector2(0, BULLET_SPEED);
        this.bulletRegion = atlas.findRegion("bullet2");
        this.bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        this.bulletDamage = BULLET_DAMAGE;

        setHitPoint(HIP_POINT);
        setShieldUp(false);
        this.shootUp = false;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(SIZE);
        setBottom(worldBounds.getBottom());
        touchPosition.set(pos);
    }

    @Override
    public void update(float delta) {

        // move to center w/o temp vector
        v.set(touchPosition).sub(pos);
        if(v.len() > SPEED) {
            v.setLength(SPEED);
        }
        pos.add(v);
        //pos.mulAdd(v, delta);

        if(getLeft() < worldBounds.getLeft()) {
            stop();
            setLeft(worldBounds.getLeft());
        }

        if(getRight() > worldBounds.getRight()) {
            stop();
            setRight(worldBounds.getRight());
        }

        shoot(delta);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        // w/o multitouch
        if(touch.y < 0) {
            touchPosition.set(touch);
        } else {
            if(touch.x < 0) {
                shootUp = true;
            } else {
                setShieldUp(true);
            }
        }
        return super.touchDown(touch, pointer, button);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        // w/o multitouch
        if(touch.y < 0) {
            touchPosition.set(touch);
        } else {
            if(touch.x < 0) {
                shootUp = false;
            } else {
                setShieldUp(false);
            }
        }
        return super.touchUp(touch, pointer, button);
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
            case Input.Keys.NUM_2:
            case Input.Keys.NUMPAD_2:
                moveLeft();
                break;
            case Input.Keys.RIGHT:
            case Input.Keys.NUM_4:
            case Input.Keys.NUMPAD_4:
                moveRight();
                break;
            case Input.Keys.SPACE:
            case Input.Keys.NUM_0:
            case Input.Keys.NUMPAD_0:
                shootUp = true;
                break;
            case Input.Keys.S:
                setShieldUp(true);
                break;
        }
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.SPACE:
            case Input.Keys.NUM_0:
            case Input.Keys.NUMPAD_0:
                shootUp = false;
                break;
            case Input.Keys.S:
                setShieldUp(false);
                break;
        }
        return super.keyUp(keycode);
    }

    private void shoot(float delta) {
        passingTime +=delta;
        if (passingTime >= reloadInterval && shootUp && !shieldUp) {
            Bullet bullet = (Bullet) bullets.obtain();
            bullet.setup(this, bulletRegion, pos, bulletV, BULLET_SIZE, worldBounds, bulletDamage);
            bulletSound.play();
            passingTime = 0;
        }
    }

    public boolean isShieldUp() {
        return shieldUp;
    }

    public void setShieldUp(boolean shieldUp) {
        this.shieldUp = shieldUp;
        if(shieldUp) {
            frame = 1;
        } else {
            frame = 0;
        }

    }

    @Override
    public void destroy() {
        super.destroy();
        music.stop();
    }


    private void moveLeft() {
        touchPosition.set(pos.x - INERTIA, pos.y);
    }

    private void moveRight() {
        touchPosition.set(pos.x + INERTIA, pos.y);
    }

    private void stop() {
        touchPosition.set(pos);
    }

    @Override
    public void dispose() {
        bulletSound.dispose();
        music.dispose();
        super.dispose();
    }
}
