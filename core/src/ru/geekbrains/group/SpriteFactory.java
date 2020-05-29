package ru.geekbrains.group;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Pool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ru.geekbrains.sprite.Bullet;
import ru.geekbrains.sprite.EnemyShip;
import ru.geekbrains.sprite.Explosion;
import ru.geekbrains.utils.Rect;
import ru.geekbrains.base.Sprite;

public class SpriteFactory {

    public enum SpriteType {
        BULLET,
        ENEMY_SHIP,
        EXPLOSION
    }

    private SpriteType type;
    private List<Sprite> activeSprites;
    private Pool<Sprite> freeSprites = new Pool<Sprite>(5) {
        @Override
        protected Sprite newObject() {
            Sprite sprite = null;
            switch (type) {
                case BULLET:
                    sprite = new Bullet();
                    break;
                case ENEMY_SHIP:
                    sprite = new EnemyShip();
                    break;
                case EXPLOSION:
                    sprite = new Explosion();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + type);
            }
            return sprite;
        }
    };;

    public SpriteFactory(SpriteType type) {
        this.type = type;
        this.activeSprites = new ArrayList<>();
    }

    public void resize(Rect worldBounds) {
        for (Sprite sprite : activeSprites) {
            sprite.resize(worldBounds);
        }
    }

    public void update(float delta) {

        Iterator itr = activeSprites.iterator();
        while (itr.hasNext()) {
            Sprite sprite = (Sprite) itr.next();
            sprite.update(delta);

            if(!sprite.isActive()) {
                itr.remove();
                freeSprites.free(sprite);
                //System.out.println(toString());
            }
        }

    }

    public void draw(SpriteBatch batch) {
        for (Sprite sprite : activeSprites) {
            sprite.draw(batch);
        }
    }

    public Sprite obtain() {
        Sprite sprite = freeSprites.obtain();
        activeSprites.add(sprite);
        //System.out.println(toString());
        return sprite;
    }

    @Override
    public String toString() {
        return String.format("%s{Active=%s,Free=%s}",
                this.getClass().getName(),
                activeSprites.size(),
                freeSprites.getFree());
    }
}
