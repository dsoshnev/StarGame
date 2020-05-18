package ru.geekbrains.sprite;

import ru.geekbrains.base.ButtonSprite;
import ru.geekbrains.utils.Rect;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class ButtonPlay extends ButtonSprite {

    public ButtonPlay(TextureAtlas atlas) {
        super(atlas.findRegion("ButtonPlay"));
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setRight(worldBounds.getRight() - 0.01f);
        setTop(worldBounds.getTop() - 0.01f);
    }

    @Override
    public void action() {

    }
}
