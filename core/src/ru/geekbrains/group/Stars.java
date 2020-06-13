package ru.geekbrains.group;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.sprite.Star;
import ru.geekbrains.utils.Rect;

public class Stars {
    private Star[] stars;

    public Stars(TextureAtlas atlas, int count) {
        stars = new Star[count];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }
    }

    public void resize(Rect worldBounds) {
        for (Star star : stars) {
            star.resize(worldBounds);
        }
    }

    public void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
    }

    public void draw(SpriteBatch batch) {
        for (Star star : stars) {
            star.draw(batch);
        }
    }
}
