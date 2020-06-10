package ru.geekbrains.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.utils.Rect;


public class SpaceShip extends Sprite {

    private static final float SPEED = 0.005f;
    private static final float SIZE =  0.20f;
    private static final float TIME_TO_SHOOT = 0.25f;
    private Vector2 touchPosition;
    private Vector2 v;

    private List<Bullet> bullets;
    private Pool<Bullet> bulletPool;

    private Sound bulletSound;
    private TextureAtlas atlas;

    private float passingTime;

    public SpaceShip(TextureAtlas atlas) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.atlas = atlas;

        // init Spaceship
        this.touchPosition = new Vector2();
        this.v = new Vector2();

        this.setBottom(-1f);
        //this.touchPosition.set(pos);

        // init Bullets
        this.bullets = new ArrayList<>();
        this.bulletPool = new Pool<Bullet>(5) {
            @Override
            protected Bullet newObject() {
                return new Bullet();
            }
        };

        this.bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(SIZE);
        for (Bullet bullet : bullets) {
            bullet.resize(worldBounds);
        }
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

        passingTime +=delta;
        if (passingTime >= TIME_TO_SHOOT) {
            shoot();
            passingTime = 0;
        }

        Iterator itr = bullets.iterator();
        while (itr.hasNext())
        {
            Bullet bullet = (Bullet) itr.next();
            bullet.update(delta);
            if(!bullet.isActive()) {
                itr.remove();
                bulletPool.free(bullet);
                System.out.printf("Pool/Active:%s/%s%n", bulletPool.getFree(),bullets.size());
            }
        }

    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        for (Bullet bullet : bullets) {
            bullet.draw(batch);
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        touchPosition.set(touch);
        return super.touchDown(touch, pointer, button);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        return super.touchUp(touch, pointer, button);
    }

    @Override
    public boolean keyDown(int keycode) {
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == 62) {
            shoot();
        }
        return super.keyUp(keycode);
    }

    private void shoot() {
        TextureRegion bulletRegion = atlas.findRegion("bulletMainShip");
        Vector2 bulletV = new Vector2(0, 0.5f);
        Bullet bullet = bulletPool.obtain();
        bullet.setup(this, bulletRegion, pos.cpy(), bulletV, 0.01f, worldBounds, 1);
        bullets.add(bullet);
        bulletSound.play();
        System.out.printf("Pool/Active:%s/%s%n", bulletPool.getFree(),bullets.size());
    }


}
