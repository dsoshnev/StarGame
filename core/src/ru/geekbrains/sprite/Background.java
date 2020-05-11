package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.geekbrains.base.Sprite;
import ru.geekbrains.utils.Rect;

public class Background extends Sprite {
    private Texture texture;

    public Background(Texture texture) {
        super(new TextureRegion(texture));
        this.texture = texture;
    }

    @Override
    public void resize(Rect worldBounds) {
        this.setHeightProportion(1f);
        this.pos.set(worldBounds.pos);
    }

    public void dispose() {
        texture.dispose();
    }
}
