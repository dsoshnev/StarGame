package ru.geekbrains.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;

public class Explosion extends Sprite {
    private static final float ANIMATE_INTERVAL = 3f;

    private float animateTimer;
    private Sound sound;

    public Explosion() {
    }

    public Explosion(TextureAtlas atlas, Sound sound) {
        super(atlas.findRegion("explosion"), 1, 2, 2);
        this.sound = sound;
    }

    public void setup(float height, Vector2 pos) {
        setHeightProportion(height);
        setActive(true);
        this.pos.set(pos);
        sound.play();
    }

    @Override
    public void update(float delta) {
        animateTimer += delta;
        if(frame == 0) { frame = 1;} else {frame = 0;}
        if (animateTimer >= ANIMATE_INTERVAL) {
            animateTimer = 0f;
            destroy();
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        frame = 0;
    }

}
