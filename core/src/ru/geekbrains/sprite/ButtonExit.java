package ru.geekbrains.sprite;

import ru.geekbrains.StarGame;
import ru.geekbrains.base.ButtonSprite;
import ru.geekbrains.utils.Rect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class ButtonExit extends ButtonSprite {

    public ButtonExit(TextureAtlas atlas, StarGame game) {
        super(atlas.findRegion("ButtonExit"), game);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setRight(worldBounds.getRight() - 0.1f);
        setBottom(worldBounds.getBottom() + 0.1f);
    }

    @Override
    public void action() {
        Gdx.app.exit();
    }
}
